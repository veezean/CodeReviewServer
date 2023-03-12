package com.veezean.codereview.server.common;

import com.veezean.codereview.server.model.ClientUserQueryReqBody;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
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
