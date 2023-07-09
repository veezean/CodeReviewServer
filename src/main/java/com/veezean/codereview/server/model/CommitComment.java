package com.veezean.codereview.server.model;

import com.veezean.codereview.server.monogo.ReviewCommentEntity;
import lombok.Data;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2021/4/26
 */
@Data
public class CommitComment {
    private List<ReviewCommentEntity> comments;
}
