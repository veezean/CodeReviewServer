package com.veezean.codereview.server.repository;

import com.veezean.codereview.server.entity.DepartmentEntity;
import com.veezean.codereview.server.entity.DictCollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/22
 */
@Repository
public interface DictCollectionRepository extends JpaRepository<DictCollectionEntity, Long> {
    DictCollectionEntity queryFirstByCode(String code);
    void deleteAllByCode(String code);
}
