package com.veezean.codereview.server.common;

import com.veezean.codereview.server.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller通用的最外层异常捕获与处理逻辑，响应外部统一的错误响应格式
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2020/9/9
 */
@org.springframework.web.bind.annotation.ControllerAdvice
@ResponseBody
@Slf4j
public class ControllerAdvice {

    /**
     * controller层抛出的异常的最终统一处理
     *
     * @param exception 异常对象数据
     * @return 响应信息
     */
    @ExceptionHandler(CodeReviewException.class)
    public Response<String> commonExceptionHandler(CodeReviewException exception) {
        log.error("commonExceptionHandler() IN. ", exception);

        return Response.simpleFailResponse(-1, exception.getMessage());
    }

    /**
     * 所有未捕获异常的最终统一处理
     *
     * @param exception 异常对象数据
     * @return 响应信息
     */
    @ExceptionHandler(Exception.class)
    public Response<String> uncatchedAllExceptionHandler(Exception exception) {
        log.error("uncatchedAllExceptionHandler() IN. ", exception);
        return Response.simpleFailResponse(-1, exception.getMessage());
    }

}
