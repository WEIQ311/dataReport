package com.report.vo;

import lombok.*;

import java.io.Serializable;

/**
 * @author weiQiang
 * @date 2018/3/24
 */
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor
@Builder
public class ResultEntity implements Serializable {

    private static final long serialVersionUID = -5159330866402406443L;

    /**
     * 返回成功与否
     */
    private boolean success;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回结果
     */
    private Object data;

    /**
     * 数据条数
     */
    @Builder.Default
    private Integer total = 0;

    /**
     * 耗时
     */
    @Builder.Default
    private Long totalTime = 0L;

}
