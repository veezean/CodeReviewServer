package com.veezean.codereview.server.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2021/6/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPwdCheckRespBody {
    private boolean pass;
}
