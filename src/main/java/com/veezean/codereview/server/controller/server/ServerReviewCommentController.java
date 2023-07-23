package com.veezean.codereview.server.controller.server;

import com.veezean.codereview.server.model.*;
import com.veezean.codereview.server.monogo.ReviewCommentEntity;
import com.veezean.codereview.server.service.MongoDbReviewCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/25
 */
@RestController
@RequestMapping("/server/comment")
public class ServerReviewCommentController {

    @Autowired
    private MongoDbReviewCommentService mongoDbReviewCommentService;

    @GetMapping("/initCreateReqBody")
    public Response<SaveReviewCommentReqBody> initCreateReqBody() {
        SaveReviewCommentReqBody reqBody = mongoDbReviewCommentService.initCreateReqBody();
        return Response.simpleSuccessResponse(reqBody);
    }

    @GetMapping("/initEditReqBody")
    public Response<SaveReviewCommentReqBody> initEditReqBody(String identifier) {
        SaveReviewCommentReqBody reqBody = mongoDbReviewCommentService.initEditReqBody(identifier);
        return Response.simpleSuccessResponse(reqBody);
    }
    @GetMapping("/initConfirmReqBody")
    public Response<SaveReviewCommentReqBody> initConfirmReqBody(String identifier) {
        SaveReviewCommentReqBody reqBody = mongoDbReviewCommentService.initConfirmReqBody(identifier);
        return Response.simpleSuccessResponse(reqBody);
    }

    @GetMapping("/initViewReqBody")
    public Response<SaveReviewCommentReqBody> initViewReqBody(String identifier) {
        SaveReviewCommentReqBody reqBody = mongoDbReviewCommentService.initViewReqBody(identifier);
        return Response.simpleSuccessResponse(reqBody);
    }

    @PostMapping("/createComment")
    public Response<String> createComment(@RequestBody SaveReviewCommentReqBody reqBody) {
        mongoDbReviewCommentService.createComment(reqBody);
        return Response.simpleSuccessResponse();
    }

    @PostMapping("/modifyComment")
    public Response<String> modifyComment(@RequestBody SaveReviewCommentReqBody reqBody) {
        mongoDbReviewCommentService.modifyComment(reqBody);
        return Response.simpleSuccessResponse();
    }

    @PostMapping("/confirmComment")
    public Response<String> confirmComment(@RequestBody SaveReviewCommentReqBody reqBody) {
        mongoDbReviewCommentService.confirmComment(reqBody);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/deleteComment")
    public Response<String> deleteComment(@RequestParam String identifier) {
        mongoDbReviewCommentService.deleteComment(identifier);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/deleteComments")
    public Response<String> deleteComments(@RequestParam List<String> identifiers) {
        mongoDbReviewCommentService.deleteBatch(identifiers);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/queryComment")
    public Response<ReviewCommentEntity> queryComment(@RequestParam String identifier) {
        com.veezean.codereview.server.monogo.ReviewCommentEntity reviewCommentEntity = mongoDbReviewCommentService.queryCommentDetail(identifier);
        return Response.simpleSuccessResponse(reviewCommentEntity);
    }

    @PostMapping("/queryComments")
    public Response<PageBeanList<Map<String, String>>> queryComments(@RequestBody PageQueryRequest<QueryCommentReqBody> request) {
        PageBeanList<Map<String, String>> pageBeanList =
                mongoDbReviewCommentService.queryCommentDetails(request);
        return Response.simpleSuccessResponse(pageBeanList);
    }
}
