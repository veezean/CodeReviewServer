package com.veezean.codereview.server.service.stats;

import com.veezean.codereview.server.common.CommonConsts;
import com.veezean.codereview.server.common.CurrentUserHolder;
import com.veezean.codereview.server.common.SystemCommentFieldKey;
import com.veezean.codereview.server.model.QueryStatReqBody;
import com.veezean.codereview.server.model.StatResultData;
import com.veezean.codereview.server.model.ValuePair;
import com.veezean.codereview.server.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据统计服务
 *
 * @author Veezean
 * @since 2024/4/16
 */
@Service
@Slf4j
public class DataStatService {
    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private ProjectService projectService;

    public StatResultData stats(QueryStatReqBody reqBody) {
        StatResultData resultData = new StatResultData();
        resultData.setConfirmResultChartModel(singleFieldStat(reqBody, "confirmResult"));
        resultData.setReviewerChartModel(singleFieldStat(reqBody, "reviewer"));
        resultData.setProjectChartModel(singleFieldStat(reqBody, "projectId"));
        resultData.setRealConfirmerChartModel(singleFieldStat(reqBody, "realConfirmer"));
        return resultData;
    }

    private List<StatResult> stats(QueryStatReqBody queryParams, String... aggregateFields) {
        // 聚合操作
        List<AggregationOperation> operations = new ArrayList<>();

        // 添加过滤条件
        operations.add(Aggregation.match(buildListQuery(queryParams)));
        // 添加聚合条件
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
        operations.add(groupOperation);

        // 执行操作
        AggregationResults<Document> commentStats =
                mongoTemplate.aggregate(Aggregation.newAggregation(operations),
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

    private BarChartModel singleFieldStat(QueryStatReqBody reqBody, String fieldName) {
        List<StatResult> mappedResults = stats(reqBody, fieldName);
        Map<String, Integer> series = new HashMap<>();
        for (StatResult result : mappedResults) {
            Optional.ofNullable(result)
                    .map(StatResult::getData)
                    .map(stringValuePairMap -> stringValuePairMap.get(fieldName))
                    .ifPresent(valuePair -> {
                        series.put(valuePair.getShowName(), result.getCount());
                    });
        }
        BarChartModel barChartModel = new BarChartModel();
        barChartModel.addSeriesData(BarChatSeriesData.create(new ArrayList<>(series.values())));
        barChartModel.setXaxisData(new ArrayList<>(series.keySet()));
        return barChartModel;
    }


    /**
     * 构建过滤查询条件
     *
     * @param queryParams 查询条件
     * @return
     */
    private Criteria buildListQuery(QueryStatReqBody queryParams) {
        Criteria criteria = Criteria.where("status").is(0);
        if (queryParams != null) {

            if (StringUtils.isNotEmpty(queryParams.getConfirmResult())) {
                criteria.and("values." + SystemCommentFieldKey.CONFIRM_RESULT.getCode() + ".value").is(queryParams.getConfirmResult());
            }
            if (StringUtils.isNotEmpty(queryParams.getCommitUser())) {
                criteria.and("values." + SystemCommentFieldKey.REVIEWER.getCode() + ".value").is(queryParams.getCommitUser());
            }
            if (StringUtils.isNotEmpty(queryParams.getAssignConfirmUser())) {
                criteria.and("values." + SystemCommentFieldKey.ASSIGN_CONFIRMER.getCode() + ".value").is(queryParams.getAssignConfirmUser());
            }
            if (StringUtils.isNotEmpty(queryParams.getRealConfirmUser())) {
                criteria.and("values." + SystemCommentFieldKey.REAL_CONFIRMER.getCode() + ".value").is(queryParams.getRealConfirmUser());
            }
            if (queryParams.getProjectId() != null && queryParams.getProjectId() > 0L) {
                criteria.and("values." + SystemCommentFieldKey.PROJECT_ID.getCode() + ".value").is(String.valueOf(queryParams.getProjectId()));
            } else if (queryParams.getDepartmentId() != null && queryParams.getDepartmentId() > 0L) {
                // 如果指定了部门，则限定在部门内的项目中查询
                criteria.and("values." + SystemCommentFieldKey.PROJECT_ID.getCode() + ".value").in(projectService.queryAccessableProjectInDept(queryParams.getDepartmentId() + "")
                        .stream()
                        .map(projectEntity -> String.valueOf(projectEntity.getId()))
                        .collect(Collectors.toList()));
            }
        }
        return criteria;
    }

    public HomePageStatData homestat() {
        HomePageStatData statData = new HomePageStatData();

        String currentUser = CurrentUserHolder.getCurrentUser().getAccount();
        // 等我确认
        Criteria criteria = Criteria.where("status").is(0);
        criteria.and("values." + SystemCommentFieldKey.CONFIRM_RESULT.getCode() + ".value").is(CommonConsts.UNCONFIRMED);
        criteria.and("values." + SystemCommentFieldKey.ASSIGN_CONFIRMER.getCode() + ".value").is(currentUser);
        statData.setWaitingMeConfirm(statCount(criteria));

        // 我提交的
        Criteria criteria2 = Criteria.where("status").is(0);
        criteria2.and("values." + SystemCommentFieldKey.REVIEWER.getCode() + ".value").is(currentUser);
        statData.setMyCommitted(statCount(criteria2));

        // 我确认的
        Criteria criteria3 = Criteria.where("status").is(0);
        criteria3.and("values." + SystemCommentFieldKey.REAL_CONFIRMER.getCode() + ".value").is(currentUser);
        statData.setMyConfirmed(statCount(criteria3));

        // 全部意见
        Criteria criteria4 = Criteria.where("status").is(0);
        statData.setAllComments(statCount(criteria4));

        // 等待确认
        Criteria criteria5 = Criteria.where("status").is(0);
        criteria5.and("values." + SystemCommentFieldKey.CONFIRM_RESULT.getCode() + ".value").is(CommonConsts.UNCONFIRMED);
        statData.setWaitingConfirm(statCount(criteria5));

        // 项目总数
        statData.setTotalProjects(projectService.count());

        return statData;
    }


    private long statCount(Criteria criteria) {
        return mongoTemplate.count(new Query(criteria), "review_comment");
    }

}
