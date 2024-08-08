package com.veezean.codereview.server.common;

import cn.hutool.core.util.StrUtil;
import com.veezean.codereview.server.model.ValuePair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

/**
 * 界面展示内容转换加工工具
 *
 * @author Veezean
 * @since 2023/6/6
 */
@Slf4j
public class CommentFieldShowContentHelper {

    /**
     * 将实际值转换为需要在界面上展示的值
     *
     * @param valuePair
     * @return
     */
    public static String getColumnShowContent(ValuePair valuePair) {
        if (valuePair == null) {
            return "";
        }
        String showContent = valuePair.getShowName();
        if (StringUtils.isEmpty(showContent)) {
            showContent = valuePair.getValue();
        }
        return StrUtil.maxLength(showContent, 300);
    }
}
