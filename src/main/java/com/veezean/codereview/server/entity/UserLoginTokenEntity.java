package com.veezean.codereview.server.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/22
 */
@Data
@Document(collection = "t_user_login_token")
public class UserLoginTokenEntity extends MongoLongIdEntity {
    @Indexed(unique = true)
    private String token;
    private long userId;
    private long expireAt;
}
