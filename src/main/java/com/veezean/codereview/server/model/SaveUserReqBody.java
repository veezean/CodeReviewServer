package com.veezean.codereview.server.model;

import lombok.Data;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/23
 */
@Data
public class SaveUserReqBody {
    private String account;
    private String name;
    private long departmentId;
    private String password;
    private String phoneNumber;
    private List<Long> roles;
}
