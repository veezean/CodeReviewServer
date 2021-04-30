package com.veezean.codereview.server.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.veezean.codereview.server.entity.CommentEntity;
import com.veezean.codereview.server.model.Comment;
import com.veezean.codereview.server.model.CommitComment;
import com.veezean.codereview.server.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2021/4/26
 */
@Service
@Slf4j
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    public List<Comment> queryCommentsByProject(String projectKey, int filterType, String currentUser) {
        return commentRepository.queryAllByProjectKey(projectKey)
                .stream()
                .filter(commentEntity -> filterPass(filterType, currentUser, commentEntity))
                .map(commentEntity -> {
                    Comment comment = new Comment();
                    BeanUtil.copyProperties(commentEntity, comment);
                    comment.setEntityUniqueId(commentEntity.getId());
                    return comment;
                })
                .collect(Collectors.toList());
    }

    private boolean filterPass(int filterType, String currentUser, CommentEntity commentEntity) {
        if (filterType == 0) { // 全部
            return true;
        }

        String handler = commentEntity.getHandler();
        String reviewer = commentEntity.getReviewer();

        if (filterType == 1) { //我提交或待我确认的
            return StrUtil.equals(handler, currentUser) || StrUtil.equals(reviewer, currentUser);
        }

        if (filterType == 2) { //我提交的
            return StrUtil.equals(reviewer, currentUser);
        }

        if (filterType == 3) { //待我确认的
            return StrUtil.equals(handler, currentUser);
        }

        return false;
    }

    @Transactional
    public void saveOrUpdateComment(CommitComment commitComment) {
        String commitUser = commitComment.getCommitUser();

        String projectKey = commitComment.getProjectKey();
        List<Comment> comments = commitComment.getComments();
        if (comments == null) {
            return;
        }
        List<CommentEntity> commentEntities = comments.stream().map(comment -> {
            CommentEntity entity = new CommentEntity();
            BeanUtil.copyProperties(comment, entity);
            // 更新场景
            if (comment.getEntityUniqueId() > 0L) {
                entity.setId(comment.getEntityUniqueId());
                entity.setUpdateUser(commitUser);
            } else {
                entity.setCommitUser(commitUser);
            }
            entity.setProjectKey(projectKey);
            return entity;
        }).collect(Collectors.toList());
        commentRepository.saveAll(commentEntities);
    }
}
