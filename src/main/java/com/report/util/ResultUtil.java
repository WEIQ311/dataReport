package com.report.util;

import com.report.enums.GlobalEnum;
import com.report.vo.ResultEntity;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 返回结果封装工具类
 *
 * @author weiQiang
 */
public class ResultUtil {

    /**
     * 成功方法
     *
     * @param globalEnum
     * @return
     */
    public static ResultEntity success(GlobalEnum globalEnum) {
        return success(globalEnum.getMessage(), null);
    }

    /**
     * 成功方法
     *
     * @param globalEnum
     * @param data
     * @return
     */
    public static ResultEntity success(GlobalEnum globalEnum, Object data) {
        return success(globalEnum.getMessage(), data);
    }

    /**
     * 成功方法
     *
     * @param message
     * @param data
     * @return
     */
    public static ResultEntity success(String message, Object data) {
        return ResultEntity.builder().success(true).message(message).data(data).build();
    }

    /**
     * 分页返回
     *
     * @param querySuccess
     * @param page
     * @return
     */
    public static ResultEntity success(GlobalEnum querySuccess, Page page) {
        return success(querySuccess, page.getContent(), page.getContent().size());
    }

    /**
     * 成功返回分页数据和总页数
     *
     * @param querySuccess
     * @param resultList
     * @param totalPages
     * @return
     */
    public static ResultEntity success(GlobalEnum querySuccess, List resultList, int totalPages) {
        return ResultEntity.builder().message(querySuccess.getMessage()).success(true).data(resultList).total(totalPages).build();
    }

    /**
     * 失败方法
     *
     * @param globalEnum
     * @return
     */
    public static ResultEntity error(GlobalEnum globalEnum) {
        return error(globalEnum.getMessage());
    }

    /**
     * 失败方法
     *
     * @param message
     * @return
     */
    public static ResultEntity error(String message) {
        return error(message, null);
    }

    /**
     * 失败方法
     *
     * @param message
     * @param data
     * @return
     */
    public static ResultEntity error(String message, Object data) {
        return ResultEntity.builder().success(false).message(message).data(data).build();
    }

}
