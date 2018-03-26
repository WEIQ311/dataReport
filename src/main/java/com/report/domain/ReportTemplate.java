package com.report.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 报告模板
 *
 * @author weiQiang
 */
@Data
@Entity
@Table(name = "report_template")
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ReportTemplate implements Serializable {
    private static final long serialVersionUID = -4498071306128356021L;

    /**
     * 模板ID
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String templateId;

    /**
     * 模板名称
     */
    @NotBlank(message = "模板名称不能为空!")
    @Length(min = 3, max = 500, message = "模板名称不能小于3个字符并且不能多于500个字符")
    @Column(unique = true)
    private String templateName;

    /**
     * 模板内容
     */
    private String templateContent;

    /**
     * 模板类型
     * <p>
     * 默认html格式
     * 上传时则为相应支持格式
     * 从配置中获取
     */
    @NotBlank(message = "模板类型不能为空!")
    @Builder.Default
    @ColumnDefault(value = "html")
    private String templateType = "html";

    /**
     * 模板形式：upload、online
     * 默认提供online
     */
    @Builder.Default
    @ColumnDefault(value = "online")
    private String templateModel = "online";

    /**
     * 模板路径
     * 当模板为online时则为空
     */
    @Column(length = 1000, columnDefinition = "VARCHAR (1000) COMMENT '模板路径'")
    private String templatePath;
    /**
     * 备注
     */
    @Length(max = 4000, message = "模板备注不能多于4000个字符")
    @Column(length = 4000, columnDefinition = "VARCHAR (4000) COMMENT '备注'")
    private String remark;

    /**
     * 创建者
     * 同一报告只能由创建者更新
     */
    @NotBlank(message = "用户ID不能为空!")
    @Column(length = 1000, columnDefinition = "VARCHAR (1000) COMMENT '用户ID'")
    private String userId;
    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @Builder.Default
    @Column(name = "update_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Timestamp updateTime = new Timestamp(System.nanoTime());

    /**
     * 更新次数
     */
    @Builder.Default
    @ColumnDefault(value = "0")
    private Integer updateCount = 0;
}
