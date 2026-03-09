package com.emtlabs.emtlabs.web.view;

import com.emtlabs.emtlabs.model.Country;
import com.emtlabs.emtlabs.model.dto.CountryDto;
import com.emtlabs.emtlabs.service.CountryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryViewController {

    private final CountryService countryService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("countries", countryService.findAll());
        return "countries";
    }

    @GetMapping("/add")
    public String showCreateForm(Model model) {
        CountryDto dto = new CountryDto("", "");
        return prepareForm(model, dto, false, null);
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Country country = countryService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));
        CountryDto dto = new CountryDto(country.getName(), country.getContinent());
        return prepareForm(model, dto, true, id);
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("country") CountryDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareForm(model, dto, false, null);
        }
        countryService.create(dto);
        return "redirect:/countries";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("country") CountryDto dto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareForm(model, dto, true, id);
        }
        countryService.update(id, dto);
        return "redirect:/countries";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        countryService.deleteById(id);
        return "redirect:/countries";
    }

    private String prepareForm(Model model, CountryDto dto, boolean editing, Long id) {
        model.addAttribute("country", dto);
        model.addAttribute("countryId", id);
        model.addAttribute("editing", editing);
        model.addAttribute("formTitle", editing ? "Edit Country" : "Add Country");
        return "country-form";
    }
}

