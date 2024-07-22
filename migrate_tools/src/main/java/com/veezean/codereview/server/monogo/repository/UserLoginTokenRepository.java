package com.veezean.codereview.server.monogo.repository;

import com.veezean.codereview.server.monogo.entity.UserLoginTokenEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/22
 */
@Repository
public interface UserLoginTokenRepository extends MongoRepository<UserLoginTokenEntity, Long> {
    UserLoginTokenEntity queryFirstByToken(String token);
    UserLoginTokenEntity queryFirstByUserId(long userId);
    void deleteAllByUserId(long userId);
    void deleteAllByExpireAtLessThan(long time);
}
