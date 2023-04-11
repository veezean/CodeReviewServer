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

    @PostMapping("/createColumn")
    public Response<String> createColumn(@RequestBody SaveColumnDefineReqBody reqBody) {
        columnDefineService.createColumn(reqBody);
        return Response.simpleSuccessResponse();
    }

    @PostMapping("/modifyColumn")
    public Response<String> modifyColumn(@RequestParam long columnId,
                                         @RequestBody SaveColumnDefineReqBody reqBody) {
        columnDefineService.modifyColumn(columnId, reqBody);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/deleteColumn")
    public Response<String> deleteColumn(@RequestParam long columnId) {
        columnDefineService.deleteColumn(columnId);
        return Response.simpleSuccessResponse();
    }

    @GetMapping("/queryColumns")
    public Response<List<ColumnDefineEntity>> queryColumns() {
        List<ColumnDefineEntity> defineEntities = columnDefineService.queryColumns();
        return Response.simpleSuccessResponse(defineEntities);
    }
}
