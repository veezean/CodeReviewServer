package com.veezean.codereview.server.service;

import com.alibaba.fastjson.JSON;
import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.common.CommonConsts;
import com.veezean.codereview.server.common.PageUtil;
import com.veezean.codereview.server.entity.ReviewCommentEntity;
import com.veezean.codereview.server.model.*;
import com.veezean.codereview.server.repository.ReviewCommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/25
 */
@Service
@Slf4j
public class ReviewCommentService {
    @Autowired
    private ReviewCommentRepository reviewCommentRepository;
    @Autowired
    private ColumnDefineService columnDefineService;

    @Transactional
    public void createOrModifyReviewComment(SaveReviewCommentReqBody reqBody) {
        if (reqBody == null
                || reqBody.getProjectId() <= 0
                || CollectionUtils.isEmpty(reqBody.getFieldModelList())
                || StringUtils.isEmpty(reqBody.getIdentifier())
        ) {
            throw new CodeReviewException("请求内容不合法");
        }
        String identifier = reqBody.getIdentifier();

        ReviewCommentEntity commentEntity = reviewCommentRepository.findFirstByIdentifier(identifier);
        if (commentEntity != null) {
            if (commentEntity.getDataVersion() != reqBody.getDataVersion()) {
                throw new CodeReviewException("保存失败，存量记录已被更新，请基于最新记录基础上进行修改");
            }
        } else {
            commentEntity = new ReviewCommentEntity();
        }

        commentEntity.setJsonData(JSON.toJSONString(reqBody.getFieldModelList()));
        commentEntity.setIdentifier(identifier);
        commentEntity.setProjectId(reqBody.getProjectId());
        // 特殊字段，抽取出来作为独立字段存储，供查询
        List<CommentFieldModel> fieldModelList = reqBody.getFieldModelList();
        for (CommentFieldModel commentFieldModel : fieldModelList) {
            if (CommonConsts.COMMENT_CONFIRM_USER_KEY.equals(commentFieldModel.getCode())) {
                commentEntity.setConfirmUser(commentFieldModel.getValue());
            }
            if (CommonConsts.COMMENT_CONFIRM_RESULT_KEY.equals(commentFieldModel.getCode())) {
                commentEntity.setConfirmResult(commentFieldModel.getValue());
            }
        }

        // 存储到数据库中,此处直接存储，不做字段内容校验
        reviewCommentRepository.saveAndFlush(commentEntity);
    }

    @Transactional
    public void deleteComment(long commentId) {
        reviewCommentRepository.deleteById(commentId);
    }

    @Transactional
    public void deleteBatch(List<Long> commentIds) {
        reviewCommentRepository.deleteAllById(commentIds);
    }

    public ReviewCommentEntity queryCommentDetail(long commentId) {
        return reviewCommentRepository.findById(commentId)
                .orElseThrow(() -> new CodeReviewException("评审意见不存在： " + commentId));
    }

    public PageBeanList<ReviewCommentEntity> queryCommentDetails(PageQueryRequest<QueryCommentReqBody> request) {
        Pageable pageable = PageUtil.buildPageable(request);
        QueryCommentReqBody queryParams = request.getQueryParams();
        Specification<ReviewCommentEntity> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (queryParams != null) {
                if (queryParams.getProjectId() != null && queryParams.getProjectId() > 0L) {
                    predicates.add(criteriaBuilder.equal(root.get("projectId"), queryParams.getProjectId()));
                }
                if (StringUtils.isNotEmpty(queryParams.getCommitUser())) {
                    predicates.add(criteriaBuilder.equal(root.get("createUser"), queryParams.getCommitUser()));
                }
                if (StringUtils.isNotEmpty(queryParams.getConfirmUser())) {
                    predicates.add(criteriaBuilder.equal(root.get("confirmUser"), queryParams.getConfirmUser()));
                }
                if (StringUtils.isNotEmpty(queryParams.getConfirmResult())) {
                    predicates.add(criteriaBuilder.equal(root.get("confirmResult"), queryParams.getConfirmResult()));
                }
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
        Page<ReviewCommentEntity> commentEntities = reviewCommentRepository.findAll(specification, pageable);
        return PageBeanList.create(commentEntities, pageable);
    }

}
