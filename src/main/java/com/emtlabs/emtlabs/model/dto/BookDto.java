package com.emtlabs.emtlabs.model.dto;

import com.emtlabs.emtlabs.model.BookCategory;
import com.emtlabs.emtlabs.model.BookState;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookDto(
        @NotBlank(message = "Book name is required")
        String name,

        @NotNull(message = "Category is required")
        BookCategory category,

        @NotNull(message = "Author ID is required")
        Long authorId,

        @NotNull(message = "State is required")
        BookState state,

        @NotNull(message = "Available copies is required")
        @Min(value = 0, message = "Available copies must be >= 0")
        Integer availableCopies
) {
}

