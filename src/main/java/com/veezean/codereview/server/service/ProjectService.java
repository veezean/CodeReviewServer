package com.veezean.codereview.server.service;

import com.veezean.codereview.server.entity.ProjectEntity;
import com.veezean.codereview.server.model.ProjectBaseInfo;
import com.veezean.codereview.server.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.model.EruptOrg;
import xyz.erupt.upms.model.EruptUser;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2021/4/26
 */
@Service
@Slf4j
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Resource
    private EruptDao eruptDao;

    /**
     * 拉取用户所属部门下绑定的项目信息列表
     *
     * @param account 用户account
     * @return 项目列表
     */
    public List<ProjectBaseInfo> queryUserAccessableProjects(String account) {
        EruptUser eruptUser = eruptDao.queryEntity(EruptUser.class, "account=" + account);
        EruptOrg eruptOrg = eruptUser.getEruptOrg();
        if (eruptOrg == null) {
            return new ArrayList<>();
        }

        return eruptDao.queryEntityList(ProjectEntity.class, "department=" + eruptOrg.getId())
                .stream()
                .filter(Objects::nonNull)
                .map(projectEntity -> {
                    ProjectBaseInfo baseInfo = new ProjectBaseInfo();
//                    baseInfo.setProjectKey(projectEntity.getProjectKey());
                    baseInfo.setProjectId(projectEntity.getId());
                    baseInfo.setProjectName(projectEntity.getProjectName());
                    baseInfo.setProjectDesc(projectEntity.getProjectDesc());
                    return baseInfo;
                })
                .collect(Collectors.toList());
    }

}
