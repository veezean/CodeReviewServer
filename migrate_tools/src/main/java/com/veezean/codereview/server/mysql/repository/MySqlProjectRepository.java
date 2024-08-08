package com.veezean.codereview.server.mysql.repository;

import com.veezean.codereview.server.mysql.entity.MySqlProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2021/4/26
 */
@Repository
public interface MySqlProjectRepository extends JpaRepository<MySqlProjectEntity, Long> {
    List<MySqlProjectEntity> findAllByDepartmentId(long deptId);
    List<MySqlProjectEntity> findAllByDepartmentIdIn(List<Long> deptIds);
    List<MySqlProjectEntity> findAllByIdIn(List<Long> projectIds);
}
