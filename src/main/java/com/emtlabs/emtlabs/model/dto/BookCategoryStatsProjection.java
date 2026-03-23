package com.emtlabs.emtlabs.model.dto;

public interface BookCategoryStatsProjection {

    String getCategory();

    Long getTotalBooks();

    Long getTotalAvailableCopies();

    Long getNotGoodConditionBooks();
}

