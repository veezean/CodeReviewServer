package com.veezean.codereview.server.monogo;

import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.monogo.entity.MongoLongIdEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * MongoDB 自增ID生成器
 *
 * @author Wang Weiren
 * @since 2024/7/21
 */
@Component
@Slf4j
public class MongoIdGenerator {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 获取指定MongoDB集的自增ID
     *
     * @param entityClazz
     * @param <T>
     * @return
     */
    @Transactional
    public <T extends MongoLongIdEntity> long nextCollectionSeqId(Class<T> entityClazz) {
        String collection = getCollectionFromClazz(entityClazz);
        return nextSeqId(collection);
    }


    /**
     * 获取指定collection的自增ID
     *
     * @param key
     * @return
     */
    @Transactional
    public long nextSeqId(String key) {
        Query query = new Query(Criteria.where("key").is(key));

        Update update = new Update();
        update.inc("seqId");

        IdGenerateEntity entity = mongoTemplate.findAndModify(query, update, IdGenerateEntity.class);
        if (entity != null) {
            return entity.getSeqId();
        }

        // 首次，初始化计数器
        initSeqId(key);
        // 初始化之后，重新获取ID
        entity = mongoTemplate.findAndModify(query, update, IdGenerateEntity.class);
        if (entity != null) {
            return entity.getSeqId();
        }

        throw new CodeReviewException("生成ID失败，计数器不存在:" + key);
    }

    private <T extends MongoLongIdEntity> String getCollectionFromClazz(Class<T> entityClazz) {
        return Optional.ofNullable(entityClazz.getAnnotation(Document.class))
                .map(Document::collection)
                .filter(StringUtils::isNotEmpty)
                .orElseThrow(() -> new CodeReviewException("缺失@Document注解，无法获取collection信息"));
    }

    /**
     * 初始化操作
     *
     * @param key
     */
    private synchronized void initSeqId(String key) {
        Query query = new Query(Criteria.where("key").is(key));
        IdGenerateEntity generateEntity = mongoTemplate.findOne(query, IdGenerateEntity.class);
        if (generateEntity == null) {
            IdGenerateEntity entity = new IdGenerateEntity();
            // 初始值从大一些开始，避免覆盖已有内容
            entity.setSeqId(10000L);
            entity.setKey(key);
            mongoTemplate.insert(entity);
        }
    }
}
