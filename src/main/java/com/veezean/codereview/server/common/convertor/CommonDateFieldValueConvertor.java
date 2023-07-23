package com.veezean.codereview.server.common.convertor;

import cn.hutool.core.date.DateUtil;
import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.model.ConvertResultModel;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/5/30
 */
public class CommonDateFieldValueConvertor implements FieldValueConvertor<Date> {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public ConvertResultModel<Date> convert(String sourceValue) {
        if (StringUtils.isEmpty(sourceValue)) {
            Date date = new Date();
            return new ConvertResultModel<>(date, simpleDateFormat.format(date));
        }
        try {
            return new ConvertResultModel<>(DateUtil.parseDateTime(sourceValue), sourceValue);
        } catch (Exception e) {
            throw new CodeReviewException("字段值转Date类型错误，原始值：" + sourceValue);
        }
    }
}
