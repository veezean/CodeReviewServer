package com.veezean.codereview.server.service.executor;

import cn.hutool.core.bean.BeanUtil;
import com.veezean.codereview.server.monogo.entity.ColumnDefineEntity;
import com.veezean.codereview.server.monogo.repository.ColumnDefineRepository;
import com.veezean.codereview.server.mysql.repository.MySqlColumnDefineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2024/7/22
 */
@Service
@Slf4j
public class MigrateColumnFieldExecutor implements IExecutor {

    @Resource
    private MySqlColumnDefineRepository mySqlColumnDefineRepository;
    @Resource
    private ColumnDefineRepository columnDefineRepository;

    @Override
    @Transactional
    public void execute() {
        // 先清空已有的内容
        columnDefineRepository.deleteAll();
        log.info("————清空已有的MongoDB字段定义信息————");

        // 从MySQL中迁移数据
        List<ColumnDefineEntity> columnDefineEntities = mySqlColumnDefineRepository.findAll()
                .stream()
                .map(mySqlColumnDefineEntity -> {
                    ColumnDefineEntity entity = new ColumnDefineEntity();
                    BeanUtil.copyProperties(mySqlColumnDefineEntity, entity);
                    entity.setId(mySqlColumnDefineEntity.getId());
                    return entity;
                }).collect(Collectors.toList());
        columnDefineRepository.saveAll(columnDefineEntities);

        log.info("————字段定义信息迁移完成，共计完成{}条记录————", columnDefineEntities.size());
    }
}
