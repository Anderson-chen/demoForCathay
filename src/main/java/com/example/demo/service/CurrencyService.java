package com.example.demo.service;

import com.example.demo.controller.CurrencyRestController;
import com.example.demo.entity.Currency;
import com.example.demo.repository.CurrencyRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CurrencyService {

    private static final String CURRENCY_NOT_FOUND = "Currency not found";
    private final CurrencyRepository currencyRepository;

    public List<Currency> query() {
        List<Currency> result = currencyRepository.findAll();
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CURRENCY_NOT_FOUND);
        }
        return result;
    }

    public Currency query(Long id) {
        return currencyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CURRENCY_NOT_FOUND));
    }

    public Currency create(CurrencyRestController.CurrencyCo currencyCo) {
        Currency currency = new Currency();
        currency.setCode(currencyCo.getCode());
        currency.setName(currencyCo.getName());
        currencyRepository.save(currency);
        return currency;
    }

    @Transactional
    public Currency update(Long id, CurrencyRestController.CurrencyCo currencyCo) {
        Optional<Currency> opt = currencyRepository.findById(id);
        Currency updateCurrency = opt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CURRENCY_NOT_FOUND));
        updateCurrency.setCode(currencyCo.getCode());
        updateCurrency.setName(currencyCo.getName());
        return updateCurrency;
    }

    public void delete(Long id) {
        Optional<Currency> opt = currencyRepository.findById(id);
        Currency deleteCurrency = opt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CURRENCY_NOT_FOUND));
        currencyRepository.delete(deleteCurrency);
    }
}
