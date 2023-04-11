package com.veezean.codereview.server.common;

import com.veezean.codereview.server.model.UserDetail;
import lombok.extern.slf4j.Slf4j;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2023/3/5
 */
@Slf4j
public class CurrentUserHolder {
    private static final ThreadLocal<UserDetail> CURRENT_USER = new ThreadLocal<>();

    public static void clearCurrentThreadCache() {
        CURRENT_USER.remove();
    }

    public static void saveCurrentUserInfo(UserDetail userDetailEntity) {
        CURRENT_USER.set(userDetailEntity);
    }

    public static UserDetail getCurrentUser() {
        return CURRENT_USER.get();
    }
}
