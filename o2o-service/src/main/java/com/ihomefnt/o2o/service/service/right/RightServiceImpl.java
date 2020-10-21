package com.ihomefnt.o2o.service.service.right;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.google.common.collect.Lists;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.life.dto.ArticleDto;
import com.ihomefnt.o2o.intf.domain.life.vo.request.ArticleRequestVo;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.FamilyOrderPayResponse;
import com.ihomefnt.o2o.intf.domain.right.dto.*;
import com.ihomefnt.o2o.intf.domain.right.vo.request.*;
import com.ihomefnt.o2o.intf.domain.right.vo.response.*;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.constant.right.*;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.manager.util.common.date.DateUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.life.LifeProxy;
import com.ihomefnt.o2o.intf.proxy.right.RightProxy;
import com.ihomefnt.o2o.intf.service.programorder.ProductProgramOrderService;
import com.ihomefnt.o2o.intf.service.right.RightService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jerfan cang
 * @date 2018/9/28 13:50
 */
@Service
@SuppressWarnings("all")
public class RightServiceImpl implements RightService {

    @Autowired
    RightProxy rightProxy;

    private static final Logger LOGGER = LoggerFactory.getLogger(RightServiceImpl.class);

    private static final String moneyHideStatusKey = "appMoneyHideStatusKey:";

    /**
     * 10000 枚举值
     */
    private static final Integer NUMBER_TEN_THOUSAND = 10000;
    /**
     * 图片100 尺寸枚举值
     */
    private static final Integer IMAGE_SIZE_ICON = 100;

    @NacosValue(value = "${NO_VERTICAL_REDUCTION_RIGHT_VERSION}", autoRefreshed = true)
    private String NO_VERTICAL_REDUCTION_RIGHT_VERSION;

    /**
     * 装修补贴头图
     */
    @NacosValue(value = "${DECORATION_SUBSIDY_HEAD_IMAGE}", autoRefreshed = true)
    private String DECORATION_SUBSIDY_HEAD_IMAGE;

    /**
     * 装修补贴示例图片
     */
    @NacosValue(value = "${DECORATION_SUBSIDY_EXAMPLE_PIC}", autoRefreshed = true)
    private String DECORATION_SUBSIDY_EXAMPLE_PIC;

    /**
     * 我的基本权益背景图
     */
    @NacosValue(value = "${MINE_DECORATION_SUBSIDY_BG_IMG}", autoRefreshed = true)
    private String MINE_DECORATION_SUBSIDY_BG_IMG;

    /**
     * 我的基本权益背景图，无支付页
     */
    @NacosValue(value = "${MINE_NOPAY_DECORATION_SUBSIDY_BG_IMG}", autoRefreshed = true)
    private String MINE_NOPAY_DECORATION_SUBSIDY_BG_IMG;

    /**
     * 补贴说明文章id
     */
    @NacosValue(value = "${SUBSIDY_INFO_ARTICLE_ID}", autoRefreshed = true)
    private Integer SUBSIDY_INFO_ARTICLE_ID;


    @Autowired
    private LifeProxy lifeProxy;

    @Autowired
    private ProductProgramOrderService orderService;

    @Override
    public CheckOrderRightsResponseVo queryOrderRightsLicense(Long orderNum) {
        CheckOrderRightsResultDto dto = rightProxy.queryOrderRightsLicense(orderNum);
        if (null == dto) {
            return null;
        }

        return ModelMapperUtil.strictMap(dto, CheckOrderRightsResponseVo.class);
    }

