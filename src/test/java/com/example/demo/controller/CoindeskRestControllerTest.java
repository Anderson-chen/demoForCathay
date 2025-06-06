package com.example.demo.controller;

import com.example.demo.entity.Currency;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.service.CurrencyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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


    @Test
    void testCoindeskToConvertApi() throws Exception {

        currencyRepository.saveAll(Arrays.asList(
                new Currency(1L, "USD", "美元"),
                new Currency(2L, "GBP", "英鎊"),
                new Currency(3L, "EUR", "歐元")
        ));


        // 呼叫 API
        String json = mockMvc.perform(get("/coindeskToConvert")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updated").value("2024/09/02 07:07:20"))
                .andExpect(jsonPath("$.updatedISO").value("2024/09/02 07:07:20"))
                .andExpect(jsonPath("$.updateduk").value("2024/09/02 08:07:00"))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        // 將 currencyList 取出
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode currencyList = root.path("currencyList");

        assertThat(currencyList).hasSize(3);

        assertThat(currencyList).anySatisfy(currency -> {
            assertThat(currency.path("code").asText()).isEqualTo("USD");
            assertThat(currency.path("name").asText()).isEqualTo("美元");
            assertThat(currency.path("rate").asText()).isEqualTo("57,756.298");
        });

        assertThat(currencyList).anySatisfy(currency -> {
            assertThat(currency.path("code").asText()).isEqualTo("GBP");
            assertThat(currency.path("name").asText()).isEqualTo("英鎊");
            assertThat(currency.path("rate").asText()).isEqualTo("43,984.02");
        });

        assertThat(currencyList).anySatisfy(currency -> {
            assertThat(currency.path("code").asText()).isEqualTo("EUR");
            assertThat(currency.path("name").asText()).isEqualTo("歐元");
            assertThat(currency.path("rate").asText()).isEqualTo("52,243.287");
        });
    }

    @Test
    void testCoindeskToConvertApiThrowError() throws Exception {

        mockMvc.perform(get("/coindeskToConvert")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("Currency is Empty"));

    }

}