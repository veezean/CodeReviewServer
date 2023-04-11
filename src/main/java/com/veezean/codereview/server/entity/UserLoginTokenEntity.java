package com.veezean.codereview.server.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/22
 */
@Entity
@Table(name = "t_user_login_token", schema = "code_review", catalog = "")
@Data
public class UserLoginTokenEntity extends BaseEntity{
    @Column(nullable = false, unique = true, length = 64)
    private String token;
    @ManyToOne
    private UserEntity user;
    @Column(nullable = false)
    private long expireAt;
}
