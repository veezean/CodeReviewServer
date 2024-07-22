package com.veezean.codereview.server.controller.server;

import com.veezean.codereview.server.model.LoginSuccRespBody;
import com.veezean.codereview.server.model.Response;
import com.veezean.codereview.server.model.UserPwdCheckReq;
import com.veezean.codereview.server.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 登录管理接口
 *
 * @author Veezean
 * @since 2023/3/22
 */
@RestController
@RequestMapping("/server/login")
@Slf4j
public class LoginController {
    @Resource
    private UserService userService;

    @PostMapping("/doLogin")
    @ApiOperation("登录鉴权")
    public Response<LoginSuccRespBody> doLogin(@RequestBody UserPwdCheckReq request) {
        log.info("doLogin IN, UserPwdCheckReq: {}", request);
        LoginSuccRespBody loginSuccRespBody = userService.userLogin(request);
        return Response.simpleSuccessResponse(loginSuccRespBody);
    }

    @GetMapping("/doLogout")
    @ApiOperation("退出登录")
    public Response<String> doLogout() {
        log.info("doLogout IN");
        userService.userLogout();
        return Response.simpleSuccessResponse();
    }
}
