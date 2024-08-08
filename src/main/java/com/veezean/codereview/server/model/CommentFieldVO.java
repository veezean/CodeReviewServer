package com.veezean.codereview.server.model;

import com.veezean.codereview.server.common.InputTypeDefine;
import lombok.Data;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * 编辑场景对应评论字段
 *
 * @author Veezean
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

    /**
     * 构建一个界面不可见字段，用于数据透传
     *
     * @return
     */
    public static CommentFieldVO buildInvisableFieldVO(String code, ValuePair valuePair) {
        CommentFieldVO editModel = new CommentFieldVO();
        editModel.setCode(code);
        editModel.setEditable(false);
        editModel.setShow(false);
        editModel.setRequired(false);
        editModel.setInputType(InputTypeDefine.TEXT.getValue());
        editModel.setEnumValues(null);
        editModel.setShowName("");
        editModel.setValuePair(valuePair);
        return editModel;
    }
}
