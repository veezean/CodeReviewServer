package com.veezean.codereview.server.model;

import lombok.Data;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/25
 */
@Data
public class SaveReviewCommentReqBody {
    private List<CommentFieldModel> fieldModelList;
    private String identifier;
    private long projectId;
    private long dataVersion;
}
