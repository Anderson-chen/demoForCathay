package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CoindeskServiceTest {

    @Autowired
    CoindeskService coindeskServicel;

    @Test
    void
    testCallCoindesk() {
        System.out.println(coindeskServicel.callCoindesk());
    }


}