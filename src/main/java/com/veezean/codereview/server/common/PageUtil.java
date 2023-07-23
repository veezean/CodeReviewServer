package com.veezean.codereview.server.common;

import com.veezean.codereview.server.model.PageQueryRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/23
 */
public class PageUtil {

    /**
     * 结合排序规则和分页请求获得分页信息
     *
     * @param request 列表分页请求
     * @return 分页信息
     */
    public static Pageable buildPageable(PageQueryRequest request) {
        // 分页参数合法性保证
        int pageIndex = request.getPageIndex();
        int pageSize = request.getPageSize();
        if (pageIndex <= 0) {
            pageIndex = 1;
        }
        if (pageSize <=0 || pageSize > 200) {
            pageSize = 10;
        }

        Sort sort = buildSort(request);
        int page = pageIndex - 1;
        Pageable pageable;
        if (sort == null) {
            pageable = PageRequest.of(page, pageSize);
        } else {
            pageable = PageRequest.of(page, pageSize, sort);
        }
        return pageable;
    }

    /**
     * 根据分页查询请求判断排序规则，以升序或降序排列
     *
     * @param request 分页列表功能请求
     * @return 排序规则
     */
    private static Sort buildSort(PageQueryRequest request) {
        Sort sort = null;
        if (StringUtils.isNotEmpty(request.getSortBy())) {
            if ("desc".equalsIgnoreCase(request.getSortType())) {
                sort = Sort.by(Sort.Order.desc(request.getSortBy()));
            } else {
                sort = Sort.by(Sort.Order.asc(request.getSortBy()));
            }
        }
        return sort;
    }


}
