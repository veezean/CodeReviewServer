package com.veezean.codereview.server.service;

import com.veezean.codereview.server.service.executor.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2024/7/22
 */
@Service
@Slf4j
public class MigrateService {

    @Resource
    private MigrateDepartmentExecutor migrateDepartmentExecutor;
    @Resource
    private MigrateRoleExecutor migrateRoleExecutor;
    @Resource
    private MigrateUserExecutor migrateUserExecutor;
    @Resource
    private MigrateProjectExecutor migrateProjectExecutor;
    @Resource
    private MigrateDictCollectionExecutor migrateDictCollectionExecutor;
    @Resource
    private MigrateColumnFieldExecutor migrateColumnFieldExecutor;


    public void execute() {
        migrateDepartmentExecutor.execute();
        migrateRoleExecutor.execute();
        migrateUserExecutor.execute();
        migrateProjectExecutor.execute();
        migrateDictCollectionExecutor.execute();
        migrateColumnFieldExecutor.execute();
    }

}
