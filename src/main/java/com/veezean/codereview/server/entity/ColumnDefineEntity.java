package com.veezean.codereview.server.entity;

import com.veezean.codereview.server.model.ValuePair;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 字段定义
 *
 * @author Veezean
 * @since 2022/5/21
 */
@Data
@Document(collection = "t_comment_column")
public class ColumnDefineEntity extends MongoLongIdEntity {

    /**
     * 字段唯一编码
     */
    @Indexed(unique = true)
    private String columnCode;
    /**
     * 对外显示名称
     */
    private String showName;
    /**
     * 排序号
     */
    private int sortIndex;

    /**
     * 是否支持导出到表格中
     */
    private boolean supportInExcel;

    /**
     * excel占用列宽
     */
    private int excelColumnWidth;

    /**
     * 是否系统预置，预置字段不允许删除或者更改
     */
    private boolean systemInitialization;
    /**
     * 是否显示在IDEA表格中
     */
    private boolean showInIdeaTable;
    /**
     * web端表格中该字段占用宽度
     */
    private int webTableColumnWidth;
    /**
     * 是否显示在WEB端表格中
     */
    private boolean showInWebTable;
    /**
     * 是否显示在新增界面
     */
    private boolean showInAddPage;
    /**
     * 是否显示在编辑界面
     */
    private boolean showInEditPage;
    /**
     * 是否显示在确认界面
     */
    private boolean showInConfirmPage;
    /**
     * 新增时是否可编辑
     */
    private boolean editableInAddPage;
    /**
     * 修改时是否可编辑
     */
    private boolean editableInEditPage;
    /**
     * 确认界面是否可编辑
     */
    private boolean editableInConfirmPage;
    /**
     * 输入类型，单行(TEXT)、多行（TEXTAREA）、下拉框（COMBO_BOX）、日期（DATE）等
     */
    private String inputType;

    /**
     * 下拉框类型的候选项
     */
    private String dictCollectionCode;

    @Transient
    private List<ValuePair> enumValues;

    /**
     * 是否必填
     */
    private boolean required;
}
