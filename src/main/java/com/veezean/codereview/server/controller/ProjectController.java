package com.veezean.codereview.server.controller;

import com.veezean.codereview.server.model.BaseResponse;
import com.veezean.codereview.server.model.ProjectBaseInfo;
import com.veezean.codereview.server.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2021/6/5
 */
@RestController
@RequestMapping("/project")
@Slf4j
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/queryUserAccessableProjects/{account}")
    public BaseResponse<List<ProjectBaseInfo>> queryUserAccessableProjects(@PathVariable String account) {
        List<ProjectBaseInfo> projectBaseInfos = projectService.queryUserAccessableProjects(account);
        return BaseResponse.simpleSuccessResponse(projectBaseInfos);
    }

}
