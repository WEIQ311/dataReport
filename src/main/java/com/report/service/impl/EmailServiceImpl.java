package com.report.service.impl;

import com.report.config.EmailConfig;
import com.report.domain.MailConfig;
import com.report.enums.GlobalEnum;
import com.report.repository.MailConfigRepository;
import com.report.service.EmailService;
import com.report.util.ResultUtil;
import com.report.vo.Pair;
import com.report.vo.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @author weiQiang
 */
@Service(value = "EmailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailConfig emailConfig;

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailConfigRepository mailConfigRepository;

    /**
     * 发送简单邮件
     *
     * @param sendTo  收件人地址
     * @param title   邮件标题
     * @param content 邮件内容
     */
    @Override
    public ResultEntity sendSimpleMail(String sendTo, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailConfig.getEmailFrom());
        message.setTo(sendTo);
        message.setSubject(title);
        message.setText(content);
        javaMailSender.send(message);
        return ResultUtil.success(GlobalEnum.SEND_SUCCESS);
    }

    /**
     * 发送简单邮件
     *
     * @param sendTo      收件人地址
     * @param title       邮件标题
     * @param content     邮件内容
     * @param attachments
     */
    @Override
    public ResultEntity sendAttachmentsMail(String sendTo, String title, String content, List<Pair<String, File>> attachments) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(emailConfig.getEmailFrom());
            helper.setTo(sendTo);
            helper.setSubject(title);
            helper.setText(content);
            for (Pair<String, File> pair : attachments) {
                helper.addAttachment(pair.getLeft(), new FileSystemResource(pair.getRight()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(mimeMessage);
        return ResultUtil.success(GlobalEnum.SEND_SUCCESS);
    }

    /**
     * 发送模板邮件
     *
     * @param sendTo      收件人地址
     * @param title       邮件标题
     * @param content     邮件内容
     * @param attachments 附件列表
     */
    @Override
    public ResultEntity sendTemplateMail(String sendTo, String title, Map<String, Object> content, List<Pair<String, File>> attachments) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(emailConfig.getEmailFrom());
            helper.setTo(sendTo);
            helper.setSubject(title);
            //TODO 将内容与模板替换为相应的html形式
            String text = String.join("\r\n", Files.readAllLines(Paths.get("E:\\IntelliJSpace\\jzpz\\src\\main\\resources\\resources\\index.html")));
            helper.setText(text, true);
            for (Pair<String, File> pair : attachments) {
                helper.addAttachment(pair.getLeft(), new FileSystemResource(pair.getRight()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(mimeMessage);
        return ResultUtil.success(GlobalEnum.SEND_SUCCESS);
    }

    /**
     * 发送邮件
     *
     * @param mailConfig
     * @return
     */
    @Override
    public ResultEntity sendTemplateMail(MailConfig mailConfig) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            //多个收件者
            InternetAddress[] internetAddressTo = InternetAddress.parse(mailConfig.getSetTo());
            mimeMessage.setRecipients(Message.RecipientType.TO, internetAddressTo);
            //多个抄送者
            if (!StringUtils.isEmpty(mailConfig.getCcUsers())) {
                InternetAddress[] internetAddressCc = InternetAddress.parse(mailConfig.getCcUsers());
                mimeMessage.setRecipients(Message.RecipientType.CC, internetAddressCc);
            }
            //多个密送者
            if (!StringUtils.isEmpty(mailConfig.getBccUsers())) {
                InternetAddress[] internetAddressBcc = InternetAddress.parse(mailConfig.getBccUsers());
                mimeMessage.setRecipients(Message.RecipientType.BCC, internetAddressBcc);
            }
            helper.setFrom(emailConfig.getEmailFrom());
            helper.setSubject(mailConfig.getSubject());
            //TODO 将内容与模板替换为相应的html形式
            String text = String.join("\r\n", Files.readAllLines(Paths.get("E:\\IntelliJSpace\\jzpz\\src\\main\\resources\\resources\\index.html")));
            helper.setText(mailConfig.getContent(), mailConfig.isContentHtml());
            for (Pair<String, File> pair : mailConfig.getPairList()) {
                helper.addAttachment(pair.getLeft(), new FileSystemResource(pair.getRight()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(mimeMessage);
        mailConfigRepository.save(mailConfig);
        return ResultUtil.success(GlobalEnum.SEND_SUCCESS);
    }

    /**
     * 通过mailConfig信息查询
     *
     * @param mailConfig
     * @param pageable
     * @return
     */
    @Override
    public ResultEntity findByMailConfig(MailConfig mailConfig, Pageable pageable) {
        Page<MailConfig> mailConfigs = mailConfigRepository.findAll(pageable);
        return ResultUtil.success(GlobalEnum.QUERY_SUCCESS, mailConfigs);
    }
}
