package com.ihomefnt.o2o.intf.proxy.right;

import com.ihomefnt.o2o.intf.domain.right.dto.*;
import com.ihomefnt.o2o.intf.domain.right.vo.request.ClassificationReq;
import com.ihomefnt.o2o.intf.domain.right.vo.request.ConfirmRightRequest;
import com.ihomefnt.o2o.intf.domain.right.vo.request.QueryMyOrderRightItemListRequest;
import com.ihomefnt.o2o.intf.domain.right.vo.response.OrderRightsResponse;
import com.ihomefnt.o2o.intf.domain.right.vo.response.OrderRightsVo;

import java.util.List;

/**
 * @author jerfan cang
 * @date 2018/9/6 19:36
 */
public interface RightProxy {


    /**
     * 根据订单id查询订单权益资质
     */
    CheckOrderRightsResultDto queryOrderRightsLicense(Long orderNum);


    /**
     * 查询订单权益(活动)详情
     * @return obj
     */
    List<GradeClassifyDto> queryOrderRightsDetail(Integer rightVersion);

    /**
     * 查询当前用户权益版本
     * @return obj
     */
    GradeVersionDto queryCurrentVersion(Integer orderNum);

    /**
     * 查询单个模块(订单权益分类)详情
     * @param param 分类id(classifyId) 分类编号(classifyNo) 版本号(version) 等级id(gradeId)
     * @return
     */
    ClassifyDetailInfo queryOrderRightsClassification(ClassificationReq param);

    /**
     * 查询特定等级权益详情
     * @param gradeId
     * @return
     */
    List<GradeRightsResultDto> queryGradeClassifyInfo(Integer gradeId,Integer rightVersion);

    /**
     * 查询我的订单权益
     * 按照权益分类区分
     * 带上等级信息
     * 时间节点往前推三天
     * 等级降级后 已经消费的权益要展示
     * @param param 请求参数
     * @return OrderRightsResultDto
     * @throws RuntimeException 当发生异常
     */
    OrderRightsResultDto queryMyOrderRightItemList(QueryMyOrderRightItemListRequest param);

    OrderSingleClassifyDto queryOrderRightSingleClassify(Integer orderNum, Integer classifyNo,Integer version);

    Boolean classifyRightConfirm(ConfirmRightRequest req);

    /**
     * 装修补贴内容查询
     * @param orderNum
     * @return
     */
    OrderRightsVo queryDecorationSubsidyInfo(Integer orderNum);
}
