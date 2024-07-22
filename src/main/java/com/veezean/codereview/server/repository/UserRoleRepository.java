//package com.veezean.codereview.server.repository;
//
//import com.veezean.codereview.server.entity.UserRoleEntity;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * <类功能简要描述>
// *
// * @author Veezean
// * @since 2023/3/22
// */
//@Repository
//public interface UserRoleRepository extends MongoRepository<UserRoleEntity, Long> {
//    List<UserRoleEntity> findAllByUserId(long userId);
//
//    List<UserRoleEntity> findAllByRoleId(long roleId);
//
//    void deleteAllByRoleId(long roleId);
//
//    void deleteAllByUserId(long userId);
//
//    UserRoleEntity findFirstByUserIdAndRoleId(long userId, long roleId);
//
//    void deleteAllByUserIdAndRoleId(long userId, long roleId);
//}
