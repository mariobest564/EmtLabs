package com.emtlabs.emtlabs.service;

import com.emtlabs.emtlabs.model.Book;
import com.emtlabs.emtlabs.model.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Book create(BookDto dto);

    Book update(Long id, BookDto dto);

    void deleteById(Long id);
}

