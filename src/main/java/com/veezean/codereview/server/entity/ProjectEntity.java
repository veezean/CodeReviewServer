package com.veezean.codereview.server.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Transient;
import java.util.List;

/**
 * 项目信息
 *
 * @author Veezean
 * @since 2021/4/26
 */
@Data
@Document(collection = "t_project")
public class ProjectEntity extends MongoLongIdEntity {
    private String projectName;
    @Indexed
    private long departmentId;
    private String projectDesc;
    @Indexed
    private List<String> memberAccounts;

    /**
     * 详细部门信息
     */
    @Transient
    private DepartmentEntity department;

}
