package com.veezean.codereview.server.controller;

import com.veezean.codereview.server.entity.ColumnDefineEntity;
import com.veezean.codereview.server.model.*;
import com.veezean.codereview.server.service.ColumnDefineService;
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

    @PostMapping("/mockNotice")
    @ApiOperation("通知服务模拟接口")
    public Response<String> mockNotice(@RequestBody String request) {
        log.info("Notice Request:{}", request);
        return Response.simpleSuccessResponse();
    }
}
