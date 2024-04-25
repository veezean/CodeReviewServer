package com.veezean.codereview.server.service.stats;

import com.veezean.codereview.server.model.QueryStatReqBody;
import com.veezean.codereview.server.model.StatResultData;
import com.veezean.codereview.server.model.ValuePair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2024/4/16
 */
@Service
@Slf4j
public class DataStatService {
    @Resource
    private MongoTemplate mongoTemplate;

    public StatResultData stats(QueryStatReqBody reqBody) {
        BarChatModel barChatModel = statByConfirmResult(reqBody);
        BarChatModel reviewerChatModel = statByReviewer(reqBody);

        StatResultData resultData = new StatResultData();
        resultData.setConfirmResultStatResult(barChatModel);
        resultData.setReviewerChatModel(reviewerChatModel);
        return resultData;
    }

    private List<StatResult> stats(String... aggregateFields) {
        Map<String, String> fieldMap = Arrays.stream(aggregateFields)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toMap(s -> "values." + s, s -> s, (s, s2) -> s));
        String[] groupFields = fieldMap.keySet().toArray(new String[]{});
        GroupOperation groupOperation = Aggregation.group(groupFields);

        Set<Map.Entry<String, String>> entries = fieldMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            groupOperation = groupOperation.first(entry.getKey()).as(entry.getValue());
        }
        groupOperation = groupOperation.count().as("count");

        AggregationResults<Document> commentStats = mongoTemplate.aggregate(Aggregation.newAggregation(groupOperation),
                "review_comment",
                Document.class
        );
        return commentStats.getMappedResults()
                .stream()
                .map(document -> {
                    StatResult result = new StatResult();
                    for (String field : aggregateFields) {
                        result.addField(field, ValuePair.build(document.get(field, Document.class)));
                    }
                    result.setCount(document.getInteger("count"));
                    return result;
                })
                .collect(Collectors.toList());
    }

    /**
     * 评审意见确认结果统计
     *
     * @param reqBody
     * @return
     */
    private BarChatModel statByConfirmResult(QueryStatReqBody reqBody) {
        List<StatResult> mappedResults = stats("confirmResult");
        Map<String, Integer> series = new HashMap<>();
        for (StatResult result : mappedResults) {
            Optional.ofNullable(result)
                    .map(StatResult::getData)
                    .map(stringValuePairMap -> stringValuePairMap.get("confirmResult"))
                    .ifPresent(valuePair -> {
                        series.put(valuePair.getShowName(), result.getCount());
                    });
        }
        BarChatModel barChatModel = new BarChatModel();
        barChatModel.addSeriesData(BarChatSeriesData.create(new ArrayList<>(series.values())));
        barChatModel.setXaxisData(new ArrayList<>(series.keySet()));
        return barChatModel;
    }

    /**
     * 按照评审人员维度进行统计
     *
     * @param reqBody
     * @return
     */
    private BarChatModel statByReviewer(QueryStatReqBody reqBody) {
        List<StatResult> mappedResults = stats("reviewer");
        Map<String, Integer> series = new HashMap<>();
        for (StatResult result : mappedResults) {
            Optional.ofNullable(result)
                    .map(StatResult::getData)
                    .map(stringValuePairMap -> stringValuePairMap.get("reviewer"))
                    .ifPresent(valuePair -> {
                        series.put(valuePair.getShowName(), result.getCount());
                    });
        }
        BarChatModel barChatModel = new BarChatModel();
        barChatModel.addSeriesData(BarChatSeriesData.create(new ArrayList<>(series.values())));
        barChatModel.setXaxisData(new ArrayList<>(series.keySet()));
        return barChatModel;
    }

}
