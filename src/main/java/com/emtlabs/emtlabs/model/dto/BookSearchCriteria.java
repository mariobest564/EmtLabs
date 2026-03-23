package com.emtlabs.emtlabs.model.dto;

import com.emtlabs.emtlabs.model.BookCategory;
import com.emtlabs.emtlabs.model.BookState;

public record BookSearchCriteria(
        BookCategory category,
        BookState state,
        Long authorId,
        Boolean available
) {
}

