package com.veezean.codereview.server.controller;

import com.veezean.codereview.server.entity.CommentEntity;
import com.veezean.codereview.server.model.*;
import com.veezean.codereview.server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2021/4/25
 */
@RestController
@RequestMapping("/review_comment")
public class ReviewCommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/queryList")
    public BaseResponse<PageBeanList<CommentEntity>> queryList(@RequestBody PageQueryRequest<ReviewQueryParams> queryParams) {
        PageBeanList<CommentEntity> pageBeanList = commentService.queryList(queryParams);
        return BaseResponse.simpleSuccessResponse(pageBeanList);
    }

}
