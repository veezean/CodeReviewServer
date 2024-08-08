package com.veezean.codereview.server.monogo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/22
 */
@Data
@Document(collection = "t_dict_collection")
@CompoundIndexes({
        @CompoundIndex(def = "{'code':1,'items.id':1}", unique = true)
})
public class DictCollectionEntity extends MongoLongIdEntity {
    @Indexed(unique = true)
    private String code;
    private String name;
    private String dictDesc;
    private int type;
    private List<DictItem> items;
}
