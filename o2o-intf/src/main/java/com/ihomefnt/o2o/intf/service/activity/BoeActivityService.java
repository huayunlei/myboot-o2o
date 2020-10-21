package com.ihomefnt.o2o.intf.service.activity;

import com.ihomefnt.o2o.intf.manager.constant.common.CommonResponseVo;
import com.ihomefnt.o2o.intf.domain.activity.vo.request.BoeShareRequest;
import com.ihomefnt.o2o.intf.domain.activity.vo.request.GetVoucherRequest;
import com.ihomefnt.o2o.intf.domain.activity.vo.request.VoucherUseRequest;

/**
 * @author xiamingyu
 * @date 2018/11/27
 */

public interface BoeActivityService {

    /**
     * 领取BOE优惠券
     * @param request
     * @return
     */
    CommonResponseVo getBoeVoucher(GetVoucherRequest request);

    /**
     * 领取BOE优惠券-内部使用
     * @param request
     * @return
     */
    CommonResponseVo getBoeVoucherPrivate(GetVoucherRequest request);

    /**
     * 获取画屏分享图片
     * @param request
     * @return
     */
    CommonResponseVo getBoeSharePic(BoeShareRequest request);

    /**
     * 获取邀请码
     * @param request
     * @return
     */
    CommonResponseVo getInviteCode(BoeShareRequest request);

    /**
     * 获取商品可用券信息
     * @param request
     * @return
     */
    CommonResponseVo getValidVoucher(VoucherUseRequest request);

}
