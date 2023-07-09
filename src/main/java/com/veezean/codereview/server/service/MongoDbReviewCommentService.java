package com.veezean.codereview.server.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.veezean.codereview.server.common.*;
import com.veezean.codereview.server.entity.ColumnDefineEntity;
import com.veezean.codereview.server.model.*;
import com.veezean.codereview.server.monogo.ReviewCommentEntity;
import com.veezean.codereview.server.monogo.ReviewCommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/25
 */
@Service
@Slf4j
public class MongoDbReviewCommentService {

    private static final int NORMAL = 0;
    private static final int DELETED = 1;

    @Autowired
    private ReviewCommentRepository reviewCommentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ColumnDefineService columnDefineService;

    @Autowired
    private ProjectService projectService;
    @Autowired
    private CommentFieldShowContentProducer commentFieldShowContentProducer;

    private List<CommentFieldVO> buildCommentFieldVO(java.util.function.Predicate<ColumnDefineEntity> showPredicate,
                                                     java.util.function.Predicate<ColumnDefineEntity> editPredicate) {
        // 策略是所有字段都给到前端，但是告知前端是否显示、是否可编辑、是否必填，前端控制显示策略
        return columnDefineService.queryColumns()
                .map(columnDefineEntity -> {
                    CommentFieldVO editModel = new CommentFieldVO();
                    editModel.setCode(columnDefineEntity.getColumnCode());
                    editModel.setEditable(editPredicate.test(columnDefineEntity));
                    editModel.setShow(showPredicate.test(columnDefineEntity));
                    editModel.setRequired(columnDefineEntity.isRequired());
                    editModel.setInputType(columnDefineEntity.getInputType());
                    editModel.setEnumValues(columnDefineEntity.getEnumValues());
                    editModel.setShowName(columnDefineEntity.getShowName());
                    editModel.setValuePair(new ValuePair());
                    return editModel;
                })
                .collect(Collectors.toList());
    }

    public SaveReviewCommentReqBody initCreateReqBody() {
        SaveReviewCommentReqBody reqBody = new SaveReviewCommentReqBody();
        List<CommentFieldVO> commentFieldVOs = buildCommentFieldVO(ColumnDefineEntity::isShowInAddPage,
                ColumnDefineEntity::isEditableInAddPage);
        commentFieldVOs.forEach(commentFieldVO -> {
            String code = commentFieldVO.getCode();
            // 新增场景，字段初始赋值
            if (SystemCommentFieldKey.IDENTIFIER.getCode().equals(code)) {
                commentFieldVO.setValuePair(ValuePair.build(RandomUtil.randomString(20)));
            }
        });
        reqBody.setFieldModelList(commentFieldVOs);
        return reqBody;
    }

    public SaveReviewCommentReqBody initEditReqBody(String identifier) {
        ReviewCommentEntity commentEntity = queryCommentDetail(identifier);
        Map<String, ValuePair> columnCodeValues = commentEntity.getValues();
        SaveReviewCommentReqBody reqBody = new SaveReviewCommentReqBody();
        List<CommentFieldVO> commentFieldVOS = buildCommentFieldVO(ColumnDefineEntity::isShowInEditPage,
                ColumnDefineEntity::isEditableInEditPage);
        commentFieldVOS.forEach(editModel -> {
            // 赋值
            editModel.setValuePair(columnCodeValues.get(editModel.getCode()));
        });
        reqBody.setFieldModelList(commentFieldVOS);
        reqBody.setDataVersion(commentEntity.getDataVersion());
        return reqBody;
    }


    public SaveReviewCommentReqBody initConfirmReqBody(String identifier) {
        ReviewCommentEntity commentEntity = queryCommentDetail(identifier);
        Map<String, ValuePair> columnCodeValues = commentEntity.getValues();
        SaveReviewCommentReqBody reqBody = new SaveReviewCommentReqBody();
        List<CommentFieldVO> commentFieldVOS = buildCommentFieldVO(ColumnDefineEntity::isShowInConfirmPage,
                ColumnDefineEntity::isEditableInConfirmPage);
        commentFieldVOS.forEach(editModel -> {
            // 赋值
            editModel.setValuePair(columnCodeValues.get(editModel.getCode()));
        });
        reqBody.setFieldModelList(commentFieldVOS);
        reqBody.setDataVersion(commentEntity.getDataVersion());
        return reqBody;
    }

    public SaveReviewCommentReqBody initViewReqBody(String identifier) {
        ReviewCommentEntity commentEntity = queryCommentDetail(identifier);
        Map<String, ValuePair> columnCodeValues = commentEntity.getValues();
        SaveReviewCommentReqBody reqBody = new SaveReviewCommentReqBody();
        List<CommentFieldVO> commentFieldVOS = buildCommentFieldVO(columnDefineEntity -> true,
                columnDefineEntity -> false);
        commentFieldVOS.forEach(editModel -> {
            // 赋值
            editModel.setValuePair(columnCodeValues.get(editModel.getCode()));
        });
        reqBody.setFieldModelList(commentFieldVOS);
        reqBody.setDataVersion(commentEntity.getDataVersion());
        return reqBody;
    }

