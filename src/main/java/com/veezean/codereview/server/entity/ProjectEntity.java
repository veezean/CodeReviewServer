package com.veezean.codereview.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 项目信息
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2021/4/26
 */
@Entity
@Table(name = "t_project", schema = "code_review", catalog = "")
@Data
public class ProjectEntity extends BaseEntity{
    private String projectName;

    @ManyToOne
    private DepartmentEntity department;

    private String projectDesc;

}
