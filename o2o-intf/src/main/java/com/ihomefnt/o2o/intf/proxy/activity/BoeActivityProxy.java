package com.ihomefnt.o2o.intf.proxy.activity;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.manager.constant.common.CommonResponseVo;

/**
 * @author xiamingyu
 * @date 2018/11/27
 */

public interface BoeActivityProxy {

    /**
     * 领取BOE优惠券
     * @param params
     * @return
     */
    CommonResponseVo getBoeVoucher(JSONObject params);

    /**
     * 领取BOE优惠券-内部使用
     * @param params
     * @return
     */
    CommonResponseVo getBoeVoucherPrivate(JSONObject params);

    /**
     * 获取画屏分享图片
     * @param params
     * @return
     */
    CommonResponseVo getInviteCode(JSONObject params);

    /**
     * 获取商品可用券信息
     * @param params
     * @return
     */
    CommonResponseVo getValidVoucher(JSONObject params);

    /**
     * 校验券是否可用
     * @param params
     * @return
     */
    CommonResponseVo validVoucher(JSONObject params);

    /**
     * 下单成功给券记录订单号
     * @param params
     * @return
     */
    CommonResponseVo updateOrderId(JSONObject params);

    /**
     * 查询订单用券情况
     * @param params
     * @return
     */
    CommonResponseVo queryOrderVoucher(JSONObject params);
}
