package com.veezean.codereview.server.model;

import lombok.Data;

import java.util.List;

/**
 * 编辑用户基本信息（自己编辑自己信息）
 *
 * @author Veezean
 * @since 2023/3/23
 */
@Data
public class EditUserBaseInfoReqBody {
    private String name;
    private String phoneNumber;
}