    @Transactional
    public void createComment(SaveReviewCommentReqBody reqBody) {
        if (reqBody == null || CollectionUtils.isEmpty(reqBody.getFieldModelList())) {
            throw new CodeReviewException("请求内容不合法");
        }
        ReviewCommentEntity commentEntity = buildCommentEntity(
                reqBody,
                s -> new ReviewCommentEntity(),
                resultMap -> {
                    resultMap.put(SystemCommentFieldKey.CONFIRM_RESULT.getCode(),
                            ValuePair.build(CommonConsts.UNCONFIRMED,
                            "待确认"));
                    String dateTime = DateUtil.formatDateTime(new Date());
                    resultMap.put(SystemCommentFieldKey.REVIEW_DATE.getCode(), ValuePair.build(dateTime, dateTime));
                    UserDetail currentUser = CurrentUserHolder.getCurrentUser();
                    resultMap.put(SystemCommentFieldKey.REVIEWER.getCode(),
                            ValuePair.build(currentUser.getAccount(), currentUser.getName())
                    );
                });

        // 存储到数据库中,此处直接存储，不做字段内容校验
        reviewCommentRepository.save(commentEntity);
    }

    @Transactional
    public void modifyComment(SaveReviewCommentReqBody reqBody) {
        if (reqBody == null || CollectionUtils.isEmpty(reqBody.getFieldModelList())) {
            throw new CodeReviewException("请求内容不合法");
        }

        ReviewCommentEntity commentEntity = buildCommentEntity(
                reqBody,
                identifier -> reviewCommentRepository.findFirstByIdAndStatus(identifier, NORMAL),
                resultMap -> {
                });
        commentEntity.increaseDataVersion();
        // 存储到数据库中,此处直接存储，不做字段内容校验
        reviewCommentRepository.save(commentEntity);
    }

    @Transactional
    public void confirmComment(SaveReviewCommentReqBody reqBody) {
        if (reqBody == null || CollectionUtils.isEmpty(reqBody.getFieldModelList())) {
            throw new CodeReviewException("请求内容不合法");
        }

        if (CommonConsts.UNCONFIRMED.equals(reqBody.getValueByKey(SystemCommentFieldKey.CONFIRM_RESULT))) {
            throw new CodeReviewException("请填写确认结果");
        }

        ReviewCommentEntity commentEntity = buildCommentEntity(
                reqBody,
                identifier -> reviewCommentRepository.findFirstByIdAndStatus(identifier, NORMAL),
                resultMap -> {
                    String dateTime = DateUtil.formatDateTime(new Date());
                    resultMap.put(SystemCommentFieldKey.CONFIRM_DATE.getCode(), ValuePair.build(dateTime, dateTime));
                    UserDetail currentUser = CurrentUserHolder.getCurrentUser();
                    resultMap.put(SystemCommentFieldKey.REAL_CONFIRMER.getCode(),
                            ValuePair.build(currentUser.getAccount(), currentUser.getName())
                    );
                });
        commentEntity.increaseDataVersion();
        // 存储到数据库中,此处直接存储，不做字段内容校验
        reviewCommentRepository.save(commentEntity);
    }

    private ReviewCommentEntity buildCommentEntity(SaveReviewCommentReqBody reqBody,
                                                   Function<String, ReviewCommentEntity> reviewEntityProvider,
                                                   Consumer<Map<String, ValuePair>> resultConsumer) {
        // 必填字段非空校验
        reqParamValidate(reqBody);
        String identifier = reqBody.findIdentifier();
        ReviewCommentEntity commentEntity = reviewEntityProvider.apply(identifier);
        if (commentEntity == null) {
            throw new CodeReviewException("操作的目标记录不存在:" + identifier);
        }

        // CAS版本控制
        if (commentEntity.getDataVersion() != reqBody.getDataVersion()) {
            throw new CodeReviewException("保存失败，记录已被更新，请基于最新记录基础上进行修改");
        }

        Map<String, ValuePair> valuePairMap =
                reqBody.getFieldModelList().stream().
                        filter(commentFieldVO -> commentFieldVO.getValuePair() != null)
                        .collect(Collectors.toMap(CommentFieldVO::getCode,
                                CommentFieldVO::getValuePair));
        resultConsumer.accept(valuePairMap);
        commentEntity.setValues(valuePairMap);
        commentEntity.setId(identifier);
        return commentEntity;
    }

    private void reqParamValidate(SaveReviewCommentReqBody reqBody) {
        reqBody.getFieldModelList().stream()
                .filter(commentFieldVO ->
                        commentFieldVO.isShow()
                                && commentFieldVO.isEditable()
                                && commentFieldVO.isRequired()
                                && (commentFieldVO.getValuePair() == null || StringUtils.isEmpty(commentFieldVO.getValuePair().getValue())))
                .findAny()
                .ifPresent(commentFieldVO -> {
                    throw new CodeReviewException("必填字段校验失败，请检查");
                });
    }

    @Transactional
    public void deleteComment(String identifier) {
        // 软删除
        ReviewCommentEntity commentEntity = reviewCommentRepository.findFirstByIdAndStatus(identifier, NORMAL);
        if (commentEntity != null) {
            commentEntity.setStatus(DELETED);
        }
        reviewCommentRepository.save(commentEntity);
    }

