package com.veezean.codereview.server.repository;

import com.veezean.codereview.server.entity.RoleEntity;
import com.veezean.codereview.server.entity.UserEntity;
import com.veezean.codereview.server.entity.UserLoginTokenEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface UserRepository extends MongoRepository<UserEntity, Long> {
    UserEntity findFirstByAccount(String account);
    List<UserEntity> findAllByDepartmentId(long deptId);
    void deleteAllByAccount(String account);
    void deleteAllByAccountIn(List<String> accounts);
}
