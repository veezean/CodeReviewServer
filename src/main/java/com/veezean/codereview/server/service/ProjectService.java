package com.veezean.codereview.server.service;

import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.common.CurrentUserHolder;
import com.veezean.codereview.server.entity.DepartmentEntity;
import com.veezean.codereview.server.entity.ProjectEntity;
import com.veezean.codereview.server.model.ProjectBaseInfo;
import com.veezean.codereview.server.model.SaveProjectReqBody;
import com.veezean.codereview.server.model.UserDetail;
import com.veezean.codereview.server.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2021/4/26
 */
@Service
@Slf4j
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private DepartmentService departmentService;
//    @Autowired
//    private CommentRepository commentRepository;

    public void createProject(SaveProjectReqBody reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getProjectName())
                || reqBody.getDepartmentId() <= 0
        ) {
            throw new CodeReviewException("请求参数不合法");
        }
        DepartmentEntity departmentEntity = departmentService.getByDeptId(reqBody.getDepartmentId());
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setDepartment(departmentEntity);
        projectEntity.setProjectName(reqBody.getProjectName());
        projectEntity.setProjectDesc(reqBody.getProjectDesc());
        projectRepository.saveAndFlush(projectEntity);
    }

    public void modifyProject(long projectId, SaveProjectReqBody reqBody) {
        if (projectId <= 0
                || reqBody == null
                || StringUtils.isEmpty(reqBody.getProjectName())
                || reqBody.getDepartmentId() <= 0
        ) {
            throw new CodeReviewException("请求参数不合法");
        }
        ProjectEntity existProjectEntity =
                projectRepository.findById(projectId).orElseThrow(() -> new CodeReviewException("项目不存在：" + projectId));
        DepartmentEntity departmentEntity = departmentService.getByDeptId(reqBody.getDepartmentId());
        existProjectEntity.setDepartment(departmentEntity);
        existProjectEntity.setProjectName(reqBody.getProjectName());
        existProjectEntity.setProjectDesc(reqBody.getProjectDesc());
        projectRepository.saveAndFlush(existProjectEntity);
    }

    @Transactional
    public void deleteProject(long projectId) {
//        List<CommentEntity> commentEntities = commentRepository.findAllByProjectId(projectId);
//        if (CollectionUtils.isNotEmpty(commentEntities)) {
//            throw new CodeReviewException("该项目下存在评审意见，请先删除对应评审意见。");
//        }
        projectRepository.deleteById(projectId);
    }

    @Transactional
    public void deleteProjects(List<Long> projectIds) {
        for (long projectId : projectIds) {
//            List<CommentEntity> commentEntities = commentRepository.findAllByProjectId(projectId);
//            if (CollectionUtils.isNotEmpty(commentEntities)) {
//                throw new CodeReviewException("所选项目下存在评审意见，请先删除对应评审意见。");
//            }
            projectRepository.deleteById(projectId);
        }
    }

    public ProjectEntity queryProject(long projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> new CodeReviewException("项目不存在：" + projectId));
    }

    public List<ProjectEntity> queryProjectInDept(long deptId) {
        if (deptId == 0L) {
            return projectRepository.findAll();
        }
        return projectRepository.findAllByDepartmentId(deptId);
    }

    /**
     * 拉取用户所属部门下绑定的项目信息列表
     *
     * @return 项目列表
     */
    public List<ProjectBaseInfo> getMyProjects() {
        // 目前不限制，查询所有项目列表
        return Optional.ofNullable(queryProjectInDept(0L))
//                .map(UserDetail::getDepartment)
//                .map(DepartmentEntity::getId)
//                .map(userDetail -> )
                .orElse(new ArrayList<>())
                .stream()
                .map(projectEntity -> {
                    ProjectBaseInfo baseInfo = new ProjectBaseInfo();
                    baseInfo.setProjectId(projectEntity.getId());
                    baseInfo.setProjectName(projectEntity.getProjectName());
                    return baseInfo;
                })
                .collect(Collectors.toList());
    }
}
