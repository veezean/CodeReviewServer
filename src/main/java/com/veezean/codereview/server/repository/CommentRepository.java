package com.veezean.codereview.server.repository;

import com.veezean.codereview.server.entity.CommentEntity;
import com.veezean.codereview.server.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2021/4/26
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    CommentEntity findFirstByIdentifier(String identifier);
    List<CommentEntity> findAllByConfirmer(String confirmer);
    List<CommentEntity> findAllByProject(ProjectEntity projectEntity);
    List<CommentEntity> findAllByProjectAndReviewer(ProjectEntity projectEntity, String reviewer);
    List<CommentEntity> findAllByProjectAndConfirmer(ProjectEntity projectEntity, String confirmer);

}
