package com.veezean.codereview.server.common.convertor;

import com.veezean.codereview.server.model.ConvertResultModel;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/5/30
 */
public class CommonStringFieldValueConvertor implements FieldValueConvertor<String> {
    @Override
    public ConvertResultModel<String> convert(String sourceValue) {
        return new ConvertResultModel<>(sourceValue, sourceValue);
    }
}
