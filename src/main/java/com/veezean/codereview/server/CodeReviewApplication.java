package com.veezean.codereview.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import xyz.erupt.core.annotation.EruptScan;

/**
 * 服务启动入口
 *
 * @author Wang Weiren
 * @since 2021/4/25
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableJpaAuditing
@EntityScan("com.veezean.codereview")
@EnableJpaRepositories(basePackages = {"com.veezean.codereview"})
@EruptScan
public class CodeReviewApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeReviewApplication.class, args);
    }
}
