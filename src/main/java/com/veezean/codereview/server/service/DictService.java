package com.veezean.codereview.server.service;

import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.entity.DictCollectionEntity;
import com.veezean.codereview.server.entity.DictItemEntity;
import com.veezean.codereview.server.model.SaveDictCollectionReqBody;
import com.veezean.codereview.server.model.SaveDictItemReqBody;
import com.veezean.codereview.server.repository.DictCollectionRepository;
import com.veezean.codereview.server.repository.DictItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
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
        if (dictCollectionEntity == null) {
            throw new CodeReviewException("字典集已经存在：" + reqBody.getCode());
        }
        DictCollectionEntity entity = new DictCollectionEntity();
        entity.setCode(reqBody.getCode());
        entity.setName(reqBody.getName());
        entity.setDictDesc(reqBody.getDictDesc());
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
        if (existEntity != null) {
            throw new CodeReviewException("字典集不存在：" + reqBody.getCode());
        }
        existEntity.setCode(reqBody.getCode());
        existEntity.setName(reqBody.getName());
        existEntity.setDictDesc(reqBody.getDictDesc());
        dictCollectionRepository.saveAndFlush(existEntity);
    }

    public void deleteDictCollection(String collectionCode) {
        Optional.ofNullable(collectionCode)
                .filter(StringUtils::isNotEmpty)
                .ifPresent(s -> dictCollectionRepository.deleteAllByCode(s));
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

    public List<DictItemEntity> queryDictItemsByCollectionCode(String collectionCode) {
        return dictItemRepository.findAllByCollectionCode(collectionCode)
                .stream()
                .sorted((o1, o2) -> o1.getSort() - o2.getSort() > 0 ? 1 : -1)
                .collect(Collectors.toList());
    }

    public void createOrModifyDictItem(SaveDictItemReqBody reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getCollectionCode())
                || StringUtils.isEmpty(reqBody.getItemKey())
                || StringUtils.isEmpty(reqBody.getItemValue())
        ) {
            throw new CodeReviewException("参数不合法");
        }
        DictCollectionEntity collectionEntity = dictCollectionRepository.queryFirstByCode(reqBody.getCollectionCode());
        if (collectionEntity == null) {
            throw new CodeReviewException("字典集不存在：" + reqBody.getCollectionCode());
        }
        DictItemEntity entity =
                Optional.ofNullable(dictItemRepository.findFirstByCollectionAndItemKey(reqBody.getCollectionCode(),
                        reqBody.getItemKey()))
                        .orElse(new DictItemEntity());
        entity.setCollection(collectionEntity);
        entity.setItemKey(reqBody.getItemKey());
        entity.setItemValue(reqBody.getItemValue());
        entity.setItemDesc(reqBody.getItemDesc());
        entity.setSort(reqBody.getSort());
        dictItemRepository.saveAndFlush(entity);
    }

    public void deleteDictItem(long itemId) {
        dictItemRepository.deleteById(itemId);
    }

}
