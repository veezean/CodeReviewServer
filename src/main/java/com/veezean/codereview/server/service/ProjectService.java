package com.veezean.codereview.server.service;

import com.veezean.codereview.server.common.CurrentUserHolder;
import com.veezean.codereview.server.entity.ProjectEntity;
import com.veezean.codereview.server.model.ProjectBaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.model.EruptOrg;
import xyz.erupt.upms.model.EruptUser;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    @Resource
    private EruptDao eruptDao;

    /**
     * 拉取用户所属部门下绑定的项目信息列表
     *
     * @return 项目列表
     */
    public List<ProjectBaseInfo> getMyProjects() {
        return Optional.ofNullable(CurrentUserHolder.getCurrentUser())
                .map(EruptUser::getEruptOrg)
                .map(EruptOrg::getId)
                .map(orgId -> eruptDao.queryEntityList(ProjectEntity.class, "department=" + orgId)
                        .stream()
                        .filter(Objects::nonNull)
                        .map(projectEntity -> {
                            ProjectBaseInfo baseInfo = new ProjectBaseInfo();
                            baseInfo.setProjectId(projectEntity.getId());
                            baseInfo.setProjectName(projectEntity.getProjectName());
                            return baseInfo;
                        })
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }
}
