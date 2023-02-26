package com.veezean.codereview.server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分页查询请求的公共抽象父类
 *
 * @author Wang Weiren
 * @since 2020/8/5
 */
@Getter
@Setter
@ToString(callSuper = true)
public class PageQueryRequest<T> extends Request {
    private int pageSize = 10;
    private int pageIndex = 1;
    private String sortBy;
    private String sortType;
    private T queryParams;
}
