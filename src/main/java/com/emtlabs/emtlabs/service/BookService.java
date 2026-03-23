package com.emtlabs.emtlabs.service;

import com.emtlabs.emtlabs.model.Book;
import com.emtlabs.emtlabs.model.BookCategory;
import com.emtlabs.emtlabs.model.dto.BookCategoryStatsProjection;
import com.emtlabs.emtlabs.model.dto.BookDetailedProjection;
import com.emtlabs.emtlabs.model.dto.BookDto;
import com.emtlabs.emtlabs.model.dto.BookLibraryViewProjection;
import com.emtlabs.emtlabs.model.dto.BookSearchCriteria;
import com.emtlabs.emtlabs.model.dto.BookSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    List<Book> findByCategory(BookCategory category);

    Optional<Book> findById(Long id);

    Page<BookSummaryProjection> searchSummaries(BookSearchCriteria criteria, Pageable pageable);

    Page<BookDetailedProjection> searchDetails(BookSearchCriteria criteria, Pageable pageable);

    List<BookLibraryViewProjection> findAllFromLibraryView();

    List<BookCategoryStatsProjection> findCategoryStats();

    Book rentBook(Long id);

    Book create(BookDto dto);

    Book update(Long id, BookDto dto);

    void deleteById(Long id);

    void softDeleteById(Long id);
}
