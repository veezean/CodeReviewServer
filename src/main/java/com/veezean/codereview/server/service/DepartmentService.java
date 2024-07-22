package com.veezean.codereview.server.service;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.entity.DepartmentEntity;
import com.veezean.codereview.server.entity.UserEntity;
import com.veezean.codereview.server.model.SaveDeptReqBody;
import com.veezean.codereview.server.repository.DepartmentRepository;
import com.veezean.codereview.server.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 部门管理服务
 *
 * @author Veezean
 * @since 2023/3/23
 */
@Service
@Slf4j
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 获取指定部门详情信息
     *
     * @param departmentId
     * @return
     */
    public DepartmentEntity getByDeptId(Long departmentId) {
        return Optional.ofNullable(departmentId)
                .map(deptId -> departmentRepository.findById(deptId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .orElseThrow(() -> new CodeReviewException("部门信息不存在：" + departmentId));
    }

    /**
     * 生成部门树
     *
     * @return
     */
    public List<Tree<Long>> getDeptTree(Long topDeptId) {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        return TreeUtil.build(departmentEntities, Optional.ofNullable(topDeptId).orElse(-1L), new TreeNodeConfig(),
                (departmentEntity, tree) -> {
                    tree.setId(departmentEntity.getId());
                    tree.setParentId(Optional.ofNullable(departmentEntity.getParentId()).orElse(-1L));
                    tree.setName(departmentEntity.getName());
                    tree.putExtra("label", departmentEntity.getName());
                    tree.putExtra("value", departmentEntity.getId());
                });
    }

    /**
     * 获取指定部门及其所有子节点ID信息（含自身）
     *
     * @param topDeptId
     * @return
     */
    public List<Long> getAllChildrenDepts(Long topDeptId) {
        List<Long> deptIds = new ArrayList<>();
        if (topDeptId != null && topDeptId != 0L) {
            deptIds.add(topDeptId);
        }
        List<Tree<Long>> deptTree = getDeptTree(topDeptId);
        collectAllChildrenDepts(deptIds, deptTree);
        return deptIds.stream().distinct().collect(Collectors.toList());
    }

    private void collectAllChildrenDepts(List<Long> deptIds, List<Tree<Long>> deptTree) {
        if (CollectionUtils.isEmpty(deptTree)) {
            return;
        }
        for (Tree<Long> tree : deptTree) {
            deptIds.add(tree.getId());
            collectAllChildrenDepts(deptIds, tree.getChildren());
        }
    }

    @Transactional
    public void deleteDept(long deptId) {
        List<UserEntity> userEntities = userRepository.findAllByDepartmentId(deptId);
        if (!userEntities.isEmpty()) {
            throw new CodeReviewException("当前部门下存在绑定的用户，不允许删除");
        }
        departmentRepository.deleteById(deptId);
    }

    @Transactional
    public void addDept(SaveDeptReqBody reqBody) {
        if (reqBody == null || StringUtils.isEmpty(reqBody.getName())) {
            throw new CodeReviewException("创建部门失败，信息缺失");
        }

        DepartmentEntity entity = new DepartmentEntity();
        entity.setName(reqBody.getName());

        // 校验父节点合法性，然后设置父节点
        DepartmentEntity parentDeptEntity = null;
        if (reqBody.getParentId() != null) {
            parentDeptEntity = departmentRepository.findById(reqBody.getParentId())
                    .orElseThrow(() -> new CodeReviewException("指定的父部门节点不存在：" + reqBody.getParentId()));
            entity.setParentId(parentDeptEntity.getId());
        }

        // 写入DB
        departmentRepository.save(entity);
    }

    @Transactional
    public void modifyDept(long deptId, SaveDeptReqBody reqBody) {
        if (reqBody == null || StringUtils.isEmpty(reqBody.getName())) {
            throw new CodeReviewException("修改部门失败，信息缺失");
        }
        // 校验父节点合法性
        DepartmentEntity parentDeptEntity = null;
        if (reqBody.getParentId() != null) {
            if (reqBody.getParentId() == deptId) {
                throw new CodeReviewException("父部门设定错误，不允许父部门为自身节点");
            }
            parentDeptEntity = departmentRepository.findById(reqBody.getParentId())
                    .orElseThrow(() -> new CodeReviewException("指定的父部门节点不存在：" + reqBody.getParentId()));
        }
        // 校验目标节点合法性
        DepartmentEntity entity = departmentRepository.findById(deptId)
                .orElseThrow(() -> new CodeReviewException("部门不存在：" + deptId));

        // 执行更新操作
        entity.setName(reqBody.getName());
        if (parentDeptEntity != null) {
            entity.setParentId(parentDeptEntity.getId());
        }
        departmentRepository.save(entity);
    }

    public Map<Long, DepartmentEntity> queryAllDepts() {
        return departmentRepository.findAll().stream()
                .collect(Collectors.toMap(DepartmentEntity::getId, entity -> entity, (t, t2) -> t));
    }
}
