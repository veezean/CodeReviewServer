package com.veezean.codereview.server.entity;

import lombok.Data;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.model.EruptDict;

import javax.persistence.*;
import java.util.List;

/**
 * 字段定义
 *
 * @author Veezean, 公众号 @架构悟道
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
            edit = @Edit(title = "唯一编码", notNull = true,
                    search = @Search)
    )
    private String columnCode;
    /**
     * 对外显示名称
     */
    @EruptField(
            views = @View(title = "对外显示名称"),
            edit = @Edit(title = "对外显示名称", notNull = true,
                    search = @Search)
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
            edit = @Edit(title = "是否支持导出到表格中", notNull = true,
                    search = @Search
            )
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
            edit = @Edit(title = "是否系统预置", notNull = true,
                    search = @Search)
    )
    private boolean systemInitialization;
    /**
     * 是否显示在表格中
     */
    @EruptField(
            views = @View(title = "是否显示在表格中"),
            edit = @Edit(title = "是否显示在表格中", notNull = true,
                    search = @Search)
    )
    private boolean showInTable;
    /**
     * 是否显示在新增界面
     */
    @EruptField(
            views = @View(title = "是否显示在新增界面"),
            edit = @Edit(title = "是否显示在新增界面", notNull = true,
                    search = @Search)
    )
    private boolean showInAddPage;
    /**
     * 是否显示在确认界面
     */
    @EruptField(
            views = @View(title = "是否显示在确认界面"),
            edit = @Edit(title = "是否显示在确认界面", notNull = true,
                    search = @Search)
    )
    private boolean showInComfirmPage;
    /**
     * 是否可编辑
     */
    @EruptField(
            views = @View(title = "是否可编辑"),
            edit = @Edit(title = "是否可编辑", notNull = true,
                    search = @Search)
    )
    private boolean editable;
    /**
     * 确认界面是否允许修改
     */
    @EruptField(
            views = @View(title = "确认界面是否允许修改"),
            edit = @Edit(title = "确认界面是否允许修改", notNull = true,
                    search = @Search)
    )
    private boolean editableInConfirmPage;
    /**
     * 输入类型，单行、多行、下拉框、radio等
     */
    @EruptField(
            views = @View(title = "输入类型"),
            edit = @Edit(title = "输入类型",
                    notNull = true,
                    search = @Search,
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
    @ManyToOne
    @EruptField(
            views = @View(title = "下拉框类型的动态拉取Code", column = "name"),
            edit = @Edit(title = "下拉框类型的动态拉取Code",
                    desc = "当输入类型为下拉框的时候需要填写，从服务端拉取指定的数据，支持的值请参见帮助文档",
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "name")
            )
    )
    private EruptDict comboBoxDictCode;

    @Transient
    private List<String> enumValues;

    /**
     * 是否为确认界面的独有字段
     */
    @EruptField(
            views = @View(title = "是否为确认界面的独有字段"),
            edit = @Edit(title = "是否为确认界面的独有字段", notNull = true,
                    search = @Search)
    )
    private boolean confirmProp;

    /**
     * 是否必填
     */
    @EruptField(
            views = @View(title = "是否必填"),
            edit = @Edit(title = "是否必填", notNull = true,
                    search = @Search)
    )
    private boolean required;
}
