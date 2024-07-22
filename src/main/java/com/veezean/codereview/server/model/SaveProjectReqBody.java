package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/24
 */
@Data
public class SaveProjectReqBody {
    private String projectName;
    private String projectDesc;
    private long departmentId;
}
