package com.veezean.codereview.server.service.executor;

import com.veezean.codereview.server.monogo.entity.UserEntity;
import com.veezean.codereview.server.monogo.repository.UserRepository;
import com.veezean.codereview.server.mysql.entity.MySqlRoleEntity;
import com.veezean.codereview.server.mysql.entity.MySqlUserRoleEntity;
import com.veezean.codereview.server.mysql.repository.MySqlUserRepository;
import com.veezean.codereview.server.mysql.repository.MySqlUserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2024/7/22
 */
@Service
@Slf4j
public class MigrateUserExecutor implements IExecutor {

    @Resource
    private MySqlUserRoleRepository mySqlUserRoleRepository;
    @Resource
    private MySqlUserRepository mySqlUserRepository;
    @Resource
    private UserRepository userRepository;

    @Override
    @Transactional
    public void execute() {
        // 先清空已有的内容
        userRepository.deleteAll();
        log.info("————清空已有的MongoDB用户信息————");

        Map<Long, List<MySqlUserRoleEntity>> userRoles = mySqlUserRoleRepository.findAll().stream()
                .collect(Collectors.groupingBy(mySqlUserRoleEntity -> mySqlUserRoleEntity.getUser().getId()));

        // 从MySQL中迁移数据
        List<UserEntity> userEntities = mySqlUserRepository.findAll()
                .stream()
                .map(mySqlUserEntity -> {
                    UserEntity entity = new UserEntity();
                    entity.setId(mySqlUserEntity.getId());
                    entity.setAccount(mySqlUserEntity.getAccount());
                    entity.setName(mySqlUserEntity.getName());
                    entity.setPassword(mySqlUserEntity.getPassword());
                    entity.setPhoneNumber(mySqlUserEntity.getPhoneNumber());
                    entity.setDepartmentId(mySqlUserEntity.getDepartment().getId());

                    List<MySqlUserRoleEntity> roleEntities = userRoles.get(mySqlUserEntity.getId());
                    if (CollectionUtils.isNotEmpty(roleEntities)) {
                        List<Long> roleIds =
                                roleEntities.stream()
                                        .map(MySqlUserRoleEntity::getRole)
                                        .map(MySqlRoleEntity::getId)
                                        .collect(Collectors.toList());
                        entity.setRoles(roleIds);
                    } else {
                        entity.setRoles(new ArrayList<>());
                    }

                    return entity;
                }).collect(Collectors.toList());
        userRepository.saveAll(userEntities);

        log.info("————用户信息迁移完成，共计完成{}条记录————", userEntities.size());
    }
}
