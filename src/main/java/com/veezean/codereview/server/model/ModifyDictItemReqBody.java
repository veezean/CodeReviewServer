package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/24
 */
@Data
public class ModifyDictItemReqBody {
    /**
     * 非必填，如果有则表示更新，没有则表示插入
     */
    private Long id;
    private String value;
    private String showName;
    private String itemDesc;
    private int sort;
}
