package com.ihomefnt.o2o.api.controller.program.customgoods;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.program.customgoods.request.*;
import com.ihomefnt.o2o.intf.domain.program.customgoods.response.*;
import com.ihomefnt.o2o.intf.domain.program.vo.request.QueryCabinetPropertyListRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.QueryCabinetPropertyListResponseNew;
import com.ihomefnt.o2o.intf.service.program.customgoods.CurtainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * app支持窗帘选配API
 *
 * @author liyonggang
 * @create 2019-03-18 15:49
 */
@RestController
@RequestMapping("/customGoods/curtain")
@Api(tags = "【APP-BOM定制品API】")
public class CurtainController {

    @Autowired
    private CurtainService curtainService;

    /**
     * 根据物料id查询物料详情
     *
     * @param request 查询参数
     * @return 物料详情
     */
    @ApiOperation(value = "根据物料id查询物料详情", notes = "传入组合id+组件id+物料id")
    @RequestMapping(value = "/queryMaterialDetail", method = RequestMethod.POST)
    public HttpBaseResponse<DetailVO> queryMaterialDetail(@RequestBody DetailQueryRequest request) {
        return HttpBaseResponse.success(curtainService.queryMaterialDetail(request));
    }

    /**
     * 根据组合id查询组合详情
     *
     * @param request 查询参数
     * @return 组合详情
     */
    @ApiOperation(value = "根据组合id查询组合详情", notes = "传入组合id")
    @RequestMapping(value = "/queryGroupDetail", method = RequestMethod.POST)
    public HttpBaseResponse<DetailVO> queryGroupDetail(@RequestBody DetailQueryRequest request) {
        return HttpBaseResponse.success(curtainService.queryGroupDetail(request));
    }


    /**
     * 根据组合分类id查询物料筛选项
     *
     * @param request 查询参数
     * @return 筛选项集合
     */
    @ApiOperation(value = "根据组合分类id查询物料筛选项", notes = "传入组合分类id")
    @RequestMapping(value = "/queryMaterialOptions", method = RequestMethod.POST)
    public HttpBaseResponse<List<QueryMaterialOptionsVO>> queryMaterialOptions(@RequestBody DetailQueryRequest request) {
        return HttpBaseResponse.success(curtainService.queryMaterialOptions(request));
    }


    /**
     * 保存组合
     *
     * @param request 查询参数
     * @return 筛选项集合
     */
    @ApiOperation("保存组合")
    @RequestMapping(value = "/saveGroup", method = RequestMethod.POST)
    public HttpBaseResponse<GroupSaveVO> saveGroup(@RequestBody GroupSaveRequest request) {
        return HttpBaseResponse.success(curtainService.saveGroup(request));
    }

    /**
     * 分页查询物料列表
     *
     * @param request 查询参数
     * @return 筛选项集合
     */
    @ApiOperation("分页查询物料列表")
    @RequestMapping(value = "/querMaterialForPage", method = RequestMethod.POST)
    public HttpBaseResponse<MaterialForPageVO> querMaterialForPage(@RequestBody QuerMaterialForPageRequest request) {
        return HttpBaseResponse.success(curtainService.querMaterialForPage(request));
    }

    /**
     * 查询组合中组件替换详情
     *
     * @param request 查询参数
     * @return 筛选项集合
     */
    @ApiOperation("查询组合中组件替换详情")
    @RequestMapping(value = "/queryGroupReplaceDetail", method = RequestMethod.POST)
    public HttpBaseResponse<GroupReplaceDetailVO> queryGroupReplaceDetail(@RequestBody QueryGroupReplaceDetailRequest request) {
        return HttpBaseResponse.success(curtainService.queryGroupReplaceDetail(request));
    }


    /**
     * 批量查询组合替换简单信息
     *
     * @param request 查询参数
     * @return 筛选项集合
     */
    @ApiOperation("批量查询组合替换简单信息")
    @RequestMapping(value = "/queryGroupReplaceDetailSimple", method = RequestMethod.POST)
    public HttpBaseResponse<List<QueryGroupReplaceDetailSimpleVO>> queryGroupReplaceDetailSimple(@RequestBody QueryGroupReplaceDetailSimpleRequest request) {
        return HttpBaseResponse.success(curtainService.queryGroupReplaceDetailSimple(request));
    }

    /**
     * 根据组合id查询组合详情
     *
     * @param request 查询参数
     * @return 组合详情
     */
    @ApiOperation(value = "根据批量组合id查询组合详情", notes = "传入组合id集合")
    @RequestMapping(value = "/queryGroupDetailList", method = RequestMethod.POST)
    public HttpBaseResponse<List<DetailVO>> queryGroupDetailList(@RequestBody GroupDetailListRequest request) {
        return HttpBaseResponse.success(curtainService.queryGroupDetailList(request));
    }


    @ApiOperation("定制柜详情查询")
    @PostMapping("/queryGroupListDetailByGroupListForCabinet")
    public HttpBaseResponse<List<CabinetBomGroupDetailResponse.GroupDetailByCabinetType>> queryGroupListDetailByGroupListForCabinet(@RequestBody QueryCabinetPropertyListRequest request){
        return HttpBaseResponse.success(curtainService.queryGroupListDetailByGroupListForCabinet(request));
    }

    @ApiOperation("批量保存组合")
    @PostMapping("/batchSaveGroup")
    public HttpBaseResponse<BatchSaveGroupResponse> batchSaveGroup(@RequestBody QueryCabinetPropertyListResponseNew request){
        return HttpBaseResponse.success(curtainService.batchSaveGroup(request));
    }
}
