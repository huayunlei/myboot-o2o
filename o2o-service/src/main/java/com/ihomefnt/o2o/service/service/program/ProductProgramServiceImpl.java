package com.ihomefnt.o2o.service.service.program;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.common.util.*;
import com.ihomefnt.message.RocketMQTemplate;
import com.ihomefnt.o2o.intf.domain.art.dto.KeywordVo;
import com.ihomefnt.o2o.intf.domain.artist.dto.DesignerInfoByRoomResponse;
import com.ihomefnt.o2o.intf.domain.bankcard.dto.BankCardDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.customer.dto.CommissionRuleDto;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicListDto;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.*;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DNAInfoResponseCommonVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DNAInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.HardStandardDetailVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.HouseInfo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BasePropertyResponseVo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BomGroupDraftVo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BomGroupVO;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.QueryDraftRequest;
import com.ihomefnt.o2o.intf.domain.main.vo.SolutionInfo;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.ProgramOrderDetailRequest;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.StyleRecordRequest;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.PersonalDesignResponse;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.StyleRecordResponse;
import com.ihomefnt.o2o.intf.domain.product.dto.SkuBaseInfoDto;
import com.ihomefnt.o2o.intf.domain.program.customgoods.request.QuerMaterialForPageRequest;
import com.ihomefnt.o2o.intf.domain.program.customgoods.request.QueryGroupReplaceDetailRequest;
import com.ihomefnt.o2o.intf.domain.program.customgoods.response.GroupReplaceDetailVO;
import com.ihomefnt.o2o.intf.domain.program.customgoods.response.MaterialForPageVO;
import com.ihomefnt.o2o.intf.domain.program.dto.*;
import com.ihomefnt.o2o.intf.domain.program.enums.SolutionStatusEnum;
import com.ihomefnt.o2o.intf.domain.program.vo.request.*;
import com.ihomefnt.o2o.intf.domain.program.vo.response.*;
import com.ihomefnt.o2o.intf.domain.programorder.dto.*;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.CreateFamilyOrderRequest;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.DraftSimpleRequest;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.DraftSimpleRequestPage;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleAnwserSelectedResponse;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleQuestionSelectedNewResponse;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleQuestionSelectedResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.HousePropertyInfoResultDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.RedisKey;
import com.ihomefnt.o2o.intf.manager.constant.home.HomeCardPraise;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstant;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstants;
import com.ihomefnt.o2o.intf.manager.constant.personalneed.DesignTaskAppEnum;
import com.ihomefnt.o2o.intf.manager.constant.product.ProductCategoryConstant;
import com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise;
import com.ihomefnt.o2o.intf.manager.constant.program.SoftItemEnum;
import com.ihomefnt.o2o.intf.manager.constant.program.SolutionStandardUpgradeConstant;
import com.ihomefnt.o2o.intf.manager.constant.programorder.RoomUseEnum;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.ServiceCallerUtil;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.IntegerUtil;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.agent.AgentAladdinCommissionProxy;
import com.ihomefnt.o2o.intf.proxy.artist.ArtistProxy;
import com.ihomefnt.o2o.intf.proxy.bankcard.BankCardProxy;
import com.ihomefnt.o2o.intf.proxy.designdemand.StyleQuestionAnwserProxy;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardBossProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardWcmProxy;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.product.ProductProxy;
import com.ihomefnt.o2o.intf.proxy.program.HardStandardWcmProxy;
import com.ihomefnt.o2o.intf.proxy.program.KeywordWcmProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.proxy.program.customgoods.CurtainProxy;
import com.ihomefnt.o2o.intf.proxy.user.PersonalCenterProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.designDemand.ProgramPersonalNeedService;
import com.ihomefnt.o2o.intf.service.home.HomeBuildingService;
import com.ihomefnt.o2o.intf.service.home.HomeV5PageService;
import com.ihomefnt.o2o.intf.service.house.HouseService;
import com.ihomefnt.o2o.intf.service.program.ProductProgramService;
import com.ihomefnt.o2o.intf.service.program.customgoods.CurtainService;
import com.ihomefnt.o2o.intf.service.programorder.ProductProgramOrderService;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import com.ihomefnt.o2o.service.service.home.HomePageService;
import com.ihomefnt.zeus.finder.ServiceCaller;
import com.ihomefnt.zeus.util.JsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * 注：20180323 定价中心  C端无优惠率概念  C端展示价格全部是折扣后的价格
 * <p>
 * 产品方案service实现层
 *
 * @author ZHAO
 */
@Service
@SuppressWarnings("all")
public class ProductProgramServiceImpl implements ProductProgramService {

    @Resource
    ServiceCaller serviceCaller;

    @Autowired
    private ProductProgramProxy productProgramProxy;

    @Autowired
    private HomeCardBossProxy homeCardBossProxy;

    @Autowired
    private HardStandardWcmProxy hardStandardWcmProxy;

    @Autowired
    private HomeCardWcmProxy homeCardWcmProxy;

    @Autowired
    private DicProxy dicProxy;

    @Autowired
    private ProductProgramOrderProxyImpl productProgramOrderProxy;

    @Autowired
    private KeywordWcmProxy keywordWcmProxy;

    @Autowired
    private ArtistProxy artistProxy;

    @Autowired
    @Lazy
    private HomeBuildingService homeBuildingService;

    @Autowired
    @Lazy
    private HomePageService homePageService;

    @Autowired
    @Lazy
    private CurtainService curtainService;

    private static final Logger LOG = LoggerFactory.getLogger(ProductProgramServiceImpl.class);

    @Autowired
    ServiceCallerUtil serviceCallerUtil;

    @Autowired
    private AgentAladdinCommissionProxy commissionProxy;

    @Autowired
    @Lazy
    private HomeV5PageService homeV5PageService;

    @Autowired
    private PersonalCenterProxy personalCenterProxy;

    @Autowired
    @Lazy
    private ProductProgramOrderService productProgramOrderService;
    @Autowired
    private HouseService houseService;

    public static final List<Integer> supportDrawCategoryList = Lists.newArrayList(682, 4, 5, 688, 15, 19, 22);

    @Autowired
    private ProgramTextBean programText;

    @Autowired
    private ProgramPersonalNeedService programPersonalNeedService;

    @Autowired
    private StyleQuestionAnwserProxy styleQuestionAnwserProxy;

    @Autowired
    private CurtainProxy curtainProxy;

    @NacosValue(value = "${APP_DRAFT_DRAW_TASK_SUPPORT_DRAW_HARDCATEGORY}", autoRefreshed = true)
    private String supportDrawHardCategory;

    @Autowired
    private ProductProxy productProxy;

    @NacosValue(value = "${APP_GUI_BOM_PROPERTY_RANGE_JSON}", autoRefreshed = true)
    private String appGuiBomPropertyRangeJson;

    @NacosValue(value = "${APP_GUI_BOM_PROPERTY_NAME_CONVERT_JSON}", autoRefreshed = true)
    private String appGuiBomPropertyNameConvertJson;

    @NacosValue(value = "${APP.SOLUTION.LIST.BOTTOM}", autoRefreshed = true)
    private String appSolutionListBottom;

    @Autowired
    private UserProxy userProxy;

    @Autowired
    private OrderProxy orderProxy;

    @NacosValue(value = "${app.program.detail.task.topic}", autoRefreshed = true)
    private String APP_PROGRAM_DETAIL_TASK_TOPIC;

    @NacosValue(value = "${APP.PROGRAM.DEL.TEST.SOLUTIONS}", autoRefreshed = true)
    private Boolean DEL_TEST_SOLUTIONS;

    @NacosValue(value = "${APP.PROGRAM.DEL.TEST.PROGRAM}", autoRefreshed = true)
    private Boolean DEL_TEST_PROGRAM;
    @NacosValue(value = "${APP.PROGRAM.BAN.ITEM.REPLACE.HARD}")
    private String appProgramBanitemReplaceHard;

    private String SUPPORT_FILTRATE_FOR_HARD = "82,83";

    @NacosValue(value = "${program.judge.Adviser.Qualification.roleCode}", autoRefreshed = true)
    private List<String> judgeAdviserQualification;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private BankCardProxy bankCardDao;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @NacosValue(value = "${aiJia.loan.solution.cetail.cache.key}", autoRefreshed = true)
    private String aiJiaLoanSolutionDetailCacheKey;

    @Override
    public UserHousePropertiesResponseVo getUserSpecificProgram(Integer userId, Integer customerHouseId) {
        UserHousePropertiesResponseVo responseVo = new UserHousePropertiesResponseVo();

        AppHousePropertyResultDto appHousePropertyResultDto = homeCardBossProxy.queryHouseDetail(customerHouseId);
        if (null != appHousePropertyResultDto) {
            HousePropertyInfoResultDto housePropertyInfoResultDto = appHousePropertyResultDto
                    .getHousePropertyInfoResultDto();
            if (null != housePropertyInfoResultDto) {
                responseVo.setBuildingId(housePropertyInfoResultDto.getBuildingId());
                responseVo.setZoneId(housePropertyInfoResultDto.getZoneId());
                responseVo.setHouseTypeId(housePropertyInfoResultDto.getLayoutId());
            }
        }

        Set<Integer> houseIdSet = new HashSet<>();
        houseIdSet.add(customerHouseId);

        // 批量查询该用户各个房产是否有资质 1:不可看，2:仅可看，3:可下单
        Map<Integer, SpecificUserResultBaseDto> specificJudgeNoViewMap = new HashMap<>();
        Map<Integer, SpecificUserResultBaseDto> specificJudgeViewMap = new HashMap<>();
        Map<Integer, SpecificUserResultBaseDto> specificJudgeOrderMap = new HashMap<>();

        if (!houseIdSet.isEmpty()) {
            SpecificUserDecisionResultDto userIsSpecificJudgeResultDto = homeCardBossProxy.queryUserHouseSpecific(userId, houseIdSet);

            if (null != userIsSpecificJudgeResultDto) {
                List<SpecificUserResultBaseDto> noViewList = userIsSpecificJudgeResultDto.getNoViewDtoList();
                if (null == noViewList) {
                    noViewList = new ArrayList<>();
                }
                for (SpecificUserResultBaseDto noViewItem : noViewList) {
                    specificJudgeNoViewMap.put(noViewItem.getHouseId(), noViewItem);
                }

                List<SpecificUserResultBaseDto> viewList = userIsSpecificJudgeResultDto.getViewDtoList();
                if (null == viewList) {
                    viewList = new ArrayList<>();
                }
                for (SpecificUserResultBaseDto viewItem : viewList) {
                    specificJudgeViewMap.put(viewItem.getHouseId(), viewItem);
                }

                List<SpecificUserResultBaseDto> orderList = userIsSpecificJudgeResultDto.getOrderDtoList();
                if (null == orderList) {
                    orderList = new ArrayList<>();
                }
                for (SpecificUserResultBaseDto orderItem : orderList) {
                    specificJudgeOrderMap.put(orderItem.getHouseId(), orderItem);
                }
            }
        }

        responseVo.setSpecific(OrderConstants.NO_VIEW);
        SpecificUserResultBaseDto specificJudge = specificJudgeOrderMap.get(customerHouseId);
        if (null != specificJudge) {
            responseVo.setSpecific(OrderConstants.AVALIABLE_ORDER);
            responseVo.setMasterOrderId(specificJudge.getOrderId());
            responseVo.setMsg(specificJudge.getMsg());
            responseVo.setCode(specificJudge.getCode());
        }

        specificJudge = specificJudgeViewMap.get(customerHouseId);
        if (null != specificJudge) {
            responseVo.setSpecific(OrderConstants.ONLY_VIEW);
            responseVo.setMasterOrderId(specificJudge.getOrderId());
            responseVo.setMsg(specificJudge.getMsg());
            responseVo.setCode(specificJudge.getCode());
        }

        specificJudge = specificJudgeNoViewMap.get(customerHouseId);
        if (null != specificJudge) {
            responseVo.setSpecific(OrderConstants.NO_VIEW);
            responseVo.setMasterOrderId(specificJudge.getOrderId());
            responseVo.setMsg(specificJudge.getMsg());
            responseVo.setCode(specificJudge.getCode());
        }

        responseVo.setHouseId(customerHouseId);
        return responseVo;
    }

    @Override
    public UserSpecificProgramResponseVo getUserSpecificProgram(HttpProductProgramRequest request, Integer userId) {
        List<SeriesProgramListResponse> responseList = new ArrayList<SeriesProgramListResponse>();

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);

        Integer width = 0;
        if (request.getWidth() != null) {
            width = request.getWidth() * ImageSize.WIDTH_PER_SIZE_50
                    / ImageSize.WIDTH_PER_SIZE_100;
        }

        boolean hardStandLookFlag = false;//不能看硬装对比（方案全部是纯软装方案），只要有一套是软装+硬装方案，则展示硬装对比

        BigDecimal solutionMinPrice = null;// 最低价格

        String decorationType = "";// 最低价格时候对应的 装修类型

        int solutionCount = 0;// 方案数量

        //判断用户是否是特定用户
        UserHousePropertiesResponseVo userInfo = this.getUserSpecificProgram(userId, request.getCustomerHouseId() == null ? request.getHouseId() : request.getCustomerHouseId());
        if (userInfo != null && userInfo.getSpecific() != null &&
                (userInfo.getSpecific() == ProductProgramPraise.PROGRAM_LIMIT_LOOK || userInfo.getSpecific() == ProductProgramPraise.PROGRAM_LIMIT_SELECT)) {
            //查询用户可选产品方案
            List<SolutionSketchInfoResponseVo> solutionSketchInfoResponseVos = productProgramProxy.getUserSpecificProgram(userId, userInfo.getBuildingId(), userInfo.getZoneId(), userInfo.getHouseTypeId());
            if (CollectionUtils.isNotEmpty(solutionSketchInfoResponseVos)) {
                for (SolutionSketchInfoResponseVo solutionSketchInfoResponseVo : solutionSketchInfoResponseVos) {
                    SeriesProgramListResponse seriesProgramListResponse = new SeriesProgramListResponse();
                    seriesProgramListResponse.setSeriesId(solutionSketchInfoResponseVo.getSeriesId());
                    seriesProgramListResponse.setSeriesName(solutionSketchInfoResponseVo.getSeriesName() + ProductProgramPraise.SERIES);

                    seriesProgramListResponse.setProgramNum(solutionSketchInfoResponseVo.getSeriesAvailableCount());
                    // 可选方案数量
                    if (seriesProgramListResponse.getProgramNum() != null) {
                        solutionCount += seriesProgramListResponse.getProgramNum();
                    }

                    List<SolutionBaseInfoVo> seriesSolutionList = solutionSketchInfoResponseVo.getSeriesSolutionList();
                    if (CollectionUtils.isNotEmpty(seriesSolutionList)) {
                        List<ProgramResponse> programList = new ArrayList<ProgramResponse>();
                        for (SolutionBaseInfoVo solutionBaseInfoVo : seriesSolutionList) {
                            ProgramResponse programResponse = new ProgramResponse();
                            //装修类型
                            String spaceCategory = "";
                            if (solutionBaseInfoVo.getDecorationType() != null) {
                                if (solutionBaseInfoVo.getDecorationType() == ProductProgramPraise.HARD_STANDARD_ALL) {
                                    spaceCategory = ProductProgramPraise.HARD_SOFT;
                                    hardStandLookFlag = true;//显示硬装对比
                                } else if (solutionBaseInfoVo.getDecorationType() == ProductProgramPraise.HARD_STANDARD_SOFT) {
                                    spaceCategory = ProductProgramPraise.HARD_STANDARD_SOFT_DESC;
                                }
                            }
                            programResponse.setCategory(spaceCategory);
							/*if(solutionBaseInfoVo.getSolutionDiscount() != null){
								programResponse.setDiscount(nf.format(solutionBaseInfoVo.getSolutionDiscount()) + ProductProgramPraise.DISCOUNT);
							}*/
                            programResponse.setFurnitureNum(solutionBaseInfoVo.getItemCount());
                            if (StringUtils.isNotBlank(solutionBaseInfoVo.getHeadImgURL())) {
                                programResponse.setHeadImgUrl(AliImageUtil.imageCompress(solutionBaseInfoVo.getHeadImgURL(), request.getOsType(), width, ImageConstant.SIZE_MIDDLE));
                            } else {
                                programResponse.setHeadImgUrl("");
                            }
                            // 设置用于轮播的图片
                            List<String> allImages = new ArrayList<String>();
                            List<String> images = solutionBaseInfoVo.getAllImages();
                            if (CollectionUtils.isNotEmpty(images)) {
                                for (String image : images) {
                                    allImages.add(AliImageUtil.imageCompress(image, request.getOsType(), width, ImageConstant.SIZE_MIDDLE));
                                }
                            }
                            programResponse.setAllImages(allImages);
                            programResponse.setName(solutionBaseInfoVo.getSolutionName());
                            if (solutionBaseInfoVo.getSolutionTotalDiscountPrice() != null) {
                                programResponse.setPrice(solutionBaseInfoVo.getSolutionTotalDiscountPrice().toString());
                                // 计算:已审核通过的方案中全屋总价最低的
                                if (solutionMinPrice == null) {
                                    solutionMinPrice = solutionBaseInfoVo.getSolutionTotalDiscountPrice();
                                    decorationType = programResponse.getCategory();
                                } else {
                                    if (solutionMinPrice.intValue() > solutionBaseInfoVo.getSolutionTotalDiscountPrice()
                                            .intValue()) {
                                        solutionMinPrice = solutionBaseInfoVo.getSolutionTotalDiscountPrice();
                                        decorationType = programResponse.getCategory();
                                    }
                                }
                            }
                            if (solutionBaseInfoVo.getSolutionTotalDiscountPrice() != null) {
                                programResponse.setDiscountPrice(solutionBaseInfoVo.getSolutionTotalDiscountPrice().toString());
                            }
                            programResponse.setProgramId(solutionBaseInfoVo.getSolutionId());
                            programResponse.setStyle(solutionBaseInfoVo.getStyleName() + ProductProgramPraise.STYLE);
                            if (StringUtils.isNotBlank(solutionBaseInfoVo.getSolutionDesignIdea())) {
                                programResponse.setSolutionDesignIdea(solutionBaseInfoVo.getSolutionDesignIdea());
                            }
                            if (StringUtils.isNotBlank(solutionBaseInfoVo.getAdvantage())) {
                                programResponse.setAdvantage(solutionBaseInfoVo.getAdvantage());
                            }
                            if (CollectionUtils.isNotEmpty(solutionBaseInfoVo.getTagList())) {
                                programResponse.setTagList(solutionBaseInfoVo.getTagList());
                            }
                            programList.add(programResponse);
                        }
                        seriesProgramListResponse.setProgramList(programList);
                    }
                    responseList.add(seriesProgramListResponse);
                }
            }

            UserSpecificProgramResponseVo result = new UserSpecificProgramResponseVo();
            result.setSeriesProgramList(responseList);
            if (solutionMinPrice != null) {
                result.setSolutionMinPrice(solutionMinPrice);
            } else {
                result.setSolutionMinPrice(new BigDecimal(0));
            }
            result.setDecorationType(decorationType);
            result.setSolutionCount(solutionCount);
            result.setSelectLimitFlag(userInfo.getSpecific());
            result.setHardStandLookFlag(hardStandLookFlag);
            if (solutionCount > 0) {
                //艾讲堂视频
                result.setVideoList(homePageService.getVideoList(request.getWidth()));
            }
            return result;
        }

        Integer code = userInfo.getCode();
        if (code != null && (code == 5 || code == 50 || code == 7)) {
            throw new BusinessException(10L, "什么都没有,内容正在酝酿中");
        }

        throw new BusinessException(HttpResponseCode.USER_NOT_SPECIFIC, MessageConstant.USER_NOT_SPECIFIC);
    }

    @Override
    //@Cacheable(cacheNames="o2o-api",keyGenerator = "springCacheKeyGenerator")
    public ProgramDetailResponse getProgramDetailById(HttpProgramDetailRequest request) {
        if (IntegerUtil.isEmpty(request.getWidth())) {
            request.setWidth(750);
        }
        if (request == null || request.getProgramId() == null) {
            return null;
        }
        Integer width = 0;
        if (request.getWidth() != null) {
            width = request.getWidth();
        }

//        // 先从缓存中查询
        String cacheKey = AppRedisUtil.generateCacheKey("getProgramDetailById:", request.getProgramId(), request.getOsType(), request.getWidth(), request.getAppVersion());
        String obj = AppRedisUtil.get(cacheKey);
        if (StringUtils.isNotBlank(obj)) {
            return JsonUtils.json2obj(obj, ProgramDetailResponse.class);
        }


        SolutionDetailResponseVo solutionDetailResponseVo = productProgramProxy.getProgramDetailById(request.getProgramId());
        if (solutionDetailResponseVo != null && !SolutionStatusEnum.ONLINE.getStatus().equals(solutionDetailResponseVo.getSolutionStatus())) {
            solutionDetailResponseVo = null;
        }
        if (null == solutionDetailResponseVo) {
            throw new BusinessException(HttpResponseCode.SOLUTION_OFFLINE, MessageConstant.SOLUTION_OFFLINE);
        }
        ProgramDetailResponse response = new ProgramDetailResponse();

        if (CollectionUtils.isNotEmpty(solutionDetailResponseVo.getSolutionRoomDetailVoList())) {
            List<SpaceDesign> spaceDesignList = new ArrayList<>();
            for (SolutionRoomDetailVo roomDetailVo : solutionDetailResponseVo.getSolutionRoomDetailVoList()) {
                if (CollectionUtils.isEmpty(roomDetailVo.getDefaultHardItemList())) {
                    continue;
                }
                SpaceDesign spaceDesign = new SpaceDesign();
                spaceDesign.setSpaceUsageName(roomDetailVo.getRoomUsageName());
                spaceDesign.setHeadImage(AliImageUtil.imageCompress(roomDetailVo.getRoomHeadImgURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                spaceDesign.setSpaceDesignId(roomDetailVo.getRoomId());
                List<HardItem> hardItemList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(roomDetailVo.getDefaultHardItemList())) {
                    for (RoomDefaultHardItemClass hardItemClass : roomDetailVo.getDefaultHardItemList()) {
                        HardItem hardItem = new HardItem();
                        hardItem.setHardItemName(hardItemClass.getHardItemClassName());
                        hardItem.setHardItemId(hardItemClass.getHardItemClassId());
                        HardItemSelection selection = new HardItemSelection();
                        selection.setHardSelectionName(hardItemClass.getHardItemName());
                        selection.setHardSelectionId(hardItemClass.getHardItemId());
                        selection.setHeadImage(QiniuImageUtils.compressImageAndSamePicTwo(hardItemClass.getHardItemHeadImage(), request.getWidth(), -1));
                        selection.setSmallImage(QiniuImageUtils.compressImageAndSamePic(hardItemClass.getHardItemHeadImage(), 100, 100));
                        selection.setHardSelectionDesc(hardItemClass.getHardItemDesc());
                        HardProcess hardProcess = new HardProcess();
                        hardProcess.setProcessName(hardItemClass.getCraftName());
                        hardProcess.setProcessImage(QiniuImageUtils.compressImageAndSamePicTwo(hardItemClass.getCraftImage(), request.getWidth(), -1));
                        hardProcess.setSmallImage(QiniuImageUtils.compressImageAndDiffPic(hardItemClass.getCraftImage(), 100, 100));
                        selection.setProcessSelected(hardProcess);
                        hardItem.setHardItemDefault(selection);
                        hardItemList.add(hardItem);
                    }
                }
                if (!VersionUtil.mustUpdate(request.getAppVersion(), "5.4.3") && CollectionUtils.isNotEmpty(roomDetailVo.getHardBomGroupList())) {
                    Map<Integer, List<BomGroupVO>> guiBomMapBySecondCategoryId = roomDetailVo.getHardBomGroupList().stream().filter(bomGroupVO -> bomGroupVO.getGroupType() != null && bomGroupVO.getGroupType().equals(9)).collect(groupingBy(BomGroupVO::getSecondCategoryId));
                    roomDetailVo.getHardBomGroupList().removeIf(bomGroupVO -> bomGroupVO.getGroupType() != null && bomGroupVO.getGroupType().equals(9));
                    for (BomGroupVO bomGroupVO : roomDetailVo.getHardBomGroupList()) {
                        HardItem hardItem = new HardItem();
                        hardItem.setBomFlag(1);
                        hardItem.setHardItemName(bomGroupVO.getCategoryName());
                        hardItem.setHardItemId(bomGroupVO.getCategoryId());
                        HardItemSelection selection = new HardItemSelection();
                        selection.setHardSelectionName(bomGroupVO.getCategoryName());
                        selection.setHardSelectionId(bomGroupVO.getCategoryId());
                        selection.setHeadImage(QiniuImageUtils.compressImageAndSamePicTwo(bomGroupVO.getGroupImage(), request.getWidth(), -1));
                        selection.setSmallImage(QiniuImageUtils.compressImageAndSamePic(bomGroupVO.getGroupImage(), 100, 100));
                        bomGroupVO.setGroupImage(QiniuImageUtils.compressImageAndSamePicTwo(bomGroupVO.getGroupImage(), request.getWidth(), -1));
                        HardBomGroup hardBomGroup = new HardBomGroup();
                        BeanUtils.copyProperties(bomGroupVO, hardBomGroup);
                        selection.setBomGroup(hardBomGroup);
                        selection.setHardSelectionDesc(bomGroupVO.getGroupDesc());
                        hardItem.setHardItemDefault(selection);
                        hardItemList.add(hardItem);
                    }
                    if (MapUtils.isNotEmpty(guiBomMapBySecondCategoryId)) {
                        guiBomMapBySecondCategoryId.forEach((secondCategoryId, hardBomGroupDataList) -> {
                            BomGroupVO bomGroupVO = hardBomGroupDataList.get(0);
                            HardItem hardItem = new HardItem();
                            hardItem.setBomFlag(102);
                            hardItem.setHardItemName("定制家具");
                            hardItem.setHardItemId(bomGroupVO.getSecondCategoryId());
                            HardItemSelection selection = new HardItemSelection();
                            selection.setHardSelectionName(bomGroupVO.getSecondCategoryName());
                            selection.setHardSelectionId(bomGroupVO.getCategoryId());
                            selection.setHeadImage(QiniuImageUtils.compressImageAndSamePicTwo(bomGroupVO.getGroupImage(), request.getWidth(), -1));
                            selection.setSmallImage(QiniuImageUtils.compressImageAndSamePic(bomGroupVO.getGroupImage(), 100, 100));
                            bomGroupVO.setGroupImage(QiniuImageUtils.compressImageAndSamePicTwo(bomGroupVO.getGroupImage(), request.getWidth(), -1));
                            HardBomGroup hardBomGroup = new HardBomGroup();
                            BeanUtils.copyProperties(bomGroupVO, hardBomGroup);
                            hardBomGroup.setGroupName(bomGroupVO.getSecondCategoryName());
                            selection.setBomGroup(hardBomGroup);
                            selection.setHardSelectionDesc(bomGroupVO.getGroupDesc());
                            if (CollectionUtils.isNotEmpty(roomDetailVo.getSolutionRoomPicVoList())) {
                                selection.setRoomHeadImage(AliImageUtil.imageCompress(roomDetailVo.getSolutionRoomPicVoList().get(0).getSolutionRoomPicURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                            } else {
                                selection.setRoomHeadImage(AliImageUtil.imageCompress(roomDetailVo.getRoomHeadImgURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                            }
                            selection.setGuiBomQueryList(hardBomGroupDataList.stream().map(mapper -> new QueryCabinetPropertyListRequest.GroupQueryRequest().setGroupId(mapper.getGroupId()).setCabinetType(mapper.getCabinetType()).setCabinetTypeName(mapper.getCabinetTypeName())).collect(Collectors.toList()));
                            hardItem.setHardItemDefault(selection);
                            hardItemList.add(hardItem);
                        });
                    }
                }
                spaceDesign.setHardItemList(hardItemList);
                spaceDesignList.add(spaceDesign);
            }
            response.setSpaceDesignList(spaceDesignList);
        }
        if (solutionDetailResponseVo != null) {
            int replaceRoomCount = 0;// 可替换空间数量
            StringBuilder replaceRoomPraise = new StringBuilder("");//可替换空间标识集合文案
            StringBuilder replaceFruniturePraise = new StringBuilder("");//可替换家具空间标识集合文案

            //产品方案新增浏览量
            homeCardWcmProxy.addVisitRecord(solutionDetailResponseVo.getSolutionId(), HomeCardPraise.VISIT_PROGRAM);

            //风格集合
            List<String> styleDescList = new ArrayList<String>();
            //品牌集合
            List<String> brandDescList = new ArrayList<String>();

            //空间描述为硬装描述的空间
            List<String> spaceHardCategoryList = getSpaceHardCategoryList();

            //装修类型
            String spaceCategory = "";
            if (solutionDetailResponseVo.getDecorationType() != null) {
                //如果是特殊户型  则默认为纯软装
                if (solutionDetailResponseVo.getDecorationType().intValue() == ProductProgramPraise.HARD_STANDARD_ALL && !isSpecialHouseType(solutionDetailResponseVo.getHouseTypeId())) {
                    spaceCategory = ProductProgramPraise.HARD_SOFT;
                } else if (solutionDetailResponseVo.getDecorationType().intValue() == ProductProgramPraise.HARD_STANDARD_SOFT) {
                    spaceCategory = ProductProgramPraise.HARD_STANDARD_SOFT_DESC;
                }
            }
            response.setCategory(spaceCategory);
            HouseLayoutVo houseLayout = solutionDetailResponseVo.getHouseLayout();//户型信息
            LOG.info("ProductProgramServiceImpl.getProgramDetailById houseLayout:{}", houseLayout);
            if (houseLayout != null) {
                if (houseLayout.getArea() != null) {
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(2);
                    nf.setGroupingUsed(false);
                    response.setHouseArea(nf.format(houseLayout.getArea()) + HomeCardPraise.HOUSE_AREA);//户型面积
                } else {
                    response.setHouseArea(0 + HomeCardPraise.HOUSE_AREA);//户型面积
                }
                StringBuffer housePattern = new StringBuffer("");
                if (houseLayout.getChamber() != null && houseLayout.getChamber() > 0) {
                    housePattern.append(houseLayout.getChamber().toString()).append(ProductProgramPraise.CHAMBER);
                }
                if (houseLayout.getHall() != null && houseLayout.getHall() > 0) {
                    housePattern.append(houseLayout.getHall().toString()).append(ProductProgramPraise.HALL);
                }
                if (houseLayout.getKitchen() != null && houseLayout.getKitchen() > 0) {
                    housePattern.append(houseLayout.getKitchen().toString()).append(ProductProgramPraise.KITCHEN);
                }
                if (houseLayout.getToilet() != null && houseLayout.getToilet() > 0) {
                    housePattern.append(houseLayout.getToilet().toString()).append(ProductProgramPraise.TOILET);
                }
                if (houseLayout.getBalcony() != null && houseLayout.getBalcony() > 0) {
                    housePattern.append(houseLayout.getBalcony().toString()).append(ProductProgramPraise.BALCONY);
                }
                response.setHousePattern(housePattern.toString());//老户型格局
            } else {
                response.setHouseArea(0 + HomeCardPraise.HOUSE_AREA);//户型面积
                response.setHousePattern("");//老户型格局
            }
            response.setSolutionDesignIdea(solutionDetailResponseVo.getSolutionDesignIdea());//设计理念
            //平面设计图
            LOG.info("ProductProgramServiceImpl.getProgramDetailById SolutionGraphicDesignUrl:{}", solutionDetailResponseVo.getSolutionGraphicDesignUrl());
            if (StringUtils.isNotBlank(solutionDetailResponseVo.getSolutionGraphicDesignUrl())) {
                response.setSolutionGraphicDesignUrl(QiniuImageUtils.compressImageAndSamePicTwo(solutionDetailResponseVo.getSolutionGraphicDesignUrl(), width, -1));
                //设计图宽高比
                Map<String, Object> imageSize = QiniuImageUtils.getImageSizeByType(solutionDetailResponseVo.getSolutionGraphicDesignUrl(), "?imageInfo", serviceCaller);
                int imageHeight = 0;
                if (imageSize.get("height") != null) {
                    imageHeight = (int) imageSize.get("height");
                }
                int imageWidth = 0;
                if (imageSize.get("width") != null) {
                    imageWidth = (int) imageSize.get("width");
                }
                if (imageHeight == 0) {
                    response.setGraphicDesignAspectRatio(new BigDecimal(1));
                } else {
                    BigDecimal aspectRatio = new BigDecimal(imageWidth).divide(new BigDecimal(imageHeight), 2,
                            BigDecimal.ROUND_HALF_UP);
                    response.setGraphicDesignAspectRatio(aspectRatio);
                }
            } else {
                response.setSolutionGraphicDesignUrl("");
                response.setGraphicDesignAspectRatio(new BigDecimal(1));
            }
            if (solutionDetailResponseVo.getSolutionTotalItemCount() != null) {
                response.setFurnitureTotalNum(solutionDetailResponseVo.getSolutionTotalItemCount());
            }
            List<String> hardList = new ArrayList<String>();//硬装文案：各空间名称集合
            response.setName(solutionDetailResponseVo.getSolutionName());
            if (solutionDetailResponseVo.getSolutionTotalDiscountPrice() != null) {
                response.setPrice(solutionDetailResponseVo.getSolutionTotalDiscountPrice().toString());
            }
            if (StringUtils.isNotBlank(solutionDetailResponseVo.getSolutionSeriesName())) {
                response.setSeriesName(solutionDetailResponseVo.getSolutionSeriesName() + ProductProgramPraise.SERIES);
            }
            if (StringUtils.isNotBlank(solutionDetailResponseVo.getSolutionStyleName())) {
                response.setStyleName(solutionDetailResponseVo.getSolutionStyleName() + ProductProgramPraise.STYLE);
                if (!styleDescList.contains(solutionDetailResponseVo.getSolutionStyleName())) {
                    styleDescList.add(solutionDetailResponseVo.getSolutionStyleName() + ProductProgramPraise.STYLE);
                }
            }
            if (StringUtils.isNotBlank(solutionDetailResponseVo.getHouseTypeName())) {
                if (solutionDetailResponseVo.getHouseTypeName().contains(HomeCardPraise.HOUSE_TYPE)) {
                    response.setHouseTypeName(solutionDetailResponseVo.getHouseTypeName());
                } else {
                    response.setHouseTypeName(solutionDetailResponseVo.getHouseTypeName() + HomeCardPraise.HOUSE_TYPE);
                }
            }
            // 户型版本，户型格局，是否拆改方案，户型ID
            response.setApartmentVersion(solutionDetailResponseVo.getApartmentVersion());
            response.setApartmentPattern(solutionDetailResponseVo.getApartmentPattern());
            response.setReformFlag(solutionDetailResponseVo.getReformFlag());
            response.setApartmentId(solutionDetailResponseVo.getApartmentId());

            List<SpaceEntity> spaceList = new ArrayList<SpaceEntity>();
            List<SolutionRoomDetailVo> solutionRoomDetailVoList = solutionDetailResponseVo.getSolutionRoomDetailVoList();
            if (CollectionUtils.isNotEmpty(solutionRoomDetailVoList)) {
                //可替换空间信息
                List<OptionalRoomResponseVo> optionalRoomResponseVos = productProgramProxy.queryRoomDesignWithSolutionId(request.getProgramId());

                for (SolutionRoomDetailVo solutionRoomDetailVo : solutionRoomDetailVoList) {
                    SpaceEntity spaceEntity = new SpaceEntity();
                    spaceEntity.setRoomId(solutionRoomDetailVo.getRoomId());
                    List<SolutionRoomItemVo> solutionRoomItemVoList = solutionRoomDetailVo.getSolutionRoomItemVoList();
                    List<BomGroupVO> bomGroupList = solutionRoomDetailVo.getBomGroupList();


                    List<FurnitureEntity> furnitureList = new ArrayList<FurnitureEntity>();//空间家具清单
                    List<String> furnitureGiftList = new ArrayList<String>();
                    List<FurnitureEntity> giftList = new ArrayList<FurnitureEntity>();//家具赠品清单
                    if (CollectionUtils.isNotEmpty(solutionRoomItemVoList)) {
                        for (SolutionRoomItemVo solutionRoomItemVo : solutionRoomItemVoList) {
                            String itemSize = ProductProgramPraise.FURNITURE_SIZE_TEXT;//家具尺寸
                            FurnitureEntity furnitureEntity = new FurnitureEntity();
                            if (StringUtils.isNotBlank(solutionRoomItemVo.getItemTopBrand())) {
                                //一级品牌（有可能没有）
                                furnitureEntity.setBrand(solutionRoomItemVo.getItemTopBrand());
                                if (!brandDescList.contains(solutionRoomItemVo.getItemTopBrand())) {
                                    brandDescList.add(solutionRoomItemVo.getItemTopBrand());
                                }
                            } else if (StringUtils.isNotBlank(solutionRoomItemVo.getItemBrand())) {
                                //二级品牌
                                furnitureEntity.setBrand(solutionRoomItemVo.getItemBrand());
                                if (!brandDescList.contains(solutionRoomItemVo.getItemBrand())) {
                                    brandDescList.add(solutionRoomItemVo.getItemBrand());
                                }
                            }
                            if (StringUtils.isNotBlank(solutionRoomItemVo.getItemColor())) {
                                furnitureEntity.setColor(solutionRoomItemVo.getItemColor());
                            }
                            if (StringUtils.isNotBlank(solutionRoomItemVo.getItemMaterial())) {
                                furnitureEntity.setMaterial(solutionRoomItemVo.getItemMaterial());
                            }
                            furnitureEntity.setSkuId(solutionRoomItemVo.getSkuId());
                            if (StringUtils.isNotBlank(solutionRoomItemVo.getItemImage())) {
                                furnitureEntity.setImgUrl(QiniuImageUtils.compressImageAndSamePicTwo(solutionRoomItemVo.getItemImage(), width, -1));//图片需切图处理
                            }
                            if (solutionRoomItemVo.getSkuPrice() != null) {
                                furnitureEntity.setPrice(solutionRoomItemVo.getSkuPrice());
                            }
                            if (solutionRoomItemVo.getParentSkuId() != null) {
                                furnitureEntity.setParentSkuId(solutionRoomItemVo.getParentSkuId());
                            }

                            if (solutionRoomItemVo.getLastCategoryId() != null) {
                                furnitureEntity.setLastCategoryId(solutionRoomItemVo.getLastCategoryId());
                            }

                            if (solutionRoomItemVo.getLastCategoryName() != null) {
                                furnitureEntity.setLastCategoryName(solutionRoomItemVo.getLastCategoryName());
                            }

                            //判断家具可替换数量
                            if (CollectionUtils.isNotEmpty(solutionRoomItemVo.getSubItemVos())) {
                                furnitureEntity.setReplaceCount(solutionRoomItemVo.getSubItemVos().size());
                            }

                            if (solutionRoomItemVo.getItemCount() != null && solutionRoomItemVo.getItemCount() > 0) {
                                furnitureEntity.setItemCount(solutionRoomItemVo.getItemCount());
                            }

                            //家具类型
                            if (solutionRoomItemVo.getFurnitureType() != null && solutionRoomItemVo.getFurnitureType() == ProductProgramPraise.FURNITURE_TYPE_GIFT) {
                                //赠品
                                if (StringUtils.isNotBlank(solutionRoomItemVo.getItemName())) {
                                    furnitureGiftList.add(solutionRoomItemVo.getItemName());
                                    furnitureEntity.setFurnitureName(solutionRoomItemVo.getItemName());
                                }
                                furnitureEntity.setFurnitureType(ProductProgramPraise.FURNITURE_TYPE_GIFT);
                                if (StringUtils.isNotBlank(solutionRoomItemVo.getItemSize())) {
                                    furnitureEntity.setItemSize(solutionRoomItemVo.getItemSize());
                                }

                                giftList.add(furnitureEntity);
                            } else {
                                //定制家具
                                if (solutionRoomItemVo.getFurnitureType() == ProductProgramPraise.FURNITURE_TYPE_ORDER) {
                                    if (StringUtils.isNotBlank(solutionRoomItemVo.getItemName())) {
                                        furnitureEntity.setFurnitureName(solutionRoomItemVo.getItemName().replaceAll(ProductProgramPraise.FURNITURE_ORDER_DESC_1, "").replaceAll(ProductProgramPraise.FURNITURE_ORDER_DESC_2, ""));
                                    }
                                    furnitureEntity.setItemType(ProductProgramPraise.FURNITURE_TYPE_ORDER_DESC);
                                    //若尺寸是1*1*1，调整为“依据现场尺寸定制”
                                    if (StringUtils.isNotBlank(solutionRoomItemVo.getItemSize())) {
                                        if (ProductProgramPraise.FURNITURE_SIZE.equals(solutionRoomItemVo.getItemSize())) {
                                            itemSize = ProductProgramPraise.FURNITURE_SIZE_DESC;
                                        } else {
                                            itemSize = solutionRoomItemVo.getItemSize();
                                        }
                                    }
                                    furnitureEntity.setFurnitureType(ProductProgramPraise.FURNITURE_TYPE_ORDER);
                                } else {
                                    if (StringUtils.isNotBlank(solutionRoomItemVo.getItemName())) {
                                        furnitureEntity.setFurnitureName(solutionRoomItemVo.getItemName());
                                    }
                                    furnitureEntity.setItemType("");
                                    furnitureEntity.setItemCount(solutionRoomItemVo.getItemCount());
                                    if (StringUtils.isNotBlank(solutionRoomItemVo.getItemSize())) {
                                        itemSize = solutionRoomItemVo.getItemSize();
                                    }
                                }
                                furnitureEntity.setItemSize(itemSize);
                                furnitureList.add(furnitureEntity);
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(bomGroupList)) {
                        Map<Integer, List<BomGroupVO>> guiBomMapBySecondCategoryId = bomGroupList.stream().filter(bomGroupVO -> bomGroupVO.getGroupType() != null && bomGroupVO.getGroupType().equals(10)).collect(groupingBy(BomGroupVO::getSecondCategoryId));
                        bomGroupList.removeIf(bomGroupVO -> bomGroupVO.getGroupType() != null && bomGroupVO.getGroupType().equals(10));
                        for (BomGroupVO bomGroup : bomGroupList) {
                            FurnitureEntity furnitureEntity = new FurnitureEntity();
                            furnitureEntity.setBomFlag(1);
                            furnitureEntity.setItemCount(bomGroup.getItemCount());
                            furnitureEntity.setLastCategoryName(bomGroup.getCategoryName());
                            furnitureEntity.setFurnitureName(bomGroup.getGroupDesc());
                            furnitureEntity.setSkuId(bomGroup.getGroupId());
                            furnitureEntity.setLastCategoryId(bomGroup.getCategoryId());
                            furnitureEntity.setFurnitureType(bomGroup.getFurnitureType());
                            if (StringUtils.isNotBlank(bomGroup.getGroupImage())) {
                                furnitureEntity.setImgUrl(QiniuImageUtils.compressImageAndSamePicTwo(bomGroup.getGroupImage(), width, -1));//图片需切图处理
                            }
                            furnitureList.add(furnitureEntity);
                        }
                        if (MapUtils.isNotEmpty(guiBomMapBySecondCategoryId)) {
                            guiBomMapBySecondCategoryId.forEach((secondCategoryId, hardBomGroupDataList) -> {
                                BomGroupVO bomGroupVO = hardBomGroupDataList.get(0);
                                FurnitureEntity furnitureEntity = new FurnitureEntity();
                                furnitureEntity.setBomFlag(102);
                                furnitureEntity.setItemCount(bomGroupVO.getItemCount());
                                furnitureEntity.setLastCategoryName("定制家具");
                                furnitureEntity.setFurnitureName(bomGroupVO.getSecondCategoryName());
                                furnitureEntity.setSkuId(bomGroupVO.getGroupId());
                                furnitureEntity.setLastCategoryId(bomGroupVO.getSecondCategoryId());
                                furnitureEntity.setFurnitureType(bomGroupVO.getFurnitureType());
                                if (StringUtils.isNotBlank(bomGroupVO.getGroupImage())) {
                                    furnitureEntity.setImgUrl(QiniuImageUtils.compressImageAndSamePicTwo(bomGroupVO.getGroupImage(), request.getWidth(), -1));//图片需切图处理
                                }
                                if (CollectionUtils.isNotEmpty(solutionRoomDetailVo.getSolutionRoomPicVoList())) {
                                    furnitureEntity.setRoomHeadImage(AliImageUtil.imageCompress(solutionRoomDetailVo.getSolutionRoomPicVoList().get(0).getSolutionRoomPicURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                                } else {
                                    furnitureEntity.setRoomHeadImage(AliImageUtil.imageCompress(solutionRoomDetailVo.getRoomHeadImgURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                                }
                                furnitureEntity.setGuiBomQueryList(hardBomGroupDataList.stream().map(mapper -> new QueryCabinetPropertyListRequest.GroupQueryRequest().setGroupId(mapper.getGroupId()).setCabinetType(mapper.getCabinetType()).setCabinetTypeName(mapper.getCabinetTypeName())).collect(Collectors.toList()));
                                furnitureList.add(furnitureEntity);
                            });
                        }
                    }
                    spaceEntity.setFurnitureGiftList(furnitureGiftList);
                    spaceEntity.setFurnitureList(furnitureList);
                    if (solutionRoomDetailVo.getRoomItemCount() != null) {
                        spaceEntity.setFurnitureNum(solutionRoomDetailVo.getRoomItemCount());
                    }
                    spaceEntity.setSpaceCategory(response.getCategory());
                    if (StringUtils.isNotBlank(solutionRoomDetailVo.getRoomDesc())) {
                        spaceEntity.setSpaceDesc(solutionRoomDetailVo.getRoomDesc());
                    }
                    if (StringUtils.isNotBlank(solutionRoomDetailVo.getRoomUsageName()) && CollectionUtils.isNotEmpty(spaceHardCategoryList) && spaceHardCategoryList.contains(solutionRoomDetailVo.getRoomUsageName())) {
                        spaceEntity.setSpaceDescType(ProductProgramPraise.SPACE_DESC_TYPE_HARD);//硬装描述
                    } else {
                        spaceEntity.setSpaceDescType(ProductProgramPraise.SPACE_DESC_TYPE_DESIGN);//设计描述
                    }
                    List<String> spaceImgList = new ArrayList<String>();//空间照片集合
                    List<ImageAspectRatioEntity> spaceImageList = new ArrayList<ImageAspectRatioEntity>();//空间照片集合
                    List<SolutionRoomPicVo> solutionRoomPicVoList = solutionRoomDetailVo.getSolutionRoomPicVoList();
                    LOG.info("ProductProgramServiceImpl.getProgramDetailById solutionRoomPicVoList:{}", solutionRoomPicVoList);
                    if (CollectionUtils.isNotEmpty(solutionRoomPicVoList)) {
                        for (SolutionRoomPicVo solutionRoomPicVo : solutionRoomPicVoList) {
                            //定宽不定高 等比压缩
                            if (StringUtils.isNotBlank(solutionRoomPicVo.getSolutionRoomPicURL())) {
                                spaceImgList.add(AliImageUtil.imageCompress(solutionRoomPicVo.getSolutionRoomPicURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                                this.addAspectRatioImage(spaceImageList, solutionRoomPicVo, width);
                            } else {
                                spaceImgList.add("");
                            }
                        }
                    }
                    spaceEntity.setSpaceImgList(spaceImgList);
                    spaceEntity.setSpaceImageList(spaceImageList);
                    spaceEntity.setSpaceName(solutionRoomDetailVo.getRoomUsageName());
                    if (solutionRoomDetailVo.getRoomPrice() != null) {
                        spaceEntity.setSpacePrice(solutionRoomDetailVo.getRoomPrice().toString());
                    }

                    spaceEntity.setGiftList(giftList);
                    //空间可替换家具数量
                    if (solutionRoomDetailVo.getSubSkuCount() != null && solutionRoomDetailVo.getSubSkuCount() > 0) {
                        spaceEntity.setCommutativeNum(solutionRoomDetailVo.getSubSkuCount());
                        if (StringUtils.isBlank(replaceFruniturePraise)) {
                            replaceFruniturePraise.append(solutionRoomDetailVo.getRoomUsageName());
                        } else {
                            replaceFruniturePraise.append("、").append(solutionRoomDetailVo.getRoomUsageName());
                        }
                    }

                    //空间是否可替换
                    if (CollectionUtils.isNotEmpty(solutionRoomDetailVo.getReplaceRoomSketchList())) {
                        if (solutionRoomDetailVo.getReplaceRoomSketchList().size() > 1) {
                            replaceRoomCount++;
                            if (StringUtils.isBlank(replaceRoomPraise)) {
                                replaceRoomPraise.append(solutionRoomDetailVo.getRoomUsageName());
                            } else {
                                replaceRoomPraise.append("、").append(solutionRoomDetailVo.getRoomUsageName());
                            }
                        }
                        setResplaceInfo(spaceEntity, optionalRoomResponseVos, width);
                    }

                    spaceList.add(spaceEntity);

                    hardList.add(solutionRoomDetailVo.getRoomUsageName());
                }
            }
            response.setReplaceRoomCount(replaceRoomCount);
            response.setReplaceFruniturePraise(replaceFruniturePraise.toString());
            response.setReplaceRoomPraise(replaceRoomPraise.toString());
            response.setHardList(hardList);
            response.setSpaceList(spaceList);
            //response.setSolutionDiscount(solutionDetailResponseVo.getSolutionDiscount());//方案折扣
            //3D巡游地址
            if (StringUtils.isNotBlank(solutionDetailResponseVo.getSolutionGlobalViewURL())) {
                response.setVrLinkUrl(solutionDetailResponseVo.getSolutionGlobalViewURL());
            }
            //小贴士信息
            setKonwledgeInfo(styleDescList, brandDescList, response);

            //增配包信息
            List<SolutionAddBagInfoVo> addBagInfoVo = solutionDetailResponseVo.getSolutionExtraItemList();
            if (addBagInfoVo != null && CollectionUtils.isNotEmpty(addBagInfoVo)) {
                List<AddBagDetail> addBagInfo = new ArrayList<AddBagDetail>();
                for (SolutionAddBagInfoVo solutionAddBagInfoVo : addBagInfoVo) {
                    if (solutionAddBagInfoVo.getSkuId() != null && solutionAddBagInfoVo.getSkuId() > 0) {
                        AddBagDetail addBagDetail = new AddBagDetail();
                        int count = 0;
                        BigDecimal price = new BigDecimal(0);
                        addBagDetail.setSkuId(solutionAddBagInfoVo.getSkuId());
                        if (StringUtils.isNotBlank(solutionAddBagInfoVo.getSkuName())) {
                            addBagDetail.setSkuName(solutionAddBagInfoVo.getSkuName());
                        }
                        if (StringUtils.isNotBlank(solutionAddBagInfoVo.getSkuHeadImgURL())) {
                            addBagDetail.setSkuImgUrl(solutionAddBagInfoVo.getSkuHeadImgURL());
                        }
                        if (solutionAddBagInfoVo.getSkuCount() != null && solutionAddBagInfoVo.getSkuCount() > 0) {
                            addBagDetail.setSkuCount(solutionAddBagInfoVo.getSkuCount());
                            count = solutionAddBagInfoVo.getSkuCount();
                        }
                        if (solutionAddBagInfoVo.getSkuUnitPrice() != null) {
                            addBagDetail.setSkuUnitPrice(solutionAddBagInfoVo.getSkuUnitPrice());
                            price = solutionAddBagInfoVo.getSkuUnitPrice();
                        }
                        addBagDetail.setSkuTotalPrice(price.multiply(new BigDecimal(count)).setScale(2, BigDecimal.ROUND_HALF_UP));
                        addBagInfo.add(addBagDetail);
                    }
                }
                response.setAddBagInfo(addBagInfo);
            }

            //标准升级项待选
            SolutionStandardUpgradesResponseVo upgradeInfoVos = solutionDetailResponseVo.getSolutionStandardUpgradesResponseVo();
            SolutionStandardUpgradeTotalResponse upgradeInfosForChoice = this.getSolutionStandardUpgradeTotalResponse(upgradeInfoVos);
            response.setUpgradeInfosForChoice(upgradeInfosForChoice);

            if (StringUtils.isNotBlank(solutionDetailResponseVo.getAdvantage())) {
                response.setAdvantage(solutionDetailResponseVo.getAdvantage());
            }
            if (CollectionUtils.isNotEmpty(solutionDetailResponseVo.getTagList())) {
                response.setTagList(solutionDetailResponseVo.getTagList());
            }

            //查询方案浏览量
            response.setVisitNum(homeCardWcmProxy.queryVisitCountByDnaId(solutionDetailResponseVo.getSolutionId(), HomeCardPraise.VISIT_PROGRAM));
        }

        // 缓存一小时
        AppRedisUtil.set(cacheKey, JsonUtils.obj2json(response), 3600);

        return response;
    }

    @Override
    public UserInfoResponse getUserInfo(AladdinUserInfoRequest request) {
        String mobileNum = "";
        if (StringUtils.isNotBlank(request.getMobileNum())) {
            mobileNum = request.getMobileNum();
        }
        int customerHouseId = request.getCustomerHouseId() == null ? request.getHouseId() : request.getCustomerHouseId();
        //版本控制 appVersion>3.1.0的版本房产信息格式变更，3.1.0及其之前版本房产信息格式不变
        String appVersion = "";
        if (null == request || null == request.getOsType() || request.getOsType() == 3) {
            appVersion = "3.1.1";
        } else {
            if (request.getOsType() != 3) {
                appVersion = request.getAppVersion();
            }
        }

        UserInfoResponse response = new UserInfoResponse();

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);

        if (customerHouseId == 0) {
            CustomerDetailInfo customerDetailInfo = productProgramProxy.getUserInfo(mobileNum);
            if (customerDetailInfo != null) {
                response.setUserId(customerDetailInfo.getCustomerId());
                response.setUserName(customerDetailInfo.getName());
                response.setMobileNum(customerDetailInfo.getMobile());
                if (StringUtils.isNotBlank(customerDetailInfo.getMobile())) {
                    response.setHideMobileNum(customerDetailInfo.getMobile().replaceAll(ProductProgramPraise.MOBILE_REGEX, ProductProgramPraise.MOBILE_REPLACE));
                }
                if (customerDetailInfo.getPayMoney() != null) {
                    response.setAmount(nf.format(customerDetailInfo.getPayMoney()));
                }
                response.setBuildingId(customerDetailInfo.getBuildingId());
                response.setCompanyCode(customerDetailInfo.getCompanyCode());
                //房屋位置信息
                CustomerHouseAddressInfo houseAddress = customerDetailInfo.getHouseAddress();
                if (houseAddress != null && StringUtils.isNotBlank(houseAddress.getAddress())) {
                    response.setBuildingAddress(houseAddress.getAddress().replace(ProductProgramPraise.HOUSE_NAME_BBC, ""));
                }
                //置家顾问信息
                CustomerAdviserInfo adviser = customerDetailInfo.getAdviser();
                if (adviser != null && StringUtils.isNotBlank(adviser.getMobile())) {
                    response.setAdviserMobileNum(adviser.getMobile());
                } else {
                    response.setAdviserMobileNum(ProductProgramPraise.ADVISER_MOBILE_DEFAULT);
                }
                if (adviser != null && StringUtils.isNotBlank(adviser.getName())) {
                    response.setAdviserMobileName(adviser.getName());
                }
                //户型信息
                CustomerHouseLayoutInfo houseLayout = customerDetailInfo.getHouseLayout();
                String houseTypeName = "";
                if (houseLayout != null) {
                    if (StringUtils.isNotBlank(houseLayout.getHouseName())) {
                        if (houseLayout.getHouseName().contains(HomeCardPraise.HOUSE_TYPE)) {
                            houseTypeName = houseLayout.getHouseName();
                        } else {
                            houseTypeName = houseLayout.getHouseName() + HomeCardPraise.HOUSE_TYPE;
                        }
                    }
                    response.setHouseName(houseTypeName);
                    String housePattern = "";
                    if (houseLayout.getChamber() != null && houseLayout.getChamber() > 0) {
                        housePattern += houseLayout.getChamber().toString() + ProductProgramPraise.CHAMBER;
                    }
                    if (houseLayout.getHall() != null && houseLayout.getHall() > 0) {
                        housePattern += houseLayout.getHall().toString() + ProductProgramPraise.HALL;
                    }
                    if (houseLayout.getToilet() != null && houseLayout.getToilet() > 0) {
                        housePattern += houseLayout.getToilet().toString() + ProductProgramPraise.TOILET;
                    }
                    response.setHousePattern(housePattern);
                    if (houseLayout.getArea() != null) {
                        response.setHouseArea(nf.format(houseLayout.getArea()) + ProductProgramPraise.AREA);
                    } else {
                        response.setHouseArea(0 + ProductProgramPraise.AREA);
                    }
                }
                response.setHouseFullName(houseTypeName + " " + response.getHousePattern() + " " + response.getHouseArea());
                if (customerDetailInfo.getPayMoney() != null) {
                    response.setAmount(nf.format(customerDetailInfo.getPayMoney()));
                }
                response.setReceiptTime(customerDetailInfo.getPayTime());
            }
        } else {
            AladdinHouseInfoResponseVo aladdinHouseInfoResponseVo = houseService.queryHouseByHouseId(customerHouseId);
            if (aladdinHouseInfoResponseVo != null) {
                //用户信息
                AladdinCustomerInfoVo userInfo = aladdinHouseInfoResponseVo.getUserInfo();
                if (userInfo != null) {
                    response.setUserId(userInfo.getUserId());
                    response.setUserName(userInfo.getName());
                    response.setMobileNum(userInfo.getMobile());
                    if (StringUtils.isNotBlank(userInfo.getMobile())) {
                        response.setHideMobileNum(userInfo.getMobile().replaceAll(ProductProgramPraise.MOBILE_REGEX, ProductProgramPraise.MOBILE_REPLACE));
                    }
                }
                //付款信息
                AladdinDealInfoVo transactionInfoVo = aladdinHouseInfoResponseVo.getTransactionInfoVo();
                if (transactionInfoVo != null) {
                    if (transactionInfoVo.getPayedAmount() != null) {
                        response.setAmount(nf.format(transactionInfoVo.getPayedAmount()));
                    }
                    if (transactionInfoVo.getDstTime() != null) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                        response.setReceiptTime(dateFormat.format(transactionInfoVo.getDstTime()));
                    }
                }
                //房产户型信息
                HouseInfoResponseVo houseInfo = aladdinHouseInfoResponseVo.getHouseInfo();
                String houseFullAddress = "";
                String houseBuildingNum = "";
                if (houseInfo != null) {
                    response.setBuildingId(houseInfo.getHouseProjectId());

                    HouseInfo houseInfoResponse = homeBuildingService.setHouseInfoStandard(houseInfo);

                    houseFullAddress = houseInfoResponse.getBuildingAddress();
                    response.setBuildingName(houseInfoResponse.getBuildingName());
                    houseBuildingNum = houseInfoResponse.getUnitRoomNo();
                }
                //置家顾问信息
                AladdinAdviserInfoVo adviserInfoVo = aladdinHouseInfoResponseVo.getAdviserInfoVo();
                if (adviserInfoVo != null && StringUtils.isNotBlank(adviserInfoVo.getMobile())) {
                    response.setAdviserMobileNum(adviserInfoVo.getMobile());
                } else {
                    response.setAdviserMobileNum(ProductProgramPraise.ADVISER_MOBILE_DEFAULT);
                }
                if (adviserInfoVo != null && StringUtils.isNotBlank(adviserInfoVo.getUserName())) {
                    response.setAdviserMobileName(adviserInfoVo.getUserName());
                }
                //户型信息
                HouseInfoResponseVo houseLayout = aladdinHouseInfoResponseVo.getHouseInfo();
                String houseTypeName = "";
                if (houseLayout != null) {
                    if (StringUtils.isNotBlank(houseLayout.getHouseTypeName())) {
                        if (houseLayout.getHouseTypeName().contains(HomeCardPraise.HOUSE_TYPE)) {
                            houseTypeName = houseLayout.getHouseTypeName();
                        } else {
                            houseTypeName = houseLayout.getHouseTypeName() + HomeCardPraise.HOUSE_TYPE;
                        }
                    }
                    response.setHouseName(houseTypeName);
                    String housePattern = "";
                    if (houseLayout.getLayoutRoom() != null && houseLayout.getLayoutRoom() > 0) {
                        housePattern += houseLayout.getLayoutRoom().toString() + ProductProgramPraise.CHAMBER;
                    }
                    if (houseLayout.getLayoutLiving() != null && houseLayout.getLayoutLiving() > 0) {
                        housePattern += houseLayout.getLayoutLiving().toString() + ProductProgramPraise.HALL;
                    }
                    if (houseLayout.getLayoutToliet() != null && houseLayout.getLayoutToliet() > 0) {
                        housePattern += houseLayout.getLayoutToliet().toString() + ProductProgramPraise.TOILET;
                    }
                    response.setHousePattern(housePattern);
                    if (houseLayout.getSize() != null) {
                        response.setHouseArea(houseLayout.getSize() + ProductProgramPraise.AREA);
                    } else {
                        response.setHouseArea(0 + ProductProgramPraise.AREA);
                    }
                    response.setHouseId(houseLayout.getId());
                    //订单信息
                    response.setMasterOrderId(houseLayout.getMasterOrderId());
                    //订单状态扭转
                    if (houseLayout.getMasterOrderStatus() != null) {
                        response.setMasterOrderState(homeBuildingService.getOrderStatus(houseLayout.getMasterOrderStatus()));
                    }
                }
                if (VersionUtil.mustUpdate("3.1.0", appVersion)) {
                    response.setBuildingAddress(houseFullAddress);
                    response.setHouseFullName(houseBuildingNum);
                } else {
                    response.setBuildingAddress(houseFullAddress + houseBuildingNum);
                    response.setHouseFullName(houseTypeName + " " + response.getHousePattern() + " " + response.getHouseArea());
                }
            }
        }

        return response;
    }

    @Override
    public SpaceDetailResponse getSpaceDetailById(HttpSpaceDetailRequest request) {
        SpaceDetailResponse response = new SpaceDetailResponse();

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);

        Integer width = 0;
        if (request.getWidth() != null) {
            width = request.getWidth();
        }

        SolutionRoomDetailResponseVo solutionRoomDetailResponseVo = productProgramProxy.getRoomDetailByCondition(request.getProgramId(), request.getSpaceId());
        if (solutionRoomDetailResponseVo != null) {
            //空间描述为硬装描述的空间
            List<String> spaceHardCategoryList = getSpaceHardCategoryList();

            response.setProgramId(solutionRoomDetailResponseVo.getSolutionId());
            response.setProgramName(solutionRoomDetailResponseVo.getSolutionName());
            response.setSeriesName(solutionRoomDetailResponseVo.getSolutionSeriesName() + ProductProgramPraise.SERIES);
            response.setStyleName(solutionRoomDetailResponseVo.getSolutionStyleName() + ProductProgramPraise.STYLE);
            response.setSpaceId(solutionRoomDetailResponseVo.getSolutionRoomId());
            response.setSpaceName(solutionRoomDetailResponseVo.getSolutionRoomTypeName());
            response.setSpaceUseId(solutionRoomDetailResponseVo.getSolutionRoomUseId());//空间用途ID
            response.setSpaceUseName(solutionRoomDetailResponseVo.getSolutionRoomUseName());//空间用途名称
            if (solutionRoomDetailResponseVo.getRoomPrice() != null) {
                response.setSpacePrice(solutionRoomDetailResponseVo.getRoomPrice().toString());//空间价格
            }
            List<String> spaceImgList = new ArrayList<String>();
            List<ImageAspectRatioEntity> spaceImageList = new ArrayList<ImageAspectRatioEntity>();
            List<SolutionRoomPicVo> solutionRoomPicList = solutionRoomDetailResponseVo.getSolutionRoomPicList();
            LOG.info("ProductProgramServiceImpl.getSpaceDetailById solutionRoomPicList:{}", solutionRoomPicList);
            if (CollectionUtils.isNotEmpty(solutionRoomPicList)) {
                for (SolutionRoomPicVo solutionRoomPicVo : solutionRoomPicList) {
                    //定宽不定高 等比压缩
                    if (StringUtils.isNotBlank(solutionRoomPicVo.getSolutionRoomPicURL())) {
                        spaceImgList.add(AliImageUtil.imageCompress(solutionRoomPicVo.getSolutionRoomPicURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                        this.addAspectRatioImage(spaceImageList, solutionRoomPicVo, width);

                    } else {
                        spaceImgList.add("");
                    }
                }
            }
            response.setSpaceImgList(spaceImgList);
            response.setSpaceImageList(spaceImageList);
            List<FurnitureEntity> furnitureList = new ArrayList<FurnitureEntity>();
            List<String> furnitureGiftList = new ArrayList<String>();
            List<FurnitureEntity> giftList = new ArrayList<FurnitureEntity>();//家具赠品清单
            List<SolutionRoomItemVo> solutionRoomItemList = solutionRoomDetailResponseVo.getSolutionRoomItemList();
            LOG.info("ProductProgramServiceImpl.getSpaceDetailById solutionRoomItemList:{}", solutionRoomItemList);
            if (CollectionUtils.isNotEmpty(solutionRoomItemList)) {
                for (SolutionRoomItemVo solutionRoomItemVo : solutionRoomItemList) {
                    String itemSize = ProductProgramPraise.FURNITURE_SIZE_TEXT;//家具尺寸
                    FurnitureEntity furnitureEntity = new FurnitureEntity();
                    if (StringUtils.isNotBlank(solutionRoomItemVo.getItemTopBrand())) {
                        //一级品牌（有可能没有）
                        furnitureEntity.setBrand(solutionRoomItemVo.getItemTopBrand());
                    } else if (StringUtils.isNotBlank(solutionRoomItemVo.getItemBrand())) {
                        //二级品牌
                        furnitureEntity.setBrand(solutionRoomItemVo.getItemBrand());
                    }
                    if (StringUtils.isNotBlank(solutionRoomItemVo.getItemColor())) {
                        furnitureEntity.setColor(solutionRoomItemVo.getItemColor());
                    }
                    if (StringUtils.isNotBlank(solutionRoomItemVo.getItemMaterial())) {
                        furnitureEntity.setMaterial(solutionRoomItemVo.getItemMaterial());
                    }
                    furnitureEntity.setSkuId(solutionRoomItemVo.getSkuId());
                    if (StringUtils.isNotBlank(solutionRoomItemVo.getItemImage())) {
                        furnitureEntity.setImgUrl(QiniuImageUtils.compressImageAndSamePicTwo(solutionRoomItemVo.getItemImage(), width, -1));//图片需切图处理
                    }
                    if (solutionRoomItemVo.getSkuPrice() != null) {
                        furnitureEntity.setPrice(solutionRoomItemVo.getSkuPrice());
                    }

                    //判断家具可替换数量
                    if (CollectionUtils.isNotEmpty(solutionRoomItemVo.getSubItemVos())) {
                        furnitureEntity.setReplaceCount(solutionRoomItemVo.getSubItemVos().size());
                    }
                    if (solutionRoomItemVo.getParentSkuId() != null) {
                        furnitureEntity.setParentSkuId(solutionRoomItemVo.getParentSkuId());
                    }
                    if (solutionRoomItemVo.getItemCount() != null && solutionRoomItemVo.getItemCount() > 0) {
                        furnitureEntity.setItemCount(solutionRoomItemVo.getItemCount());
                    }
                    //家具类型
                    if (solutionRoomItemVo.getFurnitureType() != null && solutionRoomItemVo.getFurnitureType() == ProductProgramPraise.FURNITURE_TYPE_GIFT) {
                        //赠品
                        if (StringUtils.isNotBlank(solutionRoomItemVo.getItemName())) {
                            furnitureGiftList.add(solutionRoomItemVo.getItemName());
                            furnitureEntity.setFurnitureName(solutionRoomItemVo.getItemName());
                        }
                        furnitureEntity.setFurnitureType(ProductProgramPraise.FURNITURE_TYPE_GIFT);
                        if (StringUtils.isNotBlank(solutionRoomItemVo.getItemSize())) {
                            furnitureEntity.setItemSize(solutionRoomItemVo.getItemSize());
                        }
                        giftList.add(furnitureEntity);
                    } else {
                        //定制家具
                        if (solutionRoomItemVo.getFurnitureType() == ProductProgramPraise.FURNITURE_TYPE_ORDER) {
                            if (StringUtils.isNotBlank(solutionRoomItemVo.getItemName())) {
                                furnitureEntity.setFurnitureName(solutionRoomItemVo.getItemName().replaceAll(ProductProgramPraise.FURNITURE_ORDER_DESC_1, "").replaceAll(ProductProgramPraise.FURNITURE_ORDER_DESC_2, ""));
                            }
                            furnitureEntity.setItemType(ProductProgramPraise.FURNITURE_TYPE_ORDER_DESC);
                            //若尺寸是1*1*1，调整为“依据现场尺寸定制”
                            if (StringUtils.isNotBlank(solutionRoomItemVo.getItemSize())) {
                                if (ProductProgramPraise.FURNITURE_SIZE.equals(solutionRoomItemVo.getItemSize())) {
                                    itemSize = ProductProgramPraise.FURNITURE_SIZE_DESC;
                                } else {
                                    itemSize = solutionRoomItemVo.getItemSize();
                                }
                            }
                            furnitureEntity.setFurnitureType(ProductProgramPraise.FURNITURE_TYPE_ORDER);
                        } else {
                            if (StringUtils.isNotBlank(solutionRoomItemVo.getItemName())) {
                                furnitureEntity.setFurnitureName(solutionRoomItemVo.getItemName());
                            }
                            furnitureEntity.setItemType("");
                            if (StringUtils.isNotBlank(solutionRoomItemVo.getItemSize())) {
                                itemSize = solutionRoomItemVo.getItemSize();
                            }
                        }
                        furnitureEntity.setItemSize(itemSize);
                        furnitureList.add(furnitureEntity);
                    }
                }
            }
            response.setFurnitureList(furnitureList);
            if (solutionRoomDetailResponseVo.getSolutionRoomItemCount() != null) {
                response.setFurnitureNum(solutionRoomDetailResponseVo.getSolutionRoomItemCount());//家具数量
            }
            response.setFurnitureGiftList(furnitureGiftList);
            //装修类型
            String spaceCategory = "";
            if (solutionRoomDetailResponseVo.getDecorationType() != null) {
                if (solutionRoomDetailResponseVo.getDecorationType() == ProductProgramPraise.HARD_STANDARD_ALL) {
                    spaceCategory = ProductProgramPraise.HARD_SOFT;
                } else if (solutionRoomDetailResponseVo.getDecorationType() == ProductProgramPraise.HARD_STANDARD_SOFT) {
                    spaceCategory = ProductProgramPraise.HARD_STANDARD_SOFT_DESC;
                }
            }
            response.setSpaceCategory(spaceCategory);
            if (StringUtils.isNotBlank(solutionRoomDetailResponseVo.getSolutionRoomDesc())) {
                response.setSpaceDesc(solutionRoomDetailResponseVo.getSolutionRoomDesc());
            }
            if (StringUtils.isNotBlank(solutionRoomDetailResponseVo.getSolutionRoomTypeName()) && CollectionUtils.isNotEmpty(spaceHardCategoryList) && spaceHardCategoryList.contains(solutionRoomDetailResponseVo.getSolutionRoomTypeName())) {
                response.setSpaceDescType(ProductProgramPraise.SPACE_DESC_TYPE_HARD);//硬装描述
            } else {
                response.setSpaceDescType(ProductProgramPraise.SPACE_DESC_TYPE_DESIGN);//设计描述
            }
			/*if(solutionRoomDetailResponseVo.getSolutionDiscount() != null){
				response.setDiscount(nf.format(solutionRoomDetailResponseVo.getSolutionDiscount()) + ProductProgramPraise.DISCOUNT);
			}*/
            response.setGiftList(giftList);
            //可替换数量
            if (solutionRoomDetailResponseVo.getSubSkuCount() != null) {
                response.setCommutativeNum(solutionRoomDetailResponseVo.getSubSkuCount());
            }
        }

        return response;
    }

    /**
     * 增加宽高比对象
     *
     * @param spaceImageList    封装宽高比图片对象集合
     * @param solutionRoomPicVo
     * @param width
     */
    private void addAspectRatioImage(List<ImageAspectRatioEntity> spaceImageList, SolutionRoomPicVo solutionRoomPicVo,
                                     Integer width) {
        if (width == null) {
            width = 0;
        }
        ImageAspectRatioEntity entity = new ImageAspectRatioEntity();
        if (solutionRoomPicVo != null && StringUtils.isNotBlank(solutionRoomPicVo.getSolutionRoomPicURL())) {
            String url = AliImageUtil.imageCompress(solutionRoomPicVo.getSolutionRoomPicURL(), 1, width, ImageConstant.SIZE_MIDDLE);
            entity.setSpaceImageUrl(url);
            Map<String, Object> imageSize = QiniuImageUtils.getImageSizeByType(solutionRoomPicVo.getSolutionRoomPicURL(), "?imageInfo", serviceCaller);
            int heightByQiniu = 0;
            if (imageSize.get("height") != null) {
                heightByQiniu = (int) imageSize.get("height");
            }
            int widthByQiniu = 0;
            if (imageSize.get("width") != null) {
                widthByQiniu = (int) imageSize.get("width");
            }
            if (heightByQiniu == 0) {
                entity.setAspectRatio(new BigDecimal(1));
            } else {
                BigDecimal aspectRatio = new BigDecimal(widthByQiniu).divide(new BigDecimal(heightByQiniu), 2,
                        BigDecimal.ROUND_HALF_UP);
                entity.setAspectRatio(aspectRatio);
            }
        }

        spaceImageList.add(entity);
    }

    @Override
    public List<HardStandardResponse> queryHardStandardGroup() {
        //查询标准套系
        DicListDto dicListResponseVo = dicProxy.getDicListByKey(HomeCardPraise.HARD_STANDARD);
        if (dicListResponseVo != null && CollectionUtils.isNotEmpty(dicListResponseVo.getDicList())) {
            List<HardStandardResponse> responses = new ArrayList<HardStandardResponse>();
            HardStandardDetail title = new HardStandardDetail();
            List<String> titleMaterialList = new ArrayList<String>();
            titleMaterialList.add(HomeCardPraise.HARD_DESC);
            List<DicDto> dicEntityVos = dicListResponseVo.getDicList();
            for (DicDto dicEntityVo : dicEntityVos) {
                titleMaterialList.add(dicEntityVo.getValueDesc());
            }
            title.setSameFlag(0);
            title.setSpaceName("");
            title.setMaterial(titleMaterialList);

            //查询硬装标准集合
            HardStandardGroupListResponseVo groupListResponseVo = hardStandardWcmProxy.queryHardStandGroup();
            if (groupListResponseVo != null && CollectionUtils.isNotEmpty(groupListResponseVo.getSpaceList())) {
                List<HardStandardGroupResponseVo> spaceList = groupListResponseVo.getSpaceList();
                for (HardStandardGroupResponseVo hardStandardGroupResponseVo : spaceList) {
                    HardStandardResponse hardStandardResponse = new HardStandardResponse();//空间
                    hardStandardResponse.setSpaceName(hardStandardGroupResponseVo.getSpaceName());
                    List<HardStandardDetail> materialList = new ArrayList<HardStandardDetail>();
                    materialList.add(title);//标题
                    List<HardStandardGroupDetail> materialGroupList = hardStandardGroupResponseVo.getMaterialList();//项目材质集合
                    for (HardStandardGroupDetail hardStandardGroupDetail : materialGroupList) {
                        HardStandardDetail detail = new HardStandardDetail();
                        if (StringUtils.isNotBlank(hardStandardGroupDetail.getSpaceName())) {
                            detail.setSpaceName(hardStandardGroupDetail.getSpaceName());
                        } else {
                            detail.setSpaceName("");
                        }
                        detail.setSameFlag(judgeSameFlag(hardStandardGroupDetail));//相同标志
                        detail.setMaterial(hardStandardGroupDetail.getMaterial());
                        materialList.add(detail);
                    }
                    hardStandardResponse.setMaterialList(materialList);

                    responses.add(hardStandardResponse);
                }
            }
            return responses;
        } else {
            return null;
        }
    }

    private Integer judgeSameFlag(HardStandardGroupDetail groupDetail) {
        Integer differentFlag = 0;//不同标志
        Integer sameFlag = 0;//相同标志
        if (groupDetail != null) {
            List<String> materialList = groupDetail.getMaterial();
            for (int i = 1; i < materialList.size() - 1; i++) {
                if (materialList.get(i).equals(materialList.get(i + 1))) {
                    sameFlag++;
                } else {
                    differentFlag++;
                }
            }
        }
        if (differentFlag == 0 && sameFlag > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 根据套系查询硬装标准
     */
    private void queryHardStandardBySeriesName(String seriesName, List<HardStandardSpaceResponse> responses, Integer source) {
        if (StringUtils.isNotBlank(seriesName)) {
            List<String> seriesNameList = new ArrayList<String>();
            seriesNameList.add(seriesName);
            HardStandardListResponseVo hardStandardListResponseVo = hardStandardWcmProxy.queryHardStandByCondition(seriesNameList);
            if (hardStandardListResponseVo != null && CollectionUtils.isNotEmpty(hardStandardListResponseVo.getHardStandardList())) {
                HardStandardResponseVo responseVo = hardStandardListResponseVo.getHardStandardList().get(0);//硬装标准
                if (responseVo != null && CollectionUtils.isNotEmpty(responseVo.getSpaceList())) {
                    List<HardStandardSpaceResponseVo> spaceList = responseVo.getSpaceList();
                    HardStandardItem title = new HardStandardItem();
                    title.setSubjectName(HomeCardPraise.HARD_DESC);
                    title.setMaterial(HomeCardPraise.BRAND_MATERIAL);
                    for (HardStandardSpaceResponseVo hardStandardSpaceResponseVo : spaceList) {
                        HardStandardSpaceResponse spaceResponse = new HardStandardSpaceResponse();
                        List<HardStandardItem> itemListResponse = new ArrayList<HardStandardItem>();
                        if (source.equals(0)) {
                            itemListResponse.add(title);
                        }
                        spaceResponse.setSpaceName(hardStandardSpaceResponseVo.getSpaceName());//空间
                        List<HardStandardDetailVo> itemList = hardStandardSpaceResponseVo.getItemList();//项目材质
                        for (HardStandardDetailVo hardStandardDetailVo : itemList) {
                            HardStandardItem item = new HardStandardItem();
                            item.setMaterial(hardStandardDetailVo.getMaterial());//材质
                            item.setSubjectName(hardStandardDetailVo.getSubjectName());//项目
                            itemListResponse.add(item);
                        }
                        spaceResponse.setItemList(itemListResponse);
                        responses.add(spaceResponse);
                    }
                }
            }
        }
    }

    @Override
    public List<HouseResponse> queryUserHouseList(Integer userId, Integer type) {
        List<HouseResponse> responses = new ArrayList<HouseResponse>();
        List<HouseResponse> deliveryList = new ArrayList<>();//交付阶段房产
        List<HouseResponse> signList = new ArrayList<>();//签约阶段房产
        List<HouseResponse> handSelList = new ArrayList<>();//定金阶段房产
        List<HouseResponse> purposeList = new ArrayList<>();//意向阶段房产
        List<HouseResponse> completeList = new ArrayList<>();//已完成阶段房产
        List<HouseResponse> touchHouseList = new ArrayList<HouseResponse>();//接触状态房产
        List<HouseResponse> cancelHouseList = new ArrayList<HouseResponse>();//已取消状态房产
        List<HouseResponse> otherHouseList = new ArrayList<HouseResponse>();//其他状态房产

        if (type == null) {
            type = 1;
        }

        //排序 交付中 > 已签约 > 定金阶段 > 意向阶段 > 已完成 > 接触中 > 已取消
        if (userId != null && userId > 0) {
            List<HouseInfoResponseVo> houseInfoList = houseService.queryUserHouseList(userId);
            if (CollectionUtils.isNotEmpty(houseInfoList)) {
                for (HouseInfoResponseVo houseInfoResponseVo : houseInfoList) {
                    HouseResponse houseResponse = new HouseResponse();
                    houseResponse.setOrderId(houseInfoResponseVo.getMasterOrderId());
                    if (houseInfoResponseVo.getSource() != null) {
                        houseResponse.setOrderSource(houseInfoResponseVo.getSource());
                    }
                    HouseInfo houseInfoResponse = homeBuildingService.setHouseInfoStandard(houseInfoResponseVo);
                    houseResponse.setBuildingAddress(houseInfoResponse.getBuildingAddress());
                    houseResponse.setArea(houseInfoResponse.getSize());
                    houseResponse.setHouseType(houseInfoResponse.getHouseTypeName());
                    houseResponse.setPattern(houseInfoResponse.getHousePattern());
                    houseResponse.setRoomAddress(houseInfoResponse.getUnitRoomNo());
                    houseResponse.setHouseImage(QiniuImageUtils.compressImageAndSamePicTwo(houseInfoResponseVo.getNormalLayoutPic(), 750, -1));
                    houseResponse.setHouseTypeId(houseInfoResponseVo.getHouseTypeId());
                    houseResponse.setBuildingName(houseInfoResponse.getBuildingName());
                    houseResponse.setBuildingId(houseInfoResponseVo.getHouseProjectId());
                    houseResponse.setZoneId(houseInfoResponseVo.getZoneId());
                    houseResponse.setBuildingInfo(houseInfoResponseVo.getBuildingInfo());
                    if (StringUtils.isNotBlank(houseInfoResponseVo.getCustomerName())) {
                        houseResponse.setCustomerName(houseInfoResponseVo.getCustomerName());
                    }
                    if (houseInfoResponseVo.getId() != null) {
                        houseResponse.setHouseId(houseInfoResponseVo.getId());
                        houseResponse.setCustomerHouseId(houseInfoResponseVo.getId());
                    }

                    if (houseInfoResponseVo.getLayoutId() != null) {
                        houseResponse.setLayoutId(houseInfoResponseVo.getLayoutId());
                    }

                    if (StringUtils.isNotBlank(houseInfoResponseVo.getDeliverTime())) {
                        try {
                            houseResponse.setHandoverDate(
                                    DateUtils.parseStrDateTimeToString(houseInfoResponseVo.getDeliverTime(), "yyyy-MM-dd", "yyyy年MM月dd日"));
                        } catch (Exception e) {
                            LOG.error("交房日期格式错误，请留意 orderId:{}", houseInfoResponseVo.getMasterOrderId());
                        }
                    }
                    if (IntegerUtil.isNotEmpty(houseInfoResponseVo.getHouseProjectId()) && IntegerUtil.isNotEmpty(houseInfoResponseVo.getHouseTypeId()) && StringUtil.isNotBlank(houseInfoResponseVo.getHousingNum()) && StringUtil.isNotBlank(houseInfoResponseVo.getUnitNum()) && StringUtil.isNotBlank(houseInfoResponseVo.getRoomNum()) && StringUtil.isNotBlank(houseInfoResponseVo.getCustomerName())) {
                        houseResponse.setNeedComplete(false);
                    } else {
                        houseResponse.setNeedComplete(true);
                    }
                    if (houseResponse.getHouseType() == null) {
                        houseResponse.setHouseType("");
                    }

                    //订单状态排序
                    if (houseInfoResponseVo.getMasterOrderStatus() != null) {
                        houseResponse.setOrderStatus(homeBuildingService.getOrderStatus(houseInfoResponseVo.getMasterOrderStatus()));
                        if (houseInfoResponseVo.getMasterOrderStatus().equals(OrderConstant.ORDER_OMSSTATUS_DELIVERY)) {
                            deliveryList.add(houseResponse);
                        } else if (houseInfoResponseVo.getMasterOrderStatus().equals(OrderConstant.ORDER_OMSSTATUS_SIGN)) {
                            signList.add(houseResponse);
                        } else if (houseInfoResponseVo.getMasterOrderStatus().equals(OrderConstant.ORDER_OMSSTATUS_HANDSEL)) {
                            handSelList.add(houseResponse);
                        } else if (houseInfoResponseVo.getMasterOrderStatus().equals(OrderConstant.ORDER_OMSSTATUS_PURPOSE)) {
                            purposeList.add(houseResponse);
                        } else if (houseInfoResponseVo.getMasterOrderStatus().equals(OrderConstant.ORDER_OMSSTATUS_FINISH)) {
                            completeList.add(houseResponse);
                        } else if (houseInfoResponseVo.getMasterOrderStatus().equals(OrderConstant.ORDER_OMSSTATUS_TOUCH)) {
                            touchHouseList.add(houseResponse);
                        } else if (houseInfoResponseVo.getMasterOrderStatus().equals(OrderConstant.ORDER_OMSSTATUS_CANCEL)) {
                            cancelHouseList.add(houseResponse);
                        } else {
                            otherHouseList.add(houseResponse);
                        }
                    } else {
                        otherHouseList.add(houseResponse);
                    }
                }

                //交付中 > 已签约 > 定金阶段 > 意向阶段 > 已完成 > 接触中 > 已取消>其他状态
                if (CollectionUtils.isNotEmpty(deliveryList)) {
                    responses.addAll(deliveryList);
                }
                if (CollectionUtils.isNotEmpty(signList)) {
                    responses.addAll(signList);
                }
                if (CollectionUtils.isNotEmpty(handSelList)) {
                    responses.addAll(handSelList);
                }
                if (CollectionUtils.isNotEmpty(purposeList)) {
                    responses.addAll(purposeList);
                }
                if (CollectionUtils.isNotEmpty(completeList)) {
                    responses.addAll(completeList);
                }
                if (CollectionUtils.isNotEmpty(touchHouseList)) {
                    responses.addAll(touchHouseList);
                }
                if (CollectionUtils.isNotEmpty(otherHouseList)) {
                    responses.addAll(otherHouseList);
                }
                if (type.equals(1) && CollectionUtils.isNotEmpty(cancelHouseList)) {
                    responses.addAll(cancelHouseList);
                }
                List<QueryMasterOrderIdByHouseIdResultDto> queryMasterOrderIdByHouseIdResultDtos = productProgramOrderProxy.queryMasterOrderIdsByHouseIds(houseInfoList.stream().map(HouseInfoResponseVo::getId).collect(Collectors.toList()));
                if (CollectionUtils.isNotEmpty(queryMasterOrderIdByHouseIdResultDtos)) {
                    Map<Integer, Integer> collect = queryMasterOrderIdByHouseIdResultDtos.stream().collect(toMap(QueryMasterOrderIdByHouseIdResultDto::getMasterOrderNum, QueryMasterOrderIdByHouseIdResultDto::getOrderSubStatus));
                    responses.forEach(houseResponse -> {
                        houseResponse.setOrderSubStatus(collect.get(houseResponse.getOrderId()));
                    });
                }
            }
        }
        return responses;
    }


    @Override
    public List<DicResponse> queryFlowDesc() {
        List<DicResponse> respList = new ArrayList<DicResponse>();
        DicListDto listResponseVo = dicProxy.getDicListByKey(HomeCardPraise.PROGRAM_FLOW_DESC);//全品家订单流程说明
        if (listResponseVo != null && CollectionUtils.isNotEmpty(listResponseVo.getDicList())) {
            List<DicDto> dicList = listResponseVo.getDicList();
            for (DicDto dicVo : dicList) {
                DicResponse dicResponse = new DicResponse();
                dicResponse.setKeyDesc(dicVo.getKeyDesc());
                dicResponse.setValueDesc(dicVo.getValueDesc());
                respList.add(dicResponse);
            }
        }
        return respList;
    }

    @Override
    public HardStandardDetailListResponseVo queryHardStandardDetail(HardStandardRequest request) {
        List<HardStandardSpaceResponse> responseList = new ArrayList<HardStandardSpaceResponse>();

        Integer source = 0;
        if (request.getSource() != null) {
            source = request.getSource();
        }

        //纯软装描述
        String hardStandardDesc = "";
        DicDto dicVo = dicProxy.queryDicByKey(ProductProgramPraise.HARD_STANDARD_SOFT_PRAISE);
        if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
            hardStandardDesc = dicVo.getValueDesc();
        } else {
            hardStandardDesc = ProductProgramPraise.HARD_STANDARD_SOFT_PRAISE_DESC;
        }

        if (request.getOrderId() != null && request.getOrderId() > 0) {
            //查询全品家订单方案详情
            AladdinOrderResultDto aladdinOrderResponseVo = productProgramOrderService.queryAllProductOrderDetailById(request.getOrderId(), true);
            if (aladdinOrderResponseVo != null && aladdinOrderResponseVo.getOrderInfo() != null) {
                //订单信息
                AladdinOrderBaseInfoVo orderInfo = aladdinOrderResponseVo.getOrderInfo();
                if (orderInfo.getOrderSaleType() != null && orderInfo.getOrderSaleType() == ProductProgramPraise.HARD_STANDARD_ALL) {
                    hardStandardDesc = "";
                    //软装+硬装
                    if (orderInfo.getIsAutoMatch() == null) {

                    } else if (orderInfo.getIsAutoMatch()) {
                        //自由搭配
                        AladdinSolutionDetailResponseVo solutionDetailResponseVo = productProgramOrderService.querySolutionDetailWithMasterOrderId(request.getOrderId());
                        if (solutionDetailResponseVo != null) {
                            Map<String, String> seriesSpaceList = new LinkedHashMap<String, String>();
                            List<String> seriesNameList = new ArrayList<String>();
                            //方案空间列表
                            List<SolutionRoomDetailVo> solutionRoomDetailVoList = solutionDetailResponseVo.getSolutionRoomDetailVoList();
                            for (SolutionRoomDetailVo solutionRoomDetailVo : solutionRoomDetailVoList) {
                                if (StringUtils.isNotBlank(solutionRoomDetailVo.getSeriesName()) && !seriesNameList.contains(solutionRoomDetailVo.getSeriesName())) {
                                    seriesNameList.add(solutionRoomDetailVo.getSeriesName());
                                }
                                seriesSpaceList.put(solutionRoomDetailVo.getRoomUsageName(), solutionRoomDetailVo.getSeriesName());
                            }
                            //查询硬装标准
                            queryFreeMatchHardStandard(seriesNameList, seriesSpaceList, responseList);
                        }
                    } else {
                        //整套
                        //已选方案信息
                        AladdinProgramInfoVo solutionSelectedInfo = aladdinOrderResponseVo.getSolutionSelectedInfo();
                        if (solutionSelectedInfo != null && StringUtils.isNotBlank(solutionSelectedInfo.getSeriesStr())) {
                            queryHardStandardBySeriesName(solutionSelectedInfo.getSeriesStr(), responseList, source);
                        }
                    }
                }
            }
        } else if (request.getProgramId() != null && request.getProgramId() > 0) {
            //查询方案详情
            SolutionDetailResponseVo solutionDetailResponseVo = productProgramProxy.getProgramDetailById(request.getProgramId());
            if (solutionDetailResponseVo != null && !SolutionStatusEnum.ONLINE.getStatus().equals(solutionDetailResponseVo.getSolutionStatus())) {
                solutionDetailResponseVo = null;
            }
            if (solutionDetailResponseVo != null && solutionDetailResponseVo.getDecorationType() != null && solutionDetailResponseVo.getDecorationType().intValue()
                    == ProductProgramPraise.HARD_STANDARD_ALL) {
                queryHardStandardBySeriesName(solutionDetailResponseVo.getSolutionSeriesName(), responseList, source);//硬装+软装
                hardStandardDesc = "";
            }
        }
        HardStandardDetailListResponseVo result = new HardStandardDetailListResponseVo();
        result.setSpaceList(responseList);
        result.setHardStandardDesc(hardStandardDesc);
        return result;
    }

    /**
     * 查询自由搭配方案的硬装标准信息
     *
     * @param seriesSpaceList
     * @return
     */
    private void queryFreeMatchHardStandard(List<String> seriesNameList, Map<String, String> seriesSpaceList, List<HardStandardSpaceResponse> responseList) {
        //查询所含套系的硬装标准
        HardStandardListResponseVo listResponseVo = hardStandardWcmProxy.queryHardStandByCondition(seriesNameList);
        if (listResponseVo != null && CollectionUtils.isNotEmpty(listResponseVo.getHardStandardList())) {
            List<HardStandardResponseVo> hardStandardList = listResponseVo.getHardStandardList();//硬装标准集合
            for (HardStandardResponseVo hardStandardResponseVo : hardStandardList) {
                //需要展示的空间
                List<String> spaceNameList = new ArrayList<String>();
                for (Map.Entry<String, String> entry : seriesSpaceList.entrySet()) {
                    if (hardStandardResponseVo.getSeriesName().equals(entry.getValue())) {
                        spaceNameList.add(entry.getKey());
                    }
                }
                //各空间
                if (CollectionUtils.isNotEmpty(spaceNameList)) {
                    List<HardStandardSpaceResponseVo> spaceList = hardStandardResponseVo.getSpaceList();//空间集合
                    //全屋材质
                    List<HardStandardItem> itemFullHouseList = new ArrayList<HardStandardItem>();//项目材质
                    for (HardStandardSpaceResponseVo hardStandardSpaceResponseVo : spaceList) {
                        if (hardStandardSpaceResponseVo.getSpaceName().equals(ProductProgramPraise.HARD_STANDARD_SPACE_FULLHOUSE)) {
                            List<HardStandardDetailVo> spaceItemList = hardStandardSpaceResponseVo.getItemList(); //硬装清单
                            for (HardStandardDetailVo hardStandardDetailVo : spaceItemList) {
                                HardStandardItem standardItem = new HardStandardItem();
                                standardItem.setMaterial(hardStandardDetailVo.getMaterial());//材质
                                standardItem.setSubjectName(hardStandardDetailVo.getSubjectName());//项目
                                itemFullHouseList.add(standardItem);
                            }
                        }
                    }

                    for (HardStandardSpaceResponseVo hardStandardSpaceResponseVo : spaceList) {
                        for (String spaceName : spaceNameList) {
                            String newSpaceName = "";
                            if (ProductProgramPraise.SPACE_CATEGORY_TYPE_HALL.contains(spaceName)) {
                                newSpaceName = ProductProgramPraise.SPACE_CATEGORY_TYPE_HALL_DESC;
                            } else if (ProductProgramPraise.SPACE_CATEGORY_TYPE_BALCONY.contains(spaceName)) {
                                newSpaceName = ProductProgramPraise.SPACE_CATEGORY_TYPE_BALCONY_DESC;
                            } else if (ProductProgramPraise.SPACE_CATEGORY_TYPE_BEDROOM.contains(spaceName)) {
                                newSpaceName = ProductProgramPraise.SPACE_CATEGORY_TYPE_BEDROOM_DESC;
                            } else if (ProductProgramPraise.SPACE_CATEGORY_TYPE_TOILET.contains(spaceName)) {
                                newSpaceName = ProductProgramPraise.SPACE_CATEGORY_TYPE_TOILET_DESC;
                            } else {
                                newSpaceName = spaceName;
                            }

                            if (hardStandardSpaceResponseVo.getSpaceName().contains(newSpaceName)) {
                                HardStandardSpaceResponse standardSpaceResponse = new HardStandardSpaceResponse();
                                List<HardStandardItem> itemList = new ArrayList<HardStandardItem>();//项目材质
                                //加入全屋装修标准
                                if (CollectionUtils.isNotEmpty(itemFullHouseList)) {
                                    itemList.addAll(itemFullHouseList);
                                }

                                standardSpaceResponse.setSpaceName(spaceName);
                                List<HardStandardDetailVo> spaceItemList = hardStandardSpaceResponseVo.getItemList(); //硬装清单
                                for (HardStandardDetailVo hardStandardDetailVo : spaceItemList) {
                                    HardStandardItem standardItem = new HardStandardItem();
                                    standardItem.setMaterial(hardStandardDetailVo.getMaterial());//材质
                                    standardItem.setSubjectName(hardStandardDetailVo.getSubjectName());//项目
                                    itemList.add(standardItem);
                                }
                                standardSpaceResponse.setItemList(itemList);
                                responseList.add(standardSpaceResponse);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取空间标识空间描述为硬装标准的空间标识集合
     *
     * @return
     */
    private List<String> getSpaceHardCategoryList() {
        List<BasePropertyResponseVo> basePropertyResponse = homeCardBossProxy.getProductFilterInfo();
        List<String> spaceHardList = new ArrayList<String>();
        if (null == basePropertyResponse) {
            return spaceHardList;
        }
        for (BasePropertyResponseVo basePropertyResponseVo : basePropertyResponse) {
            if (basePropertyResponseVo.getPropertyType() == 3 && basePropertyResponseVo.getRoomClassifyType() != null && ProductProgramPraise.SPACE_CATEGORY_HARD_LIST.contains(basePropertyResponseVo.getRoomClassifyType())) {
                spaceHardList.add(basePropertyResponseVo.getPropertyName());
            }
        }
        return spaceHardList;
    }

    /**
     * 方案小贴士信息
     *
     * @param styleList
     * @param brandList
     * @param response
     */
    private void setKonwledgeInfo(List<String> styleList, List<String> brandList, ProgramDetailResponse response) {
        //风格品牌集合
        List<String> descList = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(styleList)) {
            descList.addAll(styleList);
        }
        if (CollectionUtils.isNotEmpty(brandList)) {
            descList.addAll(brandList);
        }
        if (CollectionUtils.isNotEmpty(descList)) {
            KeywordListResponseVo listResponseVo = keywordWcmProxy.getKeywordList(descList);
            if (listResponseVo != null && !CollectionUtils.isEmpty(listResponseVo.getKeywordList())) {
                KnowledgeListResponse knowledgeInfo = new KnowledgeListResponse();//方案小贴士信息
                List<KonwledgeResponse> styleDescList = new ArrayList<KonwledgeResponse>();//风格说明集合
                List<KonwledgeResponse> brandDescList = new ArrayList<KonwledgeResponse>();//品牌说明集合

                List<KeywordVo> keywordList = listResponseVo.getKeywordList();
                for (KeywordVo keywordVo : keywordList) {
                    KonwledgeResponse styleKonwledge = new KonwledgeResponse();//风格
                    KonwledgeResponse brandKonwledge = new KonwledgeResponse();//品牌

                    List<String> words = keywordVo.getWords();
                    //风格
                    for (String styleDescVo : styleList) {
                        if (styleDescVo.equals(keywordVo.getKey())) {
                            String desc = "";
                            styleKonwledge.setTitle(styleDescVo);
                            for (String word : words) {
                                desc = desc + word;
                            }
                            styleKonwledge.setDesc(desc);
                            styleDescList.add(styleKonwledge);
                        }
                    }
                    //品牌
                    for (String brandDescVo : brandList) {
                        if (brandDescVo.equals(keywordVo.getKey())) {
                            String desc = "";
                            brandKonwledge.setTitle(brandDescVo);
                            if (CollectionUtils.isNotEmpty(words)) {
                                brandKonwledge.setLogImgUrl(words.get(0));
                            }
                            for (int i = 1; i < words.size(); i++) {
                                desc = desc + words.get(i);
                            }
                            brandKonwledge.setDesc(desc);
                            brandDescList.add(brandKonwledge);
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(styleDescList)) {
                    knowledgeInfo.setStyleDescList(styleDescList);
                }
                if (CollectionUtils.isNotEmpty(brandDescList)) {
                    knowledgeInfo.setBrandDescList(brandDescList);
                }
                response.setKnowledgeInfo(knowledgeInfo);
            }
        }
    }

    @Override
    public List<AddBagDetail> queryAddBagByProgramIds(AddBagSearchRequest request) {
        if (request.getProgramIdList() != null && CollectionUtils.isNotEmpty(request.getProgramIdList())) {
            Integer width = 0;
            if (request.getWidth() != null) {
                width = request.getWidth() * ImageSize.WIDTH_PER_SIZE_22
                        / ImageSize.WIDTH_PER_SIZE_100;
            }

            List<AddBagDetail> response = new ArrayList<AddBagDetail>();
            List<SolutionAddBagInfoVo> addBagInfoVos = productProgramProxy.queryExtraItemWithSolutionIds(request.getProgramIdList());
            if (addBagInfoVos != null && CollectionUtils.isNotEmpty(addBagInfoVos)) {
                for (SolutionAddBagInfoVo solutionAddBagInfoVo : addBagInfoVos) {
                    if (solutionAddBagInfoVo.getSkuId() != null && solutionAddBagInfoVo.getSkuId() > 0) {
                        AddBagDetail addBagDetail = new AddBagDetail();
                        int count = 0;
                        BigDecimal price = new BigDecimal(0);
                        addBagDetail.setSkuId(solutionAddBagInfoVo.getSkuId());
                        if (StringUtils.isNotBlank(solutionAddBagInfoVo.getSkuName())) {
                            addBagDetail.setSkuName(solutionAddBagInfoVo.getSkuName());
                        }
                        if (StringUtils.isNotBlank(solutionAddBagInfoVo.getSkuHeadImgURL())) {
                            addBagDetail.setSkuImgUrl(QiniuImageUtils.compressImageAndDiffPic(solutionAddBagInfoVo.getSkuHeadImgURL(), width, -1));
                        }
                        if (solutionAddBagInfoVo.getSkuCount() != null && solutionAddBagInfoVo.getSkuCount() > 0) {
                            addBagDetail.setSkuCount(solutionAddBagInfoVo.getSkuCount());
                            count = solutionAddBagInfoVo.getSkuCount();
                        }
                        if (solutionAddBagInfoVo.getSkuUnitPrice() != null) {
                            addBagDetail.setSkuUnitPrice(solutionAddBagInfoVo.getSkuUnitPrice());
                            price = solutionAddBagInfoVo.getSkuUnitPrice();
                        }
                        if (StringUtils.isNotBlank(solutionAddBagInfoVo.getCategoryName())) {
                            addBagDetail.setCategoryName(solutionAddBagInfoVo.getCategoryName());
                        }
                        if (StringUtils.isNotBlank(solutionAddBagInfoVo.getDesc())) {
                            addBagDetail.setSkuDesc(solutionAddBagInfoVo.getDesc());
                        }

                        addBagDetail.setSkuTotalPrice(price.multiply(new BigDecimal(count)).setScale(2, BigDecimal.ROUND_HALF_UP));
                        response.add(addBagDetail);
                    }
                }
            }
            return response;
        } else {
            return null;
        }
    }

    @Override
    public List<AdviserBuildingInfoResponse> queryAdviserBaseInfo(ProjectSearchVo params) {
        List<UsableCompanyResponseVo> companyResponseVos = productProgramProxy.querySolutionBuildingInfo(params);
        if (CollectionUtils.isNotEmpty(companyResponseVos)) {
            List<AdviserBuildingInfoResponse> listResponses = new ArrayList<AdviserBuildingInfoResponse>();
            for (UsableCompanyResponseVo usableCompanyResponseVo : companyResponseVos) {
                AdviserBuildingInfoResponse infoResponse = new AdviserBuildingInfoResponse();
                infoResponse.setCompanyId(usableCompanyResponseVo.getCompanyId());
                infoResponse.setCompanyName(usableCompanyResponseVo.getCompanyName());
                List<UsableBuildingVo> buildingList = usableCompanyResponseVo.getBuildingList();
                if (CollectionUtils.isNotEmpty(buildingList)) {
                    List<BuildingInfo> buildingInfoResponseList = new ArrayList<BuildingInfo>();
                    for (UsableBuildingVo usableBuildingVo : buildingList) {
                        if (DEL_TEST_PROGRAM && StringUtils.isNotBlank(usableBuildingVo.getBuildingName()) && usableBuildingVo.getBuildingName().contains("测试")) {
                            continue;
                        }
                        BuildingInfo buildingInfo = new BuildingInfo();
                        buildingInfo.setCompanyName(usableCompanyResponseVo.getCompanyName());
                        buildingInfo.setBuildingId(usableBuildingVo.getBuildingId());
                        buildingInfo.setBuildingName(usableBuildingVo.getBuildingName());
                        buildingInfoResponseList.add(buildingInfo);
                    }
                    infoResponse.setBuildingInfoList(buildingInfoResponseList);
                }
                listResponses.add(infoResponse);
            }
            return listResponses;
        }

        return null;
    }

    @Override
    public List<AdviserCompanyInfoResponse> queryAvailableSolutionCompany() {
        List<UsableCompanyResponseVo> companyResponseVos = productProgramProxy.querySolutionBuildingInfo(new ProjectSearchVo());
        if (CollectionUtils.isNotEmpty(companyResponseVos)) {
            List<AdviserCompanyInfoResponse> listResponses = new ArrayList<AdviserCompanyInfoResponse>();
            for (UsableCompanyResponseVo usableCompanyResponseVo : companyResponseVos) {
                AdviserCompanyInfoResponse infoResponse = new AdviserCompanyInfoResponse();
                infoResponse.setCompanyId(usableCompanyResponseVo.getCompanyId());
                infoResponse.setCompanyName(usableCompanyResponseVo.getCompanyName());
                listResponses.add(infoResponse);
            }
            return listResponses;
        }

        return null;
    }

    @Override
    public boolean judgeAdviserQualification(String mobile) {
        List<BetaUserDto> list = productProgramProxy.batchQueryBetaUserByMobileList(Lists.newArrayList(mobile));
        if (CollectionUtils.isNotEmpty(list)) {
            BetaUserDto betaUserDto = list.get(0);
            if (CollectionUtils.isNotEmpty(betaUserDto.getRoleCodeList())) {
                for (String roleCode : judgeAdviserQualification) {
                    if (betaUserDto.getRoleCodeList().contains(roleCode)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public AdviserBuildingProgramResponse queryProgramListByBuildingId(HttpProductProgramRequest request, Integer width) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);
        Integer imageWidth = 0;
        if (width != null) {
            imageWidth = width * ImageSize.WIDTH_PER_SIZE_33 / ImageSize.WIDTH_PER_SIZE_100;
        }

        if (request.getHouseProjectId() == null) {
            return null;
        }
        AdviserBuildingProgramResponse buildingProgramResponse = new AdviserBuildingProgramResponse();

        SolutionBuildingProgramResponseVo solutionBuildingProgramResponseVo = productProgramProxy.querySolutionSketchWithBuildingId(request.getHouseProjectId(), request.getZoneId(), request.getSolutionName(), request.getHouseTypeId());
        if (solutionBuildingProgramResponseVo != null) {
            buildingProgramResponse.setBuildingId(solutionBuildingProgramResponseVo.getBuildingId());
            if (StringUtils.isNotBlank(solutionBuildingProgramResponseVo.getBuildingName())) {
                buildingProgramResponse.setBuildingName(solutionBuildingProgramResponseVo.getBuildingName());
            }
            if (StringUtils.isNotBlank(solutionBuildingProgramResponseVo.getBuildingAddress())) {
                buildingProgramResponse.setBuildingAddress(solutionBuildingProgramResponseVo.getBuildingAddress());
            }

            List<SolutionZoneInfoVo> zoneList = solutionBuildingProgramResponseVo.getZoneList();//分区列表

            //增加可得佣金
            Map<String, Object> ruleFact = new HashMap<>();
            ruleFact.put("buildingId", request.getHouseProjectId());
            ruleFact.put("transactionType", 1);
            CommissionRuleDto commissionRule = commissionProxy.queryCommissionRule(ruleFact);

            if (CollectionUtils.isNotEmpty(zoneList)) {
                List<AdviserBuildingHouseTypeResponse> houseTypeListResponse = new ArrayList<AdviserBuildingHouseTypeResponse>();
                for (SolutionZoneInfoVo solutionZoneInfoVo : zoneList) {
                    //户型集合
                    List<SolutionBuildingHouseTypeResponseVo> houseTypeList = solutionZoneInfoVo.getHouseTypeList();
                    if (CollectionUtils.isNotEmpty(houseTypeList)) {
                        //List<AdviserBuildingHouseTypeResponse> houseTypeListResponse = new ArrayList<AdviserBuildingHouseTypeResponse>();
                        for (SolutionBuildingHouseTypeResponseVo houseTypeResponseVo : houseTypeList) {
                            AdviserBuildingHouseTypeResponse houseTypeResponse = new AdviserBuildingHouseTypeResponse();
                            houseTypeResponse.setHouseTypeId(houseTypeResponseVo.getHouseTypeId());
                            if (StringUtils.isNotBlank(houseTypeResponseVo.getHouseTypeName())) {
                                if (houseTypeResponseVo.getHouseTypeName().contains(HomeCardPraise.HOUSE_TYPE)) {
                                    houseTypeResponse.setHouseTypeName(houseTypeResponseVo.getHouseTypeName());
                                } else {
                                    houseTypeResponse.setHouseTypeName(houseTypeResponseVo.getHouseTypeName() + HomeCardPraise.HOUSE_TYPE);
                                }
                            }
                            if (houseTypeResponseVo.getHouseArea() != null) {
                                BigDecimal houseArea = new BigDecimal(houseTypeResponseVo.getHouseArea());
                                houseTypeResponse.setHouseArea(nf.format(houseArea) + ProductProgramPraise.AREA);//户型面积
                            } else {
                                houseTypeResponse.setHouseArea(0 + ProductProgramPraise.AREA);//户型面积
                            }

                            //方案列表
                            List<SolutionSketchInfoResponseVo> seriesProgramListResponseVo = houseTypeResponseVo.getSeriesProgramList();
                            if (CollectionUtils.isNotEmpty(seriesProgramListResponseVo)) {
                                List<SeriesProgramListResponse> seriesProgramList = new ArrayList<SeriesProgramListResponse>();
                                for (SolutionSketchInfoResponseVo solutionSketchInfoResponseVo : seriesProgramListResponseVo) {
                                    SeriesProgramListResponse seriesProgramListResponse = new SeriesProgramListResponse();
                                    seriesProgramListResponse.setSeriesId(solutionSketchInfoResponseVo.getSeriesId());
                                    seriesProgramListResponse.setSeriesName(solutionSketchInfoResponseVo.getSeriesName() + ProductProgramPraise.SERIES);
                                    seriesProgramListResponse.setProgramNum(solutionSketchInfoResponseVo.getSeriesAvailableCount());
                                    List<SolutionBaseInfoVo> seriesSolutionList = solutionSketchInfoResponseVo.getSeriesSolutionList();
                                    if (CollectionUtils.isNotEmpty(seriesSolutionList)) {
                                        List<ProgramResponse> programList = new ArrayList<ProgramResponse>();
                                        for (SolutionBaseInfoVo solutionBaseInfoVo : seriesSolutionList) {
                                            ProgramResponse programResponse = new ProgramResponse();
                                            //装修类型
                                            String spaceCategory = "";
                                            if (solutionBaseInfoVo.getDecorationType() != null) {
                                                if (solutionBaseInfoVo.getDecorationType() == ProductProgramPraise.HARD_STANDARD_ALL) {
                                                    spaceCategory = ProductProgramPraise.HARD_SOFT;
                                                } else if (solutionBaseInfoVo.getDecorationType() == ProductProgramPraise.HARD_STANDARD_SOFT) {
                                                    spaceCategory = ProductProgramPraise.HARD_STANDARD_SOFT_DESC;
                                                }
                                            }
                                            programResponse.setCategory(spaceCategory);
                                            /*if(solutionBaseInfoVo.getSolutionDiscount() != null){
                                                programResponse.setDiscount(nf.format(solutionBaseInfoVo.getSolutionDiscount()) + ProductProgramPraise.DISCOUNT);
                                            }*/
                                            programResponse.setFurnitureNum(solutionBaseInfoVo.getItemCount());
                                            if (StringUtils.isNotBlank(solutionBaseInfoVo.getHeadImgURL())) {
                                                programResponse.setHeadImgUrl(AliImageUtil.imageCompress(solutionBaseInfoVo.getHeadImgURL(), request.getOsType(), imageWidth, ImageConstant.SIZE_MIDDLE));
                                            } else {
                                                programResponse.setHeadImgUrl("");
                                            }
                                            programResponse.setName(solutionBaseInfoVo.getSolutionName());
                                            if (solutionBaseInfoVo.getSolutionTotalDiscountPrice() != null) {
                                                programResponse.setPrice(solutionBaseInfoVo.getSolutionTotalDiscountPrice().toString());
                                            }
                                            if (solutionBaseInfoVo.getSolutionTotalDiscountPrice() != null) {
                                                programResponse.setDiscountPrice(solutionBaseInfoVo.getSolutionTotalDiscountPrice().toString());
                                            }
                                            programResponse.setProgramId(solutionBaseInfoVo.getSolutionId());
                                            programResponse.setStyle(solutionBaseInfoVo.getStyleName() + ProductProgramPraise.STYLE);

                                            if (commissionRule != null && solutionBaseInfoVo.getSolutionTotalDiscountPrice() != null) {
                                                programResponse.setAvailableCommission(commissionRule.getRate().multiply(solutionBaseInfoVo.getSolutionTotalDiscountPrice()).setScale(2, BigDecimal.ROUND_DOWN).toString());
                                            } else if (commissionRule == null && solutionBaseInfoVo.getSolutionTotalDiscountPrice() != null) {
                                                //如果没有拥挤比例，按默认的2%计算
                                                programResponse.setAvailableCommission(solutionBaseInfoVo.getSolutionTotalDiscountPrice().multiply(new BigDecimal("0.02")).setScale(2, BigDecimal.ROUND_DOWN).toString());
                                            }

                                            programList.add(programResponse);
                                        }
                                        if (DEL_TEST_SOLUTIONS && CollectionUtils.isNotEmpty(programList)) {
                                            programList.removeIf(programResponse -> programResponse.getName().contains("测试"));
                                        }
                                        seriesProgramListResponse.setProgramList(programList);
                                    }
                                    seriesProgramList.add(seriesProgramListResponse);
                                }
                                houseTypeResponse.setSeriesProgramList(seriesProgramList);
                            }
                            houseTypeListResponse.add(houseTypeResponse);
                        }
                        //buildingProgramResponse.setHouseTypeList(houseTypeListResponse);
                    }
                }
                buildingProgramResponse.setHouseTypeList(houseTypeListResponse);
            }
        }

        return buildingProgramResponse;
    }


    @Override
    public SolutionStandardUpgradeTotalResponse querySolutionStandardUpgrades(List<Integer> roomIdList) {
        SolutionStandardUpgradesResponseVo vo = productProgramProxy.querySolutionStandardUpgrades(roomIdList);
        SolutionStandardUpgradeTotalResponse response = this.getSolutionStandardUpgradeTotalResponse(vo);
        return response;
    }

    private SolutionStandardUpgradeTotalResponse getSolutionStandardUpgradeTotalResponse(
            SolutionStandardUpgradesResponseVo vo) {
        SolutionStandardUpgradeTotalResponse response = new SolutionStandardUpgradeTotalResponse();
        List<SolutionStandardUpgradesResponse> upgradesList = new ArrayList<>();
        response.setUpgradesList(upgradesList);
        if (vo == null) {
            return response;
        }
        int standardUpgradeTotal = 0;
        // 1地板升级,2 地砖升级, 3卫浴升级,4厨房升级 ,5 墙面升级
        List<StandardUpgradeResponseVo> floorBoardUpgradesVo = vo.getFloorBoardUpgrades();// 地板升级
        if (CollectionUtils.isNotEmpty(floorBoardUpgradesVo)) {
            standardUpgradeTotal++;
            SolutionStandardUpgradesResponse floorBoardUpgrades = new SolutionStandardUpgradesResponse(); // 地板升级
            floorBoardUpgrades.setUpgradeTitle(SolutionStandardUpgradeConstant.FLOOR_BOARD_UPGRADE);
            floorBoardUpgrades.setUpgradeDesc(SolutionStandardUpgradeConstant.UPGRADE_PREFIX
                    + floorBoardUpgradesVo.size() + SolutionStandardUpgradeConstant.UPGRADE_SUFFIX);
            floorBoardUpgrades.setUpgradeItems(floorBoardUpgradesVo);
            floorBoardUpgrades.setType(SolutionStandardUpgradeConstant.FLOOR_BOARD_UPGRADE_TYPE);
            upgradesList.add(floorBoardUpgrades);
        }
        List<StandardUpgradeResponseVo> floorTileUpgradesVo = vo.getFloorTileUpgrades();// 地砖升级
        if (CollectionUtils.isNotEmpty(floorTileUpgradesVo)) {
            standardUpgradeTotal++;
            SolutionStandardUpgradesResponse floorTileUpgrades = new SolutionStandardUpgradesResponse(); // 地砖升级
            floorTileUpgrades.setUpgradeTitle(SolutionStandardUpgradeConstant.FLOOR_TILE_UPGRADE);
            floorTileUpgrades.setUpgradeDesc(SolutionStandardUpgradeConstant.UPGRADE_PREFIX
                    + floorTileUpgradesVo.size() + SolutionStandardUpgradeConstant.UPGRADE_SUFFIX);
            floorTileUpgrades.setUpgradeItems(floorTileUpgradesVo);
            floorTileUpgrades.setType(SolutionStandardUpgradeConstant.FLOOR_TILE_UPGRADE_TYPE);
            upgradesList.add(floorTileUpgrades);
        }
        List<StandardUpgradeResponseVo> bathRoomUpgradesVo = vo.getBathRoomUpgrades();// 卫浴升级
        if (CollectionUtils.isNotEmpty(bathRoomUpgradesVo)) {
            standardUpgradeTotal++;
            SolutionStandardUpgradesResponse bathRoomUpgrades = new SolutionStandardUpgradesResponse(); // 卫浴升级
            bathRoomUpgrades.setUpgradeTitle(SolutionStandardUpgradeConstant.BATH_ROOM_UPGRADE);
            bathRoomUpgrades.setUpgradeDesc(SolutionStandardUpgradeConstant.UPGRADE_PREFIX + bathRoomUpgradesVo.size()
                    + SolutionStandardUpgradeConstant.UPGRADE_SUFFIX);
            bathRoomUpgrades.setUpgradeItems(bathRoomUpgradesVo);
            bathRoomUpgrades.setType(SolutionStandardUpgradeConstant.BATH_ROOM_UPGRADE_TYPE);
            upgradesList.add(bathRoomUpgrades);
        }
        List<StandardUpgradeResponseVo> kitchenUpgradesVo = vo.getKitchenUpgrades();// 厨房升级
        if (CollectionUtils.isNotEmpty(kitchenUpgradesVo)) {
            standardUpgradeTotal++;
            SolutionStandardUpgradesResponse kitchenUpgrades = new SolutionStandardUpgradesResponse(); // 厨房升级
            kitchenUpgrades.setUpgradeTitle(SolutionStandardUpgradeConstant.KITCHEN_BOARD_UPGRADE);
            kitchenUpgrades.setUpgradeDesc(SolutionStandardUpgradeConstant.UPGRADE_PREFIX + kitchenUpgradesVo.size()
                    + SolutionStandardUpgradeConstant.UPGRADE_SUFFIX);
            kitchenUpgrades.setUpgradeItems(kitchenUpgradesVo);
            kitchenUpgrades.setType(SolutionStandardUpgradeConstant.KITCHEN_BOARD_UPGRADE_TYPE);
            upgradesList.add(kitchenUpgrades);
        }
        List<StandardUpgradeResponseVo> wallpaperUpgradesVo = vo.getWallpaperUpgrades();// 墙面升级
        if (CollectionUtils.isNotEmpty(wallpaperUpgradesVo)) {
            standardUpgradeTotal++;
            SolutionStandardUpgradesResponse wallpaperUpgrades = new SolutionStandardUpgradesResponse(); // 墙面升级
            wallpaperUpgrades.setUpgradeTitle(SolutionStandardUpgradeConstant.WALL_PAPER_UPGRADE);
            wallpaperUpgrades.setUpgradeDesc(SolutionStandardUpgradeConstant.UPGRADE_PREFIX
                    + wallpaperUpgradesVo.size() + SolutionStandardUpgradeConstant.UPGRADE_SUFFIX);
            wallpaperUpgrades.setUpgradeItems(wallpaperUpgradesVo);
            wallpaperUpgrades.setType(SolutionStandardUpgradeConstant.WALL_PAPER_UPGRADE_TYPE);
            upgradesList.add(wallpaperUpgrades);
        }
        response.setStandardUpgradeTotal(standardUpgradeTotal);
        return response;
    }

    @Override
    public List<DesignerInfoForProgramResponse> queryDesignerListByRoomIds(List<Integer> roomIds) {
        List<DesignerInfoByRoomResponse> designerVoList = artistProxy.queryCopyrigBySolutionRoomIds(roomIds);
        List<DesignerInfoForProgramResponse> designerList = new ArrayList<DesignerInfoForProgramResponse>();

        if (CollectionUtils.isNotEmpty(designerVoList)) {
            for (DesignerInfoByRoomResponse vo : designerVoList) {
                DesignerInfoForProgramResponse designer = new DesignerInfoForProgramResponse();

                if (vo.getUserId() != null) {
                    designer.setDesignerId(vo.getUserId());
                }

                if (StringUtils.isNotBlank(vo.getDesignerName())) {
                    designer.setDesignerName(vo.getDesignerName());
                }

                if (StringUtils.isNotBlank(vo.getDesignerImage())) {
                    designer.setHeadImgUrl(vo.getDesignerImage());
                }

                if (StringUtils.isNotBlank(vo.getDesingerBackImage())) {
                    designer.setDesingerBackImage(vo.getDesingerBackImage());
                }

                if (StringUtils.isNotBlank(vo.getDesignerTag())) {
                    String designerTag = vo.getDesignerTag();
                    String designerLables = designerTag.replaceAll(",", "、").replaceAll("，", "、");
                    designer.setDesignerLables(designerLables);
                }

                boolean mainDesignerTag = false;

                if (CollectionUtils.isNotEmpty(vo.getRoomTypeNames())) {
                    designer.setRoomList(vo.getRoomTypeNames());

                    for (String roomType : vo.getRoomTypeNames()) {
                        if (StringUtils.isNotBlank(roomType) && roomType.equals("客厅")) {
                            mainDesignerTag = true;
                            break;
                        }
                    }
                }

                designer.setMainDesignerTag(mainDesignerTag);

                designerList.add(designer);
            }
        }
        Collections.sort(designerList, new Comparator<DesignerInfoForProgramResponse>() {

            @Override
            public int compare(DesignerInfoForProgramResponse o1, DesignerInfoForProgramResponse o2) {
                return o2.isMainDesignerTag().compareTo(o1.isMainDesignerTag());
            }
        });

        return designerList;
    }

    @Override
    @Deprecated
    public List<OptionalSoftResponse> queryOptionalSoftList(Integer roomId, Integer skuId, Integer width) {
        List<OptionalSoftResponse> response = new ArrayList<OptionalSoftResponse>();

        if (width == null) {
            width = 0;
        }

        List<OptionalSkusResponseVo> optionalSkusResponseVoList = productProgramProxy.queryOptionalSkus(roomId, skuId);
        if (CollectionUtils.isNotEmpty(optionalSkusResponseVoList)) {
            for (OptionalSkusResponseVo optionalSkusResponseVo : optionalSkusResponseVoList) {
                List<SolutionRoomSubItemVo> subItemVos = optionalSkusResponseVo.getSubItemVos();//可替换sku列表
                if (CollectionUtils.isNotEmpty(subItemVos) && subItemVos.size() > 1) {
                    OptionalSoftResponse optionalSoftResponse = new OptionalSoftResponse();
                    if (StringUtils.isNotBlank(optionalSkusResponseVo.getTypeTwoName())) {
                        optionalSoftResponse.setCategory(optionalSkusResponseVo.getTypeTwoName());
                    }
                    List<FurnitureEntity> furnitureList = setFurnitureInfo(subItemVos, width, optionalSkusResponseVo.getBomGroupList(), null);
                    optionalSoftResponse.setFurnitureList(furnitureList);
                    response.add(optionalSoftResponse);
                }
            }
        }
        return response;
    }

    @Override
    public SolutionExtraInfoResponse querySolutionExtraInfo(List<Integer> roomIds, List<Integer> programIds, Integer width) {
        if (width == null) {
            width = 0;
        }

        SolutionExtraInfoResponse extraInfoResponse = new SolutionExtraInfoResponse();

        if (CollectionUtils.isNotEmpty(roomIds)) {
            //查询空间软装可替换家具
            List<OptionalSkusResponseVo> optionalSkusResponseVos = productProgramProxy.queryOptionalSkusByRoomIds(roomIds);
            List<OptionalSoftSpaceResponse> optionalSoftSpaceList = setOptionalSkusInfo(optionalSkusResponseVos, width);
            extraInfoResponse.setOptionalSoftSpaceList(optionalSoftSpaceList);

            //查询升级包
            SolutionStandardUpgradeTotalResponse standardUpgradeTotalResponse = querySolutionStandardUpgrades(roomIds);
            extraInfoResponse.setStandardUpgrade(standardUpgradeTotalResponse);
        }

        if (CollectionUtils.isNotEmpty(programIds)) {
            //查询增配包
            AddBagSearchRequest addBagSearchRequest = new AddBagSearchRequest();
            addBagSearchRequest.setProgramIdList(programIds);
            List<AddBagDetail> addBagDetails = queryAddBagByProgramIds(addBagSearchRequest);
            extraInfoResponse.setAddBagList(addBagDetails);
        }

        return extraInfoResponse;
    }

    /**
     * 设置空间软装可替换家具信息
     *
     * @param optionalSkusResponseVos
     * @return
     */
    private List<OptionalSoftSpaceResponse> setOptionalSkusInfo(List<OptionalSkusResponseVo> optionalSkusResponseVos, Integer width) {
        List<OptionalSoftSpaceResponse> spaceListResponse = new ArrayList<OptionalSoftSpaceResponse>();

        for (OptionalSkusResponseVo optionalSkusResponseVo : optionalSkusResponseVos) {
            //替换家具集合包含自己，所以需要判断可替换数大于1
            List<SolutionRoomSubItemVo> subItemVos = optionalSkusResponseVo.getSubItemVos();
            if (CollectionUtils.isNotEmpty(subItemVos) && subItemVos.size() > 1) {
                OptionalSoftSpaceResponse optionalSoftSpaceResponse = new OptionalSoftSpaceResponse();
                optionalSoftSpaceResponse.setSpaceId(optionalSkusResponseVo.getRoomId());
                if (StringUtils.isNotBlank(optionalSkusResponseVo.getSpaceName())) {
                    optionalSoftSpaceResponse.setSpaceName(optionalSkusResponseVo.getSpaceName());
                }
                if (optionalSkusResponseVo.getSpaceUseId() != null) {
                    optionalSoftSpaceResponse.setSpaceUseId(optionalSkusResponseVo.getSpaceUseId());
                }
                if (StringUtils.isNotBlank(optionalSkusResponseVo.getSpaceUseName())) {
                    optionalSoftSpaceResponse.setSpaceUseName(optionalSkusResponseVo.getSpaceUseName());
                }
                if (StringUtils.isNotBlank(optionalSkusResponseVo.getTypeTwoName())) {
                    optionalSoftSpaceResponse.setCategory(optionalSkusResponseVo.getTypeTwoName());
                }
                List<FurnitureEntity> furnitureList = setFurnitureInfo(subItemVos, width, optionalSkusResponseVo.getBomGroupList(), null);
                optionalSoftSpaceResponse.setFurnitureList(furnitureList);
                spaceListResponse.add(optionalSoftSpaceResponse);
            }
        }

        return spaceListResponse;
    }

    //赠品展示标记 0不展示 1可替换为赠品 2免费赠品 4效果图推荐
    public static Integer getShowFreeFlag(Integer freeFlag, Integer freeAble, Integer furnitureType) {

        if (furnitureType == null || furnitureType != 2) {
            return 0;
        }
        if (freeFlag != null && freeFlag == 1) {//目前免费赠品
            return 2;
        }
        if (freeFlag == 0 && furnitureType == 2) {//目前非免费赠品，可选免费
            return 4;
        }
        return 0;
    }

    /**
     * 设置家具信息
     *
     * @param subItemVos
     * @param width
     * @return
     */
    private List<FurnitureEntity> setFurnitureInfo(List<SolutionRoomSubItemVo> subItemVos, Integer width, List<BomGroupVO> bomGroupList, Integer freeAble) {
        List<FurnitureEntity> furnitureList = new ArrayList<>();

        FurnitureEntity defaultF = new FurnitureEntity();
        if (CollectionUtils.isNotEmpty(subItemVos)) {
            for (SolutionRoomSubItemVo solutionRoomSubItemVo : subItemVos) {
                FurnitureEntity furnitureEntity = new FurnitureEntity();
                //
                furnitureEntity.setStyleId((null == solutionRoomSubItemVo.getStyleId() ? null : solutionRoomSubItemVo.getStyleId()));
                furnitureEntity.setStyleName((null == solutionRoomSubItemVo.getStyleName() ? null : solutionRoomSubItemVo.getStyleName()));
                furnitureEntity.setCategoryLevelTwoId((null == solutionRoomSubItemVo.getCategoryLevelTwoId()) ? null : solutionRoomSubItemVo.getCategoryLevelTwoId());
                furnitureEntity.setCategoryLevelTwoName((null == solutionRoomSubItemVo.getCategoryLevelTwoName()) ? null : solutionRoomSubItemVo.getCategoryLevelTwoName());
                furnitureEntity.setCategoryLevelThreeId((null == solutionRoomSubItemVo.getCategoryLevelThreeId()) ? null : solutionRoomSubItemVo.getCategoryLevelThreeId());
                furnitureEntity.setCategoryLevelThreeName((null == solutionRoomSubItemVo.getCategoryLevelThreeName()) ? null : solutionRoomSubItemVo.getCategoryLevelThreeName());
                furnitureEntity.setCategoryLevelFourId((null == solutionRoomSubItemVo.getCategoryLevelFourId()) ? null : solutionRoomSubItemVo.getCategoryLevelFourId());
                furnitureEntity.setCategoryLevelFourName((null == solutionRoomSubItemVo.getCategoryLevelFourName()) ? null : solutionRoomSubItemVo.getCategoryLevelFourName());
                furnitureEntity.setLastCategoryId((null == solutionRoomSubItemVo.getLastCategoryId()) ? null : solutionRoomSubItemVo.getLastCategoryId());
                furnitureEntity.setLastCategoryName((null == solutionRoomSubItemVo.getLastCategoryName()) ? null : solutionRoomSubItemVo.getLastCategoryName());
                furnitureEntity.setSeriesName(solutionRoomSubItemVo.getSeriesName());
                furnitureEntity.setSeriesId(solutionRoomSubItemVo.getSeriesId());
                furnitureEntity.setProductType(solutionRoomSubItemVo.getProductType());
                furnitureEntity.setAppCustomizable(solutionRoomSubItemVo.isAppCustomizable());
                furnitureEntity.setPriceDiff(solutionRoomSubItemVo.getPriceDiff() == null ? BigDecimal.ZERO : solutionRoomSubItemVo.getPriceDiff());//差价设置
                furnitureEntity.setWidth(solutionRoomSubItemVo.getWidth());
                furnitureEntity.setHeight(solutionRoomSubItemVo.getHeight());
                furnitureEntity.setLength(solutionRoomSubItemVo.getLength());
                furnitureEntity.setFreeFlag(solutionRoomSubItemVo.getFreeFlag());

                //是否支持可视化
                if (solutionRoomSubItemVo.getSkuVisibleFlag() != null) {
                    furnitureEntity.setVisibleFlag(solutionRoomSubItemVo.getSkuVisibleFlag());
                }
                // 新增的8个字段 by cang
                if (StringUtils.isNotBlank(solutionRoomSubItemVo.getItemColor())) {
                    furnitureEntity.setColor(solutionRoomSubItemVo.getItemColor());
                }
                if (StringUtils.isNotBlank(solutionRoomSubItemVo.getItemMaterial())) {
                    furnitureEntity.setItemMaterial(solutionRoomSubItemVo.getItemMaterial());
                }
                if (StringUtils.isNotBlank(solutionRoomSubItemVo.getMaterial())) {
                    furnitureEntity.setMaterial(solutionRoomSubItemVo.getMaterial());
                }
                furnitureEntity.setSkuId(solutionRoomSubItemVo.getSkuId());
                if (solutionRoomSubItemVo.getItemCount() != null) {
                    furnitureEntity.setItemCount(solutionRoomSubItemVo.getItemCount());
                }
                String itemSize = "";
                if (StringUtils.isNotBlank(solutionRoomSubItemVo.getRuleSize())) {
                    itemSize = solutionRoomSubItemVo.getRuleSize();
                } else {
                    itemSize = solutionRoomSubItemVo.getItemSize();
                }
                //家具类型
                if (solutionRoomSubItemVo.getFurnitureType() != null && solutionRoomSubItemVo.getFurnitureType() == ProductProgramPraise.FURNITURE_TYPE_GIFT) {
                    //赠品
                    if (StringUtils.isNotBlank(solutionRoomSubItemVo.getItemName())) {
                        furnitureEntity.setFurnitureName(solutionRoomSubItemVo.getItemName());
                    }
                    furnitureEntity.setFurnitureType(ProductProgramPraise.FURNITURE_TYPE_GIFT);
                } else {
                    //定制家具
                    if (solutionRoomSubItemVo.getFurnitureType() == ProductProgramPraise.FURNITURE_TYPE_ORDER) {
                        if (StringUtils.isNotBlank(solutionRoomSubItemVo.getItemName())) {
                            furnitureEntity.setFurnitureName(solutionRoomSubItemVo.getItemName().replaceAll(ProductProgramPraise.FURNITURE_ORDER_DESC_1, "").replaceAll(ProductProgramPraise.FURNITURE_ORDER_DESC_2, ""));
                        }
                        furnitureEntity.setItemType(ProductProgramPraise.FURNITURE_TYPE_ORDER_DESC);
                        furnitureEntity.setFurnitureType(ProductProgramPraise.FURNITURE_TYPE_ORDER);

                        //若尺寸是1*1*1，调整为“依据现场尺寸定制”
                        if (StringUtils.isNotBlank(solutionRoomSubItemVo.getItemSize()) && ProductProgramPraise.FURNITURE_SIZE.equals(solutionRoomSubItemVo.getItemSize())) {
                            itemSize = ProductProgramPraise.FURNITURE_SIZE_DESC;
                        }
                    } else if (solutionRoomSubItemVo.getFurnitureType().equals(ProductProgramPraise.FURNITURE_TYPE_NEW)) {
                        furnitureEntity.setFurnitureType(solutionRoomSubItemVo.getFurnitureType());
                        if (StringUtils.isNotBlank(solutionRoomSubItemVo.getItemName())) {
                            furnitureEntity.setFurnitureName(solutionRoomSubItemVo.getItemName());
                        }
                    } else {
                        if (StringUtils.isNotBlank(solutionRoomSubItemVo.getItemName())) {
                            furnitureEntity.setFurnitureName(solutionRoomSubItemVo.getItemName());
                        }
                        furnitureEntity.setFurnitureType(ProductProgramPraise.FURNITURE_TYPE_FINISHED);
                    }
                }
                if (freeAble != null) {
                    Integer furnitureType = furnitureEntity.getFurnitureType();
                    if (!ProductCategoryConstant.CATEGORY_THREE_ID_D_LIST.contains(furnitureEntity.getLastCategoryId())) {//除灯具外赠品，不显示效果图推荐
                        furnitureType = 1;
                    }
                    furnitureEntity.setShowFreeFlag(getShowFreeFlag(solutionRoomSubItemVo.getFreeFlag(), freeAble, furnitureType));
                }
                furnitureEntity.setItemSize(itemSize);
                if (StringUtils.isNotBlank(solutionRoomSubItemVo.getItemImage())) {
                    furnitureEntity.setSmallImage(AliImageUtil.imageCompress(solutionRoomSubItemVo.getItemImage(), 2, width, ImageConstant.SIZE_SMALL));
                    furnitureEntity.setImgUrl(QiniuImageUtils.compressImageAndSamePicTwo(solutionRoomSubItemVo.getItemImage(), width, -1));//图片需切图处理
                }
                if (solutionRoomSubItemVo.getPrice() != null) {
                    furnitureEntity.setPrice(solutionRoomSubItemVo.getPrice());
                }
                if (solutionRoomSubItemVo.getParentSkuId() != null) {
                    furnitureEntity.setParentSkuId(solutionRoomSubItemVo.getParentSkuId());
                }
                if (StringUtils.isNotBlank(solutionRoomSubItemVo.getItemBrand())) {
                    furnitureEntity.setBrand(solutionRoomSubItemVo.getItemBrand());
                }
                if (furnitureEntity.getParentSkuId() == 0) {
                    defaultF = furnitureEntity;
                    continue;
                }
                furnitureList.add(furnitureEntity);
            }
        }
        //把默认家具置顶
        furnitureList.add(0, defaultF);

        return furnitureList;
    }

    @Override
    public List<OptionalSpaceResponse> queryOptionalSpaceList(Integer programId, Integer width) {
        if (width == null) {
            width = 0;
        }

        List<OptionalSpaceResponse> response = new ArrayList<OptionalSpaceResponse>();

        List<OptionalRoomResponseVo> optionalRoomResponseVos = productProgramProxy.queryRoomDesignWithSolutionId(programId);
        if (CollectionUtils.isNotEmpty(optionalRoomResponseVos)) {
            for (OptionalRoomResponseVo optionalRoomResponseVo : optionalRoomResponseVos) {
                OptionalSpaceResponse optionalSpaceResponse = new OptionalSpaceResponse();
                optionalSpaceResponse.setIsDefault(1);
                if (optionalRoomResponseVo.getItemCount() != null) {
                    optionalSpaceResponse.setRoomFurnitureNum(optionalRoomResponseVo.getItemCount());
                }
                List<SolutionRoomPicVo> pictureList = optionalRoomResponseVo.getPictureList();//空间效果图
                if (CollectionUtils.isNotEmpty(pictureList)) {
                    for (SolutionRoomPicVo solutionRoomPicVo : pictureList) {
                        if (StringUtils.isNotBlank(solutionRoomPicVo.getSolutionRoomPicURL()) && solutionRoomPicVo.getIsFirst().equals(1)) {
                            optionalSpaceResponse.setRoomImgUrl(AliImageUtil.imageCompress(solutionRoomPicVo.getSolutionRoomPicURL(), 1, width, ImageConstant.SIZE_MIDDLE));
                            break;
                        }
                    }
                }
                if (optionalRoomResponseVo.getRoomPrice() != null) {
                    optionalSpaceResponse.setRoomSalePrice(optionalRoomResponseVo.getRoomPrice());
                }
                optionalSpaceResponse.setSpaceId(optionalRoomResponseVo.getSolutionRoomId());
                if (StringUtils.isNotBlank(optionalRoomResponseVo.getRoomUsageName())) {
                    optionalSpaceResponse.setSpaceName(optionalRoomResponseVo.getRoomUsageName());
                }
                if (StringUtils.isNotBlank(optionalRoomResponseVo.getSolutionSeriesName())) {
                    optionalSpaceResponse.setSpaceSeriesName(optionalRoomResponseVo.getSolutionSeriesName() + ProductProgramPraise.SERIES);
                }
                if (StringUtils.isNotBlank(optionalRoomResponseVo.getSolutionStyleName())) {
                    optionalSpaceResponse.setSpaceStyleName(optionalRoomResponseVo.getSolutionStyleName());
                }
                if (StringUtils.isNotBlank(optionalRoomResponseVo.getRoomUsageName())) {
                    optionalSpaceResponse.setSpaceUseName(optionalRoomResponseVo.getRoomUsageName());
                }
                List<SolutionRoomInfoVo> roomDesignList = optionalRoomResponseVo.getRoomDesignList();//空间可替换设计列表
                if (CollectionUtils.isNotEmpty(roomDesignList)) {
                    List<OptionalSpaceInfo> optionalSpaceList = new ArrayList<OptionalSpaceInfo>();
                    for (SolutionRoomInfoVo solutionRoomInfoVo : roomDesignList) {
                        OptionalSpaceInfo optionalSpaceInfo = new OptionalSpaceInfo();
                        if (solutionRoomInfoVo.getIsDefault() != null) {
                            optionalSpaceInfo.setIsDefault(solutionRoomInfoVo.getIsDefault());
                        }
                        if (optionalRoomResponseVo.getItemCount() != null) {
                            optionalSpaceInfo.setRoomFurnitureNum(solutionRoomInfoVo.getItemCount());
                        }
                        List<SolutionRoomPicVo> roomPicVos = solutionRoomInfoVo.getPictureList();//空间效果图
                        if (CollectionUtils.isNotEmpty(roomPicVos)) {
                            for (SolutionRoomPicVo solutionRoomPicVo : roomPicVos) {
                                if (StringUtils.isNotBlank(solutionRoomPicVo.getSolutionRoomPicURL()) && solutionRoomPicVo.getIsFirst().equals(1)) {
                                    optionalSpaceInfo.setRoomImgUrl(AliImageUtil.imageCompress(solutionRoomPicVo.getSolutionRoomPicURL(), 1, width, ImageConstant.SIZE_MIDDLE));
                                    break;
                                }
                            }
                        }
                        if (optionalRoomResponseVo.getRoomPrice() != null) {
                            optionalSpaceInfo.setRoomSalePrice(solutionRoomInfoVo.getRoomPrice());
                        }
                        optionalSpaceInfo.setSpaceId(solutionRoomInfoVo.getSolutionRoomId());
                        if (StringUtils.isNotBlank(solutionRoomInfoVo.getRoomUsageName())) {
                            optionalSpaceInfo.setSpaceName(solutionRoomInfoVo.getRoomUsageName());
                        }
                        if (StringUtils.isNotBlank(solutionRoomInfoVo.getSolutionSeriesName())) {
                            optionalSpaceInfo.setSpaceSeriesName(solutionRoomInfoVo.getSolutionSeriesName() + ProductProgramPraise.SERIES);
                        }
                        if (StringUtils.isNotBlank(solutionRoomInfoVo.getSolutionStyleName())) {
                            optionalSpaceInfo.setSpaceStyleName(solutionRoomInfoVo.getSolutionStyleName());
                        }
                        if (StringUtils.isNotBlank(solutionRoomInfoVo.getRoomUsageName())) {
                            optionalSpaceInfo.setSpaceUseName(solutionRoomInfoVo.getRoomUsageName());
                        }
                        optionalSpaceList.add(optionalSpaceInfo);
                    }
                    optionalSpaceResponse.setOptionalSpaceList(optionalSpaceList);
                }
                response.add(optionalSpaceResponse);
            }
        }

        return response;
    }

    /**
     * 判断是否是特殊户型
     *
     * @param houseTypeId
     * @return Author: ZHAO
     * Date: 2018年5月29日
     */
    private boolean isSpecialHouseType(Integer houseTypeId) {
        if (houseTypeId == null) {
            return false;
        }
        DicListDto dicListResponseVo = dicProxy.getDicListByKey(ProductProgramPraise.SPECIAL_HOUSETYPE);//特殊户型
        if (dicListResponseVo != null && CollectionUtils.isNotEmpty(dicListResponseVo.getDicList())) {
            for (DicDto dicEntityVo : dicListResponseVo.getDicList()) {
                if (dicEntityVo.getKeyDesc().equals(houseTypeId.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 设置可替换信息
     *
     * @param optionalRoomResponseVos
     * @param width
     * @return Author: ZHAO
     * Date: 2018年6月1日
     */
    private void setResplaceInfo(SpaceEntity spaceEntity, List<OptionalRoomResponseVo> optionalRoomResponseVos, Integer width) {
        List<ReplaceImageInfo> replaceSpaceImgList = new ArrayList<ReplaceImageInfo>();//可替换空间图片
        List<ReplaceImageInfo> defaultImgList = new ArrayList<ReplaceImageInfo>();//默认空间图片
        List<String> replaceHeadImgList = new ArrayList<String>();//可替换空间图片
        if (null != optionalRoomResponseVos) {
            for (OptionalRoomResponseVo roomResponseVo : optionalRoomResponseVos) {
                if (roomResponseVo.getSolutionRoomId() != null && roomResponseVo.getSolutionRoomId().equals(spaceEntity.getRoomId())) {
                    if (CollectionUtils.isNotEmpty(roomResponseVo.getRoomDesignList())) {
                        for (SolutionRoomInfoVo roomInfoVo : roomResponseVo.getRoomDesignList()) {
                            ReplaceImageInfo imageInfo = setRoomImageInfo(roomInfoVo, width);
                            if (roomInfoVo.getIsDefault() != null && roomInfoVo.getIsDefault().equals(1)) {
                                //默认空间
                                defaultImgList.add(imageInfo);
                            } else {
                                //可替换空间
                                replaceSpaceImgList.add(imageInfo);
                            }
                            replaceHeadImgList.add(imageInfo.getPictureURL());
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(replaceSpaceImgList)) {
                defaultImgList.addAll(replaceSpaceImgList);
            }
            spaceEntity.setReplaceImgList(defaultImgList);
            spaceEntity.setReplaceSpaceImgList(replaceHeadImgList);
        }
    }

    /**
     * 可替换空间图片
     *
     * @param roomInfoVo
     * @param width
     * @return Author: ZHAO
     * Date: 2018年6月1日
     */
    private ReplaceImageInfo setRoomImageInfo(SolutionRoomInfoVo roomInfoVo, Integer width) {
        ReplaceImageInfo replaceImageInfo = new ReplaceImageInfo();
        if (CollectionUtils.isNotEmpty(roomInfoVo.getPictureList())) {
            List<String> imgList = new ArrayList<String>();//空间其他图片集合
            for (SolutionRoomPicVo solutionRoomPicVo : roomInfoVo.getPictureList()) {
                if (StringUtils.isNotBlank(solutionRoomPicVo.getSolutionRoomPicURL())) {
                    String imgUrl = AliImageUtil.imageCompress(solutionRoomPicVo.getSolutionRoomPicURL(), 1, width, ImageConstant.SIZE_MIDDLE);
                    if (solutionRoomPicVo.getIsFirst() != null && solutionRoomPicVo.getIsFirst().equals(1)) {
                        //首图
                        replaceImageInfo.setPictureURL(imgUrl);
                    }
                    //其他图片
                    imgList.add(imgUrl);
                }
            }
            replaceImageInfo.setImgList(imgList);
        }
        return replaceImageInfo;
    }

    @Override
    public List<ContrastInfoResponse> queryContrastInfo(HttpProductProgramRequest request, Integer userId) {
        List<ContrastInfoResponse> responseList = new ArrayList<ContrastInfoResponse>();

        Integer width = 0;
        if (request.getWidth() != null) {
            width = request.getWidth();
        }

        List<CompareSolutionResponseVo> compareSolutionResponseVos = productProgramProxy.compareSolution(userId, request.getHouseProjectId(), request.getZoneId(), request.getHouseTypeId());
        if (CollectionUtils.isNotEmpty(compareSolutionResponseVos)) {
            //硬装标准信息
//            Map<String, List<HardStandardSpaceResponseVo>> hardStandardMap = getHardStandList();

            Map<String, List<HardStandardSpaceResponseVo>> hardStandardMap = new HashMap<>();

            for (CompareSolutionResponseVo compareSolutionResponseVo : compareSolutionResponseVos) {
                List<String> tagList = new ArrayList<String>();
                ContrastInfoResponse contrastInfoResponse = new ContrastInfoResponse();
                contrastInfoResponse.setSolutionId(compareSolutionResponseVo.getSolutionId());
                if (StringUtils.isNotBlank(compareSolutionResponseVo.getSolutionName())) {
                    contrastInfoResponse.setSolutionName(compareSolutionResponseVo.getSolutionName());
                }
                if (StringUtils.isNotBlank(compareSolutionResponseVo.getStyleName())) {
                    contrastInfoResponse.setSolutionStyleName(compareSolutionResponseVo.getStyleName());
                    tagList.add(compareSolutionResponseVo.getStyleName());
                }
                //装修类型
                String spaceCategory = "";
                if (compareSolutionResponseVo.getDecorationType() != null) {
                    //如果是特殊户型  则默认为纯软装
                    if (compareSolutionResponseVo.getDecorationType() == ProductProgramPraise.HARD_STANDARD_ALL && !isSpecialHouseType(request.getHouseTypeId())) {
                        spaceCategory = ProductProgramPraise.HARD_SOFT;
                        if (StringUtils.isNotBlank(compareSolutionResponseVo.getSeriesName())) {
                            contrastInfoResponse.setSolutionSeriesName(compareSolutionResponseVo.getSeriesName());
                        }
                    } else if (compareSolutionResponseVo.getDecorationType() == ProductProgramPraise.HARD_STANDARD_SOFT) {
                        spaceCategory = ProductProgramPraise.HARD_STANDARD_SOFT_DESC;
                    }
                }
                contrastInfoResponse.setCategory(spaceCategory);
                if (StringUtils.isNotBlank(compareSolutionResponseVo.getSolutionDesignIdea())) {
                    contrastInfoResponse.setSolutionDesignIdea(compareSolutionResponseVo.getSolutionDesignIdea());
                }
                if (StringUtils.isNotBlank(compareSolutionResponseVo.getAdvantage())) {
                    contrastInfoResponse.setAdvantage(compareSolutionResponseVo.getAdvantage());
                }
                if (CollectionUtils.isNotEmpty(compareSolutionResponseVo.getTagList())) {
                    tagList.addAll(compareSolutionResponseVo.getTagList());
                }
                contrastInfoResponse.setTagList(tagList);
                if (CollectionUtils.isNotEmpty(compareSolutionResponseVo.getRoomList())) {
                    setContrastSpaceInfo(contrastInfoResponse, compareSolutionResponseVo.getRoomList(), width);
                }
                responseList.add(contrastInfoResponse);
            }
        }

        return responseList;
    }

    @Override
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

        //若宽度没传，默认宽度750
        if (IntegerUtil.isEmpty(request.getWidth())) {
            request.setWidth(750);
        }

        //加切图参数
        if (StringUtil.isNotBlank(response.getHouseTypeImage())) {
            response.setHouseTypeImage(QiniuImageUtils.compressImageAndSamePicTwo(response.getHouseTypeImage(), request.getWidth() / 2, -1));
        }
        if (CollectionUtils.isNotEmpty(response.getSolutionEffectInfoList())) {

            List<SolutionEffectInfo> solutionEffectInfoList = response.getSolutionEffectInfoList();

            response.setSolutionEffectInfoList(solutionEffectInfoList.stream().filter(e ->
                    e.getSolutionId() == 3232 ? false : true).collect(Collectors.toList()));

            setSolutionEffectInfo(solutionEffectInfoList, request);

            Collections.sort(solutionEffectInfoList, Comparator.comparing(SolutionEffectInfo::getSolutionType).thenComparing(item ->
                    -DateUtils.parseToDateTime(item.getPublishTime(), "yyyy-MM-dd HH:mm:ss").getTime()));
        }
        setSpaceDesignImage(response, request);


        return response;
    }

    @Override
    public SolutionEffectResponse querySolutionDescList(SolutionListRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("apartmentId", request.getApartmentId());
        params.put("orderNum", request.getOrderId());
        params.put("solutionId", request.getSolutionId());
        SolutionEffectResponse response = productProgramProxy.querySolutionList(params);
        if (response == null) {
            return null;
        }
        //若宽度没传，默认宽度750
        if (IntegerUtil.isEmpty(request.getWidth())) {
            request.setWidth(750);
        }

        //加切图参数
        if (StringUtil.isNotBlank(response.getHouseTypeImage())) {
            response.setHouseTypeImage(
                    AliImageUtil.imageCompress(response.getHouseTypeImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
        }
        if (CollectionUtils.isNotEmpty(response.getSolutionEffectInfoList())) {

            List<SolutionEffectInfo> solutionEffectInfoList = response.getSolutionEffectInfoList();

            if (request.getVersion() == null || request.getVersion() != 2) {//低版本过滤掉，修改中专属方案
                solutionEffectInfoList.removeIf(e -> e.getIsRevised());
            }
            // 去除艾佳贷专用方案
            response.setSolutionEffectInfoList(solutionEffectInfoList.stream().filter(e ->
                    e.getSolutionId() == 3232 ? false : true).collect(Collectors.toList()));

            setSolutionEffectInfo(solutionEffectInfoList, request);

            // 排序
            Collections.sort(solutionEffectInfoList, Comparator.comparing(SolutionEffectInfo::getSolutionType).thenComparing(item ->
                    -DateUtils.parseToDateTime(item.getPublishTime(), "yyyy-MM-dd HH:mm:ss").getTime()));
        }
        setSpaceDesignImage(response, request);
        return response;
    }

    @Override
    public SolutionEffectResponse queryRoomListBySolutionId(RoomListRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("apartmentId", request.getApartmentId());
        params.put("orderNum", request.getOrderId());
        params.put("solutionId", request.getSolutionId());

        SolutionEffectResponse response = productProgramProxy.queryRoomListBySolutionId(params);
        if (response == null) {
            return null;
        }

        // 切图
        response.setHouseTypeImage(
                AliImageUtil.imageCompress(response.getHouseTypeImage(),
                        request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));


        if (CollectionUtils.isNotEmpty(response.getSpaceMarkList())) {
            //过滤没有设计的空间标识
            List<SpaceMark> emptySpaceList = new ArrayList<>();
            for (SpaceMark spaceMark : response.getSpaceMarkList()) {
                if (CollectionUtils.isEmpty(spaceMark.getSpaceDesignList())) {
                    emptySpaceList.add(spaceMark);
                } else {
                    // 切图
                    for (SpaceDesign spaceDesign : spaceMark.getSpaceDesignList()) {
                        spaceDesign.setHeadImage(
                                AliImageUtil.imageCompress(spaceDesign.getHeadImage(),
                                        request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                        for (RoomPictureDto roomPictureDto : spaceDesign.getRoomPictureDtoList()) {
                            roomPictureDto.setPictureUrl(
                                    AliImageUtil.imageCompress(roomPictureDto.getPictureUrl(),
                                            request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE)
                            );
                        }
                    }
                }

            }
            response.getSpaceMarkList().removeAll(emptySpaceList);
        }
        return response;
    }

    @Override
    public SolutionEffectResponse querySolutionList(SolutionListRequest request) {
        SolutionEffectResponse response = productProgramProxy.querySolutionListByHouseId(request.getHouseTypeId(), request.getOrderId(), null);
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

        //若宽度没传，默认宽度750
        if (IntegerUtil.isEmpty(request.getWidth())) {
            request.setWidth(750);
        }

        //加切图参数
        if (StringUtil.isNotBlank(response.getHouseTypeImage())) {
            response.setHouseTypeImage(QiniuImageUtils.compressImageAndSamePicTwo(response.getHouseTypeImage(), request.getWidth() / 2, -1));
        }
        if (CollectionUtils.isNotEmpty(response.getSolutionEffectInfoList())) {

            List<SolutionEffectInfo> solutionEffectInfoList = response.getSolutionEffectInfoList();

            setSolutionEffectInfo(solutionEffectInfoList, request);
            Collections.sort(solutionEffectInfoList, Comparator.comparing(SolutionEffectInfo::getSolutionType).thenComparing(item ->
                    -DateUtils.parseToDateTime(item.getPublishTime(), "yyyy-MM-dd HH:mm:ss").getTime()));
        }
        setSpaceDesignImage(response, request);

        return response;
    }

    private void setSolutionEffectInfo(List<SolutionEffectInfo> solutionEffectInfoList, SolutionListRequest request) {
        for (SolutionEffectInfo solutionEffectInfo : solutionEffectInfoList) {
            if (StringUtil.isNotBlank(solutionEffectInfo.getHeadImage())) {
                solutionEffectInfo.setHeadImage(AliImageUtil.imageCompress(solutionEffectInfo.getHeadImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
            }
            if (CollectionUtils.isNotEmpty(solutionEffectInfo.getSpaceDesignList())) {
                for (SpaceDesign spaceDesign : solutionEffectInfo.getSpaceDesignList()) {
                    if (StringUtil.isNotBlank(spaceDesign.getHeadImage())) {
                        spaceDesign.setHeadImage(AliImageUtil.imageCompress(spaceDesign.getHeadImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                    }
                    if (CollectionUtils.isNotEmpty(spaceDesign.getRoomPictureDtoList())) {
                        spaceDesign.setRoomPictureDtoList(spaceDesign.getRoomPictureDtoList().stream().map(picture -> {
                            picture.setPictureUrlOrigin(picture.getPictureUrl());
                            picture.setPictureUrl(AliImageUtil.imageCompress(picture.getPictureUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                            return picture;
                        }).collect(Collectors.toList()));
                    }
                }
            }
        }
    }

    private void setSpaceDesignImage(SolutionEffectResponse response, SolutionListRequest request) {
        if (CollectionUtils.isNotEmpty(response.getSpaceMarkList())) {
            for (SpaceMark spaceMark : response.getSpaceMarkList()) {
                if (CollectionUtils.isNotEmpty(spaceMark.getSpaceDesignList())) {
                    for (SpaceDesign spaceDesign : spaceMark.getSpaceDesignList()) {
                        if (StringUtil.isNotBlank(spaceDesign.getHeadImage())) {
                            spaceDesign.setHeadImage(AliImageUtil.imageCompress(spaceDesign.getHeadImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                        }
                        if (CollectionUtils.isNotEmpty(spaceDesign.getRoomPictureDtoList())) {
                            spaceDesign.setRoomPictureDtoList(spaceDesign.getRoomPictureDtoList().stream().map(picture -> {
                                picture.setPictureUrlOrigin(picture.getPictureUrl());
                                picture.setPictureUrl(AliImageUtil.imageCompress(picture.getPictureUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                                return picture;
                            }).collect(Collectors.toList()));
                        }
                    }
                }
            }
        }
    }

    /**
     * 软硬装及选配项目信息
     *
     * @param request
     * @return
     */
    @Override
    public SelectionResponse querySpaceDesignSelections(SelectionRequest request) {
        if (request.getWidth() == null) {
            request.setWidth(750);
        }
        // 空间效果
        List<SpaceDesign> spaceDesignList = new ArrayList<>();
        //向dolly查询软硬装信息 {包数据和硬装在一起}
        Map<String, Object> queryDnaInfoMap = concurrentQuerySkuList(request.getSpaceIdList());

        //软装sku及可替换项查询 解析数据
        List<OptionalSkusResponseVo> optionalSkusResponseVoList = (List<OptionalSkusResponseVo>) queryDnaInfoMap.get(ConcurrentTaskEnum.QUERY_OPTIONAL_SKUS_BYROOMIDS.name());
        //软装数据响应
        List<OptionalSoftResponse> softResponse = getOptionalSoftMore(optionalSkusResponseVoList, request.getWidth(), Lists.newArrayList());

        // 硬装sku以及可替换项 解析
        RoomAndHardItemInfoVo hardResponseVo = (RoomAndHardItemInfoVo) queryDnaInfoMap.get(ConcurrentTaskEnum.QUERY_HARDSELECTION_LIST.name());
        if (null != hardResponseVo) {
            try {
                //获取空间列表
                List<SpaceDesignVo> spaceDesignVoList = hardResponseVo.getSpaceDesignList();
                // 遍历请求中的空间列表 和 响应的空间列表进行匹配
                for (Integer spaceId : request.getSpaceIdList()) {
                    // 初始化 空间效果
                    SpaceDesign spaceDesign = new SpaceDesign();
                    //遍历硬装
                    for (SpaceDesignVo spaceDesignVo : spaceDesignVoList) {
                        // 响应里的空间id和请求中空间id进行匹配
                        if (spaceDesignVo.getSpaceDesignId().equals(spaceId)) {
                            try {
                                spaceDesign = JsonUtils.json2obj(JsonUtil.toJsonString(spaceDesignVo), SpaceDesign.class);
                            } catch (IOException e) {
                                LOG.error("ProductProgramService.querySpaceDesignSelections o2o-exception , more info :", e);
                            }
                            spaceDesign.setHeadImageOrigin(spaceDesign.getHeadImage());
                            // 空间设计头图 图片处理
                            if (StringUtil.isNotBlank(spaceDesign.getHeadImage())) {
                                spaceDesign.setHeadImage(AliImageUtil.imageCompress(spaceDesign.getHeadImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                            }
                            // 空间硬装选配项列表 不为空则进行遍历以及转换成给app的响应
                            if (spaceDesignVo != null && CollectionUtils.isNotEmpty(spaceDesignVo.getHardItemList())) {
                                List<HardItem> hardItemList = new ArrayList<>();

                                //空间硬装选配类型 遍历 RoomHardItemClass 转换成 HardItem
                                for (RoomHardItemClass hardItemClass : spaceDesignVo.getHardItemList()) {
                                    // 硬装选配项目
                                    HardItem item = initHardItem(hardItemClass);
                                    List<HardItemSelection> selectionList = new ArrayList<>();

                                    //空间硬装sku
                                    if (!CollectionUtils.isEmpty(hardItemClass.getHardItemClassList())) {
                                        for (RoomHardItem roomHardItem : hardItemClass.getHardItemClassList()) {
                                            HardItemSelection itemSelection = new HardItemSelection();
                                            setItemSelection(roomHardItem, request.getWidth(), itemSelection);
                                            if (itemSelection.getDefaultSelection()) {
                                                selectionList.add(0, itemSelection);
                                            } else {
                                                selectionList.add(itemSelection);
                                            }
                                        }
                                    }
                                    item.setHardItemSelectionList(selectionList);

                                    //全屋空间
                                    if (RoomUseEnum.ROOM_WHOLE.getCode() == spaceDesignVo.getSpaceUsageId() &&
                                            !CollectionUtils.isEmpty(hardItemClass.getHardPackageList())) {
                                        List<RoomHardPackageVo> roomHardPackageVoList = hardItemClass.getHardPackageList();
                                        //处理图片
                                        for (RoomHardPackageVo roomHardPackageVo : roomHardPackageVoList) {
                                            roomHardPackageVo.setPackageSmallUrl(QiniuImageUtils.compressImageAndSamePic(roomHardPackageVo.getPackageUrl(), 100, 100));
                                        }
                                        item.setHardPackageList(roomHardPackageVoList);
                                    }

                                    hardItemList.add(item);
                                }
                                spaceDesign.setHardItemList(hardItemList);
                            }
                        }
                    }
                    // 空间软装选配项列表 赋值
                    spaceDesign = markOptionalSoftResponseList(softResponse, spaceId, spaceDesign);
                    spaceDesignList.add(spaceDesign);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("hard info o2o-exception , more info :", e);
            }
            /*SelectionResponse response = new SelectionResponse();
            response.setHeadImage(QiniuImageUtils.compressImageAndSamePicTwo(hardResponseVo.getHeadImage(),request.getWidth(),-1));
            response.setSpaceDesignList(spaceDesignList);
            return response;*/
        } else {
            LOG.error("硬装数据为空");
        }
        // 封装返回数据 空间设计信息
        SelectionResponse response = new SelectionResponse();
        if (hardResponseVo != null && StringUtils.isNotBlank(hardResponseVo.getHeadImage())) {
            response.setHeadImage(AliImageUtil.imageCompress(hardResponseVo.getHeadImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL));
        }
        response.setSpaceDesignList(spaceDesignList);
        return response;
    }

    /**
     * 软硬装详情列表（不包含替换项数据）
     *
     * @param request
     * @return
     */
    @Override
    public SelectionSimpleResponse querySpaceDesignList(SelectionRequest request) {
        if (request.getWidth() == null) {
            request.setWidth(750);
        }
        // 空间效果
        List<SpaceDesignSimple> spaceDesignList = new ArrayList<>();
        //向dolly查询软硬装信息 {包数据和硬装在一起}
        Map<String, Object> queryDnaInfoMap = concurrentQuerySkuList(request.getSpaceIdList());

        //软装sku及可替换项查询 解析数据
        List<OptionalSkusResponseVo> optionalSkusResponseVoList = (List<OptionalSkusResponseVo>) queryDnaInfoMap.get(ConcurrentTaskEnum.QUERY_OPTIONAL_SKUS_BYROOMIDS.name());

        //软装数据响应
        List<OptionalSoftResponse> softResponse = getOptionalSoftResponses(optionalSkusResponseVoList, null, null);
        List<Integer> supportDrawHardCategoryList = Lists.newArrayList(supportDrawHardCategory.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
        // 硬装sku以及可替换项 解析
        RoomAndHardItemInfoVo hardResponseVo = (RoomAndHardItemInfoVo) queryDnaInfoMap.get(ConcurrentTaskEnum.QUERY_HARDSELECTION_LIST.name());
        if (null != hardResponseVo) {
            try {
                //获取空间列表
                List<SpaceDesignVo> spaceDesignVoList = hardResponseVo.getSpaceDesignList();
                // 遍历请求中的空间列表 和 响应的空间列表进行匹配
                for (Integer spaceId : request.getSpaceIdList()) {
                    // 初始化 空间效果
                    SpaceDesignSimple spaceDesign = new SpaceDesignSimple();
                    //遍历硬装
                    for (SpaceDesignVo spaceDesignVo : spaceDesignVoList) {
                        // 响应里的空间id和请求中空间id进行匹配
                        if (spaceDesignVo.getSpaceDesignId().equals(spaceId)) {
                            try {
                                spaceDesign = JsonUtils.json2obj(JsonUtil.toJsonString(spaceDesignVo), SpaceDesignSimple.class);
                            } catch (IOException e) {
                                LOG.error("ProductProgramService.querySpaceDesignList o2o-exception , more info :", e);
                            }
                            spaceDesign.setHeadImageOrigin(spaceDesign.getHeadImage());
                            // 空间设计头图 图片处理
                            if (StringUtil.isNotBlank(spaceDesign.getHeadImage())) {
                                spaceDesign.setHeadImage(AliImageUtil.imageCompress(spaceDesign.getHeadImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                            }
                            // 空间硬装选配项列表 不为空则进行遍历以及转换成给app的响应
                            if (spaceDesignVo != null && CollectionUtils.isNotEmpty(spaceDesignVo.getHardItemList())) {
                                List<HardItemSimple> hardItemList = new ArrayList<>();

                                //空间硬装选配类型 遍历 RoomHardItemClass 转换成 HardItem
                                for (RoomHardItemClass hardItemClass : spaceDesignVo.getHardItemList()) {
                                    // 硬装选配项目
                                    HardItemSimple item = initHardItemSimple(hardItemClass, request.getAppVersion(), supportDrawHardCategoryList, Sets.newHashSet());
                                    List<HardItemSimpleSelection> selectionList = new ArrayList<>();
                                    //空间硬装sku
                                    if (!CollectionUtils.isEmpty(hardItemClass.getHardItemClassList())) {

                                        for (int i = 0; i < hardItemClass.getHardItemClassList().size(); i++) {
                                            RoomHardItem roomHardItem = hardItemClass.getHardItemClassList().get(i);
                                            HardItemSelection itemSelection = new HardItemSelection();
                                            HardItemSimpleSelection hardItemSimpleSelection = new HardItemSimpleSelection();
                                            hardItemSimpleSelection.setHardSelectionId(roomHardItem.getHardItemId());
                                            List<HardProcessSimple> hardProcessList = new ArrayList<>();
                                            List<HardProcess> hardProcessDefaultList = new ArrayList<>();
                                            for (HardItemCraft hardItemCraft : roomHardItem.getHardItemCraftList()) {
                                                HardProcessSimple hardProcessSimple = new HardProcessSimple();
                                                hardProcessSimple.setPrice(hardItemCraft.getTotalPrice());
                                                hardProcessSimple.setProcessId(hardItemCraft.getCraftId());
                                                hardProcessSimple.setProcessDefault(hardItemCraft.isCraftDefault());
                                                hardProcessList.add(hardProcessSimple);
                                                if (hardItemCraft.isCraftDefault()) {
                                                    HardProcess hardProcess = new HardProcess();
                                                    hardProcess.setProcessId(hardItemCraft.getCraftId());
                                                    hardProcess.setProcessName(hardItemCraft.getCraftName());
                                                    hardProcess.setPrice(hardItemCraft.getTotalPrice());
                                                    hardProcess.setProcessDefault(hardItemCraft.isCraftDefault());
                                                    hardProcessDefaultList.add(hardProcess);
                                                    itemSelection.setHardSelectionId(roomHardItem.getHardItemId());
                                                    itemSelection.setHardSelectionName(roomHardItem.getHardItemName());
                                                    itemSelection.setHardSelectionDesc(roomHardItem.getHardItemDesc());
                                                    itemSelection.setHeadImage(QiniuImageUtils.compressImageAndSamePicTwo(roomHardItem.getHardItemHeadImage(), request.getWidth(), -1));
                                                    itemSelection.setSmallImage(QiniuImageUtils.compressImageAndSamePic(roomHardItem.getHardItemHeadImage(), 100, 100));
                                                    itemSelection.setDefaultSelection(true);
                                                }
                                            }
                                            if (!itemSelection.getDefaultSelection()) {
                                                hardItemSimpleSelection.setHardProcessList(hardProcessList);
                                                selectionList.add(hardItemSimpleSelection);
                                            }
                                            if (itemSelection.getDefaultSelection()) {
                                                itemSelection.setHardProcessList(hardProcessDefaultList);
                                                item.setHardItemDefault(itemSelection);
                                            }
                                        }
                                    }
                                    item.setHardItemSelectionList(selectionList);

                                    //全屋空间
                                    if (RoomUseEnum.ROOM_WHOLE.getCode() == spaceDesignVo.getSpaceUsageId() &&
                                            !CollectionUtils.isEmpty(hardItemClass.getHardPackageList())) {
                                        List<RoomHardPackageVo> roomHardPackageVoList = hardItemClass.getHardPackageList();
                                        //处理图片
                                        for (RoomHardPackageVo roomHardPackageVo : roomHardPackageVoList) {
                                            roomHardPackageVo.setPackageSmallUrl(QiniuImageUtils.compressImageAndSamePic(roomHardPackageVo.getPackageUrl(), 100, 100));
                                        }
                                        item.setHardPackageList(roomHardPackageVoList);
                                    }

                                    hardItemList.add(item);
                                }

                                spaceDesign.setHardItemList(hardItemList);

                            }
                        }
                    }
                    // 空间软装选配项列表 赋值
                    spaceDesign = markOptionalSoftSimpleList(softResponse, spaceId, spaceDesign);
                    spaceDesignList.add(spaceDesign);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("hard info o2o-exception , more info :", e);
            }
        } else {
            LOG.error("硬装数据为空");
        }
        // 封装返回数据 空间设计信息
        SelectionSimpleResponse response = new SelectionSimpleResponse();
        if (hardResponseVo != null && StringUtils.isNotBlank(hardResponseVo.getHeadImage())) {
            response.setHeadImage(AliImageUtil.imageCompress(hardResponseVo.getHeadImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL));
        }
        response.setSpaceDesignList(spaceDesignList);
        return response;
    }

    public List<ServiceItemDto> mergeServiceItemList(List<ServiceItemDto> serviceItemDtoList) {
        if (CollectionUtils.isNotEmpty(serviceItemDtoList)) {
            List<ServiceItemDto> result = Lists.newArrayList();
            Map<Integer, List<ServiceItemDto>> collect = serviceItemDtoList.stream().collect(groupingBy(ServiceItemDto::getSkuId));
            collect.forEach((integer, serviceItemDto) -> {
                ServiceItemDto serviceItemDtoForIndex = serviceItemDto.get(0);
                serviceItemDtoForIndex.setSkuImage(AliImageUtil.imageCompress(serviceItemDtoForIndex.getSkuImage(), 1, 750, ImageConstant.SIZE_MIDDLE));
                if (!serviceItemDtoForIndex.getSkuPriceRuleType().equals(2)) {
                    serviceItemDtoForIndex.setPriceDesc("");
                }
                result.add(serviceItemDtoForIndex);
            });
            return result;
        } else {
            return Lists.newArrayList();
        }
    }


    /**
     * 软硬装详情列表（不包含替换项数据）
     *
     * @param request
     * @return
     */
    @Override
    public SelectionSimpleResponse querySpaceDesignListAddBom(SelectionRequest request) {
        if (request.getWidth() == null) {
            request.setWidth(750);
        }
        SelectionSimpleResponse response = new SelectionSimpleResponse();
        // 空间效果
        List<SpaceDesignSimple> spaceDesignList = new ArrayList<>();
        //向dolly查询软硬装信息 {包数据和硬装在一起}
        Map<String, Object> queryDnaInfoMap = concurrentQuerySkuListAddBom(request.getSpaceIdList(), request.getSolutionId());

        if (request.getSolutionId() != null) {//组装服务费
            ServiceItemResponse serviceItemResponse = (ServiceItemResponse) queryDnaInfoMap.get(ConcurrentTaskEnum.QUERY_SOLUTION_SERVICE.name());
            if (serviceItemResponse != null && CollectionUtils.isNotEmpty(serviceItemResponse.getServiceItemList())) {
                serviceItemResponse.getServiceItemList().forEach(serviceItemDto -> {
                    serviceItemDto.setSkuImage(AliImageUtil.imageCompress(serviceItemDto.getSkuImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL));
                });
                response.setServiceItemList(this.mergeServiceItemList(serviceItemResponse.getServiceItemList()));
                response.setSolutionId(request.getSolutionId());
                response.setReformApartmentUrl(AliImageUtil.imageCompress(serviceItemResponse.getReformApartmentUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                response.setSolutionGraphicDesignUrl(AliImageUtil.imageCompress(serviceItemResponse.getSolutionGraphicDesignUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                response.setApartmentUrl(AliImageUtil.imageCompress(serviceItemResponse.getApartmentUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
            }
        }

        //软装sku及可替换项查询 解析数据
        List<OptionalSkusResponseVo> optionalSkusResponseVoList = (List<OptionalSkusResponseVo>) queryDnaInfoMap.get(ConcurrentTaskEnum.QUERY_OPTIONAL_SKUS_BYROOMIDS.name());

        Map<String, List<BomGroupVO>> softBomMap = Maps.newHashMap();
        Map<Integer, List<OptionalSkusResponseVo>> cabinetBomMapByRoom = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(optionalSkusResponseVoList)) {
            List<OptionalSkusResponseVo> optionalSkusResponseVos = optionalSkusResponseVoList.parallelStream().filter(optionalSkusResponseVo -> CollectionUtils.isNotEmpty(optionalSkusResponseVo.getBomGroupList()) && optionalSkusResponseVo.getBomGroupList().get(0) != null && optionalSkusResponseVo.getBomGroupList().get(0).getGroupType() != null && optionalSkusResponseVo.getBomGroupList().get(0).getGroupType().equals(7)).collect(Collectors.toList());
            setShowFreeFlag(optionalSkusResponseVos);
            softBomMap = optionalSkusResponseVos.parallelStream().collect(toMap(OptionalSkusResponseVo::getSuperKey, OptionalSkusResponseVo::getBomGroupList));

            List<OptionalSkusResponseVo> optionalSkusResponseVos2 = optionalSkusResponseVoList.stream()
                    .filter(optionalSkusResponseVo -> CollectionUtils.isNotEmpty(optionalSkusResponseVo.getBomGroupList())
                            && optionalSkusResponseVo.getBomGroupList().get(0) != null && optionalSkusResponseVo.getBomGroupList().get(0).getGroupType() != null
                            && optionalSkusResponseVo.getBomGroupList().get(0).getGroupType().equals(10)).collect(Collectors.toList());

            optionalSkusResponseVoList.removeIf(optionalSkusResponseVo -> CollectionUtils.isNotEmpty(optionalSkusResponseVo.getBomGroupList())
                    && optionalSkusResponseVo.getBomGroupList().get(0) != null && optionalSkusResponseVo.getBomGroupList().get(0).getGroupType() != null
                    && optionalSkusResponseVo.getBomGroupList().get(0).getGroupType().equals(10));

            cabinetBomMapByRoom = optionalSkusResponseVos2.stream().collect(groupingBy(OptionalSkusResponseVo::getRoomId));
        }
        //软装数据响应
        List<OptionalSoftResponse> softResponse = getOptionalSoftResponses(optionalSkusResponseVoList, softBomMap, request.getAppVersion());

        // 硬装sku以及可替换项 解析
        List<SpaceDesignVo> spaceDesignVoList = (List<SpaceDesignVo>) queryDnaInfoMap.get(ConcurrentTaskEnum.QUERY_HARDSELECTION_LIST.name());

        Map<Integer, SpaceDesignVo> spaceDesignVoMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(spaceDesignVoList)) {
            spaceDesignVoMap = spaceDesignVoList.parallelStream().collect(toMap(SpaceDesignVo::getSpaceDesignId, spaceDesignVo -> spaceDesignVo, (item1, item2) -> item1));
        }
        List<Integer> supportDrawHardCategoryList = Lists.newArrayList(supportDrawHardCategory.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());

        Set<Integer> supportFiltrateForFardClassifyList = Lists.newArrayList(SUPPORT_FILTRATE_FOR_HARD.split(",")).stream().map(Integer::parseInt).collect(Collectors.toSet());
        try {
            //获取空间列表
            // 遍历请求中的空间列表 和 响应的空间列表进行匹配
            List<Integer> guiBomFirstGroupIdList = Lists.newArrayList();
            for (Integer spaceId : request.getSpaceIdList()) {
                if (CollectionUtils.isNotEmpty(spaceDesignVoList)) {
                    //遍历硬装
                    for (SpaceDesignVo spaceDesignVo : spaceDesignVoList) {
                        // 响应里的空间id和请求中空间id进行匹配
                        if (spaceDesignVo.getSpaceDesignId().equals(spaceId)) {
                            // 初始化 空间效果
                            SpaceDesignSimple spaceDesign = new SpaceDesignSimple();
                            try {
                                spaceDesign = JsonUtils.json2obj(JsonUtil.toJsonString(spaceDesignVo), SpaceDesignSimple.class);
                            } catch (IOException e) {
                                LOG.error("ProductProgramService.querySpaceDesignList o2o-exception , more info :", e);
                            }
                            spaceDesign.setHeadImageOrigin(spaceDesign.getHeadImage());
                            // 空间设计头图 图片处理
                            if (StringUtil.isNotBlank(spaceDesign.getHeadImage())) {
                                spaceDesign.setHeadImage(AliImageUtil.imageCompress(spaceDesign.getHeadImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                            }
                            //设置硬装bom数据
                            if (CollectionUtils.isNotEmpty(spaceDesign.getRoomPictureDtoList())) {
                                spaceDesign.getRoomPictureDtoList().forEach(roomPictureDto -> {
                                    roomPictureDto.setPictureUrlOrigin(roomPictureDto.getPictureUrl());
                                    if (StringUtil.isNotBlank(roomPictureDto.getPictureUrl())) {
                                        roomPictureDto.setPictureUrl(AliImageUtil.imageCompress(roomPictureDto.getPictureUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                                    }
                                });
                            }
                            List<RoomHardItemClass> hardBomList = null;
                            if (CollectionUtils.isNotEmpty(spaceDesignVo.getHardItemList())) {
                                hardBomList = spaceDesignVo.getHardItemList().stream().filter(roomHardItemClass -> roomHardItemClass.getBomGroup() != null && roomHardItemClass.getBomGroup() != null && roomHardItemClass.getBomGroup().getGroupType() != null && roomHardItemClass.getBomGroup().getGroupType().equals(9)).collect(Collectors.toList());
                                spaceDesignVo.getHardItemList().removeIf(roomHardItemClass -> roomHardItemClass.getBomGroup() != null && roomHardItemClass.getBomGroup() != null && roomHardItemClass.getBomGroup().getGroupType() != null && roomHardItemClass.getBomGroup().getGroupType().equals(9));
                            }
                            // 空间硬装选配项列表 不为空则进行遍历以及转换成给app的响应
                            if (spaceDesignVo != null && CollectionUtils.isNotEmpty(spaceDesignVo.getHardItemList())) {
                                List<HardItemSimple> hardItemList = new ArrayList<>();

                                //空间硬装选配类型 遍历 RoomHardItemClass 转换成 HardItem
                                for (RoomHardItemClass hardItemClass : spaceDesignVo.getHardItemList()) {
                                    // 硬装选配项目
                                    HardItemSimple item = initHardItemSimple(hardItemClass, request.getAppVersion(), supportDrawHardCategoryList, supportFiltrateForFardClassifyList);
                                    List<HardItemSimpleSelection> selectionList = new ArrayList<>();
                                    //空间硬装sku
                                    if (!CollectionUtils.isEmpty(hardItemClass.getHardItemClassList())) {
                                        for (int i = 0; i < hardItemClass.getHardItemClassList().size(); i++) {
                                            RoomHardItem roomHardItem = hardItemClass.getHardItemClassList().get(i);
                                            HardItemSelection itemSelection = new HardItemSelection();
                                            HardItemSimpleSelection hardItemSimpleSelection = new HardItemSimpleSelection();
                                            itemSelection.setSuperKey(item.getSuperKey());
                                            hardItemSimpleSelection.setHardSelectionId(roomHardItem.getHardItemId());
                                            List<HardProcessSimple> hardProcessList = new ArrayList<>();
                                            List<HardProcess> hardProcessDefaultList = new ArrayList<>();
                                            for (HardItemCraft hardItemCraft : roomHardItem.getHardItemCraftList()) {
                                                HardProcessSimple hardProcessSimple = new HardProcessSimple();
                                                hardProcessSimple.setPrice(hardItemCraft.getTotalPrice());
                                                hardProcessSimple.setProcessId(hardItemCraft.getCraftId());
                                                hardProcessSimple.setProcessDefault(hardItemCraft.isCraftDefault());
                                                hardProcessList.add(hardProcessSimple);
                                                if (hardItemCraft.isCraftDefault()) {
                                                    HardProcess hardProcess = new HardProcess();
                                                    hardProcess.setProcessId(hardItemCraft.getCraftId());
                                                    hardProcess.setProcessName(hardItemCraft.getCraftName());
                                                    hardProcess.setPrice(hardItemCraft.getTotalPrice());
                                                    hardProcess.setProcessDefault(hardItemCraft.isCraftDefault());
                                                    hardProcess.setPriceDiff(hardItemCraft.getPriceDiff());
                                                    hardProcessDefaultList.add(hardProcess);
                                                    itemSelection.setHardSelectionId(roomHardItem.getHardItemId());
                                                    itemSelection.setHardSelectionName(roomHardItem.getHardItemName());
                                                    itemSelection.setHardSelectionDesc(roomHardItem.getHardItemDesc());
                                                    itemSelection.setHeadImage(QiniuImageUtils.compressImageAndSamePicTwo(roomHardItem.getHardItemHeadImage(), request.getWidth(), -1));
                                                    itemSelection.setSmallImage(QiniuImageUtils.compressImageAndSamePic(roomHardItem.getHardItemHeadImage(), 100, 100));
                                                    itemSelection.setDefaultSelection(true);
                                                }
                                            }
                                            if (!itemSelection.getDefaultSelection()) {
                                                hardItemSimpleSelection.setHardProcessList(hardProcessList);
                                                selectionList.add(hardItemSimpleSelection);
                                            }
                                            if (itemSelection.getDefaultSelection()) {
                                                itemSelection.setHardProcessList(hardProcessDefaultList);
                                                item.setHardItemDefault(itemSelection);
                                            }
                                        }
                                    }
                                    item.setHardItemSelectionList(selectionList);

                                    //全屋空间
                                    if (RoomUseEnum.ROOM_WHOLE.getCode() == spaceDesignVo.getSpaceUsageId() &&
                                            !CollectionUtils.isEmpty(hardItemClass.getHardPackageList())) {
                                        List<RoomHardPackageVo> roomHardPackageVoList = hardItemClass.getHardPackageList();
                                        //处理图片
                                        for (RoomHardPackageVo roomHardPackageVo : roomHardPackageVoList) {
                                            roomHardPackageVo.setPackageSmallUrl(QiniuImageUtils.compressImageAndSamePic(roomHardPackageVo.getPackageUrl(), 100, 100));
                                        }
                                        item.setHardPackageList(roomHardPackageVoList);
                                    }

                                    hardItemList.add(item);
                                }
                                hardItemList.sort(Comparator.comparingInt(HardItemSimple::getIsStandardItem).reversed());
                                spaceDesign.setHardItemList(hardItemList);

                            }
                            if (CollectionUtils.isNotEmpty(hardBomList)) {
                                //硬装定制柜聚合，二级类目一样则视为一项
                                Map<Integer, List<RoomHardItemClass>> collect = hardBomList.stream().collect(groupingBy(roomHardItemClass -> roomHardItemClass.getBomGroup().getSecondCategoryId()));
                                for (List<RoomHardItemClass> value : collect.values()) {
                                    if (CollectionUtils.isNotEmpty(value)) {
                                        RoomHardItemClass roomHardItemClass = value.get(0);
                                        guiBomFirstGroupIdList.add(roomHardItemClass.getBomGroup().getGroupId());
                                        HardItemSimple hardItemSimple = initHardItemSimple(roomHardItemClass, request.getAppVersion(), supportDrawHardCategoryList, supportFiltrateForFardClassifyList);
                                        hardItemSimple.setBomGroup(null);
                                        hardItemSimple.setHardItemName("定制家具");
                                        hardItemSimple.setHardItemId(roomHardItemClass.getBomGroup().getSecondCategoryId());
                                        CabinetBomDto cabinetBomDto = new CabinetBomDto();
                                        cabinetBomDto.setFurnitureType(4);
                                        cabinetBomDto.setGroupImage(AliImageUtil.imageCompress(roomHardItemClass.getBomGroup().getGroupImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL));
                                        cabinetBomDto.setGroupType(9);
                                        cabinetBomDto.setSecondCategoryId(roomHardItemClass.getBomGroup().getSecondCategoryId());
                                        cabinetBomDto.setSecondCategoryName(roomHardItemClass.getBomGroup().getSecondCategoryName());
                                        cabinetBomDto.setSuperKey(roomHardItemClass.getSuperKey());
                                        cabinetBomDto.setTagList(roomHardItemClass.getBomGroup().getTagList());
                                        cabinetBomDto.setIsStandardItem(roomHardItemClass.getIsStandardItem());
                                        List<ReplaceBomDto> replaceBomDtoList = value.stream().map(hardItemClass -> {
                                            ReplaceBomDto replaceBomDto = new ReplaceBomDto();
                                            HardBomGroup hardBomGroup = new HardBomGroup();
                                            BeanUtils.copyProperties(hardItemClass.getBomGroup(), hardBomGroup);
                                            hardBomGroup.setGroupImage(AliImageUtil.imageCompress(hardBomGroup.getGroupImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL));
                                            replaceBomDto.setBomGroupDefault(hardBomGroup);
                                            return replaceBomDto;
                                        }).collect(Collectors.toList());
                                        cabinetBomDto.setReplaceBomList(replaceBomDtoList);
                                        BigDecimal priceDiffSum = value.stream().map(roomHardItem -> roomHardItem.getBomGroup().getPriceDiff()).reduce(BigDecimal.ZERO, BigDecimal::add);
                                        BigDecimal priceSum = value.stream().map(roomHardItem -> roomHardItem.getBomGroup().getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                                        cabinetBomDto.setPriceDiff(priceDiffSum);
                                        cabinetBomDto.setPrice(priceSum);
                                        hardItemSimple.setCabinetBomGroup(cabinetBomDto);
                                        spaceDesign.getHardItemList().add(hardItemSimple);
                                    }
                                }
                            }
                            List<OptionalSkusResponseVo> cabinetList = cabinetBomMapByRoom.get(spaceId);
                            if (CollectionUtils.isNotEmpty(cabinetList)) {
                                guiBomFirstGroupIdList.add(cabinetList.get(0).getBomGroupList().get(0).getGroupId());
                                addCabinetBomResponse(softResponse, cabinetList, request);
                            }
                            // 空间软装选配项列表 赋值
                            spaceDesign = markOptionalSoftSimpleList(softResponse, spaceId, spaceDesign);
                            handlerBomVersion(request.getAppVersion(), spaceDesign);
                            spaceDesignList.add(spaceDesign);
                        }
                    }
                }
            }
            Map<Integer, Map<String, String>> colourAndTextureByGroupIdList = this.getColourAndTextureByGroupIdList(guiBomFirstGroupIdList);
            if (MapUtils.isNotEmpty(colourAndTextureByGroupIdList)) {
                for (SpaceDesignSimple spaceDesignSimple : spaceDesignList) {
                    if (CollectionUtils.isNotEmpty(spaceDesignSimple.getHardItemList())) {
                        for (HardItemSimple hardItemSimple : spaceDesignSimple.getHardItemList()) {
                            if (hardItemSimple.getCabinetBomGroup() != null) {
                                if (CollectionUtils.isNotEmpty(hardItemSimple.getCabinetBomGroup().getReplaceBomList())) {
                                    if (hardItemSimple.getCabinetBomGroup().getReplaceBomList().get(0).getBomGroupDefault() != null) {
                                        Map<String, String> map = colourAndTextureByGroupIdList.get(hardItemSimple.getCabinetBomGroup().getReplaceBomList().get(0).getBomGroupDefault().getGroupId());
                                        if (MapUtils.isNotEmpty(map)) {
                                            hardItemSimple.getCabinetBomGroup().setColour(map.get("colour"));
                                            hardItemSimple.getCabinetBomGroup().setTexture(map.get("texture"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(spaceDesignSimple.getOptionalSoftResponseList())) {
                        for (OptionalSoftResponse optionalSoftResponse : spaceDesignSimple.getOptionalSoftResponseList()) {
                            if (optionalSoftResponse.getCabinetBomGroup() != null) {
                                if (CollectionUtils.isNotEmpty(optionalSoftResponse.getCabinetBomGroup().getReplaceBomList())) {
                                    if (optionalSoftResponse.getCabinetBomGroup().getReplaceBomList().get(0).getBomGroupDefault() != null) {
                                        Map<String, String> map = colourAndTextureByGroupIdList.get(optionalSoftResponse.getCabinetBomGroup().getReplaceBomList().get(0).getBomGroupDefault().getGroupId());
                                        if (MapUtils.isNotEmpty(map)) {
                                            optionalSoftResponse.getCabinetBomGroup().setTexture(map.get("texture"));
                                            optionalSoftResponse.getCabinetBomGroup().setColour(map.get("colour"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.specialHandlingSku(spaceDesignList);
            if (CollectionUtils.isNotEmpty(spaceDesignList)) {
                for (SpaceDesignSimple spaceDesignSimple : spaceDesignList) {
                    if (CollectionUtils.isNotEmpty(spaceDesignSimple.getOptionalSoftResponseList())) {
                        for (OptionalSoftResponse optionalSoftResponse : spaceDesignSimple.getOptionalSoftResponseList()) {
                            if (optionalSoftResponse.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(optionalSoftResponse.getCabinetBomGroup().getReplaceBomList())) {
                                optionalSoftResponse.setGuiBomGroupQueryList(optionalSoftResponse.getCabinetBomGroup().getReplaceBomList().stream()
                                        .map(hardBomGroup -> new QueryCabinetPropertyListRequest.GroupQueryRequest()
                                                .setCabinetTypeName(hardBomGroup.getBomGroupDefault().getCabinetTypeName())
                                                .setCabinetType(hardBomGroup.getBomGroupDefault().getCabinetType())
                                                .setGroupId(hardBomGroup.getBomGroupDefault().getGroupId())).collect(Collectors.toList()));
                            }
                            if (CollectionUtils.isNotEmpty(optionalSoftResponse.getGuiBomGroupQueryList()) && optionalSoftResponse.getGuiBomGroupQueryList().size() > 1) {
                                optionalSoftResponse.setItemImage(spaceDesignSimple.getHeadImage());
                                optionalSoftResponse.getCabinetBomGroup().setGroupImage(spaceDesignSimple.getHeadImage());
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(spaceDesignSimple.getHardItemList())) {
                        for (HardItemSimple hardItemSimple : spaceDesignSimple.getHardItemList()) {
                            if (hardItemSimple.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(hardItemSimple.getCabinetBomGroup().getReplaceBomList())) {
                                hardItemSimple.setGuiBomGroupQueryList(hardItemSimple.getCabinetBomGroup().getReplaceBomList().stream()
                                        .map(hardBomGroup -> new QueryCabinetPropertyListRequest.GroupQueryRequest()
                                                .setCabinetTypeName(hardBomGroup.getBomGroupDefault().getCabinetTypeName())
                                                .setCabinetType(hardBomGroup.getBomGroupDefault().getCabinetType())
                                                .setGroupId(hardBomGroup.getBomGroupDefault().getGroupId())).collect(Collectors.toList()));
                            }
                        }
                    }
                }
            }
            Set<Integer> banitemReplaceHardByHardItemIdSet = Lists.newArrayList(appProgramBanitemReplaceHard.split(",")).stream().map(Integer::parseInt).collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(banitemReplaceHardByHardItemIdSet)) {
                spaceDesignList.forEach(spaceDesignSimple -> {
                    if (CollectionUtils.isNotEmpty(spaceDesignSimple.getHardItemList())) {
                        spaceDesignSimple.getHardItemList().forEach(hardItemSimple -> {
                            if (hardItemSimple.getHardItemId() != null && banitemReplaceHardByHardItemIdSet.contains(hardItemSimple.getHardItemId())) {
                                hardItemSimple.setHasReplaceItem(Boolean.FALSE);
                            }
                        });
                    }
                });
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            LOG.error("hard info o2o-exception , more info :", e);
        }
        // 封装返回数据 空间设计信息
        response.setSpaceDesignList(spaceDesignList);
        response.setReplaceAbleDto(getReplaceAbleDto(spaceDesignList, request.getSolutionId()));
        response.setBomReplaceAbleDto(getBomReplaceAbleDto(spaceDesignList, request.getSolutionId()));
        return response;
    }

    /**
     * 设置可替换dto
     *
     * @param spaceDesignList
     * @return
     */
    private ReplaceAbleDto getReplaceAbleDto(List<SpaceDesignSimple> spaceDesignList, Long solutionId) {
        ReplaceAbleDto replaceAbleDto = new ReplaceAbleDto();
        replaceAbleDto.setSolutionId(solutionId);
        List<ReplaceAbleDto.SpaceDesignListBean> spaceDesignDtoList = new ArrayList();
        for (SpaceDesignSimple spaceDesignSimple : spaceDesignList) {
            ReplaceAbleDto.SpaceDesignListBean spaceDesignListBean = new ReplaceAbleDto.SpaceDesignListBean();
            spaceDesignListBean.setSpaceDesignId(spaceDesignSimple.getSpaceDesignId());
            spaceDesignListBean.setSpaceUsageName(spaceDesignSimple.getSpaceUsageName());
            List<ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean> optionalSoftResponseList = new ArrayList();
            if (CollectionUtils.isNotEmpty(spaceDesignSimple.getOptionalSoftResponseList())) {
                for (OptionalSoftResponse optionalSoftResponse : spaceDesignSimple.getOptionalSoftResponseList()) {
                    if (optionalSoftResponse.getFreeAbleDolly() != null && optionalSoftResponse.getFreeAbleDolly() == 1
                            && optionalSoftResponse.getFurnitureDefault() != null && optionalSoftResponse.getFurnitureDefault().getFurnitureType() == 2) {
                        ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean optionalSoftResponseListBean = new ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean();
                        optionalSoftResponseListBean.setRoomId(optionalSoftResponse.getRoomId());
                        optionalSoftResponseListBean.setLastCategoryId(optionalSoftResponse.getLastCategoryId());
                        optionalSoftResponseListBean.setSuperKey(optionalSoftResponse.getSuperKey());
                        if (optionalSoftResponse.getFurnitureDefault() != null && optionalSoftResponse.getFurnitureDefault().getFreeFlag() == 0) {
                            ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean.FurnitureDefaultBean furnitureDefault = new ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean.FurnitureDefaultBean();
                            furnitureDefault.setSkuId(optionalSoftResponse.getFurnitureDefault().getSkuId());
                            optionalSoftResponseListBean.setFurnitureDefault(furnitureDefault);
                            optionalSoftResponseList.add(optionalSoftResponseListBean);
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(optionalSoftResponseList)) {
                spaceDesignListBean.setOptionalSoftResponseList(optionalSoftResponseList);
                spaceDesignDtoList.add(spaceDesignListBean);
            }
        }
        replaceAbleDto.setSpaceDesignList(spaceDesignDtoList);
        return replaceAbleDto;
    }

    /**
     * 设置可替换dto
     *
     * @param spaceDesignList
     * @return
     */
    private ReplaceAbleDto getBomReplaceAbleDto(List<SpaceDesignSimple> spaceDesignList, Long solutionId) {
        ReplaceAbleDto replaceAbleDto = new ReplaceAbleDto();
        replaceAbleDto.setSolutionId(solutionId);
        List<ReplaceAbleDto.SpaceDesignListBean> spaceDesignDtoList = new ArrayList();
        for (SpaceDesignSimple spaceDesignSimple : spaceDesignList) {
            ReplaceAbleDto.SpaceDesignListBean spaceDesignListBean = new ReplaceAbleDto.SpaceDesignListBean();
            spaceDesignListBean.setSpaceDesignId(spaceDesignSimple.getSpaceDesignId());
            spaceDesignListBean.setSpaceUsageName(spaceDesignSimple.getSpaceUsageName());
            List<ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean> optionalSoftResponseList = new ArrayList();
            if (CollectionUtils.isNotEmpty(spaceDesignSimple.getOptionalSoftResponseList())) {
                for (OptionalSoftResponse optionalSoftResponse : spaceDesignSimple.getOptionalSoftResponseList()) {
                    if (optionalSoftResponse.getFreeAbleDolly() != null && optionalSoftResponse.getFreeAbleDolly() == 1 && CollectionUtils.isNotEmpty(optionalSoftResponse.getBomGroupList())
                            && optionalSoftResponse.getBomGroupList().get(0).getFurnitureType() == 2) {
                        ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean optionalSoftResponseListBean = new ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean();
                        optionalSoftResponseListBean.setRoomId(optionalSoftResponse.getRoomId());
                        optionalSoftResponseListBean.setLastCategoryId(optionalSoftResponse.getLastCategoryId());
                        optionalSoftResponseListBean.setSuperKey(optionalSoftResponse.getSuperKey());
                        if (CollectionUtils.isNotEmpty(optionalSoftResponse.getBomGroupList()) && optionalSoftResponse.getBomGroupList().get(0).getFreeFlag() == 0) {//bom软装
                            List<ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean.BomGroupListBean> bomGroupList = new ArrayList<>();
                            ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean.BomGroupListBean furnitureDefault = new ReplaceAbleDto.SpaceDesignListBean.OptionalSoftResponseListBean.BomGroupListBean();
                            furnitureDefault.setGroupId(optionalSoftResponse.getBomGroupList().get(0).getGroupId());
                            bomGroupList.add(furnitureDefault);
                            optionalSoftResponseListBean.setBomGroupList(bomGroupList);
                            optionalSoftResponseList.add(optionalSoftResponseListBean);
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(optionalSoftResponseList)) {
                spaceDesignListBean.setOptionalSoftResponseList(optionalSoftResponseList);
                spaceDesignDtoList.add(spaceDesignListBean);
            }
        }
        replaceAbleDto.setSpaceDesignList(spaceDesignDtoList);
        return replaceAbleDto;
    }

    /**
     * 设置bom数据展示标识
     *
     * @param optionalSkusResponseVos
     */
    private void setShowFreeFlag(List<OptionalSkusResponseVo> optionalSkusResponseVos) {
        optionalSkusResponseVos.forEach(optionalSkusResponseVo -> {
            optionalSkusResponseVo.getBomGroupList().get(0).setFreeAble(getGroupFreeAble(optionalSkusResponseVo.getBomGroupList().get(0).getComponentList()));
            optionalSkusResponseVo.getBomGroupList().get(0).setShowFreeFlag(getShowFreeFlag(optionalSkusResponseVo.getBomGroupList().get(0).getFreeFlag(), optionalSkusResponseVo.getBomGroupList().get(0).getFreeAble(), optionalSkusResponseVo.getBomGroupList().get(0).getFurnitureType()));
        });
    }

    private Integer getGroupFreeAble(List<BomGroupVO.ComponentList> componentList) {
        if (CollectionUtils.isNotEmpty(componentList)) {
            for (BomGroupVO.ComponentList list : componentList) {
                if (list.getFreeAble() == 1) {
                    return 1;
                }
            }
        }
        return 0;
    }

    private void specialHandlingSku(List<SpaceDesignSimple> spaceDesignList) {
        List<Integer> bedLastCategoryIdList = ProductCategoryConstant.BED_LAST_CATEGORY_ID_LIST_NO_BUNK_BED;
        List<Integer> mattressLastCategoryIdList = ProductCategoryConstant.MATTRESS_LAST_CATEGORY_ID_LIST;
        //处理床和床垫的属性
        List<Integer> bedSkuList = Lists.newArrayList();
        List<Integer> mattressSkuList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(spaceDesignList)) {
            for (SpaceDesignSimple spaceDesignSimple : spaceDesignList) {
                if (CollectionUtils.isNotEmpty(spaceDesignSimple.getOptionalSoftResponseList())) {
                    for (OptionalSoftResponse optionalSoftResponse : spaceDesignSimple.getOptionalSoftResponseList()) {
                        if (optionalSoftResponse.getLastCategoryId() != null && optionalSoftResponse.getFurnitureDefault() != null && optionalSoftResponse.getFurnitureDefault().getSkuId() != null) {
                            if (bedLastCategoryIdList.contains(optionalSoftResponse.getLastCategoryId())) {
                                bedSkuList.add(optionalSoftResponse.getFurnitureDefault().getSkuId());
                            } else if (mattressLastCategoryIdList.contains(optionalSoftResponse.getLastCategoryId())) {
                                mattressSkuList.add(optionalSoftResponse.getFurnitureDefault().getSkuId());
                            }
                        }
                    }
                }
            }
            if (CollectionUtils.isEmpty(mattressSkuList) && CollectionUtils.isEmpty(bedSkuList)) {
                return;
            }
            List<SkuBaseInfoDto> skuBaseInfoDtos = CollectionUtils.isNotEmpty(mattressSkuList) ? productProxy.batchQuerySkuBaseInfo(mattressSkuList) : Lists.newArrayList();
            Map<Integer, SkuBaseInfoDto.SkuExtPropertyInfo> integerSkuExtPropertyInfoMap = this.batchQuerySkuExtPropertyBySkuIdListAndPropertyType(bedSkuList, 4);
            Map<Integer, SkuBaseInfoDto> skuBaseInfoDtoMap = CollectionUtils.isNotEmpty(skuBaseInfoDtos) ? skuBaseInfoDtos.stream().collect(toMap(o -> o.getSkuId(), o -> o)) : Maps.newHashMap();
            for (SpaceDesignSimple spaceDesignSimple : spaceDesignList) {
                if (CollectionUtils.isNotEmpty(spaceDesignSimple.getOptionalSoftResponseList())) {
                    for (OptionalSoftResponse optionalSoftResponse : spaceDesignSimple.getOptionalSoftResponseList()) {
                        if (optionalSoftResponse.getFurnitureDefault() != null && optionalSoftResponse.getFurnitureDefault().getSkuId() != null) {
                            SkuBaseInfoDto skuBaseInfoDto = MapUtils.isNotEmpty(skuBaseInfoDtoMap) ? skuBaseInfoDtoMap.get(optionalSoftResponse.getFurnitureDefault().getSkuId()) : null;
                            if (skuBaseInfoDto != null) {
                                optionalSoftResponse.getFurnitureDefault().setWidth(skuBaseInfoDto.getWidth());
                                optionalSoftResponse.getFurnitureDefault().setHeight(skuBaseInfoDto.getHeight());
                                optionalSoftResponse.getFurnitureDefault().setLength(skuBaseInfoDto.getLength());
                            } else {
                                SkuBaseInfoDto.SkuExtPropertyInfo skuExtPropertyInfo = MapUtils.isNotEmpty(integerSkuExtPropertyInfoMap) ? integerSkuExtPropertyInfoMap.get(optionalSoftResponse.getFurnitureDefault().getSkuId()) : null;
                                if (skuExtPropertyInfo != null && StringUtils.isNotBlank(skuExtPropertyInfo.getPropertyValue())) {
                                    String[] split = skuExtPropertyInfo.getPropertyValue().split(";");
                                    if (split.length == 4 || split.length == 8) {
                                        optionalSoftResponse.getFurnitureDefault().setSuggestMattressLength(Integer.parseInt(split[0]));
                                        optionalSoftResponse.getFurnitureDefault().setSuggestMattressWidth(Integer.parseInt(split[1]));
                                        optionalSoftResponse.getFurnitureDefault().setSuggestMattressMinHeight(Integer.parseInt(split[2]));
                                        optionalSoftResponse.getFurnitureDefault().setSuggestMattressMaxHeight(Integer.parseInt(split[3]));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void addCabinetBomResponse(List<OptionalSoftResponse> softResponse, List<OptionalSkusResponseVo> cabinetList, SelectionRequest request) {
        Map<Integer, List<OptionalSkusResponseVo>> cabinetByCategoryId = cabinetList.stream().collect(groupingBy(bom -> bom.getBomGroupList().get(0).getSecondCategoryId()));
        cabinetByCategoryId.forEach((key, value) -> {
            if (CollectionUtils.isNotEmpty(value)) {
                OptionalSkusResponseVo optionalSkusResponseVo = value.get(0);
                // 初始化可选软装响应
                OptionalSoftResponse optionalSoftResponse = new OptionalSoftResponse();
                optionalSoftResponse.setTypeTwoId(optionalSkusResponseVo.getTypeTwoId());
                optionalSoftResponse.setSupportDrawCategory(ProductCategoryConstant.EIGHT_BIG_CATEGORY_ID_LIST.contains(optionalSkusResponseVo.getLastCategoryId()));

                optionalSoftResponse.setHasReplaceItem(optionalSkusResponseVo.getHasReplaceItem());//设置是否可替换，bom代表是否可定制
                // 给 optionalSoftResponse 赋值
                optionalSoftResponse.setRoomId(optionalSkusResponseVo.getRoomId());
                optionalSoftResponse.setSuperKey(optionalSkusResponseVo.getSuperKey());
                optionalSoftResponse.setLastCategoryId(optionalSkusResponseVo.getBomGroupList().get(0).getSecondCategoryId());
                optionalSoftResponse.setLastCategoryName(optionalSkusResponseVo.getLastCategoryName());
                optionalSoftResponse.setCategory("定制家具");
                optionalSoftResponse.setItemName(optionalSkusResponseVo.getBomGroupList().get(0).getSecondCategoryName());
                optionalSoftResponse.setItemImage(AliImageUtil.imageCompress(optionalSkusResponseVo.getBomGroupList().get(0).getGroupImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL));
                if (CollectionUtils.isNotEmpty(optionalSkusResponseVo.getSubItemVos())) {
                    if (optionalSkusResponseVo.getSubItemVos().get(0).getParentSkuId() != 0) {
                        optionalSoftResponse.setDefaultSkuId(optionalSkusResponseVo.getSubItemVos().get(0).getParentSkuId());
                    } else {
                        optionalSoftResponse.setDefaultSkuId(optionalSkusResponseVo.getSubItemVos().get(0).getSkuId());
                    }
                }
                CabinetBomDto cabinetBomDto = new CabinetBomDto();
                cabinetBomDto.setFurnitureType(4);
                cabinetBomDto.setGroupImage(AliImageUtil.imageCompress(optionalSkusResponseVo.getBomGroupList().get(0).getGroupImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL));
                cabinetBomDto.setGroupType(10);
                cabinetBomDto.setSuperKey(optionalSkusResponseVo.getSuperKey());
                List<ReplaceBomDto> replaceBomDtoList = value.stream().map(hardItemClass -> {
                    ReplaceBomDto replaceBomDto = new ReplaceBomDto();
                    HardBomGroup hardBomGroup = new HardBomGroup();
                    BeanUtils.copyProperties(hardItemClass.getBomGroupList().get(0), hardBomGroup);
                    hardBomGroup.setGroupImage(AliImageUtil.imageCompress(hardBomGroup.getGroupImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL));
                    replaceBomDto.setBomGroupDefault(hardBomGroup);
                    return replaceBomDto;
                }).collect(Collectors.toList());
                cabinetBomDto.setReplaceBomList(replaceBomDtoList);
                BigDecimal priceDiffSum = value.stream().map(roomHardItem -> roomHardItem.getBomGroupList().get(0).getPriceDiff()).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal priceSum = value.stream().map(roomHardItem -> roomHardItem.getBomGroupList().get(0).getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                cabinetBomDto.setPriceDiff(priceDiffSum);
                cabinetBomDto.setPrice(priceSum);
                cabinetBomDto.setSecondCategoryName(optionalSkusResponseVo.getBomGroupList().get(0).getSecondCategoryName());
                cabinetBomDto.setTagList(optionalSkusResponseVo.getBomGroupList().get(0).getTagList());
                cabinetBomDto.setSecondCategoryId(optionalSkusResponseVo.getBomGroupList().get(0).getSecondCategoryId());
                cabinetBomDto.setSecondCategoryName(optionalSkusResponseVo.getBomGroupList().get(0).getSecondCategoryName());
                optionalSoftResponse.setCabinetBomGroup(cabinetBomDto);
                softResponse.add(optionalSoftResponse);
            }
        });
    }

    private void handlerBomVersion(String appVersion, SpaceDesignSimple spaceDesign) {
        if (VersionUtil.mustUpdate(appVersion, "5.4.3")) {
            if (CollectionUtils.isNotEmpty(spaceDesign.getHardItemList())) {
                spaceDesign.getHardItemList().removeIf(hardItemSimple -> hardItemSimple.getBomGroup() != null && hardItemSimple.getIsStandardItem().equals(0));
            }
        }
    }

    /**
     * 设置硬装选配包
     *
     * @param roomHardItem
     * @param width
     * @param itemSelection
     */
    private void setItemSelection(RoomHardItem roomHardItem, Integer width, HardItemSelection itemSelection) {
        itemSelection.setHardSelectionId(roomHardItem.getHardItemId());
        itemSelection.setHardSelectionName(roomHardItem.getHardItemName());
        itemSelection.setHardSelectionDesc(roomHardItem.getHardItemDesc());
        itemSelection.setHeadImage(QiniuImageUtils.compressImageAndSamePicTwo(roomHardItem.getHardItemHeadImage(), width, -1));
        itemSelection.setSmallImage(AliImageUtil.imageCompress(roomHardItem.getHardItemHeadImage(), 2, width, ImageConstant.SIZE_SMALL));
        itemSelection.setHardProcessList(getHardProcessList(roomHardItem, width, itemSelection));
        itemSelection.setLastCategoryId(roomHardItem.getLastCategoryId());
        itemSelection.setLastCategoryName(roomHardItem.getLastCategoryName());
    }

    /**
     * 获取硬装工艺信息
     *
     * @param roomHardItem
     * @param width
     * @param itemSelection
     * @return
     */
    private List<HardProcess> getHardProcessList(RoomHardItem roomHardItem, Integer width, HardItemSelection itemSelection) {
        List<HardProcess> hardProcessList = new ArrayList<>();
        for (HardItemCraft hardItemCraft : roomHardItem.getHardItemCraftList()) {
            HardProcess hardProcess = new HardProcess();
            hardProcess.setProcessId(hardItemCraft.getCraftId());
            hardProcess.setProcessName(hardItemCraft.getCraftName());
            hardProcess.setProcessImage(QiniuImageUtils.compressImageAndSamePicTwo(hardItemCraft.getCraftImage(), width, -1));
            hardProcess.setSmallImage(QiniuImageUtils.compressImageAndSamePic(hardItemCraft.getCraftImage(), 100, 100));
            hardProcess.setPrice(hardItemCraft.getTotalPrice());
            hardProcess.setProcessDefault(hardItemCraft.isCraftDefault());
            hardProcess.setPriceDiff(hardItemCraft.getPriceDiff() == null ? BigDecimal.ZERO : hardItemCraft.getPriceDiff());//差价设置
            //子sku处理
            if (CollectionUtils.isNotEmpty(hardItemCraft.getRoomHardItemList())) {
                List<HardItemSelection> hardItemSelectionList = Lists.newArrayList();
                for (RoomHardItem roomHardItemClient : hardItemCraft.getRoomHardItemList()) {
                    HardItemSelection itemSelectionClient = new HardItemSelection();
                    setItemSelection(roomHardItemClient, width, itemSelectionClient);
                    if (itemSelection.getDefaultSelection()) {
                        hardItemSelectionList.add(0, itemSelectionClient);
                    } else {
                        hardItemSelectionList.add(itemSelectionClient);
                    }
                }
                hardProcess.setHardItemSelection(hardItemSelectionList);
            }
            if (hardItemCraft.isCraftDefault()) {
                itemSelection.setDefaultSelection(true);
                hardProcessList.add(0, hardProcess);
            } else {
                hardProcessList.add(hardProcess);
            }
        }
        return hardProcessList;
    }

    /**
     * 查询软装替换
     *
     * @param request
     * @return
     */
    @Override
    @Deprecated
    public OptionalSoftResponse querySoftSkuList(MoreInfoRequest request) {

        List<Integer> spaceIdList = new ArrayList<>();
        spaceIdList.add(request.getSpaceId());
        List<FurnitureEntity> furnitureList = new ArrayList<FurnitureEntity>();
        List<OptionalSkusResponseVo> optionalSkusResponseVoList = productProgramProxy.queryOptionalSkusByRoomIds(spaceIdList);
        //软装数据响应
        List<OptionalSoftResponse> softResponse = getOptionalSoftMore(optionalSkusResponseVoList, request.getWidth(), Lists.newArrayList());
        if (CollectionUtils.isEmpty(softResponse)) {
            return null;
        }
        OptionalSoftResponse otionalSoftResponse = new OptionalSoftResponse();
        for (int i = 0; i < softResponse.size(); i++) {
            if (softResponse.get(i).getDefaultSkuId().equals(request.getSkuId())) {
                otionalSoftResponse = softResponse.get(i);
            }
        }

        return otionalSoftResponse;
    }

    /**
     * 获取更多信息
     *
     * @param furnitureEntity
     * @return
     */
    private void getMoreInfo(FurnitureEntity furnitureEntity, String appVersion) {
        if (furnitureEntity.getLastCategoryId() != null) {
            if (furnitureEntity.getLastCategoryId().equals(ProductCategoryConstant.CATEGORY_THREE_ID_CHAJI) ||
                    furnitureEntity.getLastCategoryId().equals(ProductCategoryConstant.CATEGORY_THREE_ID_BIANJI)) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.COFFEE_TABLE.getImageUrl());
            } else if (ProductCategoryConstant.SOFA_LAST_CATEGORY_ID_LIST.contains(furnitureEntity.getLastCategoryId())) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.SOFA.getImageUrl());
            } else if (furnitureEntity.getLastCategoryId().equals(ProductCategoryConstant.CATEGORY_THREE_ID_DIANSHIGUI)) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.TV_CABINET.getImageUrl());
            } else if (furnitureEntity.getLastCategoryId().equals(ProductCategoryConstant.CATEGORY_THREE_ID_CANZHUO)) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.TABLE.getImageUrl());
            } else if (furnitureEntity.getLastCategoryId().equals(ProductCategoryConstant.CATEGORY_THREE_ID_CANYI)) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.DINING_CHAIR.getImageUrl());
            } else if (ProductCategoryConstant.BED_LAST_CATEGORY_ID_LIST.contains(furnitureEntity.getLastCategoryId())) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.BED.getImageUrl());
            } else if (ProductCategoryConstant.MATTRESS_LAST_CATEGORY_ID_LIST.contains(furnitureEntity.getLastCategoryId())) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.MATTRESS.getImageUrl());
            } else if (furnitureEntity.getLastCategoryId().equals(ProductCategoryConstant.CATEGORY_THREE_ID_CHUANGTOUGUI)) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.BEDSIDE_CUPBOARD.getImageUrl());
            } else if (ProductCategoryConstant.CATEGORY_THREE_ID_D_LIST.contains(furnitureEntity.getLastCategoryId())) {
                if ((appVersion == null || VersionUtil.mustUpdate(appVersion, "5.5.4"))) {
                    furnitureEntity.setHasMore(false);
                } else {
                    furnitureEntity.setHasMore(true);
                }
            }
        } else if (furnitureEntity.getCategoryLevelTwoId() != null) {
            if (furnitureEntity.getCategoryLevelTwoId().equals(SoftItemEnum.COFFEE_TABLE.getCategoryLevelTwoId())) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.COFFEE_TABLE.getImageUrl());
            } else if (furnitureEntity.getCategoryLevelTwoId().equals(SoftItemEnum.SOFA.getCategoryLevelTwoId())) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.SOFA.getImageUrl());
            } else if (furnitureEntity.getCategoryLevelTwoId().equals(SoftItemEnum.TV_CABINET.getCategoryLevelTwoId())) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.TV_CABINET.getImageUrl());
            } else if (furnitureEntity.getCategoryLevelTwoId().equals(SoftItemEnum.TABLE.getCategoryLevelTwoId())) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.TABLE.getImageUrl());
            } else if (furnitureEntity.getCategoryLevelTwoId().equals(SoftItemEnum.DINING_CHAIR.getCategoryLevelTwoId())) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.DINING_CHAIR.getImageUrl());
            } else if (furnitureEntity.getCategoryLevelTwoId().equals(SoftItemEnum.BED.getCategoryLevelTwoId())) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.BED.getImageUrl());
            } else if (furnitureEntity.getCategoryLevelTwoId().equals(SoftItemEnum.MATTRESS.getCategoryLevelTwoId())) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.MATTRESS.getImageUrl());
            } else if (furnitureEntity.getCategoryLevelTwoId().equals(SoftItemEnum.BEDSIDE_CUPBOARD.getCategoryLevelTwoId())) {
                furnitureEntity.setHasMore(true);
                furnitureEntity.setMoreImageUrl(SoftItemEnum.BEDSIDE_CUPBOARD.getImageUrl());
            } else if (furnitureEntity.getCategoryLevelTwoId().equals(SoftItemEnum.DENG_JU.getCategoryLevelTwoId())) {
                if ((appVersion == null || VersionUtil.mustUpdate(appVersion, "5.5.4"))) {
                    furnitureEntity.setHasMore(false);
                } else {
                    furnitureEntity.setHasMore(true);
                }
            }
        }
    }

    /**
     * 查询硬装更多数据
     *
     * @param request
     * @return
     */
    @Override
    @Deprecated
    public List<HardItemSelection> queryHardSelectionList(HardMoreRequest request) {
        List<Integer> spaceIdList = new ArrayList<>();
        Integer spaceId = request.getSpaceId();
        spaceIdList.add(spaceId);
        RoomAndHardItemInfoVo roomAndHardItemInfoVo = productProgramProxy.queryHardSelectionList(spaceIdList);
        if (roomAndHardItemInfoVo == null) {
            return null;
        }
        List<HardItemSelection> hardItemSelectionList = new ArrayList<HardItemSelection>();
        //获取空间列表
        List<SpaceDesignVo> spaceDesignVoList = roomAndHardItemInfoVo.getSpaceDesignList();
        // 遍历请求中的空间列表 和 响应的空间列表进行匹配
        // 初始化 空间效果
        //遍历硬装
        for (SpaceDesignVo spaceDesignVo : spaceDesignVoList) {
            // 响应里的空间id和请求中空间id进行匹配
            if (spaceDesignVo.getSpaceDesignId().equals(spaceId)) {
                // 空间硬装选配项列表 不为空则进行遍历以及转换成给app的响应
                if (spaceDesignVo != null && CollectionUtils.isNotEmpty(spaceDesignVo.getHardItemList())) {

                    //空间硬装选配类型 遍历 RoomHardItemClass 转换成 HardItem
                    for (RoomHardItemClass hardItemClass : spaceDesignVo.getHardItemList()) {
                        if (hardItemClass.getHardItemClassId().equals(request.getHardItemId())) {
                            //空间硬装sku
                            if (!CollectionUtils.isEmpty(hardItemClass.getHardItemClassList())) {
                                for (RoomHardItem roomHardItem : hardItemClass.getHardItemClassList()) {
                                    HardItemSelection itemSelection = new HardItemSelection();
                                    setItemSelection(roomHardItem, request.getWidth(), itemSelection);
                                    if (itemSelection.getDefaultSelection()) {
                                        hardItemSelectionList.add(0, itemSelection);
                                    } else {
                                        hardItemSelectionList.add(itemSelection);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return hardItemSelectionList;
    }


    /**
     * 全屋空间下的数据处理 包的数据
     *
     * @param request
     * @param spaceDesign
     * @param spaceDesignVo
     */
    private SpaceDesign markPackageData(SelectionRequest request, SpaceDesign spaceDesign, SpaceDesignVo spaceDesignVo) {

        // 空间硬装选配列表
        List<RoomHardItemClass> hardItemVoList = spaceDesignVo.getHardItemList();

        List<RoomHardPackage> roomHardPackagesList = new ArrayList<>();
        for (RoomHardItemClass roomHardItemClassVo : hardItemVoList) {
            RoomHardItemClazz roomHardItemClazz = JsonUtils.json2obj(JsonUtils.obj2json(roomHardItemClassVo), RoomHardItemClazz.class);
            // 获取到全屋下的包节点
            List<RoomHardPackageVo> hardPackageListVoList = roomHardItemClassVo.getHardPackageList();

            for (RoomHardPackageVo hardPackageVo : hardPackageListVoList) {
                RoomHardPackage roomHardPackage = JsonUtils.json2obj(JsonUtils.obj2json(hardPackageVo), RoomHardPackage.class);
                // packageUrl 处理
                String packageUrl = roomHardPackage.getPackageUrl();
                packageUrl = QiniuImageUtils.compressImageAndSamePic(packageUrl, 100, 100);
                roomHardPackage.setPackageUrl(packageUrl);
                // 商品包下sku信息 roomHardSkuListVo 节点遍历 并处理hardItemHeadImage
                List<RoomPackageHardItem> roomHardSkuListVo = hardPackageVo.getRoomHardSkuList();
                List<RoomPackageHardItem> roomHardSkuList = new ArrayList<>();
                for (RoomPackageHardItem packageHardItem : roomHardSkuListVo) {
                    String hardItemHeadImage = packageHardItem.getHardItemHeadImage();
                    hardItemHeadImage = QiniuImageUtils.compressImageAndSamePicTwo(hardItemHeadImage, request.getWidth(), -1);
                    packageHardItem.setHardItemHeadImage(hardItemHeadImage);
                    roomHardSkuList.add(packageHardItem);
                }
                roomHardPackage.setRoomHardSkuList(roomHardSkuList);

                roomHardPackagesList.add(roomHardPackage);
            }
            roomHardItemClazz.setHardPackageList(roomHardPackagesList);
        }
        // 给全屋阶节点赋值
//        spaceDesign.set(roomHardPackagesList);

        return spaceDesign;
    }

    /**
     * 硬装简单信息初始化
     *
     * @param hardItemClass
     * @param appVersion
     * @return item
     */
    private HardItemSimple initHardItemSimple(RoomHardItemClass hardItemClass, String appVersion, List<Integer> supportDrawHardCategoryList, Set<Integer> supportFiltrateForFardClassifyList) {
        HardItemSimple item = new HardItemSimple();
        item.setContain(hardItemClass.getContain());
        item.setHardItemId(hardItemClass.getHardItemClassId());
        item.setHardItemName(hardItemClass.getHardItemClassName());
        item.setHardItemDesc(hardItemClass.getHardItemClassDesc());
        item.setLastCategoryId(hardItemClass.getLastCategoryId());
        item.setLastCategoryName(hardItemClass.getLastCategoryName());
        item.setHardItemImage(QiniuImageUtils.compressImageAndSamePic(hardItemClass.getHardItemClassImage(), 100, 100));
        item.setSupportDrawCategory(supportDrawHardCategoryList.contains(hardItemClass.getHardItemClassId()));
        item.setSuperKey(hardItemClass.getSuperKey());
        if (VersionUtil.mustUpdate(appVersion, "5.4.3") && hardItemClass.getBomGroup() != null) {
            item.setHasReplaceItem(false);
        } else {
            item.setHasReplaceItem(hardItemClass.getHasReplaceItem());
        }
        if (supportFiltrateForFardClassifyList.contains(item.getHardItemId())) {
            item.setSupportFiltrate(Boolean.TRUE);
        }
        item.setHardItemClassId(hardItemClass.getHardItemClassId());
        //选配列表不为空则勾选
        item.setIsStandardItem(hardItemClass.getIsStandardItem());
        item.setBomGroup(hardItemClass.getBomGroup());
        item.setHardItemType(hardItemClass.getHardItemType());
        if (item.getBomGroup() != null) {
            item.getBomGroup().setGroupImage(AliImageUtil.imageCompress(item.getBomGroup().getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
        }
        return item;
    }

    /**
     * by cangjifeng 2018-10-07
     * 初始化HardItem
     *
     * @param hardItemClass
     * @return item
     */
    private HardItem initHardItem(RoomHardItemClass hardItemClass) {
        HardItem item = new HardItem();
        item.setContain(hardItemClass.getContain());
        item.setHardItemId(hardItemClass.getHardItemClassId());
        item.setHardItemName(hardItemClass.getHardItemClassName());
        item.setHardItemDesc(hardItemClass.getHardItemClassDesc());
        item.setHardItemImage(QiniuImageUtils.compressImageAndSamePic(hardItemClass.getHardItemClassImage(), 100, 100));
        return item;
    }

    /**
     * spaceDesign.setOptionalSoftResponseList(softResponseList);
     *
     * @param softResponse
     * @param spaceId
     * @param spaceDesign
     * @return spaceDesign
     */
    private SpaceDesign markOptionalSoftResponseList(List<OptionalSoftResponse> softResponse, Integer spaceId, SpaceDesign spaceDesign) {
        //软装
        // List<OptionalSoftResponse> softResponse
        // Integer spaceId
        //  SpaceDesign spaceDesign
        List<OptionalSoftResponse> softResponseList = new ArrayList<>();
        Integer visible = 0;//空间默认不支持可视化
        if (CollectionUtils.isNotEmpty(softResponse)) {
            for (OptionalSoftResponse softResponseVo : softResponse) {
                if (softResponseVo.getRoomId().equals(spaceId)) {
                    softResponseList.add(softResponseVo);
                    spaceDesign.setOptionalSoftResponseList(softResponseList);
                    if (softResponseVo.getVisibleFlag() != null && softResponseVo.getVisibleFlag() == 1) {
                        visible = 1;//空间下有一个类目支持可视化，则该空间支持可视化
                    }
                }
            }
        }
        spaceDesign.setVisibleFlag(visible);
        return spaceDesign;
    }

    /**
     * 空间软装选配项简单信息
     *
     * @param softResponse
     * @param spaceId
     * @param spaceDesign
     * @return
     */
    private SpaceDesignSimple markOptionalSoftSimpleList(List<OptionalSoftResponse> softResponse, Integer spaceId, SpaceDesignSimple spaceDesign) {
        List<OptionalSoftResponse> softResponseList = new ArrayList<>();
        Integer visible = 0;//空间默认不支持可视化
        if (CollectionUtils.isNotEmpty(softResponse)) {
            for (OptionalSoftResponse softResponseVo : softResponse) {
                if (softResponseVo.getRoomId().equals(spaceId)) {
                    softResponseList.add(softResponseVo);
                    spaceDesign.setOptionalSoftResponseList(softResponseList);
                    if (softResponseVo.getVisibleFlag() != null && softResponseVo.getVisibleFlag() == 1) {
                        visible = 1;//空间下有一个类目支持可视化，则该空间支持可视化
                    }
                }
            }
        }
        spaceDesign.setVisibleFlag(visible);
        return spaceDesign;
    }

    /**
     * 是否可替换为免费，用方案中的标配决定
     *
     * @return
     */
    public static Integer getFreeAble(OptionalSkusResponseVo optionalSkusResponseVo) {
        if (optionalSkusResponseVo == null) {
            return 0;
        }
        if (CollectionUtils.isNotEmpty(optionalSkusResponseVo.getBomGroupList())) {
            Integer furnitureType = optionalSkusResponseVo.getBomGroupList().get(0).getFurnitureType();
            if (furnitureType != null && furnitureType == 2) {
                return 1;
            }
        }
        if (CollectionUtils.isNotEmpty(optionalSkusResponseVo.getSubItemVos())) {
            Integer furnitureType = optionalSkusResponseVo.getSubItemVos().get(0).getFurnitureType();
            if (furnitureType != null && furnitureType == 2) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * 解析软装数据，不包含更多项
     * 改造后20190301
     *
     * @param optionalSkusResponseVoList
     * @return
     */
    private List<OptionalSoftResponse> getOptionalSoftResponses(List<OptionalSkusResponseVo> optionalSkusResponseVoList, Map<String, List<BomGroupVO>> bomMap, String appVersion) {
        List<OptionalSoftResponse> softResponse = new ArrayList<>();
        List<Integer> bedSkuList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(optionalSkusResponseVoList)) {
//            软装定制柜聚合，二级类目一样则视为一项
            for (OptionalSkusResponseVo optionalSkusResponseVo : optionalSkusResponseVoList) {
                //可替换软装sku列表
                List<SolutionRoomSubItemVo> subItemVos = optionalSkusResponseVo.getSubItemVos();

                // 初始化可选软装响应
                OptionalSoftResponse optionalSoftResponse = new OptionalSoftResponse();
                if (MapUtils.isNotEmpty(bomMap)) {
                    List<BomGroupVO> bomGroupList = bomMap.get(optionalSkusResponseVo.getSuperKey());
                    if (CollectionUtils.isNotEmpty(bomGroupList)) {
                        bomGroupList.forEach(bomGroupVO -> bomGroupVO.setGroupImage(AliImageUtil.imageCompress(bomGroupVO.getGroupImage(), null, 100, ImageConstant.SIZE_SMALL)));
                    }
                    optionalSoftResponse.setBomGroupList(bomMap.get(optionalSkusResponseVo.getSuperKey()));
                }
                optionalSoftResponse.setTypeTwoId(optionalSkusResponseVo.getTypeTwoId());
                optionalSoftResponse.setSupportDrawCategory(ProductCategoryConstant.EIGHT_BIG_CATEGORY_ID_LIST.contains(optionalSkusResponseVo.getLastCategoryId()));
                // 床三级分类ID集合
                // 只要替换前的二级类目是床，则放开所有的床，不限三级类目:5009成人床;5010儿童床;5011高低床;5012组合床
                // 5058	床垫-二级;[5059	弹簧床垫;5060	棕床垫;5061	乳胶床垫;5062	复合床垫]
                if ((appVersion == null || VersionUtil.mustUpdate(appVersion, "5.5.4")) && ProductCategoryConstant.CATEGORY_THREE_ID_D_LIST.contains(optionalSkusResponseVo.getLastCategoryId())) {
                    optionalSoftResponse.setHasReplaceItem(false);
                } else {
                    optionalSoftResponse.setHasReplaceItem(optionalSkusResponseVo.getHasReplaceItem());//设置是否可替换，bom代表是否可定制
                }
                // 给 optionalSoftResponse 赋值
                optionalSoftResponse.setRoomId(optionalSkusResponseVo.getRoomId());
                optionalSoftResponse.setSuperKey(optionalSkusResponseVo.getSuperKey());
                optionalSoftResponse.setLastCategoryId(optionalSkusResponseVo.getLastCategoryId());
                optionalSoftResponse.setLastCategoryName(optionalSkusResponseVo.getLastCategoryName());
                optionalSoftResponse.setFreeAble(getFreeAble(optionalSkusResponseVo));
                optionalSoftResponse.setFreeAbleDolly(optionalSkusResponseVo.getFreeAble());
                if (StringUtils.isNotBlank(optionalSkusResponseVo.getTypeTwoName())) {
                    optionalSoftResponse.setCategory(optionalSkusResponseVo.getTypeTwoName());
                }
                if (CollectionUtils.isNotEmpty(optionalSkusResponseVo.getSubItemVos())) {
                    if (optionalSkusResponseVo.getSubItemVos().get(0).getParentSkuId() != 0) {
                        optionalSoftResponse.setDefaultSkuId(optionalSkusResponseVo.getSubItemVos().get(0).getParentSkuId());
                    } else {
                        optionalSoftResponse.setDefaultSkuId(optionalSkusResponseVo.getSubItemVos().get(0).getSkuId());
                    }
                }
                List<FurnitureEntity> furnitureList = setFurnitureInfo(subItemVos, 0, optionalSkusResponseVo.getBomGroupList(), optionalSkusResponseVo.getFreeAble());
                if (furnitureList != null) {
                    for (int i = 0; i < furnitureList.size(); i++) {
                        if (furnitureList.get(i).getSkuId() != null && furnitureList.get(i).getSkuId().equals(optionalSoftResponse.getDefaultSkuId())) {
                            getMoreInfo(furnitureList.get(i), appVersion);
                            optionalSoftResponse.setFurnitureDefault(furnitureList.get(i));
                            if (furnitureList.size() > 1) {
                                optionalSoftResponse.setReplaceAble(true);
                            }
                        }
                    }
                }
                if (ProductCategoryConstant.MATTRESS_LAST_CATEGORY_ID_LIST.contains(optionalSoftResponse.getLastCategoryId())) {
                    optionalSoftResponse.setItemType(2);
                } else if (ProductCategoryConstant.BED_LAST_CATEGORY_ID_LIST_NO_BUNK_BED.contains(optionalSoftResponse.getLastCategoryId())) {
                    optionalSoftResponse.setItemType(1);
                    if (optionalSoftResponse.getFurnitureDefault() != null && optionalSoftResponse.getFurnitureDefault().getSkuId() != null) {
                        bedSkuList.add(optionalSoftResponse.getFurnitureDefault().getSkuId());
                    }
                }
                optionalSoftResponse.setVisibleFlag(optionalSkusResponseVo.getVisibleFlag());
                softResponse.add(optionalSoftResponse);
            }
            if (CollectionUtils.isNotEmpty(bedSkuList)) {
                Map<Integer, SkuBaseInfoDto.SkuExtPropertyInfo> skuExtPropertyInfoMap = this.batchQuerySkuExtPropertyBySkuIdListAndPropertyType(bedSkuList, 4);
                for (OptionalSoftResponse optionalSoftResponse : softResponse) {
                    if (optionalSoftResponse.getFurnitureDefault() != null && optionalSoftResponse.getFurnitureDefault().getSkuId() != null) {
                        SkuBaseInfoDto.SkuExtPropertyInfo skuExtPropertyInfo = skuExtPropertyInfoMap.get(optionalSoftResponse.getFurnitureDefault().getSkuId());
                        if (skuExtPropertyInfo != null && StringUtils.isNotBlank(skuExtPropertyInfo.getPropertyValue())) {
                            String[] split = skuExtPropertyInfo.getPropertyValue().split(";");
                            if (split.length >= 4) {
                                optionalSoftResponse.getFurnitureDefault().setSuggestMattressLength(Integer.parseInt(split[0]));
                                optionalSoftResponse.getFurnitureDefault().setSuggestMattressWidth(Integer.parseInt(split[1]));
                                optionalSoftResponse.getFurnitureDefault().setSuggestMattressMinHeight(Integer.parseInt(split[2]));
                                optionalSoftResponse.getFurnitureDefault().setSuggestMattressMaxHeight(Integer.parseInt(split[3]));
                            }
                        }
                    }
                }
            }
        } else {
            LOG.error("软装可选sku是空");
            return null;
        }
        if (softResponse.size() <= 0) {
            return null;
        }
        return softResponse;
    }

    /**
     * 解析软装数据,包含更多项
     *
     * @param optionalSkusResponseVoList
     * @return
     */
    private List<OptionalSoftResponse> getOptionalSoftMore(List<OptionalSkusResponseVo> optionalSkusResponseVoList, Integer width, List<BomGroupVO> bomGroupList) {
        List<OptionalSoftResponse> softResponse = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(optionalSkusResponseVoList)) {
            for (OptionalSkusResponseVo optionalSkusResponseVo : optionalSkusResponseVoList) {
                if (CollectionUtils.isNotEmpty(optionalSkusResponseVo.getBomGroupList())) {
                    bomGroupList.addAll(optionalSkusResponseVo.getBomGroupList());
                }
                //可替换软装sku列表
                softResponse.add(handlerSolfItemResponseHasMore(optionalSkusResponseVo, width, 0));
            }
        } else {
            LOG.error("软装可选sku是空");
            return null;
        }
        if (softResponse.size() <= 0) {
            return null;
        }
        return softResponse;
    }

    OptionalSoftResponse handlerSolfItemResponseHasMore(OptionalSkusResponseVo optionalSkusResponseVo, Integer width, Integer itemType) {
        List<SolutionRoomSubItemVo> subItemVos = optionalSkusResponseVo.getSubItemVos();

        // 初始化可选软装响应
        OptionalSoftResponse optionalSoftResponse = new OptionalSoftResponse();
        // 给 optionalSoftResponse 赋值
        optionalSoftResponse.setSuperKey(optionalSkusResponseVo.getSuperKey());
        optionalSoftResponse.setRoomId(optionalSkusResponseVo.getRoomId());
        optionalSoftResponse.setLastCategoryName(optionalSkusResponseVo.getLastCategoryName());
        optionalSoftResponse.setLastCategoryId(optionalSkusResponseVo.getLastCategoryId());
        if (StringUtils.isNotBlank(optionalSkusResponseVo.getTypeTwoName())) {
            optionalSoftResponse.setCategory(optionalSkusResponseVo.getTypeTwoName());
        }
        if (CollectionUtils.isNotEmpty(optionalSkusResponseVo.getSubItemVos())) {
            if (optionalSkusResponseVo.getSubItemVos().get(0).getParentSkuId() != 0) {
                optionalSoftResponse.setDefaultSkuId(optionalSkusResponseVo.getSubItemVos().get(0).getParentSkuId());
            } else {
                optionalSoftResponse.setDefaultSkuId(optionalSkusResponseVo.getSubItemVos().get(0).getSkuId());
            }
        }
        List<FurnitureEntity> furnitureList = setFurnitureInfo(subItemVos, 0, optionalSkusResponseVo.getBomGroupList(), null);
        if (furnitureList != null) {
            Integer visible = 0;//类目默认不支持可视化
            for (int i = 0; i < furnitureList.size(); i++) {
                getMoreInfo(furnitureList.get(i), null);
                if (i != 0 && (furnitureList.get(i).getVisibleFlag() == null || furnitureList.get(i).getVisibleFlag() == 0)) {
                    visible = 0;//类目下有一个skuid不支持可视化，则该类目不支持可视化（除了第一个默认的）
                    break;
                }
                visible = 1;
            }
            optionalSoftResponse.setVisibleFlag(visible);
        }
        if (itemType != null && !itemType.equals(0) && CollectionUtils.isNotEmpty(furnitureList)) {
            List<Integer> skuList = furnitureList.stream().map(furnitureEntity -> furnitureEntity.getSkuId()).collect(Collectors.toList());
            if (itemType.equals(1)) {
                Map<Integer, SkuBaseInfoDto.SkuExtPropertyInfo> skuExtPropertyInfoMap = this.batchQuerySkuExtPropertyBySkuIdListAndPropertyType(skuList, 4);
                for (FurnitureEntity furnitureEntity : furnitureList) {
                    SkuBaseInfoDto.SkuExtPropertyInfo skuExtPropertyInfo = skuExtPropertyInfoMap.get(furnitureEntity.getSkuId());
                    if (skuExtPropertyInfo != null && StringUtils.isNotBlank(skuExtPropertyInfo.getPropertyValue())) {
                        String[] split = skuExtPropertyInfo.getPropertyValue().split(";");
                        if (split.length >= 4) {
                            furnitureEntity.setSuggestMattressLength(Integer.parseInt(split[0]));
                            furnitureEntity.setSuggestMattressWidth(Integer.parseInt(split[1]));
                            furnitureEntity.setSuggestMattressMinHeight(Integer.parseInt(split[2]));
                            furnitureEntity.setSuggestMattressMaxHeight(Integer.parseInt(split[3]));
                        }
                    }
                }
            }
//            else if (itemType.equals(2)) {
//                List<SkuBaseInfoDto> skuBaseInfoDtos = productProxy.batchQuerySkuBaseInfo(skuList);
//                if (CollectionUtils.isNotEmpty(skuBaseInfoDtos)) {
//                    Map<Integer, SkuBaseInfoDto> skuBaseInfoDtoMap = skuBaseInfoDtos.stream().collect(toMap(o -> o.getSkuId(), o -> o));
//                    for (FurnitureEntity furnitureEntity : furnitureList) {
//                        SkuBaseInfoDto skuBaseInfoDto = skuBaseInfoDtoMap.get(furnitureEntity.getSkuId());
//                        if (skuBaseInfoDto != null) {
//                            furnitureEntity.setHeight(skuBaseInfoDto.getHeight());
//                            furnitureEntity.setWidth(skuBaseInfoDto.getWidth());
//                            furnitureEntity.setLength(skuBaseInfoDto.getLength());
//                        }
//                    }
//                }
//            }
        }
        optionalSoftResponse.setFurnitureList(furnitureList);
        return optionalSoftResponse;
    }

    private Map<String, Object> concurrentQuerySkuListAddBom(List<Integer> spaceIdList, Long solutionId) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);

        //根据空间ID集合查询可替换sku
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() throws Exception {
                return productProgramProxy.batchQuerySoftItemByRoom(spaceIdList);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_OPTIONAL_SKUS_BYROOMIDS.name();
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
                return ConcurrentTaskEnum.QUERY_HARDSELECTION_LIST.name();
            }
        });
        if (solutionId != null) {
            // 查询服务费
            queryTasks.add(new IdentityTaskAction<Object>() {
                @Override
                public Object doInAction() throws Exception {
                    return productProgramProxy.querySolutionService(solutionId);
                }

                @Override
                public String identity() {
                    return ConcurrentTaskEnum.QUERY_SOLUTION_SERVICE.name();
                }
            });
        }

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    private Map<String, Object> concurrentQuerySkuList(List<Integer> spaceIdList) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);

        //根据空间ID集合查询可替换sku
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() throws Exception {
                return productProgramProxy.queryOptionalSkusByRoomIds(spaceIdList);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_OPTIONAL_SKUS_BYROOMIDS.name();
            }
        });

        // 根据空间id集合查询硬装选配项
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() throws Exception {
                return productProgramProxy.queryHardSelectionList(spaceIdList);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_HARDSELECTION_LIST.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 设置方案对比空间数据
     *
     * @param roomList
     * @param width
     * @return Author: ZHAO
     * Date: 2018年6月23日
     */
    private void setContrastSpaceInfo(ContrastInfoResponse contrastInfoResponse, List<CompareSolutionRoom> roomList, Integer width) {
        Integer roomWidth = width * ImageSize.WIDTH_PER_SIZE_62 / ImageSize.WIDTH_PER_SIZE_100;
        String solutionHeadImgURL = "";// 方案首图地址
        List<ContrastSpaceInfo> spaceInfoList = new ArrayList<ContrastSpaceInfo>();
        for (CompareSolutionRoom solutionRoom : roomList) {
            ContrastSpaceInfo contrastSpaceInfo = new ContrastSpaceInfo();
            contrastSpaceInfo.setRoomId(solutionRoom.getRoomId());
            contrastSpaceInfo.setRoomUsageId(solutionRoom.getRoomUsageId());
            if (StringUtils.isNotBlank(solutionRoom.getRoomUsageName())) {
                contrastSpaceInfo.setRoomUsageName(solutionRoom.getRoomUsageName());
            }
            if (CollectionUtils.isNotEmpty(solutionRoom.getSolutionRoomPicVoList())) {
                List<String> roomImgList = new ArrayList<String>();//空间图片集合
                for (SolutionRoomPicVo roomPicVo : solutionRoom.getSolutionRoomPicVoList()) {
                    if (StringUtils.isNotBlank(roomPicVo.getSolutionRoomPicURL())) {
                        String roomHeadImgURL = AliImageUtil.imageCompress(roomPicVo.getSolutionRoomPicURL(), 2, width, ImageConstant.SIZE_SMALL);
                        roomImgList.add(roomHeadImgURL);
                        if (roomPicVo.getIsFirst() != null && roomPicVo.getIsFirst().equals(1)) {
                            //空间头图
                            contrastSpaceInfo.setRoomHeadImgURL(roomHeadImgURL);
                            if (StringUtils.isBlank(solutionHeadImgURL)) {
                                //方案头图
                                solutionHeadImgURL = roomHeadImgURL;

                            }
                        }
                    }
                }
                contrastSpaceInfo.setRoomImgList(roomImgList);
            }
            if (CollectionUtils.isNotEmpty(solutionRoom.getCategoryList())) {
                contrastSpaceInfo.setFurnitureClassifyList(setFurnitureClassifyInfo(solutionRoom.getCategoryList(), width));
            }
            spaceInfoList.add(contrastSpaceInfo);
        }

        if (StringUtils.isNotBlank(solutionHeadImgURL)) {
            contrastInfoResponse.setSolutionHeadImgURL(solutionHeadImgURL);
        }
        contrastInfoResponse.setSpaceList(spaceInfoList);
    }

    /**
     * 家具分类信息
     *
     * @param categoryList
     * @param width        Author: ZHAO
     *                     Date: 2018年6月23日
     */
    private List<FurnitureClassifyInfo> setFurnitureClassifyInfo(List<CompareSolutionRoomItem> categoryList, Integer width) {
        List<FurnitureClassifyInfo> furnitureClassifyList = new ArrayList<FurnitureClassifyInfo>();
        for (CompareSolutionRoomItem compareSolutionRoomItem : categoryList) {
            FurnitureClassifyInfo furnitureClassifyInfo = new FurnitureClassifyInfo();
            furnitureClassifyInfo.setClassTwoName(compareSolutionRoomItem.getSecondCategoryName());
            List<SolutionRoomItemVo> itemList = compareSolutionRoomItem.getItemList();//家具列表
            if (CollectionUtils.isNotEmpty(itemList)) {
                setFurnitureDetailInfo(furnitureClassifyInfo, itemList, width);
            }
            furnitureClassifyList.add(furnitureClassifyInfo);
        }
        return furnitureClassifyList;
    }

    /**
     * 分类家具信息
     *
     * @param furnitureClassifyInfo
     * @param itemList
     * @param width                 Author: ZHAO
     *                              Date: 2018年6月23日
     */
    private void setFurnitureDetailInfo(FurnitureClassifyInfo furnitureClassifyInfo, List<SolutionRoomItemVo> itemList, Integer width) {
        Integer furnitureWidth = width * ImageSize.WIDTH_PER_SIZE_26 / ImageSize.WIDTH_PER_SIZE_100;
        List<FurnitureEntity> furnitureList = new ArrayList<FurnitureEntity>();
        for (SolutionRoomItemVo solutionRoomItemVo : itemList) {
            String itemSize = ProductProgramPraise.FURNITURE_SIZE_TEXT;//家具尺寸
            FurnitureEntity furnitureEntity = new FurnitureEntity();
            if (StringUtils.isNotBlank(solutionRoomItemVo.getItemTopBrand())) {
                //一级品牌（有可能没有）
                furnitureEntity.setBrand(solutionRoomItemVo.getItemTopBrand());
            } else if (StringUtils.isNotBlank(solutionRoomItemVo.getItemBrand())) {
                //二级品牌
                furnitureEntity.setBrand(solutionRoomItemVo.getItemBrand());
            }
            if (StringUtils.isNotBlank(solutionRoomItemVo.getItemColor())) {
                furnitureEntity.setColor(solutionRoomItemVo.getItemColor());
            }
            if (StringUtils.isNotBlank(solutionRoomItemVo.getItemMaterial())) {
                furnitureEntity.setMaterial(solutionRoomItemVo.getItemMaterial());
            }
            furnitureEntity.setSkuId(solutionRoomItemVo.getSkuId());
            if (StringUtils.isNotBlank(solutionRoomItemVo.getItemImage())) {
                furnitureEntity.setImgUrl(QiniuImageUtils.compressImageAndSamePicTwo(solutionRoomItemVo.getItemImage(), furnitureWidth, -1));//图片需切图处理
            }
            if (solutionRoomItemVo.getSkuPrice() != null) {
                furnitureEntity.setPrice(solutionRoomItemVo.getSkuPrice());
            }
            if (solutionRoomItemVo.getParentSkuId() != null) {
                furnitureEntity.setParentSkuId(solutionRoomItemVo.getParentSkuId());
            }
            if (solutionRoomItemVo.getItemCount() != null && solutionRoomItemVo.getItemCount() > 0) {
                furnitureEntity.setItemCount(solutionRoomItemVo.getItemCount());
            }
            //家具类型
            if (solutionRoomItemVo.getFurnitureType() != null && solutionRoomItemVo.getFurnitureType() == ProductProgramPraise.FURNITURE_TYPE_GIFT) {
                //赠品
                if (StringUtils.isNotBlank(solutionRoomItemVo.getItemName())) {
                    furnitureEntity.setFurnitureName(solutionRoomItemVo.getItemName());
                }
                furnitureEntity.setFurnitureType(ProductProgramPraise.FURNITURE_TYPE_GIFT);
                if (StringUtils.isNotBlank(solutionRoomItemVo.getItemSize())) {
                    furnitureEntity.setItemSize(solutionRoomItemVo.getItemSize());
                }
            } else {
                //定制家具
                if (solutionRoomItemVo.getFurnitureType() == ProductProgramPraise.FURNITURE_TYPE_ORDER) {
                    if (StringUtils.isNotBlank(solutionRoomItemVo.getItemName())) {
                        furnitureEntity.setFurnitureName(solutionRoomItemVo.getItemName().replaceAll(ProductProgramPraise.FURNITURE_ORDER_DESC_1, "").replaceAll(ProductProgramPraise.FURNITURE_ORDER_DESC_2, ""));
                    }
                    furnitureEntity.setItemType(ProductProgramPraise.FURNITURE_TYPE_ORDER_DESC);
                    //若尺寸是1*1*1，调整为“依据现场尺寸定制”
                    if (StringUtils.isNotBlank(solutionRoomItemVo.getItemSize())) {
                        if (ProductProgramPraise.FURNITURE_SIZE.equals(solutionRoomItemVo.getItemSize())) {
                            itemSize = ProductProgramPraise.FURNITURE_SIZE_DESC;
                        } else {
                            itemSize = solutionRoomItemVo.getItemSize();
                        }
                    }
                    furnitureEntity.setFurnitureType(ProductProgramPraise.FURNITURE_TYPE_ORDER);
                } else {
                    if (StringUtils.isNotBlank(solutionRoomItemVo.getItemName())) {
                        furnitureEntity.setFurnitureName(solutionRoomItemVo.getItemName());
                    }
                    furnitureEntity.setItemType("");
                    if (StringUtils.isNotBlank(solutionRoomItemVo.getItemSize())) {
                        itemSize = solutionRoomItemVo.getItemSize();
                    }
                }
                furnitureEntity.setItemSize(itemSize);
            }
            furnitureList.add(furnitureEntity);
        }
        furnitureClassifyInfo.setFurnitureList(furnitureList);
    }

    /**
     * 查询所有硬装标准信息
     *
     * @return Author: ZHAO
     * Date: 2018年6月23日
     */
    private Map<String, List<HardStandardSpaceResponseVo>> getHardStandList() {
        Map<String, List<HardStandardSpaceResponseVo>> hardStandMap = new HashMap<String, List<HardStandardSpaceResponseVo>>();
        HardStandardListResponseVo hardStandardListResponseVo = hardStandardWcmProxy.queryHardStandByCondition(null);
        if (hardStandardListResponseVo != null && CollectionUtils.isNotEmpty(hardStandardListResponseVo.getHardStandardList())) {
            for (HardStandardResponseVo standardResponseVo : hardStandardListResponseVo.getHardStandardList()) {
                hardStandMap.put(standardResponseVo.getSeriesName(), standardResponseVo.getSpaceList());
            }
        }

        return hardStandMap;
    }

    private String getHardSeriesName(Map<String, List<HardStandardSpaceResponseVo>> hardStandMap, String seriesName) {
        for (String key : hardStandMap.keySet()) {
            if (key.contains(seriesName)) {
                return key;
            }
        }
        return "";
    }

    /**
     * 根据空间集合批量查询各空间下软装信息
     *
     * @param request
     * @return
     */
    @Override
    public SelectionResponse querySoftItemByRoom(SelectionRequest request) {
        if (CollectionUtils.isEmpty(request.getSpaceIdList())) {
            throw new BusinessException(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        List<OptionalSkusResponseVo> optionalSkusResponseVos = productProgramProxy.batchQuerySoftItemByRoom(Lists.newArrayList(request.getSpaceIdList().get(0)));
        List<BomGroupVO> bomGroupList = Lists.newArrayList();
        List<OptionalSoftResponse> softResponse = getOptionalSoftMore(optionalSkusResponseVos, request.getWidth(), bomGroupList);
        SpaceDesign spaceDesign = new SpaceDesign();
        if (CollectionUtils.isNotEmpty(optionalSkusResponseVos)) {
            OptionalSkusResponseVo optionalSkusResponseVo = optionalSkusResponseVos.get(0);
            spaceDesign.setHeadImage(QiniuImageUtils.compressImageAndSamePicTwo(optionalSkusResponseVo.getRoomImage(), request.getWidth(), -1));
            spaceDesign.setHeadImageOrigin(optionalSkusResponseVo.getRoomImage());
            spaceDesign.setBomGroupList(bomGroupList);
        }
        spaceDesign.setOptionalSoftResponseList(softResponse);
        // 封装返回数据 空间设计信息
        SelectionResponse response = new SelectionResponse();
        response.setSpaceDesignList(Lists.newArrayList(spaceDesign));
        return response;
    }

    /**
     * 根据空间集合批量查询各空间下硬装信息
     *
     * @param request
     * @return
     */
    @Override
    public SelectionResponse queryHardItemByRoom(SelectionRequest request) {
        if (CollectionUtils.isEmpty(request.getSpaceIdList())) {
            throw new BusinessException(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
        }
        List<SpaceDesignVo> spaceDesignVoList = productProgramProxy.batchQueryHardItemByRoom(Lists.newArrayList(request.getSpaceIdList().get(0)));
        if (CollectionUtils.isNotEmpty(spaceDesignVoList)) {
            //初始化 空间效果
            SpaceDesign spaceDesign = new SpaceDesign();
            SpaceDesignVo spaceDesignVo = spaceDesignVoList.get(0);
            BeanUtils.copyProperties(spaceDesignVo, spaceDesign);
            spaceDesign.setHeadImageOrigin(spaceDesign.getHeadImage());
            // 空间设计头图 图片处理
            if (StringUtil.isNotBlank(spaceDesign.getHeadImage())) {
                spaceDesign.setHeadImage(AliImageUtil.imageCompress(spaceDesign.getHeadImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
            }
            // 空间硬装选配项列表 不为空则进行遍历以及转换成给app的响应
            if (spaceDesignVo != null && CollectionUtils.isNotEmpty(spaceDesignVo.getHardItemList())) {
                List<HardItem> hardItemList = new ArrayList<>();

                //空间硬装选配类型 遍历 RoomHardItemClass 转换成 HardItem
                for (RoomHardItemClass hardItemClass : spaceDesignVo.getHardItemList()) {
                    // 硬装选配项目
                    HardItem item = initHardItem(hardItemClass);
                    List<HardItemSelection> selectionList = new ArrayList<>();

                    //空间硬装sku
                    if (!CollectionUtils.isEmpty(hardItemClass.getHardItemClassList())) {
                        for (RoomHardItem roomHardItem : hardItemClass.getHardItemClassList()) {
                            HardItemSelection itemSelection = new HardItemSelection();
                            setItemSelection(roomHardItem, request.getWidth(), itemSelection);
                            if (itemSelection.getDefaultSelection()) {
                                selectionList.add(0, itemSelection);
                            } else {
                                selectionList.add(itemSelection);
                            }
                        }
                    }
                    item.setHardItemSelectionList(selectionList);

                    //全屋空间
                    if (RoomUseEnum.ROOM_WHOLE.getCode() == spaceDesignVo.getSpaceUsageId() &&
                            !CollectionUtils.isEmpty(hardItemClass.getHardPackageList())) {
                        List<RoomHardPackageVo> roomHardPackageVoList = hardItemClass.getHardPackageList();
                        //处理图片
                        for (RoomHardPackageVo roomHardPackageVo : roomHardPackageVoList) {
                            roomHardPackageVo.setPackageSmallUrl(QiniuImageUtils.compressImageAndSamePic(roomHardPackageVo.getPackageUrl(), 100, 100));
                        }
                        item.setHardPackageList(roomHardPackageVoList);
                    }

                    hardItemList.add(item);
                }
                spaceDesign.setHardItemList(hardItemList);
            }
            ArrayList<SpaceDesign> spaceDesigns = Lists.newArrayList(spaceDesign);
            SelectionResponse selectionResponse = new SelectionResponse();
            selectionResponse.setSpaceDesignList(spaceDesigns);
            selectionResponse.setHeadImage(spaceDesignVo.getHeadImage());
            return selectionResponse;
        }
        return null;
    }


    /**
     * 创建艾佳贷订单
     *
     * @param orderId
     * @return
     */
    @Override
    public CreateFamilyOrderRequest createAiJiaLoanOrder(Integer orderId, Integer userId, Integer houseId) {
        String loanSolutionDetailCache = stringRedisTemplate.opsForValue().get(aiJiaLoanSolutionDetailCacheKey);
        SolutionDetailResponseVo programDetail = null;
        if (StringUtils.isNotBlank(loanSolutionDetailCache)) {
            programDetail = JSON.parseObject(loanSolutionDetailCache, SolutionDetailResponseVo.class);
        } else {
            programDetail = productProgramProxy.getProgramDetailById(3232);
        }
        if (programDetail != null && !SolutionStatusEnum.ONLINE.getStatus().equals(programDetail.getSolutionStatus())) {
            programDetail = null;
        }
        if (programDetail != null) {
            CreateFamilyOrderRequest createFamilyOrderRequest = new CreateFamilyOrderRequest();
            //设置基础信息
            createFamilyOrderRequest.setOrderId(orderId);
            createFamilyOrderRequest.setHouseId(houseId);//房产id
            createFamilyOrderRequest.setOpType(1);//操作类型 1-下单 2-查询订单价格,
            createFamilyOrderRequest.setSource(0);//订单来源 0-方案下单 6-代客下单
            createFamilyOrderRequest.setUserId(userId);
            //封装空间id集合
            List<Integer> roomIds = programDetail.getSolutionRoomDetailVoList().parallelStream().map(SolutionRoomDetailVo::getRoomId).collect(Collectors.toList());
            //封装空间图片
            List<RoomEffectImageDto> roomEffectImageDtos = programDetail.getSolutionRoomDetailVoList().parallelStream().map(solutionRoomDetailVo -> new RoomEffectImageDto().setRoomId(solutionRoomDetailVo.getRoomId()).setPictureUrls(Lists.newArrayList(solutionRoomDetailVo.getRoomHeadImgURL()))).collect(Collectors.toList());
            //封装空间硬装替换项
            List<RoomReplaceHardProductDto> roomReplaceHardProductDtos = programDetail.getSolutionRoomDetailVoList().parallelStream().map(solutionRoomDetailVo -> new RoomReplaceHardProductDto().setRoomId(solutionRoomDetailVo.getRoomId())).collect(Collectors.toList());

            //设置空间数据
            createFamilyOrderRequest.setRoomIds(roomIds);
            createFamilyOrderRequest.setReplaceHardProductDtos(roomReplaceHardProductDtos);
            createFamilyOrderRequest.setRoomEffectImageDtos(roomEffectImageDtos);
            createFamilyOrderRequest.setSolutionIds(Lists.newArrayList(programDetail.getSolutionId().longValue()));
            return createFamilyOrderRequest;
        }
        return null;
    }

    /**
     * 查询软装替换项信息
     *
     * @param request
     * @return
     */
    @Override
    public OptionalSoftResponse querySoftSkuListV529(MoreInfoRequest request) {
        OptionalSkusResponseVo optionalSkusResponseVo = productProgramProxy.queryReplaceSoftItemByRoom(request);
        if (optionalSkusResponseVo == null) {
            return null;
        }
        return handlerSolfItemResponseHasMore(optionalSkusResponseVo, request.getWidth(), request.getItemType());
    }

    /**
     * 查询硬装可替换列表
     *
     * @param request
     * @return
     */
    @Override
    public List<HardItemSelection> queryHardSkuListV529(HardMoreRequest request) {
        List<HardItemSelection> hardItemSelectionList = Lists.newArrayList();
        SpaceDesignVo spaceDesignVo = productProgramProxy.queryReplaceHardItemByRoom(request);
        if (spaceDesignVo != null && CollectionUtils.isNotEmpty(spaceDesignVo.getHardItemList())) {
            HardItemSelection result = new HardItemSelection();
            result.setHardProcessList(Lists.newArrayList());
            //空间硬装选配类型 遍历 RoomHardItemClass 转换成 HardItem
            for (RoomHardItemClass hardItemClass : spaceDesignVo.getHardItemList()) {
                //空间硬装sku
                if (!CollectionUtils.isEmpty(hardItemClass.getHardItemClassList())) {
                    for (RoomHardItem roomHardItem : hardItemClass.getHardItemClassList()) {
                        HardItemSelection itemSelection = new HardItemSelection();
                        setItemSelection(roomHardItem, request.getWidth(), itemSelection);
                        if (itemSelection.getDefaultSelection()) {
                            hardItemSelectionList.add(0, itemSelection);
                        } else {
                            hardItemSelectionList.add(itemSelection);
                        }
                    }
                } else if (hardItemClass.getReplaceBomGroupList() != null && CollectionUtils.isNotEmpty(hardItemClass.getReplaceBomGroupList().getList())) {
                    for (HardBomGroup hardBomGroup : hardItemClass.getReplaceBomGroupList().getList()) {
                        HardItemSelection itemSelection = new HardItemSelection();
                        itemSelection.setBomGroup(hardBomGroup);
                        itemSelection.setSuperKey(hardBomGroup.getSuperKey());
                        itemSelection.getBomGroup().setGroupImage(AliImageUtil.imageCompress(hardBomGroup.getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
                        hardItemSelectionList.add(itemSelection);
                    }
                }
            }
        }
        return hardItemSelectionList;
    }


    /**
     * 根据订单号查询房产信息
     *
     * @param request
     * @return
     */
    @Override
    public BuildingHouseInfoResponse queryBuildingHouseInfoByOrderNum(ProgramOrderDetailRequest request) {
        AppOrderBaseInfoResponseVo appOrderBaseInfoResponseVo = productProgramOrderProxy.queryAppOrderBaseInfo(request.getOrderId());
        if (appOrderBaseInfoResponseVo != null) {
            return homeBuildingService.queryHouseInfoById(appOrderBaseInfoResponseVo.getCustomerHouseId());
        }
        return null;
    }

    /**
     * 阅读草稿
     *
     * @param draftId
     */
    @Override
    public void readDraft(String draftId) {
        Integer num = homeCardWcmProxy.readDraft(draftId);
    }

    /**
     * 查询方案详情引导页
     *
     * @param request
     * @return
     */
    @Override
    public ProgramDetailsGuideResponse queryProgramDetailsGuide(HttpProgramDetailRequest request) {
        SolutionDetailResponseVo solutionDetailResponseVo = productProgramProxy.getProgramDetailById(request.getProgramId());

        if (solutionDetailResponseVo != null && !SolutionStatusEnum.ONLINE.getStatus().equals(solutionDetailResponseVo.getSolutionStatus())) {
            solutionDetailResponseVo = null;
        }

        if (solutionDetailResponseVo != null) {

            //组装封面数据
            ProgramDetailsGuideResponse.PageData cover = ProgramDetailsGuideResponse.PageData.builder()
                    .image(StringUtils.isNotBlank(solutionDetailResponseVo.getSolutionHeadImgURL()) ? Lists.newArrayList(AliImageUtil.imageCompress(solutionDetailResponseVo.getSolutionHeadImgURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE)) : null)
                    .desc(Lists.newArrayList(ProgramDetailsGuideResponse.CopyWriterPackDto.builder()
                            .title(solutionDetailResponseVo.getSolutionName())
                            .description(programText.getCoverWelcomeText()).build())).build();

//            //组装人物定位

            ProgramDetailsGuideResponse.PageData personLocation = ProgramDetailsGuideResponse.PageData.builder().build();
            personLocation.setDesc(Lists.newArrayList(ProgramDetailsGuideResponse.CopyWriterPackDto.builder()
                    .title(programText.getStyleLocation())
                    .description(solutionDetailResponseVo.getSolutionStyleName())
                    .type(2).build())).builder();
            Integer userSelectDnaId = this.handlerPersonLocationAndBackDnaId(request.getUserInfo() == null ? null : request.getUserInfo().getId(), request.getOrderId(), personLocation, request.getOsType(), request.getWidth());
            personLocation.getDesc().add(ProgramDetailsGuideResponse.CopyWriterPackDto.builder()
                    .title(programText.getHouseStatus())
                    .description(solutionDetailResponseVo.getDecorationType().equals(0) ? "毛坯" : "精装修")
                    .type(3).build());
            //组装方案概述
            ProgramDetailsGuideResponse.PageData solutionIdea = ProgramDetailsGuideResponse.PageData.builder()
                    .image(Lists.newArrayList())
                    .build();
            Integer dnaId = null;
            for (SolutionRoomDetailVo solutionRoomDetailVo : solutionDetailResponseVo.getSolutionRoomDetailVoList()) {
                if (solutionRoomDetailVo.getRoomUsageName().equals("客厅")) {
                    dnaId = solutionRoomDetailVo.getDnaId();
                    break;
                }
            }
            if (dnaId == null) {
                dnaId = userSelectDnaId;
            }
            if (dnaId != null) {
                DNAInfoResponseVo dnaDetail = homeCardBossProxy.getDnaDetailById(dnaId);
                if (dnaDetail != null && CollectionUtils.isNotEmpty(dnaDetail.getProspectPictureList())) {
                    solutionIdea.setSimpleDesc(dnaDetail.getDesignIdea());
                    solutionIdea.getImage().addAll(dnaDetail.getProspectPictureList().stream()
                            .map(dnaProspectPictureVo -> AliImageUtil.imageCompress(dnaProspectPictureVo.getPictureURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE)).collect(Collectors.toList()));
                }
            } else {
                solutionIdea.setSimpleDesc(programText.getSolutionIdeaDefaultDesc());
                solutionIdea.getImage().addAll(Lists.newArrayList(programText.getSolutionIdeaDefaultImages().split(",")).stream()
                        .map(url -> AliImageUtil.imageCompress(url, request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE)).collect(Collectors.toList()));
            }

//            //组装设计说明
            ProgramDetailsGuideResponse.PageData descOfDesign = ProgramDetailsGuideResponse.PageData.builder().build();

            //材质分析
            ProgramDetailsGuideResponse.PageData textureAnalyze = ProgramDetailsGuideResponse.PageData.builder().build();
            this.handlerDescOfDesignAndTextureAnalyze(solutionDetailResponseVo.getSolutionStyleName(), descOfDesign, textureAnalyze);
            this.handlerImageForDesc(textureAnalyze.getDesc(), request.getOsType(), request.getWidth());
            this.handlerImageForDesc(descOfDesign.getDesc(), request.getOsType(), request.getWidth());
//            //平面布局
            String houseTypeInfoTitle = this.joinHouseTypeName(solutionDetailResponseVo);
            List<String> roomNameList = solutionDetailResponseVo.getSolutionRoomDetailVoList().stream().map(SolutionRoomDetailVo::getRoomUsageName).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(roomNameList)) {
                roomNameList.removeIf(roomName -> roomName.equals("全屋"));
            }
            ProgramDetailsGuideResponse.PageData planeLayout = ProgramDetailsGuideResponse.PageData.builder()
                    .image(Lists.newArrayList(AliImageUtil.imageCompress(solutionDetailResponseVo.getSolutionGraphicDesignUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE)))
                    .desc(Lists.newArrayList(ProgramDetailsGuideResponse.CopyWriterPackDto.builder().title(programText.getHouseTypeInfoTitle()).description(houseTypeInfoTitle).build(),
                            ProgramDetailsGuideResponse.CopyWriterPackDto.builder().title(programText.getProjectSizeTitle()).description(solutionDetailResponseVo.getHouseLayout().getArea() == null ? "--" : solutionDetailResponseVo.getHouseLayout().getArea().toString() + "m²").build(),
                            ProgramDetailsGuideResponse.CopyWriterPackDto.builder().title(programText.getSpatialPatternTitle()).description(roomNameList.stream().collect(Collectors.joining("、"))).build()
                    ))
                    .build();

//            //方案效果图
            ProgramDetailsGuideResponse.PageData solutionImages = ProgramDetailsGuideResponse.PageData.builder().build();
            solutionImages.setImage(Lists.newArrayList());
            for (SolutionRoomDetailVo solutionRoomDetailVo : solutionDetailResponseVo.getSolutionRoomDetailVoList()) {
                if (CollectionUtils.isNotEmpty(solutionRoomDetailVo.getSolutionRoomPicVoList())) {
                    for (SolutionRoomPicVo solutionRoomPicVo : solutionRoomDetailVo.getSolutionRoomPicVoList()) {
                        solutionImages.getImage().add(AliImageUtil.imageCompress(solutionRoomPicVo.getSolutionRoomPicURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                    }
                }
            }


            //品牌信息
            Set<String> softBrandList = Sets.newHashSet();
            Set<String> hardBrandList = Sets.newHashSet();
            for (SolutionRoomDetailVo solutionRoomDetailVo : solutionDetailResponseVo.getSolutionRoomDetailVoList()) {
                softBrandList.addAll(solutionRoomDetailVo.getSolutionRoomItemVoList().stream().map(solutionRoomItemVo ->
                        solutionRoomItemVo.getLastCategoryId().equals(676) ? "" : solutionRoomItemVo.getItemBrand()).collect(Collectors.toSet()));
                hardBrandList.addAll(solutionRoomDetailVo.getDefaultHardItemList().stream().map(roomDefaultHardItemClass -> roomDefaultHardItemClass.getBrandName()).collect(Collectors.toSet()));
            }
            softBrandList.removeIf(s -> StringUtils.isBlank(s) || s.contains("定制"));
            hardBrandList.removeIf(s -> StringUtils.isBlank(s) || s.contains("定制"));
            List<String> topBrandList = Lists.newArrayList(programText.getTopBrandName().split(","));
            Set<String> brandNameForHard = Sets.newHashSet();
            for (String brandName : hardBrandList) {
                if (topBrandList.contains(brandName)) {
                    brandNameForHard.add(brandName);
                }
            }
            if (brandNameForHard.size() < 6) {
                for (String brandName : hardBrandList) {
                    if (brandNameForHard.size() >= 6) {
                        break;
                    }
                    brandNameForHard.add(brandName);
                }
            }
            Set<String> sortBrandListResult = Sets.newHashSet();
            if (softBrandList.size() > 6) {
                for (String brandName : softBrandList) {
                    if (sortBrandListResult.size() >= 6) {
                        break;
                    }
                    sortBrandListResult.add(brandName);
                }
            } else {
                sortBrandListResult.addAll(softBrandList);
            }
            ProgramDetailsGuideResponse.BrandDto solutionBrandInfo = ProgramDetailsGuideResponse.BrandDto.builder().hardBrandList(brandNameForHard).softBrandList(sortBrandListResult).backgroundImage(AliImageUtil.imageCompress(programText.getProgramBrandBackgroundImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE)).build();
            return ProgramDetailsGuideResponse.builder().cover(cover).descOfDesign(descOfDesign).personLocation(personLocation).planeLayout(planeLayout).solutionBrandInfo(solutionBrandInfo).solutionIdea(solutionIdea).solutionImages(solutionImages).textureAnalyze(textureAnalyze).houseProjectName(solutionDetailResponseVo.getHouseProjectName()).houseTypeName(this.joinHouseTypeName(solutionDetailResponseVo)).build();
        }
        return null;
    }

    void handlerImageForDesc(List<ProgramDetailsGuideResponse.CopyWriterPackDto> packDto, Integer osType, Integer width) {
        if (CollectionUtils.isNotEmpty(packDto)) {
            for (ProgramDetailsGuideResponse.CopyWriterPackDto copyWriterPackDto : packDto) {
                if (CollectionUtils.isNotEmpty(copyWriterPackDto.getImages())) {
                    copyWriterPackDto.setImages(copyWriterPackDto.getImages().stream().map(image -> AliImageUtil.imageCompress(image, osType, width, ImageConstant.SIZE_MIDDLE)).collect(Collectors.toList()));
                }
            }
        }
    }

    private String joinHouseTypeName(SolutionDetailResponseVo solutionDetailResponseVo) {
        String houseTypeName = "";
        if (solutionDetailResponseVo != null) {
            if (solutionDetailResponseVo.getReformFlag() != null && solutionDetailResponseVo.getReformFlag().equals(1)) {
                houseTypeName = solutionDetailResponseVo.getApartmentPattern();
            }
            if (StringUtils.isBlank(houseTypeName)) {
                HouseLayoutVo houseLayout = solutionDetailResponseVo.getHouseLayout();
                StringBuilder temp = new StringBuilder();
                if (houseLayout.getChamber() != null && houseLayout.getChamber() != 0) {
                    temp.append(houseLayout.getChamber()).append("室");
                }
                if (houseLayout.getHall() != null && houseLayout.getHall() != 0) {
                    temp.append(houseLayout.getHall()).append("厅");
                }
                if (houseLayout.getKitchen() != null && houseLayout.getKitchen() != 0) {
                    temp.append(houseLayout.getKitchen()).append("厨");
                }
                if (houseLayout.getToilet() != null && houseLayout.getToilet() != 0) {
                    temp.append(houseLayout.getToilet()).append("卫");
                }
                houseTypeName = temp.toString();
            }
        }
        return houseTypeName;
    }

    /**
     * 根据不同的风格设置不同的资源
     *
     * @param descOfDesign   设计说明
     * @param textureAnalyze 材质分析
     */
    private void handlerDescOfDesignAndTextureAnalyze(String styleName, ProgramDetailsGuideResponse.PageData descOfDesign, ProgramDetailsGuideResponse.PageData textureAnalyze) {
        switch (styleName) {
            case "美式":
                descOfDesign.setDesc(Lists.newArrayList(ProgramDetailsGuideResponse.CopyWriterPackDto.builder()
                        .title(programText.getDesignDescTitleForAmerican())
                        .description(programText.getDesignDescTextForAmerican())
                        .images(Lists.newArrayList(programText.getDesignDescImageForAmerican())).build()));
                textureAnalyze.setDesc(JSON.parseArray(programText.getTextureAnalyzeForAmerican(), ProgramDetailsGuideResponse.CopyWriterPackDto.class));
                break;
            case "现代":
                descOfDesign.setDesc(Lists.newArrayList(ProgramDetailsGuideResponse.CopyWriterPackDto.builder()
                        .title(programText.getDesignDescTitleForModern())
                        .description(programText.getDesignDescTextForModern())
                        .images(Lists.newArrayList(programText.getDesignDescImageForModern())).build()));
                textureAnalyze.setDesc(JSON.parseArray(programText.getTextureAnalyzeForModern(), ProgramDetailsGuideResponse.CopyWriterPackDto.class));
                break;
            case "中式":
                descOfDesign.setDesc(Lists.newArrayList(ProgramDetailsGuideResponse.CopyWriterPackDto.builder()
                        .title(programText.getDesignDescTitleForChinese())
                        .description(programText.getDesignDescTextForChinese())
                        .images(Lists.newArrayList(programText.getDesignDescImageForChinese())).build()));
                textureAnalyze.setDesc(JSON.parseArray(programText.getTextureAnalyzeForChinese(), ProgramDetailsGuideResponse.CopyWriterPackDto.class));
                break;
            case "欧式":
                descOfDesign.setDesc(Lists.newArrayList(ProgramDetailsGuideResponse.CopyWriterPackDto.builder()
                        .title(programText.getDesignDescTitleForEuropean())
                        .description(programText.getDesignDescTextForEuropean())
                        .images(Lists.newArrayList(programText.getDesignDescImageForEuropean())).build()));
                textureAnalyze.setDesc(JSON.parseArray(programText.getTextureAnalyzeForEuropean(), ProgramDetailsGuideResponse.CopyWriterPackDto.class));
                break;
        }
    }

    private Integer handlerPersonLocationAndBackDnaId(Integer userId, Integer orderNum, ProgramDetailsGuideResponse.PageData personLocation, Integer osType, Integer width) {
        StyleQuestionSelectedNewResponse styleQuestionSelectedNewResponse = null;
        if (userId != null && orderNum != null) {
            styleQuestionSelectedNewResponse = styleQuestionAnwserProxy.queryQuestionAnwserDetailLatest(orderNum, userId);
        }
        Integer dnaId = null;
        if (styleQuestionSelectedNewResponse != null) {
            ProgramDetailsGuideResponse.CopyWriterPackDto familyMember;
            if (CollectionUtils.isNotEmpty(styleQuestionSelectedNewResponse.getStyleQuestionSelectedResponseList())) {
                for (StyleQuestionSelectedResponse styleQuestionSelectedResponse : styleQuestionSelectedNewResponse.getStyleQuestionSelectedResponseList()) {
                    if (styleQuestionSelectedResponse.getCode().equals("101") || styleQuestionSelectedResponse.getCode().equals("115")) {
                        familyMember = ProgramDetailsGuideResponse.CopyWriterPackDto.builder().build();
                        List<StyleAnwserSelectedResponse> anwserList = styleQuestionSelectedResponse.getAnwserList();
                        List<Integer> anwserIdList = anwserList.stream().map(mapper -> mapper.getAnwserId()).collect(Collectors.toList());
//                        1	42	还没有小孩
//                        2	43	学龄前儿童
//                        3	44	青少年期
//                        4	45	和长辈同住

//                        ((1+4)|4)||(42+45)|45 ->> 三代同堂
//                        1||42   ->> 年轻夫妻
//                        ((2|3)+4)||((43|44)+45) ->> 三代同堂
//                        (2|3)||(43||44)  ->> 三口之家
                        if (anwserIdList.size() == 2 && (anwserIdList.contains(4) || anwserIdList.contains(45)) && ((anwserIdList.contains(2) || anwserIdList.contains(1) || anwserIdList.contains(3)) || (anwserIdList.contains(43) || anwserIdList.contains(42) || anwserIdList.contains(44)))) {
                            //三代同堂
                            personLocation.setImage(Lists.newArrayList(AliImageUtil.imageCompress(programText.getPersonLocationImageForA(), osType, width, ImageConstant.SIZE_MIDDLE)));
                            if (anwserIdList.contains(1) || anwserIdList.contains(42)) {
                                familyMember.setTitle(programText.getFamilyMember()).setDescription("老人\n夫妻").setType(1);
                            } else {
                                familyMember.setTitle(programText.getFamilyMember()).setDescription("老人\n夫妻\n孩子").setType(1);
                            }
                        } else if (anwserIdList.size() == 1 && (anwserIdList.contains(1) || anwserIdList.contains(42))) {
                            //年轻夫妻
                            personLocation.setImage(Lists.newArrayList(AliImageUtil.imageCompress(programText.getPersonLocationImageForC(), osType, width, ImageConstant.SIZE_MIDDLE)));
                            familyMember.setTitle(programText.getFamilyMember()).setDescription("夫妻").setType(1);
                        } else if (anwserIdList.size() == 1 && ((anwserIdList.contains(2) || anwserIdList.contains(3))) || (anwserIdList.contains(43) || anwserIdList.contains(44))) {
                            //三口之家
                            personLocation.setImage(Lists.newArrayList(AliImageUtil.imageCompress(programText.getPersonLocationImageForB(), osType, width, ImageConstant.SIZE_MIDDLE)));
                            familyMember.setTitle(programText.getFamilyMember()).setDescription("夫妻\n孩子").setType(1);
                        } else if (anwserIdList.size() == 1 && (anwserIdList.contains(4) || anwserIdList.contains(45))) {
                            personLocation.setImage(Lists.newArrayList(AliImageUtil.imageCompress(programText.getPersonLocationImageForA(), osType, width, ImageConstant.SIZE_MIDDLE)));
                            familyMember.setTitle(programText.getFamilyMember()).setDescription("老人\n夫妻").setType(1);
                        } else {
                            personLocation.setImage(Lists.newArrayList(AliImageUtil.imageCompress(programText.getPersonLocationImageForC(), osType, width, ImageConstant.SIZE_MIDDLE)));
                            personLocation.getDesc().add(0, new ProgramDetailsGuideResponse.CopyWriterPackDto().setTitle(programText.getFamilyMember()).setDescription("夫妻").setType(1));
                        }
                        personLocation.getDesc().add(0, familyMember);
                    }
                    if (styleQuestionSelectedResponse.getId().equals(4)) {
                        dnaId = styleQuestionSelectedResponse.getAnwserList().get(0).getAnwserId();
                    }
                }
            }
        } else {
            personLocation.getDesc().add(0, new ProgramDetailsGuideResponse.CopyWriterPackDto().setTitle(programText.getFamilyMember()).setDescription("夫妻").setType(1));
            personLocation.setImage(Lists.newArrayList(AliImageUtil.imageCompress(programText.getPersonLocationImageForC(), osType, width, ImageConstant.SIZE_MIDDLE)));
        }
        return dnaId;
    }

    /**
     * 查询方案商品清单
     *
     * @param request
     * @return
     */
    @Override
    public ProgramCommodityListResponse queryProgramCommodityList(HttpProgramDetailRequest request) {
        SolutionDetailResponseVo solutionDetailResponseVo = productProgramProxy.getProgramDetailById(request.getProgramId());
        if (solutionDetailResponseVo != null && !SolutionStatusEnum.ONLINE.getStatus().equals(solutionDetailResponseVo.getSolutionStatus())) {
            solutionDetailResponseVo = null;
        }

        if (solutionDetailResponseVo != null) {
            ProgramCommodityListResponse programCommodityListResponse = new ProgramCommodityListResponse();
            List<SolutionRoomDetailVo> solutionRoomDetailVoList = solutionDetailResponseVo.getSolutionRoomDetailVoList();
            if (CollectionUtils.isNotEmpty(solutionRoomDetailVoList)) {
                for (SolutionRoomDetailVo solutionRoomDetailVo : solutionRoomDetailVoList) {
                    if (CollectionUtils.isNotEmpty(request.getRoomIdList()) && !request.getRoomIdList().contains(solutionRoomDetailVo.getRoomId())) {
                        continue;
                    }
                    ProgramCommodityListResponse.RoomInfo hardRoomInfo = new ProgramCommodityListResponse.RoomInfo();
                    ProgramCommodityListResponse.RoomInfo softRoomInfo = new ProgramCommodityListResponse.RoomInfo();
                    hardRoomInfo.setRoomId(solutionRoomDetailVo.getRoomId());
                    if (CollectionUtils.isNotEmpty(solutionRoomDetailVo.getSolutionRoomPicVoList())) {
                        List<String> imageList = solutionRoomDetailVo.getSolutionRoomPicVoList().stream().map(solutionRoomPicVo -> AliImageUtil.imageCompress(solutionRoomPicVo.getSolutionRoomPicURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE)).collect(Collectors.toList());
                        softRoomInfo.setRoomPicList(imageList);
                        hardRoomInfo.setRoomPicList(imageList);
                    }
                    hardRoomInfo.setRoomName(solutionRoomDetailVo.getRoomUsageName());
                    softRoomInfo.setRoomId(solutionRoomDetailVo.getRoomId());
                    softRoomInfo.setRoomName(solutionRoomDetailVo.getRoomUsageName());
                    List<RoomDefaultHardItemClass> defaultHardItemList = solutionRoomDetailVo.getDefaultHardItemList();
                    //硬装普通sku
                    for (RoomDefaultHardItemClass roomDefaultHardItemClass : defaultHardItemList) {

                        ProgramCommodityListResponse.RoomInfo.Item hardItem = new ProgramCommodityListResponse.RoomInfo.Item();
                        hardItem.setCategoryId(roomDefaultHardItemClass.getHardItemClassId())
                                .setCategoryName(roomDefaultHardItemClass.getHardItemClassName())
                                .setItemType(1)
                                .setItemCount(1)
                                .setImage(AliImageUtil.imageCompress(roomDefaultHardItemClass.getHardItemHeadImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL))
                                .setSkuId(roomDefaultHardItemClass.getHardItemId())
                                .setSkuName(roomDefaultHardItemClass.getHardItemName());
                        hardRoomInfo.getItemList().add(hardItem);
                    }
                    //硬装bom
                    List<BomGroupVO> hardBomGroupList = solutionRoomDetailVo.getHardBomGroupList();
                    if (CollectionUtils.isNotEmpty(hardBomGroupList)) {
                        Map<Integer, List<BomGroupVO>> guiBomMapBySecondCategoryId = hardBomGroupList.stream().filter(bomGroupVO -> bomGroupVO.getGroupType() != null && bomGroupVO.getGroupType().equals(9)).collect(groupingBy(BomGroupVO::getSecondCategoryId));
                        hardBomGroupList.removeIf(bomGroupVO -> bomGroupVO.getGroupType() != null && bomGroupVO.getGroupType().equals(9));
                        for (BomGroupVO bomGroupVO : hardBomGroupList) {
                            ProgramCommodityListResponse.RoomInfo.Item hardItem = new ProgramCommodityListResponse.RoomInfo.Item();
                            hardItem.setSkuName(bomGroupVO.getGroupName())
                                    .setSkuId(bomGroupVO.getGroupId())
                                    .setImage(AliImageUtil.imageCompress(bomGroupVO.getGroupImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL))
                                    .setItemType(2)
                                    .setItemCount(1)
                                    .setCategoryName(bomGroupVO.getCategoryName())
                                    .setCategoryId(bomGroupVO.getCategoryId());
                            hardRoomInfo.getItemList().add(hardItem);
                        }
                        //硬装定制柜
                        if (MapUtils.isNotEmpty(guiBomMapBySecondCategoryId)) {
                            guiBomMapBySecondCategoryId.forEach((key, value) -> {
                                ProgramCommodityListResponse.RoomInfo.Item hardItem = new ProgramCommodityListResponse.RoomInfo.Item();
                                BomGroupVO bomGroupVO = value.get(0);
                                hardItem.setSkuName(bomGroupVO.getSecondCategoryName())
                                        .setImage(AliImageUtil.imageCompress(bomGroupVO.getGroupImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL))
                                        .setItemType(4)
                                        .setItemCount(1)
                                        .setCabinetBomGroupInfoList(value.stream().map(mapper -> {
                                            QueryCabinetPropertyListRequest.GroupQueryRequest groupQueryRequest = new QueryCabinetPropertyListRequest.GroupQueryRequest();
                                            groupQueryRequest.setCabinetType(mapper.getCabinetType());
                                            groupQueryRequest.setCabinetTypeName(mapper.getCabinetTypeName());
                                            groupQueryRequest.setGroupId(mapper.getGroupId());
                                            return groupQueryRequest;
                                        }).collect(Collectors.toList()))
                                        .setCategoryName("定制家具")
                                        .setCategoryId(bomGroupVO.getSecondCategoryId());
                                hardRoomInfo.getItemList().add(hardItem);
                            });
                        }
                    }

                    //软装普通sku
                    if (CollectionUtils.isNotEmpty(solutionRoomDetailVo.getSolutionRoomItemVoList())) {
                        for (SolutionRoomItemVo solutionRoomItemVo : solutionRoomDetailVo.getSolutionRoomItemVoList()) {
                            ProgramCommodityListResponse.RoomInfo.Item softItem = new ProgramCommodityListResponse.RoomInfo.Item();
                            softItem.setCategoryId(solutionRoomItemVo.getLastCategoryId())
                                    .setCategoryName(solutionRoomItemVo.getLastCategoryName())
                                    .setItemCount(solutionRoomItemVo.getItemCount())
                                    .setItemType(solutionRoomItemVo.getFurnitureType().equals(3) ? 3 : 1).setImage(AliImageUtil.imageCompress(solutionRoomItemVo.getItemImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL))
                                    .setSkuId(solutionRoomItemVo.getSkuId());
                            StringBuilder sb = new StringBuilder();
                            if (StringUtils.isNotBlank(solutionRoomItemVo.getItemBrand()) && !solutionRoomItemVo.getLastCategoryId().equals(676)) {
                                sb.append(solutionRoomItemVo.getItemBrand());
                            }
                            if (StringUtils.isNotBlank(solutionRoomItemVo.getItemName())) {
                                if (sb.toString().length() > 0) {
                                    sb.append(" ");
                                }
                                sb.append(solutionRoomItemVo.getItemName());
                            }
                            softItem.setSkuName(sb.toString());
                            if (softItem.getItemType().equals(3)) {
                                softItem.setCategoryName("定制家具");
                                softItem.setSkuName(solutionRoomItemVo.getItemName());
                            }
                            if (solutionRoomItemVo.getFurnitureType() != null && solutionRoomItemVo.getFurnitureType() == 2
                                    && ProductCategoryConstant.CATEGORY_THREE_ID_D_LIST.contains(solutionRoomItemVo.getLastCategoryId())) {
                                softItem.setShowFreeFlag(1);
                            }
                            if (solutionRoomItemVo.getFreeFlag() != null && solutionRoomItemVo.getFreeFlag() == 1) {
                                softItem.setShowFreeFlag(2);
                            }

                            softRoomInfo.getItemList().add(softItem);
                        }
                    }

                    //软装bom
                    if (CollectionUtils.isNotEmpty(solutionRoomDetailVo.getBomGroupList())) {
                        Map<Integer, List<BomGroupVO>> guiBomBySecondCategoryId = solutionRoomDetailVo.getBomGroupList().stream().filter(bomGroupVO -> bomGroupVO.getGroupType() != null && bomGroupVO.getGroupType().equals(10)).collect(groupingBy(BomGroupVO::getSecondCategoryId));
                        solutionRoomDetailVo.getBomGroupList().removeIf(bomGroupVO -> bomGroupVO.getGroupType() != null && bomGroupVO.getGroupType().equals(10));
                        for (BomGroupVO bomGroupVO : solutionRoomDetailVo.getBomGroupList()) {
                            ProgramCommodityListResponse.RoomInfo.Item softItem = new ProgramCommodityListResponse.RoomInfo.Item();
                            softItem.setSkuName(bomGroupVO.getGroupDesc())
                                    .setCategoryId(bomGroupVO.getCategoryId())
                                    .setSkuId(bomGroupVO.getGroupId())
                                    .setItemCount(bomGroupVO.getItemCount())
                                    .setCategoryName(bomGroupVO.getCategoryName())
                                    .setImage(AliImageUtil.imageCompress(bomGroupVO.getGroupImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL))
                                    .setItemType(2);
                            if (bomGroupVO.getFurnitureType() != null && bomGroupVO.getFurnitureType() == 2) {
                                softItem.setShowFreeFlag(1);
                            }
                            if (bomGroupVO.getFreeFlag() != null && bomGroupVO.getFreeFlag() == 1) {
                                softItem.setShowFreeFlag(2);
                            }
                            softRoomInfo.getItemList().add(softItem);
                        }
                        //软装定制柜
                        if (MapUtils.isNotEmpty(guiBomBySecondCategoryId)) {
                            guiBomBySecondCategoryId.forEach((key, value) -> {
                                ProgramCommodityListResponse.RoomInfo.Item softItem = new ProgramCommodityListResponse.RoomInfo.Item();
                                BomGroupVO bomGroupVO = value.get(0);
                                softItem.setSkuName(bomGroupVO.getSecondCategoryName())
                                        .setCategoryName("定制家具")
                                        .setImage(AliImageUtil.imageCompress(bomGroupVO.getGroupImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL))
                                        .setItemType(4)
                                        .setCabinetBomGroupInfoList(value.stream().map(mapper -> {
                                            QueryCabinetPropertyListRequest.GroupQueryRequest groupQueryRequest = new QueryCabinetPropertyListRequest.GroupQueryRequest();
                                            groupQueryRequest.setCabinetTypeName(mapper.getCabinetTypeName());
                                            groupQueryRequest.setCabinetType(mapper.getCabinetType());
                                            groupQueryRequest.setGroupId(mapper.getGroupId());
                                            return groupQueryRequest;
                                        }).collect(Collectors.toList()))
                                        .setItemCount(1)
                                        .setCategoryId(bomGroupVO.getSecondCategoryId());
                                softRoomInfo.getItemList().add(softItem);
                            });
                        }
                    }
                    if (CollectionUtils.isNotEmpty(hardRoomInfo.getItemList())) {
                        programCommodityListResponse.getHardList().add(hardRoomInfo);
                    }
                    if (CollectionUtils.isNotEmpty(softRoomInfo.getItemList())) {
                        programCommodityListResponse.getSoftList().add(softRoomInfo);
                    }
                }
            }
            return programCommodityListResponse;
        }
        return null;
    }

    private Map<String, Object> queryProgramDetailNewNeedData(Integer orderId, Integer programId) {
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(4);
        //查默认组合替换信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return productProgramProxy.getProgramDetailById(programId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_PROGRAM_DETAIL_BY_ID.name();
            }
        });
        if (orderId != null) {
            //查已选组合替换信息
            queryTasks.add(new IdentityTaskAction<Object>() {
                @Override
                public Object doInAction() {
                    return orderId == null ? null : productProgramOrderProxy.queryAppOrderBaseInfo(orderId);
                }

                @Override
                public String identity() {
                    return ConcurrentTaskEnum.QUERY_APP_ORDER_BASE_INFO.name();
                }
            });
            //查已选组合替换信息
            queryTasks.add(new IdentityTaskAction<Object>() {
                @Override
                public Object doInAction() {
                    return orderId == null ? null : productProgramOrderProxy.querySolutionInfo(orderId);
                }

                @Override
                public String identity() {
                    return ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name();
                }
            });
        }
        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    @Override
    public ProgramDetailNewResponse queryProgramDetailNew(HttpProgramDetailRequest request) {
        if (request.getProgramId() != null) {
            Map<String, Object> detailNewNeedData = this.queryProgramDetailNewNeedData(request.getOrderId(), request.getProgramId());
            SolutionDetailResponseVo solutionDetailResponseVo = (SolutionDetailResponseVo) detailNewNeedData.get(ConcurrentTaskEnum.QUERY_PROGRAM_DETAIL_BY_ID.name());
            if (solutionDetailResponseVo != null) {
                ProgramDetailNewResponse response = new ProgramDetailNewResponse();
                if (request.getOrderId() != null) {
                    AppOrderBaseInfoResponseVo appOrderBaseInfoResponseVo = (AppOrderBaseInfoResponseVo) detailNewNeedData.get(ConcurrentTaskEnum.QUERY_APP_ORDER_BASE_INFO.name());
                    SolutionInfo solutionInfo = (SolutionInfo) detailNewNeedData.get(ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name());
                    if (appOrderBaseInfoResponseVo != null) {
                        response.setOrderStatus(appOrderBaseInfoResponseVo.getOrderStatus());
                        response.setOrderSubStatus(appOrderBaseInfoResponseVo.getOrderSubstatus());
                        if (solutionInfo != null) {
                            response.setIsLoan(solutionInfo.getContainLoan() == 1);
                        }
                    }
                }
                response.setSolutionVideo(solutionDetailResponseVo.getVideoUrl());
                response.setSolutionGlobalViewURL(solutionDetailResponseVo.getSolutionGlobalViewURL());
                response.setSolutionGraphicDesignUrl(AliImageUtil.imageCompress(solutionDetailResponseVo.getSolutionGraphicDesignUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                response.setLayoutDesc(this.joinLayoutDesc(solutionDetailResponseVo));
                response.setHouseTypeName(this.joinHouseTypeName(solutionDetailResponseVo));
                response.setSize(solutionDetailResponseVo.getHouseLayout().getArea() == null ? "--" : solutionDetailResponseVo.getHouseLayout().getArea().toString() + "m²");
                response.setSolutionDesignIdea(solutionDetailResponseVo.getSolutionDesignIdea());
                response.setApartmentId(solutionDetailResponseVo.getApartmentId());
                response.setApartmentPattern(solutionDetailResponseVo.getApartmentPattern());
                response.setApartmentVersion(solutionDetailResponseVo.getApartmentVersion());
                response.setReformFlag(solutionDetailResponseVo.getReformFlag());
                List<String> programImageList = Lists.newArrayList();
                List<ProgramPicDto> programImageDtoList = new ArrayList<>();
                List<ProgramCommodityListResponse.RoomInfo> roomList = Lists.newArrayList();
                if (CollectionUtils.isNotEmpty(solutionDetailResponseVo.getSolutionRoomDetailVoList())) {
                    for (SolutionRoomDetailVo solutionRoomDetailVo : solutionDetailResponseVo.getSolutionRoomDetailVoList()) {
                        if (CollectionUtils.isNotEmpty(solutionRoomDetailVo.getSolutionRoomPicVoList()) || !VersionUtil.mustUpdate(request.getAppVersion(), "5.5.6")) {
                            ProgramCommodityListResponse.RoomInfo roomInfo = new ProgramCommodityListResponse.RoomInfo();
                            roomInfo.setRoomName(solutionRoomDetailVo.getRoomUsageName());
                            roomInfo.setRoomId(solutionRoomDetailVo.getRoomId());
                            if (CollectionUtils.isNotEmpty(solutionRoomDetailVo.getSolutionRoomPicVoList())) {
                                List<String> imageList = solutionRoomDetailVo.getSolutionRoomPicVoList().stream().map(solutionRoomPicVo -> AliImageUtil.imageCompress(solutionRoomPicVo.getSolutionRoomPicURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE)).collect(Collectors.toList());
                                List<ProgramPicDto> roomPicListNew = new ArrayList();
                                if (CollectionUtils.isNotEmpty(imageList)) {
                                    List<String> freeFurnitureNameList = new ArrayList<>();
                                    if (CollectionUtils.isNotEmpty(solutionRoomDetailVo.getSolutionRoomItemVoList())) {
                                        for (SolutionRoomItemVo solutionRoomItemVo : solutionRoomDetailVo.getSolutionRoomItemVoList()) {
                                            if (solutionRoomItemVo != null && solutionRoomItemVo.getFurnitureType() == 2
                                                    && ProductCategoryConstant.CATEGORY_THREE_ID_D_LIST.contains(solutionRoomItemVo.getLastCategoryId())) {
                                                freeFurnitureNameList.add("灯具");
                                            }
                                        }
                                    }
                                    if (CollectionUtils.isNotEmpty(solutionRoomDetailVo.getBomGroupList())) {
                                        for (BomGroupVO bomGroupVO : solutionRoomDetailVo.getBomGroupList()) {
                                            if (bomGroupVO != null && bomGroupVO.getFurnitureType() == 2 && bomGroupVO.getCategoryId().equals(105001)) {
                                                freeFurnitureNameList.add("窗帘");
                                            }
                                        }
                                    }
                                    if (CollectionUtils.isNotEmpty(freeFurnitureNameList)) {
                                        freeFurnitureNameList = freeFurnitureNameList.stream().distinct().collect(Collectors.toList());
                                    }
                                    for (int i = 0; i < imageList.size(); i++) {
                                        ProgramPicDto programPicDto = new ProgramPicDto();
                                        programPicDto.setUrl(imageList.get(i));
                                        programPicDto.setFreeFurnitureNameList(freeFurnitureNameList);
                                        roomPicListNew.add(programPicDto);
                                    }
                                }
                                roomInfo.setRoomPicListNew(roomPicListNew);
                                roomInfo.setRoomPicList(imageList);
                            }
                            roomInfo.setHardNum(solutionRoomDetailVo.getDefaultHardItemList().size() + solutionRoomDetailVo.getHardBomGroupList().size());
                            roomInfo.setSoftNum(solutionRoomDetailVo.getSolutionRoomItemVoList().size() + solutionRoomDetailVo.getBomGroupList().size());
                            roomList.add(roomInfo);
                            if (CollectionUtils.isNotEmpty(roomInfo.getRoomPicList())) {
                                programImageList.addAll(roomInfo.getRoomPicList());
                                programImageDtoList.addAll(roomInfo.getRoomPicListNew());
                            }
                        }
                    }
                }
                if (StringUtils.isBlank(solutionDetailResponseVo.getSolutionStyleName())) {
                    solutionDetailResponseVo.setSolutionStyleName("现代");
                }
                response.setProgramImageList(programImageList);
                response.setProgramImageDtoList(programImageDtoList);
                response.setRoomList(roomList);
                response.setSolutionId(solutionDetailResponseVo.getSolutionId());
                response.setSolutionName(solutionDetailResponseVo.getSolutionName());
                response.setSolutionPrice(solutionDetailResponseVo.getSolutionTotalSalePrice() == null ? "" : this.parseNumber(solutionDetailResponseVo.getSolutionTotalSalePrice()) + "元");
                response.setSolutionPriceNum(solutionDetailResponseVo.getSolutionTotalSalePrice() == null ? new BigDecimal(0) : solutionDetailResponseVo.getSolutionTotalSalePrice());
                response.setSolutionStyleName(solutionDetailResponseVo.getSolutionStyleName());
                response.setTagList(solutionDetailResponseVo.getTagList());
                List<String> apartmentLayoutList = new ArrayList<>();
                apartmentLayoutList.add(AliImageUtil.imageCompress(solutionDetailResponseVo.getHouseLayout().getApartmentUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                if (solutionDetailResponseVo.getReformFlag() != null && solutionDetailResponseVo.getReformFlag() == 1 && solutionDetailResponseVo.getReformApartment() != null) {//拆改方案
                    apartmentLayoutList.add(AliImageUtil.imageCompress(solutionDetailResponseVo.getReformApartment().getApartmentUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                }
                apartmentLayoutList.add(AliImageUtil.imageCompress(solutionDetailResponseVo.getSolutionGraphicDesignUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                apartmentLayoutList.removeIf(s -> StringUtils.isBlank(s));
                response.setApartmentLayoutList(apartmentLayoutList);
                //异步缓存用户方案导读读取记录
                this.markUserSolutionReadRecord(request);

                if (request.getUserInfo() != null && request.getUserInfo().getId() != null) {
                    AppUserMessageDto appUserMessageDto = new AppUserMessageDto();
                    List<AppUserMessageDto.TriggerListBean> triggerList = new ArrayList();
                    AppUserMessageDto.TriggerListBean TriggerListBean = new AppUserMessageDto.TriggerListBean();
                    TriggerListBean.setSceneCode(6001);
                    TriggerListBean.setTriggerTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    TriggerListBean.setUserId(request.getUserInfo().getId());
                    TriggerListBean.setSolutionId(request.getProgramId());
                    triggerList.add(TriggerListBean);
                    appUserMessageDto.setTriggerList(triggerList);
                    sendAiMessage(appUserMessageDto);
                }
                List<String> freeFurnitureNameList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(programImageDtoList)) {
                    for (ProgramPicDto programPicDto : programImageDtoList) {
                        freeFurnitureNameList.addAll(programPicDto.getFreeFurnitureNameList());
                    }
                    freeFurnitureNameList = freeFurnitureNameList.stream().distinct().collect(Collectors.toList());
                }
                response.setFreeFurnitureNameList(freeFurnitureNameList);
                return response;
            }
        }
        return null;
    }


    @Async
    void markUserSolutionReadRecord(HttpProgramDetailRequest request) {
        if (StringUtils.isNotBlank(request.getAccessToken()) && request.getProgramId() != null) {
            UserDto userByToken = userProxy.getUserByToken(request.getAccessToken());
            if (userByToken != null) {
                productProgramProxy.markUserSolutionReadRecord(request.getProgramId(), userByToken.getId());
            }
        }
    }

    /**
     * 给小艾发消息
     *
     * @param appUserMessage
     */
    @Async
    private void sendAiMessage(AppUserMessageDto appUserMessageDto) {
        try {
            LOG.info("rocket message :{} to {} start", JSON.toJSONString(appUserMessageDto), APP_PROGRAM_DETAIL_TASK_TOPIC);
            SendResult sendResult = rocketMQTemplate.syncSend(APP_PROGRAM_DETAIL_TASK_TOPIC, appUserMessageDto);
            if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
                LOG.info("program.detail task topic send success content {}, sendStatus:{}", JSON.toJSONString(appUserMessageDto), sendResult.getSendStatus().toString());
            } else {
                LOG.error("program.detail task topic send error content {} , sendStatus:{}", JSON.toJSONString(appUserMessageDto), sendResult.getSendStatus().toString());
            }
        } catch (Exception e) {
            LOG.error("program.detail task topic send error message :{}", e);
        }
    }

    @Override
    public QueryCabinetPropertyListResponseNew queryCabinetPropertyList(QueryCabinetPropertyListRequest request) {
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(4);
        //查默认组合替换信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return packCabinetPropertyList(request, true);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.BOM_GROUP_DETAIL_FOR_DEFAULT.name();
            }
        });
        //查已选组合替换信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return packCabinetPropertyList(request, false);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.BOM_GROUP_DETAIL_FOR_NEW.name();
            }
        });

        Map<String, Object> dataMap = Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
        QueryCabinetPropertyListResponseNew queryCabinetPropertyListResponseNew = (QueryCabinetPropertyListResponseNew) dataMap.get(ConcurrentTaskEnum.BOM_GROUP_DETAIL_FOR_NEW.name());
        QueryCabinetPropertyListResponseNew queryCabinetPropertyListResponseDefault = (QueryCabinetPropertyListResponseNew) dataMap.get(ConcurrentTaskEnum.BOM_GROUP_DETAIL_FOR_DEFAULT.name());
        this.removeAccidentProperty(queryCabinetPropertyListResponseNew, request.getSecondCategoryId());
        this.removeAccidentProperty(queryCabinetPropertyListResponseDefault, request.getSecondCategoryId());
        if (queryCabinetPropertyListResponseNew != null && CollectionUtils.isNotEmpty(queryCabinetPropertyListResponseNew.getCabinetDataDtoList()) && queryCabinetPropertyListResponseDefault != null && CollectionUtils.isNotEmpty(queryCabinetPropertyListResponseDefault.getCabinetDataDtoList())) {
            for (QueryCabinetPropertyListResponseNew.CabinetDataDto cabinetDataDtoForNew : queryCabinetPropertyListResponseNew.getCabinetDataDtoList()) {
                for (QueryCabinetPropertyListResponseNew.CabinetDataDto cabinetDataDtoForDefault : queryCabinetPropertyListResponseDefault.getCabinetDataDtoList()) {
                    if (cabinetDataDtoForNew.getCabinetType().equals(cabinetDataDtoForDefault.getCabinetType()) && CollectionUtils.isNotEmpty(cabinetDataDtoForNew.getGroupRelationDtoList()) && CollectionUtils.isNotEmpty(cabinetDataDtoForDefault.getDataList())) {
                        for (QueryCabinetPropertyListResponseNew.PropertyDataDto propertyDataDtoForNew : cabinetDataDtoForNew.getDataList()) {
                            for (QueryCabinetPropertyListResponseNew.PropertyDataDto propertyDataDtoForDefault : cabinetDataDtoForDefault.getDataList()) {
                                if (propertyDataDtoForNew.getCategoryId().equals(propertyDataDtoForDefault.getCategoryId()) && CollectionUtils.isNotEmpty(propertyDataDtoForNew.getOptionList())) {
                                    propertyDataDtoForNew.setDefaultPropertyValueCode(propertyDataDtoForDefault.getSelectPropertyValueCode());
                                    for (QueryCabinetPropertyListResponseNew.PropertyValueList propertyValueList : propertyDataDtoForNew.getOptionList()) {
                                        if (propertyValueList.getPropertyValueCode().equals(propertyDataDtoForDefault.getSelectPropertyValueCode())) {
                                            propertyValueList.setIsDefault(1);
                                            break;
                                        }
                                    }
                                    propertyDataDtoForNew.getOptionList().sort(Comparator.comparingInt(QueryCabinetPropertyListResponseNew.PropertyValueList::getIsDefault));
                                }
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(cabinetDataDtoForNew.getComponentDataList()) && CollectionUtils.isNotEmpty(cabinetDataDtoForDefault.getComponentDataList())) {
                        for (QueryCabinetPropertyListResponseNew.PropertyDataDto propertyDataDtoNew : cabinetDataDtoForNew.getComponentDataList()) {
                            for (QueryCabinetPropertyListResponseNew.PropertyDataDto propertyDataDtoDefault : cabinetDataDtoForDefault.getComponentDataList()) {
                                if (propertyDataDtoNew.getComponentId().equals(propertyDataDtoDefault.getComponentId())) {
                                    propertyDataDtoNew.setDefaultMaterialId(propertyDataDtoDefault.getMaterialId());
                                    propertyDataDtoNew.setDefaultPropertyValueCode(propertyDataDtoNew.getDefaultPropertyValueCode());
                                    for (QueryCabinetPropertyListResponseNew.PropertyValueList propertyValueList : propertyDataDtoNew.getOptionList()) {
                                        if (propertyValueList.getPropertyValueCode().equals(propertyDataDtoNew.getDefaultPropertyValueCode())) {
                                            propertyValueList.setIsDefault(1);
                                            break;
                                        }
                                    }
                                    propertyDataDtoNew.getOptionList().sort(Comparator.comparingInt(QueryCabinetPropertyListResponseNew.PropertyValueList::getIsDefault));
                                }
                            }
                        }
                    }
                }
            }
        }
        Map<String, String> appGuiBomPropertyNameConvertMap = JSON.parseObject(appGuiBomPropertyNameConvertJson, new TypeReference<Map<String, String>>() {
        });
        if (CollectionUtils.isNotEmpty(queryCabinetPropertyListResponseNew.getCabinetDataDtoList())) {
            for (QueryCabinetPropertyListResponseNew.CabinetDataDto cabinetDataDto : queryCabinetPropertyListResponseNew.getCabinetDataDtoList()) {
                int indexOf = 0;
                if (CollectionUtils.isNotEmpty(cabinetDataDto.getDataList())) {
                    for (QueryCabinetPropertyListResponseNew.PropertyDataDto propertyDataDto : cabinetDataDto.getDataList()) {
                        propertyDataDto.setIndex(indexOf);
                        for (QueryCabinetPropertyListResponseNew.PropertyValueList propertyValueList : propertyDataDto.getOptionList()) {
                            propertyValueList.setIndex(indexOf);
                        }
                        indexOf++;
                        String propertyName = appGuiBomPropertyNameConvertMap.get(propertyDataDto.getPropertyName());
                        if (StringUtils.isNotBlank(propertyName)) {
                            propertyDataDto.setPropertyName(propertyName);
                        }
                    }
                }
                indexOf = 0;
                if (CollectionUtils.isNotEmpty(cabinetDataDto.getComponentDataList())) {
                    for (QueryCabinetPropertyListResponseNew.PropertyDataDto propertyDataDto : cabinetDataDto.getComponentDataList()) {
                        propertyDataDto.setIndex(indexOf);
                        for (QueryCabinetPropertyListResponseNew.PropertyValueList propertyValueList : propertyDataDto.getOptionList()) {
                            propertyValueList.setIndex(indexOf);
                        }
                        indexOf++;
                        String propertyName = appGuiBomPropertyNameConvertMap.get(propertyDataDto.getPropertyName());
                        if (StringUtils.isNotBlank(propertyName)) {
                            propertyDataDto.setPropertyName(propertyName);
                        }
                    }
                }
            }
        }
        return queryCabinetPropertyListResponseNew;
    }

    private void removeAccidentProperty(QueryCabinetPropertyListResponseNew queryCabinetPropertyListResponse, Integer secondCategoryId) {
        CabinetItemCategoryDto cabinetItemCategoryDto = JSON.parseObject(appGuiBomPropertyRangeJson, new TypeReference<List<CabinetItemCategoryDto>>() {
        }).stream().collect(toMap(cabinetItemCategory -> cabinetItemCategory.getCategoryId(), cabinetItemCategory -> cabinetItemCategory)).get(secondCategoryId);

        Map<String, List<Integer>> removeIndexMapForPro = Maps.newHashMap();
        Map<String, List<Integer>> removeIndexMapForCom = Maps.newHashMap();
        if (cabinetItemCategoryDto != null && CollectionUtils.isNotEmpty(cabinetItemCategoryDto.getCabinetList())) {
            List<String> typeList = queryCabinetPropertyListResponse.getCabinetDataDtoList().stream().map(cabinetDataDto -> cabinetDataDto.getCabinetTypeName()).collect(Collectors.toList());
            for (CabinetItemCategoryDto.CabinetList cabinetList : cabinetItemCategoryDto.getCabinetList()) {
                if (!typeList.contains(cabinetList.getCabinetTypeName())) {
                    continue;
                }
                for (QueryCabinetPropertyListResponseNew.CabinetDataDto cabinetDataDto : queryCabinetPropertyListResponse.getCabinetDataDtoList()) {
                    if (!cabinetDataDto.getCabinetTypeName().equals(cabinetList.getCabinetTypeName())) {
                        continue;
                    }
                    List<Integer> indexListForPro = Lists.newArrayList();
                    List<Integer> indexListForCom = Lists.newArrayList();
                    for (QueryCabinetPropertyListResponseNew.PropertyDataDto propertyDataDto : cabinetDataDto.getDataList()) {
                        boolean flag = false;
                        for (CabinetItemCategoryDto.CabinetList.ComponentCategoryList componentCategoryList : cabinetList.getComponentCategoryList()) {
                            if (propertyDataDto.getCategoryId().equals(componentCategoryList.getComponentCategory())) {
                                for (CabinetItemCategoryDto.CabinetList.ComponentCategoryList.ComponentPropertyList componentPropertyList : componentCategoryList.getPropertyList()) {
                                    if (componentPropertyList.getType().equals(1) && componentPropertyList.getPropertyId().equals(propertyDataDto.getPropertyId())) {
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                            if (flag) {
                                break;
                            }
                        }
                        if (!flag) {
                            indexListForPro.add(propertyDataDto.getIndex());
                        }
                    }
                    for (QueryCabinetPropertyListResponseNew.PropertyDataDto propertyDataDto : cabinetDataDto.getComponentDataList()) {
                        boolean flag = false;
                        for (CabinetItemCategoryDto.CabinetList.ComponentCategoryList componentCategoryList : cabinetList.getComponentCategoryList()) {
                            if (propertyDataDto.getCategoryId().equals(componentCategoryList.getComponentCategory())) {
                                for (CabinetItemCategoryDto.CabinetList.ComponentCategoryList.ComponentPropertyList componentPropertyList : componentCategoryList.getPropertyList()) {
                                    if (componentPropertyList.getType().equals(2) && componentPropertyList.getPropertyId().equals(propertyDataDto.getCategoryId())) {
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                            if (flag) {
                                break;
                            }
                        }
                        if (!flag) {
                            indexListForCom.add(propertyDataDto.getIndex());
                        }
                    }
                    removeIndexMapForPro.put(cabinetDataDto.getCabinetTypeName(), indexListForPro);
                    removeIndexMapForCom.put(cabinetDataDto.getCabinetTypeName(), indexListForCom);
                }
            }
        }
        for (QueryCabinetPropertyListResponseNew.CabinetDataDto cabinetDataDto : queryCabinetPropertyListResponse.getCabinetDataDtoList()) {
            List<Integer> indexListForPro = removeIndexMapForPro.get(cabinetDataDto.getCabinetTypeName());
            List<Integer> indexListForCom = removeIndexMapForCom.get(cabinetDataDto.getCabinetTypeName());
            if (CollectionUtils.isNotEmpty(indexListForPro)) {
                cabinetDataDto.getDataList().removeIf(obj -> indexListForPro.contains(obj.getIndex()));
            }
            if (CollectionUtils.isNotEmpty(indexListForCom)) {
                cabinetDataDto.getComponentDataList().removeIf(obj -> indexListForCom.contains(obj.getIndex()));
            }
        }
    }

    /**
     * 查询组合替换信息
     */
    private QueryCabinetPropertyListResponseNew packCabinetPropertyList(QueryCabinetPropertyListRequest request, boolean isDefault) {
        if (CollectionUtils.isNotEmpty(request.getQueryList())) {
            QueryCabinetPropertyListResponseNew queryCabinetPropertyListResponse = new QueryCabinetPropertyListResponseNew();
            queryCabinetPropertyListResponse.setCabinetTypeNameJoin(request.getQueryList().stream().map(QueryCabinetPropertyListRequest.GroupQueryRequest::getCabinetTypeName).distinct().collect(Collectors.joining("、")));
            Map<String, List<QueryCabinetPropertyListRequest.GroupQueryRequest>> queryListGroupByType = request.getQueryList().stream().collect(groupingBy(QueryCabinetPropertyListRequest.GroupQueryRequest::getCabinetType));
            List<QueryCabinetPropertyListResponseNew.CabinetDataDto> cabinetDataDtoList = Lists.newArrayList();
            queryListGroupByType.forEach((key, value) -> cabinetDataDtoList.add(new QueryCabinetPropertyListResponseNew.CabinetDataDto().setCabinetType(key).setCabinetTypeName(value.get(0).getCabinetTypeName()).setGroupRelationDtoList(value.stream().map(mapper -> new QueryCabinetPropertyListResponseNew.GroupRelationDto().setDefaultGroupId(mapper.getDefaultGroupId()).setGroupId(mapper.getGroupId()).setDefaultGroupNum(mapper.getDefaultGroupNum())).collect(Collectors.toList()))));
            cabinetDataDtoList.parallelStream().forEach(cabinetDataDto -> {
                if (cabinetDataDto.getGroupRelationDtoList().size() == 1) {
                    List<QueryCabinetPropertyListResponseNew.PropertyDataDto> componentDataList = Lists.newArrayList();
                    QueryGroupReplaceDetailRequest queryGroupReplaceDetailRequest = new QueryGroupReplaceDetailRequest();
                    List<QueryCabinetPropertyListResponseNew.GroupRelationDto> groupRelationDtoList = cabinetDataDto.getGroupRelationDtoList();
                    QueryCabinetPropertyListResponseNew.GroupRelationDto groupRelationDto = groupRelationDtoList.get(0);
                    queryGroupReplaceDetailRequest.setReplaceGroupId(groupRelationDto.getGroupId());
                    queryGroupReplaceDetailRequest.setDefaultGroupId(groupRelationDto.getDefaultGroupId());
                    queryGroupReplaceDetailRequest.setDefaultGroupNum(groupRelationDto.getDefaultGroupNum());
                    queryGroupReplaceDetailRequest.setRoomId(request.getRoomId());
                    GroupReplaceDetailVO groupReplaceDetailVO = curtainService.queryGroupReplaceDetail(queryGroupReplaceDetailRequest);
                    if (groupReplaceDetailVO != null) {
                        if (CollectionUtils.isNotEmpty(groupReplaceDetailVO.getComponentList())) {
                            groupReplaceDetailVO.getComponentList().parallelStream().forEach(componentList -> {
                                QuerMaterialForPageRequest querMaterialForPageRequest = new QuerMaterialForPageRequest();
                                querMaterialForPageRequest.setComponentId(componentList.getComponentId());
                                querMaterialForPageRequest.setDefaultGroupId(groupReplaceDetailVO.getDefaultGroupId());
                                querMaterialForPageRequest.setDefaultGroupNum(groupRelationDto.getDefaultGroupNum());
                                querMaterialForPageRequest.setDefaultMaterialId(componentList.getDefaultMaterial().getMaterialId());
                                querMaterialForPageRequest.setPageNo(0);
                                querMaterialForPageRequest.setPageSize(Integer.MAX_VALUE);
                                querMaterialForPageRequest.setSelectedMaterialId(componentList.getReplaceMaterial().getMaterialId().toString());
                                querMaterialForPageRequest.setSortType(0);
                                querMaterialForPageRequest.setOptionValueIdList(Lists.newArrayList());
                                MaterialForPageVO materialForPageVO = curtainService.querMaterialForPage(querMaterialForPageRequest);
                                QueryCabinetPropertyListResponseNew.PropertyDataDto propertyDataDto = new QueryCabinetPropertyListResponseNew.PropertyDataDto();
                                propertyDataDto.setDefaultPropertyValueCode(componentList.getDefaultMaterial().getMaterialId());
                                propertyDataDto.setSelectPropertyValueCode(componentList.getReplaceMaterial().getMaterialId());
                                propertyDataDto.setComponentId(componentList.getComponentId());
                                propertyDataDto.setMaterialId(componentList.getReplaceMaterial().getMaterialId());
                                propertyDataDto.setDefaultMaterialId(componentList.getDefaultMaterial().getMaterialId());
                                propertyDataDto.setCategoryId(componentList.getComponentCategoryId());
                                propertyDataDto.setPropertyId(componentList.getComponentId());
                                propertyDataDto.setType(2);
                                propertyDataDto.setPropertyName(componentList.getComponentCategoryName());
                                if (materialForPageVO != null && CollectionUtils.isNotEmpty(materialForPageVO.getRecords())) {
                                    List<QueryCabinetPropertyListResponseNew.PropertyValueList> optionList = Lists.newArrayList();
                                    for (MaterialForPageVO.RecordsBean record : materialForPageVO.getRecords()) {
                                        QueryCabinetPropertyListResponseNew.PropertyValueList propertyValueList = new QueryCabinetPropertyListResponseNew.PropertyValueList();
                                        propertyValueList.setPropertyValueCode(record.getMaterialId());
                                        propertyValueList.setPropertyValue(record.getMaterialName());
                                        propertyValueList.setPropertyUrl(AliImageUtil.imageCompress(record.getMaterialImage(), 1, 750, ImageConstant.SIZE_SMALL));
                                        propertyValueList.setType(2);
                                        optionList.add(propertyValueList);
                                    }
                                    propertyDataDto.setOptionList(optionList);
                                }
                                componentList.setPropertyDataDto(propertyDataDto);
                            });
                            int indexOf = 0;
                            for (GroupReplaceDetailVO.ComponentList componentList : groupReplaceDetailVO.getComponentList()) {
                                componentList.getPropertyDataDto().setIndex(indexOf);
                                for (QueryCabinetPropertyListResponseNew.PropertyValueList propertyValueList : componentList.getPropertyDataDto().getOptionList()) {
                                    propertyValueList.setIndex(indexOf);
                                }
                                indexOf++;
                                componentDataList.add(componentList.getPropertyDataDto());
                            }
                        }
                    }
                    cabinetDataDto.setComponentDataList(componentDataList);
                }
                cabinetDataDto.setDefaultGroupIdList(queryListGroupByType.get(cabinetDataDto.getCabinetType()).stream().map(groupQueryRequest -> groupQueryRequest.getDefaultGroupId()).collect(Collectors.toList()));
                cabinetDataDto.setGroupIdList(queryListGroupByType.get(cabinetDataDto.getCabinetType()).stream().map(groupQueryRequest -> groupQueryRequest.getGroupId()).collect(Collectors.toList()));
                List<QueryCabinetPropertyListRequest.GroupQueryRequest> groupQueryRequests = queryListGroupByType.get(cabinetDataDto.getCabinetType());
                QueryCabinetPropertyListResponseDolly queryCabinetPropertyListResponseDolly;
                if (isDefault) {
                    queryCabinetPropertyListResponseDolly = productProgramProxy.queryCabinetPropertyList(groupQueryRequests.stream().map(QueryCabinetPropertyListRequest.GroupQueryRequest::getDefaultGroupId).collect(Collectors.toList()));
                } else {
                    queryCabinetPropertyListResponseDolly = productProgramProxy.queryCabinetPropertyList(groupQueryRequests.stream().map(QueryCabinetPropertyListRequest.GroupQueryRequest::getGroupId).collect(Collectors.toList()));
                }
                if (queryCabinetPropertyListResponseDolly == null) {
                    return;
                }
                for (QueryCabinetPropertyListResponseDolly.GroupList groupList : queryCabinetPropertyListResponseDolly.getGroupList()) {
                    for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList componentList : groupList.getComponentList()) {
                        for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList propertyList : componentList.getPropertyList()) {
                            propertyList.setCategoryId(componentList.getCategoryId());
                            if (CollectionUtils.isNotEmpty(propertyList.getTemplatePropertyValueList())) {
                                for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList.TemplatePropertyValueList templatePropertyValueList : propertyList.getTemplatePropertyValueList()) {
                                    templatePropertyValueList.setCategoryId(componentList.getCategoryId());
                                }
                            }
                        }
                    }
                }
                cabinetDataDto.setDataList(Lists.newArrayList());
                List<QueryCabinetPropertyListResponseDolly.GroupList.ComponentList> componentList = Lists.newArrayList();
                List<List<Integer>> categoryIdList = Lists.newArrayList();
                for (QueryCabinetPropertyListResponseDolly.GroupList groupList : queryCabinetPropertyListResponseDolly.getGroupList()) {
                    List<Integer> collect = groupList.getComponentList().stream().map(mapper -> mapper.getCategoryId()).collect(Collectors.toList());
                    categoryIdList.add(collect);
                }
                Map<Integer, Map<Integer, List<List<Integer>>>> categoryIdForPro = Maps.newHashMap();
                Map<Integer, Map<Integer, List<QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList.TemplatePropertyValueList>>> categoryIdForProAll = Maps.newHashMap();
                List<Integer> intersectionListForCategory = intersectionForList(categoryIdList);
                for (QueryCabinetPropertyListResponseDolly.GroupList groupList : queryCabinetPropertyListResponseDolly.getGroupList()) {
                    for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList list : groupList.getComponentList()) {
                        if (intersectionListForCategory.contains(list.getCategoryId())) {
                            boolean onAdd = true;
                            for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList componentList1 : componentList) {
                                if (componentList1.getCategoryId().equals(list.getCategoryId())) {
                                    onAdd = false;
                                }
                            }
                            if (onAdd) {
                                componentList.add(list);
                            }
                            if (MapUtils.isEmpty(categoryIdForPro.get(list.getCategoryId()))) {
                                categoryIdForPro.put(list.getCategoryId(), Maps.newHashMap());
                            }
                            if (MapUtils.isEmpty(categoryIdForProAll.get(list.getCategoryId()))) {
                                categoryIdForProAll.put(list.getCategoryId(), Maps.newHashMap());
                            }
                            if (CollectionUtils.isNotEmpty(list.getPropertyList())) {
                                for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList propertyList : list.getPropertyList()) {
                                    if (CollectionUtils.isEmpty(categoryIdForProAll.get(list.getCategoryId()).get(propertyList.getPropertyId()))) {
                                        categoryIdForProAll.get(list.getCategoryId()).put(propertyList.getPropertyId(), Lists.newArrayList());
                                    }
                                    if (CollectionUtils.isNotEmpty(propertyList.getTemplatePropertyValueList())) {
                                        categoryIdForProAll.get(list.getCategoryId()).get(propertyList.getPropertyId()).addAll(propertyList.getTemplatePropertyValueList());
                                    }
                                }
                            }
                            if (CollectionUtils.isNotEmpty(list.getPropertyList())) {
                                for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList propertyList : list.getPropertyList()) {
                                    if (CollectionUtils.isEmpty(categoryIdForPro.get(list.getCategoryId()).get(propertyList.getPropertyId()))) {
                                        categoryIdForPro.get(list.getCategoryId()).put(propertyList.getPropertyId(), Lists.newArrayList());
                                    }
                                    if (CollectionUtils.isNotEmpty(propertyList.getTemplatePropertyValueList())) {
                                        categoryIdForPro.get(list.getCategoryId()).get(propertyList.getPropertyId()).add(propertyList.getTemplatePropertyValueList().stream().map(templatePropertyValueList -> templatePropertyValueList.getPropertyValueCode()).collect(Collectors.toList()));
                                    } else {
                                        categoryIdForPro.get(list.getCategoryId()).get(propertyList.getPropertyId()).add(Lists.newArrayList());
                                    }
                                }
                            }
                        }
                    }
                }
                Map<Integer, List<QueryCabinetPropertyListResponseDolly.GroupList.ComponentList>> componentMapGroupByCategoryId = componentList.stream()
                        .collect(groupingBy(QueryCabinetPropertyListResponseDolly.GroupList.ComponentList::getCategoryId));//相同部位
                List<QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList> propertyListResult = Lists.newArrayList();
                List<QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList.TemplatePropertyValueList> templatePropertyValueList = Lists.newArrayList();


                componentMapGroupByCategoryId.forEach((categoryId, componentDataList) -> {

                    //找相同属性交集
                    List<List<Integer>> intersectionListForCategoryComponentId = Lists.newArrayList();
                    List<List<Integer>> intersectionListForValueCodeTemp = Lists.newArrayList();
                    for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList list : componentDataList) {
                        List<Integer> collect = list.getPropertyList().stream().map(propertyList -> propertyList.getPropertyId()).collect(Collectors.toList());
                        intersectionListForCategoryComponentId.add(collect);
                    }
                    List<Integer> intersectionForPropertyId = intersectionForList(intersectionListForCategoryComponentId);
                    for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList list : componentDataList) {
                        //收集同组件下所有可选属性
                        for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList propertyList : list.getPropertyList()) {
                            if (intersectionForPropertyId.contains(propertyList.getPropertyId())) {
                                //属性交集
                                propertyListResult.add(propertyList);
                            }
                            intersectionListForValueCodeTemp.add(propertyList.getTemplatePropertyValueList().stream().map(templatePropertyValueList1 -> templatePropertyValueList1.getPropertyValueCode()).collect(Collectors.toList()));
                        }
                    }
                    List<Integer> intersectionListForValueCode = intersectionForList(intersectionListForValueCodeTemp);
                    for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList propertyList : propertyListResult) {
                        for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList.TemplatePropertyValueList propertyValueList : propertyList.getTemplatePropertyValueList()) {
                            if (intersectionListForValueCode.contains(propertyValueList.getPropertyValueCode())) {
                                //选择项交集
                                templatePropertyValueList.add(propertyValueList);
                            }
                        }
                    }
                });
                List<QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList.TemplatePropertyValueList> templatePropertyValueListResult = Lists.newArrayList();
                for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList.TemplatePropertyValueList propertyValueList : templatePropertyValueList) {
                    boolean onAdd = true;
                    for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList.TemplatePropertyValueList valueList : templatePropertyValueListResult) {
                        if (propertyValueList.getCategoryId().equals(valueList.getCategoryId()) && propertyValueList.getPropertyId().equals(valueList.getPropertyId()) && propertyValueList.getPropertyValueCode().equals(valueList.getPropertyValueCode())) {
                            onAdd = false;
                            break;
                        }
                    }
                    if (onAdd) {
                        templatePropertyValueListResult.add(propertyValueList);
                    }
                }
                List<QueryCabinetPropertyListResponseNew.PropertyDataDto> dataList = Lists.newArrayList();
                componentMapGroupByCategoryId.forEach((integer, list) -> {
                    for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList propertyList : propertyListResult) {
                        QueryCabinetPropertyListResponseNew.PropertyDataDto propertyDataDto = null;
                        QueryCabinetPropertyListResponseDolly.GroupList.ComponentList componentListForIndex = list.get(0);
                        if (componentListForIndex.getCategoryId().equals(propertyList.getCategoryId())) {
                            propertyDataDto = new QueryCabinetPropertyListResponseNew.PropertyDataDto();
                            propertyDataDto.setCategoryId(componentListForIndex.getCategoryId());
                            propertyDataDto.setType(1);
                            propertyDataDto.setMaterialId(componentListForIndex.getMaterialId());
                            propertyDataDto.setComponentId(componentListForIndex.getComponentId());
                            propertyDataDto.setPropertyName(componentListForIndex.getCategoryName() + propertyList.getPropertyName());
                            propertyDataDto.setPropertyId(propertyList.getPropertyId());
                            propertyDataDto.setSelectPropertyValueCode(propertyList.getPropertyValueCode());
                            List<QueryCabinetPropertyListResponseNew.PropertyValueList> optionList = Lists.newArrayList();
                            Map<Integer, List<List<Integer>>> integerListMap = categoryIdForPro.get(componentListForIndex.getCategoryId());
                            for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList.TemplatePropertyValueList propertyValueList : templatePropertyValueListResult) {
                                if (propertyList.getPropertyId().equals(propertyValueList.getPropertyId()) && propertyValueList.getCategoryId().equals(propertyList.getCategoryId())) {
                                    QueryCabinetPropertyListResponseNew.PropertyValueList propertyValueListData = new QueryCabinetPropertyListResponseNew.PropertyValueList();
                                    propertyValueListData.setPropertyUrl(propertyValueList.getPropertyValueUrl());
                                    propertyValueListData.setPropertyValue(propertyValueList.getPropertyValue());
                                    propertyValueListData.setPropertyValueCode(propertyValueList.getPropertyValueCode());
                                    propertyValueListData.setType(1);
                                    optionList.add(propertyValueListData);
                                }
                            }
                            propertyDataDto.setOptionList(optionList);
                        }
                        if (propertyDataDto != null) {
                            dataList.add(propertyDataDto);
                        }
                    }
                });
                int indexOf = 0;
                for (QueryCabinetPropertyListResponseNew.PropertyDataDto propertyDataDto : dataList) {
                    Map<Integer, List<List<Integer>>> integerListMap = categoryIdForPro.get(propertyDataDto.getCategoryId());
                    Map<Integer, List<QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList.TemplatePropertyValueList>> integerListMapForAll = categoryIdForProAll.get(propertyDataDto.getCategoryId());
                    List<List<Integer>> valueCodeList = integerListMap.get(propertyDataDto.getPropertyId());
                    List<Integer> integersResult = this.intersectionForList(valueCodeList);
                    propertyDataDto.setOptionList(Lists.newArrayList());
                    if (CollectionUtils.isNotEmpty(integersResult)) {
                        for (Integer valueCode : integersResult) {
                            List<QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList.TemplatePropertyValueList> templatePropertyValueLists = integerListMapForAll.get(propertyDataDto.getPropertyId());
                            if (CollectionUtils.isNotEmpty(templatePropertyValueLists)) {
                                for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList.TemplatePropertyValueList propertyValueList : templatePropertyValueLists) {
                                    List<Integer> opList = propertyDataDto.getOptionList().stream().map(propertyValueListTemp -> propertyValueListTemp.getPropertyValueCode()).collect(Collectors.toList());
                                    if (propertyValueList.getPropertyValueCode().equals(valueCode) && !opList.contains(valueCode)) {
                                        QueryCabinetPropertyListResponseNew.PropertyValueList propertyValueListData = new QueryCabinetPropertyListResponseNew.PropertyValueList();
                                        propertyValueListData.setPropertyUrl(propertyValueList.getPropertyValueUrl());
                                        propertyValueListData.setPropertyValue(propertyValueList.getPropertyValue());
                                        propertyValueListData.setPropertyValueCode(propertyValueList.getPropertyValueCode());
                                        propertyValueListData.setType(1);
                                        propertyDataDto.getOptionList().add(propertyValueListData);
                                    }
                                }
                            }
                        }
                    }
                    propertyDataDto.setIndex(indexOf);
                    for (QueryCabinetPropertyListResponseNew.PropertyValueList propertyValueList : propertyDataDto.getOptionList()) {
                        propertyValueList.setIndex(indexOf);
                    }
                    indexOf++;
                }
                cabinetDataDto.setDataList(dataList);
            });
            queryCabinetPropertyListResponse.setCabinetDataDtoList(cabinetDataDtoList);
            return queryCabinetPropertyListResponse;
        }
        return null;
    }

    private List<Integer> intersectionForList(List<List<Integer>> targetList) {
        if (CollectionUtils.isEmpty(targetList)) {
            return Lists.newArrayList();
        }
        List<Integer> remove = targetList.remove(0);
        if (CollectionUtils.isEmpty(targetList)) {
            return remove;
        }
        List<Integer> intersectionListForCategory = Lists.newArrayList();
        for (Integer integer : remove) {
            boolean noAdd = true;
            for (List<Integer> integers : targetList) {
                if (!integers.contains(integer)) {
                    noAdd = false;
                    break;
                }
            }
            if (noAdd) {
                intersectionListForCategory.add(integer);
            }
        }
        return intersectionListForCategory;
    }

    private String joinLayoutDesc(SolutionDetailResponseVo solutionDetailResponseVo) {
        List<String> roomNameList = solutionDetailResponseVo.getSolutionRoomDetailVoList().stream().map(solutionRoomDetailVo -> solutionRoomDetailVo.getRoomUsageName()).collect(Collectors.toList());
        roomNameList.removeIf(roomName -> roomName.equals("全屋"));
        String roomNameListForJoin = roomNameList.stream().collect(Collectors.joining("、"));
        String houseTypeName = this.joinHouseTypeName(solutionDetailResponseVo);
        if (solutionDetailResponseVo.getReformFlag().equals(0)) {
            return String.format(programText.getProgramHouseLayoutNoChange(), houseTypeName, roomNameListForJoin);
        } else if (solutionDetailResponseVo.getReformFlag().equals(1)) {
            return String.format(programText.getProgramHouseLayoutHasChange(), houseTypeName, roomNameListForJoin);
        }
        return "";
    }

    /**
     * 将数字转为千位分割格式
     *
     * @param price
     * @return
     */

    public String parseNumber(BigDecimal price) {
        DecimalFormat df = new DecimalFormat(",###,###");
        return df.format(price);
    }


    public Map<Integer, SkuBaseInfoDto.SkuExtPropertyInfo> batchQuerySkuExtPropertyBySkuIdListAndPropertyType(List<Integer> skuId, Integer propertyType) {
        List<SkuBaseInfoDto> skuBaseInfoDtos = productProxy.batchQuerySkuBaseInfo(skuId);
        if (CollectionUtils.isNotEmpty(skuBaseInfoDtos)) {
            Map<Integer, SkuBaseInfoDto.SkuExtPropertyInfo> resultMap = Maps.newHashMap();
            for (SkuBaseInfoDto skuBaseInfoDto : skuBaseInfoDtos) {
                if (skuBaseInfoDto != null && CollectionUtils.isNotEmpty(skuBaseInfoDto.getPropertyExtList())) {
                    for (SkuBaseInfoDto.SkuExtPropertyInfo skuExtPropertyInfo : skuBaseInfoDto.getPropertyExtList()) {
                        if (propertyType.equals(Integer.parseInt(skuExtPropertyInfo.getPropertyType()))) {
                            resultMap.put(skuBaseInfoDto.getSkuId(), skuExtPropertyInfo);
                            break;
                        }
                    }
                }
            }
            return resultMap;
        }
        return Maps.newHashMap();
    }

    public Map<Integer, Map<String, String>> getColourAndTextureByGroupIdList(List<Integer> groupIdList) {
        Map<Integer, Map<String, String>> result = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(groupIdList)) {
            List<BomGroupVO> groupVOList = curtainProxy.queryGroupListDetailByGroupListForCabinet(groupIdList);
            for (BomGroupVO bomGroupVO : groupVOList) {
                if (CollectionUtils.isNotEmpty(bomGroupVO.getComponentList())) {
                    BomGroupVO.ComponentList componentList = bomGroupVO.getComponentList().get(0);
                    if (componentList.getMaterialDetail() != null && StringUtils.isNotBlank(componentList.getMaterialDetail().getSpecifications())) {
                        String specifications = componentList.getMaterialDetail().getSpecifications();
                        String[] split = specifications.replace(" ", "").replace("[", "").replace("]", "").split("\\|");
                        Map<String, String> map = Maps.newHashMap();
                        String colour = null;
                        String texture = null;
                        for (String s : split) {
                            if (colour == null || texture == null) {
                                String[] split1 = s.split(":");
                                if (split1[0].equals("颜色")) {
                                    colour = split1[1];
                                }
                                if (split1[0].equals("材质")) {
                                    texture = split1[1];
                                }
                            } else {
                                break;
                            }
                        }
                        map.put("colour", colour);
                        map.put("texture", texture);
                        result.put(bomGroupVO.getGroupId(), map);
                    }
                }
            }
        }
        return result;
    }


    @Override
    public DraftSimpleRequestPage querySolutionAndDraftList(SolutionListRequest request) {
        List<SolutionBottomVo> solutionBottomList = JSON.parseArray(appSolutionListBottom, SolutionBottomVo.class);
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);
        //查默认组合替换信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                return querySolutionDescList(request);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SOLUTION_DESC_LIST.name();
            }
        });
        //查已选组合替换信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                QueryDraftRequest queryDraftRequest = new QueryDraftRequest();
                queryDraftRequest.setOrderId(request.getOrderId());
                queryDraftRequest.setPageNo(1);
                queryDraftRequest.setPageSize(Integer.MAX_VALUE);
                return homeV5PageService.queryDraftList(queryDraftRequest);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_DRAFT_LIST.name();
            }
        });
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() throws Exception {
                return orderProxy.queryOrderSummaryInfo(request.getOrderId());
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name();
            }
        });
        Map<String, Object> dataMap = Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
        DraftSimpleRequestPage draftSimpleRequestPage = (DraftSimpleRequestPage) dataMap.get(ConcurrentTaskEnum.QUERY_DRAFT_LIST.name());
        SolutionEffectResponse solutionEffectResponse = (SolutionEffectResponse) dataMap.get(ConcurrentTaskEnum.QUERY_SOLUTION_DESC_LIST.name());
        OrderDetailDto orderDetailDto = (OrderDetailDto) dataMap.get(ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name());
        List<DraftSimpleRequest> draftSimpleRequestList = Lists.newArrayList();
        final Boolean[] hasCustomized = {false};
        if (null == draftSimpleRequestPage) {
            return null;
        }
        if (solutionEffectResponse != null) {
            draftSimpleRequestPage.setHouseTypeImage(solutionEffectResponse.getHouseTypeImage());
            draftSimpleRequestPage.setSpaceMarkList(solutionEffectResponse.getSpaceMarkList());
            if (CollectionUtils.isNotEmpty(solutionEffectResponse.getSolutionEffectInfoList())) {
                List<Integer> readSolutionIdList = Lists.newArrayList();
                if (request.getUserInfo() != null && request.getUserInfo().getId() != null) {
                    HttpUserInfoRequest userInfo = request.getUserInfo();
                    if (userInfo != null) {
                        List<Integer> ids = productProgramProxy.queryUserSolutionReadListByUserId(userInfo.getId());
                        readSolutionIdList.addAll(ids == null ? Lists.newArrayList() : ids);
                    }
                }
                List<DraftSimpleRequest> programList = solutionEffectResponse.getSolutionEffectInfoList().stream().map(solutionEffectInfo -> {
                    if (solutionEffectInfo.getSolutionType() != null && solutionEffectInfo.getSolutionType() == 1) {
                        hasCustomized[0] = true;
                    }
                    DraftSimpleRequest draftSimpleRequest = new DraftSimpleRequest();
                    BeanUtils.copyProperties(solutionEffectInfo, draftSimpleRequest);
                    draftSimpleRequest.setOrderNum(request.getOrderId());
                    draftSimpleRequest.setType(1);
                    draftSimpleRequest.setHasRead(CollectionUtils.isNotEmpty(readSolutionIdList) && readSolutionIdList.contains(draftSimpleRequest.getSolutionId()) ? 1 : 0);
                    return draftSimpleRequest;
                }).collect(Collectors.toList());
                List<DraftSimpleRequest> signDraftList = draftSimpleRequestPage.getOrderDraftSimpleList().stream().filter(draftSimpleRequest -> draftSimpleRequest.getDraftSignStatus().equals(1)).collect(Collectors.toList());
                draftSimpleRequestPage.getOrderDraftSimpleList().removeIf(draftSimpleRequest -> draftSimpleRequest.getDraftSignStatus().equals(1));
                if (hasCustomized[0]) {//有专属方案
                    draftSimpleRequestPage.setBottomFlag(1);
                    draftSimpleRequestList.addAll(signDraftList);
                    Collections.sort(programList, Comparator.comparing(DraftSimpleRequest::getIsRevised).reversed().thenComparing(DraftSimpleRequest::getPublishTime).reversed());
                    draftSimpleRequestList.addAll(programList);
                    draftSimpleRequestList.addAll(draftSimpleRequestPage.getOrderDraftSimpleList());
                } else {
                    draftSimpleRequestPage.setBottomFlag(2);
                    draftSimpleRequestList.addAll(signDraftList);
                    draftSimpleRequestList.addAll(draftSimpleRequestPage.getOrderDraftSimpleList());
                    draftSimpleRequestList.addAll(programList);
                }

                draftSimpleRequestPage.setOrderDraftSimpleList(draftSimpleRequestList);
            }
        }
        if (CollectionUtils.isNotEmpty(draftSimpleRequestPage.getOrderDraftSimpleList())) {
            if (request.getOpType().equals(0)) {
                draftSimpleRequestPage.setNeighborProgramNum(draftSimpleRequestPage.getOrderDraftSimpleList().stream().filter(draftSimpleRequest -> draftSimpleRequest.getType().equals(1) && draftSimpleRequest.getSolutionType() != null && !draftSimpleRequest.getSolutionType().equals(1)).count());
                if (request.getQueryType().equals(1) && hasCustomized[0]) {//有定制方案
                    if (draftSimpleRequestPage.getNeighborProgramNum() > 0) {
                        draftSimpleRequestPage.setSolutionBottomVo(solutionBottomList.get(0));
                    } else {
                        draftSimpleRequestPage.setBottomFlag(2);
                        draftSimpleRequestPage.setSolutionBottomVo(solutionBottomList.get(1));
                    }
                    draftSimpleRequestPage.getOrderDraftSimpleList().removeIf(draftSimpleRequest -> draftSimpleRequest.getType().equals(1) && draftSimpleRequest.getSolutionType() != null && !draftSimpleRequest.getSolutionType().equals(1));
                } else if (request.getQueryType().equals(1)) {//无定制方案，复合了领居家户型
                    draftSimpleRequestPage.setSolutionBottomVo(solutionBottomList.get(1));
                } else if (request.getQueryType().equals(2)) {
                    draftSimpleRequestPage.setSolutionBottomVo(solutionBottomList.get(1));
                    draftSimpleRequestPage.getOrderDraftSimpleList().removeIf(draftSimpleRequest -> draftSimpleRequest.getType().equals(2) || (draftSimpleRequest.getSolutionType() != null && draftSimpleRequest.getSolutionType().equals(1)));
                }
            } else {
                draftSimpleRequestPage.getOrderDraftSimpleList().removeIf(draftSimpleRequest -> draftSimpleRequest.getType().equals(2));
            }
        }
        if (orderDetailDto != null) {
            draftSimpleRequestPage.setOrderSubStatus(orderDetailDto.getOrderSubStatus());
            if (((orderDetailDto.getOrderStatus().equals(13) || orderDetailDto.getOrderStatus().equals(14)) && orderDetailDto.getFundAmount().compareTo(BigDecimal.ZERO) > 0) || orderDetailDto.getOrderStatus().equals(15)) {
                draftSimpleRequestPage.setOrderStatus(15);
            } else if (orderDetailDto.getOrderStatus().equals(16)) {
                draftSimpleRequestPage.setOrderStatus(16);
            } else if (orderDetailDto.getOrderStatus().equals(12) || orderDetailDto.getOrderStatus().equals(17) || orderDetailDto.getOrderStatus().equals(2)) {
                draftSimpleRequestPage.setOrderStatus(17);
            } else {
                draftSimpleRequestPage.setOrderStatus(-1);
            }
        }
        if (CollectionUtils.isNotEmpty(draftSimpleRequestPage.getOrderDraftSimpleList())) {
            draftSimpleRequestPage.getOrderDraftSimpleList().forEach(draftSimpleRequest -> {
                draftSimpleRequest.setOrderNum(request.getOrderId());
            });
        }
        StyleRecordResponse styleRecordResponse = programPersonalNeedService.queryStyleRecord(new StyleRecordRequest().setOrderId(request.getOrderId()), null);
        if (styleRecordResponse != null && CollectionUtils.isNotEmpty(styleRecordResponse.getStyleRecordList())) {
            for (PersonalDesignResponse personalDesignResponse : styleRecordResponse.getStyleRecordList()) {
                if (personalDesignResponse.getTaskStatus().equals(DesignTaskAppEnum.IN_DESIGN.getTaskStatus())) {
                    draftSimpleRequestPage.setHasTask(Boolean.TRUE);
                    break;
                }
            }
        }
        return draftSimpleRequestPage;
    }


    @Override
    public SelectionSimpleResponse querySelectedDesign(ProgramOrderDetailRequest request) {
        SolutionSelected solutionOrder = productProgramOrderService.queryOrderSolutionSelectedList(request.getOrderId(), request.getWidth());
        if (solutionOrder == null) {
            return null;
        }
        SelectionSimpleResponse result = JSON.parseObject(JSON.toJSONString(solutionOrder), SelectionSimpleResponse.class);
        SolutionInfo solutionInfo = solutionOrder.getSolutionInfo();
        List<SpaceDesign> spaceDesignSelected = solutionOrder.getSpaceDesignSelected();
        SolutionEffectInfo solutionSelected1 = solutionOrder.getSolutionSelected();
        if (solutionInfo != null) {
            result.setSolutionGraphicDesignUrl(solutionInfo.getSolutionGraphicDesignUrl());
            result.setApartmentUrl(solutionInfo.getApartmentUrl());
            result.setReformApartmentUrl(solutionInfo.getReformApartmentUrl());
        }
        result.setSpaceDesignList(solutionOrder.getSpaceDesignSelected().stream().map(spaceDesign -> {
            SpaceDesignSimple spaceDesignSimple =
                    JSON.parseObject(JSON.toJSONString(spaceDesign), SpaceDesignSimple.class);
            spaceDesignSimple.setSpaceUsageName(spaceDesignSimple.getSpaceUsageName());
            if (CollectionUtils.isNotEmpty(spaceDesignSimple.getOptionalSoftResponseList())) {
                for (OptionalSoftResponse optionalSoftResponse : spaceDesignSimple.getOptionalSoftResponseList()) {
                    optionalSoftResponse.setRoomId(spaceDesignSimple.getSpaceDesignId());
                    optionalSoftResponse.setFurnitureDefault(optionalSoftResponse.getFurnitureSelected());
                    if (optionalSoftResponse.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(optionalSoftResponse.getCabinetBomGroup().getReplaceBomList())) {
                        for (ReplaceBomDto replaceBomDto : optionalSoftResponse.getCabinetBomGroup().getReplaceBomList()) {
                            replaceBomDto.setBomGroupDefault(replaceBomDto.getBomGroupSelect());
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(spaceDesignSimple.getHardItemList())) {
                for (HardItemSimple hardItemSimple : spaceDesignSimple.getHardItemList()) {
                    if (hardItemSimple.getHardItemSelected() != null) {
                        hardItemSimple.getHardItemSelected().setHardProcessList(Lists.newArrayList(hardItemSimple.getHardItemSelected().getProcessSelected()));
                    }
                    hardItemSimple.setHardItemDefault(hardItemSimple.getHardItemSelected());
                    if (hardItemSimple.getCabinetBomGroup() != null && CollectionUtils.isNotEmpty(hardItemSimple.getCabinetBomGroup().getReplaceBomList())) {
                        for (ReplaceBomDto replaceBomDto : hardItemSimple.getCabinetBomGroup().getReplaceBomList()) {
                            replaceBomDto.setBomGroupDefault(replaceBomDto.getBomGroupSelect());
                        }
                    }
                }
            }
            return spaceDesignSimple;
        }).collect(Collectors.toList()));
        return result;
    }

    /**
     * 一键替换接口
     *
     * @param request
     * @return
     */
    @Override
    public ReplaceAbleResponse queryUnifiedReplacement(ReplaceAbleDto request) {
        ReplaceAbleResponse replaceAbleResponse = new ReplaceAbleResponse();
        List<ReplaceAbleResponse.SpaceDesignSelectedBean> spaceDesignSelected = new ArrayList<>();
        ReplaceAbleDollyResponse replaceAbleDollyResponse = productProxy.queryUnifiedReplacement(request);
        if (replaceAbleDollyResponse != null) {
            replaceAbleResponse.setTotalPriceDiff(replaceAbleDollyResponse.getTotalPriceDiff());
            //商品返回数据按照空间id分组
            if (CollectionUtils.isNotEmpty(replaceAbleDollyResponse.getSpaceDesignList())) {

                Map<Integer, List<ReplaceAbleDollyResponse.SpaceDesignListBean>> collect = replaceAbleDollyResponse.getSpaceDesignList().stream().collect(groupingBy(ReplaceAbleDollyResponse.SpaceDesignListBean::getRoomId));
                collect.forEach((integer, spaceDesignList) -> {
                    ReplaceAbleResponse.SpaceDesignSelectedBean spaceDesignSelectedBean = new ReplaceAbleResponse.SpaceDesignSelectedBean();
                    List<ReplaceAbleResponse.SpaceDesignSelectedBean.SoftResponseListBean> softResponseList = new ArrayList<>();
                    spaceDesignSelectedBean.setSpaceDesignId(integer);
                    spaceDesignSelectedBean.setSoftResponseList(softResponseList);

                    for (ReplaceAbleDollyResponse.SpaceDesignListBean spaceDesignListBean : spaceDesignList) {
                        ReplaceAbleResponse.SpaceDesignSelectedBean.SoftResponseListBean softResponseListBean = new ReplaceAbleResponse.SpaceDesignSelectedBean.SoftResponseListBean();
                        softResponseListBean.setSuperKey(spaceDesignListBean.getSuperKey());
                        softResponseListBean.setLastCategoryId(spaceDesignListBean.getLastCategoryId());
                        softResponseListBean.setLastCategoryName(spaceDesignListBean.getLastCategoryName());
                        if (spaceDesignListBean.getFurniture() != null) {//软装
                            ReplaceAbleResponse.SpaceDesignSelectedBean.SoftResponseListBean.FurnitureBean furnitureBean = ModelMapperUtil.strictMap(spaceDesignListBean.getFurniture(),
                                    ReplaceAbleResponse.SpaceDesignSelectedBean.SoftResponseListBean.FurnitureBean.class);
                            furnitureBean.setPriceDiff(spaceDesignListBean.getPriceDiff());
                            furnitureBean.setBrand(spaceDesignListBean.getFurniture().getItemBrand());
                            furnitureBean.setMaterial(spaceDesignListBean.getFurniture().getItemMaterial());
                            furnitureBean.setColor(spaceDesignListBean.getFurniture().getItemColor());
                            furnitureBean.setFurnitureType(2);
                            furnitureBean.setFreeFlag(1);
                            furnitureBean.setFurnitureName(spaceDesignListBean.getFurniture().getItemName());
                            furnitureBean.setImgUrl(AliImageUtil.imageCompress(spaceDesignListBean.getFurniture().getItemImage(), 1, request.getWidth(), ImageConstant.SIZE_SMALL));
                            furnitureBean.setSmallImage(AliImageUtil.imageCompress(spaceDesignListBean.getFurniture().getItemImage(), 1, request.getWidth(), ImageConstant.SIZE_SMALL));
                            furnitureBean.setShowFreeFlag(2);
                            softResponseListBean.setFurnitureSelected(furnitureBean);
                        }
                        if (spaceDesignListBean.getBomGroup() != null) {//bom
                            BomGroupDraftVo bomGroup = spaceDesignListBean.getBomGroup();
                            bomGroup.setPriceDiff(spaceDesignListBean.getPriceDiff());
                            bomGroup.setGroupImage(AliImageUtil.imageCompress(bomGroup.getGroupImage(), 1, request.getWidth(), ImageConstant.SIZE_SMALL));
                            bomGroup.setShowFreeFlag(2);
                            bomGroup.setFreeFlag(1);
                            softResponseListBean.setBomGroupSelected(bomGroup);
                        }
                        if (softResponseListBean.getBomGroupSelected() != null || softResponseListBean.getFurnitureSelected() != null) {
                            softResponseList.add(softResponseListBean);
                        }
                    }
                    spaceDesignSelected.add(spaceDesignSelectedBean);
                });
                replaceAbleResponse.setSpaceDesignSelected(spaceDesignSelected);
            }

        }


        return replaceAbleResponse;
    }

    @Override
    public AvailableChildCategoryResponse queryRelationCategory(Integer categoryId) {
        return productProgramProxy.queryRelationCategory(categoryId);
    }

    /**
     * 是否可进行一键替换
     * 1.用户为定金中、或意向中、或签约中但未签约户型方案
     * 2.用户方案中包括可替换为赠品的类目
     * 3.用户对本专属方案，未进行过任何一次手动或自动的“非赠品 换 赠品”的行为
     *
     * @param request
     * @return
     */
    @Override
    public Boolean queryOnceReplaceOrNot(SolutionListRequest request) {
        //先取缓存
        String qualificationCache = RedisUtil.get(RedisKey.OrderSolution.APP_ONCE_REPLACE_KEY + request.getOrderId() + ":" + request.getSolutionId());
        if (StringUtils.isNotBlank(qualificationCache)) {
            return false;
        }
        Map<String, Object> resultMap = concurrentOnceReplaceOrNotInfo(request);
        OrderDetailDto orderDetailDto = (OrderDetailDto) resultMap.get(ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name());
        SolutionDetailResponseVo solutionDetailResponseVo = (SolutionDetailResponseVo) resultMap.get(ConcurrentTaskEnum.QUERY_PROGRAM_DETAIL_BY_ID.name());
        if (getOrderOnceReplaceStatus(orderDetailDto) && getSolutionOnceReplaceStatus(solutionDetailResponseVo)) {
            return true;
        }
        return false;
    }

    /**
     * 根据项目id查询户型信息
     *
     * @param request
     * @return
     */
    @Override
    public List<AdviserBuildingHouseTypeResponse> queryHouseListByProjectId(HouseProjectRequest request) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        List<AdviserBuildingHouseTypeResponse> list = new ArrayList<>();
        List<HtpHouseTypeResponse> houseList =
                productProgramProxy.queryHouseListByProjectId(request);
        if (CollectionUtils.isNotEmpty(houseList)) {
            houseList.forEach(htpHouseTypeResponse -> {

                String houseTypeName = "";
                if (StringUtils.isNotBlank(htpHouseTypeResponse.getHouseName())) {
                    if (htpHouseTypeResponse.getHouseName().contains(HomeCardPraise.HOUSE_TYPE)) {
                        houseTypeName = htpHouseTypeResponse.getHouseName();
                    } else {
                        houseTypeName = htpHouseTypeResponse.getHouseName() + HomeCardPraise.HOUSE_TYPE;
                    }
                }
                String area = "";
                if (htpHouseTypeResponse.getArea() != null) {
                    BigDecimal houseArea = new BigDecimal(htpHouseTypeResponse.getArea());
                    area = nf.format(houseArea) + ProductProgramPraise.AREA;//户型面积
                } else {
                    area = 0 + ProductProgramPraise.AREA;//户型面积
                }
                list.add(new AdviserBuildingHouseTypeResponse().setHouseTypeId(htpHouseTypeResponse.getHouseId()).setHouseArea(area).setHouseTypeName(houseTypeName));
            });
        }

        return list;
    }

    /**
     * 方案宣传页引导页
     *
     * @param request
     * @return
     */
    public ProgramDetailsGuideNewResponse queryProgramDetailsGuideV2(HttpProgramDetailRequest request) {
        ProgramDetailsGuideNewResponse programDetailsGuideNewResponse = new ProgramDetailsGuideNewResponse();
        SolutionDetailResponseVo solutionDetailResponseVo = productProgramProxy.getProgramDetailById(request.getProgramId());

        if (solutionDetailResponseVo != null && !SolutionStatusEnum.ONLINE.getStatus().equals(solutionDetailResponseVo.getSolutionStatus())) {
            return programDetailsGuideNewResponse;
        }

        if (solutionDetailResponseVo != null) {
            Integer dnaId = null;
            for (SolutionRoomDetailVo solutionRoomDetailVo : solutionDetailResponseVo.getSolutionRoomDetailVoList()) {
                if (solutionRoomDetailVo.getRoomUsageName().equals("客厅")) {
                    dnaId = solutionRoomDetailVo.getDnaId();
                    break;
                }
            }
            if (dnaId != null) {
                DNAInfoResponseVo dnaDetail = homeCardBossProxy.getDnaDetailById(dnaId);
                if (dnaDetail != null) {
                    List<BankCardDto> bankCardDtoList = bankCardDao.getBankCardDetail(request.getUserInfo().getId());
                    programDetailsGuideNewResponse = com.ihomefnt.common.util.JsonUtils.json2obj(com.ihomefnt.common.util.JsonUtils.obj2json(dnaDetail), ProgramDetailsGuideNewResponse.class);
                    if (CollectionUtils.isNotEmpty(bankCardDtoList)) {
                        programDetailsGuideNewResponse.setName(bankCardDtoList.get(0).getName());
                    }
                    if (CollectionUtils.isNotEmpty(dnaDetail.getMaterialDiagramUrl())) {
                        programDetailsGuideNewResponse.setMaterialDiagramUrl(AliImageUtil.imageCompress(dnaDetail.getMaterialDiagramUrl().get(0), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                    }
                    if (CollectionUtils.isNotEmpty(programDetailsGuideNewResponse.getDnaRoomList())) {
                        programDetailsGuideNewResponse.getDnaRoomList().forEach(dnaRoomVo -> {
                            dnaRoomVo.setRoomName(dnaRoomVo.getRoomType());
                            dnaRoomVo.getRoomPictureList().forEach(dnaRoomPictureVo -> {
                                if (dnaRoomPictureVo.getIsFirst() != null && dnaRoomPictureVo.getIsFirst() == 1) {
                                    dnaRoomVo.setRoomPictureUrl(AliImageUtil.imageCompress(dnaRoomPictureVo.getPictureURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                                    dnaRoomPictureVo.setPictureURL(AliImageUtil.imageCompress(dnaRoomPictureVo.getPictureURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                                }
                            });
                        });
                    }
                    if (CollectionUtils.isNotEmpty(programDetailsGuideNewResponse.getProspectPictureList())) {
                        programDetailsGuideNewResponse.getProspectPictureList().forEach(dnaProspectPictureVo -> {
                            dnaProspectPictureVo.setPictureURL(AliImageUtil.imageCompress(dnaProspectPictureVo.getPictureURL(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                        });
                    }
                    programDetailsGuideNewResponse.setDnaVideoPic(AliImageUtil.imageCompress(programDetailsGuideNewResponse.getDnaVideoPic(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                    if (programDetailsGuideNewResponse.getDnaBackgroundPicDto() != null) {
                        programDetailsGuideNewResponse.getDnaBackgroundPicDto().setDnaBackgroundUrl(AliImageUtil.imageCompress(programDetailsGuideNewResponse.getDnaBackgroundPicDto().getDnaBackgroundUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                    } else {
                        programDetailsGuideNewResponse.setDnaBackgroundPic(AliImageUtil.imageCompress(programDetailsGuideNewResponse.getDnaBackgroundPic(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                    }
                    programDetailsGuideNewResponse.setColorDiagramUrl(AliImageUtil.imageCompress(programDetailsGuideNewResponse.getColorDiagramUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_MIDDLE));
                    if (CollectionUtils.isNotEmpty(programDetailsGuideNewResponse.getMaterialDiagramList())) {
                        programDetailsGuideNewResponse.getMaterialDiagramList().forEach(materialDiagramListBean -> {
                            materialDiagramListBean.setMaterialUrl(AliImageUtil.imageCompress(materialDiagramListBean.getMaterialUrl(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL));
                        });
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(programDetailsGuideNewResponse.getColorTextAndBlockList())) {
            programDetailsGuideNewResponse.getColorTextAndBlockList().forEach(colorTextAndBlockListBean -> {
                setWordColor(colorTextAndBlockListBean);
            });
        }
        return programDetailsGuideNewResponse;
    }


    private void setWordColor(DNAInfoResponseCommonVo.ColorTextAndBlockListBean colorTextAndBlockListBean) {
        try {
            String color = colorTextAndBlockListBean.getColor();
            int red = new BigInteger(color.substring(1, 3), 16).intValue();
            int green = new BigInteger(color.substring(3, 5), 16).intValue();
            int blue = new BigInteger(color.substring(5, 7), 16).intValue();
            if (red * 0.299 + green * 0.587 + blue * 0.114 > 186) {
                colorTextAndBlockListBean.setWordColor("#404040");
            } else {
                colorTextAndBlockListBean.setWordColor("#ffffff");
            }
        } catch (Exception e) {
            LOG.error("setWordColor error", e);
        }

    }

    /**
     * 方案中是否包含可替换免费赠品
     *
     * @param solutionDetailResponseVo
     * @return
     */
    private boolean getSolutionOnceReplaceStatus(SolutionDetailResponseVo solutionDetailResponseVo) {
        if (CollectionUtils.isNotEmpty(solutionDetailResponseVo.getSolutionRoomDetailVoList())) {
            for (SolutionRoomDetailVo solutionRoomDetailVo : solutionDetailResponseVo.getSolutionRoomDetailVoList()) {
                if (CollectionUtils.isNotEmpty(solutionRoomDetailVo.getBomGroupList())) {
                    for (BomGroupVO bomGroupVO : solutionRoomDetailVo.getBomGroupList()) {
                        if (bomGroupVO.getFreeAble() != null && bomGroupVO.getFreeAble() == 1) {
                            return true;
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(solutionRoomDetailVo.getSolutionRoomItemVoList())) {
                    for (SolutionRoomItemVo solutionRoomItemVo : solutionRoomDetailVo.getSolutionRoomItemVoList()) {
                        if (solutionRoomItemVo.getFreeAble() != null && solutionRoomItemVo.getFreeAble() == 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    /**
     * 订单状态是否支持一键替换
     *
     * @param orderDetailDto
     * @return
     */
    private boolean getOrderOnceReplaceStatus(OrderDetailDto orderDetailDto) {
        return orderDetailDto != null && orderDetailDto.getOrderStatus() != null &&
                (OrderConstant.ORDER_OMSSTATUS_PURPOSE.equals(orderDetailDto.getOrderStatus()) || OrderConstant.ORDER_OMSSTATUS_HANDSEL.equals(orderDetailDto.getOrderStatus()) ||
                        ((OrderConstant.ORDER_OMSSTATUS_PRE_DELIVERY.equals(orderDetailDto.getOrderStatus()) || OrderConstant.ORDER_OMSSTATUS_SIGN.equals(orderDetailDto.getOrderStatus())) &&
                                orderDetailDto.getOrderSubStatus() != null && orderDetailDto.getOrderSubStatus().equals(161)));//定金中、或意向中、或签约中但未签约户型方案
    }


    /**
     * 识别某用户+方案是否可进行一键替换
     *
     * @param params
     * @return
     */
    private Map<String, Object> concurrentOnceReplaceOrNotInfo(SolutionListRequest request) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return orderProxy.queryOrderSummaryInfo(request.getOrderId());
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name();
            }
        });


        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return productProgramProxy.getProgramDetailById(request.getSolutionId().intValue());
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_PROGRAM_DETAIL_BY_ID.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }
}
