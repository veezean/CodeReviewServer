package com.veezean.codereview.server.repository;

import com.veezean.codereview.server.entity.ColumnDefineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/5
 */
@Repository
public interface ColumnDefineRepository extends JpaRepository<ColumnDefineEntity, Long> {
    ColumnDefineEntity findFirstByColumnCode(String columnCode);
}
