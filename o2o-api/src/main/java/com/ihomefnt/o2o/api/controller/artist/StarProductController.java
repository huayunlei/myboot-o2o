/*
 * Author: ZHAO
 * Date: 2018/10/11
 * Description:StarProductController.java
 */
package com.ihomefnt.o2o.api.controller.artist;

import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarArtRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarUserLoginRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtCustomRecordResponse;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtStarGood;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtStarWork;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtStarWorkList;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.service.artist.StarProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 小星星作品商品
 *
 * @author ZHAO
 */
@ApiIgnore
@RestController
@Api(tags = "【微信小程序】小星星作品商品API",hidden = true)
@RequestMapping("/starProduct")
public class StarProductController {
    @Autowired
    private StarProductService starProductService;

    @ApiOperation(value = "查询小星星作品列表", notes = "查询小星星作品列表")
    @RequestMapping(value = "/getArtStarWorksList", method = RequestMethod.POST)
    public HttpBaseResponse<ArtStarWorkList> getArtStarWorksList(@RequestBody StarUserLoginRequest request) {
        return HttpBaseResponse.success(starProductService.getArtStarWorksList(request));
    }

    @ApiOperation(value = "查询作品详情", notes = "查询作品详情")
    @RequestMapping(value = "/getArtStarWorkDetail", method = RequestMethod.POST)
    public HttpBaseResponse<ArtStarWork> getArtStarWorkDetail(@RequestBody StarArtRequest request) {
        return HttpBaseResponse.success(starProductService.getArtStarWorkDetail(request));
    }

    @ApiOperation(value = "查询小星星商品列表", notes = "查询小星星商品列表")
    @RequestMapping(value = "/getArtStarGoodList", method = RequestMethod.POST)
    public HttpBaseResponse<List<ArtStarGood>> getArtStarGoodList(@RequestBody StarArtRequest request) {
        return HttpBaseResponse.success(starProductService.getArtStarGoodList(request));
    }

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情")
    @RequestMapping(value = "/getArtStarGoodDetail", method = RequestMethod.POST)
    public HttpBaseResponse<ArtStarGood> getArtStarGoodDetail(@RequestBody StarArtRequest request) {
        return HttpBaseResponse.success(starProductService.getArtStarGoodDetail(request));
    }

    @ApiOperation(value = "查询定制记录列表", notes = "查询定制记录列表")
    @RequestMapping(value = "/getCustomRecordList", method = RequestMethod.POST)
    public HttpBaseResponse<List<ArtCustomRecordResponse>> getCustomRecordList(@RequestBody StarArtRequest request) {
        return HttpBaseResponse.success(starProductService.getCustomRecordList(request));
    }

}
