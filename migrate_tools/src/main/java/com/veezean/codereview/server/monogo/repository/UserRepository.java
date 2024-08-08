package com.veezean.codereview.server.monogo.repository;

import com.veezean.codereview.server.monogo.entity.UserEntity;
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
