package com.veezean.codereview.server.monogo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/22
 */
@Data
@Document(collection = "t_user")
public class UserEntity extends MongoLongIdEntity {
    @Indexed(unique = true)
    private String account;
    private String name;
    @Indexed
    private long departmentId;
    private String password;
    private String phoneNumber;
    @Indexed
    private List<Long> roles;
}
