package com.veezean.codereview.server.monogo;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson.JSON;
import com.veezean.codereview.server.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 系统初始化数据预置处理服务
 *
 * @author Wang Weiren
 * @since 2024/8/10
 */
@Component
@Slf4j
public class SystemInitialDataProcessor {

    @Resource
    private MongoOperateHelper mongoOperateHelper;

    @PostConstruct
    @Transactional
    public void init() {
        log.info("系统启动，执行系统数据初始化操作 BEGIN");
        boolean needDoInit = checkIfNeedDoInit();
        if (!needDoInit) {
            log.info("非首次启动，无需执行数据初始化操作");
        } else {
            log.info("系统首次部署，开始执行数据初始化预置操作");
            doInitMongoData();
        }
        log.info("系统启动，执行系统数据初始化操作 END");
    }

    private boolean checkIfNeedDoInit() {
        try {
            Criteria criteria = Criteria.where("id").gte(0L);
            Query query = new Query(criteria);
            MongoTemplate mongoTemplate = mongoOperateHelper.getMongoTemplate();
            // 判断系统是否从0冷启动，如果基础配置表中的任意一个表为空，都表示系统没有初始化
            return mongoTemplate.count(query, UserEntity.class) <= 0L
                    || mongoTemplate.count(query, RoleEntity.class) <= 0L
                    || mongoTemplate.count(query, DepartmentEntity.class) <= 0L
                    || mongoTemplate.count(query, ColumnDefineEntity.class) <= 0L
                    || mongoTemplate.count(query, DictCollectionEntity.class) <= 0L;
        } catch (Exception e) {
            log.error("检查系统初始化状态失败", e);
        }
        return false;
    }

    private void doInitMongoData() {
        try {
            initDepartmentData();
            initRoleData();
            initUserData();
            initDictCollectionData();
            initCommentColumnData();
        } catch (Exception e) {
            log.error("系统初始化数据预置操作执行失败", e);
        }
    }

    private void initDepartmentData() {
        log.info("开始预置部门数据");
        URL resource = this.getClass().getClassLoader().getResource("init_mongo_data/t_department.json");
        String json = FileUtil.readString(resource, StandardCharsets.UTF_8.name());
        List<DepartmentEntity> entities = JSON.parseArray(json, DepartmentEntity.class);
        for (DepartmentEntity entity : entities) {
            mongoOperateHelper.getMongoTemplate().save(entity);
        }
        log.info("完成预置部门数据， 共计插入条数：{}", entities.size());
    }


    private void initRoleData() {
        log.info("------开始预置角色数据------");
        URL resource = this.getClass().getClassLoader().getResource("init_mongo_data/t_role.json");
        String json = FileUtil.readString(resource, StandardCharsets.UTF_8.name());
        List<RoleEntity> entities = JSON.parseArray(json, RoleEntity.class);
        for (RoleEntity entity : entities) {
            mongoOperateHelper.getMongoTemplate().save(entity);
        }
        log.info("------完成预置角色数据， 共计插入条数：{}------", entities.size());
    }


    private void initUserData() {
        log.info("------开始预置用户数据------");
        URL resource = this.getClass().getClassLoader().getResource("init_mongo_data/t_user.json");
        String json = FileUtil.readString(resource, StandardCharsets.UTF_8.name());
        List<UserEntity> entities = JSON.parseArray(json, UserEntity.class);
        for (UserEntity entity : entities) {
            mongoOperateHelper.getMongoTemplate().save(entity);
        }
        log.info("------完成预置用户数据， 共计插入条数：{}------", entities.size());
    }

    private void initDictCollectionData() {
        log.info("------开始预置枚举字典值数据------");
        URL resource = this.getClass().getClassLoader().getResource("init_mongo_data/t_dict_collection.json");
        String json = FileUtil.readString(resource, StandardCharsets.UTF_8.name());
        List<DictCollectionEntity> entities = JSON.parseArray(json, DictCollectionEntity.class);
        for (DictCollectionEntity entity : entities) {
            mongoOperateHelper.getMongoTemplate().save(entity);
        }
        log.info("------完成预置枚举字典值数据， 共计插入条数：{}------", entities.size());
    }

    private void initCommentColumnData() {
        log.info("------开始预置评审意见字段定义数据------");
        URL resource = this.getClass().getClassLoader().getResource("init_mongo_data/t_comment_column.json");
        String json = FileUtil.readString(resource, StandardCharsets.UTF_8.name());
        List<ColumnDefineEntity> entities = JSON.parseArray(json, ColumnDefineEntity.class);
        for (ColumnDefineEntity entity : entities) {
            mongoOperateHelper.getMongoTemplate().save(entity);
        }
        log.info("------完成预置评审意见字段定义数据， 共计插入条数：{}------", entities.size());
    }
}
