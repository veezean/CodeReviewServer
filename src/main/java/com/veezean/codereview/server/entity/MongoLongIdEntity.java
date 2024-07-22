package com.veezean.codereview.server.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * MongoDB抽象Entity父类，自动生成long型自增ID
 *
 * @author Wang Weiren
 * @since 2024/7/18
 */
@Data
public abstract class MongoLongIdEntity implements Serializable {
    @Id
    private Long id;
}
