package com.report.service;

import com.report.vo.ResultEntity;

/**
 * @author weiQiang
 * @date 2018/5/21
 */
public interface SmsService {

    /**
     * 发送验证码
     *
     * @param telephone 手机号码
     * @return ResultEntity
     */
    ResultEntity sendSms(String telephone);

    /**
     * 获取验证码
     *
     * @PARAM TELEPHONE
     * @RETURN
     */
    String getSmsCode(String telephone);
}
