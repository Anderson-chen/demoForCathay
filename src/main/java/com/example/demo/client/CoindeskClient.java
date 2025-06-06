package com.example.demo.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.OffsetDateTime;
import java.util.Map;

@FeignClient(name = "coindeskClient", url = "${coindesk.api.url}")
public interface CoindeskClient {
    @GetMapping("/blog/coindesk.json")
    CoindeskResponse getCoindeskData();

    @Data
    class CoindeskResponse {
        private Time time;
        private String disclaimer;
        private String chartName;

        private Map<String, Currency> bpi;

        @Data
        public static class Time {
            private String updated;
            private OffsetDateTime updatedISO;
            private String updateduk;
        }

        @Data
        public static class Currency {
            private String code;
            private String symbol;
            private String rate;
            private String description;

            @JsonProperty("rate_float")
            private double rateFloat;
        }
    }
}



