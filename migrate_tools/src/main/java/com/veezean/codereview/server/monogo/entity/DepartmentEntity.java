package com.veezean.codereview.server.monogo.entity;

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
@Document(collection = "t_department")
public class DepartmentEntity extends MongoLongIdEntity {

    private String name;
    @Indexed
    private Long parentId;
}
