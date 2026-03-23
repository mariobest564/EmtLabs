package com.emtlabs.emtlabs.model.dto;

public record RentBookResponse(
        Long id,
        String name,
        Integer availableCopies
) {
}

