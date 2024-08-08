package com.veezean.codereview.server.monogo;

import com.veezean.codereview.server.monogo.entity.MongoLongIdEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 入库前的监听处理器
 *
 * @author Veezean
 * @since 2023/7/2
 */
@Component
@Slf4j
public class CommonMongoEventListener extends AbstractMongoEventListener<MongoLongIdEntity> {

    @Resource
    private MongoIdGenerator mongoIdGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<MongoLongIdEntity> event) {
        // 插入前自动生成ID
        MongoLongIdEntity source = event.getSource();
        Long id = source.getId();
        if (id == null || id == 0L) {
            source.setId(mongoIdGenerator.nextCollectionSeqId(source.getClass()));
        }

        super.onBeforeConvert(event);
    }
}
