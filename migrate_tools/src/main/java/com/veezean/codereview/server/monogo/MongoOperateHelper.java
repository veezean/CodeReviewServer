package com.veezean.codereview.server.monogo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2024/7/20
 */
@Component
@Slf4j
public class MongoOperateHelper {
    @Resource
    private MongoTemplate mongoTemplate;

}
