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
@Table(name = "user_project_bindings", schema = "code_review", catalog = "")
@Data
public class UserProjectBindingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userId;
    private String projectKey;

}
