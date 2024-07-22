package com.veezean.codereview.server.mysql.repository;

import com.veezean.codereview.server.mysql.entity.DictItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/22
 */
@Repository
public interface MySqlDictItemRepository extends JpaRepository<DictItemEntity, Long> {
    List<DictItemEntity> findAllByCollectionCode(String code);
    DictItemEntity findFirstByCollectionCodeAndValue(String collectionCode, String itemValue);
    void deleteAllByCollectionId(long id);
}
