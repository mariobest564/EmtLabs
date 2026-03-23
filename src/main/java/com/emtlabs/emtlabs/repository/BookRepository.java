package com.emtlabs.emtlabs.repository;

import com.emtlabs.emtlabs.model.Book;
import com.emtlabs.emtlabs.model.BookCategory;
import com.emtlabs.emtlabs.model.BookState;
import com.emtlabs.emtlabs.model.dto.BookCategoryStatsProjection;
import com.emtlabs.emtlabs.model.dto.BookDetailedProjection;
import com.emtlabs.emtlabs.model.dto.BookLibraryViewProjection;
import com.emtlabs.emtlabs.model.dto.BookSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByDeletedFalse();

    List<Book> findAllByCategoryAndDeletedFalse(BookCategory category);

    @EntityGraph(attributePaths = {"author", "author.country"})
    @Query("""
            select b
            from Book b
            where b.deleted = false
              and (:category is null or b.category = :category)
              and (:state is null or b.state = :state)
              and (:authorId is null or b.author.id = :authorId)
              and (:available is null
                   or (:available = true and b.availableCopies > 0)
                   or (:available = false and b.availableCopies <= 0))
            """)
    Page<BookSummaryProjection> searchSummaries(
            @Param("category") BookCategory category,
            @Param("state") BookState state,
            @Param("authorId") Long authorId,
            @Param("available") Boolean available,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"author", "author.country"})
    @Query("""
            select b
            from Book b
            where b.deleted = false
              and (:category is null or b.category = :category)
              and (:state is null or b.state = :state)
              and (:authorId is null or b.author.id = :authorId)
              and (:available is null
                   or (:available = true and b.availableCopies > 0)
                   or (:available = false and b.availableCopies <= 0))
            """)
    Page<BookDetailedProjection> searchDetails(
            @Param("category") BookCategory category,
            @Param("state") BookState state,
            @Param("authorId") Long authorId,
            @Param("available") Boolean available,
            Pageable pageable
    );

    @Query(value = """
            select
                v.book_id as id,
                v.book_name as name,
                v.category as category,
                v.state as state,
                v.available_copies as availableCopies,
                v.author_full_name as authorFullName,
                v.country_name as countryName
            from book_library_view v
            order by v.book_id
            """, nativeQuery = true)
    List<BookLibraryViewProjection> findAllFromLibraryView();

    @Query(value = """
            select
                mv.category as category,
                mv.total_books as totalBooks,
                mv.total_available_copies as totalAvailableCopies,
                mv.not_good_condition_books as notGoodConditionBooks
            from book_category_stats_mv mv
            order by mv.category
            """, nativeQuery = true)
    List<BookCategoryStatsProjection> findCategoryStats();

    @Transactional
    @Modifying
    @Query(value = "refresh materialized view book_category_stats_mv", nativeQuery = true)
    void refreshCategoryStatsMaterializedView();
}
