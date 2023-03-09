package com.veezean.codereview.server.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/5
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ClientApiAuthInterceptor clientApiAuthInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(clientApiAuthInterceptor)
                .addPathPatterns(
                        "/client/**"
                )
                .excludePathPatterns(
                        "/client/system/checkConnection",
                        "/client/system/checkAuth"
                );
    }
}
