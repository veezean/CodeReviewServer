package com.veezean.codereview.server.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.common.InputTypeDefine;
import com.veezean.codereview.server.entity.ColumnDefineEntity;
import com.veezean.codereview.server.model.RecordColumns;
import com.veezean.codereview.server.model.ValuePair;
import com.veezean.codereview.server.repository.ColumnDefineRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    private DictService dictService;

    public void createOrModifyColumn(long columnId, ColumnDefineEntity reqBody) {
        reqValidate(reqBody);
        ColumnDefineEntity existColumn =
                columnDefineRepository.findById(columnId).orElse(new ColumnDefineEntity());
        BeanUtil.copyProperties(reqBody, existColumn);
        columnDefineRepository.saveAndFlush(existColumn);
    }

    private void reqValidate(ColumnDefineEntity reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getColumnCode())
                || StringUtils.isEmpty(reqBody.getShowName())
                || StringUtils.isEmpty(reqBody.getInputType())
        ) {
            throw new CodeReviewException("必填参数校验失败，请检查");
        }
        if (InputTypeDefine.COMBO_BOX.getValue().equals(reqBody.getInputType())) {
            if (StringUtils.isEmpty(reqBody.getDictCollectionCode())) {
                throw new CodeReviewException("输入类型为下拉选择框时，必须绑定对应的下拉字典集");
            }
        }
        if (reqBody.isSupportInExcel()) {
            if (reqBody.getExcelColumnWidth() < 1 || reqBody.getExcelColumnWidth() > 1000) {
                throw new CodeReviewException("导出到Excel列宽不合法，请重新输入1~999之间的数字");
            }
        }

    }

    @Transactional
    public void deleteColumn(long columnId) {
        ColumnDefineEntity columnDefineEntity = columnDefineRepository.findById(columnId).orElse(null);
        if (columnDefineEntity != null) {
            if (columnDefineEntity.isSystemInitialization()) {
                throw new CodeReviewException("系统预置字段，不允许删除");
            }
            columnDefineRepository.deleteById(columnId);
        }
    }

    @Transactional
    public void deleteColumns(List<Long> columnIds) {
        boolean containsSystemField = columnDefineRepository.findAllById(columnIds).stream()
                .anyMatch(ColumnDefineEntity::isSystemInitialization);
        if (containsSystemField) {
            throw new CodeReviewException("包含系统预置字段，不允许删除");
        }
        columnDefineRepository.deleteAllById(columnIds);
    }

    public ColumnDefineEntity queryColumn(long columnId) {
        return columnDefineRepository.findById(columnId).orElseThrow(() -> new CodeReviewException("数据不存在"));
    }

    public ColumnDefineEntity queryColumnByCode(String code) {
        return columnDefineRepository.findFirstByColumnCode(code);
    }

    public Stream<ColumnDefineEntity> queryColumns() {
        return columnDefineRepository.findAll().stream()
                .sorted(Comparator.comparingInt(ColumnDefineEntity::getSortIndex))
                .peek(columnDefineEntity -> {
                    if (InputTypeDefine.COMBO_BOX.getValue().equals(columnDefineEntity.getInputType())
                            && StringUtils.isNotEmpty(columnDefineEntity.getDictCollectionCode())) {
                        List<ValuePair> items =
                                dictService.queryListItemsByCollectionCode(columnDefineEntity.getDictCollectionCode());
                        columnDefineEntity.setEnumValues(items);
                    }
                });
    }

    public List<ValuePair> queryFieldDictItems(String fieldCode) {
        ColumnDefineEntity entity = columnDefineRepository.findFirstByColumnCode(fieldCode);
        if (entity != null && StringUtils.isNotEmpty(entity.getDictCollectionCode())) {
            return dictService.queryListItemsByCollectionCode(entity.getDictCollectionCode());
        }
        return new ArrayList<>();
    }

    public String getJsonContent() {
        RecordColumns columns = new RecordColumns();
        columns.setColumns(queryColumns().collect(Collectors.toList()));
        return JSONUtil.formatJsonStr(JSON.toJSONString(columns));
    }
}
