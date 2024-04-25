package com.veezean.codereview.server.controller;

import com.veezean.codereview.server.entity.ColumnDefineEntity;
import com.veezean.codereview.server.model.*;
import com.veezean.codereview.server.service.ColumnDefineService;
import com.veezean.codereview.server.service.SystemService;
import com.veezean.codereview.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 供客户端配置时测试网络连接与验证用户名密码使用
 *
 * @author Veezean
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
    private ColumnDefineService columnDefineService;

    @Autowired
    private UserService userService;

    @GetMapping("/checkConnection")
    @ApiOperation("服务器连接检查")
    public Response<String> checkServerConnection() {
        return Response.simpleSuccessResponse();
    }

    @PostMapping("/checkAuth")
    @ApiOperation("服务端鉴权信息检查")
    public Response<UserPwdCheckRespBody> checkUserAndPwd(@RequestBody UserPwdCheckReq request) {
        log.info("Client auth in... account:{}", request.getAccount());
        UserPwdCheckRespBody respBody = userService.authUserPwd(request);
        log.info("Client account:{}, auth result:{}", request.getAccount(), respBody.isPass());
        return Response.simpleSuccessResponse(respBody);
    }

    @GetMapping("/pullColumnDefines")
    @ApiOperation("拉取服务端配置的评审字段配置")
    public Response<RecordColumns> pullSystemColumnDefines() {
        List<ColumnDefineEntity> defineEntities = columnDefineService.queryColumns().collect(Collectors.toList());
        RecordColumns definedRecordColumns = new RecordColumns();
        definedRecordColumns.setColumns(defineEntities);
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
        List<NoticeBody> noticeBodies = new ArrayList<>();
        return Response.simpleSuccessResponse(noticeBodies);
    }

    @PostMapping("/mockNotice")
    @ApiOperation("通知服务模拟接口")
    public Response<String> mockNotice(@RequestBody String request) {
        log.info("Notice Request:{}", request);
        return Response.simpleSuccessResponse();
    }
}
