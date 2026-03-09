package com.emtlabs.emtlabs.service.impl;

import com.emtlabs.emtlabs.model.Author;
import com.emtlabs.emtlabs.model.Book;
import com.emtlabs.emtlabs.model.dto.BookDto;
import com.emtlabs.emtlabs.repository.AuthorRepository;
import com.emtlabs.emtlabs.repository.BookRepository;
import com.emtlabs.emtlabs.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book create(BookDto dto) {
        Author author = authorRepository.findById(dto.authorId())
                .orElseThrow(() -> new EntityNotFoundException("Author with id " + dto.authorId() + " not found"));

        Book book = new Book();
        book.setName(dto.name());
        book.setCategory(dto.category());
        book.setAuthor(author);
        book.setState(dto.state());
        book.setAvailableCopies(dto.availableCopies());
        return bookRepository.save(book);
    }

    @Override
    public Book update(Long id, BookDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found"));

        Author author = authorRepository.findById(dto.authorId())
                .orElseThrow(() -> new EntityNotFoundException("Author with id " + dto.authorId() + " not found"));

        book.setName(dto.name());
        book.setCategory(dto.category());
        book.setAuthor(author);
        book.setState(dto.state());
        book.setAvailableCopies(dto.availableCopies());
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book with id " + id + " not found");
        }
        bookRepository.deleteById(id);
    }
}

