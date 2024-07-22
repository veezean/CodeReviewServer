package com.veezean.codereview.server.model;

import com.veezean.codereview.server.entity.ReviewCommentEntity;
import lombok.Data;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2021/4/26
 */
@Data
public class CommitComment {
    private List<ReviewCommentEntity> comments;
}
