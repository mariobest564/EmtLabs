package com.emtlabs.emtlabs.service.impl;

import com.emtlabs.emtlabs.model.Author;
import com.emtlabs.emtlabs.model.Country;
import com.emtlabs.emtlabs.model.dto.AuthorDto;
import com.emtlabs.emtlabs.repository.AuthorRepository;
import com.emtlabs.emtlabs.repository.CountryRepository;
import com.emtlabs.emtlabs.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author create(AuthorDto dto) {
        Country country = countryRepository.findById(dto.countryId())
                .orElseThrow(() -> new EntityNotFoundException("Country with id " + dto.countryId() + " not found"));

        Author author = new Author();
        author.setName(dto.name());
        author.setSurname(dto.surname());
        author.setCountry(country);
        return authorRepository.save(author);
    }

    @Override
    public Author update(Long id, AuthorDto dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " not found"));

        Country country = countryRepository.findById(dto.countryId())
                .orElseThrow(() -> new EntityNotFoundException("Country with id " + dto.countryId() + " not found"));

        author.setName(dto.name());
        author.setSurname(dto.surname());
        author.setCountry(country);
        return authorRepository.save(author);
    }

    @Override
    public void deleteById(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new EntityNotFoundException("Author with id " + id + " not found");
        }
        authorRepository.deleteById(id);
    }
}

