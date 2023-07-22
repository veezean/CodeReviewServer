package com.veezean.codereview.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/22
 */
@Data
@Entity
@Table(name = "t_dict_collection", schema = "code_review", catalog = "")
public class DictCollectionEntity extends BaseEntity {
    private String code;
    private String name;
    private String dictDesc;
    private int type;
}
