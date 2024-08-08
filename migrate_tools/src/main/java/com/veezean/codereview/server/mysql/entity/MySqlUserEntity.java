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
@Entity
@Table(name = "t_user", schema = "code_review", catalog = "")
@Data
public class MySqlUserEntity extends BaseEntity{
    private String account;
    private String name;
    @ManyToOne
    private MySqlDepartmentEntity department;
    private String password;
    private String phoneNumber;
}