    @Override
    public RightResponse queryOrderRightsDetail(OrderRightsDetailRequestVo req, String serverName) {
        Integer rightVersion = req.getRightVersion();
        List<Integer> versionList = Lists.newArrayList(NO_VERTICAL_REDUCTION_RIGHT_VERSION.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
        if (rightVersion == null && req.getOrderNum() == null && req.getUserInfo() == null) {//无orderNum传入且用户未登录，展示新版本权益
            rightVersion = 2;
        } else {
            rightVersion = getRightVersion(req.getOrderNum());
        }
        List<GradeClassifyDto> obj = rightProxy.queryOrderRightsDetail(rightVersion);
        if (CollectionUtils.isEmpty(obj)) {
            return null;
        }
        RightResponse response;
        if (versionList.contains(rightVersion)) {
            response = getRightResponseNew2(obj, getShareUrl(serverName, rightVersion), req.getAppVersion());
        } else if (rightVersion == 2) {
            response = getRightResponseNew(obj, getShareUrl(serverName, rightVersion), req.getAppVersion());
        } else {
            response = getRightResponse(obj, getShareUrl(serverName, rightVersion), req.getAppVersion());
        }
        response.setVersionFlag(rightVersion);
        response.setIsCustomRightVersion(versionList.contains(rightVersion));

        return response;
    }


    /**
     * 获取权益版本
     *
     * @param orderNum
     * @return
     */
    private Integer getRightVersion(Integer orderNum) {
        if (orderNum == null) {
            return 2;
        }
        GradeVersionDto gradeVersionDto = rightProxy.queryCurrentVersion(orderNum);
        if (gradeVersionDto != null) {
            return gradeVersionDto.getVersion();
        }
        return 1;
    }

    @Override
    public RightDetailResponse queryGradeClassifyInfo(ClassificationReq req, String serverName) {
        Integer rightVersion = req.getVersion();
        if (rightVersion == null && req.getOrderNum() == null && req.getUserInfo() == null) {//无orderNum传入且用户未登录，展示新版本权益
            rightVersion = 2;
        } else {
            rightVersion = getRightVersion(req.getOrderNum());
        }
        List<GradeRightsResultDto> gradeRightsResultDto = rightProxy.queryGradeClassifyInfo(req.getGradeId(), rightVersion);
        if (gradeRightsResultDto == null) {
            return null;
        }
        List<Integer> versionList = Lists.newArrayList(NO_VERTICAL_REDUCTION_RIGHT_VERSION.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
        RightDetailResponse rightDetailResponse;
        if (rightVersion == 2 || versionList.contains(rightVersion)) {
            rightDetailResponse = getRightDetailResponseNew(gradeRightsResultDto, req.getGradeId(), getShareUrl(serverName, rightVersion));
        } else {
            rightDetailResponse = getRightDetailResponse(gradeRightsResultDto, req.getGradeId(), getShareUrl(serverName, rightVersion));
        }
        rightDetailResponse.setVersionFlag(rightVersion);
        return rightDetailResponse;
    }

    @Override
    public OrderSingleClassifyDto queryOrderRightSingleClassify(MyOrderRightByClassifyRequest request) {
        OrderSingleClassifyDto obj = rightProxy.queryOrderRightSingleClassify(request.getOrderNum(), request.getClassifyNo(), getRightVersion(request.getOrderNum()));
        if (null == obj) {
            return null;
        }
        // 排序
        obj = sortItemByStatus(obj);
        return obj;
    }

    @Override
    public Boolean classifyRightConfirm(ConfirmRightRequest req) {
        req.setVersion(getRightVersion(req.getOrderNum()));
        return rightProxy.classifyRightConfirm(req);
    }

    @Override
    public OrderRightPopupResponse judgeOrderRightPopup(OrderRightPopupRequest req) {
        if (null == req.getOrderNum() && StringUtil.isNullOrEmpty(req.getDeviceToken())) {
            // 订单编号和设备号不能同时为空
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        OrderRightPopupResponse response = new OrderRightPopupResponse();
        if (null != req.getOrderNum() && null != req.getGradeId()) {
            response.setGradeId(req.getGradeId());
            response.setOrderNum(req.getOrderNum());
            String key = RightRedisConstant.ORDER_RIGHT_POPUP_KEY + ":" + req.getOrderNum();
            String times = AppRedisUtil.get(key);

            if (StringUtil.isNullOrEmpty(times)) {
                // 第一次弹窗
                response.setPopupFlag(1);
                // 更新缓存,根据等级判断弹窗次数  0-普通 1-黄金 2-铂金 3-钻石
                switch (req.getGradeId()) {
                    case 3:
                        AppRedisUtil.setPersist(key, "1");// 永久
                        break;
                    default:
                        AppRedisUtil.set(key, "1", DateUtil.getRemainSecondsOneDay());// 当日倒计时
                        break;
                }
            } else {
                // 根据等级判断弹窗次数  0-普通 1-黄金 2-铂金 3-钻石
                switch (req.getGradeId()) {
                    case 0:
                        response.setPopupFlag(0);
                        break;
                    case 1:
                        response.setPopupFlag(0);
                        break;
                    case 2:
                        response.setPopupFlag(0);
                        break;
                    case 3:
                        response.setPopupFlag(0);
                        break;
                    default:
                        response.setPopupFlag(0);
                        break;
                }
            }

            return response;
        }

        // 无订单用户，根据设备token
        if (!StringUtil.isNullOrEmpty(req.getDeviceToken())) {
            String key = RightRedisConstant.ORDER_RIGHT_POPUP_TO_DEVICE_KEY + ":" + req.getDeviceToken();
            String times = AppRedisUtil.get(key);
            if (StringUtil.isNullOrEmpty(times)) {
                response.setPopupFlag(1);
                AppRedisUtil.set(key, "1", DateUtil.getRemainSecondsOneDay());// 当日倒计时
            } else {
                response.setPopupFlag(0);
            }
        }

        return response;
    }

    /**
     * 2020版本权益宣传页
     * @param req
     * @return
     */
    @Override
    public RightInfoResponse queryPublicityInfo(QueryMyOrderRightItemListRequest req) {
        RightInfoResponse rightInfoResponse = new RightInfoResponse();
        //2020版本基本权益，使用第二版本权益中的基本权益
        List<GradeRightsResultDto> gradeRightsResultList = rightProxy.queryGradeClassifyInfo(1, 2);
        RightClassifyDetail jiBenQuanYi = new RightClassifyDetail();
        for (GradeRightsResultDto gradeRightsResultDto : gradeRightsResultList) {
            if (gradeRightsResultDto.getClassifyNo() == 0) {//全品家基本权益
                jiBenQuanYi.setCassificationName("基本权益");
                jiBenQuanYi.setTotal(getTotalContent(gradeRightsResultDto));
                List<RigthtsItemBaseInfo> itemList = gradeRightsResultDto.getItemList();
                if (CollectionUtils.isNotEmpty(itemList)) {
                    itemList.forEach(rigthtsItemBaseInfo -> {
                        if(rigthtsItemBaseInfo.getItemNo()!=null){
                            rigthtsItemBaseInfo.setUrl(AliImageUtil.imageCompress(RigthtsItemEnum.getItemUrl(rigthtsItemBaseInfo.getItemNo()), req.getOsType(), req.getWidth(), ImageConstant.SIZE_MIDDLE));
                        }
                    });
                    jiBenQuanYi.setClassifyDetailList(itemList);
                }
                rightInfoResponse.setJiBenQuanYi(jiBenQuanYi);
            }
        }
        rightInfoResponse.setDecorationSubsidyExamplePic(AliImageUtil.imageCompress(DECORATION_SUBSIDY_EXAMPLE_PIC, req.getOsType(), req.getWidth(), ImageConstant.SIZE_MIDDLE));
        rightInfoResponse.setDecorationSubsidyHeadImage(AliImageUtil.imageCompress(DECORATION_SUBSIDY_HEAD_IMAGE, req.getOsType(), req.getWidth(), ImageConstant.SIZE_MIDDLE));
        ArticleDto article = lifeProxy.getArticleById(new ArticleRequestVo().setArticleId(SUBSIDY_INFO_ARTICLE_ID));
        if(article!=null && article.getContent()!=null){
            rightInfoResponse.setDecorationSubsidyInfo(AliImageUtil.compressdocumentBodyImage(article.getContent(),req.getWidth()));
        }
        if(req.getOrderNum()!=null){
            FamilyOrderPayResponse response = orderService.queryPayBaseInfo(req.getOrderNum());
            if(response!=null){
                rightInfoResponse.setAllMoney(response.getAllMoney());
            }
        }
        return rightInfoResponse;
    }

    /**
     * 2020版本我的基本权益
     * @param req
     * @return
     */
    @Override
    public OrderRightsResponse queryMineRights(QueryMyOrderRightItemListRequest req) {
        Map<String, Object> resultMap = concurrentQueryMineRightsInfo(req);
        OrderRightsResponse orderRightsResponse = new OrderRightsResponse();
        OrderRightsVo orderRightsVo = (OrderRightsVo) resultMap.get(ConcurrentTaskEnum.QUERY_DECORATION_SUBSIDY_INFO.name());
        if(orderRightsResponse==null){
            orderRightsResponse = new OrderRightsResponse();
        }
        List<GradeRightsResultDto> gradeRightsResultList = (List<GradeRightsResultDto>) resultMap.get(ConcurrentTaskEnum.QUERY_GRADE_CLASSIFY_INFO.name());
        RightClassifyDetail jiBenQuanYi = new RightClassifyDetail();
        for (GradeRightsResultDto gradeRightsResultDto : gradeRightsResultList) {
            if (gradeRightsResultDto.getClassifyNo() == 0) {//全品家基本权益
                jiBenQuanYi.setCassificationName("基本权益");
                jiBenQuanYi.setTotal(getTotalContent(gradeRightsResultDto));
                List<RigthtsItemBaseInfo> itemList = gradeRightsResultDto.getItemList();
                if (CollectionUtils.isNotEmpty(itemList)) {
                    itemList.forEach(rigthtsItemBaseInfo -> rigthtsItemBaseInfo.setUrl(AliImageUtil.imageCompress(RigthtsItemEnum.getItemUrlNew(rigthtsItemBaseInfo.getItemNo()), req.getOsType(), req.getWidth(), ImageConstant.SIZE_SMALL)));
                    jiBenQuanYi.setClassifyDetailList(itemList);
                }
                orderRightsResponse.setJiBenQuanYi(jiBenQuanYi);
            }
        }
        FamilyOrderPayResponse response = (FamilyOrderPayResponse) resultMap.get(ConcurrentTaskEnum.QUERY_PAY_BASE_INFO.name());
        orderRightsResponse.setConfirmReapAmount(response==null?BigDecimal.ZERO : response.getPaidAmount() == null ? BigDecimal.ZERO : response.getPaidAmount());
        orderRightsResponse.setOrderNum(req.getOrderNum());
        orderRightsResponse.setMineDecorationSubsidyBgImg(AliImageUtil.imageCompress(MINE_DECORATION_SUBSIDY_BG_IMG, req.getOsType(), req.getWidth(), ImageConstant.SIZE_MIDDLE));
        if(response!=null){
            orderRightsResponse.setAllMoney(response.getAllMoney());
            if(response.getAllMoney()==1||response.getAllMoney()==2){
                orderRightsResponse.setMineDecorationSubsidyBgImg(AliImageUtil.imageCompress(MINE_NOPAY_DECORATION_SUBSIDY_BG_IMG, req.getOsType(), req.getWidth(), ImageConstant.SIZE_MIDDLE));
            }
        }
        String moneyHideStatus = AppRedisUtil.get(moneyHideStatusKey + req.getOrderNum());
        if(StringUtils.isNotBlank(moneyHideStatus) && "2".equals(moneyHideStatus)){
            orderRightsResponse.setMoneyHideStatus(true);
        }
        if(orderRightsVo.getAchieveSubsidyStandard() && (orderRightsVo.getTotalAmount()==null||CollectionUtils.isEmpty(orderRightsVo.getDecorationSubsidyDetailList()))){
            orderRightsResponse.setAchieveSubsidyStandardFlag(3);
        }else if(orderRightsVo.getAchieveSubsidyStandard()){
            orderRightsResponse.setAchieveSubsidyStandardFlag(2);
            orderRightsResponse.setTotalAmount(orderRightsVo.getTotalAmount());
            setDecorationSubsidyFront(orderRightsVo,orderRightsResponse);
        }

        return orderRightsResponse;
    }

    /**
     * 装修补贴前端展示内容组装
     * @param orderRightsResponse
     */
    private void setDecorationSubsidyFront(OrderRightsVo orderRightsVo,OrderRightsResponse orderRightsResponse){
        try {
            List<OrderRightsVo.DecorationSubsidyDetail> decorationSubsidyDetailList = orderRightsVo.getDecorationSubsidyDetailList();
            //交款记录、退款记录
            List<OrderRightsResponse.DecorationSubsidyFrontDto> decorationSubsidyRefundFront = new ArrayList();
            //已回收记录
            List<OrderRightsResponse.DecorationSubsidyFrontDto> decorationSubsidyNormalFront = new ArrayList();
            if(CollectionUtils.isNotEmpty(decorationSubsidyDetailList)){
                decorationSubsidyDetailList.removeIf(o1->o1.getTotalAmount().compareTo(BigDecimal.ZERO)==0 ||o1.getSubsidySettleAmount().compareTo(BigDecimal.ZERO)==0);
                //结算日倒序
                Collections.sort(decorationSubsidyDetailList, Comparator.comparingLong(OrderRightsVo.DecorationSubsidyDetail::getId).reversed());
                decorationSubsidyDetailList.forEach(decorationSubsidyDetail -> {
                    if(decorationSubsidyDetail.getSubsidyType()!=null){
                        OrderRightsResponse.DecorationSubsidyFrontDto vo = JsonUtils.json2obj(JsonUtils.obj2json(decorationSubsidyDetail), OrderRightsResponse.DecorationSubsidyFrontDto.class);
                        if(decorationSubsidyDetail.getSubsidyType()==2){//回收类
                            decorationSubsidyRefundFront.add(vo);
                        } else {//正常类、退款类
                            decorationSubsidyNormalFront.add(vo);
                        }
                    }
                });
            }
            orderRightsResponse.setDecorationSubsidyNormalFront(decorationSubsidyNormalFront);
            orderRightsResponse.setDecorationSubsidyRefundFront(decorationSubsidyRefundFront);
        }catch (Exception e){
            LOGGER.error("setDecorationSubsidyFront error:",e);
        }
    }

    /**
     * 更新金额显示状态
     * @param req
     */
    @Override
    public void updateMoneyHideStatus(QueryMyOrderRightItemListRequest req) {
        //moneyHideStatus 1为显示 2为隐藏
        String moneyHideStatus = AppRedisUtil.get(moneyHideStatusKey + req.getOrderNum());
        if(StringUtils.isBlank(moneyHideStatus)){
            moneyHideStatus = "1";
        }
        if("1".equals(moneyHideStatus)){
            moneyHideStatus = "2";
        }else{
            moneyHideStatus = "1";
        }
        AppRedisUtil.setPersist(moneyHideStatusKey+ req.getOrderNum(), moneyHideStatus);
    }

    /**
     * 组装权益详情返回
     *
     * @param gradeRightsResultList
     * @return
     */
    private RightDetailResponse getRightDetailResponseNew(List<GradeRightsResultDto> gradeRightsResultList, Integer gradeId, ShareDto shareDto) {
        RightDetailResponse rightDetailResponse = new RightDetailResponse();
        rightDetailResponse.setGradeLevelTitle(RightLevelNewEnum.getName(gradeId) + "订单权益");
        rightDetailResponse.setGradeLevelPicUrl(RightLevelNewEnum.getGradeLevelPicUrl(gradeId));
        rightDetailResponse.setGradeLevelBackPicUrl(RightLevelNewEnum.getGradeLevelBackPicUrl(gradeId));
        rightDetailResponse.setGradelLevelUrl(RightLevelNewEnum.getGradelLevelUrl(gradeId));
        rightDetailResponse.setGradelLevelName(RightLevelNewEnum.getName(gradeId) + "权益");
        RightClassifyDetail liJianTeQuan = new RightClassifyDetail();
        RightClassifyDetail exclusiveRights = new RightClassifyDetail();
        RightClassifyDetail jiBenQuanYi = new RightClassifyDetail();
        for (GradeRightsResultDto gradeRightsResultDto : gradeRightsResultList) {
            if (gradeRightsResultDto.getClassifyNo() == 0) {//全品家基本权益
                jiBenQuanYi.setCassificationName("基本权益");
                jiBenQuanYi.setTotal(getTotalContent(gradeRightsResultDto));
                List<RigthtsItemBaseInfo> itemList = gradeRightsResultDto.getItemList();
                if (CollectionUtils.isNotEmpty(itemList)) {
                    itemList.forEach(rigthtsItemBaseInfo -> rigthtsItemBaseInfo.setUrl(AliImageUtil.imageCompress(rigthtsItemBaseInfo.getUrl(), 1, 750, ImageConstant.SIZE_SMALL)));
                    jiBenQuanYi.setClassifyDetailList(itemList);
                }
            }
            if (gradeRightsResultDto.getClassifyId() == 6) {//全品家立减特权
                liJianTeQuan.setCassificationName("全品家立减特权");
                liJianTeQuan.setTotal(gradeRightsResultDto.getRightsConfirmedLimit() + "");
                List<RigthtsItemBaseInfo> itemList = gradeRightsResultDto.getItemList();
                if (!CollectionUtils.isEmpty(itemList)) {
                    for (RigthtsItemBaseInfo rigthtsItemBaseInfo : itemList) {
                        if (rigthtsItemBaseInfo.getItemNo() != null && rigthtsItemBaseInfo.getItemNo() == 8) {//贷款用户立减特权
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_aijiadai.png");

                            rigthtsItemBaseInfo.setItemSimpleTitle("贷款贴息" + rigthtsItemBaseInfo.getItemRewardQuota() + "年");
                        }
                        if (rigthtsItemBaseInfo.getItemNo() != null && rigthtsItemBaseInfo.getItemNo() == 9) {//现金付款立减特权
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_xianjinlijian.png");
                            rigthtsItemBaseInfo.setItemSimpleTitle("最高本金额度18万");
                        }
                    }
                }
                liJianTeQuan.setClassifyDetailList(itemList);
            }
            if (gradeRightsResultDto.getClassifyNo() == 4) {//专属权益
                exclusiveRights.setCassificationName("专属权益");
                exclusiveRights.setTotal(getTotalContent(gradeRightsResultDto));
                List<RigthtsItemBaseInfo> itemList = gradeRightsResultDto.getItemList();
                if (!CollectionUtils.isEmpty(itemList)) {
                    for (RigthtsItemBaseInfo rigthtsItemBaseInfo : itemList) {
                        rigthtsItemBaseInfo.setUrl(AliImageUtil.imageCompress(rigthtsItemBaseInfo.getUrl(), 1, 750, ImageConstant.SIZE_SMALL));
                        if (rigthtsItemBaseInfo.getItemRewardQuota() != null && !rigthtsItemBaseInfo.getItemRewardQuota().equals(0) && StringUtils.isNotBlank(rigthtsItemBaseInfo.getItemRewardUnitStr())) {
                            rigthtsItemBaseInfo.setSubtitle(rigthtsItemBaseInfo.getItemRewardQuota() + rigthtsItemBaseInfo.getItemRewardUnitStr());
                        } else {
                            rigthtsItemBaseInfo.setSubtitle("");
                        }
                        if (rigthtsItemBaseInfo.getItemNo() != null && rigthtsItemBaseInfo.getItemNo() == 10) {//艾升级
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_aishengji.png");
                        }
                        if (rigthtsItemBaseInfo.getItemNo() != null && rigthtsItemBaseInfo.getItemNo() == 11) {//艾久久
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_aijiujiu.png");
                        }
                        if (rigthtsItemBaseInfo.getItemNo() != null && rigthtsItemBaseInfo.getItemNo() == 13) {//艾无忧
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_aiwuyou.png");
                            double itemRewardQuota = Double.parseDouble(rigthtsItemBaseInfo.getItemRewardQuota());
                            String quota = String.valueOf(itemRewardQuota);
                            if (itemRewardQuota > 10000) {
                                quota = String.valueOf(itemRewardQuota / 10000);
                                if (quota.indexOf(".") > -1) {
                                    quota = quota.substring(0, quota.indexOf("."));
                                }
                                quota = quota + "万";
                            }
                            rigthtsItemBaseInfo.setSubtitle(quota + rigthtsItemBaseInfo.getItemRewardUnitStr());
                        }
                        if (rigthtsItemBaseInfo.getItemNo() != null && rigthtsItemBaseInfo.getItemNo() == 14) {//艾呼吸
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_aihuxi.png");
                            rigthtsItemBaseInfo.setSubtitle("");
                        }
                        if (rigthtsItemBaseInfo.getItemNo() != null && rigthtsItemBaseInfo.getItemNo() == 15) {//艾洁士
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_aijieshi.png");
                            rigthtsItemBaseInfo.setSubtitle("共" + rigthtsItemBaseInfo.getItemRewardQuota() + "次");
                        }
                        if (rigthtsItemBaseInfo.getItemNo() != null && rigthtsItemBaseInfo.getItemNo() == 20) {//艾交付
                            rigthtsItemBaseInfo.setUrlNew("https://img13.ihomefnt.com/f34affa5155c277f57334fd4362b60f5335ddb77a01d8df8c3c77ac2f6b86649.png!M-SMALL");
                            rigthtsItemBaseInfo.setSubtitle("");
                        }
                        if (rigthtsItemBaseInfo.getItemNo() != null && rigthtsItemBaseInfo.getItemNo() == 21) {//艾乔迁
                            rigthtsItemBaseInfo.setUrlNew("https://img13.ihomefnt.com/f34affa5155c277f57334fd4362b60f55a149d59685d96c077ae870e58fbf183.png!M-SMALL");
                        }
                    }
                }
                exclusiveRights.setClassifyDetailList(itemList);
            }
        }
        rightDetailResponse.setLiJianTeQuan(liJianTeQuan);
        rightDetailResponse.setExclusiveRights(exclusiveRights);
        rightDetailResponse.setJiBenQuanYi(jiBenQuanYi);
        rightDetailResponse.setGradeShareInfo(shareDto);
        return rightDetailResponse;

    }

    /**
     * 组装权益详情返回
     *
     * @param gradeRightsResultList
     * @return
     */
    private RightDetailResponse getRightDetailResponse(List<GradeRightsResultDto> gradeRightsResultList, Integer gradeId, ShareDto shareDto) {
        RightDetailResponse rightDetailResponse = new RightDetailResponse();
        rightDetailResponse.setGradeLevelTitle(RightLevelEnum.getName(gradeId) + "订单权益");
        rightDetailResponse.setGradeLevelPicUrl(RightLevelEnum.getGradeLevelPicUrl(gradeId));
        rightDetailResponse.setGradeLevelBackPicUrl(RightLevelEnum.getGradeLevelBackPicUrl(gradeId));
        rightDetailResponse.setGradelLevelUrl(RightLevelEnum.getGradelLevelUrl(gradeId));
        rightDetailResponse.setGradelLevelName(RightLevelEnum.getName(gradeId) + "权益");
        RightClassifyDetail liJianTeQuan = new RightClassifyDetail();
        RightClassifyDetail tianJiangXiFu = new RightClassifyDetail();
        RightClassifyDetail qingYiWuJia = new RightClassifyDetail();
        RightClassifyDetail jiBenQuanYi = new RightClassifyDetail();

        for (GradeRightsResultDto gradeRightsResultDto : gradeRightsResultList) {
            if (gradeRightsResultDto.getClassifyId() == 1) {//全品家基本权益
                jiBenQuanYi.setCassificationName("基本权益");
                jiBenQuanYi.setTotal(getTotalContent(gradeRightsResultDto));
                jiBenQuanYi.setClassifyDetailList(gradeRightsResultDto.getItemList());
            }
            if (gradeRightsResultDto.getClassifyId() == 2) {//全品家立减特权
                liJianTeQuan.setCassificationName("全品家立减特权");
                liJianTeQuan.setTotal(gradeRightsResultDto.getRightsConfirmedLimit() + "");
                List<RigthtsItemBaseInfo> itemList = gradeRightsResultDto.getItemList();
                if (!CollectionUtils.isEmpty(itemList)) {
                    for (RigthtsItemBaseInfo rigthtsItemBaseInfo : itemList) {
                        if (rigthtsItemBaseInfo.getItemId() != null && rigthtsItemBaseInfo.getItemId() == 8) {//贷款用户立减特权
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_aijiadai.png");

                            rigthtsItemBaseInfo.setItemSimpleTitle("贷款贴息" + rigthtsItemBaseInfo.getItemRewardQuota() + "年");
                        }
                        if (rigthtsItemBaseInfo.getItemId() != null && rigthtsItemBaseInfo.getItemId() == 9) {//现金付款立减特权
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_xianjinlijian.png");
                            rigthtsItemBaseInfo.setItemSimpleTitle("最高本金额度18万");
                        }
                    }
                }
                liJianTeQuan.setClassifyDetailList(itemList);
            }
            if (gradeRightsResultDto.getClassifyId() == 3) {//天降喜福
                tianJiangXiFu.setCassificationName("天降喜福特权");
                tianJiangXiFu.setTotal(getTotalContent(gradeRightsResultDto));
                List<RigthtsItemBaseInfo> itemList = gradeRightsResultDto.getItemList();
                if (!CollectionUtils.isEmpty(itemList)) {
                    for (RigthtsItemBaseInfo rigthtsItemBaseInfo : itemList) {

                        if (rigthtsItemBaseInfo.getItemId() != null && rigthtsItemBaseInfo.getItemId() == 10) {//艾升级
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_aishengji.png");
                            rigthtsItemBaseInfo.setSubtitle(rigthtsItemBaseInfo.getItemRewardQuota() + rigthtsItemBaseInfo.getItemRewardUnitStr());
                        }
                        if (rigthtsItemBaseInfo.getItemId() != null && rigthtsItemBaseInfo.getItemId() == 11) {//艾久久
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_aijiujiu.png");
                            rigthtsItemBaseInfo.setSubtitle(rigthtsItemBaseInfo.getItemRewardQuota() + rigthtsItemBaseInfo.getItemRewardUnitStr());
                        }
                        if (rigthtsItemBaseInfo.getItemId() != null && rigthtsItemBaseInfo.getItemId() == 12) {//艾艺术
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_aiyishu.png");
                            rigthtsItemBaseInfo.setSubtitle("价值" + rigthtsItemBaseInfo.getItemRewardQuota() + "元");
                        }
                        if (rigthtsItemBaseInfo.getItemId() != null && rigthtsItemBaseInfo.getItemId() == 13) {//艾无忧
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_aiwuyou.png");
                            double itemRewardQuota = Double.parseDouble(rigthtsItemBaseInfo.getItemRewardQuota());
                            String quota = String.valueOf(itemRewardQuota);
                            if (itemRewardQuota > 10000) {
                                quota = String.valueOf(itemRewardQuota / 10000);
                                if (quota.indexOf(".") > -1) {
                                    quota = quota.substring(0, quota.indexOf("."));
                                }
                                quota = quota + "万";
                            }
                            rigthtsItemBaseInfo.setSubtitle(quota + rigthtsItemBaseInfo.getItemRewardUnitStr());
                        }
                        if (rigthtsItemBaseInfo.getItemId() != null && rigthtsItemBaseInfo.getItemId() == 14) {//艾呼吸
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_aihuxi.png");
                            rigthtsItemBaseInfo.setSubtitle(rigthtsItemBaseInfo.getItemRewardQuota() + "天免租金");
                        }
                        if (rigthtsItemBaseInfo.getItemId() != null && rigthtsItemBaseInfo.getItemId() == 15) {//艾洁士
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_aijieshi.png");
                            rigthtsItemBaseInfo.setSubtitle("共" + rigthtsItemBaseInfo.getItemRewardQuota() + "次");
                        }
                        if (rigthtsItemBaseInfo.getItemId() != null && rigthtsItemBaseInfo.getItemId() == 16) {//艾先住
                            rigthtsItemBaseInfo.setUrlNew("https://static.ihomefnt.com/1/image/pic_aixianzhu.png");
                            rigthtsItemBaseInfo.setSubtitle(rigthtsItemBaseInfo.getItemRewardQuota());
                        }
                    }
                }
                tianJiangXiFu.setClassifyDetailList(itemList);
            }
            if (gradeRightsResultDto.getClassifyId() == 4) {//情义无价
                qingYiWuJia.setCassificationName("情义无价特权");
                qingYiWuJia.setTotal(getTotalContent(gradeRightsResultDto));
                List<RigthtsItemBaseInfo> itemList = gradeRightsResultDto.getItemList();
                if (!CollectionUtils.isEmpty(itemList)) {
                    for (RigthtsItemBaseInfo rigthtsItemBaseInfo : itemList) {
                        rigthtsItemBaseInfo.setSubtitle(subtitle(rigthtsItemBaseInfo));
                        rigthtsItemBaseInfo.setUrl(QiniuImageUtils.compressImageAndSamePicTwo(rigthtsItemBaseInfo.getUrl(), 100, 100));
                    }
                }
                qingYiWuJia.setClassifyDetailList(itemList);
            }
        }
        rightDetailResponse.setLiJianTeQuan(liJianTeQuan);
        rightDetailResponse.setTianJiangXiFu(tianJiangXiFu);
        rightDetailResponse.setQingYiWuJia(qingYiWuJia);
        rightDetailResponse.setJiBenQuanYi(jiBenQuanYi);
        rightDetailResponse.setGradeShareInfo(shareDto);
        return rightDetailResponse;

    }

    /**
     * 根据权益项的名称 匹配其副标题
     *
     * @return subtitle 权益项的副标题
     */
    private String subtitle(RigthtsItemBaseInfo item) {
        String itemName = item.getItemName();
        String subtitle = null;
        switch (itemName) {
            case "艾升级":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾久久":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾艺术":
                subtitle = "价值" + item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾无忧":

                subtitle = (Integer.parseInt(item.getItemRewardQuota()) / NUMBER_TEN_THOUSAND) + "万" + item.getItemRewardUnitStr();
                break;
            case "艾呼吸":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr() + "免租金";
                break;
            case "艾洁士":
                subtitle = "共" + item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾先住":
                subtitle = item.getItemRewardQuota();
                break;
            case "艾前程":
                subtitle = "可推荐" + item.getItemRewardQuota() + "人";
                break;
            case "艾就业":
                subtitle = "小区大众监理" + item.getItemRewardQuota() + "人";
                break;
            case "艾聚会":
                subtitle = "年度生态大会";
                break;
            case "艾祝福":
                subtitle = "价值" + item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾乔迁":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾长者":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾小宝":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾焕新":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾维权":
                subtitle = item.getItemRewardQuota();
                break;
            case "艾佳游":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾艺星":
                subtitle = "小星星艺术节";
                break;
            case "艾扮家":
                subtitle = "参加创意大赛";
                break;
            case "艾订制":
                subtitle = "个性软饰设计";
                break;
            case "艾学习":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr() + "无敌券";
                break;
            case "艾援助":
                subtitle = item.getItemRewardQuota();
                break;
            case "艾留念":
                subtitle = "全家福留念";
                break;
            default:
                subtitle = "";
        }
        return subtitle;
    }

    /**
     * 获取权益总计
     *
     * @param gradeRightsResultDto
     * @return
     */
    private String getTotalContent(GradeRightsResultDto gradeRightsResultDto) {
        if (gradeRightsResultDto.getRightsConfirmedLimit() == gradeRightsResultDto.getRightsConfigLimit()) {
            return gradeRightsResultDto.getRightsConfirmedLimit() + "项";
        } else {
            return gradeRightsResultDto.getRightsConfigLimit() + "选" + gradeRightsResultDto.getRightsConfirmedLimit() + "项";
        }
    }

    /**
     * 获取分享地址
     *
     * @param serverName
     * @return
     */
    private ShareDto getShareUrl(String serverName, Integer rightVersion) {
        String url = "http://huodong.sit.ihomefnt.org/#/WebRightsRulePage?rightVersion=" + rightVersion;
        if ("api.ihomefnt.com".equals(serverName)) {//线上分享地址
            url = "https://huodong.ihomefnt.com/#/WebRightsRulePage?rightVersion=" + rightVersion;
        }
        ShareDto shareDto = new ShareDto();
        shareDto.setRadeShareUrl(url);
        shareDto.setGradeShareDesc("零首付，一键获得爱家贷18万元，权益升级倒计时，千万别错过~");
        shareDto.setGradeShareIconUrl(StaticResourceConstants.AIJIA_LOGO_ICON_NEW);
        shareDto.setGradeShareTitle("全品家订单权益火爆进行中，赶快升级享受更多特权吧。");
        return shareDto;
    }

    /**
     * 查询订单权益(活动)详情内容组装
     *
     * @param obj
     * @return
     */
    private RightResponse getRightResponse(List<GradeClassifyDto> obj, ShareDto shareDto, String appVersion) {
        RightResponse rightResponse = new RightResponse();
        JSONObject gradeUpgradeStandard = JSONObject.parseObject(MessageConstant.GRADE_UPGRADE_STANDARD);
        setGradeList(obj, appVersion);
        rightResponse.setGradeOverViewPicUrl(StaticResourceConstants.GRADE_OVER_VIEW_PIC_URL);
        rightResponse.setGradeUpgradeStandard(gradeUpgradeStandard);
        rightResponse.setGradeList(obj);
        rightResponse.setGradeShareInfo(shareDto);
        return rightResponse;
    }

    /**
     * 查询订单权益(活动)详情内容组装
     *
     * @param obj
     * @return
     */
    private RightResponse getRightResponseNew(List<GradeClassifyDto> obj, ShareDto shareDto, String appVersion) {
        RightResponse rightResponse = new RightResponse();
        JSONObject gradeUpgradeStandard = JSONObject.parseObject(MessageConstant.GRADE_UPGRADE_STANDARD_NEW);
        obj = setGradeListNew(obj, appVersion);
        rightResponse.setGradeOverViewPicUrl("https://img13.ihomefnt.com/4533b0011124d9a3125e1353fc4881d779951b1972c447e43c5734ed79b83c94.png!M-MIDDLE");
        rightResponse.setGradeUpgradeStandard(gradeUpgradeStandard);
        rightResponse.setGradeList(obj);
        rightResponse.setGradeShareInfo(shareDto);
        return rightResponse;
    }

    /**
     * 查询订单权益(活动)详情内容组装 无立减权益
     *
     * @param obj
     * @return
     */
    private RightResponse getRightResponseNew2(List<GradeClassifyDto> obj, ShareDto shareDto, String appVersion) {
        RightResponse rightResponse = new RightResponse();
        JSONObject gradeUpgradeStandard = JSONObject.parseObject(MessageConstant.GRADE_UPGRADE_STANDARD_NEW);
        obj = setGradeListNew2(obj, appVersion);
        rightResponse.setGradeOverViewPicUrl("https://common.ihomefnt.com/1/image/0f6137efce4ef86cae3afe208585cb196b4f6f88db68f95a70eff24b96bf833f.png!M-MIDDLE");
        rightResponse.setGradeUpgradeStandard(gradeUpgradeStandard);
        rightResponse.setGradeList(obj);
        rightResponse.setGradeShareInfo(shareDto);
        return rightResponse;
    }

    /**
     * 组装GradeList
     *
     * @param obj
     */
    private List<GradeClassifyDto> setGradeListNew2(List<GradeClassifyDto> obj, String appVersion) {
        JSONArray rightDetail = JSONObject.parseArray(MessageConstant.RIHGHT_NEW_DETAIL_NEW);
        for (GradeClassifyDto gradeClassifyDto : obj) {
            if (gradeClassifyDto.getGradeId() == RightLevelEnum.LEVEL_ONE.getCode()) {//普通等级
                gradeClassifyDto.setGradeNameCopywriting(gradeClassifyDto.getGradeName() + "权益");
            } else {
                gradeClassifyDto.setGradeNameCopywriting(gradeClassifyDto.getGradeName() + "VIP");
            }
            gradeClassifyDto.setGradeClassifyCountDesc(RightLevelNewEnum2.getTotalContent(gradeClassifyDto.getGradeId()));
            gradeClassifyDto.setGradeClassifyDeac(RightLevelNewEnum2.getGradeClassifyDeac(gradeClassifyDto.getGradeId()));
            gradeClassifyDto.setGradeNameUrl(RightLevelNewEnum2.getGradeNameUrl(gradeClassifyDto.getGradeId()));
            gradeClassifyDto.setGradeBackGround(RightLevelNewEnum2.getGradeBackGround(gradeClassifyDto.getGradeId()));
            List<RightsClassifyDto> classifyDtoList = gradeClassifyDto.getClassifyDtoList();
            if (!CollectionUtils.isEmpty(classifyDtoList)) {
                //appVersion版本号为空或者版本号5.2.6以上
                if (classifyDtoList.size() == 1 && (StringUtils.isBlank(appVersion) || !VersionUtil.mustUpdate(appVersion, "5.2.6"))) {//普通权益list补全
                    classifyDtoList.add(new RightsClassifyDto());
                }
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum2.LEVEL_ONE.getCode()) {//普通权益
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelNewEnum2.LEVEL_ONE.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum2.LEVEL_TWO.getCode()) {//黄金
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelNewEnum2.LEVEL_TWO.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum2.LEVEL_THREE.getCode()) {//铂金
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelNewEnum2.LEVEL_THREE.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum2.LEVEL_FOR.getCode()) {//钻石
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelNewEnum2.LEVEL_FOR.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum2.LEVEL_FIV.getCode()) {//白银
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelNewEnum2.LEVEL_FIV.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum2.LEVEL_SIX.getCode()) {//青铜
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelNewEnum2.LEVEL_SIX.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(obj)) {
            List<GradeClassifyDto> objNew = new ArrayList<>(6);
            for (GradeClassifyDto gradeClassifyDto : obj) {
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum2.LEVEL_ONE.getCode()) {
                    objNew.add(0, gradeClassifyDto);
                }
            }
            for (GradeClassifyDto gradeClassifyDto : obj) {
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum2.LEVEL_SIX.getCode()) {
                    objNew.add(1, gradeClassifyDto);
                }
            }
            for (GradeClassifyDto gradeClassifyDto : obj) {
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum2.LEVEL_FIV.getCode()) {
                    objNew.add(2, gradeClassifyDto);
                }
            }
            for (GradeClassifyDto gradeClassifyDto : obj) {
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum2.LEVEL_TWO.getCode()) {
                    objNew.add(3, gradeClassifyDto);
                }
            }
            for (GradeClassifyDto gradeClassifyDto : obj) {
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum2.LEVEL_THREE.getCode()) {
                    objNew.add(4, gradeClassifyDto);
                }
            }
            for (GradeClassifyDto gradeClassifyDto : obj) {
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum2.LEVEL_FOR.getCode()) {
                    objNew.add(5, gradeClassifyDto);
                }
            }
            Collections.reverse(objNew);
            objNew.forEach(gradeClassifyDto -> {
                gradeClassifyDto.getClassifyDtoList().removeIf(rightsClassifyDto -> rightsClassifyDto.getRightConfirmedLimitCopywriting().equals("不享受"));
            });
            return objNew;
        }
        return obj;
    }

    /**
     * 组装GradeList
     *
     * @param obj
     */
    private List<GradeClassifyDto> setGradeListNew(List<GradeClassifyDto> obj, String appVersion) {
        JSONArray rightDetail = JSONObject.parseArray(MessageConstant.RIHGHT_NEW_DETAIL);
        for (GradeClassifyDto gradeClassifyDto : obj) {
            if (gradeClassifyDto.getGradeId() == RightLevelEnum.LEVEL_ONE.getCode()) {//普通等级
                gradeClassifyDto.setGradeNameCopywriting(gradeClassifyDto.getGradeName() + "权益");
            } else {
                gradeClassifyDto.setGradeNameCopywriting(gradeClassifyDto.getGradeName() + "VIP");
            }
            gradeClassifyDto.setGradeClassifyCountDesc(RightLevelNewEnum.getTotalContent(gradeClassifyDto.getGradeId()));
            gradeClassifyDto.setGradeClassifyDeac(RightLevelNewEnum.getGradeClassifyDeac(gradeClassifyDto.getGradeId()));
            gradeClassifyDto.setGradeNameUrl(RightLevelNewEnum.getGradeNameUrl(gradeClassifyDto.getGradeId()));
            gradeClassifyDto.setGradeBackGround(RightLevelNewEnum.getGradeBackGround(gradeClassifyDto.getGradeId()));
            List<RightsClassifyDto> classifyDtoList = gradeClassifyDto.getClassifyDtoList();
            if (!CollectionUtils.isEmpty(classifyDtoList)) {
                //appVersion版本号为空或者版本号5.2.6以上
                if (classifyDtoList.size() == 1 && (StringUtils.isBlank(appVersion) || !VersionUtil.mustUpdate(appVersion, "5.2.6"))) {//普通权益list补全
                    classifyDtoList.add(new RightsClassifyDto());
                    classifyDtoList.add(new RightsClassifyDto());
                }
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum.LEVEL_ONE.getCode()) {//普通权益
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelNewEnum.LEVEL_ONE.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum.LEVEL_TWO.getCode()) {//黄金
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelNewEnum.LEVEL_TWO.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum.LEVEL_THREE.getCode()) {//铂金
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelNewEnum.LEVEL_THREE.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum.LEVEL_FOR.getCode()) {//钻石
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelNewEnum.LEVEL_FOR.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum.LEVEL_FIV.getCode()) {//白银
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelNewEnum.LEVEL_FIV.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum.LEVEL_SIX.getCode()) {//青铜
                    classifyDtoList.add(1, new RightsClassifyDto());
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelNewEnum.LEVEL_SIX.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(obj)) {
            List<GradeClassifyDto> objNew = new ArrayList<>(6);
            for (GradeClassifyDto gradeClassifyDto : obj) {
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum.LEVEL_ONE.getCode()) {
                    objNew.add(0, gradeClassifyDto);
                }
            }
            for (GradeClassifyDto gradeClassifyDto : obj) {
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum.LEVEL_SIX.getCode()) {
                    objNew.add(1, gradeClassifyDto);
                }
            }
            for (GradeClassifyDto gradeClassifyDto : obj) {
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum.LEVEL_FIV.getCode()) {
                    objNew.add(2, gradeClassifyDto);
                }
            }
            for (GradeClassifyDto gradeClassifyDto : obj) {
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum.LEVEL_TWO.getCode()) {
                    objNew.add(3, gradeClassifyDto);
                }
            }
            for (GradeClassifyDto gradeClassifyDto : obj) {
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum.LEVEL_THREE.getCode()) {
                    objNew.add(4, gradeClassifyDto);
                }
            }
            for (GradeClassifyDto gradeClassifyDto : obj) {
                if (gradeClassifyDto.getGradeId() == RightLevelNewEnum.LEVEL_FOR.getCode()) {
                    objNew.add(5, gradeClassifyDto);
                }
            }
            Collections.reverse(objNew);
            return objNew;
        }
        return obj;
    }

    /**
     * 组装GradeList
     *
     * @param obj
     */
    private void setGradeList(List<GradeClassifyDto> obj, String appVersion) {
        JSONArray rightDetail = JSONObject.parseArray(MessageConstant.RIHGHT_DETAIL);
        for (GradeClassifyDto gradeClassifyDto : obj) {
            if (gradeClassifyDto.getGradeId() == RightLevelEnum.LEVEL_ONE.getCode()) {//普通等级
                gradeClassifyDto.setGradeNameCopywriting(gradeClassifyDto.getGradeName() + "权益");
            } else {
                gradeClassifyDto.setGradeNameCopywriting(gradeClassifyDto.getGradeName() + "VIP");
            }
            gradeClassifyDto.setGradeClassifyCountDesc(RightLevelEnum.getTotalContent(gradeClassifyDto.getGradeId()));
            gradeClassifyDto.setGradeClassifyDeac(RightLevelEnum.getGradeClassifyDeac(gradeClassifyDto.getGradeId()));
            //老版本也塞一下两个值
            gradeClassifyDto.setGradeNameUrl(RightLevelNewEnum.getGradeNameUrl(gradeClassifyDto.getGradeId()));
            gradeClassifyDto.setGradeBackGround(RightLevelNewEnum.getGradeBackGround(gradeClassifyDto.getGradeId()));
            List<RightsClassifyDto> classifyDtoList = gradeClassifyDto.getClassifyDtoList();
            if (!CollectionUtils.isEmpty(classifyDtoList)) {
                //appVersion版本号为空或者版本号5.2.6以上
                if (classifyDtoList.size() == 1 && (StringUtils.isBlank(appVersion) || !VersionUtil.mustUpdate(appVersion, "5.2.6"))) {//普通权益list补全
                    classifyDtoList.add(new RightsClassifyDto());
                    classifyDtoList.add(new RightsClassifyDto());
                    classifyDtoList.add(new RightsClassifyDto());
                }
                if (gradeClassifyDto.getGradeId() == RightLevelEnum.LEVEL_ONE.getCode()) {//普通权益
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelEnum.LEVEL_ONE.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }
                if (gradeClassifyDto.getGradeId() == RightLevelEnum.LEVEL_TWO.getCode()) {//黄金
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelEnum.LEVEL_TWO.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }
                if (gradeClassifyDto.getGradeId() == RightLevelEnum.LEVEL_THREE.getCode()) {//铂金
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelEnum.LEVEL_THREE.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }
                if (gradeClassifyDto.getGradeId() == RightLevelEnum.LEVEL_FOR.getCode()) {//钻石
                    JSONArray jsonArray = (JSONObject.parseObject(rightDetail.get(RightLevelEnum.LEVEL_FOR.getCode()).toString())).getJSONArray("detail");
                    setClassifyDtoList(classifyDtoList, jsonArray);
                }

            }
        }


    }

    /**
     * 设置四项基本权益
     *
     * @param classifyDtoList
     * @param jsonArray
     */
    private void setClassifyDtoList(List<RightsClassifyDto> classifyDtoList, JSONArray jsonArray) {
        for (int i = 0; i < classifyDtoList.size(); i++) {
            classifyDtoList.get(i).setClassifyNameCopywriting((String) jsonArray.getJSONObject(i).get("title"));
            classifyDtoList.get(i).setRightConfirmedLimitCopywriting((String) jsonArray.getJSONObject(i).get("name"));
            classifyDtoList.get(i).setClassifyPicUrl((String) jsonArray.getJSONObject(i).get("url"));
        }
    }

    /**
     * 把权益项列表排序 根据状态 已选择的放在前面
     *
     * @param data
     * @return
     */
    private OrderSingleClassifyDto sortItemByStatus(OrderSingleClassifyDto data) {

        List<RightItemDto> itemDetailList = data.getItemDetailList();
        // 存储已经确权的项
        List<RightItemDto> itemDetailList_choose_yes = new ArrayList<>();
        // 存储未确权的项
        List<RightItemDto> itemDetailList_choose_no = new ArrayList<>();
        for (RightItemDto item : itemDetailList) {
            if (null != item.getConsumeStatus() && item.getConsumeStatus() > 0) {
                itemDetailList_choose_yes.add(item);
            } else {
                itemDetailList_choose_no.add(item);
            }
        }
        // 把未确权的追加到已经确权项的后面
        itemDetailList_choose_yes.addAll(itemDetailList_choose_no);
        data.setItemDetailList(itemDetailList_choose_yes);
        return data;
    }

    /**
     * 我的基本权益信息查询
     *
     * @param req
     * @return
     */
    private Map<String, Object> concurrentQueryMineRightsInfo(QueryMyOrderRightItemListRequest req) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                //2020版本基本权益，使用第二版本权益中的基本权益
                return rightProxy.queryGradeClassifyInfo(1, 2);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_GRADE_CLASSIFY_INFO.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return orderService.queryPayBaseInfo(req.getOrderNum());
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_PAY_BASE_INFO.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return rightProxy.queryDecorationSubsidyInfo(req.getOrderNum());
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_DECORATION_SUBSIDY_INFO.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

}
