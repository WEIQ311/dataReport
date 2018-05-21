package com.report.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户发送验证码实体类
 *
 * @author weiQiang
 * @date 2018/5/21
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false, of = "codeId")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor
@Builder
public class UserCode implements Serializable {
    private static final long serialVersionUID = 3282410173541193941L;

    /**
     * 主键ID
     */
    @Id
    @GenericGenerator(name = "codeId-uuid", strategy = "uuid")
    @GeneratedValue(generator = "codeId-uuid")
    @Column(updatable = false)
    private String codeId;

    /**
     * 手机号
     */
    @Length(max = 11, min = 11, message = "手机号最长度为11位")
    private String telephone;

    /**
     * 验证码
     */
    private String code;

    /**
     * 插入时间
     */
    private Date insertTime;

    /**
     * 更新时间
     */
    @Column(columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Date updateTime;

    /**
     * 是否过期
     */
    private boolean expire;
}
