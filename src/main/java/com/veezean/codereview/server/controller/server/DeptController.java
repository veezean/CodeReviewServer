package com.veezean.codereview.server.controller.server;

import cn.hutool.core.lang.tree.Tree;
import com.veezean.codereview.server.entity.DepartmentEntity;
import com.veezean.codereview.server.model.Response;
import com.veezean.codereview.server.model.SaveDeptReqBody;
import com.veezean.codereview.server.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/23
 */
@RestController
@RequestMapping("/server/dept")
public class DeptController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/getDeptTree")
    public Response<List<Tree<Long>>> getDeptTree(@RequestParam(required = false) Long topDeptId) {
        List<Tree<Long>> deptTree = departmentService.getDeptTree(topDeptId);
        return Response.simpleSuccessResponse(deptTree);
    }

    @PostMapping("/createDept")
    public Response<String> createDept(@RequestBody SaveDeptReqBody reqBody) {
        departmentService.addDept(reqBody);
        return Response.simpleSuccessResponse();
    }

    @PostMapping("/modifyDept")
    public Response<String> modifyDept(@RequestParam long deptId, @RequestBody SaveDeptReqBody reqBody) {
        departmentService.modifyDept(deptId, reqBody);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/deleteDept")
    public Response<String> createDept(@RequestParam long deptId) {
        departmentService.deleteDept(deptId);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/queryById")
    public Response<DepartmentEntity> queryById(@RequestParam long deptId) {
        DepartmentEntity departmentEntity = departmentService.getByDeptId(deptId);
        return Response.simpleSuccessResponse(departmentEntity);
    }
}
