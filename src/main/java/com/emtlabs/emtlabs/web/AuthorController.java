package com.emtlabs.emtlabs.web;

import com.emtlabs.emtlabs.model.Author;
import com.emtlabs.emtlabs.model.dto.AuthorDto;
import com.emtlabs.emtlabs.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@Tag(name = "Author", description = "Author management API")
public class AuthorController {

    private final AuthorService authorService;

    @Operation(summary = "Get all authors")
    @GetMapping
    public ResponseEntity<List<Author>> findAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @Operation(summary = "Get author by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Author> findById(@PathVariable Long id) {
        return authorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new author")
    @PostMapping
    public ResponseEntity<Author> create(@Valid @RequestBody AuthorDto dto) {
        return ResponseEntity.ok(authorService.create(dto));
    }

    @Operation(summary = "Update an author")
    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable Long id, @Valid @RequestBody AuthorDto dto) {
        return ResponseEntity.ok(authorService.update(id, dto));
    }

    @Operation(summary = "Delete an author")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

