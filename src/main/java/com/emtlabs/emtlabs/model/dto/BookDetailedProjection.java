package com.emtlabs.emtlabs.model.dto;

import com.emtlabs.emtlabs.model.BookCategory;
import com.emtlabs.emtlabs.model.BookState;

public interface BookDetailedProjection {

    Long getId();

    String getName();

    BookCategory getCategory();

    BookState getState();

    Integer getAvailableCopies();

    AuthorProjection getAuthor();

    interface AuthorProjection {
        String getName();

        String getSurname();

        CountryProjection getCountry();
    }

    interface CountryProjection {
        String getName();
    }
}

