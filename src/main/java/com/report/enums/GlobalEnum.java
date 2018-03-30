package com.report.enums;

/**
 * 全局枚举信息
 *
 * @author weiQiang
 */

public enum GlobalEnum {
    /**
     * 全局状态信息
     */
    QUERY_SUCCESS("查询成功!"),
    SEND_SUCCESS("发送成功!"),
    SEND_ERROR("发送成功!");
    private String message;

    GlobalEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
