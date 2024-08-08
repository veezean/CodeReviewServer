package com.veezean.codereview.server.monogo;

import com.veezean.codereview.server.model.PageBeanList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public <T> void batchUpdate(Query query, Update update, Class<T> entityClazz) {
        mongoTemplate.updateMulti(query, update, entityClazz);
    }

    /**
     * 如果数组集合字段中含有指定值，则从集合中移除 注意：此方法仅适用于简单array对象数组，比如List<Long>，List<String>之类的，对于List<Object>不适用
     *
     * @param fieldName 字段的路径名称，支持嵌套类型
     * @param value 字段具体值
     * @param entityClazz
     * @param <T>
     */
    public <T> void removeIfExistinArrayFields(String fieldName, Object value, Class<T> entityClazz) {
        Query query = new Query(Criteria.where(fieldName).in(value));
        Update update = new Update();
        update.pull(fieldName, value);
        mongoTemplate.updateMulti(query, update, entityClazz);
    }

    /**
     * 往指定的文档中的array节点中插入新的节点
     *
     * @param
     * @param arrayFieldName array类型节点名称
     * @param entityClazz
     * @param <T>
     */
    public <T> void insertIntoArrayFields(Query query, String arrayFieldName, Object arrayItem, Class<T> entityClazz) {
        Update update = new Update();
        update.push(arrayFieldName, arrayItem);
        mongoTemplate.updateFirst(query, update, entityClazz);
    }

    /**
     * 根据嵌套的数组中的字段的条件，查询符合条件的数据，返回对应的顶层Object对象
     *
     * @param fieldName
     * @param value
     * @param entityClazz
     * @param <T>
     * @return T 可能为空
     */
    public <T> T queryFirstByArrayField(String fieldName, Object value, Class<T> entityClazz) {
        Query query = new Query(Criteria.where(fieldName).in(value));
        return mongoTemplate.findOne(query, entityClazz);
    }

    public <T> List<T> queryAllByArrayField(String fieldName, Object value, Class<T> entityClazz) {
        Query query = new Query(Criteria.where(fieldName).in(value));
        return mongoTemplate.find(query, entityClazz);
    }

    public <T> List<T> queryAllByArrayFieldIn(String fieldName, Collection<?> values, Class<T> entityClazz) {
        Query query = new Query(Criteria.where(fieldName).in(values));
        return mongoTemplate.find(query, entityClazz);
    }

    public <T, R> PageBeanList<R> pageQuery(Query query, Class<T> entityClazz, int currentPage, int pageSize,
                                            Function<T, R> resultMapFunc) {
        if (currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize < 10 || pageSize > 200) {
            pageSize = 10;
        }

        long totalCount = mongoTemplate.count(query, entityClazz);
        int totalPage = (int) (Math.ceil((double) totalCount / pageSize));

        Query finalQuery = query.skip(((long) (currentPage - 1)) * pageSize).limit(pageSize);
        List<R> results = mongoTemplate.find(finalQuery, entityClazz)
                .stream()
                .map(resultMapFunc)
                .collect(Collectors.toList());
        return PageBeanList.create(totalCount, pageSize, totalPage, currentPage, results);
    }
}
