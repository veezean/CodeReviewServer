package com.veezean.codereview.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/6/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValuePair implements Serializable {
    private static final long serialVersionUID = -391672348740573303L;
    private String value;
    private String showName;

    public static ValuePair build(String value) {
        return build(value, null);
    }

    public static ValuePair build(String value, String showName) {
        ValuePair pair = new ValuePair();
        pair.setValue(value);
        pair.setShowName(showName);
        return pair;
    }

    @Override
    public String toString() {
        if (!StringUtils.isEmpty(showName)) {
            return showName;
        }
        return value;
    }
}
