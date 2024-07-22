package com.veezean.codereview.server.mysql.repository;

import com.veezean.codereview.server.mysql.entity.MySqlUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface MySqlUserRepository extends JpaRepository<MySqlUserEntity, Long> {
    MySqlUserEntity findFirstByAccount(String account);
    List<MySqlUserEntity> findAllByDepartmentId(long deptId);
    List<MySqlUserEntity> findAllByAccountIn(List<String> accounts);
    Page<MySqlUserEntity> findAllByDepartmentId(long deptId, Pageable pageable);
}
