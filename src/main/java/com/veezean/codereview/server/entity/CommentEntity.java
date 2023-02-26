package com.veezean.codereview.server.entity;

import com.veezean.codereview.server.erupt.CommentDataProxy;
import com.veezean.codereview.server.erupt.ConfirmCommentHandler;
import com.veezean.codereview.server.erupt.UserChoiceFetchHandler;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_erupt.RowOperation;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.ViewType;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.CodeEditorType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;

import javax.persistence.*;
import java.util.Date;

/**
 * 评审意见
 *
 * @author Wang Weiren
 * @since 2021/4/26
 */
@Entity
@Table(name = "t_comment", schema = "code_review", catalog = "")
@Data
@EntityListeners(value = AuditingEntityListener.class)
@Erupt(name = "评审意见",
        rowOperation = {
                @RowOperation(
                        title = "确认",
                        mode = RowOperation.Mode.SINGLE,
                        eruptClass = CommentEntity.class,
                        operationParam = "confirm",
                        operationHandler = ConfirmCommentHandler.class)
        },
        power = @Power(
                export = true
        ),
        dataProxy = CommentDataProxy.class)
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EruptField
    private Long id;

    @EruptField(
            views = @View(title = "评审意见编码"),
            edit = @Edit(title = "评审意见编码", show = false)
    )
    private long identifier;

    @ManyToOne
    @EruptField(
            views = @View(title = "归属项目", column = "projectName"),
            edit = @Edit(title = "归属项目",
                    search = @Search,
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "projectName"))
    )
    private ProjectEntity project;


    @EruptField(
            views = @View(title = "代码路径"),
            edit = @Edit(title = "代码路径")
    )
    private String filePath;

    /**
     * start ~ end的格式，用于显示 运算的时候，行号是从0计算的，因此显示的时候，start和end在实际下标上+1
     */
    @EruptField(
            views = @View(title = "代码行号范围"),
            edit = @Edit(title = "代码行号范围")
    )
    private String lineRange;

    @EruptField(
            views = @View(title = "代码片段",
            type = ViewType.CODE),
            edit = @Edit(title = "代码片段",
                    type = EditType.CODE_EDITOR,
                    codeEditType = @CodeEditorType(language = "TEXT")
            )
    )
    private String content;

    @Transient //由于该字段不需要持久化，所以使用该注解修饰
    @EruptField(
            edit = @Edit(title = "评审意见信息", type = EditType.DIVIDE)
    )
    private String reviewDivide;

//    @ManyToOne
//    @EruptField(
//            views = @View(title = "评审人员", column = "name"),
//            edit = @Edit(title = "评审人员",
//                    notNull = true,
//                    type = EditType.REFERENCE_TABLE)
//    )
//    private EruptUser reviewer;

    @EruptField(
            views = @View(title = "评审人员"),
            edit = @Edit(title = "评审人员",
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            fetchHandler = UserChoiceFetchHandler.class
                    ))
    )
    private String commitUser;

    @EruptField(
            views = @View(title = "评审时间"),
            edit = @Edit(
                    title = "评审时间",
                    show = false
            )
    )
    private Date reviewTime;

    @EruptField(
            views = @View(title = "评审意见"),
            edit = @Edit(title = "评审意见",
                    notNull = true,
                    type = EditType.TEXTAREA
            )
    )
    private String comments;

    @Transient //由于该字段不需要持久化，所以使用该注解修饰
    @EruptField(
            edit = @Edit(title = "评审意见确认信息", type = EditType.DIVIDE)
    )
    private String confirmDivide;

//    @ManyToOne
//    @EruptField(
//            views = @View(title = "确认人员", column = "name"),
//            edit = @Edit(title = "确认人员",
//                    notNull = true,
//                    type = EditType.REFERENCE_TABLE,
//                    referenceTableType = @ReferenceTableType())
//    )
//    private EruptUser confirmer;

    @EruptField(
            views = @View(title = "确认人员"),
            edit = @Edit(title = "确认人员",
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            fetchHandler = UserChoiceFetchHandler.class
                    ))
    )
    private String confirmer;

    @EruptField(
            views = @View(title = "意见确认时间"),
            edit = @Edit(
                    title = "意见确认时间",
                    show = false
            )
    )
    private Date confirmTime;

    private String confirmResult; // 确认结果， 未确认，已修改，待修改，拒绝

    @EruptField(
            views = @View(title = "确认说明"),
            edit = @Edit(title = "确认说明",
            type = EditType.TEXTAREA)
    )
    private String confirmNotes; // 确认备注

    @EruptField(
            views = @View(title = "记录提交时间"),
            edit = @Edit(title = "记录提交时间",
                    show = false)
    )
    @CreatedDate
    private Date createTime;

    @EruptField(
            views = @View(title = "最后更新时间"),
            edit = @Edit(
                    title = "最后更新时间",
                    show = false
            )
    )
    @LastModifiedDate
    private Date updateTime;
}
