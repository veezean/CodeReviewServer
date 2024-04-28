package com.veezean.codereview.server.model;

import lombok.Data;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2024/4/26
 */
@Data
public class UserProjectBindReqBody {
    private List<String> accounts;
    private long projectId;
}
