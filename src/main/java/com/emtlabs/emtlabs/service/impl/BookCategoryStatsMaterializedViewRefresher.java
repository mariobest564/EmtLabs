package com.emtlabs.emtlabs.service.impl;

import com.emtlabs.emtlabs.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookCategoryStatsMaterializedViewRefresher {

    private final BookRepository bookRepository;

    @Scheduled(fixedDelayString = "${books.mv.refresh-interval-ms:300000}")
    public void refreshMaterializedView() {
        log.info("Starting refresh of materialized view book_category_stats_mv");
        bookRepository.refreshCategoryStatsMaterializedView();
        log.info("Finished refresh of materialized view book_category_stats_mv");
    }
}

