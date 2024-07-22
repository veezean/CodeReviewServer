package com.veezean.codereview.server.repository;

import com.veezean.codereview.server.entity.DictCollectionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/22
 */
@Repository
public interface DictCollectionRepository extends MongoRepository<DictCollectionEntity, Long> {
    DictCollectionEntity queryFirstByCode(String code);
}
