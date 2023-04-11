package com.veezean.codereview.server.entity;

import lombok.Data;

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
public class ColumnDefineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 字段唯一编码
     */
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
     * 是否显示在表格中
     */
    private boolean showInTable;
    /**
     * 是否显示在新增界面
     */
    private boolean showInAddPage;
    /**
     * 是否显示在确认界面
     */
    private boolean showInComfirmPage;
    /**
     * 是否可编辑
     */
    private boolean editable;
    /**
     * 确认界面是否允许修改
     */
    private boolean editableInConfirmPage;
    /**
     * 输入类型，单行、多行、下拉框、radio等
     */
    private String inputType;

    /**
     * 下拉框类型的候选项
     */
    @ManyToOne
    private DictCollectionEntity dictCollection;

    @Transient
    private List<String> enumValues;

    /**
     * 是否为确认界面的独有字段
     */
    private boolean confirmProp;

    /**
     * 是否必填
     */
    private boolean required;
}
