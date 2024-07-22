package com.veezean.codereview.server.mysql.repository;

import com.veezean.codereview.server.mysql.entity.MySqlRoleEntity;
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
public interface MySqlRoleRepository extends JpaRepository<MySqlRoleEntity, Long> {
    List<MySqlRoleEntity> findAllByRoleCode(String roleCode);
    List<MySqlRoleEntity> findAllByRoleName(String roleName);
}
