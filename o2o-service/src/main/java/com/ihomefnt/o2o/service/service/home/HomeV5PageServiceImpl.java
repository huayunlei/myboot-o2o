package com.ihomefnt.o2o.service.service.home;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.common.concurrent.TaskAction;
import com.ihomefnt.common.concurrent.TaskProcessManager;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.common.util.RedisUtil;
import com.ihomefnt.o2o.intf.domain.art.dto.KeywordVo;
import com.ihomefnt.o2o.intf.domain.bundle.vo.request.VersionBundleRequestVo;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentsDto;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.DeliverInfoVo;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.DmsRequiredRecordVo;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.DmsRequiredVo;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.flowmsg.dto.DeleteOfflineDrawMessageDto;
import com.ihomefnt.o2o.intf.domain.homecard.dto.QueryOrderCommentRequestVo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.*;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.*;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.AboutUsResponse;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.HomeFrameResponse;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.SolutionDraftResponse;
import com.ihomefnt.o2o.intf.domain.main.vo.SolutionInfo;
import com.ihomefnt.o2o.intf.domain.maintain.dto.TaskDetailDto;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.AppSolutionDesignResponseVo;
import com.ihomefnt.o2o.intf.domain.product.dto.BomCategoryDTO;
import com.ihomefnt.o2o.intf.domain.product.dto.BomCategoryListDto;
import com.ihomefnt.o2o.intf.domain.product.dto.SkuBaseInfoDto;
import com.ihomefnt.o2o.intf.domain.program.customgoods.request.QueryGroupReplaceDetailSimpleRequest;
import com.ihomefnt.o2o.intf.domain.program.customgoods.response.QueryGroupReplaceDetailSimpleVO;
import com.ihomefnt.o2o.intf.domain.program.dto.*;
import com.ihomefnt.o2o.intf.domain.program.vo.request.AddBagCreateRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.request.SolutionListRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.DraftInfoResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.HardBomGroup;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ServiceItemResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.SolutionEffectResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.*;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.*;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.CopyWriterAndValue;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.CreateOrderResponse;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.FamilyOrderPayResponse;
import com.ihomefnt.o2o.intf.domain.visusal.vo.request.QuerySkuVisiblePicListRequest;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.RedisKey;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import com.ihomefnt.o2o.intf.manager.constant.home.*;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderErrorCodeEnum;
import com.ihomefnt.o2o.intf.manager.constant.product.ProductCategoryConstant;
import com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise;
import com.ihomefnt.o2o.intf.manager.constant.program.StyleEnum;
import com.ihomefnt.o2o.intf.manager.constant.programorder.RoomUseEnum;
import com.ihomefnt.o2o.intf.manager.constant.user.UserConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.IntegerUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.order.FamilyOrderStatus;
import com.ihomefnt.o2o.intf.proxy.delivery.DeliveryProxy;
import com.ihomefnt.o2o.intf.proxy.designdemand.PersonalNeedProxy;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.flowmsg.MessageFlowProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardWcmProxy;
import com.ihomefnt.o2o.intf.proxy.maintain.MaintainProxy;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.product.ProductProxy;
import com.ihomefnt.o2o.intf.proxy.program.KeywordWcmProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.proxy.program.customgoods.CurtainProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.proxy.visusal.VisualFurnitureMatchingProxy;
import com.ihomefnt.o2o.intf.service.comment.CommentService;
import com.ihomefnt.o2o.intf.service.common.AiDingTalk;
import com.ihomefnt.o2o.intf.service.home.HomeBuildingService;
import com.ihomefnt.o2o.intf.service.home.HomeV510PageService;
import com.ihomefnt.o2o.intf.service.home.HomeV5PageService;
import com.ihomefnt.o2o.intf.service.program.ProductProgramService;
import com.ihomefnt.o2o.intf.service.programorder.ProductProgramOrderService;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import com.ihomefnt.o2o.service.service.program.ProductProgramServiceImpl;
import com.ihomefnt.oms.trade.util.PageModel;
import lombok.extern.java.Log;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.collections.Lists;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * APP5.0新版首页Service层实现
 * Author: ZHAO
 * Date: 2018年7月19日
 */
@Service
@SuppressWarnings("all")
@Log
public class HomeV5PageServiceImpl implements HomeV5PageService {
    private static final Logger LOG = LoggerFactory.getLogger(HomeV5PageServiceImpl.class);

    @Autowired
    private MessageFlowProxy messageFlowProxy;

    @Autowired
    private ProductProgramOrderProxyImpl productProgramOrderProxy;

    @Autowired
    OrderProxy orderProxy;

    @Autowired
    private CurtainProxy curtainProxy;

    @Autowired
    private ProductProgramOrderService orderService;

    @Autowired
    private HomeCardWcmProxy homeCardWcmProxy;

    @Autowired
    private KeywordWcmProxy keywordWcmProxy;

    @Autowired
    private CommentService commentService;

    @Autowired
    private DeliveryProxy deliveryProxy;

    @Autowired
    private PersonalNeedProxy personalNeedProxy;

    @Autowired
    private ProductProgramProxy productProgramProxy;

    @Autowired
    private ProductProxy productProxy;

    @Autowired
    private DicProxy dicProxy;

    @Autowired
    private HomeBuildingService homeBuildingService;

    @Autowired
    private MaintainProxy maintainProxy;

    @Autowired
    private UserProxy userProxy;

    @Autowired
    private AiDingTalk aiDingTalk;

    private static Integer width = 0;

    private static String mobile = "";

    @Autowired
    private ProductProgramService productProgramService;

    @Autowired
    HomeV510PageService homeV510PageService;

    @Autowired
    private ProductProgramOrderService productProgramOrderService;

    @NacosValue(value = "${APP_DRAFT_DRAW_TASK_SUPPORT_DRAW_HARDCATEGORY}", autoRefreshed = true)
    private String supportDrawHardCategory;

    @Autowired
    VisualFurnitureMatchingProxy visualFurnitureMatchingProxy;

    @Override
    public HomeFrameResponse getHomePageData(HomeFrameRequest request) {
        HomeFrameResponse homeFrameResponse = new HomeFrameResponse();
        List<OrderNode> orderNodeList = new ArrayList<OrderNode>();
        Map<Integer, OrderNode> nodeMap = new HashMap<Integer, OrderNode>();

        if (request.getWidth() != null) {
            width = request.getWidth();
        }
        if (StringUtils.isNotBlank(request.getMobileNum())) {
            mobile = request.getMobileNum();
        }

        Integer userId = 0;
        // 判断是否登陆
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto != null && userDto.getId() != null) {
            userId = userDto.getId();
        }

