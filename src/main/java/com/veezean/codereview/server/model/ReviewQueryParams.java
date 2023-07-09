package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2021/6/5
 */
@Data
public class ReviewQueryParams {
    private Long projectId;
    /**
     * 全部、我提交的、待我确认
     */
    private String type = "全部";
}
