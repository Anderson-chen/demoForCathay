package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;


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
    void testTime(){

//       Date date  = new OffsetDateTime("2024-09-02T07:07:20+00:00");
        System.out.println(coindeskServicel.callCoindesk().getTime().getUpdated());

    }

}