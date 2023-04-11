package com.veezean.codereview.server.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 评审意见
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2021/4/26
 */
@Entity
@Table(name = "t_comment", schema = "code_review", catalog = "")
@Data
@EntityListeners(value = AuditingEntityListener.class)
public class CommentEntity extends BaseEntity{
    private String identifier;

    @ManyToOne
    private ProjectEntity project;

    /**
     * start ~ end的格式，用于显示 运算的时候，行号是从0计算的，因此显示的时候，start和end在实际下标上+1
     */
    private String lineRange;

    private String filePath;

    @Column(columnDefinition = "text")
    private String content;

    private String reviewer;

    private Date reviewDate;

    @Column(columnDefinition = "text")
    private String comments;

    private String confirmer;

    private String confirmResult; // 确认结果， 未确认，已修改，待修改，拒绝

    private Date confirmDate;

    @Column(columnDefinition = "text")
    private String confirmNotes; // 确认备注

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;

    /**
     * CAS机制版本号，协作场景下避免更新冲突
     */
    private long casVersion;
}
