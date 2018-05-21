package com.report.service.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.report.domain.UserCode;
import com.report.enums.GlobalEnum;
import com.report.repository.UserCodeRepository;
import com.report.service.SmsService;
import com.report.util.GloabalUtils;
import com.report.util.ResultUtil;
import com.report.vo.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * 用户验证码ServiceImpl
 *
 * @author weiQiang
 * @date 2018/5/21
 */
@Service(value = "smsService")
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Value("${aliyun.sms.accessKey}")
    private String accessKey;

    @Value("${aliyun.sms.accessKeySecret}")
    private String secertKey;

    @Value("${aliyun.sms.template.code}")
    private String templateCode;

    @Value("${aliyun.sms.sign.name}")
    private String smsSignName;

    @Autowired
    private IAcsClient acsClient;

    @Autowired
    private UserCodeRepository userCodeRepository;

    /**
     * 数字池
     */
    private static final String[] NUMS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    /**
     * 随机变量
     */
    private static final Random random = new Random();

    /**
     * 发送验证码
     *
     * @param telephone 手机号码
     * @return ResultEntity
     */
    @Override
    public ResultEntity sendSms(String telephone) {
        String code = generateRandomSmsCode();
        String templateParam = String.format("{\"code\": \"%s\"}", code);

        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();

        // 使用post提交
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(telephone);
        request.setTemplateParam(templateParam);
        request.setTemplateCode(templateCode);
        request.setSignName(smsSignName);

        boolean success = false;
        try {
            SendSmsResponse response = acsClient.getAcsResponse(request);
            log.info("发送验证码结果:{}", response.getMessage());
            if (Objects.equals(GloabalUtils.OK, response.getCode())) {
                success = true;
            }
        } catch (ClientException e) {
            log.error("发送验证码发生错误:{}", e.getMessage());
        }
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        if (success) {
            userCodeRepository.save(UserCode.builder()
                    .codeId("codeId-" + UUID.randomUUID().toString())
                    .telephone(telephone)
                    .code(code)
                    .insertTime(nowTime)
                    .updateTime(nowTime)
                    .expire(false)
                    .build());
            return ResultUtil.success(GlobalEnum.SEND_SUCCESS);
        } else {
            return ResultUtil.error(GlobalEnum.SERVER_USED);
        }
    }

    /**
     * 获取验证码
     *
     * @param telephone
     * @return
     */
    @Override
    public String getSmsCode(String telephone) {
        UserCode userCode = userCodeRepository.findByTelephone(telephone);
        if (null != userCode) {
            return userCode.getCode();
        } else {
            return null;
        }
    }

    /**
     * 6位验证码生成器
     *
     * @return
     */
    private static String generateRandomSmsCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(10);
            sb.append(NUMS[index]);
        }
        return sb.toString();
    }
}
