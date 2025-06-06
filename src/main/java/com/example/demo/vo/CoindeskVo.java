package com.example.demo.vo;


import lombok.Data;

import java.util.List;

@Data
public class CoindeskVo {
    private String updated;
    private String updatedISO;
    private String updateduk;
    private List<Currency> currencyList;

    @Data
    public static class Currency {
        private String code;
        private String name;
        private String rate;
    }
}

