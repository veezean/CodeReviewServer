package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/25
 */
@Data
public class QueryStatReqBody {
    private Long projectId;
    private String commitUser;
    private String realConfirmUser;
    private String assignConfirmUser;
    private String confirmResult;
    private Long departmentId;
}
