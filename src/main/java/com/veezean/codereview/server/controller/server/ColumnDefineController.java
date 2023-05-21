package com.veezean.codereview.server.controller.server;

import com.veezean.codereview.server.entity.ColumnDefineEntity;
import com.veezean.codereview.server.model.Response;
import com.veezean.codereview.server.model.SaveColumnDefineReqBody;
import com.veezean.codereview.server.service.ColumnDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/24
 */
@RestController
@RequestMapping("/server/column")
public class ColumnDefineController {
    @Autowired
    private ColumnDefineService columnDefineService;

    @PostMapping("/createOrModifyColumn")
    public Response<String> createOrModifyColumn(@RequestParam long columnId,
                                         @RequestBody SaveColumnDefineReqBody reqBody) {
        columnDefineService.createOrModifyColumn(columnId, reqBody);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/deleteColumn")
    public Response<String> deleteColumn(@RequestParam long columnId) {
        columnDefineService.deleteColumn(columnId);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/deleteColumns")
    public Response<String> deleteColumns(@RequestParam List<Long> columnIds) {
        columnDefineService.deleteColumns(columnIds);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/queryColumn")
    public Response<ColumnDefineEntity> queryColumn(@RequestParam long columnId) {
        ColumnDefineEntity columnDefineEntity = columnDefineService.queryColumn(columnId);
        return Response.simpleSuccessResponse(columnDefineEntity);
    }

    @GetMapping("/queryColumns")
    public Response<List<ColumnDefineEntity>> queryColumns() {
        List<ColumnDefineEntity> defineEntities = columnDefineService.queryColumns();
        return Response.simpleSuccessResponse(defineEntities);
    }
}
