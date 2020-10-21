package com.ihomefnt.o2o.service.service.home;

import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentsDto;
import com.ihomefnt.o2o.intf.domain.common.http.CopyWriterConstant;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.DeliverInfoVo;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.DmsRequiredVo;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BannerResponseVo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.OrderNode;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.HomeFrameRequest;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.QueryDraftRequest;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.HomeFrameResponse;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.SolutionDraftResponse;
import com.ihomefnt.o2o.intf.domain.maintain.dto.TaskDetailDto;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.AppSolutionDesignResponseVo;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.DesignDnaRoomVo;
import com.ihomefnt.o2o.intf.domain.program.dto.SolutionDetailResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.StyleRemarkResultDto;
import com.ihomefnt.o2o.intf.domain.program.enums.SolutionStatusEnum;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppOrderBaseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.QueryContractListResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.CopyWriterAndValue;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.FamilyOrderPayResponse;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleAnwserSelectedResponse;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleQuestionSelectedNewResponse;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleQuestionSelectedResponse;
import com.ihomefnt.o2o.intf.manager.constant.home.*;
import com.ihomefnt.o2o.intf.manager.constant.personalneed.DesignTaskAppEnum;
import com.ihomefnt.o2o.intf.manager.constant.personalneed.DesignTaskSystemEnum;
import com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise;
import com.ihomefnt.o2o.intf.manager.constant.program.StyleEnum;
import com.ihomefnt.o2o.intf.manager.constant.programorder.RoomUseEnum;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.IntegerUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.proxy.delivery.DeliveryProxy;
import com.ihomefnt.o2o.intf.proxy.designdemand.PersonalNeedProxy;
import com.ihomefnt.o2o.intf.proxy.designdemand.StyleQuestionAnwserProxy;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardWcmProxy;
import com.ihomefnt.o2o.intf.proxy.maintain.MaintainProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.comment.CommentService;
import com.ihomefnt.o2o.intf.service.home.HomeBuildingService;
import com.ihomefnt.o2o.intf.service.home.HomeV510PageService;
import com.ihomefnt.o2o.intf.service.home.HomeV5PageService;
import com.ihomefnt.o2o.intf.service.programorder.ProductProgramOrderService;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * APP5.0新版首页Service层实现
 * Author: ZHAO
 * Date: 2018年7月19日
 */
@Service
public class HomeV510PageServiceImpl implements HomeV510PageService {
    private static final Logger LOG = LoggerFactory.getLogger(HomeV510PageServiceImpl.class);

    @Autowired
    private ProductProgramOrderProxyImpl orderProxy;

    @Autowired
    private HomeCardWcmProxy homeCardWcmProxy;

    @Autowired
    private CommentService commentService;

    @Autowired
    private DeliveryProxy deliveryProxy;

    @Autowired
    private PersonalNeedProxy personalNeedProxy;

    @Autowired
    private ProductProgramProxy productProgramProxy;

    @Autowired
    private DicProxy dicProxy;

    @Autowired
    private HomeBuildingService homeBuildingService;

    @Autowired
    private MaintainProxy maintainProxy;

    @Autowired
    private UserProxy userProxy;

    @Autowired
    private StyleQuestionAnwserProxy styleQuestionAnwserProxy;

    @Autowired
    private ProductProgramOrderService productProgramOrderService;

    @Autowired
    private HomeV5PageService homeV5PageService;

    private static Integer width = 0;

    private static Integer osType = 1;

    private static String mobile = "";

