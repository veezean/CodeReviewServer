package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * 用户登录成功响应体
 *
 * @author Wang Weiren
 * @since 2023/3/22
 */
@Data
public class LoginSuccRespBody {
    private UserDetail userDetail;
    private String token;
    private long expireAt;
}
