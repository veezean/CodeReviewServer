package com.veezean.codereview.server.controller;

import com.veezean.codereview.server.model.CommitComment;
import com.veezean.codereview.server.model.CommitResult;
import com.veezean.codereview.server.model.Response;
import com.veezean.codereview.server.model.ReviewQueryParams;
import com.veezean.codereview.server.service.MongoDbReviewCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2021/4/25
 */
@RestController
@RequestMapping("/client/comment")
@Slf4j
@Api("评审意见操作")
public class ReviewCommentController {

    @Autowired
    private MongoDbReviewCommentService mongoDbReviewCommentService;

    @PostMapping("/commitComments")
    @ApiOperation("上传评审意见")
    public Response<CommitResult> commitComments(@RequestBody CommitComment commitComment) {
        log.info("收到提交评审信息请求：{}", commitComment);
        CommitResult commitResult = mongoDbReviewCommentService.clientCommit(commitComment);
        return Response.simpleSuccessResponse(commitResult);
    }

    @PostMapping("/queryList")
    public Response<CommitComment> queryList(@RequestBody ReviewQueryParams queryParams) {
        CommitComment commitComment = mongoDbReviewCommentService.clientQueryComments(queryParams);
        return Response.simpleSuccessResponse(commitComment);
    }

}
