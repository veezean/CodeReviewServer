package com.veezean.codereview.server.model;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分页列表数据对象封装外壳
 *
 * @author Wang Weiren
 * @since 2020/7/30
 */
@Data
public class PageBeanList<T> {
    private long total;
    private int pageSize;
    private int totalPage;
    private int currPage;

    /**
     * 当页数据
     */
    private List<T> list;

    @Deprecated
    public PageBeanList() {
        // TODO 标识废弃，后续不允许直接new
    }

    /**
     * 根据给定参数构造分页对象
     *
     * @param total 总条数
     * @param pageSize 每页记录数
     * @param totalPage 总页数
     * @param currPage 当前页数
     * @param list 分页数据
     * @param <T> 数据类型
     * @return 分页对象
     */
    public static <T> PageBeanList<T> create(Long total, Integer pageSize, Integer totalPage, Integer currPage,
                                             List<T> list) {
        PageBeanList<T> pageBeanList = empty();
        if (total != null && total > -1L) {
            pageBeanList.setTotal(total);
        }
        if (pageSize != null && pageSize > -1) {
            pageBeanList.setPageSize(pageSize);
        }
        if (totalPage != null && totalPage > -1) {
            pageBeanList.setTotalPage(totalPage);
        }
        if (currPage != null && currPage > -1) {
            pageBeanList.setCurrPage(currPage);
        }
        if (!CollectionUtils.isEmpty(list)) {
            pageBeanList.setList(list);
        }
        return pageBeanList;
    }

    /**
     * 构建分页数据对象
     *
     * @param page 分页数据
     * @param pageable 分页信息
     * @param <T> 对象类型
     * @return 分页数据对象
     */
    public static <T> PageBeanList<T> create(Page<T> page, Pageable pageable) {
        List<T> list = page.get().collect(Collectors.toList());
        PageBeanList<T> pageBeanList = new PageBeanList<>();
        pageBeanList.setTotal(page.getTotalElements());
        pageBeanList.setTotalPage(page.getTotalPages());
        pageBeanList.setCurrPage(pageable.getPageNumber() + 1);
        pageBeanList.setPageSize(pageable.getPageSize());
        pageBeanList.setList(list);
        return pageBeanList;
    }

    /**
     * 从已有的分页对象，复制一个对象，同时替换掉原有对象的数据列表
     *
     * @param source 原对象
     * @param data 实际数据
     * @param <T> 返回类型
     * @return 新的分页对象
     */
    public static <T> PageBeanList<T> copyFrom(PageBeanList source, List<T> data) {
        PageBeanList<T> target = new PageBeanList<>();
        target.setTotalPage(source.getTotalPage());
        target.setTotal(source.getTotal());
        target.setCurrPage(source.getCurrPage());
        target.setPageSize(source.getPageSize());
        target.setList(data);
        return target;
    }

    /**
     * 构造个空分页对象
     *
     * @param <T> 数据类型
     * @return 空分页对象
     */
    public static <T> PageBeanList<T> empty() {
        PageBeanList<T> pageBeanList = new PageBeanList<>();
        pageBeanList.setList(new ArrayList<>());
        return pageBeanList;
    }
}
