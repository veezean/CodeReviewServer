package com.veezean.codereview.server.service;

import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.entity.RoleEntity;
import com.veezean.codereview.server.entity.UserEntity;
import com.veezean.codereview.server.entity.UserRoleEntity;
import com.veezean.codereview.server.model.SaveRoleReqBody;
import com.veezean.codereview.server.repository.RoleRepository;
import com.veezean.codereview.server.repository.UserRepository;
import com.veezean.codereview.server.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/23
 */
@Service
@Slf4j
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;

    public List<RoleEntity> getRoles() {
        return roleRepository.findAll();
    }

    public RoleEntity getRoleById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new CodeReviewException("角色信息不存在：" + roleId));
    }

    public void addRole(SaveRoleReqBody reqBody) {
        RoleEntity entity = new RoleEntity();
        entity.setRoleName(reqBody.getRoleName());
        entity.setRoleDesc(reqBody.getRoleDesc());
        roleRepository.saveAndFlush(entity);
    }

    @Transactional
    public void modifyRole(long roleId, SaveRoleReqBody reqBody) {
        if (reqBody == null || StringUtils.isEmpty(reqBody.getRoleName()) || StringUtils.isEmpty(reqBody.getRoleDesc())) {
           throw new CodeReviewException("请求参数不合法，信息缺失");
        }
        roleRepository.findById(roleId)
                .ifPresent(roleEntity -> {
                    roleEntity.setRoleDesc(reqBody.getRoleDesc());
                    roleEntity.setRoleName(reqBody.getRoleName());
                });
    }

    @Transactional
    public void deleteRole(long roleId) {
        userRoleRepository.deleteAllByRoleId(roleId);
        roleRepository.deleteById(roleId);
    }

    @Transactional
    public void bindRole(String account, long roleId) {
        UserEntity userEntity = userRepository.findFirstByAccount(account);
        RoleEntity roleEntity = roleRepository.findById(roleId).orElse(null);
        if (userEntity == null || roleEntity == null) {
            throw new CodeReviewException("用户或者角色不存在");
        }
        UserRoleEntity existBindingEntity = userRoleRepository.findFirstByUserAndRole(userEntity, roleEntity);
        if (existBindingEntity == null) {
            throw new CodeReviewException("用户与角色绑定关系已存在");
        }
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUser(userEntity);
        userRoleEntity.setRole(roleEntity);
        userRoleRepository.saveAndFlush(userRoleEntity);
    }

    @Transactional
    public void unbindRole(String account, long roleId) {
        UserEntity userEntity = userRepository.findFirstByAccount(account);
        RoleEntity roleEntity = roleRepository.findById(roleId).orElse(null);
        if (userEntity == null || roleEntity == null) {
            throw new CodeReviewException("用户或者角色不存在");
        }
        userRoleRepository.deleteAllByUserAndRole(userEntity, roleEntity);
    }
}
