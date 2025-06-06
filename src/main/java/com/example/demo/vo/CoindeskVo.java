package com.example.demo.vo;


import lombok.Data;

@Data
public class CoindeskVo {
    private String updated;
    private String updatedISO;
    private String updateduk;

    @Data
    public static class Currency {
        private String code;
        private String name;
        private String rate;
    }
}

