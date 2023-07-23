package com.veezean.codereview.server.service.collector;

import com.veezean.codereview.server.model.ValuePair;
import com.veezean.codereview.server.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户列表信息下拉框内容生成器
 *
 * @author Veezean
 * @since 2023/3/12
 */
@Component
public class ProjectEnumDynamicCollector implements IEnumDynamicCollector {
    private static final String SERVER_DYNAMIC_USERLIST = "ProjectList";

    @Autowired
    private ProjectService projectService;

    @Override
    public String name() {
        return SERVER_DYNAMIC_USERLIST;
    }

    @Override
    public List<ValuePair> doCollect() {
        return projectService.queryProjectInDept("0")
                .stream()
                .map(projectEntity -> new ValuePair(String.valueOf(projectEntity.getId()),
                        projectEntity.getProjectName()))
                .collect(Collectors.toList());
    }
}
