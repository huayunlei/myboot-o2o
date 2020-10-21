package com.ihomefnt.o2o.api.controller.visusal;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.visusal.vo.request.QuerySkuVisiblePicListRequest;
import com.ihomefnt.o2o.intf.domain.visusal.vo.request.VisualFurnitureMatchingRequest;
import com.ihomefnt.o2o.intf.domain.visusal.vo.response.IsSupportSkuResponseVo;
import com.ihomefnt.o2o.intf.domain.visusal.vo.response.VisusalSpaceImgsResponseVo;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.service.home.HomeCardService;
import com.ihomefnt.o2o.service.service.visusal.VisualFurnitureMatchingServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 一句话功能简述
 * 功能详细描述
 *
 * @author jiangjun
 * @version 2.0, 2018-04-24 下午6:23
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Api(tags = "【可视化API】")
@RequestMapping("/visualFurnitureMatching")
@RestController
public class VisualFurnitureMatchingController {

    @Autowired
    VisualFurnitureMatchingServiceImpl matchingService;

    @Autowired
    HomeCardService homeCardService;

    @NacosValue(value = "${spacemark.update.version}", autoRefreshed = true)
    private String SPACEMARK_UPDATE_VERSION;

    @ApiOperation(value = "判断是否支持sku可视化选配", notes = "判断是否支持sku可视化选配",
    response = IsSupportSkuResponseVo.class)
    @RequestMapping(value = "/isSupportSKU", method = RequestMethod.POST)
    public HttpBaseResponse<IsSupportSkuResponseVo> isSupportSKU(@RequestBody VisualFurnitureMatchingRequest request) {
        
        return HttpBaseResponse.success(matchingService.supportSKU(request.getProgramId()));
    }

    @ApiOperation("获取空间渲染效果图")
    @PostMapping("/spaceImg")
    public HttpBaseResponse<VisusalSpaceImgsResponseVo> spaceImg(@RequestBody QuerySkuVisiblePicListRequest request) {
        return HttpBaseResponse.success(matchingService.spaceImgs(request));
    }
}
