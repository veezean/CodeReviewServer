package com.veezean.codereview.server.model;

import com.veezean.codereview.server.entity.DictCollectionEntity;
import lombok.Data;

import javax.persistence.ManyToOne;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/24
 */
@Data
public class SaveDictItemReqBody {
    private String itemKey;
    private String itemValue;
    private String itemDesc;
    private int sort;
    private String collectionCode;
}
