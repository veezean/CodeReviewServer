package com.veezean.codereview.server.service;

import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.common.CommonConsts;
import com.veezean.codereview.server.common.CurrentUserHolder;
import com.veezean.codereview.server.entity.RoleEntity;
import com.veezean.codereview.server.entity.UserEntity;
import com.veezean.codereview.server.model.SaveRoleReqBody;
import com.veezean.codereview.server.monogo.MongoOperateHelper;
import com.veezean.codereview.server.repository.RoleRepository;
import com.veezean.codereview.server.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色信息管理服务
 *
 * @author Veezean
 * @since 2023/3/23
 */
@Service
@Slf4j
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Resource
    private MongoOperateHelper mongoOperateHelper;

    public List<RoleEntity> getRoles() {
        return roleRepository.findAll();
    }

    public List<RoleEntity> getRoleByIds(List<Long> roleIds) {
        return roleRepository.findAllByIdIn(roleIds);
    }

    public RoleEntity getRoleById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new CodeReviewException("角色信息不存在：" + roleId));
    }

    public void addRole(SaveRoleReqBody reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getRoleCode())
                || StringUtils.isEmpty(reqBody.getRoleName())
                || StringUtils.isEmpty(reqBody.getRoleDesc())
                || StringUtils.isEmpty(reqBody.getCanAccessPage())) {
            throw new CodeReviewException("请求参数不合法，信息缺失");
        }

        List<RoleEntity> roleEntities = roleRepository.findAllByRoleCode(reqBody.getRoleCode());
        if (CollectionUtils.isNotEmpty(roleEntities)) {
            throw new CodeReviewException("角色编码已存在，请修改后重新提交");
        }
        roleEntities = roleRepository.findAllByRoleName(reqBody.getRoleName());
        if (CollectionUtils.isNotEmpty(roleEntities)) {
            throw new CodeReviewException("角色名称已存在，请修改后重新提交");
        }

        RoleEntity entity = new RoleEntity();
        entity.setRoleCode(reqBody.getRoleCode());
        entity.setRoleName(reqBody.getRoleName());
        entity.setRoleDesc(reqBody.getRoleDesc());
        if ("ALL".equals(reqBody.getCanAccessPage())) {
            String menus = String.join(",", CommonConsts.ALL_MENUS);
            entity.setCanAccessPage(menus);
        } else {
            entity.setCanAccessPage(reqBody.getCanAccessPage());
        }

        roleRepository.save(entity);
    }

    @Transactional
    public void modifyRole(long roleId, SaveRoleReqBody reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getRoleCode())
                || StringUtils.isEmpty(reqBody.getRoleName())
                || StringUtils.isEmpty(reqBody.getCanAccessPage())
                || StringUtils.isEmpty(reqBody.getRoleDesc())) {
            throw new CodeReviewException("请求参数不合法，信息缺失");
        }

        roleRepository.findById(roleId)
                .ifPresent(roleEntity -> {
                    roleRepository.findAllByRoleCode(reqBody.getRoleCode())
                            .stream()
                            .filter(entity -> entity.getId() != roleId)
                            .findAny()
                            .ifPresent(entity -> {
                                throw new CodeReviewException("角色编码已存在，请修改后重新提交");
                            });
                    roleRepository.findAllByRoleName(reqBody.getRoleName())
                            .stream()
                            .filter(entity -> entity.getId() != roleId)
                            .findAny()
                            .ifPresent(entity -> {
                                throw new CodeReviewException("角色名称已存在，请修改后重新提交");
                            });
                    roleEntity.setRoleCode(reqBody.getRoleCode());
                    roleEntity.setRoleDesc(reqBody.getRoleDesc());
                    roleEntity.setRoleName(reqBody.getRoleName());
                    if ("ALL".equals(reqBody.getCanAccessPage())) {
                        String menus = String.join(",", CommonConsts.ALL_MENUS);
                        roleEntity.setCanAccessPage(menus);
                    } else {
                        roleEntity.setCanAccessPage(reqBody.getCanAccessPage());
                    }

                    roleRepository.save(roleEntity);
                });
    }

    @Transactional
    public void deleteRole(long roleId) {
        roleRepository.deleteById(roleId);
        deleteUserBindedRole(roleId);
    }

    @Transactional
    public void deleteRoles(List<Long> roleIds) {
        for (Long roleId : roleIds) {
            roleRepository.deleteById(roleId);
            deleteUserBindedRole(roleId);
        }
    }

    /**
     * 将指定角色从所有用户身上移除
     *
     * @param roleId
     */
    private void deleteUserBindedRole(long roleId) {
        mongoOperateHelper.removeIfExistinArrayFields("roles", roleId, UserEntity.class);
    }

    public List<String> getUserCanAccessPages() {
        return CurrentUserHolder.getCurrentUser().getRoles()
                .stream()
                .map(RoleEntity::getCanAccessPage)
                .filter(StringUtils::isNotEmpty)
                .flatMap(s -> Arrays.stream(s.split(",")))
                .distinct()
                .collect(Collectors.toList());
    }
}
