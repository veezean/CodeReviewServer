package com.veezean.codereview.server.service.stats;

import com.veezean.codereview.server.model.ValuePair;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2024/4/16
 */
@Data
public class StatResult {
    private Map<String, ValuePair> data = new HashMap<>();
    private int count;

    public void addField(String field, ValuePair pair) {
        data.put(field, pair);
    }
}
