package com.veezean.codereview.server.controller.server;

import com.veezean.codereview.server.model.QueryStatReqBody;
import com.veezean.codereview.server.model.Response;
import com.veezean.codereview.server.model.StatResultData;
import com.veezean.codereview.server.service.stats.DataStatService;
import com.veezean.codereview.server.service.stats.HomePageStatData;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/23
 */
@RestController
@RequestMapping("/server/stat")
public class StatController {

    @Resource
    private DataStatService dataStatService;

    @PostMapping("/query")
    public Response<StatResultData> query(@RequestBody QueryStatReqBody reqBody) {
        StatResultData stats = dataStatService.stats(reqBody);
        return Response.simpleSuccessResponse(stats);
    }

    @GetMapping("/homestat")
    public Response<HomePageStatData> homestat() {
        HomePageStatData stats = dataStatService.homestat();
        return Response.simpleSuccessResponse(stats);
    }

}
