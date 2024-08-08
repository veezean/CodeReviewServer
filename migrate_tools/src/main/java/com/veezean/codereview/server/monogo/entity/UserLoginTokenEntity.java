package com.veezean.codereview.server.monogo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/22
 */
@Data
@Document(collection = "t_user_login_token")
public class UserLoginTokenEntity extends MongoLongIdEntity {
    @Column(nullable = false, unique = true, length = 64)
    @Indexed(unique = true)
    private String token;
    private long userId;
    @Column(nullable = false)
    private long expireAt;
}
