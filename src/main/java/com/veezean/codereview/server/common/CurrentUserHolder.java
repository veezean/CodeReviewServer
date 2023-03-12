package com.veezean.codereview.server.common;

import lombok.extern.slf4j.Slf4j;
import xyz.erupt.upms.model.EruptUser;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2023/3/5
 */
@Slf4j
public class CurrentUserHolder {
    private static final ThreadLocal<EruptUser> CURRENT_USER = new ThreadLocal<>();

    public static void clearCurrentThreadCache() {
        CURRENT_USER.remove();
    }

    public static void saveCurrentUserInfo(EruptUser userDetailEntity) {
        CURRENT_USER.set(userDetailEntity);
    }

    public static EruptUser getCurrentUser() {
        return CURRENT_USER.get();
    }
}
