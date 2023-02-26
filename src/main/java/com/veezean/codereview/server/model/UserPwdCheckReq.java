package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2021/6/4
 */
@Data
public class UserPwdCheckReq {

    private String account;
    /**
     * 密码，MD5加密后的值
     */
    private String password;
}
