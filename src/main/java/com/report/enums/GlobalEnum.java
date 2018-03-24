package com.report.enums;

/**
 * @author weiQiang
 */

public enum GlobalEnum {
    QUERY_SUCCESS("查询成功!");
    private String message;

    GlobalEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
