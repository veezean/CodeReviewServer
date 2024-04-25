package com.veezean.codereview.server.model;

import com.veezean.codereview.server.service.stats.BarChartModel;
import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2024/4/19
 */
@Data
public class StatResultData {
    /**
     * 评审意见确认结果统计
     */
    private BarChartModel confirmResultChartModel;
    private BarChartModel reviewerChartModel;
    private BarChartModel realConfirmerChartModel;
    private BarChartModel projectChartModel;
}
