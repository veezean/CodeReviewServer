package com.veezean.codereview.server.controller;

import com.veezean.codereview.server.model.ProjectBaseInfo;
import com.veezean.codereview.server.model.Response;
import com.veezean.codereview.server.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2021/6/5
 */
@RestController
@RequestMapping("/client/project")
@Slf4j
@Api("项目管理接口")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/getMyProjects")
    @ApiOperation("拉取我可以查看到的所有项目列表信息")
    public Response<List<ProjectBaseInfo>> getMyProjects() {
        List<ProjectBaseInfo> projectBaseInfos = projectService.getMyProjects();
        return Response.simpleSuccessResponse(projectBaseInfos);
    }

}
