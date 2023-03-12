package com.veezean.codereview.server.erupt;

import com.veezean.codereview.server.repository.ProjectRepository;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.model.EruptOrg;
import xyz.erupt.upms.model.EruptUser;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门信息下拉框选择列表处理器
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2022/3/19
 */
@Component
public class DepartmentChoiceFetchHandler implements ChoiceFetchHandler {

    @Resource
    private EruptDao eruptDao;

    @Override
    public List<VLModel> fetch(String[] params) {
        return eruptDao.queryEntityList(EruptOrg.class)
                .stream()
                .map(eruptOrg -> new VLModel(eruptOrg.getCode(), eruptOrg.getName()))
                .collect(Collectors.toList());
    }
}
