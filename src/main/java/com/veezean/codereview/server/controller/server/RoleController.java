package com.veezean.codereview.server.controller.server;

import com.veezean.codereview.server.entity.RoleEntity;
import com.veezean.codereview.server.model.Response;
import com.veezean.codereview.server.model.SaveRoleReqBody;
import com.veezean.codereview.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
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

    @GetMapping("/getRoleById")
    public Response<RoleEntity> getRoleById(@RequestParam long roleId) {
        RoleEntity roleEntity = roleService.getRoleById(roleId);
        return Response.simpleSuccessResponse(roleEntity);
    }

    @GetMapping("/bindRole")
    public Response<String> bindRole(@RequestParam String account, @RequestParam long roleId) {
        roleService.bindRole(account, roleId);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/unbindRole")
    public Response<String> unbindRole(@RequestParam String account, @RequestParam long roleId) {
        roleService.unbindRole(account, roleId);
        return Response.simpleSuccessResponse();
    }
}
