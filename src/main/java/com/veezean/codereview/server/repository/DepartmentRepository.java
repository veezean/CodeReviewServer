package com.veezean.codereview.server.repository;

import com.veezean.codereview.server.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/22
 */
@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}
