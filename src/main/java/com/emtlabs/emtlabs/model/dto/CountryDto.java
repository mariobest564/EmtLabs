package com.emtlabs.emtlabs.model.dto;

import jakarta.validation.constraints.NotBlank;

public record CountryDto(
        @NotBlank(message = "Country name is required")
        String name,

        @NotBlank(message = "Continent is required")
        String continent
) {
}

