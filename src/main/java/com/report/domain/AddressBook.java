package com.report.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 通讯录
 *
 * @author weiQiang
 */
@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class AddressBook implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String bookId;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空!")
    private String name;

    /**
     * 电话
     */
    @Length(min = 7, message = "电话不能小于7位")
    private String tel;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    @Builder.Default
    @Max(value = 120, message = "年龄不能大于120")
    @Min(value = 1, message = "年龄不能小于1")
    @ColumnDefault(value = "20")
    private Integer age = 20;

    @Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", message = "邮箱不能为空，且注意邮箱格式")
    @NotBlank(message = "邮箱不能为空!")
    private String mail;

    @Length(max = 4000, message = "地址不能多于4000个字符")
    @Column(length = 4000)
    private String address;

    @Length(max = 4000, message = "备注不能多于4000个字符")
    @Column(length = 4000)
    private String remark;

    private String webChat;

    private String qq;

    private String sina;

    private Timestamp createTime;

    @Builder.Default
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Timestamp updateTime = new Timestamp(System.currentTimeMillis());

    @Builder.Default
    @ColumnDefault(value = "0")
    private Integer updateCount = 0;
}
