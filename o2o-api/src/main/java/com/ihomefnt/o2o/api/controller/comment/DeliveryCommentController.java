package com.ihomefnt.o2o.api.controller.comment;

import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentLabelsDto;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentsDto;
import com.ihomefnt.o2o.intf.domain.comment.vo.request.CommentsAddRequestVo;
import com.ihomefnt.o2o.intf.domain.comment.vo.response.CommentLabelsListResponseVo;
import com.ihomefnt.o2o.intf.domain.comment.vo.response.CommentLabelsResponseVo;
import com.ihomefnt.o2o.intf.domain.comment.vo.response.DeliveryCommentsResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppOrderBaseInfoResponseVo;
import com.ihomefnt.o2o.intf.service.comment.CommentService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.domain.comment.vo.request.DelivertCommentRequest;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 一句话功能简述
 * 功能详细描述
 *
 * @author jiangjun
 * @version 2.0, 2018-04-13 上午11:36
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Api(tags = "【交付相关】")
@RequestMapping("/deliveryComment")
@RestController
public class DeliveryCommentController {
    private static final int TYPE_SOFT = 2;//订单类型全软
    private static final int TYPE_ALL = 1;//订单类型全品家 硬+软

    @Autowired
    CommentService commentService;

    @Autowired
    private ProductProgramOrderProxyImpl productProgramOrderProxy;

    @ApiOperation(value = "获取点评标签", notes = "获取点评标签")
    @RequestMapping(value = "/labels", method = RequestMethod.POST)
    public HttpBaseResponse<CommentLabelsListResponseVo> labels(@RequestBody DelivertCommentRequest request) {
        boolean isSoft = request.isSoft();
        // 6.0之前接口传入isSoft字段，6.0之后接口传入orderId不穿isSoft
        if(request.getOrderId() != null){
            AppOrderBaseInfoResponseVo orderInfo =
                    productProgramOrderProxy.queryAppOrderBaseInfo(Integer.parseInt(request.getOrderId()));
            if(orderInfo.getOrderSaleType() == 1){
                isSoft = true;
            }else{
                isSoft = false;
            }
        }

        Integer type = isSoft?TYPE_SOFT:TYPE_ALL;

        List<CommentLabelsDto> labelses = commentService.getLabels(type);
        if (CollectionUtils.isEmpty(labelses)) {
        	return HttpBaseResponse.success(null);
        }
        List<CommentLabelsResponseVo> list = ModelMapperUtil.strictMapList(labelses, CommentLabelsResponseVo.class);
        return HttpBaseResponse.success(new CommentLabelsListResponseVo(list));
    }

    @ApiOperation(value = "获取点评信息", notes = "获取点评信息")
    @RequestMapping(value = "/getComment", method = RequestMethod.POST)
    public HttpBaseResponse<DeliveryCommentsResponseVo> getComment(@RequestBody DelivertCommentRequest request) {
        String orderId = request.getOrderId();
        if(StringUtils.isBlank(orderId)){
            throw new BusinessException("订单编号不能为空");
        }

        CommentsDto comments = commentService.getComment(Integer.parseInt(orderId));
        if (null == comments) {
        	return HttpBaseResponse.success(null);
        }
        DeliveryCommentsResponseVo obj = ModelMapperUtil.strictMap(comments, DeliveryCommentsResponseVo.class);
		return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "提交点评", notes = "提交点评")
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public HttpBaseResponse<Void> comment(@RequestBody CommentsAddRequestVo request) {
        String errMsg = commentService.addComment(request);
        if(StringUtils.isNotBlank(errMsg)){
        	return HttpBaseResponse.fail(errMsg);
        }
        return HttpBaseResponse.success(null);
    }

}
