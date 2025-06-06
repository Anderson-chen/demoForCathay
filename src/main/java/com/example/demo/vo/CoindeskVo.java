package com.example.demo.vo;


import lombok.Data;

@Data
public class CoindeskVo {
    private String updatedTime;

    @Data
    public static class Currency {
        private String code;
        private String name;
        private String rate;
    }
}

