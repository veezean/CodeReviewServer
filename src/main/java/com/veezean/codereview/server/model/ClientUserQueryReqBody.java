package com.veezean.codereview.server.model;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/12
 */
@Data
public class ClientUserQueryReqBody {

    /**
     * 查询类型
     *
     * 参见 @ClientUserQueryType 取值定义
     */
    private int queryType;

}
