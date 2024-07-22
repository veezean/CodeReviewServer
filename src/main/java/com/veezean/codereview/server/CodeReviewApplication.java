package com.veezean.codereview.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 服务启动入口
 *
 * @author Veezean
 * @since 2021/4/25
 */
@SpringBootApplication
//@EnableJpaAuditing
@EntityScan("com.veezean.codereview")
//@EnableJpaRepositories(basePackages = {"com.veezean.codereview"})
@EnableScheduling
public class CodeReviewApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeReviewApplication.class, args);
    }
}
