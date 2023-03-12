package com.veezean.codereview.server.controller;

import com.veezean.codereview.server.model.*;
import com.veezean.codereview.server.service.SystemService;
import com.veezean.codereview.server.service.CommentService;
import com.veezean.codereview.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 供客户端配置时测试网络连接与验证用户名密码使用
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2021/4/25
 */
@RestController
@RequestMapping("/client/system")
@Slf4j
@Api("系统配置管理")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/checkConnection")
    @ApiOperation("服务器连接检查")
    public Response<String> checkServerConnection() {
        return Response.simpleSuccessResponse();
    }

    @PostMapping("/checkAuth")
    @ApiOperation("服务端鉴权信息检查")
    public Response<UserPwdCheckRespBody> checkUserAndPwd(@RequestBody UserPwdCheckReq request) {
        log.info("UserPwdCheckReq: {}", request);
        UserPwdCheckRespBody respBody = userService.authUserPwd(request);
        return Response.simpleSuccessResponse(respBody);
    }

    @GetMapping("/pullColumnDefines")
    @ApiOperation("拉取服务端配置的评审字段配置")
    public Response<RecordColumns> pullSystemColumnDefines() {
        RecordColumns definedRecordColumns = systemService.getDefinedRecordColumns();
        return Response.simpleSuccessResponse(definedRecordColumns);
    }

    @PostMapping("/initColumnDefines")
    @ApiOperation("初始化服务端的字段定义（覆盖）")
    public Response<String> initColumnDefines(@RequestBody RecordColumns reqBody) {
        systemService.initColumnDefines(reqBody);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/getSystemNotice")
    @ApiOperation("拉取当前系统的通知信息")
    public Response<List<NoticeBody>> getSystemNotice() {
        List<NoticeBody> myCommentNotices = commentService.getMyCommentNotice();
        List<NoticeBody> systemNotices = systemService.getSystemNotice();

        List<NoticeBody> noticeBodies = new ArrayList<>();
        noticeBodies.addAll(myCommentNotices);
        noticeBodies.addAll(systemNotices);
        return Response.simpleSuccessResponse(noticeBodies);
    }

//    @PostMapping("/getUserShortInfoList")
//    @ApiOperation("拉取用户列表")
//    public Response<List<UserShortInfo>> getUserShortInfoList(@RequestBody ClientUserQueryReqBody reqBody) {
//        List<UserShortInfo> userShortInfoList = userService.getUserShortInfoList(reqBody);
//        return Response.simpleSuccessResponse(userShortInfoList);
//    }
}
