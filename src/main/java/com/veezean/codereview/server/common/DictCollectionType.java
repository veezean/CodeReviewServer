package com.veezean.codereview.server.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/6/6
 */
@AllArgsConstructor
@Getter
public enum DictCollectionType {
    ENUM_LIST(0, "手动配置枚举项"),
    SYSTEM_DYNAMIC_LIST(1, "系统预置动态列表");

private int value;
private String desc;
}
