package com.veezean.codereview.server.service;

import com.veezean.codereview.server.entity.ProjectEntity;
import com.veezean.codereview.server.entity.UserProjectBindingsEntity;
import com.veezean.codereview.server.repository.ProjectRepository;
import com.veezean.codereview.server.repository.UserProjectBindingsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    @Autowired
    private UserProjectBindingsRepository userProjectBindingsRepository;

    /**
     * 拉取用户名下绑定的项目信息
     *
     * @param userId userId
     * @return
     */
    public List<ProjectEntity> queryUserBindedProjects(String userId) {
        return userProjectBindingsRepository.findAllByUserId(userId)
                .stream()
                .map(UserProjectBindingsEntity::getProjectKey)
                .distinct()
                .map(s -> projectRepository.findByProjectKey(s))
                .collect(Collectors.toList());
    }
}
