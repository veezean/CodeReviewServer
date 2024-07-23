package com.veezean.codereview.server.controller.server;

import com.veezean.codereview.server.entity.RoleEntity;
import com.veezean.codereview.server.model.Response;
import com.veezean.codereview.server.model.SaveRoleReqBody;
import com.veezean.codereview.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理接口
 *
 * @author Veezean
 * @since 2023/3/23
 */
@RestController
@RequestMapping("/server/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/getRoles")
    public Response<List<RoleEntity>> getRoles() {
        List<RoleEntity> roles = roleService.getRoles();
        return Response.simpleSuccessResponse(roles);
    }

    @PostMapping("/addRole")
    public Response<String> addRole(@RequestBody SaveRoleReqBody reqBody) {
        roleService.addRole(reqBody);
        return Response.simpleSuccessResponse();
    }

    @PostMapping("/modifyRole")
    public Response<String> modifyRole(@RequestParam long roleId, @RequestBody SaveRoleReqBody reqBody) {
        roleService.modifyRole(roleId, reqBody);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/deleteRole")
    public Response<String> deleteRole(@RequestParam long roleId) {
        roleService.deleteRole(roleId);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/deleteRoles")
    public Response<String> deleteRole(@RequestParam List<Long> roleIds) {
        roleService.deleteRoles(roleIds);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/getRoleById")
    public Response<RoleEntity> getRoleById(@RequestParam long roleId) {
        RoleEntity roleEntity = roleService.getRoleById(roleId);
        return Response.simpleSuccessResponse(roleEntity);
    }

    @GetMapping("/getUserCanAccessPages")
    public Response<List<String>> getUserCanAccessPages() {
        List<String> userCanAccessPages = roleService.getUserCanAccessPages();
        return Response.simpleSuccessResponse(userCanAccessPages);
    }
}
