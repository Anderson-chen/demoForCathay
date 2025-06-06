package com.example.demo.service;

import com.example.demo.client.CoindeskClient;
import com.example.demo.entity.Currency;
import com.example.demo.vo.CoindeskVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class CoindeskServiceTest {

    @Autowired
    CoindeskService coindeskServicel;

    @Test
    void testConvertTimeForUpdated() {
        String input = "Sep 2, 2024 07:07:20 UTC";
        String expected = "2024/09/02 07:07:20";
        String actual = coindeskServicel.convertTime(1, input);
        assertEquals(expected, actual);
    }

    @Test
    void testConvertTimeForUpdatedISO() {
        String input = "2024-09-02T07:07:20+00:00";
        String expected = "2024/09/02 07:07:20";
        String actual = coindeskServicel.convertTime(2, input);
        assertEquals(expected, actual);
    }

    @Test
    void testConvertTimeForUpdateduk() {
        String input = "Sep 2, 2024 at 08:07 BST";
        String expected = "2024/09/02 08:07:00";
        String actual = coindeskServicel.convertTime(3, input);
        assertEquals(expected, actual);
    }

    @Test
    void testConvertTimeThrowError() {
        assertThatThrownBy(() -> coindeskServicel.convertTime(99, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unsupported time type: 99");
    }

    @Test
    void testConvertZH() {

        HashMap<String, CoindeskClient.CoindeskResponse.Currency> bpi = new HashMap<String, CoindeskClient.CoindeskResponse.Currency>();
        CoindeskClient.CoindeskResponse.Currency currencyUSD = new CoindeskClient.CoindeskResponse.Currency();
        bpi.put("USD", currencyUSD);
        currencyUSD.setCode("USD");
        currencyUSD.setRate("57,756.298");

        CoindeskClient.CoindeskResponse.Currency currencyGBP = new CoindeskClient.CoindeskResponse.Currency();
        bpi.put("GBP", currencyGBP);
        currencyGBP.setCode("GBP");
        currencyGBP.setRate("43,984.02");

        List<Currency> currencyList = Arrays.asList(
                new Currency(1L, "USD", "美元"),
                new Currency(2L, "GBP", "英鎊")
        );

        List<CoindeskVo.Currency> actual = coindeskServicel.convertZH(bpi, currencyList);


        assertThat(actual).hasSize(2);

        assertThat(actual).anySatisfy(c -> {
            assertThat(c.getCode()).isEqualTo("USD");
            assertThat(c.getName()).isEqualTo("美元");
            assertThat(c.getRate()).isEqualTo("57,756.298");
        });

        assertThat(actual).anySatisfy(c -> {
            assertThat(c.getCode()).isEqualTo("GBP");
            assertThat(c.getName()).isEqualTo("英鎊");
            assertThat(c.getRate()).isEqualTo("43,984.02");
        });
    }
}