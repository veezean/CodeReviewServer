package com.veezean.codereview.server.model;

import lombok.Data;

import java.util.List;

/**
 * 编辑场景对应评论字段
 *
 * @author Wang Weiren
 * @since 2023/3/25
 */
@Data
public class CommentFieldVO {
    private String code;
    private ValuePair valuePair;
    private String showName;
    private String inputType;
    private List<ValuePair> enumValues;
    private boolean required;
    private boolean editable;
    private boolean show;
}
