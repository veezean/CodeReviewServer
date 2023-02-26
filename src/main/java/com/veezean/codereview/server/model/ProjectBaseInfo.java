package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/2/25
 */
@Data
public class ProjectBaseInfo {
    private Long projectId;
    private String projectName;
    private String projectDesc;
}
