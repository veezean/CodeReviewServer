package com.veezean.codereview.server.repository;

import com.veezean.codereview.server.entity.RoleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/22
 */
@Repository
public interface RoleRepository extends MongoRepository<RoleEntity, Long> {
    List<RoleEntity> findAllByRoleCode(String roleCode);
    List<RoleEntity> findAllByRoleName(String roleName);
    List<RoleEntity> findAllByIdIn(List<Long> roleIds);
}
