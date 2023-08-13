package com.veezean.codereview.server.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/7/2
 */
@Getter
@AllArgsConstructor
public enum CommentOperateType {
    COMMIT(0, "commit"),
    MODIFY(1, "modify"),
    CONFIRM(2, "confirm"),
    DELETE(3, "delete");
    private int value;
    private String desc;

    public static CommentOperateType of(int value) {
        return Arrays.stream(values()).filter(commentOperateType -> commentOperateType.getValue() == value)
                .findFirst()
                .orElse(COMMIT);
    }
}
