package com.emtlabs.emtlabs.web;

import com.emtlabs.emtlabs.model.BookCategory;
import com.emtlabs.emtlabs.model.BookState;
import com.emtlabs.emtlabs.model.dto.BookCategoryStatsProjection;
import com.emtlabs.emtlabs.model.dto.BookDetailedProjection;
import com.emtlabs.emtlabs.model.dto.BookLibraryViewProjection;
import com.emtlabs.emtlabs.model.dto.BookSearchCriteria;
import com.emtlabs.emtlabs.model.dto.BookSortField;
import com.emtlabs.emtlabs.model.dto.BookSummaryProjection;
import com.emtlabs.emtlabs.model.dto.RentBookResponse;
import com.emtlabs.emtlabs.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "Books API", description = "Search, projection endpoints, views and rental actions")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookApiController {

    private static final int DEFAULT_PAGE = 0;
    private static final int MAX_SIZE = 100;

    private final BookService bookService;

    @Operation(summary = "List/search books with pagination, sorting and filters")
    @GetMapping
    public Page<BookSummaryProjection> searchSummaries(
            @RequestParam(required = false) BookCategory category,
            @RequestParam(required = false) BookState state,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Boolean available,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "CREATED_AT") BookSortField sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        BookSearchCriteria criteria = new BookSearchCriteria(category, state, authorId, available);
        Pageable pageable = buildPageable(page, size, sortBy, direction);
        return bookService.searchSummaries(criteria, pageable);
    }

    @Operation(summary = "List books using a detailed projection")
    @GetMapping("/details")
    public Page<BookDetailedProjection> searchDetails(
            @RequestParam(required = false) BookCategory category,
            @RequestParam(required = false) BookState state,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Boolean available,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "CREATED_AT") BookSortField sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        BookSearchCriteria criteria = new BookSearchCriteria(category, state, authorId, available);
        Pageable pageable = buildPageable(page, size, sortBy, direction);
        return bookService.searchDetails(criteria, pageable);
    }

    @Operation(summary = "Read simplified book data from database view")
    @GetMapping("/view")
    public List<BookLibraryViewProjection> getLibraryView() {
        return bookService.findAllFromLibraryView();
    }

    @Operation(summary = "Read aggregated category data from materialized view")
    @GetMapping("/category-stats")
    public List<BookCategoryStatsProjection> getCategoryStats() {
        return bookService.findCategoryStats();
    }

    @Operation(summary = "Rent a book and trigger rental events/listeners")
    @PostMapping("/{id}/rent")
    public RentBookResponse rentBook(@PathVariable Long id) {
        try {
            var rentedBook = bookService.rentBook(id);
            return new RentBookResponse(rentedBook.getId(), rentedBook.getName(), rentedBook.getAvailableCopies());
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (IllegalStateException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
        }
    }

    private Pageable buildPageable(int page, int size, BookSortField sortBy, Sort.Direction direction) {
        int safePage = Math.max(page, DEFAULT_PAGE);
        int safeSize = Math.min(Math.max(size, 1), MAX_SIZE);
        Sort sort = Sort.by(direction, sortBy.getProperty());
        return PageRequest.of(safePage, safeSize, sort);
    }
}
