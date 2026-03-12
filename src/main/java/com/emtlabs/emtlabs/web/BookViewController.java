package com.emtlabs.emtlabs.web;

import com.emtlabs.emtlabs.model.Book;
import com.emtlabs.emtlabs.model.BookCategory;
import com.emtlabs.emtlabs.model.BookState;
import com.emtlabs.emtlabs.model.dto.BookDto;
import com.emtlabs.emtlabs.service.AuthorService;
import com.emtlabs.emtlabs.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookViewController {

    private final BookService bookService;
    private final AuthorService authorService;

    @GetMapping
    public String list(@RequestParam(required = false) BookCategory category, Model model) {
        if (category != null) {
            model.addAttribute("books", bookService.findByCategory(category));
        } else {
            model.addAttribute("books", bookService.findAll());
        }
        model.addAttribute("categories", BookCategory.values());
        model.addAttribute("selectedCategory", category);
        return "books";
    }

    @GetMapping("/add")
    public String showCreateForm(Model model) {
        BookDto dto = new BookDto("", null, null, null, 0);
        return prepareForm(model, dto, false, null);
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        BookDto dto = new BookDto(
                book.getName(),
                book.getCategory(),
                book.getAuthor() != null ? book.getAuthor().getId() : null,
                book.getState(),
                book.getAvailableCopies()
        );
        return prepareForm(model, dto, true, id);
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("book") BookDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareForm(model, dto, false, null);
        }
        bookService.create(dto);
        return "redirect:/books";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("book") BookDto dto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareForm(model, dto, true, id);
        }
        bookService.update(id, dto);
        return "redirect:/books";
    }

    @PostMapping("/{id}/soft-delete")
    public String softDelete(@PathVariable Long id) {
        bookService.softDeleteById(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    private String prepareForm(Model model, BookDto dto, boolean editing, Long id) {
        model.addAttribute("book", dto);
        model.addAttribute("bookId", id);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("categories", BookCategory.values());
        model.addAttribute("states", BookState.values());
        model.addAttribute("editing", editing);
        model.addAttribute("formTitle", editing ? "Edit Book" : "Add Book");
        return "book-form";
    }
}
