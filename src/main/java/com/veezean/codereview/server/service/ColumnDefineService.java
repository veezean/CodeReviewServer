package com.veezean.codereview.server.service;

import cn.hutool.core.bean.BeanUtil;
import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.common.InputTypeDefine;
import com.veezean.codereview.server.entity.ColumnDefineEntity;
import com.veezean.codereview.server.model.SaveColumnDefineReqBody;
import com.veezean.codereview.server.repository.ColumnDefineRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public void createOrModifyColumn(long columnId, SaveColumnDefineReqBody reqBody) {
        reqValidate(reqBody);
        ColumnDefineEntity existColumn =
                columnDefineRepository.findById(columnId).orElse(new ColumnDefineEntity());
        BeanUtil.copyProperties(reqBody, existColumn);
        columnDefineRepository.saveAndFlush(existColumn);
    }

    private void reqValidate(SaveColumnDefineReqBody reqBody) {
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
        columnDefineRepository.deleteById(columnId);
    }

    @Transactional
    public void deleteColumns(List<Long> columnIds) {
        for (Long columnId : columnIds) {
            columnDefineRepository.deleteById(columnId);
        }
    }

    public ColumnDefineEntity queryColumn(long columnId) {
        return columnDefineRepository.findById(columnId).orElseThrow(() -> new CodeReviewException("数据不存在"));
    }

    public List<ColumnDefineEntity> queryColumns() {
        return columnDefineRepository.findAll().stream().sorted((o1, o2) -> o1.getSortIndex() - o2.getSortIndex() > 0 ? 1 : -1)
                .collect(Collectors.toList());
    }
}
