package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * 评审意见字段对应的值
 *
 * @author Wang Weiren
 * @since 2023/3/25
 */
@Data
public class CommentFieldModel {
    private String code;
    /**
     * 实际值
     */
    private String value;
}