        //非野生用户：有订单用户
        Integer orderId = request.getOrderId();
        if (orderId != null && orderId > 0) {
            AppOrderBaseInfoResponseVo orderInfo = productProgramOrderProxy.queryAppOrderBaseInfo(orderId);
            if (orderInfo != null && orderInfo.getOrderStatus() != null) {
                Integer orderStatus = orderInfo.getOrderStatus();
                Integer houseProjectId = orderInfo.getBuildingId();
                Integer houseTypeId = orderInfo.getLayoutId();
                Integer orderSaleType = orderInfo.getOrderSaleType();
                LOG.info("orderInfo orderStatus:{}, orderStatusStr:{}", JsonUtils.obj2json(orderStatus), MasterOrderStatusEnum.getDescription(orderStatus));
                //订单基础信息
                setBaseOrderInfo(orderInfo, homeFrameResponse);

                //付款进度
                BigDecimal payRate = HomeOrderNode.COMPLETE_ZERO;
                if (orderInfo.getFundProcess() != null) {
                    payRate = orderInfo.getFundProcess();
                }

                //根据订单状态判断当前焦点、节点
                if (orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_CONTACT_STAGE.getStatus())) {
                    //接触阶段  归为 野生用户
                    //焦点  了解我们
                    focusAboutUs(nodeMap, homeFrameResponse, orderId, houseProjectId, houseTypeId);
                } else if (orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_INTENTIONAL_PHASE.getStatus()) ||
                        orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_DEPOSIT_PHASE.getStatus())) {
                    //意向阶段、定金阶段
                    //判断是否选过风格
                    if (setStyleInfo(orderId, homeFrameResponse)) {
                        //焦点  预选设计
                        focusSelectDesign(nodeMap, homeFrameResponse, orderId, orderStatus, houseProjectId, houseTypeId);
                    } else {
                        //焦点  选风格
                        focusSelectStyle(nodeMap, homeFrameResponse, orderId, orderStatus, houseProjectId, houseTypeId);
                    }
                } else if (orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_SIGNING_STAGE.getStatus())) {
                    //签约阶段
                    //需求确认信息
                    DmsRequiredVo dmsRequiredVo = deliveryProxy.queryOrderDeliverInfo(orderId);

                    if (dmsRequiredVo != null && dmsRequiredVo.getCheckResult()) {
                        //焦点 确认方案
                        focusConfirmSolution(nodeMap, homeFrameResponse, orderId, orderStatus, payRate);
                    } else {
                        if (dmsRequiredVo != null && CollectionUtils.isNotEmpty(dmsRequiredVo.getRecordDtos()) && StringUtils.isNotBlank(dmsRequiredVo.getRecordDtos().get(0).getDateStr())) {
                            List<DmsRequiredRecordVo> recordDtos = dmsRequiredVo.getRecordDtos();
                            //需求确认时间
                            try {
                                homeFrameResponse.setConfirmationTime(DateUtil.formatDate(
                                        DateUtil.parseDate(recordDtos.get(0).getDateStr(), Arrays.asList("yyyy-MM-dd HH:mm:ss")), "yyyy年MM月dd日"));
                            } catch (DateParseException e) {
                                LOG.error("setConfirmationTime  error,dateStr:{}", JsonUtils.obj2json(recordDtos.get(0).getDateStr()));
                            }
                        }

                        //调整设计次数
                        Integer adjustCount = getAjustCount(orderId, 1);
                        homeFrameResponse.setAdjustCount(adjustCount);
                        LOG.info("adjustCount:{}", adjustCount);

                        if (adjustCount > 0) {
                            //方案正在调整中（有调整草稿）
                            //焦点 调整设计
                            focusAdjustDesign(nodeMap, homeFrameResponse, orderId, orderStatus, payRate);
                        } else {
                            //方案不在调整中
                            //焦点 交装修款
                            focusPayAll(nodeMap, homeFrameResponse, orderId, orderStatus, payRate);
                        }
                    }
                } else if (orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_IN_DELIVERY.getStatus())) {
                    //交付阶段
                    //焦点  施工
                    focusConstruction(nodeMap, homeFrameResponse, orderId, orderStatus, payRate, orderSaleType);

                    //判断用户是否是特殊用户   如果订单在交付中，但用户未付清全款
                    if (payRate.compareTo(HomeOrderNode.COMPLETE_FINISHED) < 0) {
                        homeFrameResponse.setUserFlag(true);
                    }
                } else if (orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_COMPLETED.getStatus())) {
                    //已完成阶段
                    //查询施工点评
                    if (setCommentInfo(orderId, homeFrameResponse)) {
                        //用户已点评
                        //用户保修记录次数
                        setMaintainCount(userId, orderId, homeFrameResponse);
                        //焦点  维保
                        focusMaintenance(nodeMap, homeFrameResponse, orderId, orderStatus, payRate, orderSaleType);
                    } else {
                        //用户尚未点评
                        //焦点  验收
                        focusCheck(nodeMap, homeFrameResponse, orderId, orderStatus, houseProjectId, houseTypeId, payRate, orderSaleType);
                    }

                    /*//判断用户是否是特殊用户   如果订单已完成，但用户未付清全款
                    if (payRate.compareTo(HomeOrderNode.COMPLETE_FINISHED) < 0) {
                        homeFrameResponse.setUserFlag(true);
                    }*/
                }
            }
        } else {
            //野生用户:未登录的访客用户、名下无全品家订单的注册用户
            //焦点  了解我们
            focusAboutUs(nodeMap, homeFrameResponse, orderId, null, null);
        }

        for (OrderNode node : nodeMap.values()) {
            orderNodeList.add(node);
        }
        homeFrameResponse.setOrderNodeList(orderNodeList);

        return homeFrameResponse;
    }

    /**
     * 1、未登录的访客用户 2、名下无全品家订单的注册用户  3、名下有全品家订单，但是接触中阶段的订单
     * 焦点 了解我们
     */
    private void focusAboutUs(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer orderId, Integer houseProjectId, Integer houseTypeId) {
        homeFrameResponse.setFocusNode(NodeEnum.ABOUT_US.getCode());

        nodeAboutUs(nodeMap, HomeOrderNode.STATUS_NOW);
        nodePayDeposit(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, HomeOrderNode.COMPLETE_ZERO, orderId);
        nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, orderId, null);
        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, orderId, houseProjectId, houseTypeId, HomeOrderNode.COMPLETE_ZERO);
        nodePayAll(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId);
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
        nodePayDeposit(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, HomeOrderNode.COMPLETE_FINISHED, orderId);
        nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, orderId, orderStatus);
        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, orderId, houseProjectId, houseTypeId, HomeOrderNode.COMPLETE_ZERO);
        nodePayAll(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId);
        nodeAdjustDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId);
        nodeConfirmSolution(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, orderId);
        nodeConstruction(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId, null);
        nodeCheck(nodeMap, HomeOrderNode.STATUS_FUTURE);
        nodeMaintenance(nodeMap, HomeOrderNode.STATUS_FUTURE);
    }

    /**
     * 已选过风格  未选过方案
     * 焦点 预选设计
     */
    private void focusSelectDesign(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer orderId, Integer orderStatus, Integer houseProjectId, Integer houseTypeId) {
        homeFrameResponse.setFocusNode(NodeEnum.SELECT_DESIGN.getCode());

        nodeAboutUs(nodeMap, HomeOrderNode.STATUS_PAST);
        nodePayDeposit(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, HomeOrderNode.COMPLETE_FINISHED, orderId);
        nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, orderStatus);
        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, orderId, houseProjectId, houseTypeId, getSelectDesignRate(orderId, homeFrameResponse));
        nodePayAll(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId);
        nodeAdjustDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId);
        nodeConfirmSolution(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, orderId);
        nodeConstruction(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId, null);
        nodeCheck(nodeMap, HomeOrderNode.STATUS_FUTURE);
        nodeMaintenance(nodeMap, HomeOrderNode.STATUS_FUTURE);
    }

    /**
     * 签约阶段  已选过方案   方案不在调整中   未达到确认清单条件
     * 焦点 交装修款
     */
    private void focusPayAll(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer orderId, Integer orderStatus, BigDecimal payRate) {
        homeFrameResponse.setFocusNode(NodeEnum.PAY_ALL.getCode());

        getSelectDesignRate(orderId, homeFrameResponse);

        nodeAboutUs(nodeMap, HomeOrderNode.STATUS_PAST);
        nodePayDeposit(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, HomeOrderNode.COMPLETE_FINISHED, orderId);
        nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, orderStatus);
        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, null, null, HomeOrderNode.COMPLETE_FINISHED);
        nodePayAll(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, payRate, orderId);
        nodeAdjustDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, HomeOrderNode.COMPLETE_ZERO, orderId);
        nodeConfirmSolution(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, orderId);
        nodeConstruction(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId, null);
        nodeCheck(nodeMap, HomeOrderNode.STATUS_FUTURE);
        nodeMaintenance(nodeMap, HomeOrderNode.STATUS_FUTURE);
    }

    /**
     * 签约阶段  已选过方案   方案正在调整中（有调整草稿）   未达到确认清单条件
     * 焦点 调整设计
     */
    private void focusAdjustDesign(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer orderId, Integer orderStatus, BigDecimal payRate) {
        homeFrameResponse.setFocusNode(NodeEnum.ADJUST_DESIGN.getCode());

        BigDecimal selectDesignRate = getSelectDesignRate(orderId, homeFrameResponse);

        nodeAboutUs(nodeMap, HomeOrderNode.STATUS_PAST);
        nodePayDeposit(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, HomeOrderNode.COMPLETE_FINISHED, orderId);
        nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, orderStatus);
        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, null, null, HomeOrderNode.COMPLETE_FINISHED);
        nodePayAll(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, payRate, orderId);
        nodeAdjustDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, selectDesignRate, orderId);
        nodeConfirmSolution(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, orderId);
        nodeConstruction(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_FUTURE, HomeOrderNode.COMPLETE_ZERO, orderId, null);
        nodeCheck(nodeMap, HomeOrderNode.STATUS_FUTURE);
        nodeMaintenance(nodeMap, HomeOrderNode.STATUS_FUTURE);
    }

    /**
     * 签约阶段  已到达确认清单条件，但尚未确认清单
     * 焦点 确认方案
     */
    private void focusConfirmSolution(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer orderId, Integer orderStatus, BigDecimal payRate) {
        homeFrameResponse.setFocusNode(NodeEnum.CONFIRM_SOLUTION.getCode());

        BigDecimal selectDesignRate = getSelectDesignRate(orderId, homeFrameResponse);

        //方案确认标志
        homeFrameResponse.setCheckFlag(true);

        nodeAboutUs(nodeMap, HomeOrderNode.STATUS_PAST);
        nodePayDeposit(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, HomeOrderNode.COMPLETE_FINISHED, orderId);
        nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, orderStatus);
        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, null, null, HomeOrderNode.COMPLETE_FINISHED);
        nodePayAll(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, payRate, orderId);
        nodeAdjustDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, selectDesignRate, orderId);
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
        nodePayDeposit(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, HomeOrderNode.COMPLETE_FINISHED, orderId);
        nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, orderStatus);
        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, null, null, HomeOrderNode.COMPLETE_FINISHED);
        nodePayAll(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, payRate, orderId);
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
        nodePayDeposit(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, HomeOrderNode.COMPLETE_FINISHED, orderId);
        nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, orderStatus);
        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, houseProjectId, houseTypeId, HomeOrderNode.COMPLETE_FINISHED);
        nodePayAll(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, payRate, orderId);
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
    private void focusMaintenance(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer orderId, Integer orderStatus, BigDecimal payRate, Integer orderSaleType) {
        homeFrameResponse.setFocusNode(NodeEnum.MAINTENANCE.getCode());

        nodeAboutUs(nodeMap, HomeOrderNode.STATUS_PAST);
        nodePayDeposit(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, HomeOrderNode.COMPLETE_FINISHED, orderId);
        nodeSelectStyle(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, orderStatus);
        nodeSelectDesign(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_PAST, orderId, null, null, HomeOrderNode.COMPLETE_FINISHED);
        nodePayAll(nodeMap, homeFrameResponse, HomeOrderNode.STATUS_NOW, payRate, orderId);
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
     * 节点    交定金
     */
    private void nodePayDeposit(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer status, BigDecimal completeRate, Integer orderId) {
        if (!status.equals(HomeOrderNode.STATUS_PAST)) {
            //非过去时
            //查询合同信息
            List<QueryContractListResponseVo> contractListResponseVos = productProgramOrderProxy.queryContractList(orderId);
            if (CollectionUtils.isEmpty(contractListResponseVos)) {
                //合同模板URL
                DicDto dicVo = dicProxy.queryDicByKey(ElectronicContractTypeEnum.SUBSCRIBE_AGREEMENT.getKey());
                if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
                    homeFrameResponse.setSubscribeAgeementUrl(dicVo.getValueDesc());
                }
            }
        }
        //默认定金
        String depositMoneyDefalut = "5000";
        DicDto dicVo = dicProxy.queryDicByKey(ProductProgramPraise.GLOBAL_DEPOSIT_MONEY_DEFAULT);
        if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
            depositMoneyDefalut = dicVo.getValueDesc();
        }
        homeFrameResponse.setDepositMoneyDefalut(new BigDecimal(depositMoneyDefalut));
        setNodeInfo(nodeMap, NodeEnum.PAY_DEPOSIT.getCode(), completeRate, status);
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
        setNodeInfo(nodeMap, NodeEnum.SELECT_STYLE.getCode(), completeRate, status);
    }

    /**
     * 节点    预选设计
     */
    private void nodeSelectDesign(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer status, Integer orderId, Integer houseProjectId, Integer houseTypeId, BigDecimal selectDesignRate) {
        if (!status.equals(HomeOrderNode.STATUS_PAST)) {
            //进行时  将来时
            Integer solutionCount = 0;
            if (orderId != null) {
                solutionCount = productProgramProxy.queryAvailableSolutionCount(houseProjectId, houseTypeId, orderId.longValue());
            }
            homeFrameResponse.setSolutionCount(solutionCount);
        }
        setNodeInfo(nodeMap, NodeEnum.SELECT_DESIGN.getCode(), selectDesignRate, status);
    }

    /**
     * 节点    交装修款
     */
    private void nodePayAll(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer
            status, BigDecimal payRate, Integer orderId) {
        if (status.equals(HomeOrderNode.STATUS_FUTURE)) {
            //将来时
            //合同模板URL
            DicDto dicVo = dicProxy.queryDicByKey(ElectronicContractTypeEnum.SERVICE_CONTRACT.getKey());
            if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
                homeFrameResponse.setServiceContractUrl(dicVo.getValueDesc());
            }
        } else {
            //若已全部付款，则节点状态更改为过去时
            if (payRate.compareTo(HomeOrderNode.COMPLETE_FINISHED) >= 0) {
                status = HomeOrderNode.STATUS_PAST;
            }
        }
        setNodeInfo(nodeMap, NodeEnum.PAY_ALL.getCode(), payRate, status);
    }

    /**
     * 节点    调整设计
     */
    private void nodeAdjustDesign(Map<Integer, OrderNode> nodeMap, HomeFrameResponse homeFrameResponse, Integer
            status, BigDecimal completeRate, Integer orderId) {
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
    private void nodeConfirmSolution(Map<Integer, OrderNode> nodeMap, HomeFrameResponse
            homeFrameResponse, Integer status, Integer orderId) {
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
    private Integer nodeConstruction(Map<Integer, OrderNode> nodeMap, HomeFrameResponse
            homeFrameResponse, Integer status, BigDecimal completeRate, Integer orderId, Integer orderSaleType) {
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
    private void setNodeInfo(Map<Integer, OrderNode> nodeMap, Integer nodeId, BigDecimal completeRate, Integer
            status) {
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
        if (orderInfo.getContractAmount() != null) {
            totalAmount = orderInfo.getContractAmount();
        }
        if (orderInfo.getFundAmount() != null) {
            paidAmount = orderInfo.getFundAmount();
        }
        homeFrameResponse.setTotalPrice(totalAmount);
        homeFrameResponse.setPaidMoney(paidAmount);
        homeFrameResponse.setUnpaidMoney(totalAmount.subtract(paidAmount));
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
            homeFrameResponse.setSolutionImgUrl(orderInfo.getSolutionUrl());
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
    }

    /**
     * 方案草稿  选方案进度
     */
    private BigDecimal getSelectDesignRate(Integer orderId, HomeFrameResponse homeFrameResponse) {
        BigDecimal selectDesignRate = HomeOrderNode.COMPLETE_ZERO;
        //查询是否有选方案草稿  WCM
        QueryDraftRequest queryDraftParams = new QueryDraftRequest();
        queryDraftParams.setOrderId(orderId);
        LOG.info("search selectDesignDraft params:{}", JsonUtils.obj2json(queryDraftParams));
        SolutionDraftResponse queSolutionDraftResponse = querySolutionDraft(queryDraftParams);
        if (queSolutionDraftResponse != null && StringUtils.isNotBlank(queSolutionDraftResponse.getDraftJsonStr())) {
            homeFrameResponse.setSelectDesignDraft(queSolutionDraftResponse.getDraftJsonStr());
            homeFrameResponse.setSelectDesignDraftId(queSolutionDraftResponse.getDraftId());
            if (queSolutionDraftResponse.getDraftProgress() != null) {
                selectDesignRate = queSolutionDraftResponse.getDraftProgress();
            }
        }
        LOG.info("getSelectDesignRate result selectDesignRate:{}", selectDesignRate);
        return selectDesignRate;
    }

    /**
     * 硬装交付信息
     */
    private Integer getDeliverInfo(Integer orderId, HomeFrameResponse homeFrameResponse, Integer
            orderSaleType, Integer status) {
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
            Integer softTotal = deliverInfoVo.getSoftTotal() == null ? 0 : deliverInfoVo.getSoftTotal();
            Integer softFinishNum = deliverInfoVo.getSoftFinishNum() == null ? 0 : deliverInfoVo.getSoftFinishNum();
            homeFrameResponse.setSoftTotal(softTotal);
            homeFrameResponse.setSoftFinishNum(softFinishNum);
            if (orderSaleType != null && orderSaleType.equals(0)) {
                //软装+硬装
                if (softFinishNum > 0) {
                    if (softFinishNum == softTotal) {
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
            homeFrameResponse.setDnaHeadImgUrl(AliImageUtil.imageCompress(designResponseVo.getDnaHeadImg(), 2, width, ImageConstant.SIZE_MIDDLE));
        }
        if (StringUtils.isNotBlank(designResponseVo.getDnaStyle())) {
            homeFrameResponse.setDnaStyleName(designResponseVo.getDnaStyle());
        }
        LOG.info("setStyleInfo result true");
        return true;
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

    @Override
    public AboutUsResponse getAboutUsInfo(VersionBundleRequestVo request) {
        AboutUsResponse response = new AboutUsResponse();
        //查询评论 一条
        response.setCommentList(getUserCommentList(null));

        //省、楼盘、订单数查询
        AppSystemInfoResponseVo systemInfoResponseVo = productProgramOrderProxy.queryAppSystemInfo();
        if (systemInfoResponseVo != null) {
            if (systemInfoResponseVo.getProjectCount() != null) {
                response.setProjectNum(systemInfoResponseVo.getProjectCount());
            }
            if (systemInfoResponseVo.getProvinceCount() != null) {
                response.setProvinceNum(systemInfoResponseVo.getProvinceCount());
            }
            if (systemInfoResponseVo.getRoomCount() != null) {
                response.setUserNum(systemInfoResponseVo.getRoomCount());
            }
        }

        //banner
        List<Banner> bannerList = new ArrayList<Banner>();
        List<BannerResponseVo> bannerResponseVos = homeCardWcmProxy.queryBannerByType();
        homeV510PageService.reduceBannerByVersion(bannerResponseVos, request.getAppVersion());
        if (CollectionUtils.isNotEmpty(bannerResponseVos)) {
            for (BannerResponseVo bannerResponseVo : bannerResponseVos) {
                if (request.getBundleVersion() == null && (bannerResponseVo.getId().equals(11) || bannerResponseVo.getId().equals(12))) {
                    continue;
                }
                if (request.getOsType() == 1 && request.getBundleVersion() != null && VersionUtil.mustUpdate(request.getBundleVersion(), "5.1.0.3") && bannerResponseVo.getId().equals(12)) {
                    continue;
                }

                if (request.getOsType() == 2 && request.getBundleVersion() != null && VersionUtil.mustUpdate(request.getBundleVersion(), "5.1.1.3") && bannerResponseVo.getId().equals(12)) {
                    continue;
                }

                if (request.getBundleVersion() != null && bannerResponseVo.getId() >= 13) {
                    StringBuilder version = new StringBuilder();
                    version.append(bannerResponseVo.getBigVersion()).append(".").append(bannerResponseVo.getMidVersion()).append(".").append(bannerResponseVo.getSmallVersion()).append(".0");
                    if (VersionUtil.mustUpdate(request.getBundleVersion(), version.toString())) {
                        continue;
                    }
                }
                Banner banner = new Banner();
                banner.setHeadImage(bannerResponseVo.getImg());
                banner.setLinkUrl(bannerResponseVo.getUrl());
                bannerList.add(banner);
            }
        }
        response.setBanner(bannerList);

        return response;
    }

    @Override
    public PageModel getUserCommentList(UserCommentRequest request) {
        QueryOrderCommentRequestVo params = new QueryOrderCommentRequestVo();
        if (request != null) {
            params.setPageNo(request.getPageNo());
            params.setPageSize(request.getPageSize());
        } else {
            params.setPageNo(1);
            params.setPageSize(1);
        }

        PageModel commentPage = homeCardWcmProxy.queryOrderCommentListByCondition(params);

        return commentPage;
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

    @Override
    public String createSolutionCraft(CreateDraftRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("draftContent", request.getDraftJsonStr());
        params.put("orderNum", request.getOrderId());
        params.put("operationProgress", request.getDraftProgress());
        if (request.getDraftType() != null) {
            params.put("draftType", request.getDraftType());
        } else {
            params.put("draftType", 0);
        }
        return homeCardWcmProxy.addOrderDraft(params);
    }

    @Override
    public Boolean deleteSolutionDraft(QueryDraftRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderNum", request.getOrderId());
        return homeCardWcmProxy.deleteOrderDraft(params);
    }


    /**
     * 保存或更新草稿
     *
     * @param request
     * @return
     */
    @Override
    public String addOrUpdateDraft(DraftInfoRequest request) {
        log.info("draftContentBean.needDrawRoomList:" + JSON.toJSONString(request.getDraftContent().getNeedDrawRoomList()));
        Map<String, Object> params = createDraft(request);
        String draftId = null;
        if (request.getDraftProfileNum() == null) {//新增草稿
            draftId = homeCardWcmProxy.addDraft(params);
        } else {//更新草稿
            params.put("draftProfileNum", request.getDraftProfileNum());
            draftId = homeCardWcmProxy.updateDraft(params);
        }

        if (draftId == null) {
            throw new BusinessException(HttpReturnCode.WCM_FAILED, MessageConstant.FAILED);
        }
        if (request.getDraftContent() != null && request.getDraftContent().getSolutionSelected() != null) {
            setOnceReplaceCach(request.getOnceReplaceFlag(), request.getOrderId(), request.getDraftContent().getSolutionSelected().getSolutionId());
        }
        return draftId;
    }

    /**
     * 设置软硬装默认信息
     */
    private void setSoftHardDefault(DraftInfoRequest.DraftContentBean draftContent, SolutionEffectInfo
            solutionEffectInfo, List<OptionalSkusResponseVo> optionalSkusResponseVoList, List<SpaceDesignVo> spaceDesignVoList) {
        List<Integer> selectSpaceList = new ArrayList<>();//已选空间
        draftContent.getSpaceDesignSelected().forEach(spaceDesignSelected -> solutionEffectInfo.getSpaceDesignList().forEach(spaceDesign -> {
            if (spaceDesignSelected.getRoomId().equals(spaceDesign.getSpaceDesignId())) {//已选空间
                selectSpaceList.add(spaceDesign.getSpaceDesignId());
            }
        }));
        if (CollectionUtils.isNotEmpty(draftContent.getSpaceDesignSelected())) {
            draftContent.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
                if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getHardItemList())) {
                    spaceDesignSelectedBean.getHardItemList().removeIf(hardItemListBean -> hardItemListBean.getStatus().equals(0));
                }

                if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getSoftResponseList())) {
                    spaceDesignSelectedBean.getSoftResponseList().removeIf(softResponseListBean -> softResponseListBean.getStatus().equals(0));
                }
            });
        }

        List<DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean> SpaceDesignSelectedList = new ArrayList<>();
        Set<Integer> referenceOnlyRoomList = draftContent.getReferenceOnlyRoomList();
        List<OptionalSkusResponseVo> guiBomListForSoft = optionalSkusResponseVoList.stream().filter(optionalSkusResponseVo -> CollectionUtils.isNotEmpty(optionalSkusResponseVo.getBomGroupList()) && optionalSkusResponseVo.getBomGroupList().get(0) != null && optionalSkusResponseVo.getBomGroupList().get(0).getGroupType() != null && optionalSkusResponseVo.getBomGroupList().get(0).getGroupType().equals(10)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(guiBomListForSoft)) {
            optionalSkusResponseVoList.removeIf(optionalSkusResponseVo -> CollectionUtils.isNotEmpty(optionalSkusResponseVo.getBomGroupList()) && optionalSkusResponseVo.getBomGroupList().get(0) != null && optionalSkusResponseVo.getBomGroupList().get(0).getGroupType() != null && optionalSkusResponseVo.getBomGroupList().get(0).getGroupType().equals(10));
        }
        solutionEffectInfo.getSpaceDesignList().forEach(spaceDesign -> {
            DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean spaceDesignSelectedBean = new DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean();
            if (selectSpaceList.contains(spaceDesign.getSpaceDesignId())) {//已选空间
                for (DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean spaceDesignSelected : draftContent.getSpaceDesignSelected()) {
                    if (spaceDesignSelected.getRoomId().equals(spaceDesign.getSpaceDesignId())) {//已选空间
                        spaceDesignSelectedBean = spaceDesignSelected;
                        spaceDesignSelectedBean.getDefaultSpace().setHeadImage
                                (AliImageUtil.imageCompress(spaceDesignSelectedBean.getDefaultSpace().getHeadImage(), 2, 750, ImageConstant.SIZE_MIDDLE));
                        spaceDesignSelectedBean.getSelected().setHeadImage
                                (AliImageUtil.imageCompress(spaceDesignSelectedBean.getSelected().getHeadImage(), 2, 750, ImageConstant.SIZE_MIDDLE));
                        if (spaceDesignSelectedBean.getSelected().getRoomImage() != null && CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getSelected().getRoomImage().getOldPictureList())) {
                            spaceDesignSelectedBean.getSelected().getRoomImage().setOldPictureList(spaceDesignSelectedBean.getSelected().getRoomImage().getOldPictureList().stream().map(pictureUrls ->
                                    AliImageUtil.imageCompress(pictureUrls, 2, 750, ImageConstant.SIZE_MIDDLE)).collect(Collectors.toList()));
                        }
                        //..
                        if (CollectionUtils.isNotEmpty(referenceOnlyRoomList) && referenceOnlyRoomList.contains(spaceDesignSelected.getRoomId())) {
                            if (spaceDesignSelected.getSelected() != null && spaceDesignSelected.getSelected().getRoomImage() != null) {
                                spaceDesignSelected.getSelected().getRoomImage().setReferenceOnlyFlag(Constants.REFERENCE_ONLY_SHOW);
                            }
                        }
                    }
                }


                List<DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.SoftResponseListBean> softResponseList = new ArrayList<>();
                List<DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean> hardItemList = new ArrayList<>();
                DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean finalSpaceDesignSelectedBean = spaceDesignSelectedBean;
                if (optionalSkusResponseVoList != null) {
                    optionalSkusResponseVoList.forEach(optionalSkusResponseVo -> {//装载软装
                        if (optionalSkusResponseVo.getRoomId().equals(finalSpaceDesignSelectedBean.getSelected().getSpaceDesignId())) {//软装数据列表中空间id

                            List<String> selectSoftList = new ArrayList<>();//已选软装分类

                            DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.SoftResponseListBean softResponseListBean =
                                    new DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.SoftResponseListBean();

                            draftContent.getSpaceDesignSelected().forEach(draftSpaceDesign -> {
                                if (draftSpaceDesign.getRoomId().equals(optionalSkusResponseVo.getRoomId())) {
                                    if (draftSpaceDesign.getSoftResponseList() != null) {
                                        draftSpaceDesign.getSoftResponseList().forEach(softResponse -> {
                                            if (optionalSkusResponseVo.getSuperKey().equals(softResponse.getSuperKey())) {
                                                selectSoftList.add(softResponse.getSuperKey());
                                            }
                                        });
                                    }
                                }
                            });
                            if (selectSoftList.contains(optionalSkusResponseVo.getSuperKey())) {//唯一标识
                                for (DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean spaceDesignSelected : draftContent.getSpaceDesignSelected()) {
                                    if (spaceDesignSelected.getRoomId().equals(optionalSkusResponseVo.getRoomId())) {
                                        for (DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.SoftResponseListBean softResponseBean : spaceDesignSelected.getSoftResponseList()) {
                                            if (softResponseBean.getSuperKey() != null && softResponseBean.getSuperKey().equals(optionalSkusResponseVo.getSuperKey())) {
                                                softResponseListBean = softResponseBean;
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else if (CollectionUtils.isNotEmpty(optionalSkusResponseVo.getSubItemVos())) {
                                softResponseListBean.setDefaultSkuId(optionalSkusResponseVo.getSubItemVos().get(0).getSkuId())
                                        .setStatus(Constants.ITEM_STATUS_DEFAULT)
                                        .setCategory(optionalSkusResponseVo.getTypeTwoName())
                                        .setSuperKey(optionalSkusResponseVo.getSuperKey())
                                        .setFreeAble(ProductProgramServiceImpl.getFreeAble(optionalSkusResponseVo))
                                        .setLastCategoryId(optionalSkusResponseVo.getLastCategoryId())
                                        .setLastCategoryName(optionalSkusResponseVo.getLastCategoryName());
                                DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.SoftResponseListBean.FurnitureSelectedBean furnitureSelectedBean =
                                        new DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.SoftResponseListBean.FurnitureSelectedBean();

                                Integer furnitureType = optionalSkusResponseVo.getSubItemVos().get(0).getFurnitureType();
                                if (!ProductCategoryConstant.CATEGORY_THREE_ID_D_LIST.contains(optionalSkusResponseVo.getLastCategoryId())) {//除灯具外赠品，不显示效果图推荐
                                    furnitureType = 1;
                                }
                                furnitureSelectedBean.setCategoryLevelTwoId(optionalSkusResponseVo.getSubItemVos().get(0).getCategoryLevelTwoId())
                                        .setPrice(optionalSkusResponseVo.getSubItemVos().get(0).getPrice())
                                        .setPriceDiff(optionalSkusResponseVo.getSubItemVos().get(0).getPriceDiff())
                                        .setItemCount(optionalSkusResponseVo.getSubItemVos().get(0).getItemCount())
                                        .setItemName(optionalSkusResponseVo.getSubItemVos().get(0).getItemName())
                                        .setFurnitureType(optionalSkusResponseVo.getSubItemVos().get(0).getFurnitureType())
                                        .setFurnitureName(optionalSkusResponseVo.getSubItemVos().get(0).getItemName())
                                        .setSkuId(optionalSkusResponseVo.getSubItemVos().get(0).getSkuId())
                                        .setBrand(optionalSkusResponseVo.getSubItemVos().get(0).getItemBrand())
                                        .setColor(optionalSkusResponseVo.getSubItemVos().get(0).getItemColor())
                                        .setMaterial(optionalSkusResponseVo.getSubItemVos().get(0).getItemMaterial())
                                        .setSmallImage(AliImageUtil.imageCompress(optionalSkusResponseVo.getSubItemVos().get(0).getItemImage(), 2, 100, ImageConstant.SIZE_100))
                                        .setLastCategoryId(optionalSkusResponseVo.getSubItemVos().get(0).getLastCategoryId())
                                        .setFreeFlag(optionalSkusResponseVo.getSubItemVos().get(0).getFreeFlag())
                                        .setLastCategoryName(optionalSkusResponseVo.getSubItemVos().get(0).getLastCategoryName())
                                        .setShowFreeFlag(ProductProgramServiceImpl.getShowFreeFlag(optionalSkusResponseVo.getSubItemVos().get(0).getFreeFlag(), optionalSkusResponseVo.getFreeAble(), furnitureType));
                                softResponseListBean.setFurnitureSelected(furnitureSelectedBean);
                                softResponseListBean.setFurnitureDefault(furnitureSelectedBean.clone());

                            } else if (CollectionUtils.isNotEmpty(optionalSkusResponseVo.getBomGroupList())) {
                                softResponseListBean.setDefaultSkuId(optionalSkusResponseVo.getBomGroupList().get(0).getGroupId())
                                        .setStatus(0)
                                        .setCategory(optionalSkusResponseVo.getTypeTwoName())
                                        .setSuperKey(optionalSkusResponseVo.getSuperKey())
                                        .setFreeAble(ProductProgramServiceImpl.getFreeAble(optionalSkusResponseVo))
                                        .setLastCategoryName(optionalSkusResponseVo.getLastCategoryName())
                                        .setLastCategoryId(optionalSkusResponseVo.getLastCategoryId());
                                optionalSkusResponseVo.getBomGroupList().get(0).setLastCategoryId(optionalSkusResponseVo.getLastCategoryId());
                                optionalSkusResponseVo.getBomGroupList().get(0).setLastCategoryName(optionalSkusResponseVo.getLastCategoryName());
                                BomGroupDraftVo bomGroupDefault = new BomGroupDraftVo();
                                BeanUtils.copyProperties(optionalSkusResponseVo.getBomGroupList().get(0), bomGroupDefault);
                                bomGroupDefault.setGroupImage(AliImageUtil.imageCompress(bomGroupDefault.getGroupImage(), 2, 100, ImageConstant.SIZE_100));
                                bomGroupDefault.setShowFreeFlag(ProductProgramServiceImpl.getShowFreeFlag(bomGroupDefault.getFreeFlag(), ProductProgramServiceImpl.getFreeAble(optionalSkusResponseVo), bomGroupDefault.getFurnitureType()));
                                softResponseListBean.setBomGroupDefault(bomGroupDefault);
                                softResponseListBean.setBomGroupSelected(bomGroupDefault.clone());
                            }
                            softResponseList.add(softResponseListBean);
                        }
                    });
                    if (CollectionUtils.isNotEmpty(guiBomListForSoft)) {
                        List<String> selectGuiBomList = Lists.newArrayList();
                        //空间替换类目收集
                        draftContent.getSpaceDesignSelected().forEach(draftSpaceDesign -> {
                            if (draftSpaceDesign.getRoomId().equals(spaceDesign.getSpaceDesignId())) {
                                if (draftSpaceDesign.getSoftResponseList() != null) {
                                    draftSpaceDesign.getSoftResponseList().forEach(softResponse -> {
                                        if (softResponse.getCabinetBomGroup() != null) {
                                            selectGuiBomList.add(draftSpaceDesign.getRoomId() + ":" + softResponse.getCabinetBomGroup().getSecondCategoryId());
                                        }
                                    });
                                }
                            }
                        });
                        Map<Integer, List<OptionalSkusResponseVo>> guiBomListForSoftByRoom = guiBomListForSoft.stream().collect(Collectors.groupingBy(OptionalSkusResponseVo::getRoomId));
                        guiBomListForSoftByRoom.forEach((roomId, skusResponseVo) -> {
                            Map<Integer, List<OptionalSkusResponseVo>> softBomResponseBySecondCategoryId = skusResponseVo.stream().collect(Collectors.groupingBy(mapper -> mapper.getBomGroupList().get(0).getSecondCategoryId()));
                            softBomResponseBySecondCategoryId.forEach((key, value) -> {
                                if (CollectionUtils.isNotEmpty(value)) {
                                    OptionalSkusResponseVo optionalSkusResponseVo2 = value.get(0);
                                    if (optionalSkusResponseVo2.getRoomId().equals(spaceDesign.getSpaceDesignId())) {
                                        DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.SoftResponseListBean softResponseListBean =
                                                new DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.SoftResponseListBean();
                                        if (selectGuiBomList.contains(roomId + ":" + value.get(0).getBomGroupList().get(0).getSecondCategoryId())) {
                                            //替换
                                            for (DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean spaceDesignSelected : draftContent.getSpaceDesignSelected()) {
                                                if (spaceDesignSelected.getRoomId().equals(spaceDesign.getSpaceDesignId())) {
                                                    for (DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.SoftResponseListBean softResponseBean : spaceDesignSelected.getSoftResponseList()) {
                                                        if (softResponseBean.getCabinetBomGroup() != null && softResponseBean.getCabinetBomGroup().getSecondCategoryId().equals(key)) {
                                                            if (softResponseBean.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(softResponseBean.getCabinetBomGroup().getReplaceBomList())) {
                                                                softResponseBean.getCabinetBomGroup().getReplaceBomList().forEach(replaceBomDto -> {
                                                                    if (replaceBomDto.getBomGroupSelect() == null) {
                                                                        HardBomGroup hardBomGroup = null;
                                                                        hardBomGroup = replaceBomDto.getBomGroupDefault().clone();
                                                                        replaceBomDto.setBomGroupSelect(hardBomGroup);
                                                                    }
                                                                });
                                                            }
                                                            softResponseListBean = softResponseBean;
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            OptionalSkusResponseVo optionalSkusResponseVo = value.get(0);
                                            softResponseListBean
                                                    .setStatus(0)
                                                    .setCategory("定制家具")
                                                    .setSuperKey(optionalSkusResponseVo.getSuperKey())
                                                    .setLastCategoryName(optionalSkusResponseVo.getLastCategoryName())
                                                    .setLastCategoryId(optionalSkusResponseVo.getBomGroupList().get(0).getSecondCategoryId());
                                            CabinetBomDto cabinetBomDto = new CabinetBomDto();
                                            BeanUtils.copyProperties(optionalSkusResponseVo.getBomGroupList().get(0), cabinetBomDto);
                                            cabinetBomDto.setFurnitureType(4);
                                            cabinetBomDto.setGroupImage(AliImageUtil.imageCompress(optionalSkusResponseVo.getBomGroupList().get(0).getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                                            cabinetBomDto.setGroupType(10);
                                            cabinetBomDto.setSuperKey(optionalSkusResponseVo.getSuperKey());
                                            List<ReplaceBomDto> replaceBomDtoList = value.stream().map(optionalSkusResponseVoFor -> {
                                                ReplaceBomDto replaceBomDto = new ReplaceBomDto();
                                                HardBomGroup hardBomGroup = new HardBomGroup();
                                                BeanUtils.copyProperties(optionalSkusResponseVoFor.getBomGroupList().get(0), hardBomGroup);
                                                hardBomGroup.setGroupImage(hardBomGroup.getGroupImage());
                                                replaceBomDto.setBomGroupDefault(hardBomGroup);
                                                replaceBomDto.setBomGroupSelect(hardBomGroup.clone());
                                                return replaceBomDto;
                                            }).collect(Collectors.toList());
                                            cabinetBomDto.setReplaceBomList(replaceBomDtoList);
                                            BigDecimal priceDiffSum = value.stream().map(roomHardItem -> roomHardItem.getBomGroupList().get(0).getPriceDiff()).reduce(BigDecimal.ZERO, BigDecimal::add);
                                            BigDecimal priceSum = value.stream().map(roomHardItem -> roomHardItem.getBomGroupList().get(0).getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                                            cabinetBomDto.setPriceDiff(priceDiffSum);
                                            cabinetBomDto.setPrice(priceSum);
                                            softResponseListBean.setCabinetBomGroup(cabinetBomDto);
                                        }
                                        softResponseList.add(softResponseListBean);
                                    }
                                }
                            });
                        });
                    }
                }
                if (spaceDesignVoList != null) {
                    DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean finalSpaceDesignSelectedBean1 = spaceDesignSelectedBean;
                    spaceDesignVoList.forEach(spaceDesignVo -> {//装载硬装
                        if (spaceDesignVo.getSpaceDesignId().equals(finalSpaceDesignSelectedBean.getSelected().getSpaceDesignId())) {
                            List<String> selectHardList = new ArrayList<>();//已选硬装分类
                            draftContent.getSpaceDesignSelected().forEach(draftSpaceDesign -> {
                                if (draftSpaceDesign.getRoomId().equals(spaceDesignVo.getSpaceDesignId())) {
                                    spaceDesignVo.getHardItemList().forEach(roomHardItemClass -> {
                                        if (draftSpaceDesign.getHardItemList() != null) {
                                            draftSpaceDesign.getHardItemList().forEach(hardItemListBean -> {
                                                if (roomHardItemClass.getSuperKey().equals(hardItemListBean.getSuperKey())) {
                                                    selectHardList.add(hardItemListBean.getSuperKey());
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                            List<String> superKeyList = spaceDesignVo.getHardItemList().stream().map(RoomHardItemClass::getSuperKey).collect(Collectors.toList());
                            List<RoomHardItemClass> guiBomListForHard = spaceDesignVo.getHardItemList().stream().filter(roomHardItemClass -> roomHardItemClass.getBomGroup() != null && roomHardItemClass.getBomGroup().getGroupType().equals(9)).collect(Collectors.toList());
                            if (CollectionUtils.isNotEmpty(guiBomListForHard)) {
                                spaceDesignVo.getHardItemList().removeIf(roomHardItemClass -> roomHardItemClass.getBomGroup() != null && roomHardItemClass.getBomGroup().getGroupType().equals(9));
                            }
                            spaceDesignVo.getHardItemList().forEach(roomHardItemClass -> {
                                DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean hardItemListBean = null;
                                if (selectHardList.contains(roomHardItemClass.getSuperKey())) {//已包含
                                    for (DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean spaceDesignSelectedBeanx : draftContent.getSpaceDesignSelected()) {
                                        if ((spaceDesignSelectedBeanx.getRoomId()).equals(spaceDesignVo.getSpaceDesignId())) {
                                            for (DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean hardItemListBeanY : spaceDesignSelectedBeanx.getHardItemList()) {
                                                if (hardItemListBeanY.getSuperKey().equals(roomHardItemClass.getSuperKey())) {
                                                    if ((RoomUseEnum.ROOM_WHOLE.getCode() == spaceDesignSelectedBeanx.getSpaceUsageId()
                                                            || RoomUseEnum.ROOM_WHOLE.getDescription().equals(spaceDesignSelectedBeanx.getSpaceUsageName())) && hardItemListBeanY.getHardItemSelected() != null
                                                            && hardItemListBeanY.getHardItemSelected().getProcessSelected() != null
                                                            && hardItemListBeanY.getHardItemSelected().getProcessSelected().getSelectChildHardItem() != null) {//全屋空间
                                                        hardItemListBeanY.getHardItemSelected().getProcessSelected()
                                                                .setSelectChildHardItem(hardItemListBeanY.getHardItemSelected().getProcessSelected().getSelectChildHardItem());
                                                    } else if ((hardItemListBeanY.getStatus() == Constants.ITEM_STATUS_ADD ||
                                                            hardItemListBeanY.getStatus() == Constants.ITEM_STATUS_SELECTED) &&
                                                            RoomUseEnum.ROOM_WHOLE.getCode() != spaceDesignSelectedBeanx.getSpaceUsageId() &&
                                                            !RoomUseEnum.ROOM_WHOLE.getDescription().equals(spaceDesignSelectedBeanx.getSpaceUsageName())) {//非全屋空间，进行了硬装替换或增加商品
                                                        if (finalSpaceDesignSelectedBean1.getSelected() != null && finalSpaceDesignSelectedBean1.getSelected().getRoomImage() != null) {
                                                            finalSpaceDesignSelectedBean1.getSelected().getRoomImage().setReferenceOnlyFlag(Constants.REFERENCE_ONLY_SHOW);
                                                        }
                                                    }
                                                    hardItemListBean = hardItemListBeanY;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                } else if (RoomUseEnum.ROOM_WHOLE.getCode() != spaceDesignVo.getSpaceUsageId()
                                        && !RoomUseEnum.ROOM_WHOLE.getDescription().equals(spaceDesignVo.getSpaceUsageName())) {//硬装默认项
                                    List<RoomHardItem> hardItemClassList = roomHardItemClass.getHardItemClassList();
                                    if (CollectionUtils.isNotEmpty(hardItemClassList)) {//必须有默认项目
                                        hardItemListBean = new DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean();
                                        hardItemListBean.setStatus(Constants.ITEM_STATUS_DEFAULT);
                                        hardItemListBean.setHardItemName(roomHardItemClass.getHardItemClassName());
                                        hardItemListBean.setHardItemId(roomHardItemClass.getHardItemClassId());
                                        DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean.HardItemBean hardItemSelected =
                                                new DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean.HardItemBean();
                                        DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean.HardItemBean hardItemDefault =
                                                new DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean.HardItemBean();

                                        hardItemSelected.setHardSelectionId(hardItemClassList.get(0).getHardItemId())
                                                .setHardSelectionName(hardItemClassList.get(0).getHardItemName())
                                                .setSmallImage(AliImageUtil.imageCompress(hardItemClassList.get(0).getHardItemHeadImage(), 2, 100, ImageConstant.SIZE_100));
                                        hardItemDefault.setHardSelectionId(hardItemClassList.get(0).getHardItemId())
                                                .setHardSelectionName(hardItemClassList.get(0).getHardItemName())
                                                .setSmallImage(AliImageUtil.imageCompress(hardItemClassList.get(0).getHardItemHeadImage(), 2, 100, ImageConstant.SIZE_100));

                                        DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean.HardItemBean.ProcessBean processSelected =
                                                new DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean.HardItemBean.ProcessBean();
                                        DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean.HardItemBean.ProcessBean processDefault =
                                                new DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean.HardItemBean.ProcessBean();
                                        List<HardItemCraft> hardItemCraftList = hardItemClassList.get(0).getHardItemCraftList();
                                        hardItemCraftList.forEach(hardItemCraft -> {
                                            if (hardItemCraft.isCraftDefault()) {
                                                processSelected.setPrice(hardItemCraft.getTotalPrice())
                                                        .setPriceDiff(hardItemCraft.getPriceDiff())
                                                        .setProcessId(hardItemCraft.getCraftId())
                                                        .setProcessName(hardItemCraft.getCraftName());
                                                processDefault.setPrice(hardItemCraft.getTotalPrice())
                                                        .setPriceDiff(hardItemCraft.getPriceDiff())
                                                        .setProcessId(hardItemCraft.getCraftId())
                                                        .setProcessName(hardItemCraft.getCraftName());
                                            }
                                        });
                                        hardItemSelected.setProcessSelected(processSelected);
                                        hardItemDefault.setProcessSelected(processDefault);


                                        hardItemListBean.setHardItemSelected(hardItemSelected);
                                        hardItemListBean.setSuperKey(roomHardItemClass.getSuperKey());
                                        hardItemListBean.setHardItemDefault(hardItemDefault);
                                    } else if (roomHardItemClass.getBomGroup() != null && roomHardItemClass.getIsStandardItem().equals(1)) {
                                        //硬装标配bom
                                        hardItemListBean = new DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean();
                                        roomHardItemClass.getBomGroup().setGroupImage(AliImageUtil.imageCompress(roomHardItemClass.getBomGroup().getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                                        hardItemListBean.setHardBomGroupDefault(roomHardItemClass.getBomGroup().clone());
                                        hardItemListBean.setHardBomGroupSelect(roomHardItemClass.getBomGroup().clone());
                                        hardItemListBean.setSuperKey(roomHardItemClass.getSuperKey());
                                        hardItemListBean.setStatus(Constants.ITEM_STATUS_DEFAULT);
                                        hardItemListBean.setHardItemName(roomHardItemClass.getHardItemClassName());
                                        hardItemListBean.setHardItemId(roomHardItemClass.getHardItemClassId());
                                    }
                                }
                                if (hardItemListBean != null) {
                                    hardItemList.add(hardItemListBean);
                                }
                            });
                            if (CollectionUtils.isNotEmpty(guiBomListForHard)) {
                                List<String> selectGuiBomList = Lists.newArrayList();
                                //空间替换类目收集
                                draftContent.getSpaceDesignSelected().forEach(draftSpaceDesign -> {
                                    if (draftSpaceDesign.getRoomId().equals(spaceDesign.getSpaceDesignId())) {
                                        if (draftSpaceDesign.getHardItemList() != null) {
                                            draftSpaceDesign.getHardItemList().forEach(hardItemListBean -> {
                                                if (hardItemListBean.getCabinetBomGroup() != null) {
                                                    selectGuiBomList.add(draftSpaceDesign.getRoomId() + ":" + hardItemListBean.getCabinetBomGroup().getSecondCategoryId());
                                                }
                                            });
                                        }
                                    }
                                });
                                Map<Integer, List<RoomHardItemClass>> hardGuiBomBySecondCategoryId = guiBomListForHard.stream().collect(Collectors.groupingBy(roomHardItemClass -> roomHardItemClass.getBomGroup().getSecondCategoryId()));

                                hardGuiBomBySecondCategoryId.forEach((secondCategoryId, bomGroupVOList) -> {
                                    DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean hardItemListBeanY = null;
                                    if (selectGuiBomList.contains(spaceDesign.getSpaceDesignId() + ":" + secondCategoryId)) {
                                        for (DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean spaceDesignSelected : draftContent.getSpaceDesignSelected()) {
                                            if (spaceDesignSelected.getRoomId().equals(spaceDesign.getSpaceDesignId())) {
                                                for (DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean hardItemListBean : spaceDesignSelected.getHardItemList()) {
                                                    if (hardItemListBean.getCabinetBomGroup() != null && hardItemListBean.getCabinetBomGroup().getSecondCategoryId().equals(secondCategoryId)) {
                                                        if (hardItemListBean.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(hardItemListBean.getCabinetBomGroup().getReplaceBomList())) {
                                                            for (ReplaceBomDto replaceBomDto : hardItemListBean.getCabinetBomGroup().getReplaceBomList()) {
                                                                if (replaceBomDto.getBomGroupSelect() == null) {
                                                                    HardBomGroup hardBomGroup = null;
                                                                    hardBomGroup = replaceBomDto.getBomGroupDefault().clone();
                                                                    replaceBomDto.setBomGroupSelect(hardBomGroup);
                                                                }
                                                            }
                                                        }
                                                        hardItemListBeanY = hardItemListBean;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        RoomHardItemClass roomHardItemClass = bomGroupVOList.get(0);
                                        hardItemListBeanY = new DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean.HardItemListBean();
                                        hardItemListBeanY.setSuperKey(roomHardItemClass.getSuperKey());
                                        hardItemListBeanY.setStatus(Constants.ITEM_STATUS_DEFAULT);
                                        hardItemListBeanY.setHardItemName("定制家具");
                                        hardItemListBeanY.setHardItemId(roomHardItemClass.getBomGroup().getSecondCategoryId());
                                        CabinetBomDto cabinetBomDto = new CabinetBomDto();
                                        BeanUtils.copyProperties(roomHardItemClass.getBomGroup(), cabinetBomDto);
                                        cabinetBomDto.setFurnitureType(4);
                                        cabinetBomDto.setGroupImage(AliImageUtil.imageCompress(roomHardItemClass.getBomGroup().getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                                        cabinetBomDto.setGroupType(9);
                                        cabinetBomDto.setSuperKey(roomHardItemClass.getSuperKey());
                                        List<ReplaceBomDto> replaceBomDtoList = bomGroupVOList.stream().map(hardItemClass -> {
                                            ReplaceBomDto replaceBomDto = new ReplaceBomDto();
                                            HardBomGroup hardBomGroup = new HardBomGroup();
                                            BeanUtils.copyProperties(hardItemClass.getBomGroup(), hardBomGroup);
                                            hardBomGroup.setGroupImage(hardBomGroup.getGroupImage());
                                            replaceBomDto.setBomGroupDefault(hardBomGroup);
                                            replaceBomDto.setBomGroupSelect(hardBomGroup.clone());
                                            return replaceBomDto;
                                        }).collect(Collectors.toList());
                                        cabinetBomDto.setReplaceBomList(replaceBomDtoList);
                                        BigDecimal priceDiffSum = bomGroupVOList.stream().map(roomHardItem -> roomHardItem.getBomGroup().getPriceDiff()).reduce(BigDecimal.ZERO, BigDecimal::add);
                                        BigDecimal priceSum = bomGroupVOList.stream().map(roomHardItem -> roomHardItem.getBomGroup().getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                                        cabinetBomDto.setPriceDiff(priceDiffSum);
                                        cabinetBomDto.setPrice(priceSum);
                                        hardItemListBeanY.setCabinetBomGroup(cabinetBomDto);
                                    }
                                    hardItemList.add(hardItemListBeanY);
                                });
                            }
                        }
                    });
                }
                spaceDesignSelectedBean.setSoftResponseList(softResponseList);
                spaceDesignSelectedBean.setHardItemList(hardItemList);

                SpaceDesignSelectedList.add(spaceDesignSelectedBean);
            }
        });
        //定制柜价格聚合
        SpaceDesignSelectedList.parallelStream().forEach(spaceDesignSelectedBean -> {
            if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getHardItemList())) {
                spaceDesignSelectedBean.getHardItemList().forEach(hardItemListBean -> {
                    if (hardItemListBean.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(hardItemListBean.getCabinetBomGroup().getReplaceBomList())) {
                        hardItemListBean.getCabinetBomGroup().setPrice(hardItemListBean.getCabinetBomGroup().getReplaceBomList().stream().map(replaceBomDto -> replaceBomDto.getBomGroupSelect().getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
                        hardItemListBean.getCabinetBomGroup().setPriceDiff(hardItemListBean.getCabinetBomGroup().getReplaceBomList().stream().map(replaceBomDto -> replaceBomDto.getBomGroupSelect().getPriceDiff()).reduce(BigDecimal.ZERO, BigDecimal::add));
                    }
                });
            }
            if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getSoftResponseList())) {
                spaceDesignSelectedBean.getSoftResponseList().forEach(softResponseListBean -> {
                    if (softResponseListBean.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(softResponseListBean.getCabinetBomGroup().getReplaceBomList())) {
                        softResponseListBean.getCabinetBomGroup().setPriceDiff(softResponseListBean.getCabinetBomGroup().getReplaceBomList().stream().map(replaceBomDto -> replaceBomDto.getBomGroupSelect().getPriceDiff()).reduce(BigDecimal.ZERO, BigDecimal::add));
                        softResponseListBean.getCabinetBomGroup().setPrice(softResponseListBean.getCabinetBomGroup().getReplaceBomList().stream().map(replaceBomDto -> replaceBomDto.getBomGroupSelect().getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
                    }
                });
            }

        });
        draftContent.setSpaceDesignSelected(SpaceDesignSelectedList);
    }


    /**
     * 方案列表查询
     *
     * @param request
     * @return
     */
    public SolutionEffectResponse querySolutionInfoList(SolutionListRequest request) {
        SolutionEffectResponse response = productProgramProxy.querySolutionListByHouseId(request.getHouseTypeId(), request.getOrderId(), request.getSolutionId());
        if (response == null) {
            return null;
        }
        if (CollectionUtils.isNotEmpty(response.getSpaceMarkList())) {
            //过滤没有设计的空间标识
            List<SpaceMark> emptySpaceList = new ArrayList<>();
            for (SpaceMark spaceMark : response.getSpaceMarkList()) {
                if (CollectionUtils.isEmpty(spaceMark.getSpaceDesignList())) {
                    emptySpaceList.add(spaceMark);
                }
            }
            response.getSpaceMarkList().removeAll(emptySpaceList);
        }
        return response;
    }


    private Map<String, Object> concurrentQuerySkuList(List<Integer> spaceIdList, DraftInfoRequest request) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);

        //根据空间ID集合查询可替换sku
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() throws Exception {
                return productProgramProxy.batchQuerySoftItemByRoom(spaceIdList);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SOFT_ITEM_BY_ROOM.name();
            }
        });

        // 根据空间id集合查询硬装选配项
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() throws Exception {
                return productProgramProxy.batchQueryHardItemByRoom(spaceIdList);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_HARD_ITEM_BY_ROOM.name();
            }
        });

        if (request.getDraftContent().getSolutionSelected().getReformFlag() != null && request.getDraftContent().getSolutionSelected().getReformFlag() == 1) {
            // 查询服务费
            queryTasks.add(new IdentityTaskAction<Object>() {
                @Override
                public Object doInAction() throws Exception {
                    return productProgramProxy.querySolutionService(request.getDraftContent().getSolutionSelected().getSolutionId().longValue());
                }

                @Override
                public String identity() {
                    return ConcurrentTaskEnum.QUERY_SOLUTION_SERVICE.name();
                }
            });
        }

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 查询草稿
     *
     * @param request
     * @return
     */
    @Override
    public DraftInfoResponse queryDraftInfo(QueryDraftRequest request) {
        long start = System.currentTimeMillis();
        Map<String, Object> params = new HashMap<>();
        if (request.getDraftProfileNum() != null) {
            params.put("draftProfileNum", request.getDraftProfileNum());
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
        if (request.getQueryType().equals(1)) {
            params.put("draftSignStatusList", new Integer[]{1});
        }
        // 获取草稿基础信息
        DraftInfoResponse draftInfoResponse = homeCardWcmProxy.queryDraftInfo(params);
        //数据库中草稿方案总价
        Integer initialTotalPrice = draftInfoResponse.getDraftContent().getTotalPrice();
        draftInfoResponse.getDraftContent().setBomTotalDiffPrice(BigDecimal.ZERO);
        //进行校验
        doCkeckProcedure(draftInfoResponse, request.getOrderId());
        //方案总价加上bom的替换差价
        draftInfoResponse.getDraftContent().addTotalPrice(draftInfoResponse.getDraftContent().getBomTotalDiffPrice().intValue());
        //实时方案总价
        Integer finalTotalPrice = draftInfoResponse.getDraftContent().getTotalPrice();
        if (initialTotalPrice != null && finalTotalPrice != null && !initialTotalPrice.equals(finalTotalPrice)) {//方案总价价格变动提示
            draftInfoResponse.getDraftContent().setTotalPriceChangeFlag(1);
            draftInfoResponse.getDraftContent().setTotalPriceChangeAmount(finalTotalPrice - initialTotalPrice);
        }

        draftInfoResponse.getDraftContent().getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
            DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SpaceBean selected = spaceDesignSelectedBean.getSelected();
            RoomImageDto roomImage = selected.getRoomImage();
            if (roomImage != null) {
                List<String> pictureUrls = roomImage.getOldPictureList();
                if (CollectionUtils.isNotEmpty(pictureUrls)) {
                    roomImage.setOldPictureList(pictureUrls.stream().map(s -> AliImageUtil.imageCompress(s, null, 750, ImageConstant.SIZE_MIDDLE)).collect(Collectors.toList()));
                }
                if (CollectionUtils.isNotEmpty(roomImage.getPictureList())) {
                    roomImage.setPictureList(roomImage.getPictureList().stream().map(s -> AliImageUtil.imageCompress(s, null, 750, ImageConstant.SIZE_MIDDLE)).collect(Collectors.toList()));
                }
            }
        });
        if (CollectionUtils.isNotEmpty(draftInfoResponse.getDraftContent().getSolutionSelected().getServiceItemList())) {
            draftInfoResponse.getDraftContent().getSolutionSelected().getServiceItemList().forEach(serviceItemDto -> {
                serviceItemDto.setSkuImage(AliImageUtil.imageCompress(serviceItemDto.getSkuImage(), 2, 750, ImageConstant.SIZE_SMALL));
            });
        }
        draftInfoResponse.getDraftContent().getSolutionSelected().setServiceItemList(productProgramService.mergeServiceItemList(draftInfoResponse.getDraftContent().getSolutionSelected().getServiceItemList()));
        long end = System.currentTimeMillis();
        LOG.info("queryDraftInfo time consuming :{}", end - start);
        setReplacedAbleDto(draftInfoResponse);
        setReplacedBomAbleDto(draftInfoResponse);
        return draftInfoResponse;
    }

    /**
     * 设置已替换dto
     *
     * @param spaceDesignList
     * @return
     */
    private void setReplacedAbleDto(DraftInfoResponse draftInfoResponse) {
        try {
            DraftInfoResponse.DraftJsonStrBean draftContent = draftInfoResponse.getDraftContent();
            ReplaceAbleDto replaceAbleDto = new ReplaceAbleDto();
            replaceAbleDto.setSolutionId(draftContent.getSolutionSelected().getSolutionId().longValue());
            List<ReplaceAbleDto.SpaceDesignListBean> spaceDesignDtoList = new ArrayList();
            for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean spaceDesignSimple : draftContent.getSpaceDesignSelected()) {
                ReplaceAbleDto.SpaceDesignListBean spaceDesignListBean = new ReplaceAbleDto.SpaceDesignListBean();
                spaceDesignListBean.setSpaceDesignId(spaceDesignSimple.getSpaceDesignId());
                List<ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean> optionalSoftResponseList = new ArrayList();
                if (CollectionUtils.isNotEmpty(spaceDesignSimple.getSoftResponseList())) {
                    for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean optionalSoftResponse : spaceDesignSimple.getSoftResponseList()) {
                        ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean optionalSoftResponseListBean = new ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean();
                        optionalSoftResponseListBean.setRoomId(optionalSoftResponse.getRoomId());
                        optionalSoftResponseListBean.setLastCategoryId(optionalSoftResponse.getLastCategoryId());
                        optionalSoftResponseListBean.setSuperKey(optionalSoftResponse.getSuperKey());
                        if (optionalSoftResponse.getFurnitureSelected() != null && optionalSoftResponse.getFurnitureSelected().getFreeFlag() != null && optionalSoftResponse.getFurnitureSelected().getFreeFlag() == 1) {
                            ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean.FurnitureDefaultBean furnitureDefault = new ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean.FurnitureDefaultBean();
                            furnitureDefault.setSkuId(optionalSoftResponse.getFurnitureDefault().getSkuId());
                            optionalSoftResponseListBean.setFurnitureDefault(furnitureDefault);
                            optionalSoftResponseList.add(optionalSoftResponseListBean);
                        }
                    }
                }
                spaceDesignListBean.setOptionalSoftResponseList(optionalSoftResponseList);
                spaceDesignDtoList.add(spaceDesignListBean);
            }
            if (CollectionUtils.isNotEmpty(spaceDesignDtoList)) {
                spaceDesignDtoList.removeIf(spaceDesignListBean -> CollectionUtils.isEmpty(spaceDesignListBean.getOptionalSoftResponseList()));
            }
            if (CollectionUtils.isEmpty(spaceDesignDtoList)) {
                draftInfoResponse.getDraftContent().setReplacedAbleDto(null);
            } else {
                replaceAbleDto.setSpaceDesignList(spaceDesignDtoList);
                draftInfoResponse.getDraftContent().setReplacedAbleDto(replaceAbleDto);
            }
        } catch (Exception e) {
            LOG.info("setReplacedAbleDto error", e);
        }

    }

    /**
     * 设置bom已替换dto
     *
     * @param spaceDesignList
     * @return
     */
    private void setReplacedBomAbleDto(DraftInfoResponse draftInfoResponse) {
        try {
            DraftInfoResponse.DraftJsonStrBean draftContent = draftInfoResponse.getDraftContent();
            ReplaceAbleDto replaceAbleDto = new ReplaceAbleDto();
            replaceAbleDto.setSolutionId(draftContent.getSolutionSelected().getSolutionId().longValue());
            List<ReplaceAbleDto.SpaceDesignListBean> spaceDesignDtoList = new ArrayList();
            for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean spaceDesignSimple : draftContent.getSpaceDesignSelected()) {
                ReplaceAbleDto.SpaceDesignListBean spaceDesignListBean = new ReplaceAbleDto.SpaceDesignListBean();
                spaceDesignListBean.setSpaceDesignId(spaceDesignSimple.getSpaceDesignId());
                List<ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean> optionalSoftResponseList = new ArrayList();
                for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean optionalSoftResponse : spaceDesignSimple.getSoftResponseList()) {
                    ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean optionalSoftResponseListBean = new ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean();
                    optionalSoftResponseListBean.setRoomId(optionalSoftResponse.getRoomId());
                    optionalSoftResponseListBean.setLastCategoryId(optionalSoftResponse.getLastCategoryId());
                    optionalSoftResponseListBean.setSuperKey(optionalSoftResponse.getSuperKey());
                    if (optionalSoftResponse.getBomGroupSelected() != null && optionalSoftResponse.getBomGroupSelected().getFreeFlag() != null && optionalSoftResponse.getBomGroupSelected().getFreeFlag() == 1) {//bom软装
                        List<ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean.BomGroupListBean> bomGroupList = new ArrayList<>();
                        ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean.BomGroupListBean furnitureDefault = new ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean.BomGroupListBean();
                        furnitureDefault.setGroupId(optionalSoftResponse.getBomGroupSelected().getGroupId());
                        bomGroupList.add(furnitureDefault);
                        optionalSoftResponseListBean.setBomGroupList(bomGroupList);
                        optionalSoftResponseList.add(optionalSoftResponseListBean);
                    }
                    if(optionalSoftResponse.getCabinetBomGroup() != null && optionalSoftResponse.getCabinetBomGroup().getReplaceBomList() != null &&
                            CollectionUtils.isNotEmpty(optionalSoftResponse.getCabinetBomGroup().getReplaceBomList())){
                        if(optionalSoftResponse.getCabinetBomGroup().getReplaceBomList().size() > 1){
                            optionalSoftResponse.getCabinetBomGroup().setGroupImage(spaceDesignSimple.getSelected().getHeadImage());
                        }
                    }

                }
                spaceDesignListBean.setOptionalSoftResponseList(optionalSoftResponseList);
                spaceDesignDtoList.add(spaceDesignListBean);
            }
            if (CollectionUtils.isNotEmpty(spaceDesignDtoList)) {
                spaceDesignDtoList.removeIf(spaceDesignListBean -> CollectionUtils.isEmpty(spaceDesignListBean.getOptionalSoftResponseList()));
            }
            if (CollectionUtils.isEmpty(spaceDesignDtoList)) {
                draftInfoResponse.getDraftContent().setReplacedBomAbleDto(null);
            } else {
                replaceAbleDto.setSpaceDesignList(spaceDesignDtoList);
                draftInfoResponse.getDraftContent().setReplacedBomAbleDto(replaceAbleDto);
            }
        } catch (Exception e) {
            LOG.error("setReplacedBomAbleDto error", e);
        }

    }


    /**
     * 进入校验线程
     */
    private void doCkeckProcedure(DraftInfoResponse draftInfoResponse, Integer orderId) {
        List<TaskAction<Object>> taskActions = new ArrayList<>();
        taskActions.add(() -> {
            try {
                //查询方案、空间、sku（下单参数中的信息）是否下架
                setSolutionAndSkuStatus(draftInfoResponse);
            } catch (Exception e) {

            }
            return 1;
        });
        taskActions.add(() -> {
            try {
                //方案及空间信息设置
                setSolutionInfo(draftInfoResponse.getDraftContent(), orderId);
            } catch (Exception e) {

            }
            return 1;
        });
        taskActions.add(() -> {
            try {
                //sku价格校验
                setPriceDiff(draftInfoResponse.getDraftContent());
            } catch (Exception e) {

            }
            return 1;
        });
        taskActions.add(() -> {
            try {
                //bom价格校验
                setBomPriceCheck(draftInfoResponse.getDraftContent());
            } catch (Exception e) {

            }
            return 1;
        });
        taskActions.add(() -> {
            try {
                //软装sku详情更新
                setSoftSkuBaseInfo(draftInfoResponse.getDraftContent());
            } catch (Exception e) {

            }
            return 1;
        });
        taskActions.add(() -> {
            try {
                //软装skuBom类目信息更新
                setSoftSkuBomBaseInfo(draftInfoResponse.getDraftContent());
            } catch (Exception e) {

            }
            return 1;
        });
        taskActions.add(() -> {
            try {
                //定制柜bom校验
                setCabinetBomPriceCheck(draftInfoResponse.getDraftContent());
            } catch (Exception e) {

            }
            return 1;
        });
        taskActions.add(() -> {
            try {
                //sku属性匹配验证
                bedsAndMattressesCheck(draftInfoResponse.getDraftContent());
            } catch (Exception e) {

            }
            return 1;
        });
        taskActions.add(() -> {
            try {
                //补全sku字段
                setSkuBasicInfo(draftInfoResponse);
            } catch (Exception e) {

            }
            return 1;
        });
        TaskProcessManager.getTaskProcess().executeTask(taskActions);
    }

    private void setSkuBasicInfo(DraftInfoResponse draftInfoResponse) {
        // 硬装sku以及可替换项 解析
        List<SpaceDesignVo> spaceDesignVoList = productProgramProxy.batchQueryHardItemByRoom(draftInfoResponse.getDraftContent().getSpaceDesignSelected().stream().map(spaceDesignSelectedBean -> spaceDesignSelectedBean.getRoomId()).collect(Collectors.toList()));
        Set<Integer> skuIdList = Sets.newHashSet();
        Set<Integer> groupIdList = Sets.newHashSet();
        draftInfoResponse.getDraftContent().getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
            if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getSoftResponseList())) {
                spaceDesignSelectedBean.getSoftResponseList().forEach(softResponseListBean -> {
                    if (softResponseListBean.getFurnitureDefault() != null) {
                        skuIdList.add(softResponseListBean.getFurnitureDefault().getSkuId());
                    }
                    if (softResponseListBean.getFurnitureSelected() != null) {
                        skuIdList.add(softResponseListBean.getFurnitureSelected().getSkuId());
                    }
                    if (softResponseListBean.getBomGroupSelected() != null) {
                        groupIdList.add(softResponseListBean.getBomGroupSelected().getGroupId());
                    }
                    if (softResponseListBean.getBomGroupDefault() != null) {
                        groupIdList.add(softResponseListBean.getBomGroupDefault().getGroupId());
                    }
                    if (softResponseListBean.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(softResponseListBean.getCabinetBomGroup().getReplaceBomList())) {
                        softResponseListBean.getCabinetBomGroup().getReplaceBomList().forEach(replaceBomDto -> {
                            if (replaceBomDto.getBomGroupSelect() != null) {
                                groupIdList.add(replaceBomDto.getBomGroupSelect().getGroupId());
                            }
                            if (replaceBomDto.getBomGroupDefault() != null) {
                                groupIdList.add(replaceBomDto.getBomGroupDefault().getGroupId());
                            }
                        });
                    }
                });
            }
            if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getHardItemList())) {
                spaceDesignSelectedBean.getHardItemList().forEach(hardItemListBean -> {
                    if (hardItemListBean.getHardItemDefault() != null) {
                        skuIdList.add(hardItemListBean.getHardItemDefault().getHardSelectionId());
                    }
                    if (hardItemListBean.getHardItemSelected() != null) {
                        skuIdList.add(hardItemListBean.getHardItemSelected().getHardSelectionId());
                    }
                    if (hardItemListBean.getHardBomGroupSelect() != null) {
                        groupIdList.add(hardItemListBean.getHardBomGroupSelect().getGroupId());
                    }
                    if (hardItemListBean.getHardBomGroupDefault() != null) {
                        groupIdList.add(hardItemListBean.getHardBomGroupDefault().getGroupId());
                    }
                    if (hardItemListBean.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(hardItemListBean.getCabinetBomGroup().getReplaceBomList())) {
                        hardItemListBean.getCabinetBomGroup().getReplaceBomList().forEach(replaceBomDto -> {
                            if (replaceBomDto.getBomGroupSelect() != null) {
                                groupIdList.add(replaceBomDto.getBomGroupSelect().getGroupId());
                            }
                            if (replaceBomDto.getBomGroupDefault() != null) {
                                groupIdList.add(replaceBomDto.getBomGroupDefault().getGroupId());
                            }
                        });
                    }
                });
            }
        });
        if (CollectionUtils.isNotEmpty(groupIdList) || CollectionUtils.isNotEmpty(skuIdList)) {
            List<SkuBaseInfoDto> skuBaseInfoDtos = productProxy.batchQuerySkuBaseInfo(Lists.newArrayList(skuIdList));
            List<BomGroupVO> bomGroupVOS = curtainProxy.queryGroupListDetailByGroupListForCabinet(Lists.newArrayList(groupIdList));
            Map<Integer, SkuBaseInfoDto> skuBaseInfoMap = Maps.newHashMap();
            Map<Integer, BomGroupVO> bomGroupMap = Maps.newHashMap();
            if (CollectionUtils.isNotEmpty(skuBaseInfoDtos)) {
                Map<Integer, SkuBaseInfoDto> skuBaseInfoMapTemp = skuBaseInfoDtos.stream().collect(Collectors.toMap(skuBaseInfoDto -> skuBaseInfoDto.getSkuId(), skuBaseInfoDto -> skuBaseInfoDto));
                skuBaseInfoMap.putAll(skuBaseInfoMapTemp);
            }
            if (CollectionUtils.isNotEmpty(bomGroupVOS)) {
                Map<Integer, BomGroupVO> bomGroupMapTemp = bomGroupVOS.stream().collect(Collectors.toMap(bomGroup -> bomGroup.getGroupId(), bomGroup -> bomGroup));
                bomGroupMap.putAll(bomGroupMapTemp);
            }
            if (skuBaseInfoMap != null || bomGroupMap != null) {
                draftInfoResponse.getDraftContent().getSpaceDesignSelected().stream().forEach(spaceDesignSelectedBean -> {
                    if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getHardItemList())) {
                        spaceDesignSelectedBean.getHardItemList().forEach(hardItemListBean -> {
                            if (hardItemListBean.getHardItemSelected() != null || hardItemListBean.getHardItemDefault() != null) {
                                this.packSkuItemImage(skuBaseInfoMap, null, hardItemListBean);
                            }
                            if (hardItemListBean.getHardBomGroupSelect() != null || hardItemListBean.getHardBomGroupDefault() != null) {
                                this.packBomGroupImage(bomGroupMap, null, hardItemListBean);
                            }
                            if (hardItemListBean.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(hardItemListBean.getCabinetBomGroup().getReplaceBomList())) {
                                this.packGuiBomGroupImage(bomGroupMap, null, hardItemListBean);
                            }
                        });
                    }

                    if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getSoftResponseList())) {
                        spaceDesignSelectedBean.getSoftResponseList().forEach(softResponseListBean -> {
                            if (softResponseListBean.getFurnitureSelected() != null || softResponseListBean.getFurnitureDefault() != null) {
                                this.packSkuItemImage(skuBaseInfoMap, softResponseListBean, null);
                            }
                            if (softResponseListBean.getBomGroupSelected() != null || softResponseListBean.getBomGroupDefault() != null) {
                                this.packBomGroupImage(bomGroupMap, softResponseListBean, null);
                            }
                            if (softResponseListBean.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(softResponseListBean.getCabinetBomGroup().getReplaceBomList())) {
                                this.packGuiBomGroupImage(bomGroupMap, softResponseListBean, null);
                            }
                        });
                    }
                });
            }
        }
        if (CollectionUtils.isNotEmpty(spaceDesignVoList)) {
            draftInfoResponse.getDraftContent().getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
                spaceDesignVoList.forEach(spaceDesignVo -> {
                    if (spaceDesignSelectedBean.getSpaceDesignId().equals(spaceDesignVo.getSpaceDesignId())) {
                        if (CollectionUtils.isNotEmpty(spaceDesignVo.getHardItemList()) && CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getHardItemList())) {
                            spaceDesignVo.getHardItemList().forEach(roomHardItemClass -> {
                                spaceDesignSelectedBean.getHardItemList().forEach(hardItemListBean -> {
                                    if (roomHardItemClass.getSuperKey().equals(hardItemListBean.getSuperKey())) {
                                        hardItemListBean.setIsStandardItem(roomHardItemClass.getIsStandardItem());
                                    }
                                });
                            });
                        }
                    }
                });

            });

        }
    }

    void packSkuItemImage
            (Map<Integer, SkuBaseInfoDto> skuBaseInfoMap, DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean
                    softResponseListBean, DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean
                     hardItemListBean) {
        if (softResponseListBean != null) {
            if (softResponseListBean.getFurnitureDefault() != null) {
                SkuBaseInfoDto skuBaseInfoDto = skuBaseInfoMap.get(softResponseListBean.getFurnitureDefault().getSkuId());
                if (skuBaseInfoDto != null) {
                    softResponseListBean.getFurnitureDefault().setImgUrl(AliImageUtil.imageCompress(skuBaseInfoDto.getImage(), 1, 750, ImageConstant.SIZE_SMALL));
                    softResponseListBean.getFurnitureDefault().setSmallImage(AliImageUtil.imageCompress(skuBaseInfoDto.getImage(), 1, 750, ImageConstant.SIZE_MIDDLE));
                }
            }
            if (softResponseListBean.getFurnitureSelected() != null) {
                SkuBaseInfoDto skuBaseInfoDto = skuBaseInfoMap.get(softResponseListBean.getFurnitureSelected().getSkuId());
                if (skuBaseInfoDto != null) {
                    softResponseListBean.getFurnitureSelected().setImgUrl(AliImageUtil.imageCompress(skuBaseInfoDto.getImage(), 1, 750, ImageConstant.SIZE_MIDDLE));
                    softResponseListBean.getFurnitureSelected().setSmallImage(AliImageUtil.imageCompress(skuBaseInfoDto.getImage(), 1, 750, ImageConstant.SIZE_SMALL));
                }
            }
        } else if (hardItemListBean != null) {
            if (hardItemListBean.getHardItemSelected() != null) {
                SkuBaseInfoDto skuBaseInfoDto = skuBaseInfoMap.get(hardItemListBean.getHardItemSelected().getHardSelectionId());
                if (skuBaseInfoDto != null) {
                    hardItemListBean.getHardItemSelected().setSmallImage(AliImageUtil.imageCompress(skuBaseInfoDto.getImage(), 1, 750, ImageConstant.SIZE_SMALL));
                    hardItemListBean.getHardItemSelected().setHeadImage(AliImageUtil.imageCompress(skuBaseInfoDto.getImage(), 1, 750, ImageConstant.SIZE_MIDDLE));
                }
            }
            if (hardItemListBean.getHardItemDefault() != null) {
                SkuBaseInfoDto skuBaseInfoDto = skuBaseInfoMap.get(hardItemListBean.getHardItemDefault().getHardSelectionId());
                if (skuBaseInfoDto != null) {
                    hardItemListBean.getHardItemDefault().setSmallImage(AliImageUtil.imageCompress(skuBaseInfoDto.getImage(), 1, 750, ImageConstant.SIZE_SMALL));
                    hardItemListBean.getHardItemDefault().setHeadImage(AliImageUtil.imageCompress(skuBaseInfoDto.getImage(), 1, 750, ImageConstant.SIZE_MIDDLE));
                }
            }
        }
    }

    void packBomGroupImage
            (Map<Integer, BomGroupVO> bomGroupVOMap, DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean
                    softResponseListBean, DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean
                     hardItemListBean) {
        if (softResponseListBean != null) {
            if (softResponseListBean.getBomGroupDefault() != null) {
                BomGroupVO bomGroupVO = bomGroupVOMap.get(softResponseListBean.getBomGroupDefault().getGroupId());
                if (bomGroupVO != null) {
                    softResponseListBean.getBomGroupDefault().setGroupImage(AliImageUtil.imageCompress(bomGroupVO.getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                }
            } else if (softResponseListBean.getBomGroupSelected() != null) {
                BomGroupVO bomGroupVO = bomGroupVOMap.get(softResponseListBean.getBomGroupSelected().getGroupId());
                if (bomGroupVO != null) {
                    softResponseListBean.getBomGroupSelected().setGroupImage(AliImageUtil.imageCompress(bomGroupVO.getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                }
            }
        } else if (hardItemListBean != null) {
            if (hardItemListBean.getHardBomGroupDefault() != null) {
                BomGroupVO bomGroupVO = bomGroupVOMap.get(hardItemListBean.getHardBomGroupDefault().getGroupId());
                if (bomGroupVO != null) {
                    hardItemListBean.getHardBomGroupDefault().setGroupImage(AliImageUtil.imageCompress(bomGroupVO.getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                }
            } else if (hardItemListBean.getHardBomGroupSelect() != null) {
                BomGroupVO bomGroupVO = bomGroupVOMap.get(hardItemListBean.getHardBomGroupSelect().getGroupId());
                if (bomGroupVO != null) {
                    hardItemListBean.getHardBomGroupSelect().setGroupImage(AliImageUtil.imageCompress(bomGroupVO.getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                }
            }
        }
    }

    void packGuiBomGroupImage
            (Map<Integer, BomGroupVO> bomGroupVOMap, DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean
                    softResponseListBean, DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean
                     hardItemListBean) {
        if (softResponseListBean != null) {
            softResponseListBean.getCabinetBomGroup().getReplaceBomList().forEach(replaceBomDto -> {
                if (replaceBomDto.getBomGroupDefault() != null) {
                    BomGroupVO bomGroupVO = bomGroupVOMap.get(replaceBomDto.getBomGroupDefault().getGroupId());
                    if (bomGroupVO != null) {
                        replaceBomDto.getBomGroupDefault().setGroupImage(AliImageUtil.imageCompress(bomGroupVO.getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                    }
                }
                if (replaceBomDto.getBomGroupSelect() != null) {
                    BomGroupVO bomGroupVO = bomGroupVOMap.get(replaceBomDto.getBomGroupSelect().getGroupId());
                    if (bomGroupVO != null) {
                        replaceBomDto.getBomGroupSelect().setGroupImage(AliImageUtil.imageCompress(bomGroupVO.getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                    }
                }
            });
        } else if (hardItemListBean != null) {
            hardItemListBean.getCabinetBomGroup().getReplaceBomList().forEach(replaceBomDto -> {
                if (replaceBomDto.getBomGroupDefault() != null) {
                    BomGroupVO bomGroupVO = bomGroupVOMap.get(replaceBomDto.getBomGroupDefault().getGroupId());
                    if (bomGroupVO != null) {
                        replaceBomDto.getBomGroupDefault().setGroupImage(AliImageUtil.imageCompress(bomGroupVO.getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                    }
                }
                if (replaceBomDto.getBomGroupSelect() != null) {
                    BomGroupVO bomGroupVO = bomGroupVOMap.get(replaceBomDto.getBomGroupSelect().getGroupId());
                    if (bomGroupVO != null) {
                        replaceBomDto.getBomGroupSelect().setGroupImage(AliImageUtil.imageCompress(bomGroupVO.getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                    }
                }
            });
        }
    }

    /**
     * 设置方案服务费
     */
    private void setSolutionService(DraftInfoResponse.DraftJsonStrBean draftContent) {
        ServiceItemResponse serviceItemResponse = productProgramProxy.querySolutionService(draftContent.getSolutionSelected().getSolutionId().longValue());
        if (serviceItemResponse != null) {
            draftContent.getSolutionSelected().setSolutionGraphicDesignUrl(serviceItemResponse.getSolutionGraphicDesignUrl());
            draftContent.getSolutionSelected().setApartmentUrl(serviceItemResponse.getApartmentUrl());
            draftContent.getSolutionSelected().setReformApartmentUrl(serviceItemResponse.getReformApartmentUrl());
            draftContent.getSolutionSelected().setServiceItemList(serviceItemResponse.getServiceItemList());
        }
    }

    /**
     * 定制柜bom价格校验
     *
     * @param draftContent
     */
    private void setCabinetBomPriceCheck(DraftInfoResponse.DraftJsonStrBean draftContent) {
        try {
            //开始比价
            List<QueryGroupReplaceDetailSimpleRequest.QueryInfo> queryGroupReplaceDetailList = new ArrayList<>();
            List<Integer> guiBomFirstGroupIdList = Lists.newArrayList();
            draftContent.getSpaceDesignSelected().stream().forEach(spaceDesignSelectedBean -> {
                if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getHardItemList())) {
                    spaceDesignSelectedBean.getHardItemList().forEach(hardItemListBean -> {
                        if (hardItemListBean.getCabinetBomGroup() != null) {
                            List<ReplaceBomDto> replaceBomList = hardItemListBean.getCabinetBomGroup().getReplaceBomList();
                            if (CollectionUtils.isNotEmpty(replaceBomList)) {
                                guiBomFirstGroupIdList.add(replaceBomList.get(0).getBomGroupSelect().getGroupId());
                                for (ReplaceBomDto replaceBomDto : replaceBomList) {
                                    queryGroupReplaceDetailList.add(new QueryGroupReplaceDetailSimpleRequest.QueryInfo()
                                            .setDefaultGroupId(replaceBomDto.getBomGroupDefault() == null ? null : replaceBomDto.getBomGroupDefault().getGroupId())
                                            .setReplaceGroupId(replaceBomDto.getBomGroupSelect().getGroupId())
                                            .setDefaultGroupNum(replaceBomDto.getBomGroupDefault() == null ? replaceBomDto.getBomGroupSelect().getItemCount() : replaceBomDto.getBomGroupDefault().getItemCount()));
                                }
                            }
                        }
                    });
                }
                if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getSoftResponseList())) {
                    spaceDesignSelectedBean.getSoftResponseList().forEach(softResponseListBean -> {
                        if (softResponseListBean.getCabinetBomGroup() != null) {
                            List<ReplaceBomDto> replaceBomList = softResponseListBean.getCabinetBomGroup().getReplaceBomList();
                            if (CollectionUtils.isNotEmpty(replaceBomList)) {
                                guiBomFirstGroupIdList.add(replaceBomList.get(0).getBomGroupSelect().getGroupId());
                                for (ReplaceBomDto replaceBomDto : replaceBomList) {
                                    queryGroupReplaceDetailList.add(new QueryGroupReplaceDetailSimpleRequest.QueryInfo()
                                            .setDefaultGroupId(replaceBomDto.getBomGroupDefault() == null ? null : replaceBomDto.getBomGroupDefault().getGroupId())
                                            .setReplaceGroupId(replaceBomDto.getBomGroupSelect().getGroupId())
                                            .setDefaultGroupNum(replaceBomDto.getBomGroupDefault() == null ? replaceBomDto.getBomGroupSelect().getItemCount() : replaceBomDto.getBomGroupSelect().getItemCount()));
                                }
                            }
                        }
                    });
                }
            });

            List<QueryGroupReplaceDetailSimpleVO> queryGroupReplaceDetailSimpleVOS = curtainProxy.queryGroupReplaceDetailSimple(queryGroupReplaceDetailList);

            //比完价格塞回去
            draftContent.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
                //硬装
                if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getHardItemList())) {
                    spaceDesignSelectedBean.getHardItemList().forEach(hardItemListBean -> {
                        if (hardItemListBean.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(hardItemListBean.getCabinetBomGroup().getReplaceBomList())) {
                            for (ReplaceBomDto replaceBomDto : hardItemListBean.getCabinetBomGroup().getReplaceBomList()) {
                                if (replaceBomDto.getBomGroupDefault() == null && replaceBomDto.getBomGroupSelect() != null) {
                                    //新增项
                                    if (CollectionUtils.isNotEmpty(queryGroupReplaceDetailSimpleVOS)) {
                                        queryGroupReplaceDetailSimpleVOS.forEach(queryGroupReplaceDetailSimpleVO -> {
                                            if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getGroupId().equals(replaceBomDto.getBomGroupSelect().getGroupId()) &&
                                                    queryGroupReplaceDetailSimpleVO.getDefaultGroup().getGroupId() == null) {//增项
                                                if (replaceBomDto.getBomGroupSelect().getPriceDiff() != null) {
                                                    draftContent.addBomTotalDiffPrice(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences());
                                                    replaceBomDto.getBomGroupSelect().setGroupImage(AliImageUtil.imageCompress(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                                                    if (replaceBomDto.getBomGroupSelect().getPriceDiff().compareTo(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences()) != 0) {
                                                        setHardCabinetBomCompareInfo(replaceBomDto, queryGroupReplaceDetailSimpleVO, spaceDesignSelectedBean);
                                                    }
                                                }
                                            }
                                        });
                                    }
                                } else if (replaceBomDto.getBomGroupSelect() != null) {
                                    //替换项
                                    queryGroupReplaceDetailSimpleVOS.forEach(queryGroupReplaceDetailSimpleVO -> {
                                        if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getGroupId().equals(replaceBomDto.getBomGroupSelect().getGroupId()) &&
                                                queryGroupReplaceDetailSimpleVO.getDefaultGroup().getGroupId() != null && queryGroupReplaceDetailSimpleVO.getDefaultGroup().getGroupId().equals(replaceBomDto.getBomGroupDefault().getGroupId())) {//替换项
                                            if (replaceBomDto.getBomGroupSelect().getPriceDiff() != null) {
                                                draftContent.addBomTotalDiffPrice(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences());
                                                replaceBomDto.getBomGroupSelect().setGroupImage(AliImageUtil.imageCompress(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                                                replaceBomDto.getBomGroupDefault().setGroupImage(AliImageUtil.imageCompress(queryGroupReplaceDetailSimpleVO.getDefaultGroup().getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                                                if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences().subtract(replaceBomDto.getBomGroupSelect().getPriceDiff()).compareTo(new BigDecimal(0)) != 0) {
                                                    setHardCabinetBomCompareInfo(replaceBomDto, queryGroupReplaceDetailSimpleVO, spaceDesignSelectedBean);
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                            //汇总价格
                            this.collectCabinetBomPrice(hardItemListBean.getCabinetBomGroup());
                        }
                    });
                }
                //软装
                if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getSoftResponseList())) {
                    spaceDesignSelectedBean.getSoftResponseList().forEach(softResponseListBean -> {
                        if (softResponseListBean.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(softResponseListBean.getCabinetBomGroup().getReplaceBomList())) {
                            for (ReplaceBomDto replaceBomDto : softResponseListBean.getCabinetBomGroup().getReplaceBomList()) {
                                if (replaceBomDto.getBomGroupDefault() == null && replaceBomDto.getBomGroupSelect() != null) {
                                    //新增项
                                    if (CollectionUtils.isNotEmpty(queryGroupReplaceDetailSimpleVOS)) {
                                        queryGroupReplaceDetailSimpleVOS.forEach(queryGroupReplaceDetailSimpleVO -> {
                                            if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getGroupId().equals(replaceBomDto.getBomGroupSelect().getGroupId()) &&
                                                    queryGroupReplaceDetailSimpleVO.getDefaultGroup().getGroupId() == null) {//增项
                                                if (replaceBomDto.getBomGroupSelect().getPriceDiff() != null) {
                                                    draftContent.addBomTotalDiffPrice(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences());
                                                    replaceBomDto.getBomGroupSelect().setGroupImage(AliImageUtil.imageCompress(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                                                    if (replaceBomDto.getBomGroupSelect().getPriceDiff().compareTo(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences()) != 0) {
                                                        setHardCabinetBomCompareInfo(replaceBomDto, queryGroupReplaceDetailSimpleVO, spaceDesignSelectedBean);
                                                    }
                                                }
                                            }
                                        });
                                    }
                                } else if (replaceBomDto.getBomGroupSelect() != null) {
                                    //替换项
                                    queryGroupReplaceDetailSimpleVOS.forEach(queryGroupReplaceDetailSimpleVO -> {
                                        if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getGroupId().equals(replaceBomDto.getBomGroupSelect().getGroupId()) &&
                                                queryGroupReplaceDetailSimpleVO.getDefaultGroup().getGroupId() != null && queryGroupReplaceDetailSimpleVO.getDefaultGroup().getGroupId().equals(replaceBomDto.getBomGroupDefault().getGroupId())) {//替换项
                                            if (replaceBomDto.getBomGroupSelect().getPriceDiff() != null) {
                                                draftContent.addBomTotalDiffPrice(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences());
                                                replaceBomDto.getBomGroupSelect().setGroupImage(AliImageUtil.imageCompress(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                                                replaceBomDto.getBomGroupDefault().setGroupImage(AliImageUtil.imageCompress(queryGroupReplaceDetailSimpleVO.getDefaultGroup().getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                                                if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences().subtract(replaceBomDto.getBomGroupSelect().getPriceDiff()).compareTo(new BigDecimal(0)) != 0) {
                                                    setHardCabinetBomCompareInfo(replaceBomDto, queryGroupReplaceDetailSimpleVO, spaceDesignSelectedBean);
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                            this.collectCabinetBomPrice(softResponseListBean.getCabinetBomGroup());
                        }
                    });

                }
            });
            if (CollectionUtils.isNotEmpty(guiBomFirstGroupIdList)) {
                Map<Integer, Map<String, String>> colourAndTextureByGroupIdMap = productProgramService.getColourAndTextureByGroupIdList(guiBomFirstGroupIdList);
                if (MapUtils.isNotEmpty(colourAndTextureByGroupIdMap)) {
                    for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean spaceDesignSelectedBean : draftContent.getSpaceDesignSelected()) {
                        if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getSoftResponseList())) {
                            for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean softResponseListBean : spaceDesignSelectedBean.getSoftResponseList()) {
                                if (softResponseListBean.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(softResponseListBean.getCabinetBomGroup().getReplaceBomList())) {
                                    Map<String, String> map = colourAndTextureByGroupIdMap.get(softResponseListBean.getCabinetBomGroup().getReplaceBomList().get(0).getBomGroupSelect().getGroupId());
                                    if (MapUtils.isNotEmpty(map)) {
                                        softResponseListBean.getCabinetBomGroup().setTexture(map.get("texture"));
                                        softResponseListBean.getCabinetBomGroup().setColour(map.get("colour"));
                                    }
                                }
                            }
                        }
                        if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getHardItemList())) {
                            for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean hardItemListBean : spaceDesignSelectedBean.getHardItemList()) {
                                if (hardItemListBean.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(hardItemListBean.getCabinetBomGroup().getReplaceBomList())) {
                                    Map<String, String> map = colourAndTextureByGroupIdMap.get(hardItemListBean.getCabinetBomGroup().getReplaceBomList().get(0).getBomGroupSelect().getGroupId());
                                    if (MapUtils.isNotEmpty(map)) {
                                        hardItemListBean.getCabinetBomGroup().setTexture(map.get("texture"));
                                        hardItemListBean.getCabinetBomGroup().setColour(map.get("colour"));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (
                Exception e) {
            LOG.error("check hard bom price fail o2o-exception , more info :", e);
        }

    }

    void collectCabinetBomPrice(CabinetBomDto cabinetBomGroup) {
        cabinetBomGroup.setPrice(new BigDecimal(0));
        cabinetBomGroup.setPriceDiff(new BigDecimal(0));//重新计算总价
        for (ReplaceBomDto replaceBomDto : cabinetBomGroup.getReplaceBomList()) {
            if (replaceBomDto.getBomGroupSelect().getCompareStatus().equals(2)) {
                cabinetBomGroup.setCompareStatus(Constants.COMPARE_STATUS_PRICE_CHANGE);//变价
                cabinetBomGroup.setChangePrice(cabinetBomGroup.getChangePrice().add(replaceBomDto.getBomGroupSelect().getChangePrice()));//变价金额
                cabinetBomGroup.setMessage(cabinetBomGroup.getChangePrice());//变价金额
            }
            cabinetBomGroup.setPriceDiff(cabinetBomGroup.getPriceDiff().add(replaceBomDto.getBomGroupSelect().getPriceDiff()));
            cabinetBomGroup.setPrice(cabinetBomGroup.getPrice().add(replaceBomDto.getBomGroupSelect().getPrice()));
        }
    }


    /**
     * 方案及空间信息
     */
    private void setSolutionInfo(DraftInfoResponse.DraftJsonStrBean draftJsonStr, Integer orderId) {
        try {
            SolutionListRequest req = new SolutionListRequest();
            req.setHouseTypeId(draftJsonStr.getHouseTypeId() == null ? 1495 : draftJsonStr.getHouseTypeId() == 0 ? 1495 : draftJsonStr.getHouseTypeId());//老草稿中无户型id，放入一个默认户型id
            req.setOrderId(orderId);
            req.setSolutionId(draftJsonStr.getSolutionSelected().getSolutionId().longValue());
            SolutionEffectResponse solutionEffectResponse = querySolutionInfoList(req);//查询方案及空间信息
            solutionEffectResponse.getSolutionEffectInfoList().forEach(solutionEffectInfo -> {
                if (solutionEffectInfo.getSolutionId().equals(draftJsonStr.getSolutionSelected().getSolutionId())) {
                    draftJsonStr.getSolutionSelected().setSolutionStyle(solutionEffectInfo.getSolutionStyle());
                    draftJsonStr.getSolutionSelected().setSolutionName(solutionEffectInfo.getSolutionName());
                    draftJsonStr.getSolutionSelected().setSolutionGraphicDesignUrl(AliImageUtil.imageCompress(solutionEffectInfo.getSolutionGraphicDesignUrl(), 2, 750, ImageConstant.SIZE_MIDDLE));
                }
            });
            // 自由组合空间信息
            solutionEffectResponse.getSpaceMarkList().forEach(spaceMark -> draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
                //todo 用roomId代替原 SpaceMarkId
                if (spaceMark.getRoomId().equals(spaceDesignSelectedBean.getRoomId())) {
                    spaceMark.getSpaceDesignList().forEach(spaceDesign -> {
                        if (spaceDesign.getSpaceDesignId().equals(spaceDesignSelectedBean.getSelected().getSpaceDesignId())) {
                            spaceDesignSelectedBean.getSelected().setSolutionName(spaceDesign.getSolutionName());
                            spaceDesignSelectedBean.getSelected().setSpaceStyle(spaceDesign.getSpaceStyle());
                            if (CollectionUtils.isNotEmpty(spaceDesign.getRoomPictureDtoList()) &&
                                    spaceDesignSelectedBean.getSelected().getRoomImage() != null &&
                                    spaceDesignSelectedBean.getSelected().getRoomImage().getOldPictureList() != null) {
                                spaceDesignSelectedBean.getSelected().getRoomImage().setOldPictureList(spaceDesign.getRoomPictureDtoList().stream().map(roomPictureDto -> AliImageUtil.imageCompress(roomPictureDto.getPictureUrl(), 2, 750, ImageConstant.SIZE_MIDDLE)).collect(Collectors.toList()));
                            }
                        }
                    });
                }
            }));
        } catch (Exception e) {
            LOG.error("set solution info fail o2o-exception , more info :", e);
        }
    }

    /**
     * bom价格校验
     */
    private void setBomPriceCheck(DraftInfoResponse.DraftJsonStrBean draftJsonStr) {
        try {
            //开始比价
            List<QueryGroupReplaceDetailSimpleRequest.QueryInfo> queryGroupReplaceDetailList = getQueryGroupReplaceDetailList(draftJsonStr);
            List<QueryGroupReplaceDetailSimpleVO> queryGroupReplaceDetailSimpleVOS = curtainProxy.queryGroupReplaceDetailSimple(queryGroupReplaceDetailList);

            //比完价格塞回去
            draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
                setSoftBomSpaceInfo(spaceDesignSelectedBean, queryGroupReplaceDetailSimpleVOS, draftJsonStr);
                setHardBomSpaceInfo(spaceDesignSelectedBean, queryGroupReplaceDetailSimpleVOS, draftJsonStr);
            });
        } catch (Exception e) {
            LOG.error("check bom price fail o2o-exception , more info :", e);
        }
    }

    /**
     * 设置软装bom空间信息
     *
     * @param spaceDesignSelectedBean
     * @param queryGroupReplaceDetailSimpleVOS
     * @param draftJsonStr
     */
    private void setSoftBomSpaceInfo(DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean
                                             spaceDesignSelectedBean, List<QueryGroupReplaceDetailSimpleVO> queryGroupReplaceDetailSimpleVOS, DraftInfoResponse.DraftJsonStrBean
                                             draftJsonStr) {
        if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getSoftResponseList())) {
            spaceDesignSelectedBean.getSoftResponseList().forEach(softResponseListBean -> {
                if (softResponseListBean.getBomGroupSelected() != null) {
                    if (CollectionUtils.isNotEmpty(queryGroupReplaceDetailSimpleVOS)) {
                        queryGroupReplaceDetailSimpleVOS.forEach(queryGroupReplaceDetailSimpleVO -> {
                            if (softResponseListBean.getBomGroupSelected() != null && softResponseListBean.getBomGroupDefault() != null
                                    && ((spaceDesignSelectedBean.getRoomId() != null && spaceDesignSelectedBean.getRoomId().equals(queryGroupReplaceDetailSimpleVO.getRoomId()))
                                    || (spaceDesignSelectedBean.getSpaceDesignId() != null && spaceDesignSelectedBean.getSpaceDesignId().equals(queryGroupReplaceDetailSimpleVO.getRoomId()))) &&
                                    queryGroupReplaceDetailSimpleVO.getReplaceGroup().getGroupId().equals(softResponseListBean.getBomGroupSelected().getGroupId()) &&
                                    queryGroupReplaceDetailSimpleVO.getDefaultGroup().getGroupId().equals(softResponseListBean.getBomGroupDefault().getGroupId())) {
                                if (softResponseListBean.getBomGroupSelected().getPriceDiff() != null) {

                                    draftJsonStr.addBomTotalDiffPrice(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences());

                                    if (softResponseListBean.getBomGroupSelected().getPriceDiff().compareTo(BigDecimal.ZERO) > 0) {//所选物料价格比标配高
                                        if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences().subtract
                                                (softResponseListBean.getBomGroupSelected().getPriceDiff()).compareTo(BigDecimal.ZERO) > 0) {//物料价格升高
                                            setBomCompareInfo(softResponseListBean, queryGroupReplaceDetailSimpleVO, spaceDesignSelectedBean);

                                        } else if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences().subtract
                                                (softResponseListBean.getBomGroupSelected().getPriceDiff()).compareTo(BigDecimal.ZERO) < 0) {//物料价格降低
                                            if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences().compareTo(BigDecimal.ZERO) > 0) {
                                                setBomCompareInfo(softResponseListBean, queryGroupReplaceDetailSimpleVO, spaceDesignSelectedBean);
                                            } else {
                                                softResponseListBean.getBomGroupSelected().setSkuCompareStatus(Constants.COMPARE_STATUS_PRICE_CHANGE).setMessage(new BigDecimal(0)
                                                        .subtract(softResponseListBean.getBomGroupSelected().getPriceDiff()).toString());
                                                spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_PRICE_CHANGE);
                                                softResponseListBean.getBomGroupSelected().setPriceDiff(new BigDecimal(0));
                                            }
                                        }

                                    } else if (softResponseListBean.getBomGroupSelected().getPriceDiff().compareTo(BigDecimal.ZERO) < 0) {//所选物料价格比标配低
                                        if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences().compareTo(BigDecimal.ZERO) > 0) {//变动后价格比标配高
                                            setBomCompareInfo(softResponseListBean, queryGroupReplaceDetailSimpleVO, spaceDesignSelectedBean);
                                        }

                                    } else if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences().subtract
                                            (softResponseListBean.getBomGroupSelected().getPriceDiff()).compareTo(BigDecimal.ZERO) > 0) {
                                        setBomCompareInfo(softResponseListBean, queryGroupReplaceDetailSimpleVO, spaceDesignSelectedBean);
                                    }
                                }
                            }
                        });
                    }
                }

            });
        }
    }

    /**
     * 设置硬装bom空间信息
     *
     * @param spaceDesignSelectedBean
     * @param queryGroupReplaceDetailSimpleVOS
     * @param draftJsonStr
     */
    private void setHardBomSpaceInfo(DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean
                                             spaceDesignSelectedBean, List<QueryGroupReplaceDetailSimpleVO> queryGroupReplaceDetailSimpleVOS, DraftInfoResponse.DraftJsonStrBean
                                             draftJsonStr) {
        if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getHardItemList())) {
            spaceDesignSelectedBean.getHardItemList().forEach(hardItemListBean -> {
                if (hardItemListBean.getHardBomGroupDefault() == null && hardItemListBean.getHardBomGroupSelect() != null) {

                    if (CollectionUtils.isNotEmpty(queryGroupReplaceDetailSimpleVOS)) {
                        queryGroupReplaceDetailSimpleVOS.forEach(queryGroupReplaceDetailSimpleVO -> {
                            if (((spaceDesignSelectedBean.getRoomId() != null && spaceDesignSelectedBean.getRoomId().equals(queryGroupReplaceDetailSimpleVO.getRoomId()))
                                    || (spaceDesignSelectedBean.getSpaceDesignId() != null && spaceDesignSelectedBean.getSpaceDesignId().equals(queryGroupReplaceDetailSimpleVO.getRoomId())))
                                    && queryGroupReplaceDetailSimpleVO.getReplaceGroup().getGroupId().equals(hardItemListBean.getHardBomGroupSelect().getGroupId()) &&
                                    queryGroupReplaceDetailSimpleVO.getDefaultGroup().getGroupId() == null) {//增项
                                if (hardItemListBean.getHardBomGroupSelect().getPriceDiff() != null) {
                                    draftJsonStr.addBomTotalDiffPrice(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences());
                                    if (hardItemListBean.getHardBomGroupSelect().getPriceDiff().compareTo(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences()) != 0) {
                                        setHardBomCompareInfo(hardItemListBean, queryGroupReplaceDetailSimpleVO, spaceDesignSelectedBean);
                                    }
                                }
                            }
                        });
                    }

                } else if (hardItemListBean.getHardBomGroupSelect() != null) {
                    if (CollectionUtils.isNotEmpty(queryGroupReplaceDetailSimpleVOS)) {
                        queryGroupReplaceDetailSimpleVOS.forEach(queryGroupReplaceDetailSimpleVO -> {
                            if (queryGroupReplaceDetailSimpleVO.getDefaultGroup().getGroupId() != null && queryGroupReplaceDetailSimpleVO.getReplaceGroup().getGroupId() != null
                                    && ((spaceDesignSelectedBean.getRoomId() != null && spaceDesignSelectedBean.getRoomId().equals(queryGroupReplaceDetailSimpleVO.getRoomId()))
                                    || (spaceDesignSelectedBean.getSpaceDesignId() != null && spaceDesignSelectedBean.getSpaceDesignId().equals(queryGroupReplaceDetailSimpleVO.getRoomId()))) &&
                                    queryGroupReplaceDetailSimpleVO.getReplaceGroup().getGroupId().equals(hardItemListBean.getHardBomGroupSelect().getGroupId()) &&
                                    queryGroupReplaceDetailSimpleVO.getDefaultGroup().getGroupId().equals(hardItemListBean.getHardBomGroupDefault().getGroupId())
                            ) {//替换项
                                if (hardItemListBean.getHardBomGroupSelect().getPriceDiff() != null) {
                                    draftJsonStr.addBomTotalDiffPrice(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences());

                                    if (hardItemListBean.getHardBomGroupSelect().getPriceDiff().compareTo(BigDecimal.ZERO) > 0) {//所选物料价格比标配高
                                        if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences().subtract
                                                (hardItemListBean.getHardBomGroupSelect().getPriceDiff()).compareTo(BigDecimal.ZERO) > 0) {//物料价格升高
                                            setHardBomCompareInfo(hardItemListBean, queryGroupReplaceDetailSimpleVO, spaceDesignSelectedBean);

                                        } else if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences().subtract
                                                (hardItemListBean.getHardBomGroupSelect().getPriceDiff()).compareTo(BigDecimal.ZERO) < 0) {//物料价格降低
                                            if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences().compareTo(BigDecimal.ZERO) > 0) {
                                                setHardBomCompareInfo(hardItemListBean, queryGroupReplaceDetailSimpleVO, spaceDesignSelectedBean);
                                            } else {
                                                hardItemListBean.getHardBomGroupSelect().setSkuCompareStatus(Constants.COMPARE_STATUS_PRICE_CHANGE).setMessage(new BigDecimal(0)
                                                        .subtract(hardItemListBean.getHardBomGroupSelect().getPriceDiff()).toString());
                                                spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_PRICE_CHANGE);
                                                hardItemListBean.getHardBomGroupSelect().setPriceDiff(new BigDecimal(0));
                                            }
                                        }

                                    } else if (hardItemListBean.getHardBomGroupSelect().getPriceDiff().compareTo(BigDecimal.ZERO) < 0) {//所选物料价格比标配低
                                        if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences().compareTo(BigDecimal.ZERO) > 0) {//变动后价格比标配高
                                            setHardBomCompareInfo(hardItemListBean, queryGroupReplaceDetailSimpleVO, spaceDesignSelectedBean);
                                        }

                                    } else if (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences().subtract
                                            (hardItemListBean.getHardBomGroupSelect().getPriceDiff()).compareTo(BigDecimal.ZERO) > 0) {
                                        setHardBomCompareInfo(hardItemListBean, queryGroupReplaceDetailSimpleVO, spaceDesignSelectedBean);
                                    }
                                }
                            }
                        });
                    }
                }

            });
        }
    }

    /**
     * 获取bom查询入参
     *
     * @param draftJsonStr
     * @return
     */
    private List<QueryGroupReplaceDetailSimpleRequest.QueryInfo> getQueryGroupReplaceDetailList
    (DraftInfoResponse.DraftJsonStrBean draftJsonStr) {
        List<QueryGroupReplaceDetailSimpleRequest.QueryInfo> queryGroupReplaceDetailList = new ArrayList<>();
        draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
            ComparePriceRequest comparePriceRequest = new ComparePriceRequest();
            comparePriceRequest.setRoomId(spaceDesignSelectedBean.getSelected().getSpaceDesignId());

            if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getSoftResponseList())) {
                spaceDesignSelectedBean.getSoftResponseList().forEach(softResponseListBean -> {
                    if (softResponseListBean.getBomGroupSelected() != null && softResponseListBean.getBomGroupDefault() != null &&
                            !softResponseListBean.getBomGroupSelected().getGroupId().equals(softResponseListBean.getBomGroupDefault().getGroupId())) {//bom选配
                        queryGroupReplaceDetailList.add(new QueryGroupReplaceDetailSimpleRequest.QueryInfo()
                                .setDefaultGroupId(softResponseListBean.getBomGroupDefault() == null ? null : softResponseListBean.getBomGroupDefault().getGroupId())
                                .setReplaceGroupId(softResponseListBean.getBomGroupSelected().getGroupId())
                                .setRoomId(spaceDesignSelectedBean.getSelected().getSpaceDesignId())
                                .setDefaultGroupNum(softResponseListBean.getBomGroupDefault() == null ? softResponseListBean.getBomGroupSelected().getItemCount() : softResponseListBean.getBomGroupDefault().getItemCount()));
                    }
                });
            }
            if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getHardItemList())) {
                spaceDesignSelectedBean.getHardItemList().forEach(hardItemListBean -> {
                    if (hardItemListBean.getHardBomGroupSelect() != null) {
                        queryGroupReplaceDetailList.add(new QueryGroupReplaceDetailSimpleRequest.QueryInfo()
                                .setDefaultGroupId(hardItemListBean.getHardBomGroupDefault() == null ? null : hardItemListBean.getHardBomGroupDefault().getGroupId())
                                .setReplaceGroupId(hardItemListBean.getHardBomGroupSelect().getGroupId())
                                .setRoomId(spaceDesignSelectedBean.getSelected().getSpaceDesignId())
                                .setDefaultGroupNum(hardItemListBean.getHardBomGroupDefault() == null ? hardItemListBean.getHardBomGroupSelect().getItemCount() : hardItemListBean.getHardBomGroupDefault().getItemCount()));
                    }
                });
            }

        });
        return queryGroupReplaceDetailList;
    }


    /**
     * 设置bom对比信息
     *
     * @param softResponseListBean
     * @param queryGroupReplaceDetailSimpleVO
     * @param spaceDesignSelectedBean
     */
    private void setBomCompareInfo
    (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean
             softResponseListBean,
     QueryGroupReplaceDetailSimpleVO
             queryGroupReplaceDetailSimpleVO, DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean
             spaceDesignSelectedBean) {
        softResponseListBean.getBomGroupSelected().setSkuCompareStatus(Constants.COMPARE_STATUS_PRICE_CHANGE).setMessage
                (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences().subtract(softResponseListBean.getBomGroupSelected().getPriceDiff()).toString());
        spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_PRICE_CHANGE);
        softResponseListBean.getBomGroupSelected().setPriceDiff(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences());
    }

    /**
     * 软装bom类目信息更新
     *
     * @param draftJsonStr
     */
    private void setSoftSkuBomBaseInfo(DraftInfoResponse.DraftJsonStrBean draftJsonStr) {
        try {
            List<Integer> bomGroupIdList = new ArrayList<>();
            // 组装skuList，取软装default和selected下的skuId
            for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean spaceDesignSelected : draftJsonStr.getSpaceDesignSelected()) {
                if (CollectionUtils.isNotEmpty(spaceDesignSelected.getSoftResponseList())) {
                    for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean softResponse : spaceDesignSelected.getSoftResponseList()) {
                        Integer selectedBomId = 0;
                        if (softResponse.getBomGroupSelected() != null && softResponse.getBomGroupSelected().getGroupId() != null) {
                            if (!bomGroupIdList.contains(softResponse.getBomGroupSelected().getGroupId())) {
                                bomGroupIdList.add(softResponse.getBomGroupSelected().getGroupId());
                            }
                            selectedBomId = softResponse.getBomGroupSelected().getGroupId();
                        }
                        if (softResponse.getBomGroupDefault() != null && softResponse.getBomGroupDefault().getGroupId() != null &&
                                softResponse.getBomGroupDefault().getGroupId() != selectedBomId) {
                            if (!bomGroupIdList.contains(softResponse.getBomGroupDefault().getGroupId())) {
                                bomGroupIdList.add(softResponse.getBomGroupDefault().getGroupId());
                            }
                        }

                    }
                }
            }
            if (CollectionUtils.isEmpty(bomGroupIdList)) {
                return;
            }

            // 查询sku信息
            BomCategoryListDto bomCategoryDtoTmp = productProxy.batchQuerySkuBomBaseInfo(bomGroupIdList);
            if (bomCategoryDtoTmp == null) {
                return;
            }
            List<BomCategoryDTO> bomCategoryDTOS = bomCategoryDtoTmp.getGroupCategoryList();
            if (CollectionUtils.isEmpty(bomCategoryDTOS)) {
                return;
            }
            // 重新赋值
            for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean spaceDesignSelected : draftJsonStr.getSpaceDesignSelected()) {
                if (CollectionUtils.isEmpty(spaceDesignSelected.getSoftResponseList())) {
                    continue;
                }
                for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean softResponse : spaceDesignSelected.getSoftResponseList()) {
                    if ((softResponse.getBomGroupSelected() == null || softResponse.getBomGroupSelected().getGroupId() == null) &&
                            (softResponse.getBomGroupDefault() == null || softResponse.getBomGroupDefault().getGroupId() == null)) {
                        continue;
                    }
                    for (BomCategoryDTO bomCategoryDTO : bomCategoryDTOS) {
                        if (bomCategoryDTO.getGroupId() == null) {
                            continue;
                        }
                        if (softResponse.getBomGroupSelected() != null && softResponse.getBomGroupSelected().getGroupId() != null) {
                            if (softResponse.getBomGroupSelected().getGroupId().equals(bomCategoryDTO.getGroupId())) {
                                initNewFurnitureBomInfo(softResponse.getBomGroupSelected(), bomCategoryDTO);
                            }
                        }
                        if (softResponse.getBomGroupDefault() != null && softResponse.getBomGroupDefault().getGroupId() != null) {
                            if (softResponse.getBomGroupDefault().getGroupId().equals(bomCategoryDTO.getGroupId())) {
                                initNewFurnitureBomInfo(softResponse.getBomGroupDefault(), bomCategoryDTO);
                                softResponse.setLastCategoryId(bomCategoryDTO.getLastCategoryId());
                                softResponse.setLastCategoryName(bomCategoryDTO.getLastCategoryName());
                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            LOG.error("set soft bom lastCategory info fail o2o-exception , more info :", e);
        }
    }

    /**
     * 更新sku信息
     *
     * @param draftJsonStr
     */
    private void setSoftSkuBaseInfo(DraftInfoResponse.DraftJsonStrBean draftJsonStr) {
        try {
            List<Integer> skuList = new ArrayList<>();
            // 组装skuList，取软装default和selected下的skuId
            if (CollectionUtils.isEmpty(draftJsonStr.getSpaceDesignSelected())) {
                return;
            }
            for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean spaceDesignSelected : draftJsonStr.getSpaceDesignSelected()) {
                if (CollectionUtils.isNotEmpty(spaceDesignSelected.getSoftResponseList())) {
                    for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean softResponse : spaceDesignSelected.getSoftResponseList()) {//软装
                        Integer selectedSkuId = 0;
                        if (softResponse.getFurnitureSelected() != null && softResponse.getFurnitureSelected().getSkuId() != null) {
                            if (!skuList.contains(softResponse.getFurnitureSelected().getSkuId())) {
                                skuList.add(softResponse.getFurnitureSelected().getSkuId());
                            }
                            selectedSkuId = softResponse.getFurnitureSelected().getSkuId();
                        }
                        if (softResponse.getFurnitureDefault() != null && softResponse.getFurnitureDefault().getSkuId() != null &&
                                softResponse.getFurnitureDefault().getSkuId() != selectedSkuId) {
                            if (!skuList.contains(softResponse.getFurnitureDefault().getSkuId())) {
                                skuList.add(softResponse.getFurnitureDefault().getSkuId());
                            }
                        }
                        softResponse.setSupportDrawCategory(ProductCategoryConstant.EIGHT_BIG_CATEGORY_ID_LIST.contains(softResponse.getLastCategoryId()));
                    }
                }
                if (CollectionUtils.isNotEmpty(spaceDesignSelected.getHardItemList())) {//硬装
                    for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean hardItemListBean : spaceDesignSelected.getHardItemList()) {
                        if (hardItemListBean.getStatus() != null && (hardItemListBean.getStatus() == 0 || hardItemListBean.getStatus() == 1 || hardItemListBean.getStatus() == 3)
                                && hardItemListBean.getHardItemSelected() != null
                                && !skuList.contains(hardItemListBean.getHardItemSelected().getHardSelectionId())) {//默认、替换、新增
                            skuList.add(hardItemListBean.getHardItemSelected().getHardSelectionId());
                        }
                        if (hardItemListBean.getStatus() != null && hardItemListBean.getStatus() == 2 && hardItemListBean.getHardItemDefault() != null
                                && !skuList.contains(hardItemListBean.getHardItemDefault().getHardSelectionId())) {//删除
                            skuList.add(hardItemListBean.getHardItemDefault().getHardSelectionId());
                        }
                        List<Integer> supportDrawHardCategoryList = Lists.newArrayList(supportDrawHardCategory.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
                        hardItemListBean.setSupportDrawCategory(supportDrawHardCategoryList.contains(hardItemListBean.getHardItemId()));
                    }
                }
            }

            // 查询sku信息
            List<SkuBaseInfoDto> skuBaseInfoDtos = productProxy.batchQuerySkuBaseInfo(skuList);
            if (CollectionUtils.isEmpty(skuBaseInfoDtos)) {
                return;
            }
            // 重新赋值
            for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean spaceDesignSelected : draftJsonStr.getSpaceDesignSelected()) {
                for (SkuBaseInfoDto skuBaseInfoDto : skuBaseInfoDtos) {
                    if (skuBaseInfoDto.getSkuId() != null) {
                        if (CollectionUtils.isNotEmpty(spaceDesignSelected.getSoftResponseList())) {
                            for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean softResponse : spaceDesignSelected.getSoftResponseList()) {
                                if (softResponse.getFurnitureSelected() != null && softResponse.getFurnitureSelected().getSkuId() != null
                                        && softResponse.getFurnitureSelected().getSkuId().equals(skuBaseInfoDto.getSkuId())) {
                                    initNewFurnitureInfo(softResponse.getFurnitureSelected(), skuBaseInfoDto);
                                }
                                if (softResponse.getFurnitureDefault() != null && softResponse.getFurnitureDefault().getSkuId() != null
                                        && softResponse.getFurnitureDefault().getSkuId().equals(skuBaseInfoDto.getSkuId())) {
                                    initNewFurnitureInfo(softResponse.getFurnitureDefault(), skuBaseInfoDto);
                                    softResponse.setLastCategoryId(skuBaseInfoDto.getLastCategoryId());
                                    softResponse.setLastCategoryName(skuBaseInfoDto.getLastCategoryName());
                                    softResponse.setRootCategoryId(skuBaseInfoDto.getRootCategoryId());
                                    if (skuBaseInfoDto.getRootCategoryId() != null && skuBaseInfoDto.getRootCategoryId().equals(Constants.CUSTOMIZED_ROOT_CATEGORY_ID)) {
                                        softResponse.setRootCategoryName(Constants.CUSTOMIZED_ROOT_CATEGORY_NAME);
                                    } else {
                                        softResponse.setRootCategoryName(skuBaseInfoDto.getRootCategoryName());
                                    }
                                }

                            }
                        }
                        if (CollectionUtils.isNotEmpty(spaceDesignSelected.getHardItemList())) {
                            for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean hardItemListBean : spaceDesignSelected.getHardItemList()) {
                                if (hardItemListBean.getHardItemDefault() != null && hardItemListBean.getHardItemDefault().getHardSelectionId() != null
                                        && hardItemListBean.getHardItemDefault().getHardSelectionId().equals(skuBaseInfoDto.getSkuId())) {
                                    hardItemListBean.getHardItemDefault().setHardSelectionName(skuBaseInfoDto.getProductName());
                                    hardItemListBean.setHardItemName(skuBaseInfoDto.getSecondCategoryName());
                                    hardItemListBean.setLastCategoryId(skuBaseInfoDto.getCategoryId());
                                    hardItemListBean.setLastCategoryName(skuBaseInfoDto.getLastCategoryName());
                                }
                                if (hardItemListBean.getHardItemSelected() != null && hardItemListBean.getHardItemSelected().getHardSelectionId() != null
                                        && hardItemListBean.getHardItemSelected().getHardSelectionId().equals(skuBaseInfoDto.getSkuId())) {
                                    hardItemListBean.setHardItemName(skuBaseInfoDto.getSecondCategoryName());
                                    hardItemListBean.getHardItemSelected().setHardSelectionName(skuBaseInfoDto.getProductName());
                                    hardItemListBean.setLastCategoryId(skuBaseInfoDto.getCategoryId());
                                    hardItemListBean.setLastCategoryName(skuBaseInfoDto.getLastCategoryName());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("set sku base info fail o2o-exception , more info :", e);
        }
    }

    private void initNewFurnitureInfo
            (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean.FurnitureBean
                     furnitureBean,
             SkuBaseInfoDto skuBaseInfoDto) {
        furnitureBean.setColor(skuBaseInfoDto.getColor());
        furnitureBean.setFurnitureName(skuBaseInfoDto.getProductName());
        furnitureBean.setItemName(skuBaseInfoDto.getProductName());
//        furnitureBean.setFurnitureType(skuBaseInfoDto.getType());
        furnitureBean.setBrand(skuBaseInfoDto.getBrandName());
        furnitureBean.setMaterial(skuBaseInfoDto.getMaterial());
        furnitureBean.setLastCategoryId(skuBaseInfoDto.getLastCategoryId());
        furnitureBean.setLastCategoryName(skuBaseInfoDto.getLastCategoryName());
    }

    private void initNewFurnitureBomInfo(BomGroupDraftVo bomGroupDraftVo,
                                         BomCategoryDTO bomCategoryDTO) {
        bomGroupDraftVo.setLastCategoryId(bomCategoryDTO.getLastCategoryId());
        bomGroupDraftVo.setLastCategoryName(bomCategoryDTO.getLastCategoryName());
    }


    /**
     * 设置硬装bom对比信息
     *
     * @param softResponseListBean
     * @param queryGroupReplaceDetailSimpleVO
     * @param spaceDesignSelectedBean
     */
    private void setHardBomCompareInfo
    (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean
             hardItemListBean,
     QueryGroupReplaceDetailSimpleVO
             queryGroupReplaceDetailSimpleVO, DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean
             spaceDesignSelectedBean) {
        hardItemListBean.getHardBomGroupSelect().setSkuCompareStatus(Constants.COMPARE_STATUS_PRICE_CHANGE).setMessage
                (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences().subtract(hardItemListBean.getHardBomGroupSelect().getPriceDiff()).toString());
        spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_PRICE_CHANGE);
        hardItemListBean.getHardBomGroupSelect().setPriceDiff(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences());
    }

    /**
     * 设置定制柜变价信息
     *
     * @param replaceBomDto
     * @param queryGroupReplaceDetailSimpleVO
     * @param spaceDesignSelectedBean
     */
    private void setHardCabinetBomCompareInfo(ReplaceBomDto replaceBomDto, QueryGroupReplaceDetailSimpleVO
            queryGroupReplaceDetailSimpleVO, DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean
                                                      spaceDesignSelectedBean) {
        replaceBomDto.getBomGroupSelect().setSkuCompareStatus(Constants.COMPARE_STATUS_PRICE_CHANGE).setChangePrice(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences()
                .subtract(replaceBomDto.getBomGroupSelect().getPriceDiff())).setMessage
                (queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences().subtract(replaceBomDto.getBomGroupSelect().getPriceDiff()).toString());
        spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_PRICE_CHANGE);
        replaceBomDto.getBomGroupSelect().setPriceDiff(queryGroupReplaceDetailSimpleVO.getReplaceGroup().getPriceDifferences());
    }

    /**
     * sku、方案价格校验
     */
    private void setPriceDiff(DraftInfoResponse.DraftJsonStrBean draftJsonStr) {
        try {
            //开始比价
            SolutionEffectInfo solutionEffectInfo = new SolutionEffectInfo();
            //传入方案id和自由组合空间id
            solutionEffectInfo.setSolutionId(draftJsonStr.getSolutionSelected().getSolutionId());
            if (CollectionUtils.isNotEmpty(draftJsonStr.getSpaceDesignSelected())) {
                List<SpaceDesign> spaceDesignList = new ArrayList<>();
                List<SpaceDesign> finalSpaceDesignList = spaceDesignList;
                draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
                    //spaceDesignId设置，为了兼容前端老代码
                    spaceDesignSelectedBean.setSpaceDesignId(spaceDesignSelectedBean.getRoomId());
                    finalSpaceDesignList.add(new SpaceDesign(spaceDesignSelectedBean.getSelected().getSpaceDesignId()));
                    finalSpaceDesignList.add(new SpaceDesign(spaceDesignSelectedBean.getDefaultSpace().getSpaceDesignId()));
                });
                spaceDesignList = spaceDesignList.stream().distinct().collect(Collectors.toList());
                solutionEffectInfo.setSpaceDesignList(spaceDesignList);
            }
            //传入sku替换项信息
            List<ComparePriceRequest> comparePriceRequestList = new ArrayList<>();
            draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
                ComparePriceRequest comparePriceRequest = new ComparePriceRequest();
                comparePriceRequest.setRoomId(spaceDesignSelectedBean.getSelected().getSpaceDesignId());
                List<ComparePriceRequest.HardReplaceListBean> hardReplaceList = new ArrayList<>();
                List<ComparePriceRequest.SoftReplaceListBean> softReplaceList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getSoftResponseList())) {
                    spaceDesignSelectedBean.getSoftResponseList().forEach(softResponseListBean -> {
                        if (softResponseListBean.getFurnitureSelected() != null && softResponseListBean.getFurnitureDefault() != null &&
                                !softResponseListBean.getFurnitureSelected().getSkuId().equals(softResponseListBean.getFurnitureDefault().getSkuId())) {//软装选配
                            ComparePriceRequest.SoftReplaceListBean softReplaceListBean = new ComparePriceRequest.SoftReplaceListBean();
                            softReplaceListBean.setFurnitureType(softResponseListBean.getFurnitureDefault().getFurnitureType());
                            softReplaceListBean.setNewSkuId(softResponseListBean.getFurnitureSelected().getSkuId());
                            softReplaceListBean.setSkuId(softResponseListBean.getFurnitureDefault().getSkuId());
                            softReplaceList.add(softReplaceListBean);
                        }
                    });
                }
                if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getHardItemList())) {
                    spaceDesignSelectedBean.getHardItemList().forEach(hardItemListBean -> {
                        if (hardItemListBean.getStatus() == Constants.ITEM_STATUS_ADD) {//新增
                            if (hardItemListBean.getHardItemSelected() != null && hardItemListBean.getHardItemSelected().getProcessSelected() != null &&
                                    hardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem() != null) {//全屋选配
                                ComparePriceRequest.HardReplaceListBean hardReplaceListBean = new ComparePriceRequest.HardReplaceListBean()
                                        .setNewCraftId(hardItemListBean.getHardItemSelected().getProcessSelected().getProcessId())
                                        .setNewSkuId(hardItemListBean.getHardItemSelected().getHardSelectionId())
                                        .setParentCraftId(0).setParentSkuId(0);

                                ComparePriceRequest.HardReplaceListBean sonHardReplaceBean = new ComparePriceRequest.HardReplaceListBean()
                                        .setParentCraftId(hardItemListBean.getHardItemSelected().getProcessSelected().getProcessId())
                                        .setParentSkuId(hardItemListBean.getHardItemSelected().getHardSelectionId())
                                        .setNewSkuId(hardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getHardSelectionId())
                                        .setNewCraftId(hardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getProcessSelected().getProcessId());

                                hardReplaceList.add(hardReplaceListBean);
                                hardReplaceList.add(sonHardReplaceBean);
                            } else {//普通
                                if (hardItemListBean.getHardItemSelected() != null) {
                                    ComparePriceRequest.HardReplaceListBean hardReplaceListBean = new ComparePriceRequest.HardReplaceListBean()
                                            .setNewCraftId(hardItemListBean.getHardItemSelected().getProcessSelected().getProcessId())
                                            .setNewSkuId(hardItemListBean.getHardItemSelected().getHardSelectionId());
                                    hardReplaceList.add(hardReplaceListBean);
                                }
                            }

                        } else if (hardItemListBean.getStatus() == Constants.ITEM_STATUS_DELETE) {//删除
                            ComparePriceRequest.HardReplaceListBean hardReplaceListBean = new ComparePriceRequest.HardReplaceListBean()
                                    .setCraftId(hardItemListBean.getHardItemDefault().getProcessSelected().getProcessId())
                                    .setSkuId(hardItemListBean.getHardItemDefault().getHardSelectionId());
                            hardReplaceList.add(hardReplaceListBean);
                        } else if (hardItemListBean.getHardItemSelected() != null && ((!hardItemListBean.getHardItemSelected().getHardSelectionId().equals(hardItemListBean.getHardItemDefault().getHardSelectionId())) ||
                                (hardItemListBean.getHardItemSelected().getProcessSelected() != null && hardItemListBean.getHardItemDefault().getProcessSelected() != null &&
                                        !hardItemListBean.getHardItemSelected().getProcessSelected().getProcessId().equals(hardItemListBean.getHardItemDefault().getProcessSelected().getProcessId())))) {//硬装选配
                            ComparePriceRequest.HardReplaceListBean hardReplaceListBean = new ComparePriceRequest.HardReplaceListBean()
                                    .setCraftId(hardItemListBean.getHardItemDefault().getProcessSelected().getProcessId())
                                    .setNewCraftId(hardItemListBean.getHardItemSelected().getProcessSelected().getProcessId())
                                    .setNewSkuId(hardItemListBean.getHardItemSelected().getHardSelectionId())
                                    .setSkuId(hardItemListBean.getHardItemDefault().getHardSelectionId());
                            hardReplaceList.add(hardReplaceListBean);
                        }
                    });
                }
                comparePriceRequest.setHardReplaceList(hardReplaceList);
                comparePriceRequest.setSoftReplaceList(softReplaceList);
                comparePriceRequestList.add(comparePriceRequest);

            });

            QueryPriceDiffDTO queryPriceDiffDTO = productProgramProxy.queryPriceDiff(new QueryPriceDiffDTO(comparePriceRequestList, solutionEffectInfo));
            if (queryPriceDiffDTO == null) {
                return;
            }
            List<ComparePriceRequest> comparePriceResponseList = queryPriceDiffDTO.getQueryPriceDiffByRoomDTOList();
            SolutionEffectInfo solutionEffectInfoResponse = queryPriceDiffDTO.getSolutionEffectInfo();

            //方案价格变动
            if (solutionEffectInfoResponse != null && solutionEffectInfoResponse.getSolutionPrice() != null) {//方案价格为空表示该方案已下架
                draftJsonStr.setTotalPrice(solutionEffectInfoResponse.getSolutionPrice());
                if (!solutionEffectInfoResponse.getSolutionPrice().equals(draftJsonStr.getSolutionSelected().getSolutionPrice())) {
                    draftJsonStr.getSolutionSelected().setCompareStatus(Constants.COMPARE_STATUS_PRICE_CHANGE);
                    draftJsonStr.getSolutionSelected().setMessage((solutionEffectInfoResponse.getSolutionPrice() - draftJsonStr.getSolutionSelected().getSolutionPrice()));
                    draftJsonStr.getSolutionSelected().setSolutionPrice(solutionEffectInfoResponse.getSolutionPrice());
                }
                if (CollectionUtils.isNotEmpty(solutionEffectInfoResponse.getSpaceDesignList())) {
                    solutionEffectInfoResponse.getSpaceDesignList().forEach(spaceDesign -> draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
                                //已选空间取相同空间
                                if (spaceDesign.getSpaceDesignId().equals(spaceDesignSelectedBean.getSelected().getSpaceDesignId()) &&
                                        spaceDesign.getSpaceDesignPrice() - spaceDesignSelectedBean.getSelected().getSpaceDesignPrice() != 0) {
                                    Integer spaceDesignPrice = spaceDesignSelectedBean.getSelected().getSpaceDesignPrice();
                                    spaceDesignSelectedBean.getSelected().setCompareStatus(Constants.COMPARE_STATUS_PRICE_CHANGE);
                                    spaceDesignSelectedBean.getSelected().setMessage(spaceDesign.getSpaceDesignPrice() - spaceDesignPrice);
                                    spaceDesignSelectedBean.getSelected().setSpaceDesignPrice(spaceDesign.getSpaceDesignPrice());
                                }
                                //默认空间取相同空间
                                if (spaceDesign.getSpaceDesignId().equals(spaceDesignSelectedBean.getDefaultSpace().getSpaceDesignId()) &&
                                        spaceDesign.getSpaceDesignPrice() - spaceDesignSelectedBean.getDefaultSpace().getSpaceDesignPrice() != 0) {
                                    spaceDesignSelectedBean.getDefaultSpace().setSpaceDesignPrice(spaceDesign.getSpaceDesignPrice());
                                }
                            })

                    );
                    solutionEffectInfoResponse.getSpaceDesignList().forEach(spaceDesign -> draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
                                //替换空间差价累计
                                if (spaceDesign.getSpaceDesignId().equals(spaceDesignSelectedBean.getSelected().getSpaceDesignId()) &&
                                        !spaceDesignSelectedBean.getSelected().getSpaceDesignId().equals(spaceDesignSelectedBean.getDefaultSpace().getSpaceDesignId())) {
                                    draftJsonStr.addTotalPrice(spaceDesignSelectedBean.getSelected().getSpaceDesignPrice() - spaceDesignSelectedBean.getDefaultSpace().getSpaceDesignPrice());
                                }
                            })

                    );
                }
            }

            //sku价格回填
            draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> comparePriceResponseList.forEach(comparePriceRequest -> {
                if (spaceDesignSelectedBean.getSelected().getSpaceDesignId().equals(comparePriceRequest.getRoomId())) {
                    // 硬装变价处理
                    comparePriceRequest.getHardReplaceList().forEach(hardReplaceListBean -> spaceDesignSelectedBean.getHardItemList().forEach(hardItemListBean -> {
                        if (hardItemListBean.getStatus() == Constants.ITEM_STATUS_DELETE && hardItemListBean.getHardItemSelected().getHardSelectionId().equals(hardReplaceListBean.getSkuId())
                                && hardItemListBean.getHardItemSelected().getProcessSelected().getProcessId().equals(hardReplaceListBean.getCraftId())
                                && hardReplaceListBean.getPriceDiff() != null) {//减项
                            draftJsonStr.delTotalPrice((hardReplaceListBean.getPriceDiff().intValue()));
                            hardItemListBean.getHardItemSelected().getProcessSelected().setPrice(hardReplaceListBean.getPriceDiff());
                            hardItemListBean.getHardItemDefault().getProcessSelected().setPrice(hardReplaceListBean.getPriceDiff());

                        } else if (hardItemListBean.getStatus() == Constants.ITEM_STATUS_ADD && hardItemListBean.getHardItemSelected() != null &&
                                hardItemListBean.getHardItemSelected().getHardSelectionId().equals(hardReplaceListBean.getNewSkuId())
                                && hardItemListBean.getHardItemSelected().getProcessSelected().getProcessId().equals(hardReplaceListBean.getNewCraftId()) && !isRoomWhole(spaceDesignSelectedBean) &&
                                hardReplaceListBean.getPriceDiff() != null) {//普通空间增项
                            draftJsonStr.addTotalPrice(hardReplaceListBean.getPriceDiff().intValue());
                            if (hardReplaceListBean.getPriceDiff().subtract(hardItemListBean.getHardItemSelected().getProcessSelected().getPrice()).compareTo(BigDecimal.ZERO) != 0) {
                                hardItemListBean.getHardItemSelected().setSkuCompareStatus(Constants.COMPARE_STATUS_PRICE_CHANGE)
                                        .setMessage(hardReplaceListBean.getPriceDiff().subtract(hardItemListBean.getHardItemSelected().getProcessSelected().getPrice()).intValue());
                                hardItemListBean.getHardItemSelected().getProcessSelected().setPrice(hardReplaceListBean.getPriceDiff());
                                spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_PRICE_CHANGE);
                            }
                        } else if (isSkuMatch(hardReplaceListBean, hardItemListBean)) {// 草稿sku与比价接口返回sku匹配（非全屋）
                            if (hardReplaceListBean.getPriceDiff() != null) {
                                draftJsonStr.addTotalPrice((hardReplaceListBean.getPriceDiff().intValue()));
                                if (hardReplaceListBean.getPriceDiff().subtract(hardItemListBean.getHardItemSelected().getProcessSelected().getPriceDiff()).compareTo(BigDecimal.ZERO) != 0) {
                                    hardItemListBean.getHardItemSelected().setSkuCompareStatus(Constants.COMPARE_STATUS_PRICE_CHANGE)
                                            .setMessage(hardReplaceListBean.getPriceDiff().subtract(hardItemListBean.getHardItemSelected().getProcessSelected().getPriceDiff()).intValue());
                                    spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_PRICE_CHANGE);

                                    hardItemListBean.getHardItemSelected().getProcessSelected().setPriceDiff(hardReplaceListBean.getPriceDiff());
                                }
                            }
                            //全屋空间
                        } else if (isRoomWhole(spaceDesignSelectedBean)) {
                            //草稿sku与比价接口返回sku匹配（全屋硬装第一层结构）
                            if (isWholeHard(hardReplaceListBean, hardItemListBean)) {

                                if (hardReplaceListBean.getPriceDiff() != null) {
                                    draftJsonStr.addTotalPrice((hardReplaceListBean.getPriceDiff().intValue()));
                                    if (hardReplaceListBean.getPriceDiff().subtract(hardItemListBean.getHardItemSelected().getProcessSelected().getPrice()).compareTo(BigDecimal.ZERO) != 0) {
                                        hardItemListBean.getHardItemSelected().setSkuCompareStatus(Constants.COMPARE_STATUS_PRICE_CHANGE)
                                                .setMessage(hardReplaceListBean.getPriceDiff().subtract(hardItemListBean.getHardItemSelected().getProcessSelected().getPrice()).intValue());
                                        spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_PRICE_CHANGE);
                                        hardItemListBean.getHardItemSelected().getProcessSelected().setPrice(hardReplaceListBean.getPriceDiff());
                                    }
                                }
                                //草稿sku与比价接口返回sku匹配（硬装全屋第二层结构）
                            } else if (isWholeHardSec(hardReplaceListBean, hardItemListBean)) {

                                if (hardReplaceListBean.getPriceDiff() != null) {
                                    draftJsonStr.addTotalPrice((hardReplaceListBean.getPriceDiff().intValue()));
                                    if (hardReplaceListBean.getPriceDiff().subtract(hardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getProcessSelected().getPrice()).
                                            compareTo(BigDecimal.ZERO) != 0) {
                                        hardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem().setSkuCompareStatus(Constants.COMPARE_STATUS_PRICE_CHANGE)
                                                .setMessage(hardReplaceListBean.getPriceDiff().subtract(hardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getProcessSelected()
                                                        .getPrice()).intValue());
                                        spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_PRICE_CHANGE);
                                        hardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getProcessSelected().setPrice(hardReplaceListBean.getPriceDiff());
                                    }
                                }
                            }
                        }
                    }));
                    // 软装变价处理
                    if (comparePriceRequest.getSoftReplaceList() != null) {
                        comparePriceRequest.getSoftReplaceList().forEach(softReplaceListBean -> spaceDesignSelectedBean.getSoftResponseList().forEach(softResponseListBean -> {
                            if (softResponseListBean.getFurnitureDefault() != null && softReplaceListBean.getSkuId().equals(softResponseListBean.getFurnitureDefault().getSkuId()) &&
                                    softReplaceListBean.getNewSkuId().equals(softResponseListBean.getFurnitureSelected().getSkuId()) &&
                                    softReplaceListBean.getFurnitureType().equals(softResponseListBean.getFurnitureSelected().getFurnitureType())) {//相同
                                if (softReplaceListBean.getPriceDiff() != null) {
                                    draftJsonStr.addTotalPrice((softReplaceListBean.getPriceDiff().intValue()));
                                    if (softReplaceListBean.getPriceDiff().subtract(softResponseListBean.getFurnitureSelected().getPriceDiff()).compareTo(BigDecimal.ZERO) != 0) {
                                        softResponseListBean.getFurnitureSelected().setSkuCompareStatus(Constants.COMPARE_STATUS_PRICE_CHANGE)
                                                .setMessage(softReplaceListBean.getPriceDiff().subtract(softResponseListBean.getFurnitureSelected().getPriceDiff()).intValue());
                                        spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_PRICE_CHANGE);
                                        softResponseListBean.getFurnitureSelected().setPriceDiff(softReplaceListBean.getPriceDiff());
                                    }
                                }

                            }
                        }));
                    }
                }
            }));
        } catch (Exception e) {
            LOG.error("check solution sku price fail o2o-exception , more info :", e);
            e.printStackTrace();
        }
    }

    /**
     * 草稿sku与比价接口返回sku匹配（非全屋）
     *
     * @param hardReplaceListBean
     * @param hardItemListBean
     * @return
     */
    private boolean isSkuMatch(ComparePriceRequest.HardReplaceListBean hardReplaceListBean,
                               DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean hardItemListBean) {
        return hardReplaceListBean.getSkuId() != null && hardReplaceListBean.getNewSkuId() != null && hardItemListBean.getHardItemSelected() != null &&
                hardItemListBean.getHardItemDefault() != null &&
                hardReplaceListBean.getSkuId().equals(hardItemListBean.getHardItemDefault().getHardSelectionId()) &&
                hardReplaceListBean.getNewSkuId().equals(hardItemListBean.getHardItemSelected().getHardSelectionId()) &&
                hardReplaceListBean.getCraftId().equals(hardItemListBean.getHardItemDefault().getProcessSelected().getProcessId()) &&
                hardReplaceListBean.getNewCraftId().equals(hardItemListBean.getHardItemSelected().getProcessSelected().getProcessId());
    }

    /**
     * 草稿sku与比价接口返回sku匹配（全屋硬装第一层结构）
     *
     * @param hardReplaceListBean
     * @param hardItemListBean
     * @return
     */
    private boolean isWholeHard(ComparePriceRequest.HardReplaceListBean hardReplaceListBean,
                                DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean hardItemListBean) {
        return hardReplaceListBean.getParentSkuId() != null && hardReplaceListBean.getNewSkuId() != null && hardItemListBean.getHardItemSelected() != null
                && hardReplaceListBean.getNewSkuId().equals(hardItemListBean.getHardItemSelected().getHardSelectionId()) &&//硬装全屋第一层结构
                hardReplaceListBean.getNewCraftId().equals(hardItemListBean.getHardItemSelected().getProcessSelected().getProcessId()) &&
                hardReplaceListBean.getParentSkuId().equals(Constants.WHOLE_HARD_ONE_PARENT) && hardReplaceListBean.getParentCraftId().equals(Constants.WHOLE_HARD_ONE_PARENT);
    }

    /**
     * 草稿sku与比价接口返回sku匹配（硬装全屋第二层结构）
     *
     * @param hardReplaceListBean
     * @param hardItemListBean
     * @return
     */
    private boolean isWholeHardSec(ComparePriceRequest.HardReplaceListBean hardReplaceListBean,
                                   DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean hardItemListBean) {
        return hardReplaceListBean.getParentSkuId() != null && hardItemListBean.getHardItemSelected() != null && hardItemListBean.getHardItemSelected().getProcessSelected() != null
                && hardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem() != null
                && hardReplaceListBean.getNewSkuId().equals(hardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getHardSelectionId()) &&
                hardReplaceListBean.getNewCraftId().equals(hardItemListBean.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getProcessSelected().getProcessId()) &&
                hardReplaceListBean.getParentSkuId().equals(hardItemListBean.getHardItemSelected().getHardSelectionId()) &&
                hardReplaceListBean.getParentCraftId().equals(hardItemListBean.getHardItemSelected().getProcessSelected().getProcessId());
    }

    /**
     * 判断是否全屋空间
     *
     * @param spaceDesignSelectedBean
     * @return
     */
    private boolean isRoomWhole(DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean
                                        spaceDesignSelectedBean) {
        return RoomUseEnum.ROOM_WHOLE.getCode() == spaceDesignSelectedBean.getSpaceUsageId()
                || RoomUseEnum.ROOM_WHOLE.getDescription().equals(spaceDesignSelectedBean.getSpaceUsageName());
    }


    /**
     * 查询方案、空间、sku（下单参数中的信息）是否下架
     *
     * @param draftInfoResponse
     */
    private void setSolutionAndSkuStatus(DraftInfoResponse draftInfoResponse) {
        try {

            DraftInfoResponse.DraftJsonStrBean draftJsonStr = draftInfoResponse.getDraftContent();
            CreateFamilyOrderRequest orderRequest = new CreateFamilyOrderRequest();
            //组装下单参数
            setOrderRequest(orderRequest, draftInfoResponse);
            // dolly校验sku是否有效下架
            ValidateParamResultDto validateParamResponse = productProgramProxy.dollyValidateOrderParam(orderRequest);
            //存在错误下单参数
            if (validateParamResponse == null || validateParamResponse.getCheckResult() != 0) {
                return;
            }
            List<ValidateParamResultDto.SolutionError> solutionErrorList = validateParamResponse.getSolutionErrorList();
            List<ValidateParamResultDto.RoomErrorListBean> roomErrorList = validateParamResponse.getRoomErrorList();
            List<ValidateParamResultDto.HardAddErrorBean> hardReplaceErrorList = validateParamResponse.getHardReplaceErrorList();
            List<ValidateParamResultDto.AddErrorBean> hardStandardErrorList = validateParamResponse.getHardStandardErrorList();
            List<ValidateParamResultDto.SoftReplaceErrorListBean> softReplaceErrorList = validateParamResponse.getSoftReplaceErrorList();
            List<ValidateParamResultDto.HardAddErrorBean> wholeHardReplaceErrorList = validateParamResponse.getWholeHardReplaceErrorList();

            List<ValidateParamResultDto.SoftBomReplaceError> softBomReplaceErrorList = validateParamResponse.getSoftBomReplaceErrorList();
            List<ValidateParamResultDto.HardAddErrorBean> hardAddErrorList = validateParamResponse.getHardAddErrorList();
//            List<ValidateParamResponse.AddErrorBean> hardAddBagErrorList = validateParamResponse.getHardAddBagErrorList();
//            List<ValidateParamResponse.AddErrorBean> softAddBagErrorList = validateParamResponse.getSoftAddBagErrorList();
//            List<ValidateParamResponse.HardAddErrorBean> hardDeleteErrorList = validateParamResponse.getHardDeleteErrorList();

            // 方案下架
            if (CollectionUtils.isNotEmpty(solutionErrorList)) {
                solutionErrorList.forEach(solutionError -> {
                    if (solutionError.getErrorCode() == ErrorCodeEnum.SOLUTION_OFF_LINE.getErrorCode()) {
                        if (draftJsonStr.getSolutionSelected().getSolutionId().equals(solutionError.getSolutionId().intValue())) {
                            draftJsonStr.getSolutionSelected().setCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                        }
                    }
                });
            }

            // 软硬装替换前sku已下架
            if (CollectionUtils.isNotEmpty(softBomReplaceErrorList)) {
                softBomReplaceErrorList.forEach(softBomReplaceError -> {
                    if (softBomReplaceError.getErrorCode() == ErrorCodeEnum.SOFT_REPLACE_SKU_OFF_LINE.getErrorCode()) {
                        draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> spaceDesignSelectedBean.getSoftResponseList().forEach(softResponse -> {
                            if (softResponse.getBomGroupDefault() != null && softResponse.getBomGroupDefault().getGroupId().equals(Integer.parseInt(softBomReplaceError.getGroupId().toString()))) {
                                softResponse.getBomGroupSelected().setCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                softResponse.getBomGroupSelected().setSkuCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                draftJsonStr.setSpecialExceptionStatus(Constants.SPECIAL_EXCEPTION_STATUS_EXCEPTION);
                            }
                        }));
                    }
                });
            }

            // 空间下架
            if (CollectionUtils.isNotEmpty(roomErrorList)) {
                roomErrorList.forEach(roomErrorListBean -> {
                    if (roomErrorListBean.getErrorCode() == ErrorCodeEnum.ROOM_NOT_EXIST.getErrorCode()) {
                        draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> {
                            if (spaceDesignSelectedBean.getSelected().getSpaceDesignId().equals(roomErrorListBean.getRoomId())) {//空间下架
                                spaceDesignSelectedBean.getSelected().setCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                            }
                        });
                    }
                });
            }

            if (CollectionUtils.isNotEmpty(hardReplaceErrorList)) {
                hardReplaceErrorList.forEach(addErrorBean -> {
                    // 硬装替换后sku已下架
                    if (addErrorBean.getErrorCode() == ErrorCodeEnum.HARD_REPLACE_NEW_SKU_OFF_LINE.getErrorCode()) {
                        draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> spaceDesignSelectedBean.getHardItemList().forEach(hardItem -> {
                            if (hardItem.getHardItemSelected() != null && hardItem.getHardItemSelected().getHardSelectionId().equals(addErrorBean.getNewSkuId())) {
                                hardItem.getHardItemSelected().setCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                hardItem.getHardItemSelected().setSkuCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_OFFLINE);
                            }
                        }));
                    }
                    // 硬装替换前sku下架
                    if (addErrorBean.getErrorCode() == ErrorCodeEnum.HARD_REPLACE_SKU_NOT_EXIST.getErrorCode()) {
                        draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> spaceDesignSelectedBean.getHardItemList().forEach(hardItem -> {
                            if (hardItem.getHardItemDefault() != null &&
                                    hardItem.getHardItemDefault().getHardSelectionId() != null
                                    && addErrorBean.getRoomId() != null
                                    && spaceDesignSelectedBean.getRoomId() != null
                                    && addErrorBean.getRoomId().equals(spaceDesignSelectedBean.getRoomId())
                                    && hardItem.getHardItemDefault().getHardSelectionId().equals(addErrorBean.getSkuId())) {
                                hardItem.getHardItemDefault().setCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                hardItem.getHardItemDefault().setSkuCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                draftJsonStr.setSpecialExceptionStatus(Constants.SPECIAL_EXCEPTION_STATUS_EXCEPTION);
                            }
                        }));
                    }
                });
            }

            // 全屋新增硬装sku已下架
            if (CollectionUtils.isNotEmpty(wholeHardReplaceErrorList)) {
                wholeHardReplaceErrorList.forEach(wholeErrorBean -> {
                    if (wholeErrorBean.getErrorCode() == ErrorCodeEnum.WHOLE_HARD_ADD_SKU_OFF_LINE.getErrorCode()) {
                        draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> spaceDesignSelectedBean.getHardItemList().forEach(hardItem -> {
                            if (hardItem.getHardItemSelected() != null && hardItem.getHardItemSelected().getHardSelectionId().equals(wholeErrorBean.getNewSkuId()) &&
                                    hardItem.getHardItemSelected().getProcessSelected().getProcessId().equals(wholeErrorBean.getNewCraftId())) {
                                hardItem.getHardItemSelected().setCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                hardItem.getHardItemSelected().setSkuCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_OFFLINE);
                            }
                            if (hardItem.getHardItemSelected() != null && hardItem.getHardItemSelected().getProcessSelected().getSelectChildHardItem() != null && hardItem.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getHardSelectionId().equals(wholeErrorBean.getNewSkuId()) &&
                                    hardItem.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getProcessSelected().getProcessId().equals(wholeErrorBean.getNewCraftId())) {
                                hardItem.getHardItemSelected().getProcessSelected().getSelectChildHardItem().setCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                hardItem.getHardItemSelected().getProcessSelected().getSelectChildHardItem().setSkuCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_OFFLINE);
                            }
                        }));
                    }
                    if (wholeErrorBean.getErrorCode() == ErrorCodeEnum.HARD_ADD_ITEM_CLASS_OFF_LINE.getErrorCode()) {//全屋类目下架
                        draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> spaceDesignSelectedBean.getHardItemList().forEach(hardItem -> {
                            if (hardItem.getHardItemSelected() != null && hardItem.getHardItemSelected().getHardSelectionId().equals(wholeErrorBean.getNewSkuId()) &&
                                    hardItem.getHardItemSelected().getProcessSelected().getProcessId().equals(wholeErrorBean.getNewCraftId())) {
                                hardItem.getHardItemSelected().setCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                hardItem.getHardItemSelected().setSkuCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                draftJsonStr.setSpecialExceptionStatus(Constants.SPECIAL_EXCEPTION_STATUS_EXCEPTION);
                            }
                            if (hardItem.getHardItemSelected() != null && hardItem.getHardItemSelected().getProcessSelected().getSelectChildHardItem() != null && hardItem.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getHardSelectionId().equals(wholeErrorBean.getNewSkuId()) &&
                                    hardItem.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getProcessSelected().getProcessId().equals(wholeErrorBean.getNewCraftId())) {
                                hardItem.getHardItemSelected().setCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                hardItem.getHardItemSelected().setSkuCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                draftJsonStr.setSpecialExceptionStatus(Constants.SPECIAL_EXCEPTION_STATUS_EXCEPTION);
                            }
                        }));
                    }
                });
            }

            // 硬装标准升级项sku已下架
            if (CollectionUtils.isNotEmpty(hardStandardErrorList)) {
                hardStandardErrorList.forEach(addErrorBean -> {
                    if (addErrorBean.getErrorCode() == ErrorCodeEnum.HARD_STANDARD_SKU_OFF_LIE.getErrorCode()) {
                        draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> spaceDesignSelectedBean.getHardItemList().forEach(hardItem -> {
                            if (hardItem.getHardItemSelected() != null && hardItem.getHardItemSelected().getHardSelectionId().equals(addErrorBean.getSkuId())) {
                                hardItem.getHardItemSelected().setCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                hardItem.getHardItemSelected().setSkuCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_OFFLINE);
                            }
                        }));
                    }
                });
            }

            if (CollectionUtils.isNotEmpty(softReplaceErrorList)) {
                softReplaceErrorList.forEach(addErrorBean -> {
                    // 软装替换后sku已下架
                    if (addErrorBean.getErrorCode() == ErrorCodeEnum.SOFT_REPLACE_NEW_SKU_OFF_LINE.getErrorCode()) {
                        draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> spaceDesignSelectedBean.getSoftResponseList().forEach(hardItem -> {
                            if (hardItem.getFurnitureSelected() != null && hardItem.getFurnitureSelected().getSkuId() != null && hardItem.getFurnitureSelected().getSkuId().equals(addErrorBean.getNewSkuId())) {
                                hardItem.getFurnitureSelected().setCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                hardItem.getFurnitureSelected().setSkuCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_OFFLINE);
                            }
                        }));
                    }
                    // 软装替换前sku已下架
                    if (addErrorBean.getErrorCode() == ErrorCodeEnum.SOFT_REPLACE_SKU_OFF_LINE.getErrorCode()) {
                        draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> spaceDesignSelectedBean.getSoftResponseList().forEach(hardItem -> {
                            if (hardItem.getFurnitureDefault() != null && hardItem.getFurnitureDefault().getSkuId() != null
                                    && addErrorBean.getRoomId() != null
                                    && spaceDesignSelectedBean.getRoomId() != null
                                    && addErrorBean.getRoomId().equals(spaceDesignSelectedBean.getRoomId())
                                    && hardItem.getFurnitureDefault().getSkuId().equals(addErrorBean.getSkuId())) {
                                hardItem.getFurnitureDefault().setCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                hardItem.getFurnitureDefault().setSkuCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                draftJsonStr.setSpecialExceptionStatus(Constants.SPECIAL_EXCEPTION_STATUS_EXCEPTION);
                            }
                        }));
                    }
                });
            }
            //空间硬装商品新增错误信息集合
            if (CollectionUtils.isNotEmpty(hardAddErrorList)) {
                hardAddErrorList.forEach(hardAddErrorBean -> {
                    // 选配商品下架
                    if (hardAddErrorBean.getErrorCode() == ErrorCodeEnum.HARD_ADD_SKU_OFF_LINE.getErrorCode()) {
                        draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> spaceDesignSelectedBean.getHardItemList().forEach(hardItem -> {
                            if (hardItem.getStatus() == Constants.ITEM_STATUS_ADD
                                    && hardItem.getHardItemSelected() != null && hardItem.getHardItemSelected().getHardSelectionId().equals(hardAddErrorBean.getNewSkuId())) {
                                hardItem.getHardItemSelected().setCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                hardItem.getHardItemSelected().setSkuCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                spaceDesignSelectedBean.setInsideSkuStatus(Constants.INSIDE_SKU_STATUS_OFFLINE);
                            }
                        }));
                    }
                    //选配类目下架
                    if (hardAddErrorBean.getErrorCode() == ErrorCodeEnum.HARD_ADD_ITEM_CLASS_OFF_LINE.getErrorCode()) {
                        draftJsonStr.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> spaceDesignSelectedBean.getHardItemList().forEach(hardItem -> {
                            if (hardItem.getStatus() == Constants.ITEM_STATUS_ADD
                                    && hardItem.getHardItemSelected() != null && hardItem.getHardItemSelected().getHardSelectionId().equals(hardAddErrorBean.getNewSkuId())) {
                                hardItem.getHardItemSelected().setCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                hardItem.getHardItemSelected().setSkuCompareStatus(Constants.COMPARE_STATUS_OFFLINE);
                                draftJsonStr.setSpecialExceptionStatus(Constants.SPECIAL_EXCEPTION_STATUS_EXCEPTION);
                            }
                        }));
                    }
                });
            }
        } catch (Exception e) {
            LOG.error("check sku up status fail o2o-exception , more info :", e);
        }


    }

    /**
     * 草稿项信息查询
     *
     * @param request
     * @return
     */
    @Override
    public DraftSimpleRequestPage queryDraftList(QueryDraftRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderNum", request.getOrderId());
        params.put("pageNo", request.getPageNo());
        params.put("pageSize", request.getPageSize());
        Map<String, Object> stringObjectMap = concurrentQueryList(params);
        DraftSimpleRequestPage draftSimplePage = (DraftSimpleRequestPage) stringObjectMap.get(ConcurrentTaskEnum.QUERY_DRAFT_LIST.name());
        SolutionInfo solutionInfo = (SolutionInfo) stringObjectMap.get(ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name());
        // 查询订单，则放到第一的位置
        //    如果草稿不存在签约，则草稿总数和签约草稿数都+1，把订单数据加入到返回的第一位
        //    如果草稿存在签约，则草稿总数和签约草稿数不变
        AppOrderBaseInfoResponseVo appOrderBaseInfoResponseVo = (AppOrderBaseInfoResponseVo) stringObjectMap.get(ConcurrentTaskEnum.QUERY_APP_ORDER_BASE_INFO.name());

        List<DraftSimpleRequest> draftSimpleRequests = draftSimplePage.getOrderDraftSimpleList();
        if (draftSimpleRequests == null) {
            draftSimpleRequests = Lists.newArrayList();
        }
        if (CollectionUtils.isNotEmpty(draftSimpleRequests)) {
            for (DraftSimpleRequest draftSimple : draftSimpleRequests) {
                draftSimple.setUrl(AliImageUtil.imageCompress(draftSimple.getUrl(), 2, request.getWidth(), ImageConstant.SIZE_MIDDLE));
                draftSimple.setPreMasterTaskFinishTime(draftSimple.getPreMasterTaskFinishTime());
            }
            for (DraftSimpleRequest draftSimple : draftSimpleRequests) {
                if (draftSimple.getDraftSignStatus() == Constants.DRAFT_SIGN_STATUS_NO_SIGN) {
                    draftSimple.setDraftSignStatus(Constants.DRAFT_SIGN_STATUS_LATEST);//最新草稿
                    break;
                }
            }
        }
        if (solutionInfo != null) {
            draftSimplePage.setCreateOrderType(2);
            draftSimplePage.setSignEnter(2);
            draftSimplePage.setIsLoan(solutionInfo.getContainLoan().equals(1));
            //未完成全款支付，非锁价中的订单（老订单），未确认方案可以重新签约
            if (appOrderBaseInfoResponseVo != null && appOrderBaseInfoResponseVo.getPreConfirmed() != null && appOrderBaseInfoResponseVo.getPreConfirmed().equals(0)
                    && appOrderBaseInfoResponseVo.getLockPriceFlag() != null && appOrderBaseInfoResponseVo.getLockPriceFlag().equals(-1) && appOrderBaseInfoResponseVo.getAllMoney().equals(0)) {
                draftSimplePage.setSignEnter(1);
                draftSimplePage.setUnAllMoneyAndUnLocked(1);
            }
        }
        //设置方案状态
        setSoutionStatus(draftSimplePage, solutionInfo);
        if (appOrderBaseInfoResponseVo != null) {
            draftSimplePage.setAllMoney(appOrderBaseInfoResponseVo.getAllMoney() == null ? 0 : appOrderBaseInfoResponseVo.getAllMoney());
        }
        // 如果有非取消的订单,非爱家贷
        if (solutionInfo != null && appOrderBaseInfoResponseVo != null && solutionInfo.getContainLoan() != 1 &&
                !appOrderBaseInfoResponseVo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_CANCELED.getStatus()) && request.getPageNo() != null) {
            if (draftSimplePage.getSignRecords() == 0) {
                DraftSimpleRequest draftSimpleRequest = new DraftSimpleRequest()
                        .setUrl(AliImageUtil.imageCompress(solutionInfo.getSolutionImgUrl(), 2, request.getWidth(), ImageConstant.SIZE_MIDDLE))
                        .setDraftSignStatus(Constants.DRAFT_SIGN_STATUS_HAS_SIGN)
                        .setDraftName(solutionInfo.getSolutionTypeStr())
                        .setDecorationType(solutionInfo.getSolutionType())
                        .setSolutionId(solutionInfo.getSolutionId())
                        .setStandardHardSolutionFlag(solutionInfo.getStandardHardSolutionFlag())
                        .setCreateTime(appOrderBaseInfoResponseVo.getCreateTime())
                        .setUpdateTime(appOrderBaseInfoResponseVo.getCreateTime())
                        .setStyleName(appOrderBaseInfoResponseVo.getSolutionStyleName())
                        .setLockPriceExpireTime(appOrderBaseInfoResponseVo.getLockPriceExpireTime())
                        .setLockPriceFlag(appOrderBaseInfoResponseVo.getLockPriceFlag() == null ? -1 : appOrderBaseInfoResponseVo.getLockPriceFlag())
                        .setPriceLockCount(getPriceLockCount(appOrderBaseInfoResponseVo.getLockPriceExpireTime()))
                        .setApartmentId(solutionInfo.getLayoutId())// 户型ID，订单字段名不一样
                        .setApartmentVersion(solutionInfo.getApartmentVersion())
                        .setReformFlag(solutionInfo.getReformFlag() == null ? 0 : solutionInfo.getReformFlag());
                draftSimplePage.setSignRecords(1);
                draftSimplePage.setTotalRecords(draftSimplePage.getTotalRecords() + 1);
                draftSimpleRequest.setPurchaseType(solutionInfo.getPurchaseType());
                draftSimpleRequest.setTotalPrice((appOrderBaseInfoResponseVo.getSolutionAmount() == null ?
                        0 : appOrderBaseInfoResponseVo.getSolutionAmount().subtract(appOrderBaseInfoResponseVo.getPriceDisAmount() == null ? BigDecimal.ZERO : appOrderBaseInfoResponseVo.getPriceDisAmount()).intValue()));
                if (request.getPageNo() == 1) {
                    draftSimpleRequests.add(0, draftSimpleRequest);
                }
            } else if (draftSimplePage.getSignRecords() == 1 && CollectionUtils.isNotEmpty(draftSimpleRequests)) {
                draftSimpleRequests.forEach(draftSimple -> {
                    if (draftSimple.getDraftSignStatus() == Constants.DRAFT_SIGN_STATUS_HAS_SIGN) {
                        draftSimple.setTotalPrice(appOrderBaseInfoResponseVo.getOrderTotalAmount().intValue())
                                .setLockPriceExpireTime(appOrderBaseInfoResponseVo.getLockPriceExpireTime())
                                .setLockPriceFlag(appOrderBaseInfoResponseVo.getLockPriceFlag() == null ? -1 : appOrderBaseInfoResponseVo.getLockPriceFlag())
                                .setPriceLockCount(getPriceLockCount(appOrderBaseInfoResponseVo.getLockPriceExpireTime()));
                    }
                });
            }
        }


        if (appOrderBaseInfoResponseVo != null) {
            draftSimplePage.setPreConfirmed(appOrderBaseInfoResponseVo.getPreConfirmed());
            draftSimplePage.setHasPayed(appOrderBaseInfoResponseVo.getFundAmount() != null && appOrderBaseInfoResponseVo.getFundAmount().compareTo(BigDecimal.ZERO) > 0);
            draftSimplePage.setOrderStatus(homeBuildingService.getOrderStatus(appOrderBaseInfoResponseVo.getOrderStatus()));
        }
        if (request.getSortType() != null && request.getSortType() == 1 && CollectionUtils.isNotEmpty(draftSimplePage.getOrderDraftSimpleList())) {//对比列表排序
            sortDraftListBytype(draftSimplePage);
        }
        return draftSimplePage;
    }

    /**
     * 方案对比页面的排序
     *
     * @param draftSimplePage
     */
    private void sortDraftListBytype(DraftSimpleRequestPage draftSimplePage) {
        List<DraftSimpleRequest> lastDraftSimple = new ArrayList<>();
        draftSimplePage.getOrderDraftSimpleList().forEach(draftSimpleRequest -> {
            if (draftSimpleRequest.getStandardHardSolutionFlag() != null && draftSimpleRequest.getStandardHardSolutionFlag() == 0) {//非标准化硬装
                if (!lastDraftSimple.contains(draftSimpleRequest)) {
                    lastDraftSimple.add(draftSimpleRequest);
                }
            }
            if (draftSimpleRequest.getPurchaseType() != null && draftSimpleRequest.getPurchaseType() != 1) {//非app下单方案
                if (!lastDraftSimple.contains(draftSimpleRequest)) {
                    lastDraftSimple.add(draftSimpleRequest);
                }
            }
        });
        if (CollectionUtils.isNotEmpty(lastDraftSimple)) {
            lastDraftSimple.forEach(draftSimpleRequest -> {
                draftSimplePage.getOrderDraftSimpleList().remove(draftSimpleRequest);
            });
            lastDraftSimple.forEach(draftSimpleRequest -> {
                draftSimplePage.getOrderDraftSimpleList().add(draftSimpleRequest);
            });
            Collections.sort(draftSimplePage.getOrderDraftSimpleList(), Comparator.comparing(DraftSimpleRequest::getStandardHardSolutionFlag).reversed());
        }
        List<DraftSimpleRequest> offlineList = new ArrayList<>();
        for (int i = 0; i < draftSimplePage.getOrderDraftSimpleList().size(); i++) {
            if (draftSimplePage.getOrderDraftSimpleList().get(i).getSolutionStatus() != 4) {
                offlineList.add(draftSimplePage.getOrderDraftSimpleList().get(i));
                draftSimplePage.getOrderDraftSimpleList().remove(i);
                i--;
            }
        }
        if (CollectionUtils.isNotEmpty(offlineList)) {
            draftSimplePage.getOrderDraftSimpleList().addAll(offlineList);
        }
    }

    /**
     * 获取锁价倒计时（秒）
     *
     * @param lockPriceExpireTime
     * @return
     */
    private Integer getPriceLockCount(Date lockPriceExpireTime) {
        if (lockPriceExpireTime != null) {
            return (int) ((new Date().getTime() - lockPriceExpireTime.getTime()) / 1000);
        } else {
            return null;
        }

    }

    private Map<String, Object> concurrentQueryList(Map<String, Object> params) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return homeCardWcmProxy.queryDraftList(params);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_DRAFT_LIST.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return productProgramOrderProxy.querySolutionInfo((Integer) params.get("orderNum"));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return productProgramOrderProxy.queryAppOrderBaseInfo((Integer) params.get("orderNum"));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_APP_ORDER_BASE_INFO.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 设置方案状态
     *
     * @param draftSimpleRequestPage
     */
    private void setSoutionStatus(DraftSimpleRequestPage draftSimpleRequestPage, SolutionInfo solutionInfo) {
        List<Integer> solutionList = new ArrayList<>();
        if (draftSimpleRequestPage != null && CollectionUtils.isNotEmpty(draftSimpleRequestPage.getOrderDraftSimpleList())) {
            draftSimpleRequestPage.getOrderDraftSimpleList().forEach(draftSimpleRequest -> {
                solutionList.add(draftSimpleRequest.getSolutionId());
                if (CollectionUtils.isNotEmpty(draftSimpleRequest.getSolutionIdList())) {
                    solutionList.addAll(draftSimpleRequest.getSolutionIdList());
                }

            });
        }
        if (solutionInfo != null) {
            solutionList.add(solutionInfo.getSolutionId());
        }
        if (CollectionUtils.isNotEmpty(solutionList)) {
            BatchSolutionBaseInfoVo batchSolutionBaseInfoVo = productProgramProxy.batchQuerySolutionBaseInfo(solutionList.stream().distinct().collect(Collectors.toList()));
            if (batchSolutionBaseInfoVo != null && CollectionUtils.isNotEmpty(batchSolutionBaseInfoVo.getSolutionBaseInfoList())) {
                batchSolutionBaseInfoVo.getSolutionBaseInfoList().forEach(solutionBaseInfoVo -> {
                    if (CollectionUtils.isNotEmpty(draftSimpleRequestPage.getOrderDraftSimpleList())) {
                        draftSimpleRequestPage.getOrderDraftSimpleList().forEach(draftSimpleRequest -> {
                            if (draftSimpleRequest.getSolutionId().equals(solutionBaseInfoVo.getSolutionId()) || CollectionUtils.isNotEmpty(draftSimpleRequest.getSolutionIdList()) &&
                                    draftSimpleRequest.getSolutionIdList().contains(solutionBaseInfoVo.getSolutionId())) {
                                //纯软装不展示'2018年8月前的设计方案，不支持加入对比'
                                if (solutionBaseInfoVo.getStandardHardSolutionFlag() == 0) {
                                    draftSimpleRequest.setStandardHardSolutionFlag(solutionBaseInfoVo.getDecorationType() == 1 ? 1 : solutionBaseInfoVo.getStandardHardSolutionFlag());
                                }
                                if (draftSimpleRequest.getDraftSignStatus() != 1 && solutionBaseInfoVo.getSolutionStatus() != 4) {
                                    draftSimpleRequest.setSolutionStatus(solutionBaseInfoVo.getSolutionStatus());
                                }
                            }
                        });
                    }
                    if (solutionInfo != null) {
                        if (solutionInfo != null && solutionBaseInfoVo.getSolutionId().equals(solutionInfo.getSolutionId())) {
                            //纯软装不展示'2018年8月前的设计方案，不支持加入对比'
                            if (solutionBaseInfoVo.getStandardHardSolutionFlag() == 0) {
                                solutionInfo.setStandardHardSolutionFlag(solutionBaseInfoVo.getDecorationType() == 1 ? 1 : solutionBaseInfoVo.getStandardHardSolutionFlag());
                            }
                        }
                        ;
                    }
                    if (solutionInfo != null) {
                        if (solutionInfo != null && solutionBaseInfoVo.getSolutionId().equals(solutionInfo.getSolutionId())) {
                            //纯软装不展示'2018年8月前的设计方案，不支持加入对比'
                            if (solutionBaseInfoVo.getStandardHardSolutionFlag() == 0) {
                                solutionInfo.setStandardHardSolutionFlag(solutionBaseInfoVo.getDecorationType() == 1 ? 1 : solutionBaseInfoVo.getStandardHardSolutionFlag());
                            }
                        }
                    }
                });
            }
        }
    }


    /**
     * 价格比较接口
     *
     * @param request
     * @return
     */
    @Override
    public boolean compareOrderToDraft(QueryDraftRequest request) {
        AppOrderBaseInfoResponseVo orderInfo = productProgramOrderProxy.queryAppOrderBaseInfo(request.getOrderId());
        BigDecimal totalAmount = orderInfo.getOrderTotalAmount() == null ? new BigDecimal(0) : orderInfo.getOrderTotalAmount().setScale(0, BigDecimal.ROUND_HALF_UP);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("draftId", request.getDraftId());
        DraftInfoResponse solutionDraftResponse = homeCardWcmProxy.queryDraftInfo(params);
        if (solutionDraftResponse != null && solutionDraftResponse.getDraftContent() != null) {
            Integer totalPrice = solutionDraftResponse.getDraftContent().getTotalPrice();
            if (totalAmount.equals(new BigDecimal(totalPrice))) {
                return true;
            }
        }
        return false;
    }


    /**
     * 根据草稿ID，查询下单参数
     *
     * @param request
     * @return
     */
    @Override
    public CreateFamilyOrderRequest queryOrderParam(FamilyOrderRequest request) {
        if (request == null) {
            LOG.info("ProductProgramOrderController.createFamilyOrderByDraft all params is Empty ");
            throw new BusinessException(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        CreateOrderResponse orderResponse = new CreateOrderResponse();
        // 判断是否登陆
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null || StringUtils.isBlank(userDto.getMobile())) {
            LOG.info("ProductProgramOrderController.createFamilyOrderByDraft userDto is Empty");
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("draftId", request.getDraftId());
        DraftInfoResponse solutionDraftResponse = homeCardWcmProxy.queryDraftInfo(params);
        CreateFamilyOrderRequest orderRequest = new CreateFamilyOrderRequest();
        //组装下单参数..........
        setOrderRequest(orderRequest, solutionDraftResponse);
        // 特定用户，可以下单
        orderRequest.setUserId(userDto.getId());
        orderRequest.setMobileNum(userDto.getMobile());
        orderRequest.setHouseId(request.getHouseId());
        return orderRequest;

    }

    /**
     * 保存草稿并下单
     *
     * @param request
     * @return
     */
    @Override
    public CreateOrderResponse createDraftAndOrder(CreateOrderAndDraftRequest request) {

        if (request == null) {
            LOG.info("ProductProgramOrderController.createFamilyOrderByDraft all params is Empty ");
            throw new BusinessException(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }


        CreateOrderResponse orderResponse = new CreateOrderResponse();
        // 判断是否登陆
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null || StringUtils.isBlank(userDto.getMobile())) {
            LOG.info("ProductProgramOrderController.createFamilyOrderByDraft userDto is Empty");
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        request.setUserId(userDto.getId());
        log.info("draftContentBean.needDrawRoomList:" + JSON.toJSONString(request.getDraftContent().getNeedDrawRoomList()));
        //组装草稿数据
        Map<String, Object> params = createDraft(request);

        String draftId = null;
        if (request.getDraftProfileNum() == null) {
            //新增草稿
            draftId = homeCardWcmProxy.addDraft(params);
        } else {
            //更新草稿
            params.put("draftProfileNum", request.getDraftProfileNum());
            draftId = homeCardWcmProxy.updateDraft(params);
        }

        if (draftId == null) {
            throw new BusinessException(HttpReturnCode.WCM_FAILED, MessageConstant.FAILED);
        }
        params.put("draftId", draftId);
        DraftInfoResponse solutionDraftResponse = homeCardWcmProxy.queryDraftInfo(params);
        CreateFamilyOrderRequest orderRequest = new CreateFamilyOrderRequest();
        //组装下单参数..........
        setOrderRequest(orderRequest, solutionDraftResponse);
        params.put("orderNum", solutionDraftResponse.getOrderNum());
        params.put("draftProfileNum", solutionDraftResponse.getDraftProfileNum());
        params.put("userId", userDto.getId());


        // 特定用户，可以下单
        orderRequest.setUserId(userDto.getId());
        orderRequest.setMobileNum(userDto.getMobile());
        orderRequest.setHouseId(request.getHouseId());
        AladdinCreateOrderResponseVo result;

        SolutionInfo solutionInfo = productProgramOrderProxy.querySolutionInfo(request.getOrderId());

        if (solutionInfo == null) {//新建订单
            LOG.info("ProductProgramOrderController.createFamilyOrder params:" + JsonUtils.obj2json(orderRequest));
            result = orderService.createFamilyOrder(orderRequest);
        } else {//更新订单
            orderRequest.setHouseId(orderRequest.getHouseId() == null ? 1 : orderRequest.getHouseId());
            LOG.info("ProductProgramOrderController.updateFamilyOrder params:" + JsonUtils.obj2json(orderRequest));
            result = orderService.updateFamilyOrder(orderRequest);
        }

        //下单价格异步校验
        if (result != null && result.getContractAmount() != null) {
            params.put("totalPrice", result.getContractAmount());
            params.put("draftLatestTotalPrice", request.getDraftLatestTotalPrice());
            params.put("mobile", userDto.getMobile());
            setSignInfoList(orderResponse, params);
            //异步价格校验
            if (request.getDraftLatestTotalPrice().subtract((BigDecimal) params.get("totalPrice")).compareTo(BigDecimal.ZERO) != 0) {
                String message = "手机号：" + params.get("mobile") + "，订单号：" + params.get("orderNum") +
                        "，草稿ID：" + params.get("draftId");

                aiDingTalk.asynSendCheckPriceDingTalk("ProductProgramOrderServiceImpl.CheckPrice", message
                        , request.getDraftLatestTotalPrice(), result.getContractAmount());
            }

        }
        if (result != null && result.getOrderNum() != null) {
            orderResponse.setContractAmount(result.getContractAmount());
            orderResponse.setHouseId(result.getCustomerHouseId());
            orderResponse.setOrderId(result.getOrderNum());
        } else if (result != null) {
            String msg = OrderErrorCodeEnum.getShowMsgByCode(result.getCode() == null ? 2 : result.getCode());
            throw new BusinessException(msg);
        } else {
            throw new BusinessException(OrderErrorCodeEnum.getShowMsgByCode(2));
        }
        if (request.getDraftContent() != null && request.getDraftContent().getSolutionSelected() != null) {
            setOnceReplaceCach(request.getOnceReplaceFlag(), request.getOrderId(), request.getDraftContent().getSolutionSelected().getSolutionId());
        }
        return orderResponse;

    }

    /**
     * 创建草稿
     *
     * @param request
     * @return
     */
    private Map<String, Object> createDraft(DraftInfoRequest request) {
        long node0 = System.currentTimeMillis();
        Map<String, Object> params = new HashMap<>();
        DraftInfoRequest.DraftContentBean draftContent = request.getDraftContent();
        draftContent.setHasAjustment(false);//将前端是否有调整字段置为false
//        handlerneedDrawRoomList(draftContent);
        params.put("draftContent", draftContent);
        params.put("draftSignStatus", request.getDraftSignStatus());
        params.put("orderNum", request.getOrderId());
        params.put("draftName", request.getDraftName());
        params.put("operationProgress", request.getDraftProgress());
        params.put("draftType", request.getDraftType() == null ? 0 : request.getDraftType());
        params.put("userId", request.getUserId());
        params.put("mobileNum", request.getMobileNum());
        //组装完整草稿数据,最小级为方案
        SolutionListRequest req = new SolutionListRequest();
        req.setApartmentId(draftContent.getHouseTypeId());
        AppOrderBaseInfoResponseVo orderInfo = productProgramOrderProxy.queryAppOrderBaseInfo(request.getOrderId());
        if (orderInfo != null && orderInfo.getLayoutId() != null) {
            req.setApartmentId(orderInfo.getLayoutId());
        }
        req.setOrderId(request.getOrderId());
        req.setSolutionId(draftContent.getSolutionSelected().getSolutionId().longValue());
        SolutionEffectResponse solutionEffectResponse = productProgramService.querySolutionDescList(req);
        List<Integer> spaceIdList = new ArrayList<>();
        if (solutionEffectResponse == null) {
            throw new BusinessException("方案明细信息获取失败！");
        }
        solutionEffectResponse.getSolutionEffectInfoList().forEach(solutionEffectInfo -> {
            if ((solutionEffectInfo.getSolutionId()).equals(draftContent.getSolutionSelected().getSolutionId())) {
                draftContent.getSpaceDesignSelected().forEach(spaceDesignSelectedBean -> spaceIdList.add(spaceDesignSelectedBean.getSelected().getSpaceDesignId()));
                //获取主线程信息

                Map<String, Object> stringObjectMap = concurrentQuerySkuList(spaceIdList, request);
                //软装sku 解析数据
                List<OptionalSkusResponseVo> optionalSkusResponseVoList = (List<OptionalSkusResponseVo>) stringObjectMap.get(ConcurrentTaskEnum.QUERY_SOFT_ITEM_BY_ROOM.name());

                // 硬装sku以及可替换项 解析
                List<SpaceDesignVo> spaceDesignVoList = (List<SpaceDesignVo>) stringObjectMap.get(ConcurrentTaskEnum.QUERY_HARD_ITEM_BY_ROOM.name());

                if (request.getDraftContent().getSolutionSelected().getReformFlag() != null && request.getDraftContent().getSolutionSelected().getReformFlag() == 1) {
                    ServiceItemResponse serviceItemResponse = (ServiceItemResponse) stringObjectMap.get(ConcurrentTaskEnum.QUERY_SOLUTION_SERVICE.name());
                    if (serviceItemResponse != null) {
                        draftContent.getSolutionSelected().setSolutionGraphicDesignUrl(AliImageUtil.imageCompress(serviceItemResponse.getSolutionGraphicDesignUrl(), 1, 750, ImageConstant.SIZE_MIDDLE));
                        draftContent.getSolutionSelected().setApartmentUrl(AliImageUtil.imageCompress(serviceItemResponse.getApartmentUrl(), 1, 750, ImageConstant.SIZE_MIDDLE));
                        draftContent.getSolutionSelected().setReformApartmentUrl(AliImageUtil.imageCompress(serviceItemResponse.getReformApartmentUrl(), 1, 750, ImageConstant.SIZE_MIDDLE));
                        draftContent.getSolutionSelected().setServiceItemList(serviceItemResponse.getServiceItemList());
                    }
                }

                setSoftHardDefault(draftContent, solutionEffectInfo, optionalSkusResponseVoList, spaceDesignVoList);
                this.handlerOffLineTaskRoom(draftContent);
            }
        });

        return params;
    }

    private void handlerOffLineTaskRoom(DraftInfoRequest.DraftContentBean draftContent) {
        draftContent.getNeedDrawRoomList().clear();
        if (CollectionUtils.isNotEmpty(draftContent.getSpaceDesignSelected())) {
            draftContent.getSpaceDesignSelected().parallelStream().forEach(spaceDesignSelectedBean -> {
                QuerySkuVisiblePicListRequest querySkuVisiblePicListRequest = this.packRoomReplaceResult(spaceDesignSelectedBean);
                if (CollectionUtils.isNotEmpty(querySkuVisiblePicListRequest.getReplaceHardSkuIds())||CollectionUtils.isNotEmpty(querySkuVisiblePicListRequest.getSkuIdList())) {
                    List<String> list = visualFurnitureMatchingProxy.spaceImgs(querySkuVisiblePicListRequest);
                    if (CollectionUtils.isEmpty(list)) {
                        draftContent.getNeedDrawRoomList().add(spaceDesignSelectedBean.getRoomId());
                    }
                }
            });
        }
    }

    private QuerySkuVisiblePicListRequest packRoomReplaceResult(DraftInfoRequest.DraftContentBean.SpaceDesignSelectedBean spaceDesignSelectedBean) {
        List<Integer> supportDrawSoftCategoryList = ProductCategoryConstant.EIGHT_BIG_CATEGORY_ID_LIST;//软装离线渲染分类
        List<Integer> supportDrawHardCategoryList = Lists.newArrayList(supportDrawHardCategory.split(","))
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());//硬装离线渲染分类

        QuerySkuVisiblePicListRequest querySkuVisiblePicListRequest = new QuerySkuVisiblePicListRequest();
        querySkuVisiblePicListRequest.setRoomId(spaceDesignSelectedBean.getRoomId());
        querySkuVisiblePicListRequest.setSolutionId(spaceDesignSelectedBean.getSelected().getSolutionId());
        List<QuerySkuVisiblePicListRequest.ReplaceSkuInfoVo> replaceHardSkuIds = Lists.newArrayList();//硬装替换过的支持离线渲染的sku
        List<Integer> replaceSoftSkuIds = Lists.newArrayList();//软装替换过的支持离线渲染的sku
        //处理硬装替换项
        if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getHardItemList())) {
            spaceDesignSelectedBean.getHardItemList().forEach(hardItemListBean -> {
                if (hardItemListBean != null && supportDrawHardCategoryList.contains(hardItemListBean.getHardItemId())) {
                    if (hardItemListBean.getHardItemSelected() != null) {
                        if (hardItemListBean.getHardItemDefault() == null || !hardItemListBean.getHardItemDefault().getHardSelectionId().equals(hardItemListBean.getHardItemSelected().getHardSelectionId())) {
                            QuerySkuVisiblePicListRequest.ReplaceSkuInfoVo replaceSkuInfoVo = new QuerySkuVisiblePicListRequest.ReplaceSkuInfoVo();
                            replaceSkuInfoVo.setReplaceSkuId(hardItemListBean.getHardItemSelected().getHardSelectionId());
                            if (hardItemListBean.getHardItemSelected().getProcessSelected() != null) {
                                replaceSkuInfoVo.setReplaceCraftId(hardItemListBean.getHardItemSelected().getProcessSelected().getProcessId());
                                replaceHardSkuIds.add(replaceSkuInfoVo);
                            }
                        }
                    }
                }
            });
        }
        //处理软装替换项
        if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getSoftResponseList())) {
            spaceDesignSelectedBean.getSoftResponseList().forEach(softResponseListBean -> {
                if (supportDrawSoftCategoryList.contains(softResponseListBean.getLastCategoryId())) {
                    if (softResponseListBean.getFurnitureSelected() != null) {
                        if (softResponseListBean.getFurnitureDefault() == null || !softResponseListBean.getFurnitureSelected().getSkuId().equals(softResponseListBean.getFurnitureDefault().getSkuId())) {
                            replaceSoftSkuIds.add(softResponseListBean.getFurnitureSelected().getSkuId());
                        }
                    }
                }
            });
        }
        querySkuVisiblePicListRequest.setReplaceHardSkuIds(replaceHardSkuIds);
        querySkuVisiblePicListRequest.setSkuIdList(replaceSoftSkuIds);
        return querySkuVisiblePicListRequest;
    }


    private void handlerneedDrawRoomList(DraftInfoRequest.DraftContentBean draftContent) {
        if (CollectionUtils.isNotEmpty(draftContent.getNeedDrawRoomList())) {
            RoomIdRelationDto roomIdRelationDto = productProgramOrderProxy.batchQueryRoomIdRelation(draftContent.getNeedDrawRoomList());
            if (CollectionUtils.isNotEmpty(roomIdRelationDto.getRoomIdRelationList())) {
                roomIdRelationDto.getRoomIdRelationList().forEach(roomIdRelation -> {
                    if (roomIdRelation.getDrRoomId() == null || roomIdRelation.getDrRoomId().equals(0)) {
                        draftContent.getNeedDrawRoomList().remove(roomIdRelation.getBetaRoomId());
                    }
                });
            }
        }
    }

    /**
     * 根据草稿id下单
     *
     * @param request
     * @param req
     * @return
     */
    @Override
    public CreateOrderResponse createFamilyOrderByDraft(FamilyOrderRequest request, HttpServletRequest req) {
        if (request == null) {
            LOG.info("ProductProgramOrderController.createFamilyOrderByDraft all params is Empty ");
            throw new BusinessException(HttpReturnCode.O2O_PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        if (request.getCustomerHouseId() == null) {
            request.setCustomerHouseId(request.getHouseId());
        }
        CreateOrderResponse orderResponse = new CreateOrderResponse();
        // 判断是否登陆
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null || StringUtils.isBlank(userDto.getMobile())) {
            LOG.info("ProductProgramOrderController.createFamilyOrderByDraft userDto is Empty");
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("draftId", request.getDraftId());

        DraftInfoResponse solutionDraftResponse = homeCardWcmProxy.queryDraftInfo(params);

        CreateFamilyOrderRequest orderRequest = new CreateFamilyOrderRequest();
        //组装下单参数..........
        setOrderRequest(orderRequest, solutionDraftResponse);
        // 特定用户，可以下单
        orderRequest.setUserId(userDto.getId());
        orderRequest.setMobileNum(userDto.getMobile());
        orderRequest.setHouseId(request.getHouseId());
        AladdinCreateOrderResponseVo result;

        if (request.getType() == 1) {//新建订单
            LOG.info("ProductProgramOrderController.createFamilyOrder params:" + JsonUtils.obj2json(orderRequest));
            result = orderService.createFamilyOrder(orderRequest);
        } else {//更新订单
            orderRequest.setHouseId(orderRequest.getHouseId() == null ? 1 : orderRequest.getHouseId());
            LOG.info("ProductProgramOrderController.updateFamilyOrder params:" + JsonUtils.obj2json(orderRequest));
            result = orderService.updateFamilyOrder(orderRequest);
        }
        //下单价格异步校验
        if (result != null && result.getContractAmount() != null) {
            params.put("orderNum", solutionDraftResponse.getOrderNum());
            params.put("draftProfileNum", solutionDraftResponse.getDraftProfileNum());
            params.put("totalPrice", result.getContractAmount());
            setSignInfoList(orderResponse, params);
            if (request.getDraftLatestTotalPrice().compareTo(result.getContractAmount()) != 0) {
                params.put("draftLatestTotalPrice", request.getDraftLatestTotalPrice());
                String message = "手机号：" + userDto.getMobile() + "，订单号：" + params.get("orderNum") +
                        "，草稿编号：" + solutionDraftResponse.getDraftProfileNum();

                aiDingTalk.asynSendCheckPriceDingTalk("ProductProgramOrderServiceImpl.CheckPrice", message
                        , request.getDraftLatestTotalPrice(), result.getContractAmount());
            }
        }
        if (result != null && result.getOrderNum() != null) {
            orderResponse.setContractAmount(result.getContractAmount());
            orderResponse.setHouseId(result.getCustomerHouseId());
            orderResponse.setCustomerHouseId(result.getCustomerHouseId());
            orderResponse.setOrderId(result.getOrderNum());
        } else if (result != null) {
            String msg = OrderErrorCodeEnum.getShowMsgByCode(result.getCode() == null ? 2 : result.getCode());
            throw new BusinessException(msg);
        } else {
            throw new BusinessException(OrderErrorCodeEnum.getShowMsgByCode(2));
        }
        return orderResponse;
    }


    /**
     * 设置一键替换缓存
     *
     * @param request
     * @param solutionDraftResponse
     */
    private void setOnceReplaceCach(Integer onceReplaceFlag, Integer orderId, Integer solutionId) {
        try {
            //保存成功设置缓存
            if (onceReplaceFlag != null && onceReplaceFlag == 1) {//进行了免费赠品的替换动作
                RedisUtil.set(RedisKey.OrderSolution.APP_ONCE_REPLACE_KEY + orderId + ":" +
                        solutionId, JSON.toJSONString(true), 0);
            }
        } catch (Exception e) {
            LOG.error("set onceReplaceCach o2o-exception , more info :", e);
        }
    }

    /**
     * 修改草稿签约状态，并设置signInfoList
     *
     * @param orderResponse
     * @param params
     */
    private void setSignInfoList(CreateOrderResponse orderResponse, Map<String, Object> params) {
        Map<String, Object> resultMap = concurrentQuerySummaryInfo(params);
        OrderDetailDto orderDetailDto = (OrderDetailDto) resultMap.get(ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name());
        FamilyOrderPayResponse familyOrderPayResponse = queryPayBaseInfo(orderDetailDto);
        List<String> refundDescList = new ArrayList();
        if (isFinalAndPaidAmountEmpty(familyOrderPayResponse) &&
                familyOrderPayResponse.getFinalOrderPrice().getValue().compareTo(familyOrderPayResponse.getPaidAmount()) <= 0) {//已付清全款
            orderResponse.setAllMoney(1);
            KeywordListResponseVo listResponseVo = (KeywordListResponseVo) resultMap.get(ConcurrentTaskEnum.QUERY_ALL_MONEY_INFO.name());
            if (listResponseVo != null && CollectionUtils.isNotEmpty(listResponseVo.getKeywordList())) {
                List<KeywordVo> keywordList = listResponseVo.getKeywordList();
                for (KeywordVo keywordVo : keywordList) {
                    if (keywordVo != null) {
                        List<String> words = keywordVo.getWords();
                        for (String str : words) {
                            refundDescList.add(str);
                        }
                    }
                }
            }
        } else {//未付清全款
            KeywordListResponseVo listResponseVo = (KeywordListResponseVo) resultMap.get(ConcurrentTaskEnum.QUERY_UNALL_MONEY_INFO.name());
            if (listResponseVo != null && CollectionUtils.isNotEmpty(listResponseVo.getKeywordList())) {
                List<KeywordVo> keywordList = listResponseVo.getKeywordList();
                for (KeywordVo keywordVo : keywordList) {
                    if (keywordVo != null) {
                        List<String> words = keywordVo.getWords();
                        for (String str : words) {
                            refundDescList.add(str);
                        }
                    }
                }
            }
        }
        orderResponse.setSignInfoList(refundDescList);
    }

    /**
     * 判断订单金额和已付金额是否为空
     *
     * @param familyOrderPayResponse
     * @return
     */
    private boolean isFinalAndPaidAmountEmpty(FamilyOrderPayResponse familyOrderPayResponse) {
        return familyOrderPayResponse != null && familyOrderPayResponse.getFinalOrderPrice() != null && familyOrderPayResponse.getFinalOrderPrice().getValue() != null
                && familyOrderPayResponse.getFinalOrderPrice().getValue().compareTo(BigDecimal.ZERO) != 0 && familyOrderPayResponse.getPaidAmount() != null;
    }


    /**
     * 签约动作后续查询
     *
     * @param params
     * @return
     */
    private Map<String, Object> concurrentQuerySummaryInfo(Map<String, Object> params) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return orderProxy.queryOrderSummaryInfo((Integer) params.get("orderNum"));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return homeCardWcmProxy.signDraft(params);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SIGN_DRFAT_INFO.name();
            }
        });

        //已付清全款提交说明
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                List<String> refundList = new ArrayList();
                refundList.add(ProductProgramPraise.KEYWORD_ALL_MONEY_DESC);
                return keywordWcmProxy.getKeywordList(refundList);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ALL_MONEY_INFO.name();
            }
        });

        //未付清全款提交说明
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                List<String> refundList = new ArrayList();
                refundList.add(ProductProgramPraise.KEYWORD_UNALL_MONEY_DESC);
                return keywordWcmProxy.getKeywordList(refundList);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_UNALL_MONEY_INFO.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 收银台内容组装
     *
     * @param orderDetailDto
     * @return
     */
    private FamilyOrderPayResponse queryPayBaseInfo(OrderDetailDto orderDetailDto) {
        if (orderDetailDto != null) {
            FamilyOrderPayResponse response = new FamilyOrderPayResponse();
            response.setUpItemAmount(orderDetailDto.getUpItemAmount());//选配升级项金额
            response.setOrderStatus(FamilyOrderStatus.getOrderStatus(orderDetailDto.getOrderStatus()));
            response.setAdviserName(StringUtils.isBlank(orderDetailDto.getAdviserName()) ? null : orderDetailDto.getAdviserName());
            response.setAdviserMobile(StringUtils.isBlank(orderDetailDto.getAdviserMobile()) ? null : orderDetailDto.getAdviserMobile());
            response.setOrderSource(orderDetailDto.getSource());
            response.setGradeId(orderDetailDto.getGradeId());
            response.setGradeName(orderDetailDto.getGradeName());
            //其他优惠
            response.setOtherDisAmount(orderDetailDto.getNewOtherDisAmount() == null ? BigDecimal.ZERO : orderDetailDto.getNewOtherDisAmount());

            // 艾升级券面值
            response.setUpGradeCouponAmount(orderDetailDto.getUpGradeCouponAmount() == null ? BigDecimal.ZERO : orderDetailDto.getUpGradeCouponAmount());
            // 全品家立减金额
            response.setRightsDiscountAmount(orderDetailDto.getRightsDiscountAmount() == null ? BigDecimal.ZERO : orderDetailDto.getRightsDiscountAmount());

            //权益可抵扣金额，若已确认方案，则为已抵扣金额
            response.setRightAmount(orderDetailDto.getUpItemDeAmount() == null ? BigDecimal.ZERO : orderDetailDto.getUpItemDeAmount());

            //用户已付金额
            response.setPaidAmount(orderDetailDto.getFundAmount() == null ? BigDecimal.ZERO : orderDetailDto.getFundAmount());

            //实际合同额
            BigDecimal contractAmount = orderDetailDto.getContractAmount() == null ? BigDecimal.ZERO : orderDetailDto.getContractAmount();

            //已确认收款金额
            response.setConfirmReapAmount(orderDetailDto.getConfirmedAmount() == null ? BigDecimal.ZERO : orderDetailDto.getConfirmedAmount());

            //剩余应付
            BigDecimal restPay = BigDecimal.ZERO;

            //方案总价
            BigDecimal solutionPrice = BigDecimal.ZERO;

            if (IntegerUtil.isNullOrZero(orderDetailDto.getPreConfirmed())) {
                //方案未确认
                //app订单总价  = beta订单总价- 艾升级权益
                response.setFinalOrderPrice(CopyWriterAndValue.build(CopyWriterConstant.Order.ORDER_TOTAL_PRICE, orderDetailDto.getOrderTotalAmount() == null || orderDetailDto.getOrderTotalAmount().equals(BigDecimal.ZERO) ? BigDecimal.ZERO : orderDetailDto.getOrderTotalAmount().subtract(orderDetailDto.getUpItemDeAmount() == null ? BigDecimal.ZERO : orderDetailDto.getUpItemDeAmount())));
                //由于老订单一些返现活动导致订单总价和合同价不一致，取合同价计算会出现剩余应付错误，故此处修改为使用订单总价来计算，废除合同额
                contractAmount = response.getFinalOrderPrice().getValue();
                if (orderDetailDto.getOriginalOrderAmount() == null) {
                    //订单未记录原始方案价格，则原始方案价格视为实际合同额
                    solutionPrice = contractAmount;
                } else {
                    solutionPrice = orderDetailDto.getOriginalOrderAmount();
                }

                //则前端显示的抵扣后合同额为：实际合同额-权益可抵扣金额
                BigDecimal restContract = contractAmount.subtract(response.getRightAmount());
                response.setContractAmount(restContract);

                //用户剩余应付，确认方案前为：合同额-已付金额-权益抵扣，若<=0 则为0
                restPay = contractAmount.subtract(response.getPaidAmount()).subtract(response.getRightAmount());
            } else if (orderDetailDto.getPreConfirmed().equals(1)) {
                //app订单总价  = beta订单总价
                response.setFinalOrderPrice(CopyWriterAndValue.build(CopyWriterConstant.Order.ORDER_TOTAL_PRICE, orderDetailDto.getOrderTotalAmount() == null || orderDetailDto.getOrderTotalAmount().equals(BigDecimal.ZERO) ? BigDecimal.ZERO : orderDetailDto.getOrderTotalAmount()));
                //由于老订单一些返现活动导致订单总价和合同价不一致，取合同价计算会出现剩余应付错误，故此处修改为使用订单总价来计算，废除合同额
                contractAmount = response.getFinalOrderPrice().getValue();
                //方案已确认
                if (orderDetailDto.getOriginalOrderAmount() == null) {
                    //订单未记录原始方案价格，则原始方案价格视为实际合同额+权益已抵扣金额
                    solutionPrice = contractAmount.add(response.getRightAmount());

                } else {
                    solutionPrice = orderDetailDto.getOriginalOrderAmount();
                }

                //则前端展示的抵扣后合同额为：实际合同额
                response.setContractAmount(orderDetailDto.getContractAmount() == null ? BigDecimal.ZERO : orderDetailDto.getContractAmount());

                //用户剩余应付，确认方案后为：合同额-已支付金额
                restPay = contractAmount.subtract(response.getPaidAmount());

            }
            //3月款项优化后的剩余应付
            response.setNewRestAmount(response.getFinalOrderPrice().getValue().subtract(response.getPaidAmount()));
            if (response.getNewRestAmount().compareTo(BigDecimal.ZERO) < 0) {
                response.setNewRestAmount(BigDecimal.ZERO);
            }
            response.setContractAmount(response.getFinalOrderPrice().getValue());
            //原始方案价，若为空，则取合同金额
            response.setSolutionTotalPrice(solutionPrice);
            //方案总价
            response.setSolutionAmount(orderDetailDto.getNewSolutionAmount() == null ? BigDecimal.ZERO : orderDetailDto.getNewSolutionAmount());
            response.setRestAmount(restPay.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : restPay);

            response.setCanLoanAmount(response.getNewRestAmount() != null && response.getNewRestAmount().compareTo(BigDecimal.ZERO) > 0 ? response.getNewRestAmount() : UserConstants.CAN_LOAN_AMOUNT.subtract(response.getPaidAmount() == null ? BigDecimal.ZERO : response.getPaidAmount()));
            response.setCanLoanAmount(response.getCanLoanAmount().compareTo(BigDecimal.ZERO) >= 0 ? response.getCanLoanAmount() : BigDecimal.ZERO);
            return response;
        }
        return null;
    }


    /**
     * 下单查询多线程
     *
     * @param params
     * @return
     */
    private Map<String, Object> concurrentOrderQueryList(Map<String, Object> params) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return homeCardWcmProxy.queryDraftInfo(params);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_DRAFT_INFO.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return productProgramOrderProxy.querySolutionInfo((Integer) params.get("orderNum"));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 根据草稿组装下单参数
     *
     * @param orderRequest
     * @param draftInfoResponse
     */
    @Override
    public void setOrderRequest(CreateFamilyOrderRequest orderRequest, DraftInfoResponse draftInfoResponse) {
        List<DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean> spaceDesignSelected = draftInfoResponse.getDraftContent().getSpaceDesignSelected();
        List<Integer> roomIds = new ArrayList<>();
        List<Long> solutionIds = new ArrayList<>();
        List<AddBagCreateRequest> hardAddBagProducts = new ArrayList<>();
        List<RoomEffectImageDto> roomEffectImageDtos = new ArrayList<>();
        List<RoomReplaceProductDto> replaceProductDtos = new ArrayList<>();
        List<RoomReplaceHardProductDto> replaceHardProductDtos = new ArrayList<>();
        WholeRoomSpaceInfo wholeRoomSpaceInfo = new WholeRoomSpaceInfo();
        solutionIds.add((draftInfoResponse.getDraftContent().getSolutionSelected().getSolutionId().longValue()));
        List<WholeRoomSpaceDetail> wholeRoomRepalceList = new ArrayList<>();
        spaceDesignSelected.forEach(spaceDesign -> {
            if (CollectionUtils.isNotEmpty(spaceDesign.getSoftResponseList())) {
                spaceDesign.getSoftResponseList().forEach(softResponseListBean -> {
                    if ((softResponseListBean.getStatus() == null || softResponseListBean.getStatus().equals(0))) {
                        softResponseListBean.setStatus(0);
                        if (softResponseListBean.getFurnitureDefault() != null) {
                            softResponseListBean.setFurnitureSelected(softResponseListBean.getFurnitureDefault().clone());
                        } else if (softResponseListBean.getBomGroupDefault() != null) {
                            softResponseListBean.setBomGroupSelected(softResponseListBean.getBomGroupDefault());
                        } else if (softResponseListBean.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(softResponseListBean.getCabinetBomGroup().getReplaceBomList())) {
                            softResponseListBean.getCabinetBomGroup().getReplaceBomList().forEach(replaceBomDto -> {
                                if (replaceBomDto.getBomGroupSelect() == null) {
                                    replaceBomDto.setBomGroupSelect(replaceBomDto.getBomGroupDefault());
                                }
                            });
                        }
                    }
                });
            }
            if (CollectionUtils.isNotEmpty(spaceDesign.getHardItemList())) {
                spaceDesign.getHardItemList().forEach(hardItemListBean -> {
                    if ((hardItemListBean.getStatus() == null || hardItemListBean.getStatus().equals(0))) {
                        hardItemListBean.setStatus(0);
                        if (hardItemListBean.getHardItemDefault() != null) {
                            hardItemListBean.setHardItemSelected(hardItemListBean.getHardItemDefault().clone());
                        } else if (hardItemListBean.getHardBomGroupDefault() != null) {
                            hardItemListBean.setHardBomGroupSelect(hardItemListBean.getHardBomGroupDefault());
                        } else if (hardItemListBean.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(hardItemListBean.getCabinetBomGroup().getReplaceBomList())) {
                            hardItemListBean.getCabinetBomGroup().getReplaceBomList().forEach(replaceBomDto -> {
                                if (replaceBomDto.getBomGroupSelect() == null) {
                                    replaceBomDto.setBomGroupSelect(replaceBomDto.getBomGroupDefault());
                                }
                            });
                        }
                    }
                });
            }
            roomIds.add(spaceDesign.getSelected().getSpaceDesignId());
            RoomEffectImageDto roomImage = new RoomEffectImageDto();
            if (spaceDesign.getSelected().getRoomImage() != null) {
                roomImage.setReferenceOnlyFlag(spaceDesign.getSelected().getRoomImage().getReferenceOnlyFlag());
                roomImage.setTaskType(spaceDesign.getSelected().getRoomImage().getType());
            }
            roomImage.setRoomId(spaceDesign.getSelected().getSpaceDesignId());
            if (spaceDesign.getSelected().getRoomImage() != null) {
                if (spaceDesign.getSelected().getRoomImage().getTaskStatus() != null && !spaceDesign.getSelected().getRoomImage().getTaskStatus().equals(0)) {
                    if (spaceDesign.getSelected().getRoomImage().getTaskStatus().equals(3)) {
                        //渲染任务已经完成
                        roomImage.setNewPictureDto(new OffLineMessageDto(spaceDesign.getSelected().getRoomImage().getTaskId(),
                                spaceDesign.getSelected().getRoomImage().getPictureList().stream().map(url -> AliImageUtil.getImageDeCompress(url)).collect(Collectors.toList())));
                        roomImage.setPictureUrls(spaceDesign.getSelected().getRoomImage().getPictureList().stream().map(url -> AliImageUtil.getImageDeCompress(url)).collect(Collectors.toList()));
                    } else if (spaceDesign.getSelected().getRoomImage().getTaskStatus().equals(2)) {
                        roomImage.setNewPictureDto(new OffLineMessageDto(spaceDesign.getSelected().getRoomImage().getTaskId(), Lists.newArrayList()));
                        if (CollectionUtils.isNotEmpty(spaceDesign.getSelected().getRoomImage().getPictureList())) {
                            roomImage.setPictureUrls(spaceDesign.getSelected().getRoomImage().getPictureList().stream().map(url -> AliImageUtil.getImageDeCompress(url)).collect(Collectors.toList()));
                        } else {
                            if (CollectionUtils.isNotEmpty(spaceDesign.getSelected().getRoomImage().getOldPictureList())) {
                                roomImage.setPictureUrls(spaceDesign.getSelected().getRoomImage().getOldPictureList().stream().map(url -> AliImageUtil.getImageDeCompress(url)).collect(Collectors.toList()));
                            }
                        }
                    } else {
                        if (CollectionUtils.isNotEmpty(spaceDesign.getSelected().getRoomImage().getPictureList())) {
                            roomImage.setPictureUrls(spaceDesign.getSelected().getRoomImage().getPictureList().stream().map(url -> AliImageUtil.getImageDeCompress(url)).collect(Collectors.toList()));
                        } else if (CollectionUtils.isNotEmpty(spaceDesign.getSelected().getRoomImage().getOldPictureList())) {
                            roomImage.setPictureUrls(spaceDesign.getSelected().getRoomImage().getOldPictureList().stream().map(url -> AliImageUtil.getImageDeCompress(url)).collect(Collectors.toList()));
                        }
                    }
                } else {
                    if (CollectionUtils.isNotEmpty(spaceDesign.getSelected().getRoomImage().getPictureList())) {
                        if (spaceDesign.getSelected().getRoomImage().getTaskId() != null && !spaceDesign.getSelected().getRoomImage().getTaskId().equals(0)) {
                            roomImage.setNewPictureDto(new OffLineMessageDto(spaceDesign.getSelected().getRoomImage().getTaskId(),
                                    spaceDesign.getSelected().getRoomImage().getPictureList().stream().map(url -> AliImageUtil.getImageDeCompress(url)).collect(Collectors.toList())));
                        } else {
                            roomImage.setPictureUrls(spaceDesign.getSelected().getRoomImage().getPictureList().stream().map(url -> AliImageUtil.getImageDeCompress(url)).collect(Collectors.toList()));
                        }
                    }
                    if (CollectionUtils.isEmpty(spaceDesign.getSelected().getRoomImage().getPictureList()) && CollectionUtils.isNotEmpty(spaceDesign.getSelected().getRoomImage().getOldPictureList())) {
                        roomImage.setPictureUrls(spaceDesign.getSelected().getRoomImage().getOldPictureList().stream().map(url -> AliImageUtil.getImageDeCompress(url)).collect(Collectors.toList()));
                    }
                }
            } else {
                List<String> pictureUrls = new ArrayList<>();
                pictureUrls.add(AliImageUtil.getImageDeCompress(spaceDesign.getSelected().getHeadImage()));
                roomImage.setPictureUrls(pictureUrls);
            }
            roomEffectImageDtos.add(roomImage);
            List<DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean> softResponseList = spaceDesign.getSoftResponseList();
            RoomReplaceProductDto roomReplace = new RoomReplaceProductDto();
            roomReplace.setRoomId(spaceDesign.getSelected().getSpaceDesignId());
            //替换商品信息
            List<ReplaceProductRequest> productDtos = new ArrayList<>();
            List<BomReplaceRequest> bomReplaceRequest = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(softResponseList)) {
                softResponseList.forEach(e -> {
                    if (e.getFurnitureDefault() != null && !e.getFurnitureDefault().getSkuId().equals(e.getFurnitureSelected().getSkuId())) {//正常软装
                        ReplaceProductRequest replaceProductRequest = new ReplaceProductRequest()
                                .setSkuId(e.getFurnitureDefault().getSkuId())
                                .setNewSkuId(e.getFurnitureSelected().getSkuId())
                                .setFurnitureType(e.getFurnitureSelected().getFurnitureType() == null ?
                                        e.getFurnitureDefault().getFurnitureType() : e.getFurnitureSelected().getFurnitureType());
                        productDtos.add(replaceProductRequest);
                    } else if (e.getBomGroupDefault() != null && !e.getBomGroupDefault().getGroupId().equals(e.getBomGroupSelected().getGroupId())) {//bom软装
                        BomReplaceRequest bomReplaceBean = new BomReplaceRequest()
                                .setGroupId(e.getBomGroupDefault().getGroupId())
                                .setNewGroupId(e.getBomGroupSelected().getGroupId())
                                .setCabinetType(e.getBomGroupDefault().getCabinetType())
                                .setCabinetTypeName(e.getBomGroupSelected().getCabinetTypeName())
                                .setPositionIndex(e.getBomGroupSelected().getPositionIndex())
                                .setFurnitureType(e.getBomGroupDefault().getFurnitureType());
                        bomReplaceRequest.add(bomReplaceBean);
                    } else if (e.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(e.getCabinetBomGroup().getReplaceBomList())) {
                        for (ReplaceBomDto replaceBomDto : e.getCabinetBomGroup().getReplaceBomList()) {
                            BomReplaceRequest bomReplaceBean = new BomReplaceRequest()
                                    .setGroupId(replaceBomDto.getBomGroupDefault().getGroupId())
                                    .setNewGroupId(replaceBomDto.getBomGroupSelect().getGroupId())
                                    .setCabinetType(replaceBomDto.getBomGroupDefault().getCabinetType())
                                    .setCabinetTypeName(replaceBomDto.getBomGroupSelect().getCabinetTypeName())
                                    .setPositionIndex(replaceBomDto.getBomGroupSelect().getPositionIndex())
                                    .setFurnitureType(replaceBomDto.getBomGroupDefault().getFurnitureType());
                            bomReplaceRequest.add(bomReplaceBean);
                        }
                    }
                });
            }
            roomReplace.setProductDtos(productDtos);
            roomReplace.setReplaceBomDtos(bomReplaceRequest);
            replaceProductDtos.add(roomReplace);

            //空间硬装商品调整对象
            RoomReplaceHardProductDto roomReplaceHardProductDto = new RoomReplaceHardProductDto();
            roomReplaceHardProductDto.setRoomId(spaceDesign.getSelected().getSpaceDesignId());
            List<HardReplace> replaceHardProductDtoList = new ArrayList<>();

            //删除选配
            List<HardReplace> delHardProductDtoList = new ArrayList<>();

            List<HardReplace> addHardProductDtoList = new ArrayList<>();
            List<HardBomDto> addRoomBomDtoList = Lists.newArrayList();
            List<HardBomDto> replaceBomDtoList = Lists.newArrayList();
            List<DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean> hardItemList = spaceDesign.getHardItemList();
            if (CollectionUtils.isNotEmpty(hardItemList)) {
                hardItemList.forEach(e -> {
                    WholeRoomSpaceDetail wholeRoomSpaceDetail = new WholeRoomSpaceDetail();
                    List<HardReplace> wholeaddHardProductDtoList = new ArrayList<>();
                    HardReplace hardReplace = null;
                    DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean.HardItemBean hardItemSelected = e.getHardItemSelected();
                    DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.HardItemListBean.HardItemBean hardItemDefault = e.getHardItemDefault();
                    // 替换项
                    if (e.getStatus() == Constants.ITEM_STATUS_SELECTED && hardItemSelected != null && hardItemSelected.getProcessSelected() != null &&
                            hardItemDefault != null && (!hardItemSelected.getHardSelectionId().equals(hardItemDefault.getHardSelectionId()) ||
                            !hardItemSelected.getProcessSelected().getProcessId().equals(hardItemDefault.getProcessSelected().getProcessId()))) {
                        hardReplace = new HardReplace()
                                .setRoomClassId(e.getHardItemId())
                                .setSkuId(hardItemDefault.getHardSelectionId())
                                .setCraftId(hardItemDefault.getProcessSelected().getProcessId())
                                .setNewSkuId(hardItemSelected.getHardSelectionId())
                                .setNewCraftId(hardItemSelected.getProcessSelected().getProcessId());
                    } else if (e.getStatus() == Constants.ITEM_STATUS_SELECTED && e.getHardBomGroupSelect() != null && e.getHardBomGroupDefault() != null) {
                        replaceBomDtoList.add(new HardBomDto().setCabinetType(e.getHardBomGroupDefault().getCabinetType())
                                .setCabinetTypeName(e.getHardBomGroupDefault().getCabinetTypeName())
                                .setPositionIndex(e.getHardBomGroupSelect().getPositionIndex())
                                .setFurnitureType(e.getHardBomGroupDefault().getFurnitureType())
                                .setGroupId(e.getHardBomGroupDefault().getGroupId())
                                .setNewGroupId(e.getHardBomGroupSelect().getGroupId()));
                    } else if (e.getStatus() == Constants.ITEM_STATUS_SELECTED && e.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(e.getCabinetBomGroup().getReplaceBomList())) {
                        //定制柜替换项
                        for (ReplaceBomDto replaceBomDto : e.getCabinetBomGroup().getReplaceBomList()) {
                            replaceBomDtoList.add(new HardBomDto().setCabinetType(replaceBomDto.getBomGroupDefault().getCabinetType())
                                    .setCabinetTypeName(replaceBomDto.getBomGroupDefault().getCabinetTypeName())
                                    .setPositionIndex(replaceBomDto.getBomGroupSelect().getPositionIndex())
                                    .setFurnitureType(replaceBomDto.getBomGroupDefault().getFurnitureType())
                                    .setGroupId(replaceBomDto.getBomGroupDefault().getGroupId())
                                    .setNewGroupId(replaceBomDto.getBomGroupSelect().getGroupId()));
                        }
                    }
                    if (hardReplace != null) {
                        replaceHardProductDtoList.add(hardReplace);
                    }
                    //删除项
                    if (e.getStatus() == Constants.ITEM_STATUS_DELETE) {
                        HardReplace delHard = new HardReplace();
                        delHard.setRoomClassId(e.getHardItemId())
                                .setSkuId(hardItemSelected.getHardSelectionId())
                                .setCraftId(hardItemSelected.getProcessSelected().getProcessId());
                        delHardProductDtoList.add(delHard);
                    }

                    // 新增项
                    if (e.getStatus() == Constants.ITEM_STATUS_ADD) {
                        if (e.getHardBomGroupSelect() != null) {
                            addRoomBomDtoList.add(new HardBomDto().setGroupId(e.getHardBomGroupSelect().getGroupId()));
                        } else {
                            HardReplace addHard = new HardReplace();
                            if (isRoomWhole(spaceDesign) &&
                                    e.getHardItemSelected() != null && e.getHardItemSelected().getProcessSelected() != null) {//全屋
                                wholeRoomSpaceInfo.setRoomId(spaceDesign.getRoomId());
                                wholeRoomSpaceDetail.setRoomClassId(e.getHardItemId());
                                addHard.setRoomClassId(e.getHardItemId())
                                        .setNewSkuId(hardItemSelected.getHardSelectionId())
                                        .setNewCraftId(hardItemSelected.getProcessSelected().getProcessId())
                                        .setParentCraftId(0)
                                        .setParentSkuId(0);
                                if (e.getHardItemSelected().getProcessSelected().getSelectChildHardItem() != null
                                        && e.getHardItemSelected().getProcessSelected().getSelectChildHardItem().getHardSelectionId() > 0) {//选则无子集时，前端给了个负的HardSelectionId
                                    HardReplace addSonHard = new HardReplace();
                                    addSonHard.setRoomClassId(e.getHardItemId())
                                            .setNewSkuId(hardItemSelected.getProcessSelected().getSelectChildHardItem().getHardSelectionId())
                                            .setNewCraftId(hardItemSelected.getProcessSelected().getSelectChildHardItem().getProcessSelected().getProcessId())
                                            .setParentCraftId(hardItemSelected.getProcessSelected().getProcessId())
                                            .setParentSkuId(hardItemSelected.getHardSelectionId());
                                    wholeaddHardProductDtoList.add(addSonHard);
                                }


                                wholeaddHardProductDtoList.add(addHard);
                            } else {
                                addHard.setRoomClassId(e.getHardItemId());
                                addHard.setNewSkuId(hardItemSelected.getHardSelectionId());
                                addHard.setNewCraftId(hardItemSelected.getProcessSelected().getProcessId());
                                addHardProductDtoList.add(addHard);
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(wholeaddHardProductDtoList)) {
                        wholeRoomSpaceDetail.setAddHardProductDtoList(wholeaddHardProductDtoList);
                        wholeRoomRepalceList.add(wholeRoomSpaceDetail);
                    }
                });
            }
            roomReplaceHardProductDto.setReplaceHardProductDtoList(replaceHardProductDtoList)
                    .setDelHardProductDtoList(delHardProductDtoList)
                    .setAddHardProductDtoList(addHardProductDtoList).setReplaceBomDtos(replaceBomDtoList).setAddRoomBomDtos(addRoomBomDtoList);
            replaceHardProductDtos.add(roomReplaceHardProductDto);

        });
        if (CollectionUtils.isNotEmpty(wholeRoomRepalceList)) {
            wholeRoomSpaceInfo.setWholeRoomRepalceList(wholeRoomRepalceList);
            orderRequest.setWholeRoomSpaceInfo(wholeRoomSpaceInfo);
        }
        orderRequest.setRoomIds(roomIds);
        orderRequest.setRoomEffectImageDtos(roomEffectImageDtos);
        orderRequest.setHardAddBagProducts(hardAddBagProducts);
        orderRequest.setReplaceProductDtos(replaceProductDtos);
        orderRequest.setReplaceHardProductDtos(productProgramOrderService.checkAndReplaceHardRoomClassId(replaceHardProductDtos));
        orderRequest.setSolutionIds(solutionIds);
        orderRequest.setOrderId(draftInfoResponse.getOrderNum());
    }


    /**
     * 新建全品家订单
     *
     * @param request
     * @return
     */
    public CreateOrderResponse createFamilyOrder(HttpUserInfoRequest userDto, FamilyOrderRequest
            request, HttpServletRequest req, Integer flag) {
        CreateOrderResponse orderResponse = new CreateOrderResponse();
        AppOrderBaseInfoResponseVo appOrderBaseInfoResponseVo = productProgramOrderProxy.queryAppOrderBaseInfo(request.getOrderId());
        if (appOrderBaseInfoResponseVo == null) {
            throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.ORDER_NOT_EXITS);
        }
        // 特定用户，可以下单
        CreateFamilyOrderRequest orderRequest = null;
        if (flag.equals(1)) {
            orderRequest = new CreateFamilyOrderRequest();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("draftId", request.getDraftId());
            DraftInfoResponse draftInfoResponse = homeCardWcmProxy.queryDraftInfo(params);
            setOrderRequest(orderRequest, draftInfoResponse);
            // 特定用户，可以下单
            orderRequest.setUserId(userDto.getId());
            orderRequest.setMobileNum(userDto.getMobile());
            orderRequest.setOrderId(request.getOrderId());
        } else if (flag.equals(2)) {
            // 创建爱家贷
            orderRequest = productProgramService.createAiJiaLoanOrder(request.getOrderId(), userDto.getId(), request.getHouseId());
            orderRequest.setMobileNum(request.getMobileNum());
        } else {
            throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.NONSUPPORT_ORDER);
        }
        if (orderRequest == null) {
            throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.SUBMIT_APPLY_FAILED);
        }
        AladdinCreateOrderResponseVo result;
        if (request.getType() == 1) {//新建订单
            LOG.info("ProductProgramOrderController.createFamilyOrder params:" + JsonUtils.obj2json(orderRequest));
            result = orderService.createFamilyOrder(orderRequest);
        } else {//更新订单
            orderRequest.setHouseId(orderRequest.getHouseId() == null ? 1 : orderRequest.getHouseId());
            LOG.info("ProductProgramOrderController.updateFamilyOrder params:" + JsonUtils.obj2json(orderRequest));
            result = orderService.updateFamilyOrder(orderRequest);
        }

        if (result != null && result.getOrderNum() != null) {
            orderResponse.setContractAmount(result.getContractAmount());
            orderResponse.setHouseId(result.getCustomerHouseId());
            orderResponse.setOrderId(result.getOrderNum());
        } else if (result != null) {
            String msg = OrderErrorCodeEnum.getShowMsgByCode(result.getCode() == null ? 2 : result.getCode());
            throw new BusinessException(msg);
        } else {
            throw new BusinessException(OrderErrorCodeEnum.getShowMsgByCode(2));
        }
        return orderResponse;
    }

    /**
     * 床和床垫的尺寸校验
     *
     * @param draftContent
     */
    private void bedsAndMattressesCheck(DraftInfoResponse.DraftJsonStrBean draftContent) {
        List<Integer> bedLastCategoryIdList = ProductCategoryConstant.BED_LAST_CATEGORY_ID_LIST_NO_BUNK_BED;
        List<Integer> mattressLastCategoryIdList = ProductCategoryConstant.MATTRESS_LAST_CATEGORY_ID_LIST;
        draftContent.getSpaceDesignSelected().stream().forEach(spaceDesignSelectedBean -> {

            List<Integer> bedSkuList = Lists.newArrayList();
            List<Integer> mattressSkuList = Lists.newArrayList();
            List<Integer> bedSkuListDefault = Lists.newArrayList();
            List<Integer> mattressSkuListDefault = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(spaceDesignSelectedBean.getSoftResponseList())) {
                for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean softResponseListBean : spaceDesignSelectedBean.getSoftResponseList()) {
                    if (softResponseListBean.getLastCategoryId() != null && softResponseListBean.getFurnitureSelected() != null) {
                        if (bedLastCategoryIdList.contains(softResponseListBean.getLastCategoryId())) {
                            if (softResponseListBean.getFurnitureSelected() != null)
                                bedSkuList.add(softResponseListBean.getFurnitureSelected().getSkuId());
                            if (softResponseListBean.getFurnitureDefault() != null) ;
                            bedSkuListDefault.add(softResponseListBean.getFurnitureDefault().getSkuId());
                        } else if (mattressLastCategoryIdList.contains(softResponseListBean.getLastCategoryId())) {
                            if (softResponseListBean.getFurnitureSelected() != null) ;
                            mattressSkuList.add(softResponseListBean.getFurnitureSelected().getSkuId());
                            if (softResponseListBean.getFurnitureDefault() != null) ;
                            mattressSkuListDefault.add(softResponseListBean.getFurnitureDefault().getSkuId());
                        }
                    }
                }
                if (bedSkuList.size() == mattressSkuList.size() && mattressSkuList.size() == 1) {
                    Map<Integer, SkuBaseInfoDto.SkuExtPropertyInfo> integerSkuExtPropertyInfoMap = productProgramService.batchQuerySkuExtPropertyBySkuIdListAndPropertyType(bedSkuList, 4);
                    Map<Integer, SkuBaseInfoDto.SkuExtPropertyInfo> integerSkuExtPropertyInfoMapDefault = productProgramService.batchQuerySkuExtPropertyBySkuIdListAndPropertyType(bedSkuListDefault.stream().distinct().collect(Collectors.toList()), 4);
                    List<SkuBaseInfoDto> skuBaseInfoDtos = productProxy.batchQuerySkuBaseInfo(mattressSkuList);
                    List<SkuBaseInfoDto> skuBaseInfoDtosForBed = productProxy.batchQuerySkuBaseInfo(bedSkuList);
                    List<SkuBaseInfoDto> skuBaseInfoMattressDtosDefault = productProxy.batchQuerySkuBaseInfo(mattressSkuListDefault.stream().distinct().collect(Collectors.toList()));
                    Map<Integer, SkuBaseInfoDto> skuBaseInfoMattressDtosDefaultMap = null;
                    if (CollectionUtils.isNotEmpty(skuBaseInfoMattressDtosDefault)) {
                        skuBaseInfoMattressDtosDefaultMap = skuBaseInfoMattressDtosDefault.stream().collect(Collectors.toMap(o -> o.getSkuId(), o -> o));
                    }
                    Map<Integer, SkuBaseInfoDto> skuBaseInfoDtosForBedDefaultMap = null;
                    List<SkuBaseInfoDto> skuBaseInfoDtosForBedDefault = productProxy.batchQuerySkuBaseInfo(bedSkuListDefault.stream().distinct().collect(Collectors.toList()));
                    if (CollectionUtils.isNotEmpty(skuBaseInfoDtosForBedDefault)) {
                        skuBaseInfoDtosForBedDefaultMap = skuBaseInfoDtosForBedDefault.stream().collect(Collectors.toMap(o -> o.getSkuId(), o -> o));
                    }
                    if (CollectionUtils.isNotEmpty(skuBaseInfoDtos) && integerSkuExtPropertyInfoMap.get(bedSkuList.get(0)) != null) {
                        SkuBaseInfoDto skuBaseInfoDto = skuBaseInfoDtos.get(0);
                        SkuBaseInfoDto skuBaseInfoDtoForBed = skuBaseInfoDtosForBed.get(0);
                        SkuBaseInfoDto.SkuExtPropertyInfo skuExtPropertyInfo = integerSkuExtPropertyInfoMap.get(bedSkuList.get(0));
                        for (DraftInfoResponse.DraftJsonStrBean.SpaceDesignSelectedBean.SoftResponseListBean softResponseListBean : spaceDesignSelectedBean.getSoftResponseList()) {
                            if (softResponseListBean.getFurnitureSelected() != null) {
                                if (softResponseListBean.getFurnitureSelected().getSkuId().equals(skuBaseInfoDto.getSkuId())) {
                                    softResponseListBean.getFurnitureSelected().setWidth(skuBaseInfoDto.getWidth());
                                    softResponseListBean.getFurnitureSelected().setHeight(skuBaseInfoDto.getHeight());
                                    softResponseListBean.getFurnitureSelected().setLength(skuBaseInfoDto.getLength());
                                    softResponseListBean.getFurnitureSelected().setItemSize(skuBaseInfoDto.getRuleSize());
                                    softResponseListBean.setItemType(2);
                                } else if (integerSkuExtPropertyInfoMap.get(softResponseListBean.getFurnitureSelected().getSkuId()) != null) {
                                    SkuBaseInfoDto.SkuExtPropertyInfo skuExtPropertyInfo1 = integerSkuExtPropertyInfoMap.get(softResponseListBean.getFurnitureSelected().getSkuId());
                                    String[] split = skuExtPropertyInfo1.getPropertyValue().split(";");
                                    softResponseListBean.getFurnitureSelected().setSuggestMattressLength(Integer.parseInt(split[0]));
                                    softResponseListBean.getFurnitureSelected().setSuggestMattressWidth(Integer.parseInt(split[1]));
                                    softResponseListBean.getFurnitureSelected().setSuggestMattressMinHeight(Integer.parseInt(split[2]));
                                    softResponseListBean.getFurnitureSelected().setSuggestMattressMaxHeight(Integer.parseInt(split[3]));
                                    softResponseListBean.getFurnitureSelected().setItemSize(skuBaseInfoDtoForBed.getRuleSize());
                                    softResponseListBean.setItemType(1);
                                }
                            }
                            if (softResponseListBean.getFurnitureDefault() != null && (softResponseListBean.getItemType().equals(1) || softResponseListBean.getItemType().equals(2))) {
                                SkuBaseInfoDto skuBaseInfoDtoForMattressDefault = skuBaseInfoMattressDtosDefaultMap.get(softResponseListBean.getFurnitureDefault().getSkuId());
                                SkuBaseInfoDto.SkuExtPropertyInfo skuExtPropertyInfoForDefault = integerSkuExtPropertyInfoMapDefault.get(softResponseListBean.getFurnitureDefault().getSkuId());
                                SkuBaseInfoDto skuBaseInfoDtoForBedDefault = skuBaseInfoDtosForBedDefaultMap.get(softResponseListBean.getFurnitureDefault().getSkuId());
                                if (skuBaseInfoDtoForMattressDefault != null) {
                                    //床垫
                                    softResponseListBean.getFurnitureDefault().setWidth(skuBaseInfoDto.getWidth());
                                    softResponseListBean.getFurnitureDefault().setHeight(skuBaseInfoDto.getHeight());
                                    softResponseListBean.getFurnitureDefault().setLength(skuBaseInfoDto.getLength());
                                    softResponseListBean.getFurnitureDefault().setItemSize(skuBaseInfoDto.getRuleSize());
                                } else if (skuBaseInfoDtoForBedDefault != null) {
                                    //床
                                    SkuBaseInfoDto.SkuExtPropertyInfo skuExtPropertyInfo1 = integerSkuExtPropertyInfoMapDefault.get(softResponseListBean.getFurnitureDefault().getSkuId());
                                    String[] split = skuExtPropertyInfo1.getPropertyValue().split(";");
                                    softResponseListBean.getFurnitureDefault().setSuggestMattressLength(Integer.parseInt(split[0]));
                                    softResponseListBean.getFurnitureDefault().setSuggestMattressWidth(Integer.parseInt(split[1]));
                                    softResponseListBean.getFurnitureDefault().setSuggestMattressMinHeight(Integer.parseInt(split[2]));
                                    softResponseListBean.getFurnitureDefault().setSuggestMattressMaxHeight(Integer.parseInt(split[3]));
                                    softResponseListBean.getFurnitureDefault().setItemSize(skuBaseInfoDtoForBedDefault.getRuleSize());
                                }
                            }
                        }
                        //长、宽、最小高、最大高
                        String[] split = skuExtPropertyInfo.getPropertyValue().split(";");
                        List<String> ruleSize = Lists.newArrayList(skuBaseInfoDto.getRuleSize().replace("米", "").split("\\*"));
                        List<String> ruleSizeForBed = Lists.newArrayList(skuBaseInfoDtoForBed.getRuleSize().replace("米", "").split("\\*"));

                        if (!ruleSize.containsAll(ruleSizeForBed)
                                || !((skuBaseInfoDto.getHeight() != null && (skuBaseInfoDto.getHeight() <= Integer.parseInt(split[3])))//厚度
                        )) {
                            spaceDesignSelectedBean.setMattressMismatching(1);
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean deleteDraft(DeleteDraftRequest deleteDraftRequest) {
        Map<String, Object> params = new HashMap<>();
        params.put("draftId", deleteDraftRequest.getDraftId());
        params.put("draftProfileNum", deleteDraftRequest.getDraftProfileNum());
        params.put("draftType", deleteDraftRequest.getDraftType());
        params.put("orderNum", deleteDraftRequest.getOrderNum());

        boolean isDelete =  homeCardWcmProxy.deleteDraft(params);
        if (!isDelete) {
            throw new BusinessException(MessageConstant.OP_FAILED);
        }

        DeleteOfflineDrawMessageDto deleteOfflineDrawMessageDto = new DeleteOfflineDrawMessageDto();
        deleteOfflineDrawMessageDto.setUserId(deleteDraftRequest.getUserInfo().getId())
                .setOrderId(deleteDraftRequest.getOrderNum())
                .setDraftProfileNum(deleteDraftRequest.getDraftProfileNum());
        // 删除离线渲染信息流
        messageFlowProxy.deleteOfflineDrawMessage(deleteOfflineDrawMessageDto);

        // 删除离线渲染任务
        homeCardWcmProxy.deleteProgramMasterAndSubTask(deleteDraftRequest.getOrderNum(), deleteDraftRequest.getDraftProfileNum());

        return true;
    }
}