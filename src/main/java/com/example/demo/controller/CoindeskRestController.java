package com.example.demo.controller;


import com.example.demo.client.CoindeskClient;
import com.example.demo.service.CoindeskService;
import com.example.demo.vo.CoindeskVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CoindeskRestController {

    private final CoindeskService coindeskService;

    @GetMapping("/coindesk")
    public CoindeskClient.CoindeskResponse callCoindesk() {
        return coindeskService.callCoindesk();
    }

    @GetMapping("/coindeskToConvert")
    public CoindeskVo callCoindeskToConvert() {
        return coindeskService.callCoindeskToConvert();
    }


}
