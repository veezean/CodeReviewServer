package com.veezean.codereview.server.monogo.entity;

import cn.hutool.core.util.RandomUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/22
 */
@Data
public class DictItem implements Serializable {
    /**
     * 唯一标识
     */
    private long id;
    private String value;
    private String showName;
    private String itemDesc;
    private int sort;

    public static DictItem createEmptyItem() {
        DictItem item = new DictItem();
        item.setId(RandomUtil.randomLong(9999999999L));
        return item;
    }
}
