package com.emtlabs.emtlabs.service;

import com.emtlabs.emtlabs.model.Author;
import com.emtlabs.emtlabs.model.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> findAll();

    Optional<Author> findById(Long id);

    Author create(AuthorDto dto);

    Author update(Long id, AuthorDto dto);

    void deleteById(Long id);
}
