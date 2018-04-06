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
    QUERY_ERROR("查询失败!"),
    INSERT_SUCCESS("增加成功!"),
    INSERT_ERROR("增加失败!"),
    DELETE_SUCCESS("删除成功!"),
    DELETE_ERROR("删除失败!"),
    UPDATE_SUCCESS("更新成功!"),
    UPDATE_ERROR("更新失败!"),
    SEND_SUCCESS("发送成功!"),
    SEND_ERROR("发送成功!"),
    ID_ERROR("ID为空!");
    private String message;

    GlobalEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
