package com.veezean.codereview.server.service;

import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.common.DictCollectionType;
import com.veezean.codereview.server.entity.DictCollectionEntity;
import com.veezean.codereview.server.entity.DictItem;
import com.veezean.codereview.server.model.CreateOrModifyDictItemReqBody;
import com.veezean.codereview.server.model.SaveDictCollectionReqBody;
import com.veezean.codereview.server.model.ValuePair;
import com.veezean.codereview.server.monogo.MongoOperateHelper;
import com.veezean.codereview.server.repository.DictCollectionRepository;
import com.veezean.codereview.server.service.collector.EnumDynamicCollectorManage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/24
 */
@Service
@Slf4j
public class DictService {
    @Resource
    private DictCollectionRepository dictCollectionRepository;
    @Resource
    private MongoOperateHelper mongoOperateHelper;

    public void createDictCollection(SaveDictCollectionReqBody reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getCode())
                || StringUtils.isEmpty(reqBody.getName())
        ) {
            throw new CodeReviewException("请求参数不合法");
        }

        DictCollectionEntity dictCollectionEntity = dictCollectionRepository.queryFirstByCode(reqBody.getCode());
        if (dictCollectionEntity != null) {
            throw new CodeReviewException("字典集已经存在：" + reqBody.getCode());
        }
        DictCollectionEntity entity = new DictCollectionEntity();
        entity.setCode(reqBody.getCode());
        entity.setName(reqBody.getName());
        entity.setDictDesc(reqBody.getDictDesc());
        entity.setType(reqBody.getType());
        dictCollectionRepository.save(entity);
    }

    public void modifyDictCollection(SaveDictCollectionReqBody reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getCode())
                || StringUtils.isEmpty(reqBody.getName())
        ) {
            throw new CodeReviewException("请求参数不合法");
        }

        DictCollectionEntity existEntity = dictCollectionRepository.queryFirstByCode(reqBody.getCode());
        if (existEntity == null) {
            throw new CodeReviewException("字典集不存在：" + reqBody.getCode());
        }
        existEntity.setCode(reqBody.getCode());
        existEntity.setName(reqBody.getName());
        existEntity.setDictDesc(reqBody.getDictDesc());
        existEntity.setType(reqBody.getType());
        dictCollectionRepository.save(existEntity);
    }

    @Transactional
    public void deleteDictCollection(long id) {
        dictCollectionRepository.deleteById(id);
    }

    @Transactional
    public void deleteDictCollections(List<Long> ids) {
        for (long id : ids) {
            dictCollectionRepository.deleteById(id);
        }
    }

    public DictCollectionEntity queryCollection(String collectionCode) {
        return Optional.ofNullable(collectionCode)
                .filter(StringUtils::isNotEmpty)
                .map(code -> dictCollectionRepository.queryFirstByCode(code))
                .orElseThrow(() -> new CodeReviewException("字典集不存在：" + collectionCode));
    }

    public List<DictCollectionEntity> queryCollections() {
        return dictCollectionRepository.findAll();
    }

    public DictItem queryItemById(Long itemId) {
        DictCollectionEntity entity = mongoOperateHelper.queryFirstByArrayField("items.id", itemId,
                DictCollectionEntity.class);
        if (entity != null && CollectionUtils.isNotEmpty(entity.getItems())) {
            return entity.getItems().stream()
                    .filter(dictItem -> dictItem.getId() == itemId)
                    .findFirst()
                    .orElseThrow(() -> new CodeReviewException("记录不存在"));
        }

        throw new CodeReviewException("记录不存在");
    }

    public List<DictItem> queryDictItemsByCollectionCode(String collectionCode) {
        DictCollectionEntity collectionEntity = dictCollectionRepository.queryFirstByCode(collectionCode);

        // 只有枚举类型的时候，可以查询对应的Item列表
        if (collectionEntity != null && collectionEntity.getType() == DictCollectionType.ENUM_LIST.getValue()) {
            return Optional.ofNullable(collectionEntity.getItems())
                    .orElse(new ArrayList<>())
                    .stream()
                    .sorted((o1, o2) -> o1.getSort() - o2.getSort() > 0 ? 1 : -1)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public List<ValuePair> queryListItemsByCollectionCode(String collectionCode) {
        DictCollectionEntity collectionEntity = dictCollectionRepository.queryFirstByCode(collectionCode);
        if (collectionEntity == null) {
            return new ArrayList<>();
        }
        // 只有枚举类型的时候，可以查询对应的Item列表
        if (collectionEntity.getType() == DictCollectionType.SYSTEM_DYNAMIC_LIST.getValue()) {
            return EnumDynamicCollectorManage.getCollector(collectionCode).orElseThrow(() -> new CodeReviewException(
                            "指定的" + DictCollectionType.SYSTEM_DYNAMIC_LIST.getDesc() + "code不支持"))
                    .doCollect();
        } else {
            return queryDictItemsByCollectionCode(collectionCode)
                    .stream()
                    .sorted((o1, o2) -> o1.getSort() - o2.getSort() > 0 ? 1 : -1)
                    .map(dictItem -> new ValuePair(dictItem.getValue(), dictItem.getShowName()))
                    .collect(Collectors.toList());
        }
    }

    public void createOrModifyDictItem(CreateOrModifyDictItemReqBody reqBody) {
        if (reqBody != null && reqBody.getId() != null && reqBody.getId() > 0L) {
            modifyDictItem(reqBody);
        } else {
            createDictItem(reqBody);
        }
    }

    private void modifyDictItem(CreateOrModifyDictItemReqBody reqBody) {
        if (reqBody == null
                || reqBody.getId() == null
                || reqBody.getId() <= 0L
                || StringUtils.isEmpty(reqBody.getValue())
                || StringUtils.isEmpty(reqBody.getShowName())
        ) {
            throw new CodeReviewException("参数不合法");
        }

        // 更新指定item节点
        Query query = new Query(Criteria.where("items.id").is(reqBody.getId()));
        Update update = new Update();
        update.set("items.$.value", reqBody.getValue());
        update.set("items.$.showName", reqBody.getShowName());
        update.set("items.$.itemDesc", reqBody.getItemDesc());
        update.set("items.$.sort", reqBody.getSort());
        mongoOperateHelper.batchUpdate(query, update, DictCollectionEntity.class);
    }


    private void createDictItem(CreateOrModifyDictItemReqBody reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getCollectionCode())
                || StringUtils.isEmpty(reqBody.getValue())
                || StringUtils.isEmpty(reqBody.getShowName())
        ) {
            throw new CodeReviewException("参数不合法");
        }

        Query query = new Query(Criteria.where("code").is(reqBody.getCollectionCode()));
        DictItem dictItem = DictItem.createEmptyItem();
        dictItem.setItemDesc(reqBody.getItemDesc());
        dictItem.setValue(reqBody.getValue());
        dictItem.setSort(reqBody.getSort());
        dictItem.setShowName(reqBody.getShowName());
        mongoOperateHelper.insertIntoArrayFields(query, "items", dictItem, DictCollectionEntity.class);

        DictCollectionEntity collectionEntity = dictCollectionRepository.queryFirstByCode(reqBody.getCollectionCode());
        if (collectionEntity == null) {
            throw new CodeReviewException("字典集不存在：" + reqBody.getCollectionCode());
        }
    }

    @Transactional
    public void deleteDictItem(long itemId) {
        DictCollectionEntity entity = mongoOperateHelper.queryFirstByArrayField("items.id", itemId, DictCollectionEntity.class);
        if (entity == null) {
            throw  new CodeReviewException("字典值不存在:" + itemId);
        }

        List<DictItem> dictItems = entity.getItems().stream().filter(dictItem -> dictItem.getId() != itemId).collect(Collectors.toList());
        entity.setItems(dictItems);
        dictCollectionRepository.save(entity);
    }

}
