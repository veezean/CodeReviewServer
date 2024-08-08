package com.veezean.codereview.server.mysql.repository;

import com.veezean.codereview.server.mysql.entity.MySqlUserProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户和项目绑定关系操作
 *
 * @author Veezean
 * @date 2024/4/26
 */
@Repository
public interface MySqlUserProjectRepository extends JpaRepository<MySqlUserProjectEntity, Long> {
    List<MySqlUserProjectEntity> findAllByProjectId(long projectId);

    List<MySqlUserProjectEntity> findAllByAccount(String account);
    void deleteAllByProjectId(long projectId);
}
