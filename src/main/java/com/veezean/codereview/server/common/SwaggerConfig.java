package com.veezean.codereview.server.common;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/7
 */
@Configuration
@Slf4j
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                //开启
                .enable(true)
                .select()
                //扫描路径下使用@Api的controller
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("CodeReviewServer接口文档")
                .description("")
                //设置作者信息
                .contact(new Contact("veezean","http://blog.codingcoder.cn","veezean@outlook.com"))
                .version("1.0")
                .build();
    }
}
