package com.ihomefnt.o2o.service.service.activity;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.activity.dto.InviteCodeDto;
import com.ihomefnt.o2o.intf.domain.activity.vo.request.BoeShareRequest;
import com.ihomefnt.o2o.intf.domain.activity.vo.request.GetVoucherRequest;
import com.ihomefnt.o2o.intf.domain.activity.vo.request.VoucherUseRequest;
import com.ihomefnt.o2o.intf.domain.activity.vo.response.BoeShareResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.manager.constant.common.CommonResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.util.common.Watermark;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.proxy.activity.BoeActivityProxy;
import com.ihomefnt.o2o.intf.service.activity.BoeActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiamingyu
 * @date 2018/11/27
 */

@Service
public class BoeActivityServiceImpl implements BoeActivityService {

    @Autowired
    private BoeActivityProxy proxy;

    @Override
    public CommonResponseVo getBoeVoucher(GetVoucherRequest request) {
        JSONObject params = new JSONObject();
        params.put("activityId",2);
        params.put("mobile",request.getMobileNum());
        params.put("inviteCode",request.getInviteCode());
        CommonResponseVo commonResponseVo = proxy.getBoeVoucher(params);
        return commonResponseVo;
    }

    @Override
    public CommonResponseVo getBoeVoucherPrivate(GetVoucherRequest request) {
        JSONObject params = new JSONObject();
        params.put("activityId",2);
        params.put("mobile",request.getMobileNum());
        CommonResponseVo commonResponseVo = proxy.getBoeVoucherPrivate(params);
        return commonResponseVo;
    }

    @Override
    public CommonResponseVo getBoeSharePic(BoeShareRequest request) {

        JSONObject params = new JSONObject();
        params.put("mobile",request.getMobileNum());
        CommonResponseVo commonResponseVo = proxy.getInviteCode(params);

        if(commonResponseVo!=null && commonResponseVo.getObj()!=null){
            InviteCodeDto inviteCodeDto = JsonUtils.json2obj(JsonUtils.obj2json(commonResponseVo.getObj()),InviteCodeDto.class);
            BoeShareResponse response = new BoeShareResponse();
            Watermark inviteCodeMark = new Watermark();
            inviteCodeMark.setText(inviteCodeDto.getInviteCode());
            inviteCodeMark.setFont("wqy-microhei");
            inviteCodeMark.setFontsize(65);//
            inviteCodeMark.setFill("FFFFFF");//字体颜色
            inviteCodeMark.setGravity("north");//水印位置
            inviteCodeMark.setDistanceX(0);//横轴边距
            inviteCodeMark.setDistanceY(680);//纵轴边距

            String url = StaticResourceConstants.SHARE_PIC_URL+ AliImageUtil.addTextToImage(inviteCodeMark);;
            response.setSharePicUrl(url);
            commonResponseVo.setObj(response);
        }
        return commonResponseVo;
    }

    @Override
    public CommonResponseVo getInviteCode(BoeShareRequest request) {

        JSONObject params = new JSONObject();
        params.put("mobile",request.getMobileNum());
        CommonResponseVo commonResponseVo = proxy.getInviteCode(params);

        if(commonResponseVo!=null && commonResponseVo.getObj()!=null){
            InviteCodeDto inviteCodeDto = JsonUtils.json2obj(JsonUtils.obj2json(commonResponseVo.getObj()),InviteCodeDto.class);
            commonResponseVo.setObj(inviteCodeDto);
        }
        return commonResponseVo;
    }

    @Override
    public CommonResponseVo getValidVoucher(VoucherUseRequest request) {
        JSONObject params = new JSONObject();
        params.put("activityId",2);
        params.put("mobile",request.getMobileNum());
        params.put("productId",request.getProductId());
        CommonResponseVo commonResponseVo = proxy.getValidVoucher(params);
        if(commonResponseVo!=null && commonResponseVo.getCode()==2011){
            commonResponseVo.setCode(HttpReturnCode.DING_MONITOR_WHITE_START);
        }
        return commonResponseVo;
    }

}
