package com.veezean.codereview.server.controller;

import com.veezean.codereview.server.model.BaseResponse;
import com.veezean.codereview.server.model.UserPwdCheckReq;
import com.veezean.codereview.server.model.UserPwdCheckRespBody;
import com.veezean.codereview.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2021/6/4
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/checkUserPwd")
    public BaseResponse<UserPwdCheckRespBody> checkUserPwd(@RequestBody UserPwdCheckReq req) {
        boolean userPwd = userService.authUserPwd(req);
        return BaseResponse.simpleSuccessResponse(new UserPwdCheckRespBody(userPwd));
    }

}
