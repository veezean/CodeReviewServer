package com.veezean.codereview.server.monogo.repository;

import com.veezean.codereview.server.monogo.entity.ProjectEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2021/4/26
 */
@Repository
public interface ProjectRepository extends MongoRepository<ProjectEntity, Long> {
    List<ProjectEntity> findAllByDepartmentId(long deptId);
    List<ProjectEntity> findAllByDepartmentIdIn(List<Long> deptIds);
    List<ProjectEntity> findAllByIdIn(List<Long> projectIds);
}
