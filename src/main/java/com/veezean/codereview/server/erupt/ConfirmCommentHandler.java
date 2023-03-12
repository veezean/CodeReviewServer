package com.veezean.codereview.server.erupt;

import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.entity.CommentConfirmModel;
import com.veezean.codereview.server.entity.CommentEntity;
import com.veezean.codereview.server.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.OperationHandler;
import xyz.erupt.upms.service.EruptUserService;

import java.util.Date;
import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2023/2/26
 */
@Component
@Slf4j
public class ConfirmCommentHandler implements OperationHandler {

    @Autowired
    private CommentService commentService;
    @Autowired
    private EruptUserService eruptUserService;

    @Override
    public String exec(List data, Object o, String[] param) {

        if (data == null || data.isEmpty() || o == null) {
            throw new CodeReviewException("原始数据异常，操作失败");
        }

        Object obj = data.get(0);
        if (!(obj instanceof CommentEntity) || !(o instanceof CommentConfirmModel)) {
            throw new CodeReviewException("原始数据不合法，操作失败");
        }

        CommentConfirmModel confirmModel = (CommentConfirmModel) o;
        CommentEntity commentEntity = (CommentEntity) obj;
        commentEntity.setConfirmDate(new Date());
        commentEntity.setConfirmer(eruptUserService.getCurrentAccount());
        commentEntity.setConfirmResult(confirmModel.getConfirmResult());
        commentEntity.setConfirmNotes(confirmModel.getConfirmNotes());
        commentService.saveAndFlush(commentEntity);

        log.info("确认结论已更新到记录中:{}", commentEntity);
        return null;
    }
}
