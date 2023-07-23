package com.veezean.codereview.server.monogo;

import com.veezean.codereview.server.common.CommentOperateType;
import com.veezean.codereview.server.common.CommonConsts;
import com.veezean.codereview.server.common.CurrentUserHolder;
import com.veezean.codereview.server.common.SystemCommentFieldKey;
import com.veezean.codereview.server.model.ValuePair;
import com.veezean.codereview.server.notice.NoticeCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/7/2
 */
@Component
@Slf4j
public class MongoEventListener extends AbstractMongoEventListener<ReviewCommentEntity> {
    @Autowired
    private ReviewCommentRepository reviewCommentRepository;

    @Override
    public void onAfterSave(AfterSaveEvent<ReviewCommentEntity> event) {

        ReviewCommentEntity commentEntity = event.getSource();
        long dataVersion = commentEntity.getDataVersion();
        Optional<ValuePair> reviewer = commentEntity.findByKey(SystemCommentFieldKey.REVIEWER);
        Optional<ValuePair> assignConfirmer = commentEntity.findByKey(SystemCommentFieldKey.ASSIGN_CONFIRMER);
        Optional<ValuePair> realConfirmer = commentEntity.findByKey(SystemCommentFieldKey.REAL_CONFIRMER);
        Optional<ValuePair> confirmResult = commentEntity.findByKey(SystemCommentFieldKey.CONFIRM_RESULT);

        boolean unconfirmed =
                confirmResult.isPresent() && CommonConsts.UNCONFIRMED.equals(confirmResult.get().getValue());

        CommentNoticeEvent commentNoticeEvent = new CommentNoticeEvent();
        commentNoticeEvent.setCommentId(commentEntity.getId());
        if (dataVersion == 0L && unconfirmed && assignConfirmer.isPresent() && reviewer.isPresent()) {
            // 新增
            commentNoticeEvent.setOperateType(CommentOperateType.COMMIT);
            commentNoticeEvent.setOperator(reviewer.get());
            commentNoticeEvent.setNoticeRecevier(assignConfirmer.get());

            NoticeCache.addNoticeEvent(commentNoticeEvent);
        } else if (dataVersion != 0L && !unconfirmed && realConfirmer.isPresent() && reviewer.isPresent()) {
            // 确认
            commentNoticeEvent.setOperateType(CommentOperateType.COMMIT);
            commentNoticeEvent.setOperator(realConfirmer.get());
            commentNoticeEvent.setNoticeRecevier(reviewer.get());

            NoticeCache.addNoticeEvent(commentNoticeEvent);
        }

        super.onAfterSave(event);
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<ReviewCommentEntity> event) {
        String id = event.getSource().get("id", String.class);
        ReviewCommentEntity commentEntity = reviewCommentRepository.findFirstByIdAndStatus(id, 1);
        if (commentEntity == null) {
            return;
        }
        Optional<ValuePair> reviewer = commentEntity.findByKey(SystemCommentFieldKey.REVIEWER);
        if (reviewer.isPresent()) {
            CommentNoticeEvent commentNoticeEvent = new CommentNoticeEvent();
            commentNoticeEvent.setCommentId(commentEntity.getId());

            String account = CurrentUserHolder.getCurrentUser().getAccount();
            String name = CurrentUserHolder.getCurrentUser().getName();
            ValuePair operator = ValuePair.build(account, name);

            commentNoticeEvent.setOperator(operator);
            commentNoticeEvent.setNoticeRecevier(reviewer.get());
            commentNoticeEvent.setOperateType(CommentOperateType.DELETE);

            NoticeCache.addNoticeEvent(commentNoticeEvent);
        }
        super.onAfterDelete(event);
    }
}
