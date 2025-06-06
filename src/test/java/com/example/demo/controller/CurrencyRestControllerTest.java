package com.example.demo.controller;

import com.example.demo.entity.Currency;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CurrencyRestControllerTest {
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void init() {
        List<Currency> list = Arrays.asList(
                new Currency(1L, "USD", "美元"),
                new Currency(2L, "TWD", "新台幣")
        );

        currencyRepository.saveAll(list);
    }

    @Test
    void testQueryAll() throws Exception {
        mockMvc.perform(get("/currency"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].code", is("USD")));
    }

    @Test
    void testQueryById() throws Exception {
        mockMvc.perform(get("/currency/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("USD")))
                .andExpect(jsonPath("$.name", is("美元")));
    }

    @Test
    void testQueryByIdForNotFound() throws Exception {
        mockMvc.perform(get("/currency/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateCurrency() throws Exception {
        CurrencyRestController.CurrencyCo co = new CurrencyRestController.CurrencyCo();
        co.setCode("EUR");
        co.setName("歐元");

        mockMvc.perform(post("/currency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(co)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("EUR")));
    }

    @Test
    void testUpdateCurrency() throws Exception {
        CurrencyRestController.CurrencyCo co = new CurrencyRestController.CurrencyCo();
        co.setCode("JPY");
        co.setName("日圓");
        mockMvc.perform(put("/currency/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(co)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("JPY")))
                .andExpect(jsonPath("$.name", is("日圓")));
    }

    @Test
    void testDeleteCurrencyF() throws Exception {
        mockMvc.perform(delete("/currency/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteCurrencyForNotFound() throws Exception {
        mockMvc.perform(delete("/currency/3"))
                .andExpect(status().isNotFound());
    }
}