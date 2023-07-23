package com.veezean.codereview.server.common.convertor;

import com.veezean.codereview.server.model.ConvertResultModel;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/5/30
 */
public interface FieldValueConvertor<T> {
    ConvertResultModel<T> convert(String sourceValue);
}
