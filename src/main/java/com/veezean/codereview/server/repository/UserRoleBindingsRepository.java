package com.veezean.codereview.server.repository;

import com.veezean.codereview.server.entity.UserRoleBindingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2021/4/26
 */
@Repository
public interface UserRoleBindingsRepository extends JpaRepository<UserRoleBindingsEntity, Long> {

}
