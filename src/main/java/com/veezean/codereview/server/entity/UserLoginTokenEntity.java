package com.veezean.codereview.server.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/22
 */
@Entity
@Table(name = "t_user_login_token", schema = "code_review", catalog = "")
@Data
public class UserLoginTokenEntity extends BaseEntity{
    @Column(nullable = false, unique = true, length = 64)
    private String token;
    private long userId;
    @Column(nullable = false)
    private long expireAt;
}
