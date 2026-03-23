package com.emtlabs.emtlabs.model.dto;

import com.emtlabs.emtlabs.model.BookCategory;
import com.emtlabs.emtlabs.model.BookState;

public interface BookSummaryProjection {

    Long getId();

    String getName();

    BookCategory getCategory();

    BookState getState();

    Integer getAvailableCopies();
}

