package com.veezean.codereview.server.controller;

import com.veezean.codereview.server.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 供客户端配置时测试网络连接与验证用户名密码使用
 *
 * @author Wang Weiren
 * @since 2021/4/25
 */
@RestController
@RequestMapping("/check")
@Slf4j
public class NetworkConfigCheckController {

    @GetMapping("/serverConnection")
    public Response<String> checkServerConnection() {
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/checkUserAndPwd")
    public Response<String> checkUserAndPwd(String user, String pwd) {
        log.info("user: {} , pwd: {}", user, pwd);
        return Response.simpleSuccessResponse();
    }
}
