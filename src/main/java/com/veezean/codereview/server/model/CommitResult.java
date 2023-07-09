package com.veezean.codereview.server.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/6/30
 */
@Data
public class CommitResult {
    private boolean success;
    private String errDesc;
    private Map<String, Long> versionMap = new HashMap<>();
    private List<String> failedIds = new ArrayList<>();

    public void putVersion(String id, long version) {
        if (versionMap == null) {
            versionMap = new HashMap<>();
        }
        versionMap.put(id, version);
    }

    public void addFailedId(String id) {
        if (failedIds == null) {
            failedIds = new ArrayList<>();
        }
        failedIds.add(id);
    }
}
