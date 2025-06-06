package com.example.demo.controller;

import com.example.demo.entity.Currency;
import com.example.demo.service.CurrencyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/currency")
public class CurrencyRestController {

    private final CurrencyService currencyService;


    @GetMapping
    public List<Currency> query() {
        return currencyService.query();
    }

    @GetMapping("/{id}")
    public Currency query(@PathVariable Long id) {
        return currencyService.query(id);
    }

    @PostMapping
    public Currency create(@RequestBody @Valid CurrencyCo currencyCo) {
        return currencyService.create(currencyCo);
    }


    @PutMapping("/{id}")
    public Currency update(@PathVariable Long id, @RequestBody @Valid CurrencyCo currencyCo) {
        return currencyService.update(id, currencyCo);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        currencyService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Data
    public static class CurrencyCo {
        @NotBlank
        private String code;

        @NotBlank
        private String name;
    }
}
