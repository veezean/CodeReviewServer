package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * 通用response响应类
 *
 * @author Wang Weiren
 * @since 2021/4/25
 */
@Data
public class BaseResponse<T>{

    private int status;
    private String error;
    private T data;

    /**
     * 构造简单的成功响应对象
     *
     * @param message 响应描述信息
     * @param <T>     响应数据类型
     * @return 响应结果
     */
    public static <T> BaseResponse<T> simpleSuccessResponse(String message) {
        return buildResponse(0, message, null);
    }

    /**
     * 构造简单的成功响应对象
     *
     * @param message 响应描述信息
     * @param data    响应数据
     * @return 响应结果
     */
    public static BaseResponse<String> simpleSuccessResponse(String message, String data) {
        return buildResponse(0, message, data);
    }


    /**
     * 构造简单的成功响应对象
     *
     * @param <T> 响应数据类型
     * @return 响应结果
     */
    public static <T> BaseResponse<T> simpleSuccessResponse() {
        return simpleSuccessResponse("");
    }

    /**
     * 构造简单的成功响应对象
     *
     * @param data 响应数据
     * @param <T>  响应数据类型
     * @return 响应结果
     */
    public static <T> BaseResponse<T> simpleSuccessResponse(T data) {
        BaseResponse<T> response = simpleSuccessResponse();
        response.setData(data);
        return response;
    }

    /**
     * 构造简单的失败响应对象
     *
     * @param <T> 响应数据类型
     * @return 响应结果
     */
    public static <T> BaseResponse<T> simpleFailResponse() {
        return buildResponse(-1, "操作失败", null);
    }

    /**
     * 构造简单的失败响应对象
     *
     * @param errCode 错误码
     * @param message 错误描述
     * @param <T>     响应数据类型
     * @return 响应结果
     */
    public static <T> BaseResponse<T> simpleFailResponse(int errCode, String message) {
        return buildResponse(errCode, message, null);
    }

    /**
     * 构造简单的失败响应对象
     *
     * @param errCode 错误码
     * @param message 错误描述
     * @param data    响应数据
     * @param <T>     响应数据类型
     * @return 响应结果
     */
    public static <T> BaseResponse<T> simpleFailResponse(int errCode, String message, T data) {
        return buildResponse(errCode, message, data);
    }

    private static <T> BaseResponse<T> buildResponse(int code, String message, T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatus(code);
        response.setError(message);
        response.setData(data);
        return response;
    }

}
