package com.veezean.codereview.server.service.stats;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2024/4/18
 */
@Data
public class BarChatModel {
    /**
     * 图例
     */
    private List<String> legendData = new ArrayList<>();
    /**
     * X轴标签
     */
    private List<String> xaxisData = new ArrayList<>();
    /**
     * 数据集
     */
    private List<Integer> seriesData = new ArrayList<>();

    /**
     * 添加系列数据
     *
     * @param seriesData
     */
    public void addSeriesData(BarChatSeriesData seriesData) {
        String name = seriesData.getName();
        if (StringUtils.isNotEmpty(name)) {
            legendData.add(name);
        }
        this.seriesData.addAll(seriesData.getData());
    }
}
