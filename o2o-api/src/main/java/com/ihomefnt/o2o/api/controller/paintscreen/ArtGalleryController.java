package com.ihomefnt.o2o.api.controller.paintscreen;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.ArtOrderRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.ArtPayRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.CommonPageRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.ScreenQueryRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.*;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.unionpay.IpUtils;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.paintscreen.ArtGalleryService;
import com.ihomefnt.o2o.intf.service.pay.PayforService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.ihomefnt.o2o.intf.manager.constant.pay.PayConstants.MODULE_CODE_FGW;

/**
 * 艺术画管API
 *
 * @author liyonggang
 * @create 2018-12-04 14:04
 */
@Api(tags = "【画屏API】")
@RestController
@RequestMapping("/artGallery")
public class ArtGalleryController {

    private static final Logger LOG = LoggerFactory.getLogger(ArtGalleryController.class);

    @Autowired
    private ArtGalleryService artGalleryService;

    @Autowired
    UserProxy userProxy;

    @Resource
    PayforService payService;

    @Autowired
    OrderProxy orderProxy;


    @ApiOperation(value = "首页内容信息", notes = "艺术画廊首页")
    @RequestMapping(value = "/queryArtHomePage", method = RequestMethod.POST)
    public HttpBaseResponse<ArtHomePageResponse> queryArtHomePage(@RequestBody HttpBaseRequest request) {
        return HttpBaseResponse.success(artGalleryService.queryArtHomePage(request.getWidth()));
    }

    @ApiOperation(value = "热门作品列表", notes = "热门作品列表查询")
    @RequestMapping(value = "/queryHostScreenSimple", method = RequestMethod.POST)
    public HttpBaseResponse<PageResponse<ScreenSimpleDetailResponse>> queryHostScreenSimple(@RequestBody CommonPageRequest request) {
        return HttpBaseResponse.success(artGalleryService.queryHostScreenSimple(request));
    }

    @ApiOperation(value = "精选画集列表", notes = "精选画集列表查询")
    @RequestMapping(value = "/querySelectedScreenSimple", method = RequestMethod.POST)
    public HttpBaseResponse<PageResponse<SelectedScreenResponse>> querySelectedScreenSimple(@RequestBody ScreenQueryRequest request) {
        return HttpBaseResponse.success(artGalleryService.querySelectedScreenSimple(request));
    }

