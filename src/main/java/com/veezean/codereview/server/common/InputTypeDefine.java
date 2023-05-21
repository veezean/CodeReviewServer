package com.veezean.codereview.server.common;

/**
 * 输入类型定义
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2022/5/22
 */
public enum InputTypeDefine {
    TEXT("TEXT"),
    TEXT_AREA("TEXTAREA"),
    COMBO_BOX("COMBO_BOX");

    private String value;

    InputTypeDefine(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
