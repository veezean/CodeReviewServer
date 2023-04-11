package com.veezean.codereview.server.model;

import com.veezean.codereview.server.entity.DepartmentEntity;
import lombok.Data;

import javax.persistence.ManyToOne;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/24
 */
@Data
public class SaveProjectReqBody {
    private String projectName;
    private String projectDesc;
    private long departmentId;
}
