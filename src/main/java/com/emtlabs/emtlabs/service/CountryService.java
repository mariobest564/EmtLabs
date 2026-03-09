package com.emtlabs.emtlabs.service;

import com.emtlabs.emtlabs.model.Country;
import com.emtlabs.emtlabs.model.dto.CountryDto;

import java.util.List;
import java.util.Optional;

public interface CountryService {

    List<Country> findAll();

    Optional<Country> findById(Long id);

    Country create(CountryDto dto);

    Country update(Long id, CountryDto dto);

    void deleteById(Long id);
}

