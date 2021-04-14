package com.report.controller;

import com.report.enums.GlobalEnum;
import com.report.service.SmsService;
import com.report.util.LoginUserUtil;
import com.report.util.ResultUtil;
import com.report.vo.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiQiang
 * @date 2018/5/21
 */
@RestController
@RequestMapping(value = "sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    /**
     * 发送验证码
     *
     * @param telephone 手机号码
     * @return ResultEntity
     */
    @GetMapping(value = "code")
    public ResultEntity smsCode(String telephone) {
        if (!LoginUserUtil.checkTelephone(telephone)) {
            return ResultUtil.error(GlobalEnum.BAD_TEL);
        }
        return smsService.sendSms(telephone);
    }
}
