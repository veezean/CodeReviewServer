package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/24
 */
@Data
public class SaveDictCollectionReqBody {
    private String code;
    private String name;
    private String dictDesc;
}
