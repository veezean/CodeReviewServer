package com.veezean.codereview.server.entity;

import com.veezean.codereview.server.erupt.*;
import lombok.Data;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.expr.ExprBool;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_erupt.RowOperation;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.DateType;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/5
 */
@Data
@Erupt(name = "意见确认")
public class CommentConfirmModel extends BaseModel {

    @EruptField(
            views = @View(title = "确认结果"),
            edit = @Edit(
                    title = "确认结果",
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            type = ChoiceType.Type.RADIO,
                            fetchHandler = CommentConfirmResultFetchHandler.class
                    )
            )
    )
    private String confirmResult; // 确认结果， 未确认，已修改，待修改，拒绝

    @EruptField(
            views = @View(title = "确认说明"),
            edit = @Edit(title = "确认说明",
                    type = EditType.TEXTAREA
            )
    )
    private String confirmNotes; // 确认备注
}
