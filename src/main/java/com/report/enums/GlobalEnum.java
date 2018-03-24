package com.report.enums;

/**
 * @author weiQiang
 */

public enum GlobalEnum {
    ;
    private String message;

    GlobalEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
