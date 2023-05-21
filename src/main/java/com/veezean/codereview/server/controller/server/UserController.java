package com.veezean.codereview.server.controller.server;

import com.veezean.codereview.server.model.*;
import com.veezean.codereview.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/23
 */
@RestController
@RequestMapping("/server/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public Response<String> createUser(@RequestBody SaveUserReqBody reqBody) {
        userService.createUser(reqBody);
        return Response.simpleSuccessResponse();
    }

    @PostMapping("/modifyUser")
    public Response<String> modifyUser(@RequestBody EditUserReqBody reqBody) {
        userService.modifyUser(reqBody);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/deleteUser")
    public Response<String> deleteUser(@RequestParam String account) {
        userService.deleteUser(account);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/deleteUsers")
    public Response<String> deleteUser(@RequestParam List<String> accounts) {
        userService.deleteUsers(accounts);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/getUserDetail")
    public Response<UserDetail> getUserDetail(@RequestParam String account) {
        UserDetail userDetail = userService.getUserDetail(account);
        return Response.simpleSuccessResponse(userDetail);
    }

    @PostMapping("/getUserDetails")
    public Response<PageBeanList<UserDetail>> getUserDetails(@RequestBody PageQueryRequest<UserQueryBody> request) {
        PageBeanList<UserDetail> userDetails = userService.getUserDetails(request);
        return Response.simpleSuccessResponse(userDetails);
    }

    @PostMapping("/changePwd")
    public Response<String> changePwd(@RequestBody ChangePwdReqBody reqBody) {
        userService.modifyPassword(reqBody);
        return Response.simpleSuccessResponse();
    }
}
