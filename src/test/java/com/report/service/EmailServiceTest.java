package com.report.service;

import com.report.vo.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendSimpleMail() {
        String sendTo = "1107438082@QQ.COM";
        String title = "1107438082";
        String content = "1107438082测试邮件内容";
        Map<String, Object> contentMap = new HashMap<>();
        List<Pair<String, File>> attachments = new ArrayList<>();
        attachments.add(Pair.create("index.html", new File("E:\\IntelliJSpace\\jzpz\\src\\main\\resources\\resources\\index.html")));
        emailService.sendSimpleMail(sendTo, title, content);
        emailService.sendAttachmentsMail(sendTo, title, content, attachments);
        emailService.sendTemplateMail(sendTo, title, contentMap, attachments);
    }

    @Test
    public void sendAttachmentsMail() {
    }

    @Test
    public void sendTemplateMail() {
    }
}