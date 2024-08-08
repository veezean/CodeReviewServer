package com.veezean.codereview.server.mysql.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/22
 */
@Data
@Entity
@Table(name = "t_department", schema = "code_review", catalog = "")
public class MySqlDepartmentEntity extends BaseEntity{
    private String name;
    @ManyToOne
    private MySqlDepartmentEntity parent;
}
