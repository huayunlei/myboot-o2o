package com.ihomefnt.o2o.service.proxy.right;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.right.dto.*;
import com.ihomefnt.o2o.intf.domain.right.vo.request.ClassificationReq;
import com.ihomefnt.o2o.intf.domain.right.vo.request.ConfirmRightRequest;
import com.ihomefnt.o2o.intf.domain.right.vo.request.QueryMyOrderRightItemListRequest;
import com.ihomefnt.o2o.intf.domain.right.vo.response.OrderRightsResponse;
import com.ihomefnt.o2o.intf.domain.right.vo.response.OrderRightsVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinOrderServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinPromotionServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.util.common.response.ResponseVo;
import com.ihomefnt.o2o.intf.proxy.right.RightProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author jerfan cang
 *   2018/9/6 19:50
 */
@Service
public class RightProxyImpl implements RightProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(RightProxyImpl.class);

    @Resource
    private StrongSercviceCaller strongSercviceCaller;

    /**
     * 图片100 尺寸枚举值
     */
    private static final Integer IMAGE_SIZE_ICON = 100;

    /**
     * 10000 枚举值
     */
    private static final Integer NUMBER_TEN_THOUSAND = 10000;

    /**
     * 查询订单权益(活动)详情
     * @return obj
     */
    @Override
    public CheckOrderRightsResultDto queryOrderRightsLicense(Long orderNum) {
        if( null == orderNum ){
            return null;
        }

        JSONObject param = new JSONObject();
        param.put("orderNum", orderNum);
        ResponseVo<CheckOrderRightsResultDto> response;
        try{
            response = strongSercviceCaller.post(AladdinOrderServiceNameConstants.SERVER_NAME_QUERY_ORDER_RIGHT_LICENSE,param,
                    new TypeReference<ResponseVo<CheckOrderRightsResultDto>>() {
                    });
        }catch (Exception e){
            return null;
        }

        if (response == null || response.getData() == null) {
            return null;
        }
        return response.getData();
    }


    /**
     * 查询订单权益(活动)详情
     * @return obj
     */
    @Override
    public List<GradeClassifyDto> queryOrderRightsDetail(Integer rightVersion) {
        JSONObject param = new JSONObject();
        param.put("version", rightVersion);
        ResponseVo<List<GradeClassifyDto>> response;
        try{
            response = strongSercviceCaller.post(AladdinOrderServiceNameConstants.SERVER_NAME_QUERY_ORDER_RIGHT_DETAIL, param,
                    new TypeReference<ResponseVo<List<GradeClassifyDto>>>() {
                    });
        }catch (Exception e){
            return null;
        }

        if (response == null || response.getData() == null) {
            return null;
        }
        return response.getData();
    }

    /**
     * 查询当前用户权益版本
     * @return obj
     */
    @Override
    public GradeVersionDto queryCurrentVersion(Integer orderNum) {
        ResponseVo<GradeVersionDto> response;
        try{
            response = strongSercviceCaller.post(AladdinOrderServiceNameConstants.ALADDIN_ORDER_MASTERORDER_APP_QUERYORDERVERSION, orderNum,
                    new TypeReference<ResponseVo<GradeVersionDto>>() {
                    });
        }catch (Exception e){
            return null;
        }

        if (response == null || response.getData() == null) {
            return null;
        }
        return response.getData();
    }

    /**
     * 查询订单权益分类详情
     * @param param 分类id(classifyId) 分类编号(classifyNo) 版本号(version) 等级id(gradeId)
     * @return obj
     */
    @Override
    public ClassifyDetailInfo queryOrderRightsClassification(ClassificationReq param) {
        if( null == param ){
            return null;
        }
        ResponseVo<ClassifyDetailInfo> response;
        try{
            response = strongSercviceCaller.post(AladdinOrderServiceNameConstants.SERVER_NAME_QUERY_ORDER_RIGHT_CLASSIFY,param,
                    new TypeReference<ResponseVo<List<ClassifyDetailInfo>>>() {
                    });
        }catch (Exception e){
            return null;
        }

        if (response == null || response.getData() == null) {
            return null;
        }

        return response.getData();
    }

    /**
     * 查询特定等级权益详情
     * @param gradeId
     * @return
     */
    @Override
    public List<GradeRightsResultDto> queryGradeClassifyInfo(Integer gradeId,Integer rightVersion) {
        HashMap<String,Object> map= new HashMap<String,Object>();
        map.put("gradeId",gradeId);
        map.put("version",rightVersion);
        ResponseVo<List<GradeRightsResultDto>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.SERVER_URL_QUERY_GRADE_RIGHTS_LIST, map,
                            new TypeReference<ResponseVo<List<GradeRightsResultDto>>>() {
                            });
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        return responseVo.getData();
    }

    @Override
    public OrderRightsResultDto queryMyOrderRightItemList(QueryMyOrderRightItemListRequest param) {
        if( null == param ){
            return null;
        }
        ResponseVo<OrderRightsResultDto> response = null;
        try{
            response = strongSercviceCaller.post(AladdinOrderServiceNameConstants.SERVER_NAME_QUERY_ORDER_RIGHT_MIME,param,
                    new TypeReference<ResponseVo<OrderRightsResultDto>>() {
                    });
        }catch (Exception e){
            return null;
        }

        if (response == null || response.getData() == null) {
            return null;
        }

        return response.getData();
    }

    @Override
    public OrderSingleClassifyDto queryOrderRightSingleClassify(Integer orderNum, Integer classifyNo,Integer version) {
        JSONObject params = new JSONObject();
        params.put("orderNum",orderNum);
        params.put("classifyNo",classifyNo);
        params.put("version",version);

        ResponseVo<OrderSingleClassifyDto> response = null;
        try{
            response = strongSercviceCaller.post(AladdinOrderServiceNameConstants.SERVER_URL_QUERY_ORDER_RIGHT_SINGLE_CLASSIFY,params,
                    new TypeReference<ResponseVo<OrderSingleClassifyDto>>() {
                    });
        }catch (Exception e){
            return null;
        }

        if (response == null || response.getData() == null) {
            return null;
        }

        OrderSingleClassifyDto data =  response.getData();
        return markSubtitle(data);
    }

    @Override
    public Boolean classifyRightConfirm(ConfirmRightRequest req) {
        ResponseVo<Boolean> response = null;
        try{
            response = strongSercviceCaller.post(AladdinOrderServiceNameConstants.SERVER_URL_CONFIRM_ORDER_CLASSIFY_RIGHT,req,
                    new TypeReference<ResponseVo<Boolean>>() {
                    });
        }catch (Exception e){
            return false;
        }

        if (response == null || response.getData() == null) {
            return false;
        }

        return response.getData();
    }

    /**
     * 装修补贴详细信息查询
     * @param orderNum
     * @return
     */
    @Override
    public OrderRightsVo queryDecorationSubsidyInfo(Integer orderNum) {
        ResponseVo<OrderRightsVo> response = null;
        try{
            response = strongSercviceCaller.post(AladdinPromotionServiceNameConstants.QUERY_ORDER_DECORANTION_DETAIL,orderNum,
                    new TypeReference<ResponseVo<OrderRightsVo>>() {
                    });
        }catch (Exception e){
            return null;
        }

        if (response == null || response.getData() == null) {
            return null;
        }

        return response.getData();
    }

    /**
     * 副标题赋值
     * @param dto
     * @return
     */
    private OrderSingleClassifyDto markSubtitle(OrderSingleClassifyDto dto){
        List<RightItemDto>  RightItemDtoList = dto.getItemDetailList();
        for( RightItemDto rightItemDto :RightItemDtoList){
            rightItemDto.setSubtitle(subtitle(rightItemDto));
        }
        return  dto;

    }
    /**
     * 副标题 枚举匹配
     * @param item RightItemDto
     * @return subtitle
     */
    private String subtitle(RightItemDto item){
        String itemName = item.getItemName();
        String subtitle=null;
        switch (itemName){
            case "艾升级" :
                subtitle=item.getItemRewardQuota()+item.getItemRewardUnitStr();
                break;
            case "艾久久" :
                subtitle=item.getItemRewardQuota()+item.getItemRewardUnitStr();
                break;
            case "艾艺术" :
                subtitle="价值"+item.getItemRewardQuota()+item.getItemRewardUnitStr();
                break;
            case "艾无忧" :

                subtitle=(Integer.parseInt(item.getItemRewardQuota())/NUMBER_TEN_THOUSAND)+"万"+item.getItemRewardUnitStr();
                break;
            case "艾呼吸" :
                subtitle=item.getItemRewardQuota()+item.getItemRewardUnitStr()+"免租金";
                break;
            case "艾洁士" :
                subtitle="共"+item.getItemRewardQuota()+item.getItemRewardUnitStr();
                break;
            case "艾先住" :
                subtitle=item.getItemRewardQuota();
                break;
            case "艾前程" :
                subtitle="可推荐"+item.getItemRewardQuota()+"人";
                break;
            case "艾就业" :
                subtitle="小区大众监理"+item.getItemRewardQuota()+"人";
                break;
            case "艾聚会" :
                subtitle="年度生态大会";
                break;
            case "艾祝福" :
                subtitle="价值"+item.getItemRewardQuota()+item.getItemRewardUnitStr();
                break;
            case "艾乔迁" :
                subtitle=item.getItemRewardQuota()+item.getItemRewardUnitStr();
                break;
            case "艾长者" :
                subtitle=item.getItemRewardQuota()+item.getItemRewardUnitStr();
                break;
            case "艾小宝" :
                subtitle=item.getItemRewardQuota()+item.getItemRewardUnitStr();
                break;
            case "艾焕新" :
                subtitle=item.getItemRewardQuota()+item.getItemRewardUnitStr();
                break;
            case "艾维权" :
                subtitle=item.getItemRewardQuota();
                break;
            case "艾佳游" :
                subtitle=item.getItemRewardQuota()+item.getItemRewardUnitStr();
                break;
            case "艾艺星" :
                subtitle="小星星艺术节";
                item.getItemRewardUnitStr();
                break;
            case "艾扮家" :
                subtitle="参加创意大赛";
                break;
            case "艾订制" :
                subtitle="个性软饰设计";
                break;
            case "艾学习" :
                subtitle=item.getItemRewardQuota()+item.getItemRewardUnitStr()+"无敌券";
                break;
            case "艾援助" :
                subtitle=item.getItemRewardQuota();
                break;
            case "艾留念" :
                subtitle="全家福留念";
                break;
            default:
                subtitle="";
        }
        return subtitle;
    }
}
