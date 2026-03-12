package com.emtlabs.emtlabs.repository;

import com.emtlabs.emtlabs.model.Book;
import com.emtlabs.emtlabs.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByDeletedFalse();

    List<Book> findAllByCategoryAndDeletedFalse(BookCategory category);
}