    @Override
    @Deprecated
    public HomeFrameResponse getHomePageData(HomeFrameRequest request) {
        HomeFrameResponse homeFrameResponse = new HomeFrameResponse();
        List<OrderNode> orderNodeList = new ArrayList<OrderNode>();
        Map<Integer, OrderNode> nodeMap = new HashMap<Integer, OrderNode>();

        if (request.getWidth() != null) {
            width = request.getWidth();
        }
        if (request.getOsType() != null && request.getOsType() != 0) {
            osType = request.getOsType();
        }
        if (StringUtils.isNotBlank(request.getMobileNum())) {
            mobile = request.getMobileNum();
        }

        Integer userId = 0;
        // 判断是否登陆
        if (request.getUserInfo() != null && request.getUserInfo().getId() != null) {
            userId = request.getUserInfo().getId();
        }

        if (userId == 0) {
            //未登录用户,焦点：了解我们
            focusAboutUs(nodeMap, homeFrameResponse, null, null, null);
        } else {
            Integer orderId = request.getOrderId();
            if (IntegerUtil.isEmpty(orderId)) {
                homeFrameResponse.setOldFlag(false);
                //登录了但无订单
                if (setStyleInfo(orderId, homeFrameResponse)) {
                    //选过风格的用户，焦点：预选设计
                    focusSelectDesign(nodeMap, homeFrameResponse, null, null, null, null);
                } else {
                    //未选过风格的用户，焦点：了解我们
                    focusAboutUs(nodeMap, homeFrameResponse, null, null, null);
                }
                // 已选风格问题答案列表
                setStyleQuestionSelectedResponse(null, userId, homeFrameResponse);
            } else {
                //有订单的用户
                AppOrderBaseInfoResponseVo orderInfo = orderProxy.queryAppOrderBaseInfo(orderId);
                if (orderInfo != null && orderInfo.getOrderStatus() != null) {
                    Integer orderStatus = orderInfo.getOrderStatus();
                    Integer houseProjectId = orderInfo.getBuildingId();
                    Integer houseTypeId = orderInfo.getLayoutId();
                    Integer orderSaleType = orderInfo.getOrderSaleType();
                    //订单基础信息
                    setBaseOrderInfo(orderInfo, homeFrameResponse);

                    //电子合同信息
                    setContractInfo(homeFrameResponse, orderId);

                    //付款进度
                    BigDecimal payRate = HomeOrderNode.COMPLETE_ZERO;
                    if (orderInfo.getFundProcess() != null) {
                        payRate = orderInfo.getFundProcess();
                    }
                    if (orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_CONTACT_STAGE.getStatus())) {
                        //接触中的用户
                        if (setStyleInfo(orderId, homeFrameResponse)) {
                            //选过风格的用户，焦点：预选设计
                            focusSelectDesign(nodeMap, homeFrameResponse, orderId, orderStatus, houseProjectId, houseTypeId);
                        } else {
                            //未选过风格的用户，焦点：了解我们
                            focusAboutUs(nodeMap, homeFrameResponse, orderId, houseProjectId, houseTypeId);
                        }
                    } else if (orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_INTENTIONAL_PHASE.getStatus()) || orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_DEPOSIT_PHASE.getStatus())) {
                        //意向、定金阶段的用户
                        if (setStyleInfo(orderId, homeFrameResponse)) {
                            //选过风格的用户，焦点：预选设计
                            focusSelectDesign(nodeMap, homeFrameResponse, orderId, orderStatus, houseProjectId, houseTypeId);
                        } else {
                            //未选过风格的用户，焦点：选风格
                            focusSelectStyle(nodeMap, homeFrameResponse, orderId, orderStatus, houseProjectId, houseTypeId);
                        }
                    } else if (orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_SIGNING_STAGE.getStatus())) {
                        //签约阶段的用户,先查询用户是否有已选方案
                        if (orderInfo.getContractAmount() == null || orderInfo.getContractAmount().compareTo(BigDecimal.ZERO) <= 0) {
                            //合同额<=0，则认为用户不存在已选方案（可能是方案被后台取消),则判断用户有没有选过风格
                            if (!setStyleInfo(orderId, homeFrameResponse)) {
                                //用户未选过风格，焦点：选风格
                                focusSelectStyle(nodeMap, homeFrameResponse, orderId, orderStatus, houseProjectId, houseTypeId);
                            } else {
                                //用户已选过风格，焦点：预选设计
                                focusSelectDesign(nodeMap, homeFrameResponse, orderId, orderStatus, houseProjectId, houseTypeId);
                            }
                            homeFrameResponse.setSignSubStatus(SignSubStatusEnum.NO_SOLUTION.getStatus());
                        } else {
                            // 存在已选方案，则焦点：确认方案
                            if (orderInfo.getPreConfirmed().equals(0)) {
                                homeFrameResponse.setSignSubStatus(SignSubStatusEnum.WAIT_CONFIRM.getStatus());
//                          allMoney  ("用户享受权益并缴满全款，0: 不满足 1: 满足") 字段已废弃，使用fundProcess("收款进度")>=1表示收款完成 From 张彬
                            } else if (orderInfo.getFundProcess() != null && orderInfo.getFundProcess().compareTo(BigDecimal.ONE) < 0) {
                                homeFrameResponse.setSignSubStatus(SignSubStatusEnum.WAIT_PAY_ALL.getStatus());
                            } else {
                                DmsRequiredVo dmsRequiredVo = deliveryProxy.queryOrderDeliverInfo(orderId);
                                if (dmsRequiredVo != null && dmsRequiredVo.getCheckResult()) {
                                    homeFrameResponse.setSignSubStatus(SignSubStatusEnum.WAIT_CONSTRUCTION.getStatus());
                                } else {
                                    homeFrameResponse.setSignSubStatus(SignSubStatusEnum.CONFIRM_ADN_PAY_COMPLETED.getStatus());
                                }
                            }
                            focusConfirmSolution(nodeMap, homeFrameResponse, orderId, orderStatus);
                        }
                    } else if (orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_IN_DELIVERY.getStatus())) {
                        focusConstruction(nodeMap, homeFrameResponse, orderId, orderStatus, payRate, orderSaleType);
                    } else if (orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_COMPLETED.getStatus())) {
                        //已完成阶段
                        if (setCommentInfo(orderId, homeFrameResponse)) {
                            //用户已点评,焦点：维保
                            //用户保修记录次数
                            setMaintainCount(userId, orderId, homeFrameResponse);
                            focusMaintenance(nodeMap, homeFrameResponse, orderId, orderStatus, payRate, orderSaleType, request.getAppVersion());
                        } else {
                            //用户尚未点评，焦点：验收
                            focusCheck(nodeMap, homeFrameResponse, orderId, orderStatus, houseProjectId, houseTypeId, payRate, orderSaleType);
                        }
                    }
                    // 已选风格问题答案列表
                    //setStyleQuestionSelectedResponse(null, userId, homeFrameResponse);
                }
            }
        }

        for (OrderNode node : nodeMap.values()) {
            orderNodeList.add(node);
        }
        homeFrameResponse.setOrderNodeList(orderNodeList);

        return homeFrameResponse;
    }

    private void setStyleQuestionSelectedResponse(Integer orderId, Integer userId, HomeFrameResponse homeFrameResponse) {
        StyleQuestionSelectedNewResponse styleQuestionSelectedNewResponse = styleQuestionAnwserProxy.queryQuestionAnwserDetailLatest(orderId, userId);
        if(styleQuestionSelectedNewResponse == null){
            LOG.info("queryQuestionAnwserDetailLatest return empty");
            return;
        }
        List<StyleQuestionSelectedResponse> selectedStyleQuestionList = styleQuestionSelectedNewResponse.getStyleQuestionSelectedResponseList();
        if (CollectionUtils.isNotEmpty(selectedStyleQuestionList)) {
            StyleQuestionSelectedResponse questionTwo = new StyleQuestionSelectedResponse();
            for (StyleQuestionSelectedResponse vo : selectedStyleQuestionList) {
                // DNA样板间问题 需要单独赋值
                if (4 == vo.getId()) {
                    List<StyleAnwserSelectedResponse> anwserList = new ArrayList<>();
                    StyleAnwserSelectedResponse anwser = new StyleAnwserSelectedResponse();

                    if (null != vo.getAnwserList() && null != vo.getAnwserList().get(0)) {
                        StyleAnwserSelectedResponse styleAnwser = vo.getAnwserList().get(0);
                        anwser.setAnwserId(styleAnwser.getAnwserId());
                        anwser.setAnwserContent(styleAnwser.getAnwserContent());
                        anwser.setQuestionId(styleAnwser.getQuestionId());
                        vo.setAnwserList(null);
                    }
                    Map<String, Object> obj = new HashMap<>();
                    obj.put("dnaId", homeFrameResponse.getDnaId());
                    obj.put("dnaName", homeFrameResponse.getDnaName());
                    obj.put("dnaStyleName", homeFrameResponse.getDnaStyleName());
                    obj.put("dnaHeadImgUrl", homeFrameResponse.getDnaHeadImgUrl());

                    AppSolutionDesignResponseVo designResponseVo = null;
                    if (orderId != null && orderId > 0) {
                        Map<String, Object> params = new HashMap<>();
                        params.put("orderNum", orderId);
                        if(styleQuestionSelectedNewResponse.getTaskId() > 0){
                            params.put("taskId", styleQuestionSelectedNewResponse.getTaskId());
                        }
                        designResponseVo = personalNeedProxy.queryDesignDemond(params);
                    } else if (userId != null && userId > 0) {
                        Map<String, Object> params = new HashMap<>();
                        params.put("userId", userId);
                        if(styleQuestionSelectedNewResponse.getTaskId() > 0){
                            params.put("taskId", styleQuestionSelectedNewResponse.getTaskId());
                        }
                        List<AppSolutionDesignResponseVo> list = personalNeedProxy.queryDesignDemondHistory(params);
                        if (CollectionUtils.isNotEmpty(list)) {
                            designResponseVo = list.get(0);
                        }
                    }
                    // 组装dna空间信息
                    if (null != designResponseVo) {
                        this.assembleDnaRoomList(obj, designResponseVo.getTaskDnaRoomList(), questionTwo, osType, width);
                    }

                    anwser.setObj(obj);
                    anwser.setAnwserType(1);
                    anwserList.add(anwser);
                    vo.setAnwserList(anwserList);
                    break;
                }
            }
            if (null != questionTwo && !StringUtil.isNullOrEmpty(questionTwo.getQuestionDetail())) {
                selectedStyleQuestionList.add(1, questionTwo);
            }
            homeFrameResponse.setSelectedStyleQuestionList(selectedStyleQuestionList);
        }
        // remark
        checkAndSetResponseRemark(homeFrameResponse);
    }

    @Override
    public void assembleDnaRoomList(Map<String, Object> obj, List<DesignDnaRoomVo> taskDnaRoomList, StyleQuestionSelectedResponse questionTwo, Integer osType, Integer width) {
        if (CollectionUtils.isEmpty(taskDnaRoomList)) {
            return;
        }
        List<StyleAnwserSelectedResponse> anwserList = new ArrayList<>();

        Iterator<DesignDnaRoomVo> it = taskDnaRoomList.iterator();
        while (it.hasNext()) {
            DesignDnaRoomVo vo = it.next();
            if (null != vo.getDnaRoomUsageId()) {
                String useDesc = CommonRoomUseEnum.getDescriptionByCode(vo.getDnaRoomUsageId());
                if (!StringUtil.isNullOrEmpty(useDesc)) {
                    StyleAnwserSelectedResponse anwser = new StyleAnwserSelectedResponse();
                    anwser.setAnwserContent(useDesc);
                    anwserList.add(anwser);
                }
            }
            // 删除非用户自选的
            if (vo.getUserSelected() == null || 1 != vo.getUserSelected()) {
                it.remove();
                continue;
            }

            // 用户自选的 需要压缩图片
            if (!StringUtil.isNullOrEmpty(vo.getDnaRoomPicUrl())) {
                if (osType == null || osType == 0) {
                    osType = 1;
                }
                if (width == null) {
                    width = 750;
                }
                vo.setDnaRoomPicUrl(AliImageUtil.imageCompress(vo.getDnaRoomPicUrl(), osType, width, ImageConstant.SIZE_MIDDLE));
            }
            if (CollectionUtils.isNotEmpty(vo.getRoomItemBrandList())) {
                StringBuilder sb = new StringBuilder();
                for (String x : vo.getRoomItemBrandList()) {
                    sb.append(x).append(" ");
                }
                vo.setDnaRoomBrandPraise(sb.toString());
            }
            if (null != vo.getDnaRoomUsageId()) {
                vo.setDnaRoomUsageDesc(RoomUseEnum.getDescription(vo.getDnaRoomUsageId()));
            }
        }
        if (CollectionUtils.isNotEmpty(anwserList)) {
            questionTwo.setQuestionDetail("希望拥有怎样的功能房？");
            questionTwo.setAnwserList(anwserList);
        }
        obj.put("designDnaRoomList", taskDnaRoomList);
    }

    private void checkAndSetResponseRemark(HomeFrameResponse homeFrameResponse) {
        if (null != homeFrameResponse.getRemark() && homeFrameResponse.getRemark().startsWith("[{") && homeFrameResponse.getRemark().endsWith("}]")) {
            try {
                List<StyleRemarkResultDto> remarks = JsonUtils.json2list(homeFrameResponse.getRemark(), StyleRemarkResultDto.class);
                if (CollectionUtils.isNotEmpty(remarks)) {
                    for (int i = remarks.size() - 1; i >= 0; i--) {
                        StyleRemarkResultDto rm = remarks.get(i);
                        if ("其他需求".equals(rm.getQuestion()) && CollectionUtils.isNotEmpty(rm.getAnswers())) {
                            homeFrameResponse.setRemark(rm.getAnswers().get(0));
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                LOG.error("getHomePageData 解析remark异常  remark:{}", homeFrameResponse.getRemark(), e);
                homeFrameResponse.setRemark("");
            }

            if (homeFrameResponse.getRemark().startsWith("[{")) {
                homeFrameResponse.setRemark("");
            }
        }
    }

    /**
     * 1、未登录的访客用户 2、名下无全品家订单的注册用户  3、名下有全品家订单，但是接触中阶段的订单
     * 焦点 了解我们
     */
    private void focusAboutUs(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer orderId, Integer houseProjectId, Integer houseTypeId) {
        homeFrameResponse.setFocusNode(NodeEnum.ABOUT_US.getCode());

        nodeAboutUs(nodeMap, HomeOrderNode.STATUS_NOW);
        nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, orderId, null);
        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, orderId, houseProjectId, houseTypeId, HomeOrderNode.COMPLETE_ZERO);
        nodeAdjustDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId);
        nodeConfirmSolution(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, orderId);
        nodeConstruction(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId, null);
        nodeCheck(nodeMap, HomeOrderNode.STATUS_FUTURE);
        nodeMaintenance(nodeMap, HomeOrderNode.STATUS_FUTURE);
    }

    /**
     * 已交款未选方案  1.意向用户 2.定金用户 未选过风格
     * 焦点 选风格
     */
    private void focusSelectStyle(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer orderId, Integer orderStatus, Integer houseProjectId, Integer houseTypeId) {
        homeFrameResponse.setFocusNode(NodeEnum.SELECT_STYLE.getCode());

        nodeAboutUs(nodeMap, HomeOrderNode.STATUS_PAST);
        nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, orderId, orderStatus);
        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, orderId, houseProjectId, houseTypeId, HomeOrderNode.COMPLETE_ZERO);
        nodeAdjustDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId);
        nodeConfirmSolution(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, orderId);
        nodeConstruction(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId, null);
        nodeCheck(nodeMap, HomeOrderNode.STATUS_FUTURE);
        nodeMaintenance(nodeMap, HomeOrderNode.STATUS_FUTURE);
    }

    /**
     * 已选过风格
     * 焦点 预选设计
     */
    private void focusSelectDesign(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer orderId, Integer orderStatus, Integer houseProjectId, Integer houseTypeId) {
        homeFrameResponse.setFocusNode(NodeEnum.SELECT_DESIGN.getCode());

        nodeAboutUs(nodeMap, HomeOrderNode.STATUS_PAST);

        nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, orderStatus);
        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, orderId, houseProjectId, houseTypeId, getSelectDesignRate(orderId, homeFrameResponse));

        nodeAdjustDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId);
        nodeConfirmSolution(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, orderId);
        nodeConstruction(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId, null);
        nodeCheck(nodeMap, HomeOrderNode.STATUS_FUTURE);
        nodeMaintenance(nodeMap, HomeOrderNode.STATUS_FUTURE);
    }

    /**
     * 签约阶段  已选方案
     * 焦点 确认方案
     */
    private void focusConfirmSolution(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer orderId, Integer orderStatus) {
        homeFrameResponse.setFocusNode(NodeEnum.CONFIRM_SOLUTION.getCode());

        BigDecimal selectDesignRate = getSelectDesignRate(orderId, homeFrameResponse);

        //方案确认标志 5.1.0废弃
        //homeFrameResponse.setCheckFlag(true);

        nodeAboutUs(nodeMap, HomeOrderNode.STATUS_PAST);
        if (setStyleInfo(orderId, homeFrameResponse)) {
            nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, orderStatus);
        } else {
            nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, orderId, orderStatus);
        }

        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, null, null, HomeOrderNode.COMPLETE_FINISHED);
        //确认过方案：调整设计过去时时  未确认过方案：调整设计进行时
        if (SignSubStatusEnum.WAIT_CONFIRM.getStatus().equals(homeFrameResponse.getSignSubStatus())) {
            Integer adjustCount = getAjustCount(orderId, 1);
            homeFrameResponse.setAdjustCount(adjustCount);
            nodeAdjustDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, selectDesignRate, orderId);
        } else {
            nodeAdjustDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, selectDesignRate, orderId);
        }
        nodeConfirmSolution(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, orderId);
        nodeConstruction(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId, null);
        nodeCheck(nodeMap, HomeOrderNode.STATUS_FUTURE);
        nodeMaintenance(nodeMap, HomeOrderNode.STATUS_FUTURE);
    }

    /**
     * 交付阶段
     * 焦点 施工
     */
    private void focusConstruction(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer orderId, Integer orderStatus, BigDecimal payRate, Integer orderSaleType) {
        homeFrameResponse.setFocusNode(NodeEnum.CONSTRUCTION.getCode());

        nodeAboutUs(nodeMap, HomeOrderNode.STATUS_PAST);
        nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, orderStatus);
        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, null, null, HomeOrderNode.COMPLETE_FINISHED);
        nodeAdjustDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, HomeOrderNode.COMPLETE_FINISHED, orderId);
        nodeConfirmSolution(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId);
        nodeConstruction(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, HomeOrderNode.COMPLETE_FINISHED, orderId, orderSaleType);
        nodeCheck(nodeMap, HomeOrderNode.STATUS_FUTURE);
        nodeMaintenance(nodeMap, HomeOrderNode.STATUS_FUTURE);
    }

    /**
     * 已完成阶段   用户尚未点评
     * 焦点 验收
     */
    private void focusCheck(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer orderId, Integer orderStatus, Integer houseProjectId, Integer houseTypeId, BigDecimal payRate, Integer orderSaleType) {
        homeFrameResponse.setFocusNode(NodeEnum.CHECK.getCode());

        nodeAboutUs(nodeMap, HomeOrderNode.STATUS_PAST);
        nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, orderStatus);
        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, houseProjectId, houseTypeId, HomeOrderNode.COMPLETE_FINISHED);
        nodeAdjustDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, HomeOrderNode.COMPLETE_FINISHED, orderId);
        nodeConfirmSolution(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId);
        nodeConstruction(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, HomeOrderNode.COMPLETE_FINISHED, orderId, orderSaleType);
        nodeCheck(nodeMap, HomeOrderNode.STATUS_NOW);
        nodeMaintenance(nodeMap, HomeOrderNode.STATUS_FUTURE);
    }

    /**
     * 已完成阶段   用户已点评
     * 焦点 维保
     */
    private void focusMaintenance(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer orderId, Integer orderStatus, BigDecimal payRate, Integer orderSaleType, String appVersion) {
        homeFrameResponse.setFocusNode(NodeEnum.MAINTENANCE.getCode());

        nodeAboutUs(nodeMap, HomeOrderNode.STATUS_PAST);
        nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, orderStatus);
        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, null, null, HomeOrderNode.COMPLETE_FINISHED);
        nodeAdjustDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, HomeOrderNode.COMPLETE_FINISHED, orderId);
        nodeConfirmSolution(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId);
        Integer deliverStatus = nodeConstruction(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, HomeOrderNode.COMPLETE_FINISHED, orderId, orderSaleType);
        nodeCheck(nodeMap, HomeOrderNode.STATUS_PAST);
        //判断维保期是否已过
        if (deliverStatus.equals(8)) {
            nodeMaintenance(nodeMap, HomeOrderNode.STATUS_PAST);
        } else {
            nodeMaintenance(nodeMap, HomeOrderNode.STATUS_NOW);
        }
    }

    /**
     * 节点    了解我们
     */
    private void nodeAboutUs(Map<Integer, OrderNode> nodeMap, Integer status) {
        BigDecimal completeRate = HomeOrderNode.COMPLETE_ZERO;
        if (status.equals(HomeOrderNode.STATUS_PAST)) {
            //过去时
            completeRate = HomeOrderNode.COMPLETE_FINISHED;
        }
        setNodeInfo(nodeMap, NodeEnum.ABOUT_US.getCode(), completeRate, status);
    }

    /**
     * 查询合同信息并赋值
     */
    private void setContractInfo(HomeFrameResponse homeFrameResponse, Integer orderId) {
        //查询合同信息
        List<QueryContractListResponseVo> contractListResponseVos = orderProxy.queryContractList(orderId);
        if (CollectionUtils.isEmpty(contractListResponseVos)) {
            //合同模板URL
            DicDto dicVo = dicProxy.queryDicByKey(ElectronicContractTypeEnum.SUBSCRIBE_AGREEMENT.getKey());
            if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
                homeFrameResponse.setSubscribeAgeementUrl(dicVo.getValueDesc());
            }
        }
    }

    /**
     * 节点    选风格
     */
    private void nodeSelectStyle(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer status, Integer orderId, Integer orderStatus) {
        BigDecimal completeRate = HomeOrderNode.COMPLETE_ZERO;
        if (status.equals(HomeOrderNode.STATUS_PAST)) {
            //过去时
            completeRate = HomeOrderNode.COMPLETE_FINISHED;
            //风格信息
            setStyleInfo(orderId, homeFrameResponse);
        }
        getSelectDesignRate(orderId, homeFrameResponse);
        setNodeInfo(nodeMap, NodeEnum.SELECT_STYLE.getCode(), completeRate, status);
    }

    /**
     * 节点    预选设计
     */
    private void nodeSelectDesign(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer status, Integer orderId, Integer houseProjectId, Integer houseTypeId, BigDecimal selectDesignRate) {
        if (!status.equals(HomeOrderNode.STATUS_PAST)) {
            //进行时  将来时
            if (orderId != null) {
                Integer solutionCount = productProgramProxy.queryAvailableSolutionCount(houseProjectId, houseTypeId, orderId.longValue());
                homeFrameResponse.setSolutionCount(solutionCount);
            }

        }
        setNodeInfo(nodeMap, NodeEnum.SELECT_DESIGN.getCode(), selectDesignRate, status);
    }

    /**
     * 节点    调整设计
     */
    private void nodeAdjustDesign(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer status, BigDecimal completeRate, Integer orderId) {
        if (status.equals(HomeOrderNode.STATUS_NOW)) {
            //进行时  查询订单草稿
            QueryDraftRequest queryDraftParams = new QueryDraftRequest();
            queryDraftParams.setOrderId(orderId);
            queryDraftParams.setDraftProgress(new BigDecimal(1));
            queryDraftParams.setDraftType(1);
            LOG.info("search orderDraft params:{}", JsonUtils.obj2json(queryDraftParams));
            SolutionDraftResponse orderDraft = querySolutionDraft(queryDraftParams);
            if (orderDraft != null && StringUtils.isNotBlank(orderDraft.getDraftJsonStr())) {
                homeFrameResponse.setOrderDraft(orderDraft.getDraftJsonStr());
            }
        }
        setNodeInfo(nodeMap, NodeEnum.ADJUST_DESIGN.getCode(), completeRate, status);
    }

    /**
     * 节点    确认方案
     */
    private void nodeConfirmSolution(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer status, Integer orderId) {
        BigDecimal completeRate = HomeOrderNode.COMPLETE_ZERO;

        if (status.equals(HomeOrderNode.STATUS_PAST)) {
            //过去时
            completeRate = HomeOrderNode.COMPLETE_FINISHED;
        }

        setNodeInfo(nodeMap, NodeEnum.CONFIRM_SOLUTION.getCode(), completeRate, status);
    }

    /**
     * 节点    施工
     */
    private Integer nodeConstruction(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer status, BigDecimal completeRate, Integer orderId, Integer orderSaleType) {
        Integer deliverStatus = -1;
        if (!status.equals(HomeOrderNode.STATUS_FUTURE)) {
            //进行时  过去时
            deliverStatus = getDeliverInfo(orderId, homeFrameResponse, orderSaleType, status);
        }
        setNodeInfo(nodeMap, NodeEnum.CONSTRUCTION.getCode(), completeRate, status);
        return deliverStatus;
    }

    /**
     * 节点    验收
     */
    private void nodeCheck(Map<Integer, OrderNode> nodeMap, Integer status) {
        BigDecimal completeRate = HomeOrderNode.COMPLETE_ZERO;
        if (status.equals(HomeOrderNode.STATUS_PAST)) {
            //过去时
            completeRate = HomeOrderNode.COMPLETE_FINISHED;
        }
        setNodeInfo(nodeMap, NodeEnum.CHECK.getCode(), completeRate, status);
    }

    /**
     * 节点    维保
     */
    private void nodeMaintenance(Map<Integer, OrderNode> nodeMap, Integer status) {
        BigDecimal completeRate = HomeOrderNode.COMPLETE_ZERO;
        if (status.equals(HomeOrderNode.STATUS_PAST)) {
            //过去时
            completeRate = HomeOrderNode.COMPLETE_FINISHED;
        }
        setNodeInfo(nodeMap, NodeEnum.MAINTENANCE.getCode(), completeRate, status);
    }

    /**
     * 设置节点基础信息
     */
    private void setNodeInfo(Map<Integer, OrderNode> nodeMap, Integer nodeId, BigDecimal completeRate, Integer status) {
        OrderNode nodeInfo = new OrderNode();
        nodeInfo.setNodeId(nodeId);
        nodeInfo.setNodeName(NodeEnum.getName(nodeId));
        nodeInfo.setCompleteRate(completeRate);
        nodeInfo.setStatus(status);
        nodeMap.put(nodeId, nodeInfo);
    }

    /**
     * 订单基础信息
     */
    private void setBaseOrderInfo(AppOrderBaseInfoResponseVo orderInfo, HomeFrameResponse homeFrameResponse) {
        //订单交款信息
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal paidAmount = BigDecimal.ZERO;
        BigDecimal upGradeCouponAmount = BigDecimal.ZERO;
        BigDecimal contractAmount = BigDecimal.ZERO;

        if (orderInfo.getUpGradeCouponAmount() != null) {
            upGradeCouponAmount = orderInfo.getUpGradeCouponAmount();
        }
        if (orderInfo.getOriginalOrderAmount() != null) {
            totalAmount = orderInfo.getOriginalOrderAmount();
        }
        if (orderInfo.getContractAmount() != null) {
            contractAmount = orderInfo.getContractAmount();
            totalAmount = orderInfo.getContractAmount();
        } else {
            //订单未记录原始方案价格，则原始方案价格视为实际合同额
            totalAmount = orderInfo.getContractAmount() == null ? BigDecimal.ZERO : orderInfo.getContractAmount();
        }
        if (orderInfo.getFundAmount() != null) {
            paidAmount = orderInfo.getFundAmount();
        }
        if (orderInfo.getGradeId() != null && paidAmount.compareTo(new BigDecimal(0)) > 0) {
            homeFrameResponse.setGradeId(orderInfo.getGradeId());
            homeFrameResponse.setGradeName(orderInfo.getGradeName());
        }
        homeFrameResponse.setTotalPrice(totalAmount);
        homeFrameResponse.setPaidMoney(paidAmount);
        homeFrameResponse.setUnpaidMoney(contractAmount.equals(BigDecimal.ZERO) ? contractAmount : contractAmount.subtract(paidAmount));
        homeFrameResponse.setUpGradeCouponAmount(upGradeCouponAmount);
        homeFrameResponse.setFinalOrderPrice(CopyWriterAndValue.build(CopyWriterConstant.Order.ORDER_TOTAL_PRICE, orderInfo.getOrderTotalAmount() == null ? new BigDecimal(0) : orderInfo.getOrderTotalAmount()));
        //款项类型
        if (orderInfo.getPaymentType() != null) {
            homeFrameResponse.setPaymentType(PaymentTypeEnum.getDescription(orderInfo.getPaymentType()));
        }
        //置家顾问
        if (StringUtils.isNotBlank(orderInfo.getAdviserPhone())) {
            homeFrameResponse.setHomeAdviserMobile(orderInfo.getAdviserPhone());
        } else {
            homeFrameResponse.setHomeAdviserMobile(ProductProgramPraise.ADVISER_MOBILE_DEFAULT);
        }
        //是否是老订单(0. 老订单 1. 新订单)
        if (orderInfo.getOldOrder() != null && orderInfo.getOldOrder().equals(1)) {
            homeFrameResponse.setOldFlag(false);
        }
        //距离交房时间
        if (orderInfo.getDeliverDiff() != null) {
            homeFrameResponse.setLeaveRoomDays(orderInfo.getDeliverDiff());
        }
        //方案首图
        if (StringUtils.isNotBlank(orderInfo.getSolutionUrl())) {
            homeFrameResponse.setSolutionImgUrl(AliImageUtil.imageCompress(orderInfo.getSolutionUrl(), 2, 750, ImageConstant.SIZE_MIDDLE));
        }
        //硬装风格图片
        if (StringUtils.isNotBlank(orderInfo.getSolutionStyleName())) {
            homeFrameResponse.setStyleImgUrl(StyleEnum.getImgUrlByName(orderInfo.getSolutionStyleName()));
        }
        //售卖类型：0：全品家（软+硬） 1：全品家（软）
        if (orderInfo.getOrderSaleType() != null) {
            homeFrameResponse.setOrderSaleType(orderInfo.getOrderSaleType());
        }
        //订单状态
        homeFrameResponse.setOrderStatus(homeBuildingService.getOrderStatus(orderInfo.getOrderStatus()));
        //订单号
        homeFrameResponse.setOrderId(orderInfo.getOrderNum());
        //软装送货情况
        if (orderInfo.getTotalProductCount() != null) {
            homeFrameResponse.setSoftTotal(orderInfo.getTotalProductCount());
        }
        if (orderInfo.getCompleteDelivery() != null) {
            homeFrameResponse.setSoftFinishNum(orderInfo.getCompleteDelivery());
        }
        FamilyOrderPayResponse familyOrderPayResponse = productProgramOrderService.queryPayBaseInfo(orderInfo.getOrderNum());
        //设置方案总价和剩余应付
        if (familyOrderPayResponse != null) {
            homeFrameResponse.setSolutionTotalPrice(familyOrderPayResponse.getSolutionAmount());
            homeFrameResponse.setNewRestAmount(familyOrderPayResponse.getNewRestAmount());
            //由于老订单合同价问题，现在以订单总价作为方案总价
            homeFrameResponse.setTotalPrice(familyOrderPayResponse.getFinalOrderPrice().getValue());
            homeFrameResponse.setUnpaidMoney(familyOrderPayResponse.getNewRestAmount());
        }
    }

    /**
     * 方案草稿  选方案进度
     */
    private BigDecimal getSelectDesignRate(Integer orderId, HomeFrameResponse homeFrameResponse) {
        BigDecimal selectDesignRate = HomeOrderNode.COMPLETE_ZERO;
        //查询是否有选方案草稿  WCM
        QueryDraftRequest queryDraftParams = new QueryDraftRequest();
        queryDraftParams.setOrderId(orderId);
        SolutionDraftResponse queSolutionDraftResponse = querySolutionDraft(queryDraftParams);
        if (queSolutionDraftResponse != null && StringUtils.isNotBlank(queSolutionDraftResponse.getDraftJsonStr())) {
            homeFrameResponse.setSelectDesignDraft(queSolutionDraftResponse.getDraftJsonStr());
            homeFrameResponse.setSelectDesignDraftId(queSolutionDraftResponse.getDraftId());
            if (queSolutionDraftResponse.getDraftProgress() != null) {
                selectDesignRate = queSolutionDraftResponse.getDraftProgress();
            }

            // 方案有效标识
            if (StringUtils.isNotEmpty(homeFrameResponse.getSelectDesignDraft())) {
                // 查询方案是否下架
                Map<String, Object> map = JsonUtils.json2map(homeFrameResponse.getSelectDesignDraft());
                String solutionSelected = map.get("solutionSelected") != null ? JsonUtils.obj2json(map.get("solutionSelected")) : null;
                if (StringUtils.isNotEmpty(solutionSelected)) {
                    Map<String, Object> map2 = JsonUtils.json2map(solutionSelected);
                    Integer solutionId = map2.get("solutionId") != null ? (Integer) map2.get("solutionId") : 0;

                    if (solutionId > 0) {
                        SolutionDetailResponseVo vo = productProgramProxy.getProgramDetailById(solutionId);
                        if (vo !=null && !SolutionStatusEnum.ONLINE.getStatus().equals(vo.getSolutionStatus())){
                            vo = null;
                        }
                        if (null == vo || StringUtils.isEmpty(vo.getSolutionName())) {
                            homeFrameResponse.setSolutionFlag(1);
                        }
                    }
                }
            }
        } else {
            homeFrameResponse.setSolutionFlag(0);
        }
        LOG.info("getSelectDesignRate result selectDesignRate:{}", selectDesignRate);
        return selectDesignRate;
    }

    /**
     * 硬装交付信息
     */
    private Integer getDeliverInfo(Integer orderId, HomeFrameResponse homeFrameResponse, Integer orderSaleType, Integer status) {
        Integer deliverStatus = -1;
        List<Integer> orderIdList = new ArrayList<Integer>();
        orderIdList.add(orderId);
        List<DeliverInfoVo> deliverInfoVos = deliveryProxy.queryByOrderIdList(orderIdList);
        if (CollectionUtils.isNotEmpty(deliverInfoVos)) {
            DeliverInfoVo deliverInfoVo = deliverInfoVos.get(0);
            if (deliverInfoVo.getDeliverDay() != null) {
                homeFrameResponse.setStartedDays(deliverInfoVo.getDeliverDay());
            }
            homeFrameResponse.setConstructionPeriod(90);//默认为90天
            if (StringUtils.isNotBlank(deliverInfoVo.getProjectStatus())) {
                if (status.equals(HomeOrderNode.STATUS_PAST)) {
                    homeFrameResponse.setHardProgress("已竣工");
                } else {
                    homeFrameResponse.setHardProgress(deliverInfoVo.getProjectStatus());
                }
            }
            //从订单系统查软装商品件数
            Integer softTotal = homeFrameResponse.getSoftTotal() == null ? 0 : homeFrameResponse.getSoftTotal();
            Integer softFinishNum = homeFrameResponse.getSoftFinishNum() == null ? 0 : homeFrameResponse.getSoftFinishNum();
            if (orderSaleType != null && orderSaleType.equals(0)) {
                //软装+硬装
                if (softFinishNum > 0) {
                    if (softFinishNum.equals(softTotal)) {
                        homeFrameResponse.setSoftProgress("已全部到货");
                    } else {
                        homeFrameResponse.setSoftProgress("已到货" + softFinishNum + "件");
                    }
                } else if (deliverInfoVo.getOrderStatus() > -1) {
                    homeFrameResponse.setSoftProgress("采购中");
                } else {
                    homeFrameResponse.setSoftProgress("会在硬装结束后进场");
                }
            }
            if (deliverInfoVo.getStatus() != null) {
                deliverStatus = deliverInfoVo.getStatus();
            }
        }
        return deliverStatus;
    }

    /**
     * 查询设计草稿次数
     */
    private Integer getAjustCount(Integer orderId, Integer draftType) {
        //调整设计次数
        Map<String, Object> queryDraftCountParams = new HashMap<String, Object>();
        queryDraftCountParams.put("orderNum", orderId);
        queryDraftCountParams.put("draftType", draftType);
        queryDraftCountParams.put("draftProgress", new BigDecimal(1));
        Integer adjustCount = homeCardWcmProxy.queryOrderDraftCountByCondition(queryDraftCountParams);
        if (adjustCount != null && adjustCount > 0) {
            adjustCount = adjustCount - 1;
        }
        return adjustCount;
    }

    /**
     * 已选风格信息：  false 无风格  true 有风格
     */
    private boolean setStyleInfo(Integer orderId, HomeFrameResponse homeFrameResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderNum", orderId);
        params.put("mobile", mobile);
        AppSolutionDesignResponseVo designResponseVo = personalNeedProxy.queryDesignDemond(params);
        if (designResponseVo == null || designResponseVo.getUserId() == null || designResponseVo.getDnaId() == null) {
            LOG.info("setStyleInfo result false");
            return false;
        }
        //风格信息
        if (StringUtils.isNotBlank(designResponseVo.getBudget())) {
            homeFrameResponse.setBudget(designResponseVo.getBudget());
        }
        if (StringUtils.isNotBlank(designResponseVo.getRemark())) {
            homeFrameResponse.setRemark(designResponseVo.getRemark());
        }
        if (designResponseVo.getDnaId() != null) {
            homeFrameResponse.setDnaId(designResponseVo.getDnaId());
        }
        if (StringUtils.isNotBlank(designResponseVo.getDnaName())) {
            homeFrameResponse.setDnaName(designResponseVo.getDnaName());
        }
        if (StringUtils.isNotBlank(designResponseVo.getDnaHeadImg())) {
            homeFrameResponse.setDnaHeadImgUrl(AliImageUtil.imageCompress(designResponseVo.getDnaHeadImg(), osType, width, ImageConstant.SIZE_MIDDLE));
        }
        if (StringUtils.isNotBlank(designResponseVo.getDnaStyle())) {
            homeFrameResponse.setDnaStyleName(designResponseVo.getDnaStyle());
        }
        DesignTaskAppEnum designTaskAppEnumByTaskStatus = getDesignTaskAppEnumByTaskStatus(designResponseVo.getTaskStatus());
        if (designTaskAppEnumByTaskStatus != null) {
            homeFrameResponse.setTaskStatus(designTaskAppEnumByTaskStatus.getTaskStatus());
            homeFrameResponse.setTaskStatusStr(designTaskAppEnumByTaskStatus.getTaskStatusStr());
        }
        return true;
    }

    /**
     * 对用户选风格数据状态的转换处理
     * <p>
     * o2o对bata状态的转换处理逻辑
     * -1,0  -> 1
     * 3,1,5,6 -> 2
     * 2  -> 3
     * 4,7,-3  ->  -1
     *
     * @param bataTaskStatus
     * @return
     */
    public static DesignTaskAppEnum getDesignTaskAppEnumByTaskStatus(Integer bataTaskStatus) {
        if (bataTaskStatus == null) {
            return null;
        }
        if (bataTaskStatus.equals(DesignTaskSystemEnum.UNNEED_DESIGN.getTaskStatus()) || bataTaskStatus.equals(DesignTaskSystemEnum.WAIT_DESIGN.getTaskStatus())) {
            return DesignTaskAppEnum.COMMITTED;
        } else if (bataTaskStatus.equals(DesignTaskSystemEnum.WAIT_ACCEPT.getTaskStatus())
                || bataTaskStatus.equals(DesignTaskSystemEnum.DESIGNING.getTaskStatus())
                || bataTaskStatus.equals(DesignTaskSystemEnum.AUDITING.getTaskStatus())
                || bataTaskStatus.equals(DesignTaskSystemEnum.AUDIT_NOTPASS.getTaskStatus())) {
            return DesignTaskAppEnum.IN_DESIGN;
        } else if (bataTaskStatus.equals(DesignTaskSystemEnum.DESIGNED.getTaskStatus())) {
            return DesignTaskAppEnum.DESIGN_FINISH;
        } else if (bataTaskStatus.equals(DesignTaskSystemEnum.INVALID2.getTaskStatus()) ||bataTaskStatus.equals(DesignTaskSystemEnum.INVALID.getTaskStatus()) || bataTaskStatus.equals(DesignTaskSystemEnum.CANCELLATION.getTaskStatus())) {
            return DesignTaskAppEnum.INVALID;
        } else {
            return null;
        }
    }

    /**
     * 点评信息:  false 未点评  true 已点评
     */
    private boolean setCommentInfo(Integer orderId, HomeFrameResponse homeFrameResponse) {
        //查询施工点评
        CommentsDto comments = commentService.getComment(orderId);
        if (comments != null && comments.getOrderId() != null) {
            //用户已点评
            homeFrameResponse.setEvaluationContent(comments.getComment());
            homeFrameResponse.setEvaluationStars(comments.getScore());
            LOG.info("setCommentInfo result true");
            return true;
        }
        LOG.info("setCommentInfo result false");
        return false;
    }

    /**
     * 查询用户保修记录次数
     */
    private void setMaintainCount(Integer userId, Integer orderId, HomeFrameResponse homeFrameResponse) {
        //查询报修记录
        List<TaskDetailDto> detailResponseVos = maintainProxy.queryList(userId, null, orderId);
        Integer maintainCount = detailResponseVos == null ? 0 : detailResponseVos.size();
        homeFrameResponse.setMaintainCount(maintainCount);
    }

    /**
     * 根据版本号过滤banner数据
     */
    @Override
    public void reduceBannerByVersion(List<BannerResponseVo> bannerResponseVos,String appVersion){
        if(appVersion==null){
            appVersion="5.5.0";
        }
        if(CollectionUtils.isNotEmpty(bannerResponseVos)){
            String finalAppVersion = appVersion;
            bannerResponseVos.removeIf(item->{
                String version = item.getBigVersion()+"."+item.getMidVersion()+"."+item.getSmallVersion();
                return VersionUtil.mustUpdate(finalAppVersion,version);
            });
        }
    }

    @Override
    public SolutionDraftResponse querySolutionDraft(QueryDraftRequest request) {
        if (request.getOrderId() == null) {
            return null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        if (request.getDraftId() != null) {
            params.put("draftId", request.getDraftId());
        }
        if (request.getOrderId() != null) {
            params.put("orderNum", request.getOrderId());
        }
        if (request.getDraftProgress() != null) {
            params.put("draftProgress", request.getDraftProgress());
        }
        if (request.getDraftType() != null) {
            params.put("draftType", request.getDraftType());
        }
        return homeCardWcmProxy.queryOrderDraftByCondition(params);
    }
}
