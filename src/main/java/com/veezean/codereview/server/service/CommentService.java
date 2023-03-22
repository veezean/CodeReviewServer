package com.veezean.codereview.server.service;

import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.common.CurrentUserHolder;
import com.veezean.codereview.server.entity.CommentEntity;
import com.veezean.codereview.server.entity.ProjectEntity;
import com.veezean.codereview.server.model.*;
import com.veezean.codereview.server.repository.CommentRepository;
import com.veezean.codereview.server.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.upms.model.EruptUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2021/4/26
 */
@Service
@Slf4j
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public List<CommentReqBody> queryList(ReviewQueryParams queryParams) {
        String account = CurrentUserHolder.getCurrentUser().getAccount();

        log.info("{}用户，查询评审列表，请求： {}", account, queryParams);
        ProjectEntity projectEntity = Optional.ofNullable(queryParams.getProjectId())
                .map(projId -> projectRepository.findById(projId))
                .map(Optional::get)
                .orElse(null);
        if (projectEntity == null) {
            log.info("{}用户，查询评审列表，项目不存在", account);
            return new ArrayList<>();
        }

        String type = queryParams.getType();
        List<CommentEntity> commentEntities;
        switch (type) {
            case "我提交的":
                commentEntities = commentRepository.findAllByProjectAndReviewer(projectEntity, account);
                break;
            case "我确认的":
                commentEntities = commentRepository.findAllByProjectAndConfirmer(projectEntity, account);
                break;
            default:
                commentEntities = commentRepository.findAllByProject(projectEntity);
                break;
        }

        List<CommentReqBody> commentReqBodies = commentEntities.stream()
                .map(CommentModel::convertToModel)
                .map(CommentReqBody::convertToReqBody)
                .collect(Collectors.toList());
        log.info("{}用户，查询评审列表，查询到的记录： {}条", account, commentReqBodies.size());
        return commentReqBodies;
    }

    public void saveComments(CommitComment commitComment) {
        List<CommentReqBody> commentReqBodies = commitComment.getComments();
        if (CollectionUtils.isEmpty(commentReqBodies)) {
            return;
        }

        ProjectEntity projectEntity = projectRepository.findById(commitComment.getProjectId())
                .<CodeReviewException>orElseThrow(() -> new CodeReviewException("项目ID不存在"));
        List<CommentEntity> commentEntities = commentReqBodies.stream()
                .map(CommentReqBody::convertToCommentModel)
                .map(commentModel -> {
                    CommentEntity entity;
                    CommentEntity existEntity =
                            commentRepository.findFirstByIdentifier(commentModel.getIdentifier());
                    if (existEntity != null) {
                        // 更新场景
                        entity = existEntity;
                    } else {
                        // 新增场景
                        entity = new CommentEntity();
                    }
                    commentModel.mappedToEntity(entity);
                    entity.setProject(projectEntity);
                    return entity;
                })
                .collect(Collectors.toList());
        commentRepository.saveAll(commentEntities);
    }

    public void saveAndFlush(CommentEntity entity) {
        commentRepository.saveAndFlush(entity);
    }

    /**
     * 查询与我有关的一些数据信息，并生层通知信息用于IDEA查看。
     *
     * @return
     */
    public List<NoticeBody> getMyCommentNotice() {
        List<NoticeBody> results = new ArrayList<>();
        EruptUser currentUser = CurrentUserHolder.getCurrentUser();
        long count = commentRepository.findAllByConfirmer(currentUser.getAccount())
                .stream()
                .filter(entity -> {
                    return StringUtils.isEmpty(entity.getConfirmResult()) || "未确认".equals(entity.getConfirmResult());
                })
                .count();
        if (count > 0L) {
            NoticeBody noticeBody = new NoticeBody();
            noticeBody.setMsg("您有" + count + "条评审意见待确认！请至管理界面查看，或者点击拉取到IDEA本地确认。");
            results.add(noticeBody);
        }

        return results;
    }
}
