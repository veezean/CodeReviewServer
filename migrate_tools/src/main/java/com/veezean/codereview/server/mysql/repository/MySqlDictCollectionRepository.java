package com.veezean.codereview.server.mysql.repository;

import com.veezean.codereview.server.mysql.entity.MySqlDictCollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/22
 */
@Repository
public interface MySqlDictCollectionRepository extends JpaRepository<MySqlDictCollectionEntity, Long> {
    MySqlDictCollectionEntity queryFirstByCode(String code);
}
