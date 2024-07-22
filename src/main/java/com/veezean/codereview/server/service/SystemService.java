package com.veezean.codereview.server.service;

import com.veezean.codereview.server.model.RecordColumns;
import com.veezean.codereview.server.repository.ColumnDefineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统配置管理服务
 *
 * @author Veezean
 * @since 2023/3/5
 */
@Service
@Slf4j
public class SystemService {
    @Autowired
    private ColumnDefineRepository columnDefineRepository;

    @Transactional
    public void initColumnDefines(RecordColumns recordColumns) {
        log.info("重置初始化评审字段配置信息：{}", recordColumns);
        columnDefineRepository.deleteAll();
        columnDefineRepository.saveAll(recordColumns.getColumns());
        log.info("评审字段配置信息重置完成");
    }
}
