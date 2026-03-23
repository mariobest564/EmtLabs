package com.emtlabs.emtlabs.model.dto;

public interface BookLibraryViewProjection {

    Long getId();

    String getName();

    String getCategory();

    String getState();

    Integer getAvailableCopies();

    String getAuthorFullName();

    String getCountryName();
}

