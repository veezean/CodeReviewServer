package com.veezean.codereview.server.service.executor;

import com.veezean.codereview.server.monogo.entity.RoleEntity;
import com.veezean.codereview.server.monogo.repository.RoleRepository;
import com.veezean.codereview.server.mysql.repository.MySqlRoleRepository;
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
public class MigrateRoleExecutor implements IExecutor {
    @Resource
    private MySqlRoleRepository mySqlRoleRepository;
    @Resource
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void execute() {
        // 先清空已有的内容
        roleRepository.deleteAll();
        log.info("————清空已有的MongoDB角色信息————");

        // 从MySQL中迁移数据
        List<RoleEntity> roleEntities = mySqlRoleRepository.findAll()
                .stream()
                .map(mySqlRoleEntity -> {
                    RoleEntity entity = new RoleEntity();
                    entity.setId(mySqlRoleEntity.getId());
                    entity.setRoleCode(mySqlRoleEntity.getRoleCode());
                    entity.setRoleName(mySqlRoleEntity.getRoleName());
                    entity.setRoleDesc(mySqlRoleEntity.getRoleDesc());
                    entity.setCanAccessPage(mySqlRoleEntity.getCanAccessPage());
                    return entity;
                }).collect(Collectors.toList());
        roleRepository.saveAll(roleEntities);

        log.info("————角色信息迁移完成，共计完成{}条记录————", roleEntities.size());
    }
}
