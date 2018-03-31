package com.report.service;


import com.report.domain.MailConfig;
import com.report.vo.Pair;
import com.report.vo.ResultEntity;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author weiQiang
 */
public interface EmailService {

    /**
     * 发送简单邮件
     *
     * @param sendTo  收件人地址
     * @param title   邮件标题
     * @param content 邮件内容
     * @return
     */
    ResultEntity sendSimpleMail(String sendTo, String title, String content);

    /**
     * 发送简单邮件
     *
     * @param sendTo              收件人地址
     * @param title               邮件标题
     * @param content             邮件内容
     * @param attachments<文件名,附件> 附件列表
     * @return
     */
    ResultEntity sendAttachmentsMail(String sendTo, String title, String content, List<Pair<String, File>> attachments);

    /**
     * 发送模板邮件
     *
     * @param sendTo              收件人地址
     * @param title               邮件标题
     * @param content<key,内容>     邮件内容
     * @param attachments<文件名,附件> 附件列表
     * @return
     */
    ResultEntity sendTemplateMail(String sendTo, String title, Map<String, Object> content, List<Pair<String, File>> attachments);

    /**
     * 发送邮件
     * @param mailConfig
     * @return
     */
    ResultEntity sendTemplateMail(MailConfig mailConfig);
}
