package com.veezean.codereview.server.entity;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.expr.ExprBool;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.VL;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 字段定义
 *
 * @author Wang Weiren
 * @since 2022/5/21
 */
@Entity
@Table(name = "t_comment_column", schema = "code_review", catalog = "")
@Data
@Erupt(name = "评审字段定义")
public class ColumnDefineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EruptField
    private Long id;

    /**
     * 字段唯一编码
     */
    @EruptField(
            views = @View(title = "唯一编码"),
            edit = @Edit(title = "唯一编码", notNull = true)
    )
    private String columnCode;
    /**
     * 对外显示名称
     */
    @EruptField(
            views = @View(title = "对外显示名称"),
            edit = @Edit(title = "对外显示名称", notNull = true)
    )
    private String showName;
    /**
     * 排序号
     */
    @EruptField(
            views = @View(title = "排序号"),
            edit = @Edit(title = "排序号", notNull = true)
    )
    private int sortIndex;

    /**
     * 是否支持导出到表格中
     */
    @EruptField(
            views = @View(title = "是否支持导出到表格中"),
            edit = @Edit(title = "是否支持导出到表格中", notNull = true)
    )
    private boolean supportInExcel;

    /**
     * excel占用列宽
     */
    @EruptField(
            views = @View(title = "excel占用列宽"),
            edit = @Edit(title = "excel占用列宽", notNull = true)
    )
    private int excelColumnWidth;

    /**
     * 是否系统预置，预置字段不允许删除或者更改
     */
    @EruptField(
            views = @View(title = "是否系统预置"),
            edit = @Edit(title = "是否系统预置", notNull = true)
    )
    private boolean systemInitialization;
    /**
     * 是否显示在表格中
     */
    @EruptField(
            views = @View(title = "是否显示在表格中"),
            edit = @Edit(title = "是否显示在表格中", notNull = true)
    )
    private boolean showInTable;
    /**
     * 是否显示在新增界面
     */
    @EruptField(
            views = @View(title = "是否显示在新增界面"),
            edit = @Edit(title = "是否显示在新增界面", notNull = true)
    )
    private boolean showInAddPage;
    /**
     * 是否显示在确认界面
     */
    @EruptField(
            views = @View(title = "是否显示在确认界面"),
            edit = @Edit(title = "是否显示在确认界面", notNull = true)
    )
    private boolean showInComfirmPage;
    /**
     * 是否可编辑
     */
    @EruptField(
            views = @View(title = "是否可编辑"),
            edit = @Edit(title = "是否可编辑", notNull = true)
    )
    private boolean editable;
    /**
     * 确认界面是否允许修改
     */
    @EruptField(
            views = @View(title = "确认界面是否允许修改"),
            edit = @Edit(title = "确认界面是否允许修改", notNull = true)
    )
    private boolean editableInConfirmPage;
    /**
     * 输入类型，单行、多行、下拉框、radio等
     */
    @EruptField(
            views = @View(title = "输入类型"),
            edit = @Edit(title = "输入类型",
                    notNull = true,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            type = ChoiceType.Type.RADIO,
                            vl = {
                                    @VL(value = "TEXT", label = "单行输入"),
                                    @VL(value = "TEXTAREA", label = "多行输入"),
                                    @VL(value = "COMBO_BOX", label = "下拉框")
                            }
                    )
            )
    )
    private String inputType;

    /**
     * 下拉框类型的候选项
     */
    @EruptField(
            views = @View(title = "下拉框类型的候选项"),
            edit = @Edit(title = "下拉框类型的候选项", notNull = true,
                    desc = "当输入类型为下拉框的时候需要填写，多个字段之间用逗号分隔"
            )
    )
    private String enumValuesString;

    @Transient
    private List<String> enumValues;

    public List<String> getEnumValues() {
        if (!CollectionUtils.isEmpty(enumValues)) {
            return enumValues;
        }
        enumValues = Arrays.stream(Optional.ofNullable(enumValuesString)
                .map(string -> string.split(","))
                .orElse(new String[0])).collect(Collectors.toList());
        return enumValues;
    }

    /**
     * 是否为确认界面的独有字段
     */
    @EruptField(
            views = @View(title = "确认界面显示"),
            edit = @Edit(title = "确认界面显示", notNull = true)
    )
    private boolean confirmProp;

    /**
     * 是否必填
     */
    @EruptField(
            views = @View(title = "是否必填"),
            edit = @Edit(title = "是否必填", notNull = true)
    )
    private boolean required;
}
