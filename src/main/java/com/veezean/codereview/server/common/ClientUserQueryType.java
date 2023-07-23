package com.veezean.codereview.server.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/12
 */
@Getter
@AllArgsConstructor
public enum  ClientUserQueryType {
    ALL(0, "所有用户"),
    SAME_DEPARTMENT(1, "当前用户同部门的用户");
    private int value;
    private String desc;

    public static ClientUserQueryType getType(int value) {
        return Arrays.stream(values()).filter(clientUserQueryType -> clientUserQueryType.value == value)
                .findFirst()
                .orElse(ALL);
    }
}
