package com.veezean.codereview.server.service;

import cn.hutool.core.bean.BeanUtil;
import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.entity.ColumnDefineEntity;
import com.veezean.codereview.server.model.SaveColumnDefineReqBody;
import com.veezean.codereview.server.repository.ColumnDefineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/24
 */
@Service
@Slf4j
public class ColumnDefineService {
    @Autowired
    private ColumnDefineRepository columnDefineRepository;

    public void createColumn(SaveColumnDefineReqBody reqBody) {
        ColumnDefineEntity columnDefineEntity = new ColumnDefineEntity();
        BeanUtil.copyProperties(reqBody, columnDefineEntity);
        columnDefineRepository.saveAndFlush(columnDefineEntity);
    }

    public void modifyColumn(long columnId, SaveColumnDefineReqBody reqBody) {
        ColumnDefineEntity existColumn =
                columnDefineRepository.findById(columnId).orElseThrow(() -> new CodeReviewException("字段不存在：" + columnId));
        BeanUtil.copyProperties(reqBody, existColumn);
        existColumn.setId(columnId);
        columnDefineRepository.saveAndFlush(existColumn);
    }

    public void deleteColumn(long columnId) {
        columnDefineRepository.deleteById(columnId);
    }

    public List<ColumnDefineEntity> queryColumns() {
        return columnDefineRepository.findAll();
    }
}
