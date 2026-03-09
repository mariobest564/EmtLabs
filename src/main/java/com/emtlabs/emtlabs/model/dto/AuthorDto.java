package com.emtlabs.emtlabs.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthorDto(
        @NotBlank(message = "Author name is required")
        String name,

        @NotBlank(message = "Author surname is required")
        String surname,

        @NotNull(message = "Country ID is required")
        Long countryId
) {
}
