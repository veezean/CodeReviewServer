package com.veezean.codereview.server.service.executor;

import com.veezean.codereview.server.monogo.entity.DictCollectionEntity;
import com.veezean.codereview.server.monogo.entity.DictItem;
import com.veezean.codereview.server.monogo.repository.DictCollectionRepository;
import com.veezean.codereview.server.mysql.entity.DictItemEntity;
import com.veezean.codereview.server.mysql.repository.MySqlDictCollectionRepository;
import com.veezean.codereview.server.mysql.repository.MySqlDictItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2024/7/22
 */
@Service
@Slf4j
public class MigrateDictCollectionExecutor implements IExecutor {

    @Resource
    private MySqlDictCollectionRepository mySqlDictCollectionRepository;
    @Resource
    private MySqlDictItemRepository mySqlDictItemRepository;
    @Resource
    private DictCollectionRepository dictCollectionRepository;

    @Override
    @Transactional
    public void execute() {

        // 先清空已有的内容
        dictCollectionRepository.deleteAll();
        log.info("————清空已有的MongoDB字典值信息————");

        Map<String, List<DictItemEntity>> collectionItems = mySqlDictItemRepository.findAll().stream()
                .collect(Collectors.groupingBy(dictItemEntity -> dictItemEntity.getCollection().getCode()));

        // 从MySQL中迁移数据
        List<DictCollectionEntity> collectionEntities = mySqlDictCollectionRepository.findAll()
                .stream()
                .map(mySqlDictCollectionEntity -> {
                    DictCollectionEntity entity = new DictCollectionEntity();
                    entity.setId(mySqlDictCollectionEntity.getId());
                    entity.setCode(mySqlDictCollectionEntity.getCode());
                    entity.setDictDesc(mySqlDictCollectionEntity.getDictDesc());
                    entity.setType(mySqlDictCollectionEntity.getType());
                    entity.setName(mySqlDictCollectionEntity.getName());

                    List<DictItemEntity> itemEntities = collectionItems.get(mySqlDictCollectionEntity.getCode());
                    if (CollectionUtils.isNotEmpty(itemEntities)) {
                        List<DictItem> dictItems = itemEntities.stream()
                                .map(dictItemEntity -> {
                                    DictItem item = new DictItem();
                                    item.setId(dictItemEntity.getId());
                                    item.setValue(dictItemEntity.getValue());
                                    item.setShowName(dictItemEntity.getShowName());
                                    item.setItemDesc(dictItemEntity.getItemDesc());
                                    item.setSort(dictItemEntity.getSort());
                                    return item;
                                }).collect(Collectors.toList());
                        entity.setItems(dictItems);
                    } else {
                        entity.setItems(new ArrayList<>());
                    }

                    return entity;
                }).collect(Collectors.toList());
        dictCollectionRepository.saveAll(collectionEntities);

        log.info("————字典值信息迁移完成，共计完成{}条记录————", collectionEntities.size());
    }
}
