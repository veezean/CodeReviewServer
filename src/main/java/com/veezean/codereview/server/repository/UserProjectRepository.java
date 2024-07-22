//package com.veezean.codereview.server.repository;
//
//import com.veezean.codereview.server.entity.UserProjectEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * 用户和项目绑定关系操作
// *
// * @author Veezean
// * @date 2024/4/26
// */
//@Repository
//public interface UserProjectRepository extends MongoRepository<UserProjectEntity, Long> {
//    List<UserProjectEntity> findAllByProjectId(long projectId);
//
//    List<UserProjectEntity> findAllByAccount(String account);
//    void deleteAllByProjectId(long projectId);
//}
