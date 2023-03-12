package com.veezean.codereview.server.entity;

import com.veezean.codereview.server.erupt.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.expr.ExprBool;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_erupt.RowOperation;
import xyz.erupt.annotation.sub_field.*;
import xyz.erupt.annotation.sub_field.sub_edit.*;

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
@Erupt(name = "评审意见",
        rowOperation = {
                @RowOperation(
                        title = "意见确认",
                        mode = RowOperation.Mode.SINGLE,
                        show = @ExprBool(params = "confirmButton", exprHandler = CommentFieldRenderHandler.class),
                        eruptClass = CommentConfirmModel.class,
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
            views = @View(title = "唯一ID"),
            edit = @Edit(title = "唯一ID", readonly = @Readonly,
                    ifRender = @ExprBool(params = "identifier", exprHandler = CommentFieldRenderHandler.class))
    )
    private String identifier;

    @ManyToOne
    @EruptField(
            views = @View(title = "归属项目", column = "projectName"),
            edit = @Edit(title = "归属项目",
                    search = @Search,
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "projectName"))
    )
    private ProjectEntity project;

    /**
     * start ~ end的格式，用于显示 运算的时候，行号是从0计算的，因此显示的时候，start和end在实际下标上+1
     */
    @EruptField(
            views = @View(title = "代码行号范围"),
            edit = @Edit(title = "代码行号范围", readonly = @Readonly(add = false, edit = true))
    )
    private String lineRange;

    @EruptField(
            views = @View(title = "代码路径"),
            edit = @Edit(title = "代码路径",
                    type = EditType.INPUT,
                    inputType = @InputType(fullSpan = true),
                    readonly = @Readonly(add = false, edit = true))
    )
    private String filePath;

    @EruptField(
            views = @View(title = "代码片段",
                    type = ViewType.CODE),
            edit = @Edit(title = "代码片段",
                    readonly = @Readonly(add = false),
                    type = EditType.CODE_EDITOR,
                    codeEditType = @CodeEditorType(language = "TEXT")
            )
    )
    @Column(columnDefinition = "text")
    private String content;

    @Transient //由于该字段不需要持久化，所以使用该注解修饰
    @EruptField(
            edit = @Edit(title = "评审意见信息",
                    type = EditType.DIVIDE)
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
                    search = @Search,
                    choiceType = @ChoiceType(
                            fetchHandler = UserChoiceFetchHandler.class
                    ))
    )
    private String reviewer;

    @EruptField(
            views = @View(title = "评审时间"),
            edit = @Edit(
                    title = "评审时间",
                    search = @Search,
                    type = EditType.DATE,
                    dateType = @DateType(type = DateType.Type.DATE_TIME),
                    readonly = @Readonly(params = "reviewTime", exprHandler = CommentEditReadonlyHandler.class),
                    ifRender = @ExprBool(params = "reviewTime", exprHandler = CommentFieldRenderHandler.class)
            )
    )
    private Date reviewDate;

    @EruptField(
            views = @View(title = "评审意见"),
            edit = @Edit(title = "评审意见",
                    notNull = true,
                    readonly = @Readonly(params = "comments", exprHandler = CommentEditReadonlyHandler.class),
                    type = EditType.TEXTAREA
            )
    )
    @Column(columnDefinition = "text")
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
                    search = @Search,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            fetchHandler = UserChoiceFetchHandler.class
                    ))
    )
    private String confirmer;

    @EruptField(
            views = @View(title = "确认结果"),
            edit = @Edit(
                    title = "确认结果",
                    search = @Search,
                    ifRender = @ExprBool(params = "confirmResult", exprHandler = CommentFieldRenderHandler.class),
                    readonly = @Readonly(params = "confirmResult", exprHandler = CommentEditReadonlyHandler.class)
            )
    )
    private String confirmResult; // 确认结果， 未确认，已修改，待修改，拒绝

    @EruptField(
            views = @View(title = "意见确认时间"),
            edit = @Edit(
                    title = "意见确认时间",
                    search = @Search,
                    type = EditType.DATE,
                    dateType = @DateType(type = DateType.Type.DATE_TIME),
                    ifRender = @ExprBool(params = "confirmTime", exprHandler = CommentFieldRenderHandler.class),
                    readonly = @Readonly(params = "confirmTime", exprHandler = CommentEditReadonlyHandler.class)
            )
    )
    private Date confirmDate;

    @EruptField(
            views = @View(title = "确认说明"),
            edit = @Edit(title = "确认说明",
                    readonly = @Readonly(params = "confirmNotes", exprHandler = CommentEditReadonlyHandler.class),
                    ifRender = @ExprBool(params = "confirmNotes", exprHandler = CommentFieldRenderHandler.class),
                    type = EditType.TEXTAREA)
    )
    @Column(columnDefinition = "text")
    private String confirmNotes; // 确认备注

    @Transient //由于该字段不需要持久化，所以使用该注解修饰
    @EruptField(
            edit = @Edit(title = "其它补充信息", type = EditType.DIVIDE)
    )
    private String additionalDivide;

    @EruptField(
            views = @View(title = "记录提交时间"),
            edit = @Edit(title = "记录提交时间",
                    type = EditType.DATE,
                    search = @Search,
                    dateType = @DateType(type = DateType.Type.DATE_TIME),
                    ifRender = @ExprBool(params = "confirmNotes", exprHandler = CommentFieldRenderHandler.class))
    )
    @CreatedDate
    private Date createTime;

    @EruptField(
            views = @View(title = "最后更新时间"),
            edit = @Edit(
                    title = "最后更新时间",
                    search = @Search,
                    type = EditType.DATE,
                    dateType = @DateType(type = DateType.Type.DATE_TIME),
                    ifRender = @ExprBool(params = "confirmNotes", exprHandler = CommentFieldRenderHandler.class)
            )
    )
    @LastModifiedDate
    private Date updateTime;

}
