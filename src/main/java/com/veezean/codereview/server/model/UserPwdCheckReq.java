package com.veezean.codereview.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2021/6/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPwdCheckReq {

    private String account;
    /**
     * 密码，MD5加密后的值
     */
    private String password;
}
