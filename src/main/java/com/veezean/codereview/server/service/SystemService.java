package com.veezean.codereview.server.service;

import com.veezean.codereview.server.entity.ColumnDefineEntity;
import com.veezean.codereview.server.model.NoticeBody;
import com.veezean.codereview.server.model.RecordColumns;
import com.veezean.codereview.server.repository.ColumnDefineRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.model.EruptDict;
import xyz.erupt.upms.model.EruptDictItem;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/5
 */
@Service
@Slf4j
public class SystemService {

    @Autowired
    private EruptDao eruptDao;

    @Autowired
    private ColumnDefineRepository columnDefineRepository;

    public RecordColumns getDefinedRecordColumns() {
        List<ColumnDefineEntity> columnDefineEntities = columnDefineRepository.findAll()
                .stream()
                .sorted((o1, o2) -> o1.getSortIndex() - o2.getSortIndex() > 0 ? 1 : -1)
                .collect(Collectors.toList());
        RecordColumns columns = new RecordColumns();
        columns.setColumns(columnDefineEntities);
        return columns;
    }

    public List<NoticeBody> getSystemNotice() {
        Map<String, Object> params = new HashMap<>();
        params.put("code", "SystemNotice");

        // 使用数据字典中SystemNotice编码对应的枚举值作为系统通知配置
        return Optional.ofNullable(eruptDao.queryEntityList(EruptDictItem.class, "eruptDict.code = :code", params))
                .orElse(new ArrayList<>())
                .stream()
                .map(eruptDictItem -> {
                    NoticeBody noticeBody = new NoticeBody();
                    noticeBody.setMsg(eruptDictItem.getRemark());
                    return noticeBody;
                })
                .collect(Collectors.toList());
    }
}
