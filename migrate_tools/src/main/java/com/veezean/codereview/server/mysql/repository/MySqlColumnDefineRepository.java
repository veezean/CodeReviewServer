package com.veezean.codereview.server.mysql.repository;

import com.veezean.codereview.server.mysql.entity.MySqlColumnDefineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/5
 */
@Repository
public interface MySqlColumnDefineRepository extends JpaRepository<MySqlColumnDefineEntity, Long> {
    MySqlColumnDefineEntity findFirstByColumnCode(String columnCode);
}
