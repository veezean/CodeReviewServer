package com.veezean.codereview.server.service.executor;

import com.veezean.codereview.server.monogo.entity.ProjectEntity;
import com.veezean.codereview.server.monogo.repository.ProjectRepository;
import com.veezean.codereview.server.mysql.entity.MySqlUserProjectEntity;
import com.veezean.codereview.server.mysql.repository.MySqlProjectRepository;
import com.veezean.codereview.server.mysql.repository.MySqlUserProjectRepository;
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
public class MigrateProjectExecutor implements IExecutor {

    @Resource
    private MySqlUserProjectRepository mySqlUserProjectRepository;
    @Resource
    private MySqlProjectRepository mySqlProjectRepository;
    @Resource
    private ProjectRepository projectRepository;

    @Override
    @Transactional
    public void execute() {

        // 先清空已有的内容
        projectRepository.deleteAll();
        log.info("————清空已有的MongoDB项目信息————");

        Map<Long, List<MySqlUserProjectEntity>> projectMembers = mySqlUserProjectRepository.findAll().stream()
                .collect(Collectors.groupingBy(MySqlUserProjectEntity::getProjectId));

        // 从MySQL中迁移数据
        List<ProjectEntity> projectEntities = mySqlProjectRepository.findAll()
                .stream()
                .map(mySqlProjectEntity -> {
                    ProjectEntity entity = new ProjectEntity();
                    entity.setId(mySqlProjectEntity.getId());
                    entity.setProjectName(mySqlProjectEntity.getProjectName());
                    entity.setProjectDesc(mySqlProjectEntity.getProjectDesc());
                    entity.setDepartmentId(mySqlProjectEntity.getDepartment().getId());

                    List<MySqlUserProjectEntity> members = projectMembers.get(mySqlProjectEntity.getId());
                    if (CollectionUtils.isNotEmpty(members)) {
                        List<String> memberAccounts =
                                members.stream()
                                        .map(MySqlUserProjectEntity::getAccount)
                                        .collect(Collectors.toList());
                        entity.setMemberAccounts(memberAccounts);
                    } else {
                        entity.setMemberAccounts(new ArrayList<>());
                    }

                    return entity;
                }).collect(Collectors.toList());
        projectRepository.saveAll(projectEntities);

        log.info("————项目信息迁移完成，共计完成{}条记录————", projectEntities.size());
    }
}
