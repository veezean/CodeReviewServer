package com.veezean.codereview.server.entity;

import com.alibaba.fastjson.JSON;
import com.veezean.codereview.server.model.CommentFieldModel;
import com.veezean.codereview.server.service.ReviewCommentAuditListener;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/25
 */
@Table(name = "t_review_comment", schema = "code_review", catalog = "")
@Data
@Entity
@EntityListeners(value = {AuditingEntityListener.class, ReviewCommentAuditListener.class})
public class ReviewCommentEntity extends BaseEntity {
    private String jsonData;
    @Transient
    private List<CommentFieldModel> fieldModelList;
    private String identifier;
    private String confirmResult;
    private String confirmUser;
    private long projectId;
    private long dataVersion;
    private String createUser;
    private String lastModifiedUser;
    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date lastModifiedTime;

    public List<CommentFieldModel> getFieldModelList() {
        if (fieldModelList == null) {
            fieldModelList = JSON.parseArray(jsonData, CommentFieldModel.class);
        }
        return fieldModelList;
    }
    public void setFieldModelList(List<CommentFieldModel> fieldModelList) {
        this.fieldModelList = fieldModelList;
        this.jsonData = JSON.toJSONString(fieldModelList);
    }
}
