package com.veezean.codereview.server.entity;

import lombok.Data;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTreeType;
import xyz.erupt.upms.model.EruptOrg;

import javax.persistence.*;

/**
 * 项目信息
 *
 * @author Wang Weiren
 * @since 2021/4/26
 */
@Entity
@Table(name = "t_project", schema = "code_review", catalog = "")
@Data
@Erupt(name = "项目信息",
        linkTree = @LinkTree(
                field = "department"
        ),
        power = @Power(
                export = true
        )
)
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EruptField
    private Long id;

//    @EruptField(
//            views = @View(title = "项目唯一编码"),
//            edit = @Edit(title = "项目唯一编码", notNull = true)
//    )
//    private String projectKey;
    @EruptField(
            views = @View(title = "项目名称"),
            edit = @Edit(title = "项目名称", notNull = true)
    )
    private String projectName;

    @ManyToOne
    @EruptField(
            views = {@View(
                    title = "所属组织",
                    column = "name"
            )},
            edit = @Edit(
                    title = "所属组织",
                    type = EditType.REFERENCE_TREE,
                    referenceTreeType = @ReferenceTreeType(
                            pid = "parentOrg.id"
                    )
            )
    )
    private EruptOrg department;

    @EruptField(
            views = @View(title = "项目描述信息"),
            edit = @Edit(title = "项目描述信息", type = EditType.TEXTAREA)
    )
    private String projectDesc;

}
