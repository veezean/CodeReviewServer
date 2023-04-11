package com.veezean.codereview.server.repository;

import com.veezean.codereview.server.entity.ReviewCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/25
 */
@Repository
public interface ReviewCommentRepository extends JpaRepository<ReviewCommentEntity, Long>,
        JpaSpecificationExecutor<ReviewCommentEntity> {

    ReviewCommentEntity findFirstByIdentifier(String identifier);

}
