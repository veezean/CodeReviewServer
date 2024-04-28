package com.veezean.codereview.server.service.stats;

import lombok.Data;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2024/4/18
 */
@Data
public class BarChatSeriesData {
    private String name;
    private String type = "bar";
    private List<Integer> data;

    public static BarChatSeriesData create(List<Integer> data) {
        BarChatSeriesData seriesData = new BarChatSeriesData();
        seriesData.setData(data);
        return seriesData;
    }
}