    @ApiOperation(value = "画集详情", notes = "画集详情查询")
    @RequestMapping(value = "/querySelectedScreenSimpleDetail", method = RequestMethod.POST)
    public HttpBaseResponse<PageResponse<ScreenSimpleResponse>> querySelectedScreenSimpleDetail(@RequestBody CommonPageRequest request) {
        if(request.getId()==null){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED,MessageConstant.DATA_TRANSFER_EMPTY);
        }
        return HttpBaseResponse.success(artGalleryService.querySelectedScreenSimpleDetail(request));
    }

    @ApiOperation(value = "画作详情", notes = "画集详情查询")
    @RequestMapping(value = "/queryScreenSimple", method = RequestMethod.POST)
    public HttpBaseResponse<ScreenSimpleDetailResponse> queryScreenSimple(@RequestBody CommonPageRequest request) {
        if(request.getId()==null){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED,MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpUserInfoRequest user = request.getUserInfo();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("resourceId",request.getId());
        map.put("resourceType",0);
        map.put("width",request.getWidth());
        if (user != null) {
            map.put("userId",user.getId());
        }
        return HttpBaseResponse.success(artGalleryService.queryScreenSimple(map));
    }

    @ApiOperation(value = "创建订单", notes = "创建画作订单")
    @RequestMapping(value = "/createArtOrder", method = RequestMethod.POST)
    public HttpBaseResponse<ArtOrderVo> createArtOrder(@RequestBody ArtOrderRequest request) {
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        if(CollectionUtils.isEmpty(request.getOrderItemDtoList())){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED,MessageConstant.DATA_TRANSFER_EMPTY);
        }
        request.setUserId(user.getId());
        request.setMobile(request.getMobileNum());
        ArtOrderVo artOrderVo=artGalleryService.createArtOrder(request);
        if(artOrderVo==null){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED,MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(artOrderVo);
    }

    @ApiOperation(value = "查询画集浏览次数", notes = "查询画集浏览次数")
    @RequestMapping(value = "/queryBrowseCount", method = RequestMethod.POST)
    public HttpBaseResponse<BrowseCountResponse> queryBrowseCount(@RequestBody CommonPageRequest request) {
        return HttpBaseResponse.success(artGalleryService.queryBrowseCount(request));
    }

    @SuppressWarnings("rawtypes")
    @ApiOperation(value = "支付接口", notes = "支付接口")
    @RequestMapping(value = "/artPay", method = RequestMethod.POST)
    public HttpBaseResponse artPay(@RequestBody ArtPayRequest request, HttpServletRequest httpServletRequest) {

        String msg=validRequestParam(request);
        if(null != msg){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED,msg);
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }

        //查询订单详情
        ArtOrderVo order = null;
        try{
            order = artGalleryService.queryOrderDetail(Integer.parseInt(request.getOrderId()));
        }catch (Exception e){
            LOG.error("ArtGalleryController.artPay o2o-exception , more info : {}",e.getMessage());
            return HttpBaseResponse.fail(HttpResponseCode.FAILED,MessageConstant.FAILED);
        }
        if(order==null){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED,"订单不存在");
        }

        //  其他参数赋值
        JSONObject artPayDto = configRequestParam(request,order,user,httpServletRequest);

        try{
            Object obj =payService.pay(artPayDto);
            return HttpBaseResponse.success(obj);
        }catch (RuntimeException e){
            LOG.error("ArtGalleryController.artPay o2o-exception , more info :{} ", e.getMessage());
            return HttpBaseResponse.fail(HttpResponseCode.FAILED,MessageConstant.FAILED);
        }

    }

    @ApiOperation(value = "查询订单详情", notes = "查询订单详情")
    @RequestMapping(value = "/queryOrderDetail", method = RequestMethod.POST)
    public HttpBaseResponse<ArtOrderVo> queryOrderDetail(@RequestBody ArtPayRequest request, HttpServletRequest httpServletRequest) {
        //查询订单详情
        ArtOrderVo order = null;
        try{
            order = artGalleryService.queryOrderDetail(Integer.parseInt(request.getOrderId()));
        }catch (Exception e){
            LOG.error("ArtGalleryController.queryOrderDetail o2o-exception , more info : {}",e.getMessage());
            return HttpBaseResponse.fail(HttpResponseCode.FAILED,MessageConstant.FAILED);
        }
        if(order==null){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED,"订单不存在");
        }
        return HttpBaseResponse.success(order);
    }

    @SuppressWarnings("rawtypes")
    @ApiOperation(value = "画屏入口控制", notes = "true：显示，false：隐藏")
    @RequestMapping(value = "/queryScreenShow", method = RequestMethod.POST)
    public HttpBaseResponse queryScreenShow(@RequestBody HttpBaseRequest request) {
        Map<String,Object> map = new HashMap <String,Object>();
        if (VersionUtil.mustUpdate(request.getAppVersion(),"5.2.3")){
            map.put("screenShow",0);//1显示 0 隐藏
        }else {
            map.put("screenShow",1);//1显示 0 隐藏
        }
        return HttpBaseResponse.success(map);
    }
    
    /**
     * 验证请求参数
     * @param req 请求参数
     */
    private String validRequestParam(ArtPayRequest req) {
        if (null == req) {
            return "参数错误";
        }
        if (StringUtils.isBlank(req.getOrderId())) {
            return "订单号不能为空";
        }
        if (req.getChannelSource()==null) {
            return "支付渠道不能为空";
        }
        return null;
    }


    /**
     * 支付请求参数组装
     * @param req req
     * @param order 订单
     * @param user 用户
     * @param httpServletRequest  httpServletRequest
     * @return param
     */
    private JSONObject configRequestParam(ArtPayRequest req, ArtOrderVo order, HttpUserInfoRequest user, HttpServletRequest httpServletRequest){
        JSONObject json  = new JSONObject();
        json.put("orderNum",order.getOrderNo());
        json.put("orderType",req.getOrderType());
        json.put("userId",user.getId());
        json.put("actualPayMent",order.getOrderAmount());
        json.put("totalPayMent",order.getOrderAmount());
        json.put("notifyUrl","arts-centre.art-order.pay");//支付类型18，通知接口
        json.put("source",req.getSource());
        json.put("channelSource",req.getChannelSource());
        json.put("ip",IpUtils.getIpAddr(httpServletRequest));
        json.put("orderInfo",null+":"+order.getOrderNo());
        json.put("moduleCode",MODULE_CODE_FGW);
        json.put("platform",req.getOsType());
        return json;
    }

}
