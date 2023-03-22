package com.veezean.codereview.server.model;

import com.veezean.codereview.server.entity.CommentEntity;
import com.veezean.codereview.server.service.UserService;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 与客户端之间进行通信的数据模型
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2021/4/26
 */
@Data
public class CommentModel {
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
        entity.setConfirmResult(confirmResult);
        entity.setConfirmNotes(confirmNotes);

        // 将客户端可能的拼接格式解析为具体的account
        parseUserAccountAndSet(reviewer, entity::setReviewer);
        parseUserAccountAndSet(confirmer, entity::setConfirmer);
    }

    /**
     * 将客户端提交的拼接格式转换为account格式入库
     *
     * @param userAccountName 客户端的拼接格式， showName|account格式
     * @param consumer 结果处理者
     */
    private void parseUserAccountAndSet(String userAccountName, Consumer<String> consumer) {
        Optional.ofNullable(userAccountName).map(accountName -> {
            String[] split = accountName.split("\\|");
            if (split.length == 2) {
                return split[1];
            } else {
                return accountName;
            }
        }).ifPresent(consumer);
    }

    public static CommentModel convertToModel(CommentEntity entity) {
        CommentModel model = new CommentModel();

        model.setIdentifier(entity.getIdentifier());
        model.setLineRange(entity.getLineRange());
        model.setFilePath(entity.getFilePath());
        model.setContent(entity.getContent());
        model.setConfirmNotes(entity.getConfirmNotes());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date rDate = Optional.ofNullable(entity.getReviewDate())
                .orElse(new Date());
        model.setReviewDate(simpleDateFormat.format(rDate));
        Optional.ofNullable(entity.getConfirmDate())
                .ifPresent(date -> {
                    model.setConfirmDate(simpleDateFormat.format(date));
                });

        model.setComment(entity.getComments());
        model.setConfirmResult(entity.getConfirmResult());
        model.setConfirmNotes(entity.getConfirmNotes());

        convertUserInfoToClientFormat(entity.getReviewer(), model::setReviewer);
        convertUserInfoToClientFormat(entity.getConfirmer(), model::setConfirmer);

        return model;
    }

    /**
     * 将服务端的账号数据拼接为客户端需要的格式
     *
     * @param account
     * @param consumer
     */
    private static void convertUserInfoToClientFormat(String account, Consumer<String> consumer) {
        Optional.ofNullable(account)
                .map(UserService::getUserDetailByAccout)
                .map(eruptUser -> eruptUser.getName() + "|" + eruptUser.getAccount())
                .ifPresent(consumer);
    }
}
