package com.veezean.codereview.server.repository;

import com.veezean.codereview.server.entity.DictCollectionEntity;
import com.veezean.codereview.server.entity.DictItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/22
 */
@Repository
public interface DictItemRepository extends JpaRepository<DictItemEntity, Long> {
    List<DictItemEntity> findAllByCollectionCode(String code);
    DictItemEntity findFirstByCollectionCodeAndItemKey(String collectionCode, String itemKey);
    void deleteAllByCollectionId(long id);
}
