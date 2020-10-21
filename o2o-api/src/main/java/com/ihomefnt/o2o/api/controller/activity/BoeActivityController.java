package com.ihomefnt.o2o.api.controller.activity;

import com.ihomefnt.o2o.intf.domain.activity.dto.InviteCodeDto;
import com.ihomefnt.o2o.intf.domain.activity.dto.VoucherDto;
import com.ihomefnt.o2o.intf.domain.activity.vo.request.BoeShareRequest;
import com.ihomefnt.o2o.intf.domain.activity.vo.request.GetVoucherRequest;
import com.ihomefnt.o2o.intf.domain.activity.vo.request.VoucherUseRequest;
import com.ihomefnt.o2o.intf.domain.activity.vo.response.BoeShareResponse;
import com.ihomefnt.o2o.intf.service.activity.BoeActivityService;
import com.ihomefnt.o2o.intf.manager.constant.common.CommonResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiamingyu
 * @date 2018/11/24
 */

@RestController
@Api(tags = "【画屏促销API】")
@RequestMapping("/boe")
public class BoeActivityController {

    private static final Logger LOG = LoggerFactory.getLogger(BoeActivityController.class);

    @Autowired
    private BoeActivityService boeActivityService;


    @ApiOperation(value = "领取画屏优惠券",notes = "领取画屏优惠券接口")
    @RequestMapping(value = "/getBoeVoucher",method = RequestMethod.POST)
    public CommonResponseVo<VoucherDto> getBoeVoucher(@RequestBody GetVoucherRequest request){
        CommonResponseVo commonResponseVo = boeActivityService.getBoeVoucher(request);
        return CommonResponseVo.buildResponse(commonResponseVo);
    }

    @ApiOperation(value = "领取画屏优惠券-内部使用",notes = "领取画屏优惠券接口-内部使用")
    @RequestMapping(value = "/getBoeVoucherPrivate",method = RequestMethod.POST)
    public CommonResponseVo<VoucherDto> getBoeVoucherPrivate(@RequestBody GetVoucherRequest request){
        CommonResponseVo commonResponseVo = boeActivityService.getBoeVoucherPrivate(request);
        return CommonResponseVo.buildResponse(commonResponseVo);
    }

    @ApiOperation(value = "获取画屏专属分享图片",notes = "获取画屏专属分享图片接口")
    @RequestMapping(value = "/getBoeSharePic",method = RequestMethod.POST)
    public CommonResponseVo<BoeShareResponse> getBoeSharePic(@RequestBody BoeShareRequest request){
        CommonResponseVo commonResponseVo = boeActivityService.getBoeSharePic(request);
        return CommonResponseVo.buildResponse(commonResponseVo);

    }

    @ApiOperation(value = "获取画屏购买邀请码",notes = "获取画屏购买邀请码")
    @RequestMapping(value = "/getInviteCode",method = RequestMethod.POST)
    public CommonResponseVo<InviteCodeDto> getInviteCode(@RequestBody BoeShareRequest request){
        CommonResponseVo commonResponseVo = boeActivityService.getInviteCode(request);
        return CommonResponseVo.buildResponse(commonResponseVo);

    }

    @ApiOperation(value = "查询商品可领和可用券信息",notes = "查询商品可领和可用券信息接口")
    @RequestMapping(value = "/getValidVoucher",method = RequestMethod.POST)
    public CommonResponseVo<VoucherDto> getValidVoucher(@RequestBody VoucherUseRequest request){
        CommonResponseVo commonResponseVo = boeActivityService.getValidVoucher(request);
        return CommonResponseVo.buildResponse(commonResponseVo);
    }

}
