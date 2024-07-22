package com.veezean.codereview.server.mysql.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 项目信息
 *
 * @author Veezean
 * @since 2021/4/26
 */
@Entity
@Table(name = "t_project", schema = "code_review", catalog = "")
@Data
public class MySqlProjectEntity extends BaseEntity{
    private String projectName;

    @ManyToOne
    private MySqlDepartmentEntity department;

    private String projectDesc;

}
