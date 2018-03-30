package com.report.controller;

import com.report.service.EmailService;
import com.report.vo.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送邮件
 *
 * @author weiQiang
 */
@RestController
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "send", method = RequestMethod.GET)
    public ResultEntity sendEmail(String sendTo, String title, String content) {
        return emailService.sendSimpleMail(sendTo, title, content);
    }
}
