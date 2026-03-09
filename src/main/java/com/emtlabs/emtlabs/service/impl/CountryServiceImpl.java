package com.emtlabs.emtlabs.service.impl;

import com.emtlabs.emtlabs.model.Country;
import com.emtlabs.emtlabs.model.dto.CountryDto;
import com.emtlabs.emtlabs.repository.CountryRepository;
import com.emtlabs.emtlabs.service.CountryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public Country create(CountryDto dto) {
        Country country = new Country();
        country.setName(dto.name());
        country.setContinent(dto.continent());
        return countryRepository.save(country);
    }

    @Override
    public Country update(Long id, CountryDto dto) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country with id " + id + " not found"));
        country.setName(dto.name());
        country.setContinent(dto.continent());
        return countryRepository.save(country);
    }

    @Override
    public void deleteById(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new EntityNotFoundException("Country with id " + id + " not found");
        }
        countryRepository.deleteById(id);
    }
}

