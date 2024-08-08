package com.veezean.codereview.server.mysql.repository;

import com.veezean.codereview.server.mysql.entity.MySqlRoleEntity;
import com.veezean.codereview.server.mysql.entity.MySqlUserEntity;
import com.veezean.codereview.server.mysql.entity.MySqlUserRoleEntity;
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
public interface MySqlUserRoleRepository extends JpaRepository<MySqlUserRoleEntity, Long> {
    List<MySqlUserRoleEntity> findAllByUser(MySqlUserEntity user);

    List<MySqlUserRoleEntity> findAllByRoleId(long roleId);

    void deleteAllByRoleId(long roleId);

    void deleteAllByUser(MySqlUserEntity user);

    MySqlUserRoleEntity findFirstByUserAndRole(MySqlUserEntity userEntity, MySqlRoleEntity roleEntity);

    void deleteAllByUserAndRole(MySqlUserEntity userEntity, MySqlRoleEntity roleEntity);
}
