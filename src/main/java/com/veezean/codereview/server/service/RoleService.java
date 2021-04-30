package com.veezean.codereview.server.service;

import com.veezean.codereview.server.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2021/4/26
 */
@Service
@Slf4j
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
}
