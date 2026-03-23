package com.emtlabs.emtlabs.service.impl;

import com.emtlabs.emtlabs.model.Author;
import com.emtlabs.emtlabs.model.Book;
import com.emtlabs.emtlabs.model.BookCategory;
import com.emtlabs.emtlabs.model.dto.BookCategoryStatsProjection;
import com.emtlabs.emtlabs.model.dto.BookDetailedProjection;
import com.emtlabs.emtlabs.model.dto.BookDto;
import com.emtlabs.emtlabs.model.dto.BookLibraryViewProjection;
import com.emtlabs.emtlabs.model.dto.BookSearchCriteria;
import com.emtlabs.emtlabs.model.dto.BookSummaryProjection;
import com.emtlabs.emtlabs.repository.AuthorRepository;
import com.emtlabs.emtlabs.repository.BookRepository;
import com.emtlabs.emtlabs.service.BookService;
import com.emtlabs.emtlabs.service.event.BookRentedEvent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAllByDeletedFalse();
    }

    @Override
    public List<Book> findByCategory(BookCategory category) {
        return bookRepository.findAllByCategoryAndDeletedFalse(category);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Page<BookSummaryProjection> searchSummaries(BookSearchCriteria criteria, Pageable pageable) {
        return bookRepository.searchSummaries(
                criteria.category(),
                criteria.state(),
                criteria.authorId(),
                criteria.available(),
                pageable
        );
    }

    @Override
    public Page<BookDetailedProjection> searchDetails(BookSearchCriteria criteria, Pageable pageable) {
        return bookRepository.searchDetails(
                criteria.category(),
                criteria.state(),
                criteria.authorId(),
                criteria.available(),
                pageable
        );
    }

    @Override
    public List<BookLibraryViewProjection> findAllFromLibraryView() {
        return bookRepository.findAllFromLibraryView();
    }

    @Override
    public List<BookCategoryStatsProjection> findCategoryStats() {
        return bookRepository.findCategoryStats();
    }

    @Override
    @Transactional
    public Book rentBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found"));

        if (book.isDeleted()) {
            throw new IllegalStateException("Book with id " + id + " is deleted");
        }

        if (book.getAvailableCopies() == null || book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("Book with id " + id + " has no available copies");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        Book savedBook = bookRepository.save(book);

        eventPublisher.publishEvent(new BookRentedEvent(
                savedBook.getId(),
                savedBook.getName(),
                savedBook.getAvailableCopies()
        ));

        return savedBook;
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
    public void softDeleteById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found"));
        book.setDeleted(true);
        bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book with id " + id + " not found");
        }
        bookRepository.deleteById(id);
    }
}
