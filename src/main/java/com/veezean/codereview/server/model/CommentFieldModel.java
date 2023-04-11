package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/25
 */
@Data
public class CommentFieldModel {
    private String code;
    private String name;
    private String value;
    private int sort;
    private String type;
}
