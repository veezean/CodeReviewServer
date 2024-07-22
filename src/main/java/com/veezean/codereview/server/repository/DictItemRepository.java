//package com.veezean.codereview.server.repository;
//
//import com.veezean.codereview.server.entity.DictItem;
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
//public interface DictItemRepository extends MongoRepository<DictItem, Long> {
//    List<DictItem> findAllByCollectionCode(String code);
//    DictItem findFirstByCollectionCodeAndValue(String collectionCode, String itemValue);
//    void deleteAllByCollectionId(long id);
//}
