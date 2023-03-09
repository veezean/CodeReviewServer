package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2021/6/5
 */
@Data
public class ReviewQueryParams {
    private Long projectId;
    /**
     * 全部、我提交的、我确认的
     */
    private String type = "全部";
}