    @Transactional
    public void deleteBatch(List<String> commentIds) {
        // 软删除
        List<ReviewCommentEntity> commentEntities = reviewCommentRepository.findAllByIdInAndStatus(commentIds, NORMAL);
        if (commentEntities != null) {
            commentEntities.forEach(reviewCommentEntity -> reviewCommentEntity.setStatus(DELETED));
            reviewCommentRepository.saveAll(commentEntities);
        }

    }

    public ReviewCommentEntity queryCommentDetail(String identifier) {
        ReviewCommentEntity commentEntity = reviewCommentRepository.findFirstByIdAndStatus(identifier, NORMAL);
        if (commentEntity == null) {
            throw new CodeReviewException("评审意见不存在： " + identifier);
        }
        return commentEntity;
    }

    public PageBeanList<Map<String, String>> queryCommentDetails(PageQueryRequest<QueryCommentReqBody> request) {
        Pageable pageable = PageUtil.buildPageable(request);
        QueryCommentReqBody queryParams = request.getQueryParams();
        Query query = buildListQuery(queryParams);
        long count = mongoTemplate.count(query, ReviewCommentEntity.class);
        List<Map<String, String>> commentEntities =
                mongoTemplate.find(query
                                .skip((pageable.getPageNumber() - 1) * pageable.getPageSize())
                                .limit(pageable.getPageSize()),
                        ReviewCommentEntity.class)
                        .stream()
                        .map(entity -> {
                            Map<String, String> result = new HashMap<>();
                            entity.getValues().forEach((key, value) -> {
                                result.put(key,
                                        commentFieldShowContentProducer.getColumnShowContent(value)
                                );
                            });
                            return result;
                        })
                        .collect(Collectors.toList());

        Page<Map<String, String>> page = new PageImpl<>(commentEntities, pageable, count);
        return PageBeanList.create(page, pageable);
    }

    private Query buildListQuery(QueryCommentReqBody queryParams) {
        Criteria criteria = Criteria.where("id").ne(null).and("status").is(0);
        if (queryParams != null) {

            if (StringUtils.isNotEmpty(queryParams.getConfirmResult())) {
                criteria.and("values." + SystemCommentFieldKey.CONFIRM_RESULT.getCode() + ".value").is(queryParams.getConfirmResult());
            }
            if (StringUtils.isNotEmpty(queryParams.getIdentifier())) {
                criteria.and("values." + SystemCommentFieldKey.IDENTIFIER.getCode() + ".value").is(queryParams.getIdentifier());
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
                criteria.and("values." + SystemCommentFieldKey.PROJECT_ID.getCode() + ".value").in(projectService.queryProjectInDept(queryParams.getDepartmentId() + "")
                        .stream()
                        .map(projectEntity -> String.valueOf(projectEntity.getId()))
                        .collect(Collectors.toList()));
            }
        }

        return new Query(criteria);
    }

    @Transactional
    public CommitResult clientCommit(CommitComment commitComment) {
        CommitResult result = new CommitResult();
        List<ReviewCommentEntity> passEntitiespassEntities = new ArrayList<>();
        commitComment.getComments().forEach(reviewCommentEntity -> {
            ReviewCommentEntity existEntity =
                    reviewCommentRepository.findFirstByIdAndStatus(reviewCommentEntity.getId(), NORMAL);
            if (existEntity != null && existEntity.getDataVersion() != reviewCommentEntity.getDataVersion()) {
                result.addFailedId(reviewCommentEntity.getId());
            } else {
                // 版本+1，CAS控制
                reviewCommentEntity.increaseDataVersion();
                passEntitiespassEntities.add(reviewCommentEntity);
                result.putVersion(reviewCommentEntity.getId(), reviewCommentEntity.getDataVersion());
            }
        });
        reviewCommentRepository.saveAll(passEntitiespassEntities);
        if (result.getFailedIds().isEmpty()) {
            result.setSuccess(true);
            return result;
        } else {
            result.setErrDesc("数据版本冲突，请先备份本地数据，然后更新至服务端最新数据，解决冲突再提交。");
            log.error("存在数据提交失败：{}", result.getFailedIds());
        }
        return result;
    }

    public CommitComment clientQueryComments(ReviewQueryParams queryParams) {
        QueryCommentReqBody reqBody = new QueryCommentReqBody();
        reqBody.setProjectId(queryParams.getProjectId());
        switch (queryParams.getType()) {
            case "我提交的":
                reqBody.setCommitUser(CurrentUserHolder.getCurrentUser().getAccount());
                break;
            case "待我确认":
                reqBody.setAssignConfirmUser(CurrentUserHolder.getCurrentUser().getAccount());
                break;
            default:
                break;
        }
        Query query = buildListQuery(reqBody);
        List<ReviewCommentEntity> reviewCommentEntities = mongoTemplate.find(query, ReviewCommentEntity.class);
        CommitComment commitComment = new CommitComment();
        commitComment.setComments(reviewCommentEntities);
        return commitComment;
    }
}
