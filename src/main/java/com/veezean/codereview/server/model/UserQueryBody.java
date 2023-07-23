package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/23
 */
@Data
public class UserQueryBody {
    private Long deptId;
    private String phoneNumber;
    private String account;
    private String name;
}
