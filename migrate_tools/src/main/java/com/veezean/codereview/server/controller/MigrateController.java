package com.veezean.codereview.server.controller;

import com.veezean.codereview.server.model.Response;
import com.veezean.codereview.server.service.MigrateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 将MySQL历史数据迁移到MongoDB中的服务
 *
 * @author Veezean
 * @since 2024/7/22
 */
@RestController
@RequestMapping("/migrate")
@Slf4j
@Api("将MySQL历史数据迁移到MongoDB中的服务")
public class MigrateController {
    @Resource
    private MigrateService migrateService;

    @GetMapping("/execute")
    @ApiOperation("服务器连接检查")
    public Response<String> execute() {
        migrateService.execute();
        return Response.simpleSuccessResponse();
    }

}
