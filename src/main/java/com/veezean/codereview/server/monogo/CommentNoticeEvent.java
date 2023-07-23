package com.veezean.codereview.server.monogo;

import com.veezean.codereview.server.common.CommentOperateType;
import com.veezean.codereview.server.model.ValuePair;
import lombok.Data;

import java.text.MessageFormat;
import java.util.Optional;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/7/2
 */
@Data
public class CommentNoticeEvent {
    private CommentOperateType operateType;
    private ValuePair operator;
    private ValuePair noticeRecevier;
    private String commentId;

    public Optional<String> findReceiver() {
        return Optional.ofNullable(noticeRecevier).map(ValuePair::getValue);
    }

    public String buildNoticeMsg() {
        if (operateType == CommentOperateType.COMMIT) {
            String pattern = "由{0}提交的评审意见{1}，已指派给{2}，请关注。";
            return MessageFormat.format(pattern, operator, commentId, noticeRecevier);
        }
        if (operateType == CommentOperateType.CONFIRM) {
            String pattern = "由{0}提交的评审意见{1}，已被{2}确认完成，请关注。";
            return MessageFormat.format(pattern, operator, commentId, noticeRecevier);
        }
        if (operateType == CommentOperateType.DELETE) {
            String pattern = "由{0}提交的评审意见{1}，已被{2}删除，请关注。";
            return MessageFormat.format(pattern, operator, commentId, noticeRecevier);
        }
        return "";
    }
}
