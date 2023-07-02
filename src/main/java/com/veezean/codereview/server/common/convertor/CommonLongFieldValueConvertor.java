package com.veezean.codereview.server.common.convertor;

import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.model.ConvertResultModel;
import org.apache.commons.lang.StringUtils;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/5/30
 */
public class CommonLongFieldValueConvertor implements FieldValueConvertor<Long> {
    @Override
    public ConvertResultModel<Long> convert(String sourceValue) {
        if (StringUtils.isEmpty(sourceValue)) {
            return new ConvertResultModel<>(0L, "0");
        }
        try {
            return new ConvertResultModel<>(Long.parseLong(sourceValue), sourceValue);
        } catch (Exception e) {
            throw new CodeReviewException("字段值转Long类型错误，原始值：" + sourceValue);
        }
    }
}
