package com.emtlabs.emtlabs.model.dto;

public enum BookSortField {
    NAME("name"),
    CREATED_AT("createdAt");

    private final String property;

    BookSortField(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}

