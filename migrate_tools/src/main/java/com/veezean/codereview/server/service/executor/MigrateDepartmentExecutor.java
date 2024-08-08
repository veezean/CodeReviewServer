package com.veezean.codereview.server.service.executor;

import com.veezean.codereview.server.monogo.entity.DepartmentEntity;
import com.veezean.codereview.server.monogo.repository.DepartmentRepository;
import com.veezean.codereview.server.mysql.entity.MySqlDepartmentEntity;
import com.veezean.codereview.server.mysql.repository.MySqlDepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2024/7/22
 */
@Service
@Slf4j
public class MigrateDepartmentExecutor implements IExecutor {

    @Resource
    private MySqlDepartmentRepository mySqlDepartmentRepository;
    @Resource
    private DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public void execute() {
        // 先清空已有的内容
        departmentRepository.deleteAll();
        log.info("————清空已有的MongoDB部门信息————");

        // 从MySQL中迁移数据
        List<DepartmentEntity> departmentEntities = mySqlDepartmentRepository.findAll()
                .stream()
                .map(mySqlDepartmentEntity -> {
                    DepartmentEntity entity = new DepartmentEntity();
                    entity.setId(mySqlDepartmentEntity.getId());
                    entity.setParentId(
                            Optional.ofNullable(mySqlDepartmentEntity.getParent())
                                    .map(MySqlDepartmentEntity::getId)
                                    .orElse(null));
                    entity.setName(mySqlDepartmentEntity.getName());
                    return entity;
                }).collect(Collectors.toList());
        departmentRepository.saveAll(departmentEntities);

        log.info("————部门信息迁移完成，共计完成{}条记录————", departmentEntities.size());
    }
}
