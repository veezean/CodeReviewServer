package com.veezean.codereview.server.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/22
 */
@Data
@Entity
@Table(name = "t_role", schema = "code_review", catalog = "")
public class RoleEntity extends BaseEntity{
    private String roleCode;
    private String roleName;
    private String roleDesc;
    private String canAccessPage;
}
