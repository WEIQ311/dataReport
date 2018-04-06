package com.report.controller;

import com.report.domain.MailConfig;
import com.report.service.EmailService;
import com.report.vo.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 发送邮件
 *
 * @author weiQiang
 */
@Controller
@RequestMapping(value = "/email")
public class EmailController extends BaseController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = {"", "/", "/index", "/list"}, method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, MailConfig mailConfig) {
        ResultEntity resultEntity = emailService.findByMailConfig(mailConfig, Pageable.unpaged());
        request.setAttribute("result", resultEntity);
        return EMAIL_INDEX;
    }

    @RequestMapping(value = "send", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity sendEmail(MailConfig mailConfig) {
        return emailService.sendTemplateMail(mailConfig);
    }
}
