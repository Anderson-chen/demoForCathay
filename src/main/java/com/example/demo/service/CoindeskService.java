package com.example.demo.service;


import com.example.demo.client.CoindeskClient;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.vo.CoindeskVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class CoindeskService {

    private final CoindeskClient coindeskClient;

    private CurrencyRepository currencyRepository;

    //呼叫 coindesk 的 API
    public CoindeskClient.CoindeskResponse callCoindesk() {
        return coindeskClient.getCoindeskData();
    }

    //呼叫 coindesk 的 API 並做資料處理
    public CoindeskVo convertToCoindeskVo() {
        CoindeskClient.CoindeskResponse fetchData = callCoindesk();

        //將CoindeskResponse轉換成CoindeskVo
        CoindeskVo coindeskVo = new CoindeskVo();
        fetchData.getTime().getUpdated();
        //時間轉換

        //幣別中英轉換


        return coindeskVo;
    }

    /**
     * 更新時間(時間格式範例:1990/01/01 00:00:00)。
     *
     * @param updateTime 原始時間格式
     * @return 轉換結果
     */
    public String convertTime(OffsetDateTime updateTime) {


        return null;
    }

}
