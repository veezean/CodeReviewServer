package com.veezean.codereview.server.repository;

import com.veezean.codereview.server.entity.ReviewCommentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/6/25
 */
@Repository
public interface ReviewCommentRepository extends MongoRepository<ReviewCommentEntity, String> {
    ReviewCommentEntity findFirstByIdAndStatus(String identifier, int status);
    void deleteAllByIdIn(List<String> ids);
    void deleteAllById(String identifier);
    List<ReviewCommentEntity> findAllByIdInAndStatus(List<String> ids, int status);
}
