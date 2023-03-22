package com.veezean.codereview.server.model;

import cn.hutool.core.util.ReflectUtil;
import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2023/3/9
 */
@Data
public class CommentReqBody {
    private Map<String, String> propValues = new HashMap<>();

    public CommentModel convertToCommentModel() {
        CommentModel model = new CommentModel();

        Optional.ofNullable(propValues).orElse(new HashMap<>())
                .forEach((key, value) -> {
                    try {
                        ReflectUtil.setFieldValue(model, key, value);
                    } catch (Exception e) {
                        // do nothing
                    }
                });

        return model;
    }

    public static CommentReqBody convertToReqBody(CommentModel commentModel) {
        CommentReqBody reqBody = new CommentReqBody();
        Map<String, String> propValues = new HashMap<>();
        Arrays.stream(ReflectUtil.getFields(CommentModel.class))
                .filter(field -> field.getType().getSimpleName().equals(String.class.getSimpleName()))
                .forEach(field -> {
                    String name = field.getName();
                    propValues.put(name, (String) ReflectUtil.getFieldValue(commentModel, field));
                });

        reqBody.setPropValues(propValues);
        return reqBody;
    }
}
