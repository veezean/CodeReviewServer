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
@Document(collection = "t_role")
public class RoleEntity extends MongoLongIdEntity {
    @Indexed(unique = true)
    private String roleCode;
    private String roleName;
    private String roleDesc;
    private String canAccessPage;
}
