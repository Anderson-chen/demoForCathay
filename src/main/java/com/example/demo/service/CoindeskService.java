package com.example.demo.service;


import com.example.demo.client.CoindeskClient;
import com.example.demo.entity.Currency;
import com.example.demo.exception.MyException;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.vo.CoindeskVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        //查詢中英幣別對照
        List<Currency> currency = currencyRepository.findAll();

        if (currency.isEmpty()) {
            throw new MyException("Currency is Empty");
        }

        //呼叫https://kengp3.github.io/blog/coindesk.json
        CoindeskClient.CoindeskResponse fetchData = callCoindesk();

        //將CoindeskResponse轉換成CoindeskVo
        CoindeskVo coindeskVo = new CoindeskVo();

        //時間轉換

        //幣別中英轉換
        List<CoindeskVo.Currency> currencyVoList = convertZH(fetchData.getBpi(), currency);
        coindeskVo.setCurrencyList(currencyVoList);

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

    public List<CoindeskVo.Currency> convertZH(Map<String, CoindeskClient.CoindeskResponse.Currency> bpi, List<Currency> currency) {
        List<CoindeskVo.Currency> result = new ArrayList<>();

        Map<String, String> currencyMap = currency.stream().collect(Collectors.toMap(Currency::getCode, Currency::getName));

        bpi.forEach((key, value) -> {
            String zhName = currencyMap.getOrDefault(key, "");
            CoindeskVo.Currency currencyVo = new CoindeskVo.Currency();
            currencyVo.setCode(key);
            currencyVo.setName(zhName);
            currencyVo.setRate(value.getRate());
            result.add(currencyVo);
        });

        return result;
    }

}
