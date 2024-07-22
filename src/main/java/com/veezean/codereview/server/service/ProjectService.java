package com.veezean.codereview.server.service;

import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.common.CurrentUserHolder;
import com.veezean.codereview.server.entity.DepartmentEntity;
import com.veezean.codereview.server.entity.ProjectEntity;
import com.veezean.codereview.server.model.ProjectBaseInfo;
import com.veezean.codereview.server.model.SaveProjectReqBody;
import com.veezean.codereview.server.model.UserProjectBindReqBody;
import com.veezean.codereview.server.monogo.MongoOperateHelper;
import com.veezean.codereview.server.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 项目管理服务
 *
 * @author Veezean
 * @since 2021/4/26
 */
@Service
@Slf4j
public class ProjectService {
    @Resource
    private ProjectRepository projectRepository;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private MongoOperateHelper mongoOperateHelper;

    public void createProject(SaveProjectReqBody reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getProjectName())
                || reqBody.getDepartmentId() <= 0
        ) {
            throw new CodeReviewException("请求参数不合法");
        }
//        DepartmentEntity departmentEntity = departmentService.getByDeptId(reqBody.getDepartmentId());
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setDepartmentId(reqBody.getDepartmentId());
        projectEntity.setProjectName(reqBody.getProjectName());
        projectEntity.setProjectDesc(reqBody.getProjectDesc());
        projectEntity.setMemberAccounts(new ArrayList<>());
        projectRepository.save(projectEntity);
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
//        DepartmentEntity departmentEntity = departmentService.getByDeptId(reqBody.getDepartmentId());
//        existProjectEntity.setDepartment(departmentEntity);
        existProjectEntity.setDepartmentId(reqBody.getDepartmentId());
        existProjectEntity.setProjectName(reqBody.getProjectName());
        existProjectEntity.setProjectDesc(reqBody.getProjectDesc());
        projectRepository.save(existProjectEntity);
    }

    @Transactional
    public void deleteProject(long projectId) {
        projectRepository.deleteById(projectId);
//        // 同步删除项目与用户之间的绑定关系
//        userProjectRepository.deleteAllByProjectId(projectId);
    }

    @Transactional
    public void deleteProjects(List<Long> projectIds) {
        for (long projectId : projectIds) {
            projectRepository.deleteById(projectId);
//            // 同步删除项目与用户之间的绑定关系
//            userProjectRepository.deleteAllByProjectId(projectId);
        }
    }

    public ProjectEntity queryProject(long projectId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new CodeReviewException("项目不存在：" + projectId));

        // 补齐部门信息，展示需要使用
        DepartmentEntity departmentEntity = departmentService.getByDeptId(projectEntity.getDepartmentId());
        projectEntity.setDepartment(departmentEntity);

        return projectEntity;
    }

    /**
     * 批量添加用户到指定项目中
     *
     * @param reqBody
     */
    @Transactional
    public void saveProjectMembers(UserProjectBindReqBody reqBody) {
        List<String> accounts = reqBody.getAccounts();
        if (accounts == null) {
            accounts = new ArrayList<>();
        }
        long projectId = reqBody.getProjectId();
        if (projectId <= 0L) {
            throw new CodeReviewException("项目参数非法");
        }

        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new CodeReviewException("项目不存在"));
        projectEntity.setMemberAccounts(accounts);

        projectRepository.save(projectEntity);
    }

    /**
     * 查询项目下面绑定的用户列表
     *
     * @param projectId 项目ID
     * @return
     */
    public List<String> queryProjectMembers(long projectId) {
        if (projectId <= 0L) {
            throw new CodeReviewException("项目参数非法");
        }

        return projectRepository.findById(projectId)
                .map(ProjectEntity::getMemberAccounts)
                .orElse(new ArrayList<>());
    }

    /**
     * 查询指定部门下有权访问的项目列表
     *
     * @param deptId 部门ID
     * @return
     */
    public List<ProjectEntity> queryAccessableProjectInDept(String deptId) {
        Long deptIdValue = null;
        try {
            deptIdValue = Long.parseLong(deptId);
        } catch (Exception e) {
            // do not care this exception...
        }

        // 拉取当前用户绑定的项目，然后进行过滤
        String account = CurrentUserHolder.getCurrentUser().getAccount();
        List<Long> bindedProjectIds = mongoOperateHelper.queryAllByArrayField("memberAccounts", account,
                        ProjectEntity.class)
                .stream()
                .map(ProjectEntity::getId)
                .distinct()
                .collect(Collectors.toList());
//
//
//        List<Long> bindedProjectIds = userProjectRepository.findAllByAccount(account)
//                .stream()
//                .map(UserProjectEntity::getProjectId)
//                .distinct()
//                .collect(Collectors.toList());

        // 获取输入部门以及所有子部门ID列表
        List<Long> allChildrenDepts = departmentService.getAllChildrenDepts(deptIdValue);

        // 如果是管理角色，则允许查看所有项目信息
        boolean adminRole = CurrentUserHolder.isAdminRole();

        // 所有部门信息
        Map<Long, DepartmentEntity> allDepts = departmentService.queryAllDepts();

        // 过滤指定部门下，用户绑定的项目
        return mongoOperateHelper.queryAllByArrayFieldIn("departmentId", allChildrenDepts, ProjectEntity.class)
                .stream()
                .filter(projectEntity -> adminRole || bindedProjectIds.contains(projectEntity.getId()))
                .peek(projectEntity -> {
                    // 补齐部门信息，界面需要使用
                    projectEntity.setDepartment(allDepts.get(projectEntity.getDepartmentId()));
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取指定用户绑定的项目信息
     *
     * @return
     */
    public List<ProjectBaseInfo> getUserAccessableProjects() {
        List<ProjectEntity> projectEntities;
        if (CurrentUserHolder.isAdminRole()) {
            projectEntities = projectRepository.findAll();
        } else {
            String account = CurrentUserHolder.getCurrentUser().getAccount();
            List<Long> projectIds = mongoOperateHelper.queryAllByArrayField("memberAccounts", account,
                            ProjectEntity.class)
                    .stream()
                    .map(ProjectEntity::getId)
                    .distinct()
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(projectIds)) {
                projectEntities = new ArrayList<>();
            } else {
                projectEntities = projectRepository.findAllByIdIn(projectIds);
            }
        }

        return projectEntities
                .stream()
                .map(projectEntity -> {
                    ProjectBaseInfo baseInfo = new ProjectBaseInfo();
                    baseInfo.setProjectId(projectEntity.getId());
                    baseInfo.setProjectName(projectEntity.getProjectName());
                    return baseInfo;
                })
                .collect(Collectors.toList());
    }

    public List<String> getUserAccessableProjectIds() {
        return getUserAccessableProjects()
                .stream()
                .map(ProjectBaseInfo::getProjectId)
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

    public long count() {
        return projectRepository.count();
    }
}
