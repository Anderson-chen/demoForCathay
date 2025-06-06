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
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CoindeskRestControllerTest {
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CurrencyService currencyService;

    @Test
    void testCallCoindeskApi() throws Exception {
        mockMvc.perform(get("/coindesk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.time.updated", not(emptyOrNullString())))
                .andExpect(jsonPath("$.time.updatedISO", containsString("T")))
                .andExpect(jsonPath("$.disclaimer", is("just for test")))
                .andExpect(jsonPath("$.chartName", is("Bitcoin")))
                .andExpect(jsonPath("$.bpi.USD.code", is("USD")))
                .andExpect(jsonPath("$.bpi.USD.rate_float", greaterThan(0.0)))
                .andExpect(jsonPath("$.bpi.GBP.code", is("GBP")))
                .andExpect(jsonPath("$.bpi.EUR.code", is("EUR")));
    }

}