package com.veezean.codereview.server.service;

import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.common.DictCollectionType;
import com.veezean.codereview.server.entity.DictCollectionEntity;
import com.veezean.codereview.server.entity.DictItemEntity;
import com.veezean.codereview.server.model.SaveDictCollectionReqBody;
import com.veezean.codereview.server.model.SaveDictItemReqBody;
import com.veezean.codereview.server.model.ValuePair;
import com.veezean.codereview.server.repository.DictCollectionRepository;
import com.veezean.codereview.server.repository.DictItemRepository;
import com.veezean.codereview.server.service.collector.EnumDynamicCollectorManage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private DictCollectionRepository dictCollectionRepository;
    @Autowired
    private DictItemRepository dictItemRepository;

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
        dictCollectionRepository.saveAndFlush(entity);
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
        dictCollectionRepository.saveAndFlush(existEntity);
    }

    @Transactional
    public void deleteDictCollection(long id) {
        dictItemRepository.deleteAllByCollectionId(id);
        dictCollectionRepository.deleteById(id);
    }

    @Transactional
    public void deleteDictCollections(List<Long> ids) {
        for (long id : ids) {
            dictItemRepository.deleteAllByCollectionId(id);
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

    public DictItemEntity queryItemById(Long itemId) {
        return dictItemRepository.findById(itemId).orElseThrow(() -> new CodeReviewException("记录不存在"));
    }

    public List<DictItemEntity> queryDictItemsByCollectionCode(String collectionCode) {
        DictCollectionEntity collectionEntity = dictCollectionRepository.queryFirstByCode(collectionCode);

        // 只有枚举类型的时候，可以查询对应的Item列表
        if (collectionEntity != null && collectionEntity.getType() == DictCollectionType.ENUM_LIST.getValue()) {
            return dictItemRepository.findAllByCollectionCode(collectionCode)
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
            return dictItemRepository.findAllByCollectionCode(collectionCode)
                    .stream()
                    .sorted((o1, o2) -> o1.getSort() - o2.getSort() > 0 ? 1 : -1)
                    .map(dictItemEntity -> new ValuePair(dictItemEntity.getValue(), dictItemEntity.getShowName()))
                    .collect(Collectors.toList());
        }
    }

    /**
     * 根据字典集Code和字典值，查询对应的显示内容
     * @param collectionCode
     * @param itemValue
     * @return
     */
    public String queryShowNameByCollectionCodeAndItemValue(String collectionCode, String itemValue) {
        return queryListItemsByCollectionCode(collectionCode).stream()
                .filter(valueNamePair -> StringUtils.equals(itemValue, valueNamePair.getValue()))
                .map(ValuePair::getShowName)
                .findFirst()
                .orElse(itemValue);
    }

    public void createOrModifyDictItem(SaveDictItemReqBody reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getCollectionCode())
                || StringUtils.isEmpty(reqBody.getValue())
                || StringUtils.isEmpty(reqBody.getShowName())
        ) {
            throw new CodeReviewException("参数不合法");
        }
        DictCollectionEntity collectionEntity = dictCollectionRepository.queryFirstByCode(reqBody.getCollectionCode());
        if (collectionEntity == null) {
            throw new CodeReviewException("字典集不存在：" + reqBody.getCollectionCode());
        }
        DictItemEntity entity =
                Optional.ofNullable(dictItemRepository.findFirstByCollectionCodeAndValue(reqBody.getCollectionCode(),
                        reqBody.getValue()))
                        .orElse(new DictItemEntity());
        entity.setCollection(collectionEntity);
        entity.setValue(reqBody.getValue());
        entity.setShowName(reqBody.getShowName());
        entity.setItemDesc(reqBody.getItemDesc());
        entity.setSort(reqBody.getSort());
        dictItemRepository.saveAndFlush(entity);
    }

    public void deleteDictItem(long itemId) {
        dictItemRepository.deleteById(itemId);
    }

}
