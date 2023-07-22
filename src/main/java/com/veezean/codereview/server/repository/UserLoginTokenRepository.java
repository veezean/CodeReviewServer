package com.veezean.codereview.server.repository;

import com.veezean.codereview.server.entity.UserEntity;
import com.veezean.codereview.server.entity.UserLoginTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/22
 */
@Repository
public interface UserLoginTokenRepository extends JpaRepository<UserLoginTokenEntity, Long> {
    UserLoginTokenEntity queryFirstByToken(String token);
    UserLoginTokenEntity queryFirstByUserId(long userId);
    void deleteAllByUserId(long userId);
    void deleteAllByExpireAtLessThan(long time);
}
