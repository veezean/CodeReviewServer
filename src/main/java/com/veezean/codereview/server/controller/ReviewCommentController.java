package com.veezean.codereview.server.controller;

import com.veezean.codereview.server.model.*;
import com.veezean.codereview.server.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2021/4/25
 */
@RestController
@RequestMapping("/client/comment")
@Slf4j
@Api("评审意见操作")
public class ReviewCommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/commitComments")
    @ApiOperation("上传评审意见")
    public Response<String> commitComments(@RequestBody CommitComment commitComment) {
        log.info("收到提交评审信息请求：{}", commitComment);
        commentService.saveComments(commitComment);
        return Response.simpleSuccessResponse();
    }

    @PostMapping("/queryList")
    public Response<List<CommentReqBody>> queryList(@RequestBody ReviewQueryParams queryParams) {
        List<CommentReqBody> pageBeanList = commentService.queryList(queryParams);
        return Response.simpleSuccessResponse(pageBeanList);
    }

}
