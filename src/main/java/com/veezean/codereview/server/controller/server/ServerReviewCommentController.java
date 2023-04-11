package com.veezean.codereview.server.controller.server;

import com.veezean.codereview.server.entity.ReviewCommentEntity;
import com.veezean.codereview.server.model.*;
import com.veezean.codereview.server.repository.ReviewCommentRepository;
import com.veezean.codereview.server.service.ReviewCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/25
 */
@RestController
@RequestMapping("/server/comment")
public class ServerReviewCommentController {

    @Autowired
    private ReviewCommentService reviewCommentService;

    @PostMapping("/createOrModifyComment")
    public Response<String> createOrModifyComment(@RequestBody SaveReviewCommentReqBody reqBody) {
        reviewCommentService.createOrModifyReviewComment(reqBody);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/deleteComment")
    public Response<String> deleteComment(@RequestParam long commentId) {
        reviewCommentService.deleteComment(commentId);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/deleteComments")
    public Response<String> deleteComments(@RequestParam List<Long> commentIds) {
        reviewCommentService.deleteBatch(commentIds);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/queryComment")
    public Response<ReviewCommentEntity> queryComment(@RequestParam long commentId) {
        ReviewCommentEntity reviewCommentEntity = reviewCommentService.queryCommentDetail(commentId);
        return Response.simpleSuccessResponse(reviewCommentEntity);
    }

    @PostMapping("/queryComments")
    public Response<PageBeanList<ReviewCommentEntity>> queryComments(@RequestBody PageQueryRequest<QueryCommentReqBody> request) {
        PageBeanList<ReviewCommentEntity> pageBeanList =
                reviewCommentService.queryCommentDetails(request);
        return Response.simpleSuccessResponse(pageBeanList);
    }
}
