package com.veezean.codereview.server.service;

import com.veezean.codereview.server.common.CurrentUserHolder;
import com.veezean.codereview.server.entity.ReviewCommentEntity;
import com.veezean.codereview.server.model.CommentFieldModel;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/25
 */
@Slf4j
public class ReviewCommentAuditListener {

    /**
     * 新增操作前执行
     *
     * @param entity
     */
    @PostPersist
    public void postPersist(ReviewCommentEntity entity) {
      log.info("postPersist() IN. entity:{}", entity);

      entity.setCreateUser(CurrentUserHolder.getCurrentUser().getAccount());
      entity.setDataVersion(1L);
    }

    /**
     * 更新操作前执行
     *
     * @param entity
     */
    @PostUpdate
    public void postUpdate(ReviewCommentEntity entity) {
        log.info("postUpdate() IN. entity:{}", entity);

        entity.setLastModifiedUser(CurrentUserHolder.getCurrentUser().getAccount());
        entity.setDataVersion(entity.getDataVersion() + 1);
    }

    /**
     * 删除操作前执行
     *
     * @param entity
     */
    @PostRemove
    public void postRemove(ReviewCommentEntity entity) {
        log.info("postRemove() IN. entity:{}", entity);
    }
}
