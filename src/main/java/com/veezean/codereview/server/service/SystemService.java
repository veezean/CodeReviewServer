package com.veezean.codereview.server.service;

import com.veezean.codereview.server.entity.ColumnDefineEntity;
import com.veezean.codereview.server.model.NoticeBody;
import com.veezean.codereview.server.model.RecordColumns;
import com.veezean.codereview.server.repository.ColumnDefineRepository;
import com.veezean.codereview.server.service.collector.EnumDynamicCollectorManage;
import com.veezean.codereview.server.service.collector.IEnumDynamicCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.model.EruptDictItem;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2023/3/5
 */
@Service
@Slf4j
public class SystemService {

    private static final String SERVER_DYNAMIC_ENUM_PREFIX = "ServerDynamic_";

    @Autowired
    private EruptDao eruptDao;

    @Autowired
    private ColumnDefineRepository columnDefineRepository;

    public RecordColumns getDefinedRecordColumns() {
        List<ColumnDefineEntity> columnDefineEntities = columnDefineRepository.findAll()
                .stream()
                .sorted((o1, o2) -> o1.getSortIndex() - o2.getSortIndex() > 0 ? 1 : -1)
                .peek(columnDefineEntity -> Optional.ofNullable(columnDefineEntity.getComboBoxDictCode())
                        .ifPresent(eruptDict -> {
                            String eruptDictCode = eruptDict.getCode();
                            if (!eruptDictCode.startsWith(SERVER_DYNAMIC_ENUM_PREFIX)) {
                                Map<String, Object> params = new HashMap<>();
                                params.put("code", eruptDictCode);
                                // 拉取对应枚举值定义列表，返给客户端
                                List<String> enums =
                                        Optional.ofNullable(eruptDao.queryEntityList(EruptDictItem.class, "eruptDict" +
                                                ".code = :code", params))
                                                .orElse(new ArrayList<>())
                                                .stream()
                                                .map(EruptDictItem::getCode)
                                                .collect(Collectors.toList());
                                columnDefineEntity.setEnumValues(enums);
                                log.info("枚举值定义拉取完成，枚举值code:{}, 拉取枚举数据条数：{}", eruptDictCode, enums.size());
                            } else {
                                String dictCode = eruptDictCode.replaceFirst(SERVER_DYNAMIC_ENUM_PREFIX, "");
                                // 定制策略，拉取服务端用户列表
                                EnumDynamicCollectorManage.getCollector(dictCode).ifPresent(collector -> {
                                    List<String> enums = collector.doCollect();
                                    columnDefineEntity.setEnumValues(enums);
                                    log.info("动态定制枚举数据处理完成，名称：{}， 数据量：{}", dictCode, enums.size());
                                });
                            }
                        }))
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

    @Transactional
    public void initColumnDefines(RecordColumns recordColumns) {
        log.info("重置初始化评审字段配置信息：{}", recordColumns);
        columnDefineRepository.deleteAll();
        columnDefineRepository.saveAll(recordColumns.getColumns());
        log.info("评审字段配置信息重置完成");
    }
}
