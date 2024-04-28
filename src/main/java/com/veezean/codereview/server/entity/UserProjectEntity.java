package com.veezean.codereview.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 用户和项目绑定关系
 *
 * @author Veezean
 * @since 2024/4/26
 */
@Data
@Entity
@Table(name = "t_user_project", schema = "code_review", catalog = "")
public class UserProjectEntity extends BaseEntity{
    private String account;
    private long projectId;
}
