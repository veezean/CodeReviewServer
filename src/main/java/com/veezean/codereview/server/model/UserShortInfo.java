package com.veezean.codereview.server.model;

import com.veezean.codereview.server.entity.DepartmentEntity;
import com.veezean.codereview.server.entity.UserEntity;
import lombok.Data;

import java.util.Optional;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/12
 */
@Data
public class UserShortInfo {
    private String account;
    private String userName;
    private String department;

    public static UserShortInfo from(UserEntity userEntity, DepartmentEntity departmentEntity) {
        UserShortInfo userShortInfo = new UserShortInfo();
        userShortInfo.setAccount(userEntity.getAccount());
        userShortInfo.setUserName(userEntity.getName());
        Optional.ofNullable(departmentEntity)
                .map(DepartmentEntity::getName)
                .ifPresent(userShortInfo::setDepartment);
        return userShortInfo;
    }
}
