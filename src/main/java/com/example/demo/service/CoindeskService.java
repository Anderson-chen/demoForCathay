package com.example.demo.service;


import com.example.demo.client.CoindeskClient;
import com.example.demo.vo.CoindeskVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CoindeskService {

    private final CoindeskClient coindeskClient;

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


    public String convertTime(String updateTime) {


        return null;
    }

}
