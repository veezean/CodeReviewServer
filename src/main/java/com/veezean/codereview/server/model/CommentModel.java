package com.veezean.codereview.server.model;

import com.veezean.codereview.server.entity.CommentEntity;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * 与客户端之间进行通信的数据模型
 *
 * @author Wang Weiren
 * @since 2021/4/26
 */
@Data
public class CommentModel {
    // 用于记录DB中唯一ID，供后续扩展更新场景使用
    private Long entityUniqueId = -1L;

    private String identifier;
    private String lineRange;
    private String filePath;
    private String content;
    private String reviewer;
    private String reviewDate;
    private String comment;
    private String confirmer;
    private String confirmDate;
    private String confirmResult;
    private String confirmNotes;

    public void mappedToEntity(CommentEntity entity) {
        entity.setIdentifier(identifier);
        entity.setLineRange(lineRange);
        entity.setFilePath(filePath);
        entity.setContent(content);
        entity.setReviewer(reviewer);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Optional.ofNullable(reviewDate).ifPresent(s -> {
            try {
                entity.setReviewDate(simpleDateFormat.parse(s));
            } catch (ParseException e) {
                entity.setReviewDate(new Date());
            }
        });
        Optional.ofNullable(confirmDate).ifPresent(s -> {
            try {
                entity.setConfirmDate(simpleDateFormat.parse(s));
            } catch (ParseException e) {
                entity.setConfirmDate(new Date());
            }
        });

        entity.setComments(comment);
        entity.setConfirmer(confirmer);
        entity.setConfirmResult(confirmResult);
        entity.setConfirmNotes(confirmNotes);
    }

    public static CommentModel convertToModel(CommentEntity entity) {
        CommentModel model = new CommentModel();

        model.setEntityUniqueId(entity.getId());
        model.setIdentifier(entity.getIdentifier());
        model.setLineRange(entity.getLineRange());
        model.setFilePath(entity.getFilePath());
        model.setContent(entity.getContent());
        model.setConfirmNotes(entity.getConfirmNotes());
        model.setReviewer(entity.getReviewer());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date rDate = Optional.ofNullable(entity.getReviewDate())
                .orElse(new Date());
        model.setReviewDate(simpleDateFormat.format(rDate));
        Optional.ofNullable(entity.getConfirmDate())
                .ifPresent(date -> {
                    model.setConfirmDate(simpleDateFormat.format(date));
                });

        model.setComment(entity.getComments());
        model.setConfirmer(entity.getConfirmer());
        model.setConfirmResult(entity.getConfirmResult());
        model.setConfirmNotes(entity.getConfirmNotes());

        return model;
    }
}
