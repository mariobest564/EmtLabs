package com.emtlabs.emtlabs.web;

import com.emtlabs.emtlabs.model.Country;
import com.emtlabs.emtlabs.model.dto.CountryDto;
import com.emtlabs.emtlabs.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
@Tag(name = "Country", description = "Country management API")
public class CountryController {

    private final CountryService countryService;

    @Operation(summary = "Get all countries")
    @GetMapping
    public ResponseEntity<List<Country>> findAll() {
        return ResponseEntity.ok(countryService.findAll());
    }

    @Operation(summary = "Get country by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Country> findById(@PathVariable Long id) {
        return countryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new country")
    @PostMapping
    public ResponseEntity<Country> create(@Valid @RequestBody CountryDto dto) {
        return ResponseEntity.ok(countryService.create(dto));
    }

    @Operation(summary = "Update a country")
    @PutMapping("/{id}")
    public ResponseEntity<Country> update(@PathVariable Long id, @Valid @RequestBody CountryDto dto) {
        return ResponseEntity.ok(countryService.update(id, dto));
    }

    @Operation(summary = "Delete a country")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        countryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
