package com.veezean.codereview.server.repository;

import com.veezean.codereview.server.entity.RoleEntity;
import com.veezean.codereview.server.entity.UserEntity;
import com.veezean.codereview.server.entity.UserRoleEntity;
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
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    List<UserRoleEntity> findAllByUser(UserEntity user);
    List<UserRoleEntity> findAllByRoleId(long roleId);
    void deleteAllByRoleId(long roleId);
    void deleteAllByUser(UserEntity user);
    UserRoleEntity findFirstByUserAndRole(UserEntity userEntity, RoleEntity roleEntity);
    void deleteAllByUserAndRole(UserEntity userEntity, RoleEntity roleEntity);
}
