package com.emtlabs.emtlabs.web;

import com.emtlabs.emtlabs.model.Book;
import com.emtlabs.emtlabs.model.dto.BookDto;
import com.emtlabs.emtlabs.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Book", description = "Book management API")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Get all books")
    @GetMapping
    public ResponseEntity<List<Book>> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @Operation(summary = "Get book by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new book")
    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody BookDto dto) {
        return ResponseEntity.ok(bookService.create(dto));
    }

    @Operation(summary = "Update a book")
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @Valid @RequestBody BookDto dto) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    @Operation(summary = "Delete a book")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

