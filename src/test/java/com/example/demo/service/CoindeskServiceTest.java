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


@SpringBootTest
class CoindeskServiceTest {

    @Autowired
    CoindeskService coindeskServicel;

    @Test
    void
    testCallCoindesk() {

//        = new LocalDateTime();
//
//        coindeskServicel.convertTime(new OffsetDateTime());
    }


    @Test
    void testTime() {

//       Date date  = new OffsetDateTime("2024-09-02T07:07:20+00:00");
        System.out.println(coindeskServicel.callCoindesk().getTime().getUpdated());

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