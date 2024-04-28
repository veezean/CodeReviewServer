package com.veezean.codereview.server.service.stats;

import lombok.Data;

/**
 * 首页统计数据
 *
 * @author Veezean
 * @since 2024/4/28
 */
@Data
public class HomePageStatData {
    /**
     * 待我确认的
     */
    private long waitingMeConfirm;
    /**
     * 我提交的
     */
    private long myCommitted;
    /**
     * 我确认的
     */
    private long myConfirmed;

    /**
     * 全部意见
     */
    private long allComments;
    /**
     * 等待确认
     */
    private long waitingConfirm;
    /**
     * 项目总数
     */
    private long totalProjects;
}
