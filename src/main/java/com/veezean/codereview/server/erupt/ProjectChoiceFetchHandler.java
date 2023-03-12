package com.veezean.codereview.server.erupt;

import com.veezean.codereview.server.repository.ProjectRepository;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.model.EruptUser;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目信息下拉框选择列表处理器
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2022/3/19
 */
@Component
public class ProjectChoiceFetchHandler implements ChoiceFetchHandler {

    @Resource
    private ProjectRepository projectRepository;

    @Override
    public List<VLModel> fetch(String[] params) {
        // TODO 此处需要优化下，改为反馈当前用户有权访问的列表
//        return projectRepository.findAll()
//                .stream()
//                .map(projectEntity -> new VLModel(projectEntity.getProjectKey(), projectEntity.getProjectName()))
//                .collect(Collectors.toList());
        return new ArrayList<>();
    }
}
