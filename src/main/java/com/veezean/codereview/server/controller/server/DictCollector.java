package com.veezean.codereview.server.controller.server;

import com.veezean.codereview.server.entity.DictCollectionEntity;
import com.veezean.codereview.server.entity.DictItemEntity;
import com.veezean.codereview.server.model.Response;
import com.veezean.codereview.server.model.SaveDictCollectionReqBody;
import com.veezean.codereview.server.model.SaveDictItemReqBody;
import com.veezean.codereview.server.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/24
 */
@RestController
@RequestMapping("/server/dict")
public class DictCollector {
    @Autowired
    private DictService dictService;

    @PostMapping("/createDictCollection")
    public Response<String> createDictCollection(@RequestBody SaveDictCollectionReqBody reqBody) {
        dictService.createDictCollection(reqBody);
        return Response.simpleSuccessResponse();
    }
    @PostMapping("/modifyDictCollection")
    public Response<String> modifyDictCollection(@RequestBody SaveDictCollectionReqBody reqBody) {
        dictService.modifyDictCollection(reqBody);
        return Response.simpleSuccessResponse();
    }
    @GetMapping("/deleteCollection")
    public Response<String> deleteCollection(@RequestParam Long id) {
         dictService.deleteDictCollection(id);
        return Response.simpleSuccessResponse();
    }
    @GetMapping("/deleteCollections")
    public Response<String> deleteCollections(@RequestParam List<Long> ids) {
         dictService.deleteDictCollections(ids);
        return Response.simpleSuccessResponse();
    }
    @GetMapping("/queryDictCollection")
    public Response<DictCollectionEntity> queryDictCollection(@RequestParam String collectionCode) {
        DictCollectionEntity entity = dictService.queryCollection(collectionCode);
        return Response.simpleSuccessResponse(entity);
    }
    @GetMapping("/queryDictCollections")
    public Response<List<DictCollectionEntity>> queryDictCollections() {
        List<DictCollectionEntity> dictCollectionEntities = dictService.queryCollections();
        return Response.simpleSuccessResponse(dictCollectionEntities);
    }
    @GetMapping("/queryDictItem")
    public Response<DictItemEntity> queryDictItem(@RequestParam Long itemId) {
        DictItemEntity dictItemEntity = dictService.queryItemById(itemId);
        return Response.simpleSuccessResponse(dictItemEntity);
    }
    @GetMapping("/queryDictItems")
    public Response<List<DictItemEntity>> queryDictItems(@RequestParam String collectionCode) {
        List<DictItemEntity> dictItemEntities = dictService.queryDictItemsByCollectionCode(collectionCode);
        return Response.simpleSuccessResponse(dictItemEntities);
    }
    @PostMapping("/createOrModifyDictItem")
    public Response<String> createOrModifyDictItem(@RequestBody SaveDictItemReqBody reqBody) {
        dictService.createOrModifyDictItem(reqBody);
        return Response.simpleSuccessResponse();
    }
    @GetMapping("/deleteDictItem")
    public Response<String> deleteDictItem(@RequestParam long dictItemId) {
        dictService.deleteDictItem(dictItemId);
        return Response.simpleSuccessResponse();
    }
}
