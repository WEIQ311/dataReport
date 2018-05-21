package com.report.service;

import com.report.vo.ResultEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author weiQiang
 * @date 2018/5/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsServiceTest {

    @Autowired
    private SmsService smsService;

    @Test
    public void sendSms() {
        ResultEntity resultEntity = smsService.sendSms("17600116525");
        System.out.println(resultEntity);
    }

    @Test
    public void getSmsCode() {
        String smsCode = smsService.getSmsCode("17600116525");
        System.out.println(smsCode);
    }
}