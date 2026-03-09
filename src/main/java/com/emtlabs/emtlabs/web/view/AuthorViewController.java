package com.emtlabs.emtlabs.web.view;

import com.emtlabs.emtlabs.model.Author;
import com.emtlabs.emtlabs.model.dto.AuthorDto;
import com.emtlabs.emtlabs.service.AuthorService;
import com.emtlabs.emtlabs.service.CountryService;
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
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorViewController {

    private final AuthorService authorService;
    private final CountryService countryService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "authors";
    }

    @GetMapping("/add")
    public String showCreateForm(Model model) {
        AuthorDto dto = new AuthorDto("", "", null);
        return prepareForm(model, dto, false, null);
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Author author = authorService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

        AuthorDto dto = new AuthorDto(author.getName(), author.getSurname(),
                author.getCountry() != null ? author.getCountry().getId() : null);
        return prepareForm(model, dto, true, id);
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("author") AuthorDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareForm(model, dto, false, null);
        }
        authorService.create(dto);
        return "redirect:/authors";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("author") AuthorDto dto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareForm(model, dto, true, id);
        }
        authorService.update(id, dto);
        return "redirect:/authors";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        authorService.deleteById(id);
        return "redirect:/authors";
    }

    private String prepareForm(Model model, AuthorDto dto, boolean editing, Long id) {
        model.addAttribute("author", dto);
        model.addAttribute("authorId", id);
        model.addAttribute("countries", countryService.findAll());
        model.addAttribute("editing", editing);
        model.addAttribute("formTitle", editing ? "Edit Author" : "Add Author");
        return "author-form";
    }
}
