package com.veezean.codereview.server.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2021/4/26
 */
@Entity
@Table(name = "role", schema = "code_review", catalog = "")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String roleKey;
    private String roleName;
    private String roleDesc;

}
