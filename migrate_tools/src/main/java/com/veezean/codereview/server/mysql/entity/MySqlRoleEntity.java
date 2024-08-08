package com.veezean.codereview.server.mysql.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/22
 */
@Data
@Entity
@Table(name = "t_role", schema = "code_review", catalog = "")
public class MySqlRoleEntity extends BaseEntity{
    private String roleCode;
    private String roleName;
    private String roleDesc;
    private String canAccessPage;
}
