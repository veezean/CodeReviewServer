package com.veezean.codereview.server.scheduler;

import com.veezean.codereview.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/22
 */
@Component
@Slf4j
public class TokenCleanScheduler {

    @Autowired
    private UserService userService;

    @Scheduled(initialDelay = 60000L, fixedDelay = 60000L)
    public void timelyCleanExpiredToken() {
        log.info("开始清理过期token");
        userService.cleanExpiredTokens();
        log.info("结束清理过期token");
    }
}
