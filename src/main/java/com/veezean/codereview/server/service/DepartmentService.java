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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * <类功能简要描述>
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
     * @param deptId
     * @return
     */
    public DepartmentEntity getByDeptId(Long deptId) {
        return Optional.ofNullable(deptId)
                .map(departmentId -> departmentRepository.findById(departmentId))
                .map(Optional::get)
                .orElseThrow(() -> new CodeReviewException("部门信息不存在：" + deptId));
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
                    tree.setParentId(Optional.ofNullable(departmentEntity.getParent()).map(DepartmentEntity::getId).orElse(-1L));
                    tree.setName(departmentEntity.getName());
                    tree.putExtra("label", departmentEntity.getName());
                    tree.putExtra("value", departmentEntity.getId());
                });
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
        DepartmentEntity parentDeptEntity = null;
        if (reqBody.getParentId() != null) {
            parentDeptEntity = departmentRepository.findById(reqBody.getParentId())
                    .orElseThrow(() -> new CodeReviewException("指定的父部门节点不存在：" + reqBody.getParentId()));
        }
        DepartmentEntity entity = new DepartmentEntity();
        entity.setName(reqBody.getName());
        entity.setParent(parentDeptEntity);
        departmentRepository.saveAndFlush(entity);
    }

    @Transactional
    public void modifyDept(long deptId, SaveDeptReqBody reqBody) {
        if (reqBody == null || StringUtils.isEmpty(reqBody.getName())) {
            throw new CodeReviewException("修改部门失败，信息缺失");
        }
        if (reqBody.getParentId() == deptId) {
            throw new CodeReviewException("父部门设定错误，不允许父部门为自身节点");
        }
        DepartmentEntity parentDeptEntity = null;
        if (reqBody.getParentId() != null) {
            parentDeptEntity = departmentRepository.findById(reqBody.getParentId())
                    .orElseThrow(() -> new CodeReviewException("指定的父部门节点不存在：" + reqBody.getParentId()));
        }
        DepartmentEntity entity = departmentRepository.findById(deptId).orElseThrow(() -> new CodeReviewException(
                "部门不存在：" + deptId));
        entity.setName(reqBody.getName());
        if (parentDeptEntity != null) {
            entity.setParent(parentDeptEntity);
        }
        departmentRepository.saveAndFlush(entity);
    }
}
