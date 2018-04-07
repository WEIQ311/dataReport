package com.report.controller;

import com.report.domain.MailConfig;
import com.report.service.EmailService;
import com.report.vo.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

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

    /**
     * 邮件首页
     *
     * @param request
     * @param response
     * @param mailConfig
     * @return
     */
    @RequestMapping(value = {"", "/", "/index", "/list"}, method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, MailConfig mailConfig) {
        ResultEntity resultEntity = emailService.findByMailConfig(mailConfig, Pageable.unpaged());
        request.setAttribute("result", resultEntity);
        request.setAttribute("mailConfig", mailConfig);
        return EMAIL_INDEX;
    }

    /**
     * 发送邮件
     *
     * @param mailConfig
     * @return
     */
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public String sendEmail(HttpServletRequest request, @Valid MailConfig mailConfig, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            request.setAttribute("message", bindingResult.getFieldError().getDefaultMessage());
            request.setAttribute("status", 200);
            request.setAttribute("mailConfig", mailConfig);
            request.setAttribute("showDetail", false);
            request.setAttribute("title", "发送邮件");
            return EMAIL_ADD_OR_DETAIL;
        }
        ResultEntity resultEntity = emailService.sendTemplateMail(mailConfig);
        return "redirect:".concat(EMAIL_INDEX);
    }

    /**
     * 添加邮件
     *
     * @return
     */
    @RequestMapping(value = "addEmail")
    public String addEmail(HttpServletRequest request, MailConfig mailConfig) {
        request.setAttribute("mailConfig", mailConfig);
        request.setAttribute("showDetail", false);
        request.setAttribute("title", "发送邮件");
        return EMAIL_ADD_OR_DETAIL;
    }

    /**
     * 邮件详情
     *
     * @param mailId
     * @return
     */
    @RequestMapping(value = "detail")
    public String emailDetail(HttpServletRequest request, HttpServletResponse response, String mailId) {
        MailConfig mailConfig = new MailConfig();
        List<MailConfig> mailConfigs = emailService.queryMailConfig(MailConfig.builder().mailId(mailId).build());
        if (null != mailConfigs && mailConfigs.size() > 0) {
            mailConfig = mailConfigs.get(0);
            request.setAttribute("status", 200);
        } else {
            request.setAttribute("message", "未查询到邮件信息");
            request.setAttribute("status", 404);
        }
        request.setAttribute("mailConfig", mailConfig);
        request.setAttribute("showDetail", true);
        request.setAttribute("title", mailConfig.getSubject().concat("邮件详情"));
        return EMAIL_ADD_OR_DETAIL;
    }

}
