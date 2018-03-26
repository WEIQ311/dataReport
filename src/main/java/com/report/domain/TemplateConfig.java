package com.report.domain;

import com.report.enums.ExportEnum;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author weiQiang
 */
@Entity
@Data
@Table(name = "template_config")
@EqualsAndHashCode(callSuper = false, of = "detailId")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor
@Builder
public class TemplateConfig implements Serializable {

    private static final long serialVersionUID = 2209414213996357671L;

    /**
     * 模板详情ID
     */
    @Id
    @GenericGenerator(name = "detail-uuid", strategy = "uuid")
    @GeneratedValue(generator = "detail-uuid")
    @Column(updatable = false)
    private String detailId;

    @NotEmpty(message = "模板中的关键字不能为空!")
    @Max(value = 500, message = "关键字最大长度不能超过500")
    private String keyWord;

    @NotEmpty(message = "模板中关键字名称不能为空!")
    @Max(value = 500, message = "关键字名称最大长度不能超过500")
    private String keyName;

    /**
     * CONTENT对应类型:0:sql，1:存储过程
     */
    @Max(value = 4000, message = "报告SQL最大长度不能超过500")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR (255) default 'SQL'")
    private ExportEnum sqlType;
    /**
     * 类型:1:普通文本,2:循环文本,3:表格,4:图片,5:折线图,6:柱状图,7:饼状图；如果是5,6,7则需要对返回哪些列名有约束
     */
    @Enumerated(EnumType.STRING)
    private ExportEnum type;

    /**
     * 导出格式：0：无效，导出格式:1：word,2:excel,3:word+excel
     */
    @Enumerated(EnumType.STRING)
    private ExportEnum exportType;

    /**
     * 如果类型是循环文本，拼接的字符串是什么，默认中文逗号
     */
    private String splitStr;

    /**
     * 模板Id
     */
    @NotEmpty(message = "模板名称不能为空!")
    @Column(columnDefinition = "VARCHAR (500) COMMENT '模板ID'")
    private ReportTemplate templateId;

    /**
     * 报告排序 序号
     */
    @Column(columnDefinition = "tinyint default 1")
    private Integer ord;

    /**
     * 备注
     */
    @Max(value = 4000, message = "备注最大长度不能超过500")
    @Column(length = 4000, columnDefinition = "VARCHAR (4000) COMMENT '备注'")
    private String remark;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 创建者ID
     */
    @NotEmpty(message = "用户ID不能为空!")
    @Column(length = 1000, columnDefinition = "VARCHAR (1000) COMMENT '用户ID'")
    private String createId;

    /**
     * 更新时间
     */
    @Column(name = "update_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Date updateTime;
    /**
     * 更新次数
     */
    @Builder.Default
    @Column(name = "update_count", columnDefinition = "tinyint default 0")
    private Integer updateCount = 0;
}

