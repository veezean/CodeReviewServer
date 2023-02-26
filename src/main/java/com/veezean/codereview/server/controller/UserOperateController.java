package com.veezean.codereview.server.controller;

import com.veezean.codereview.server.model.Comment;
import com.veezean.codereview.server.model.CommitComment;
import com.veezean.codereview.server.model.Response;
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

    @PostMapping("/commitComments")
    public Response<String> commitComments(@RequestBody CommitComment commitComment) {
        log.info("收到提交评审信息请求：{}", commitComment);
        commentService.saveOrUpdateComment(commitComment);
        return Response.simpleSuccessResponse();
    }
}
