package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/25
 */
@Data
public class QueryCommentReqBody {
    private Long projectId;
    private String commitUser;
    private String realConfirmUser;
    private String assignConfirmUser;
    private String confirmResult;
    private String identifier;
    private Long departmentId;
}
