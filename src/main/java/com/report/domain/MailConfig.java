package com.report.domain;

import com.report.vo.Pair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 邮件配置
 *
 * @author weiQiang
 */
@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MailConfig implements Serializable {
    private static final long serialVersionUID = -7626274791582708601L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    public String mailId;

    /**
     * 收件人可能有多个
     */
    @NotBlank(message = "收件人不能为空!")
    private String setTo;

    /**
     * 抄送者
     */
    private String ccUsers;

    /**
     * 密送者
     */
    private String bccUsers;

    /**
     * 主题
     */
    @NotBlank(message = "邮件主题不能为空!")
    private String subject;

    /**
     * 邮件内容
     */
    @NotBlank(message = "邮件内容不能为空!")
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 邮件内容是否为HTML
     */
    @Builder.Default
    private boolean contentHtml = false;

    /**
     * 附件
     */
    private String attachments;

    /**
     * 用于适用模板替换html页面参数
     */
    @Transient
    private Map<String, Object> contentMap;

    /**
     * 模板集合
     * <p>
     * 适用于:使用模板发送邮件
     * </p>
     */
    private String templateIds;

    /**
     * 多附件
     * <p>
     * 数据来源:detailIds值
     * </p>
     */
    @Transient
    private List<Pair<String, File>> pairList;

    /**
     * 报告集合
     * <p>
     * 适用于:使用发送多个报告
     * </p>
     */
    private String detailIds;

    /**
     * 发送时间
     */
    @Builder.Default
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Timestamp createTime = new Timestamp(System.currentTimeMillis());
}
