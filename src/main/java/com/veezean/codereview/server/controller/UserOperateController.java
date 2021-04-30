package com.veezean.codereview.server.controller;

import com.veezean.codereview.server.entity.CommentEntity;
import com.veezean.codereview.server.entity.ProjectEntity;
import com.veezean.codereview.server.model.Comment;
import com.veezean.codereview.server.model.CommitComment;
import com.veezean.codereview.server.model.Response;
import com.veezean.codereview.server.repository.CommentRepository;
import com.veezean.codereview.server.service.CommentService;
import com.veezean.codereview.server.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户维度的操作接口
 *
 * @author Wang Weiren
 * @since 2021/4/26
 */
@RestController
@RequestMapping("/user_operate")
@Slf4j
public class UserOperateController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/queryUserBindedProjects")
    public Response<List<ProjectEntity>> queryUserBindedProjects(@RequestParam("userId") String userId) {
        List<ProjectEntity> projectEntities = projectService.queryUserBindedProjects(userId);
        return Response.simpleSuccessResponse(projectEntities);
    }

    @GetMapping("/queryProjectComments")
    public Response<List<Comment>> queryProjectComments(@RequestParam("projectKey") String projectKey, @RequestParam(
            "filterType")int filterType, @RequestParam("currentUser") String currentUser) {
        log.info("收到查询评审信息请求：{}", projectKey);
        List<Comment> commentEntities = commentService.queryCommentsByProject(projectKey, filterType, currentUser);
        return Response.simpleSuccessResponse(commentEntities);
    }

    @PostMapping("/commitComments")
    public Response<String> commitComments(@RequestBody CommitComment commitComment) {
        log.info("收到提交评审信息请求：{}", commitComment);
        commentService.saveOrUpdateComment(commitComment);
        return Response.simpleSuccessResponse();
    }
}
