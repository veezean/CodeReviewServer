package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * 通用response响应类
 *
 * @author Veezean
 * @since 2021/4/25
 */
@Data
public class Response<T>{

    private int code;
    private String message;
    private T data;

    /**
     * 构造简单的成功响应对象
     *
     * @param message 响应描述信息
     * @param <T>     响应数据类型
     * @return 响应结果
     */
    public static <T> Response<T> simpleSuccessResponse(String message) {
        return buildResponse(0, message, null);
    }

    /**
     * 构造简单的成功响应对象
     *
     * @param message 响应描述信息
     * @param data    响应数据
     * @return 响应结果
     */
    public static Response<String> simpleSuccessResponse(String message, String data) {
        return buildResponse(0, message, data);
    }


    /**
     * 构造简单的成功响应对象
     *
     * @param <T> 响应数据类型
     * @return 响应结果
     */
    public static <T> Response<T> simpleSuccessResponse() {
        return simpleSuccessResponse("操作成功");
    }

    /**
     * 构造简单的成功响应对象
     *
     * @param data 响应数据
     * @param <T>  响应数据类型
     * @return 响应结果
     */
    public static <T> Response<T> simpleSuccessResponse(T data) {
        Response<T> response = simpleSuccessResponse();
        response.setData(data);
        return response;
    }

    /**
     * 构造简单的失败响应对象
     *
     * @param <T> 响应数据类型
     * @return 响应结果
     */
    public static <T> Response<T> simpleFailResponse() {
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
    public static <T> Response<T> simpleFailResponse(int errCode, String message) {
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
    public static <T> Response<T> simpleFailResponse(int errCode, String message, T data) {
        return buildResponse(errCode, message, data);
    }

    private static <T> Response<T> buildResponse(int code, String message, T data) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

}
