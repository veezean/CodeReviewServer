package com.veezean.codereview.server.common;

import com.veezean.codereview.server.interceptor.ClientApiAuthInterceptor;
import com.veezean.codereview.server.interceptor.ServerApiAuthInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2023/3/5
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ClientApiAuthInterceptor clientApiAuthInterceptor;
    @Autowired
    private ServerApiAuthInterceptor serverApiAuthInterceptor;

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

        registry.addInterceptor(serverApiAuthInterceptor)
                .addPathPatterns(
                        "/server/**"
                )
                .excludePathPatterns(
                        "/server/login/doLogin",
                        "/server/login/doLogout"
                );
    }
}
