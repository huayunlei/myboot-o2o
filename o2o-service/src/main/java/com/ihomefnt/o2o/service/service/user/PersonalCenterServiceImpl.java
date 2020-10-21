package com.ihomefnt.o2o.service.service.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.agent.dto.PageModel;
import com.ihomefnt.o2o.intf.domain.cart.dto.AjbAccountDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.SolutionDraftResponse;
import com.ihomefnt.o2o.intf.domain.main.dto.DeliverySimpleInfoDto;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderResponse;
import com.ihomefnt.o2o.intf.domain.program.dto.HouseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ContractInfoResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.HouseResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppOrderBaseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.TransactionListVo;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.FamilyOrderPayResponse;
import com.ihomefnt.o2o.intf.domain.right.vo.response.RightsAndOrderResponse;
import com.ihomefnt.o2o.intf.domain.toc.dto.HasOrderDto;
import com.ihomefnt.o2o.intf.domain.toc.dto.UserDistinguishDto;
import com.ihomefnt.o2o.intf.domain.user.dto.*;
import com.ihomefnt.o2o.intf.domain.user.vo.*;
import com.ihomefnt.o2o.intf.domain.user.vo.response.OrderDeliverAndOrderSaleType;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.constant.PersonalCenterConstants;
import com.ihomefnt.o2o.intf.manager.constant.RedisKey;
import com.ihomefnt.o2o.intf.manager.constant.UserRightsForPersonalCenterEnum;
import com.ihomefnt.o2o.intf.manager.constant.home.MasterOrderStatusEnum;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.cart.ShoppingCartProxy;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardWcmProxy;
import com.ihomefnt.o2o.intf.proxy.main.DmsProxy;
import com.ihomefnt.o2o.intf.proxy.user.PersonalCenterProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.home.HomeBuildingService;
import com.ihomefnt.o2o.intf.service.house.HouseService;
import com.ihomefnt.o2o.intf.service.order.ArtOrderService;
import com.ihomefnt.o2o.intf.service.program.ProductProgramService;
import com.ihomefnt.o2o.intf.service.programorder.ProductProgramOrderService;
import com.ihomefnt.o2o.intf.service.toc.TocService;
import com.ihomefnt.o2o.intf.service.user.PersonalCenterService;
import com.ihomefnt.o2o.service.proxy.program.ProductProgramProxyImpl;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 个人中心Service
 *
 * @author liyonggang
 * @create 2019-02-21 09:26
 */
@Service
@SuppressWarnings("all")
public class PersonalCenterServiceImpl implements PersonalCenterService {

    @Autowired
    private PersonalCenterProxy personalCenterProxy;

    @Autowired
    private UserProxy userProxy;

    @Autowired
    private TocService tocService;

    @Autowired
    private ShoppingCartProxy shoppingCartProxy;

    @Autowired
    private DicProxy dicProxy;

    @Autowired
    private ProductProgramOrderService productProgramOrderService;

    @Autowired
    private ProductProgramService productProgramService;

    @Autowired
    private ProductProgramProxyImpl productProgramProxy;

    @Autowired
    private ProductProgramOrderProxyImpl orderProxy;

    @Autowired
    private HomeCardWcmProxy homeCardWcmProxy;

    @Autowired
    private HomeBuildingService homeBuildingService;
    @Autowired
    private HouseService houseService;

    @Autowired
    private DmsProxy dmsProxy;

    public static final Logger LOGGER = LoggerFactory.getLogger(PersonalCenterServiceImpl.class);

    /**
     * 设置房产基础信息
     *
     * @param userId
     * @param list
     */
    private void setHouseInfo(Integer userId, List<PersonalCenterMiddleInfoVo> list) {
        List<HouseInfoResponseVo> houseInfoResponseVos = houseService.queryUserHouseList(userId);//查询用户房产订单信息
        if (CollectionUtils.isNotEmpty(houseInfoResponseVos)) {
            houseInfoResponseVos.forEach(houseInfoResponseVo -> {
                PersonalCenterMiddleInfoVo personalCenterMiddleInfoVo = new PersonalCenterMiddleInfoVo();
                personalCenterMiddleInfoVo.setBuildingName(houseInfoResponseVo.getHouseProjectName());
                personalCenterMiddleInfoVo.setHouseId(houseInfoResponseVo.getId());
                personalCenterMiddleInfoVo.setHouseTypeName(houseInfoResponseVo.getHouseTypeName());
                personalCenterMiddleInfoVo.setHousingNum(houseInfoResponseVo.getHousingNum());
                personalCenterMiddleInfoVo.setOrderId(houseInfoResponseVo.getMasterOrderId());
                personalCenterMiddleInfoVo.setRoomNum(houseInfoResponseVo.getRoomNum());
                personalCenterMiddleInfoVo.setSize(houseInfoResponseVo.getSize());
                personalCenterMiddleInfoVo.setUnitNum(houseInfoResponseVo.getUnitNum());
                personalCenterMiddleInfoVo.setClickCopyWriter("查看");
                personalCenterMiddleInfoVo.setCustomerHouseId(houseInfoResponseVo.getId());
                personalCenterMiddleInfoVo.setHouseInfo(houseInfoResponseVo);
                list.add(personalCenterMiddleInfoVo);
            });
        }
    }

    /**
     * 根据用户id查询用户个人中心款项记录的中间页数据
     *
     * @param userId
     * @return
     */
    @Override
    public List<PersonalCenterMiddleInfoVo> queryFundRecordForPersonalCenter(Integer userId) {
        List<PersonalCenterMiddleInfoVo> result = Lists.newArrayList();
        setHouseInfo(userId, result);
        if (CollectionUtils.isNotEmpty(result)) {
            result.parallelStream().forEach(personalCenterMiddleInfoVo -> {
                PageModel<TransactionListVo> transactionListVoPageModel = orderProxy.queryPayDetailInfoListWithParam(String.valueOf(personalCenterMiddleInfoVo.getOrderId()), null);
                if (transactionListVoPageModel != null && CollectionUtils.isNotEmpty(transactionListVoPageModel.getList())) {
                    personalCenterMiddleInfoVo.setClickCopyWriter(transactionListVoPageModel.getList().size() + "条记录");
                }
            });
        }
        return result;
    }

    /**
     * 根据用户id查询用户个人中心我的合同
     *
     * @param userId
     * @return
     */
    @Override
    public List<PersonalCenterMiddleInfoVo> queryUserContractForPersonalCenter(Integer userId) {
        List<PersonalCenterMiddleInfoVo> result = Lists.newArrayList();
        setHouseInfo(userId, result);
        result.parallelStream().forEach(personalCenterMiddleInfoVo -> {
            //查询合同信息
            List<ContractInfoResponse> contractInfoResponses = productProgramOrderService.queryContractListByOrderId(personalCenterMiddleInfoVo.getOrderId());
            if (CollectionUtils.isNotEmpty(contractInfoResponses)) {
                personalCenterMiddleInfoVo.setClickCopyWriter(contractInfoResponses.size() + "份合同");
                personalCenterMiddleInfoVo.setContractList(contractInfoResponses);
            } else {
                personalCenterMiddleInfoVo.setContractList(Lists.newArrayList());
            }
        });
        return result;
    }


    /**
     * 我的方案列表信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<PersonalCenterMiddleInfoVo> queryUserProgramListForPersonalCenter(Integer userId) {
        List<PersonalCenterMiddleInfoVo> result = Lists.newArrayList();
        setHouseInfo(userId, result);
        return result;
    }


    private SolutionDraftResponse querySolutionDraft(Integer orderId, Integer draftType, BigDecimal draftProgress) {
        if (orderId == null) {
            return null;
        }
        Map<String, Object> params = Maps.newHashMap();
        params.put("orderNum", orderId);
        if (draftProgress != null) {
            params.put("draftProgress", draftProgress);
        }
        if (draftType != null) {
            params.put("draftType", draftType);
        }
        return homeCardWcmProxy.queryOrderDraftByCondition(params);
    }

    /**
     * 根据用户id查询个人中心我的权益
     *
     * @param userId
     * @return
     */
    @Override
    public List<PersonalCenterMiddleInfoVo> queryUserRightsForPersonalCenter(Integer userId, Integer width, String appVersion) {
        if (width == null) {
            return null;
        }
        List<PersonalCenterMiddleInfoVo> result = Lists.newArrayList();
        setHouseInfo(userId, result);
        if (CollectionUtils.isNotEmpty(result)) {
            //批量查询订单信息
            List<HbmsOrderDetailDto> hbmsOrderDetailDtos = personalCenterProxy.batchQueryMasterOrderDetail(result.parallelStream().map(PersonalCenterMiddleInfoVo::getOrderId).collect(Collectors.toList()));
            List<AppMasterOrderResultDto> masterOrderList = personalCenterProxy.queryMasterOrderListByUserId(userId);
            Map<Integer, HbmsOrderDetailDto> hbmsOrderDetailDtoMap = hbmsOrderDetailDtos.parallelStream().collect(Collectors.toMap(HbmsOrderDetailDto::getOrderNum, hbmsOrderDetailDto -> hbmsOrderDetailDto));
            if (CollectionUtils.isNotEmpty(hbmsOrderDetailDtos) && CollectionUtils.isNotEmpty(masterOrderList)) {
                Map<Integer, Integer> collect = masterOrderList.stream().collect(Collectors.toMap(AppMasterOrderResultDto::getMasterOrderId, AppMasterOrderResultDto::getRightsVersion));
                result.parallelStream().forEach(personalCenterMiddleInfoVo -> {
                    personalCenterMiddleInfoVo.setRightsVersion(collect.get(personalCenterMiddleInfoVo.getOrderId()));
                    if (personalCenterMiddleInfoVo.getRightsVersion().equals(4)) {
                        personalCenterMiddleInfoVo.setClickCopyWriter("我的权益");
                    } else {
                    HbmsOrderDetailDto hbmsOrderDetailDto = hbmsOrderDetailDtoMap.get(personalCenterMiddleInfoVo.getOrderId());
                    if (hbmsOrderDetailDto != null) {
                        UserRightsForPersonalCenterEnum enumByGradeId = UserRightsForPersonalCenterEnum.getEnumByGradeId(hbmsOrderDetailDto.getGradeId());
                        if (enumByGradeId != null) {
                            if (enumByGradeId.getGradeId().equals(UserRightsForPersonalCenterEnum.COMMON.getGradeId()) && VersionUtil.mustUpdate(appVersion, "5.4.0")) {
                                if (hbmsOrderDetailDto.getConfirmedAmount().compareTo(BigDecimal.ZERO) > 0) {
                                    personalCenterMiddleInfoVo.setClickCopyWriter(enumByGradeId.getGradeName() + "权益");
                                } else {
                                    personalCenterMiddleInfoVo.setClickCopyWriter("暂无权益");
                                }
                            } else {
                                personalCenterMiddleInfoVo.setClickCopyWriter(enumByGradeId.getGradeName() + "权益");
                            }
                        }
                    }
                }
                });
            }
        }
        return result;
    }

    @Autowired
    private ArtOrderService artOrderService;

    /**
     * 获取个人中心主体数据
     *
     * @param request
     * @return
     */
    @Override
    public PersonalCenterVo queryPersonalCenter(HttpBaseRequest request) {
        UserDto userByToken = userProxy.getUserByToken(request.getAccessToken());
        PersonalCenterVo result;
        if (userByToken != null) {
            //登陆用户处理
            result = handlerShowOrHeddin(getResoureDataGroupByCategory(), userByToken, request.getAppVersion());//处理所有的入口显示与不显示，过滤
            sortPersonalCenterVo(result, Comparator.comparingInt(ResourceVo::getSort));//排序
            setSpecialRightFlag(userByToken.getId(),result);
        } else {
            //未登录用户
            result = getNotLoginData(getResoureDataGroupByCategory(), request.getAppVersion());
        }
        return result;
    }

    /**
     * 2020权益特殊处理
     * @param id
     * @param result
     */
    private void setSpecialRightFlag(Integer id,PersonalCenterVo result){
        try{
            List<OrderResponse> orderResponses = artOrderService.queryMasterOrderList(id);
            if (CollectionUtils.isNotEmpty(orderResponses) && orderResponses.size() == 1
                    && orderResponses.get(0).getAllProductOrder() != null
                    && orderResponses.get(0).getAllProductOrder().getRightsVersion() == 4) {//只有一个订单并且为2020版本权益
                result.getFunctions().getClientNodes().forEach(clientNodes -> {
                    if (clientNodes.getId() == 3) {
                        clientNodes.getClientNodes().forEach(clientNodeSon -> {
                            if (clientNodeSon.getId() == 14) {
                                clientNodeSon.setOnlyRightsVersion(4);
                            }
                        });
                    }
                });
            }
        }catch (Exception e){
            LOGGER.error("setSpecialRightFlag error :",e);
        }

    }

    /**
     * 响应数据排序
     *
     * @param personalCenterVo
     * @param comparator
     */
    private void sortPersonalCenterVo(PersonalCenterVo personalCenterVo, Comparator<ResourceVo> comparator) {
        sortResouce(personalCenterVo.getBrands(), comparator);
        sortResouce(personalCenterVo.getFunctions(), comparator);
        sortResouce(personalCenterVo.getUserCenter(), comparator);
        sortResouce(personalCenterVo.getTopMenu(), comparator);
    }

    /**
     * 资源排序
     */
    private void sortResouce(ResourceVo resourceVo, Comparator<ResourceVo> resourceVoComparator) {
        if (resourceVo != null && CollectionUtils.isNotEmpty(resourceVo.getClientNodes())) {
            resourceVo.getClientNodes().sort(resourceVoComparator);
            resourceVo.getClientNodes().forEach(nodes -> {
                if (nodes.getType().equals(3) && CollectionUtils.isNotEmpty(nodes.getClientNodes())) {//是目录，且有子节点
                    sortResouce(nodes, resourceVoComparator);//递归
                }
            });
        }
    }

    /**
     * 封装入口数据，权限处理+特殊处理
     *
     * @param resoureData
     * @param userByToken
     */
    private PersonalCenterVo handlerShowOrHeddin(Map<String, List<ResourceVo>> resoureData, UserDto userByToken, String appVersion) {
        PersonalCenterVo result = new PersonalCenterVo();
        List<ResourceVo> userCenter = resoureData.get(PersonalCenterConstants.ResouseKey.USER_CENTER);//获取用户中心的几个入口
        List<ResourceVo> fuctionList = resoureData.get(PersonalCenterConstants.ResouseKey.FUCTION_LIST);//获取功能列表的数据
        List<ResourceVo> brand = resoureData.get(PersonalCenterConstants.ResouseKey.BRAND);//获取所有BRAND
        List<ResourceVo> topMenu = resoureData.get(PersonalCenterConstants.ResouseKey.TOP_MENU);
        boolean isOld = tocService.judgeNewOrOldCustomer(userByToken.getId());
        HasOrderDto hasOrderOrNot = tocService.getHasOrderOrNot(userByToken.getId());
        boolean showTocBrand = false;
        if (hasOrderOrNot != null) {
            showTocBrand = hasOrderOrNot.isHasOrder();
        }
        List<String> userRoleList = userByToken.getRoles().parallelStream().map(RoleDto::getCode).collect(Collectors.toList());


        //===============================处理用户中心的入口================================
        if (CollectionUtils.isNotEmpty(userCenter)) {
            handlerPermissionsAndVersioning(userCenter, userRoleList, appVersion);
            handlerUserCenter(userCenter.get(0), isOld, userByToken, hasOrderOrNot.isHasOrder());
            result.setUserCenter(CollectionUtils.isEmpty(userCenter) ? null : userCenter.get(0));
        }
        //==================================处理功能列表,递归========================================
        if (CollectionUtils.isNotEmpty(fuctionList)) {
            handlerPermissionsAndVersioning(fuctionList, userRoleList, appVersion);
            handlerFuctionList(userRoleList, fuctionList.get(0), appVersion);
            result.setFunctions(CollectionUtils.isEmpty(fuctionList) ? null : fuctionList.get(0));
        }

        //===================================处理brand==========================================
        if (CollectionUtils.isNotEmpty(brand)) {
            handlerPermissionsAndVersioning(brand, userRoleList, appVersion);
            ResourceVo brandResouce = brand.get(0);
            if (brandResouce != null && CollectionUtils.isNotEmpty(brandResouce.getClientNodes())) {
                if (!showTocBrand) {
                    //非老用户删除toc推广入口
                    brandResouce.getClientNodes().removeIf(resourceVo -> resourceVo.getKey().equals(PersonalCenterConstants.ResouseKey.TOC));//toc老用户展示推广入口
                }
            }
            result.setBrands(CollectionUtils.isEmpty(brand) ? null : brand.get(0));
        }
        //==================================处理顶部菜单=======================================
        if (CollectionUtils.isNotEmpty(topMenu)) {
            handlerPermissionsAndVersioning(topMenu, userRoleList, appVersion);
            result.setTopMenu(CollectionUtils.isEmpty(topMenu) ? null : topMenu.get(0));
        }
        return result;

    }

    /**
     * 处理用户中心
     *
     * @param userCenter
     * @param isOld
     * @param userVo
     */
    private void handlerUserCenter(ResourceVo userCenter, boolean isOld, UserDto userVo, boolean hasOrder) {
        //已登陆用户我的福利入口需要动态调整
        if (userCenter != null && CollectionUtils.isNotEmpty(userCenter.getClientNodes())) {
            userCenter.getClientNodes().removeIf(item -> {
                //如果是老用户
                if (isOld) {
                    //老用户显示老用户入口，过滤新用户入口
                    return item.getKey().equals(PersonalCenterConstants.ResouseKey.MY_WELFARE_FOR_NEW_HAS_CODE) || item.getKey().equals(PersonalCenterConstants.ResouseKey.MY_WELFARE_FOR_NEW_NO_CODE);
                } else {
                    boolean hasCode = tocService.queryCurrentUserIsUserWithInvitationCode(userVo.getId());
                    if (hasCode) {
                        return item.getKey().equals(PersonalCenterConstants.ResouseKey.MY_WELFARE_FOR_OLD) || item.getKey().equals(PersonalCenterConstants.ResouseKey.MY_WELFARE_FOR_NEW_NO_CODE);
                    } else if (hasOrder) {
                        //有订单并且没有邀请码，过滤掉老带新福利入口
                        return item.getKey().equals(PersonalCenterConstants.ResouseKey.MY_WELFARE_FOR_NEW_HAS_CODE) || item.getKey().equals(PersonalCenterConstants.ResouseKey.MY_WELFARE_FOR_NEW_NO_CODE) || item.getKey().equals(PersonalCenterConstants.ResouseKey.MY_WELFARE_FOR_OLD);
                    } else {
                        //无邀请码
                        return item.getKey().equals(PersonalCenterConstants.ResouseKey.MY_WELFARE_FOR_OLD) || item.getKey().equals(PersonalCenterConstants.ResouseKey.MY_WELFARE_FOR_NEW_HAS_CODE);
                    }
                }
            });
        }
    }

    /**
     * 处理功能列表
     *
     * @param userRoleList
     * @param appVersion
     * @param fuction
     */
    private void handlerFuctionList(List<String> userRoleList, ResourceVo fuction, String appVersion) {
        if (fuction != null && CollectionUtils.isNotEmpty(fuction.getClientNodes())) {
            removeNoPermissionsVersioning(userRoleList, fuction.getClientNodes(), appVersion);//权限处理
            fuction.getClientNodes().forEach(resourceVo -> {
                if (resourceVo.getType().equals(PersonalCenterConstants.ResouseType.CATEGORY) && CollectionUtils.isNotEmpty(resourceVo.getClientNodes())) {//如果是目录，并且有子节点
                    handlerFuctionList(userRoleList, resourceVo, appVersion);//递归
                }
            });
        }
    }

    /**
     * 处理资源的版本和权限控制
     *
     * @param appVersion
     * @param userRoles
     * @param resourceVos
     */
    private void handlerPermissionsAndVersioning(List<ResourceVo> resourceVos, List<String> userRoles, String appVersion) {
        if (CollectionUtils.isNotEmpty(resourceVos)) {
            //资源列表不为空
            removeNoPermissionsVersioning(userRoles, resourceVos, appVersion);//对当前层级进行版本和权限的控制
            if (!resourceVos.isEmpty()) {
                //如果当前层级依然有数据
                resourceVos.forEach(resourceVo -> {
                    if (CollectionUtils.isNotEmpty(resourceVo.getClientNodes())) {
                        //如果有子节点，递归
                        handlerPermissionsAndVersioning(resourceVo.getClientNodes(), userRoles, appVersion);
                    }
                });
            }
        }
    }

    /**
     * 删除资源中用户没有权限的资源
     *
     * @param userRoles
     * @param resourceVos
     */
    private void removeNoPermissionsVersioning(List<String> userRoles, List<ResourceVo> resourceVos, String userAppVersion) {
        resourceVos.removeIf(item -> {
            boolean flag = false;
            boolean versionFilter = checkVersioning(item, userAppVersion);
            if (versionFilter) {
                //版本控制需要过滤
                flag = true;
            }
            boolean permissionFilter = checkPermissions(item, userRoles);
            if (permissionFilter) {
                //权限控制需要过滤
                flag = true;
            }
            return flag;
        });
    }

    /**
     * 权限控制，返回要不要过滤资源，true表示需要过滤，false表示不需要过滤
     */
    private boolean checkPermissions(ResourceVo resourceVo, List<String> userRoles) {
        boolean flag = resourceVo.getRole().equalsIgnoreCase(PersonalCenterConstants.Role.ALL);//角色为所有用户
        if (!flag) {
            //指定角色有权限
            String[] split = resourceVo.getRole().split(",");
            for (String role : split) {
                if (userRoles.contains(role)) {
                    //包含角色
                    flag = true;
                    break;
                }
            }
        }
        return !flag;
    }

    /**
     * 检查版本控制，返回要不要过滤该资源，true表示需要过滤，false表示不需要过滤
     *
     * @param resourceVo
     * @param userAppVersion
     * @return
     */
    private boolean checkVersioning(ResourceVo resourceVo, String userAppVersion) {
        String showVersions = resourceVo.getShowVersions();
        String hideVersions = resourceVo.getHideVersions();
        if (PersonalCenterConstants.Version.ALL.equalsIgnoreCase(showVersions)) {
            //展示的版本为所有
            if (hideVersions != null && !PersonalCenterConstants.Version.NONE.equalsIgnoreCase(hideVersions)) {
                //有单独设置隐藏的版本
                List<String> split = Lists.newArrayList(hideVersions.split(","));
                if (split.contains(userAppVersion)) {
                    //隐藏的版本包含用户的版本，就过滤当前资源
                    return true;
                }
            }
        }
        if (PersonalCenterConstants.Version.ALL.equalsIgnoreCase(hideVersions)) {
            //隐藏版本为所有
            if (showVersions != null && !PersonalCenterConstants.Version.NONE.equalsIgnoreCase(showVersions)) {
                //有单独设置显示的版本
                List<String> split = Lists.newArrayList(showVersions.split(","));
                //显示的版本不包含用户的版本，就过滤当前资源;
                return !split.contains(userAppVersion);
            }
        }
        return false;
    }

    /**
     * 获取redis中的资源数据
     *
     * @return
     */
    private Map<String, List<ResourceVo>> getResoureDataGroupByCategory() {
        String data = AppRedisUtil.get(RedisKey.PersonalCenter.PERSONAL_CENTER_RESOURCE_GROUPBY_CATEGORY);
        Map<String, List<ResourceVo>> dataMap = null;
        if (data == null) {
            //查wcm
            List<ResourceVo> resourceVos = personalCenterProxy.queryPersonalCenterResoureData();
            if (CollectionUtils.isNotEmpty(resourceVos)) {
                dataMap = handlerDataMap(resourceVos.parallelStream().collect(Collectors.groupingBy(ResourceVo::getCategory)));
                dataMap = dataMap.get(PersonalCenterConstants.ResouseKey.TOP).stream().collect(Collectors.groupingBy(ResourceVo::getKey));
                //设置redis
                AppRedisUtil.setPersist(RedisKey.PersonalCenter.PERSONAL_CENTER_RESOURCE_GROUPBY_CATEGORY, JSON.toJSONString(dataMap));
            }
        } else {
            dataMap = JSON.parseObject(data, new TypeReference<Map<String, List<ResourceVo>>>() {
            });
        }
        return dataMap;
    }

    private Map<String, List<ResourceVo>> handlerDataMap(Map<String, List<ResourceVo>> dataMap) {
        Map<String, List<ResourceVo>> result = Maps.newHashMap();
        result.put(PersonalCenterConstants.ResouseKey.TOP, dataMap.get(PersonalCenterConstants.ResouseKey.TOP));
        List<ResourceVo> resourceVos = result.get(PersonalCenterConstants.ResouseKey.TOP);
        if (CollectionUtils.isNotEmpty(resourceVos)) {
            resourceVos.forEach(resourceVo -> {
                if (resourceVo.getType().equals(PersonalCenterConstants.ResouseType.CATEGORY)) {
                    resourceVo.setClientNodes(dataMap.get(resourceVo.getKey()));
                    handlerClientNodes(dataMap, resourceVo);
                }
            });
        }
        return result;
    }

    /**
     * 子节点组装
     *
     * @param dataMap
     * @param targetResource
     */
    private void handlerClientNodes(Map<String, List<ResourceVo>> dataMap, ResourceVo targetResource) {
        if (CollectionUtils.isNotEmpty(targetResource.getClientNodes())) {
            targetResource.getClientNodes().forEach(resourceVo -> {
                if (resourceVo.getType().equals(PersonalCenterConstants.ResouseType.CATEGORY)) {
                    resourceVo.setClientNodes(dataMap.get(resourceVo.getKey()));
                    handlerClientNodes(dataMap, resourceVo);
                }
            });
        }
    }

    private Map<String, ResourceVo> getResoureDataGroupByKey() {
        String data = AppRedisUtil.get(RedisKey.PersonalCenter.PERSONAL_CENTER_RESOURCE_GROUPBY_KEY);
        Map<String, ResourceVo> dataMap = null;
        if (data == null) {
            //查wcm
            List<ResourceVo> resourceVos = personalCenterProxy.queryPersonalCenterResoureData();
            if (CollectionUtils.isNotEmpty(resourceVos)) {
                dataMap = resourceVos.parallelStream().collect(Collectors.toMap(ResourceVo::getKey, resourceVo -> resourceVo));
            }
            //设置redis
            AppRedisUtil.setPersist(RedisKey.PersonalCenter.PERSONAL_CENTER_RESOURCE_GROUPBY_KEY, JSON.toJSONString(dataMap));
        } else {
            dataMap = JSON.parseObject(data, new TypeReference<Map<String, ResourceVo>>() {
            });
        }
        return dataMap;
    }

    /**
     * 获取未登录时的数据
     *
     * @param resoureData
     * @return
     */
    private PersonalCenterVo getNotLoginData(Map<String, List<ResourceVo>> resoureData, String appVersion) {
        PersonalCenterVo personalCenterVo;
        String personalCenterDataForNoLogin = AppRedisUtil.get(RedisKey.PersonalCenter.PERSONAL_CENTER_DATA_NO_LOGIN + appVersion);
        if (StringUtils.isEmpty(personalCenterDataForNoLogin)) {
            personalCenterVo = new PersonalCenterVo();
            List<ResourceVo> userCenter = resoureData.get(PersonalCenterConstants.ResouseKey.USER_CENTER);//获取用户中心的几个入口
            if (CollectionUtils.isNotEmpty(userCenter)) {
                filterMustLoginResoureAndVersioning(userCenter, appVersion);
                personalCenterVo.setUserCenter(userCenter.get(0));
            }
            List<ResourceVo> fuctionList = resoureData.get(PersonalCenterConstants.ResouseKey.FUCTION_LIST);//获取功能列表的数据
            if (CollectionUtils.isNotEmpty(fuctionList)) {
                filterMustLoginResoureAndVersioning(fuctionList, appVersion);
                personalCenterVo.setFunctions(fuctionList.get(0));
            }
            List<ResourceVo> brand = resoureData.get(PersonalCenterConstants.ResouseKey.BRAND);//获取所有BRAND
            if (CollectionUtils.isNotEmpty(brand)) {
                filterMustLoginResoureAndVersioning(brand, appVersion);
                personalCenterVo.setBrands(brand.get(0));
            }
            List<ResourceVo> topMenu = resoureData.get(PersonalCenterConstants.ResouseKey.TOP_MENU);//获取顶部菜单
            if (CollectionUtils.isNotEmpty(topMenu)) {
                filterMustLoginResoureAndVersioning(topMenu, appVersion);
                personalCenterVo.setTopMenu(topMenu.get(0));
            }
            sortPersonalCenterVo(personalCenterVo, Comparator.comparingInt(ResourceVo::getSort));//排序
            AppRedisUtil.setPersist(RedisKey.PersonalCenter.PERSONAL_CENTER_DATA_NO_LOGIN + appVersion, JSON.toJSONString(personalCenterVo));
        } else {
            personalCenterVo = JSON.parseObject(personalCenterDataForNoLogin, PersonalCenterVo.class);
        }
        return personalCenterVo;
    }

    /**
     * 未登录状态下资源处理
     *
     * @param resoureData
     * @return
     */
    private void filterMustLoginResoureAndVersioning(List<ResourceVo> resoureData, String appVersion) {
        resoureData.removeIf(resourceVo -> resourceVo.getMustLogin().equals(1) || checkVersioning(resourceVo, appVersion));//过滤必须登陆才能显示的内容
        if (resoureData.size() > 0) {
            resoureData.forEach(resourceVo -> {
                if (resourceVo.getType().equals(PersonalCenterConstants.ResouseType.CATEGORY) && CollectionUtils.isNotEmpty(resourceVo.getClientNodes())) {
                    //如果是目录，并且有子节点，递归
                    filterMustLoginResoureAndVersioning(resourceVo.getClientNodes(), appVersion);
                }
            });
        }
    }

    /**
     * 获取个人中心角标数据和用户信息
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public PersonalCenterCornerMarkVo queryPersonalCenterCornerMark(HttpBaseRequest request) {
        PersonalCenterCornerMarkVo personalCenterCornerMarkVo = new PersonalCenterCornerMarkVo();
        UserDto userByToken = userProxy.getUserByToken(request.getAccessToken());
        if (userByToken == null) {
            return null;
        }
        Map<String, Object> dataMap = concurrentQueryPersonalCenterCornerMarkNeedData(userByToken.getId());
        AjbAccountDto ajbAccountVo = (AjbAccountDto) dataMap.get(ConcurrentTaskEnum.QUERY_AJBINFO.name());
        //设置基础用户信息
        personalCenterCornerMarkVo.setMobile(userByToken.getMobile());
        if (userByToken.getMember() == null || StringUtils.isBlank(userByToken.getMember().getuImg())) {
            String defaultAvatarImage = AppRedisUtil.get(RedisKey.PersonalCenter.DEFAULT_AVATAR_IMAGE);//查询redis中默认头像
            if (defaultAvatarImage == null) {
                DicDto dicVo = dicProxy.queryDicByKey(PersonalCenterConstants.DicKey.DEFAULT_AVATAR_IMAGE);//查询字典表默认头像
                if (dicVo != null) {
                    defaultAvatarImage = dicVo.getValueDesc();
                    AppRedisUtil.setPersist(PersonalCenterConstants.DicKey.DEFAULT_AVATAR_IMAGE, defaultAvatarImage);//设置缓存
                }
            }
            personalCenterCornerMarkVo.setUserImgUrl(QiniuImageUtils.compressImageAndSamePicTwo(defaultAvatarImage, 120, 120));//默认头像
        } else {
            personalCenterCornerMarkVo.setUserImgUrl(QiniuImageUtils.compressImageAndSamePicTwo(userByToken.getMember().getuImg(), 120, 120));
        }
        if (ajbAccountVo != null && ajbAccountVo.getAmount() != null) {
            //设置艾积分
            personalCenterCornerMarkVo.setAiIntegral(new BigDecimal(ajbAccountVo.getAmount()));
        } else {
            personalCenterCornerMarkVo.setAiIntegral(new BigDecimal(0));
        }

        personalCenterCornerMarkVo.setResourceList(Lists.newArrayList());
        Map<String, ResourceVo> resoureData = getResoureDataGroupByKey();
        UserDistinguishDto userDistinguishDto = (UserDistinguishDto) dataMap.get(ConcurrentTaskEnum.QUERY_USER_IS_NEW_OR_OLD.name());
        //toc的角标
        if (userDistinguishDto != null) {
            if (!userDistinguishDto.isOld()) {
                //新用户
                if (!userDistinguishDto.isBinding()) {
                    //未绑定经纪人(未绑定邀请码)
                    ResourceVo myWelfareForNewNoCode = resoureData.get(PersonalCenterConstants.ResouseKey.MY_WELFARE_FOR_NEW_NO_CODE);
                    if (myWelfareForNewNoCode != null) {
                        personalCenterCornerMarkVo.getResourceList().add(buildCornerMarkVo(myWelfareForNewNoCode.getCornerMarkStyle(), myWelfareForNewNoCode.getCornerMark(), myWelfareForNewNoCode.getId()));
                    }
                } else {
                    personalCenterCornerMarkVo.setAgentMobile(userDistinguishDto.getMobile());
                    //绑定了邀请码
                    if (userDistinguishDto.getResidueNum() != null && userDistinguishDto.getResidueNum() > 0) {
                        //有抽奖次数没用完
                        ResourceVo myWelfareForNewHasCode = resoureData.get(PersonalCenterConstants.ResouseKey.MY_WELFARE_FOR_NEW_HAS_CODE);
                        if (myWelfareForNewHasCode != null) {
                            personalCenterCornerMarkVo.getResourceList().add(buildCornerMarkVo(myWelfareForNewHasCode.getCornerMarkStyle(), myWelfareForNewHasCode.getCornerMark(), myWelfareForNewHasCode.getId()));
                        }
                    }
                }
            }
        }
        //购物车角标
        Integer shoppingCartCount = (Integer) dataMap.get(ConcurrentTaskEnum.QUERY_USER_GOODS_COUNT.name());
        if (shoppingCartCount != null && shoppingCartCount > 0) {
            ResourceVo shoppingCart = resoureData.get(PersonalCenterConstants.ResouseKey.USER_SHOPPING_CART);
            if (shoppingCart != null) {
                personalCenterCornerMarkVo.getResourceList().add(buildCornerMarkVo(shoppingCart.getCornerMarkStyle(), String.valueOf(shoppingCartCount), shoppingCart.getId()));
            }
        }
        //我的权益角标
        ResourceVo myRights = resoureData.get(PersonalCenterConstants.ResouseKey.MY_RIGHTS);
        List<UpgradeInfoDto> upgradeInfoDtoList = (List<UpgradeInfoDto>) dataMap.get(ConcurrentTaskEnum.QUERY_BATCH_UPGRADE_INFO.name());
        if (myRights != null) {
            if (CollectionUtils.isNotEmpty(upgradeInfoDtoList)) {
                for (UpgradeInfoDto upgradeInfoDto : upgradeInfoDtoList) {
                    if (upgradeInfoDto.getUpgradable()) {
                        personalCenterCornerMarkVo.getResourceList().add(buildCornerMarkVo(myRights.getCornerMarkStyle(), myRights.getCornerMark(), myRights.getId()));
                        break;
                    }
                }
            }
        }
        return personalCenterCornerMarkVo;
    }

    private List<HbmsOrderDetailDto> queryUserMasterOrderDetailList(Integer userId) {
        List<HouseInfoResponseVo> houseInfoResponseVos = houseService.queryUserHouseList(userId);
        if (CollectionUtils.isNotEmpty(houseInfoResponseVos)) {
            return personalCenterProxy.batchQueryMasterOrderDetail(houseInfoResponseVos.parallelStream().map(HouseInfoResponseVo::getMasterOrderId).collect(Collectors.toList()));
        }
        return null;
    }

    /**
     * 查询可升级权益
     *
     * @param userId
     * @return
     */
    private List<UpgradeInfoDto> batchUpgradeInfo(Integer userId) {
        List<HouseInfoResponseVo> houseInfoResponseVos = houseService.queryUserHouseList(userId);
        if (CollectionUtils.isNotEmpty(houseInfoResponseVos)) {
            return personalCenterProxy.batchUpgradeInfo(houseInfoResponseVos.parallelStream().map(HouseInfoResponseVo::getMasterOrderId).collect(Collectors.toList()));
        }
        return null;
    }

    /**
     * 个人中心角标所需数据
     *
     * @param userId
     * @return
     */
    private Map<String, Object> concurrentQueryPersonalCenterCornerMarkNeedData(Integer userId) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(4);

        //区分新老用户
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return tocService.queryCurrentUserIsOldOrNew(userId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_USER_IS_NEW_OR_OLD.name();
            }
        });

        // 查询用户购物车
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return shoppingCartProxy.goodsCount(Maps.newHashMap("userId", userId));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_USER_GOODS_COUNT.name();
            }
        });
        // 查询用户艾积分信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return shoppingCartProxy.ajbInfo(Maps.newHashMap("userId", userId));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_AJBINFO.name();
            }
        });
        // 查询用户订单权益信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return batchUpgradeInfo(userId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_BATCH_UPGRADE_INFO.name();
            }
        });


        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 个人中心方案详情
     *
     * @param orderId
     * @param userId
     * @param width
     * @return
     */
    @Override
    public UserProgramForPersonalCenterVo queryUserProgramForPersonalCenter(Integer orderId, Integer userId, Integer osType, Integer width) {
        UserProgramForPersonalCenterVo result = new UserProgramForPersonalCenterVo();
        Map<String, Object> dataMap = concurrentQueryUserProgramForPersonalCenterNeedData(orderId, userId);
        SolutionDraftResponse selectDesignDraft = (SolutionDraftResponse) dataMap.get(ConcurrentTaskEnum.QUERY_SELECTDESIGNDRAFT.name());//方案草稿，最近一次保存草稿的数据
        AppOrderBaseInfoResponseVo orderInfo = (AppOrderBaseInfoResponseVo) dataMap.get(ConcurrentTaskEnum.QUERY_APPORDER_BASEINFO.name());
        HouseResponse houseResponse = (HouseResponse) dataMap.get(ConcurrentTaskEnum.QUERY_HOUSEINFO.name());
        FamilyOrderPayResponse familyOrderPayResponse = (FamilyOrderPayResponse) dataMap.get(ConcurrentTaskEnum.QUERY_PAY_BASEINFO.name());
        if (orderInfo == null) {
            throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.DDATA_GET_FAILED);
        }
        result.setOrderId(orderId);
        result.setAdviserPhone(orderInfo.getAdviserPhone());
        result.setOldOrder(orderInfo.getOldOrder());
        result.setGradeId(orderInfo.getGradeId());
        result.setGradeName(orderInfo.getGradeName());
        result.setUpGradeCouponAmount(orderInfo.getUpGradeCouponAmount());
        result.setHasPayed(orderInfo.getFundAmount() != null && orderInfo.getFundAmount().compareTo(BigDecimal.ZERO) > 0);
        result.setOrderStatus(homeBuildingService.getOrderStatus(orderInfo.getOrderStatus()));
        result.setHouseInfo(houseResponse);
        result.setHasPrimaryProgram(selectDesignDraft != null && StringUtils.isNotBlank(selectDesignDraft.getDraftJsonStr()));
        if (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_COMPLETED.getStatus()) || orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_IN_DELIVERY.getStatus()) || orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_SIGNING_STAGE.getStatus()) || orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_PRE_DELIVERY.getStatus())) {
            result.setPic(AliImageUtil.imageCompress(orderInfo.getSolutionUrl(), osType, width, ImageConstant.SIZE_MIDDLE));
            result.setPreConfirmed(orderInfo.getPreConfirmed());
            result.setSolutionStyleId(orderInfo.getSolutionStyleId());
            result.setSolutionStyleName(orderInfo.getSolutionStyleName());
            result.setTotalPrice(familyOrderPayResponse.getSolutionAmount());
            //不管是代客下单还是app下单，有合同额则视为有提交方案
            if (orderInfo.getContractAmount() == null || orderInfo.getContractAmount().compareTo(BigDecimal.ZERO) <= 0) {
                //无合同额，表示未下单，或者被后台取消
                result.setHasSubmitProgram(false);
            } else {
                //有合同额，已下单
                result.setHasSubmitProgram(true);
            }
        } else {
            result.setHasSubmitProgram(false);
        }
        return result;
    }

    /**
     * 查询用户订单号集合
     *
     * @param userId
     * @return
     */
    @Override
    public List<OrderIdResponse> queryUserOrderIdList(Integer userId) {
        List<AppMasterOrderResultDto> appMasterOrderResultDtos = personalCenterProxy.queryMasterOrderListByUserId(userId);
        if (CollectionUtils.isEmpty(appMasterOrderResultDtos)) {
            return Collections.emptyList();
        }
        return appMasterOrderResultDtos.parallelStream().map(houseInfoResponseVo -> new OrderIdResponse().setOrderId(houseInfoResponseVo.getMasterOrderId())).collect(Collectors.toList());
    }

    /**
     * 根据订单号查权益等级
     *
     * @param orderId
     * @return
     */
    @Override
    @Deprecated
    public Integer queryRightsByOrderId(Integer orderId) {
        List<HbmsOrderDetailDto> hbmsOrderDetailDtos = personalCenterProxy.batchQueryMasterOrderDetail(Lists.newArrayList(orderId));
        if (CollectionUtils.isNotEmpty(hbmsOrderDetailDtos)) {
            return hbmsOrderDetailDtos.get(0).getGradeId();
        }
        return null;
    }

    /**
     * 查询权益和权益可抵扣金额
     *
     * @param orderId
     * @return
     */
    @Override
    public RightsAndOrderResponse queryRightsInfoByOrderId(Integer orderId) {
        BigDecimal upGradeCouponAmount = BigDecimal.ZERO;
        AppOrderBaseInfoResponseVo orderInfo = orderProxy.queryAppOrderBaseInfo(orderId);
        Integer gradeId = null;
        if (orderInfo != null) {
            if (orderInfo.getUpGradeCouponAmount() != null) {
                upGradeCouponAmount = orderInfo.getUpGradeCouponAmount();
            }
            gradeId = orderInfo.getGradeId();
        }
        return new RightsAndOrderResponse(gradeId, upGradeCouponAmount);
    }


    /**
     * 查询已经签约过的订单
     *
     * @param orderId
     * @return
     */
    @Override
    public AppOrderBaseInfoResponseVo queryOrderInfoByOrderId(Integer orderId) {
        AppOrderBaseInfoResponseVo orderInfo = orderProxy.queryAppOrderBaseInfo(orderId);
        if (orderInfo == null) {
            return null;
        }
        //只返回已经签约过的订单
        if (orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_COMPLETED.getStatus()) || orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_IN_DELIVERY.getStatus()) || orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_SIGNING_STAGE.getStatus()) || orderInfo.getOrderStatus().equals(MasterOrderStatusEnum.ORDER_STATUS_PRE_DELIVERY.getStatus())) {
            return orderInfo;
        }
        return null;
    }


    /**
     * 查询订单信息
     *
     * @param orderId
     * @return
     */
    @Override
    public AppOrderBaseInfoResponseVo queryAppOrderBaseInfo(Integer orderId) {
        return orderProxy.queryAppOrderBaseInfo(orderId);
    }

    /**
     * 查询个人中心我的工地数据
     *
     * @param userId
     * @return
     */
    @Override
    public List<PersonalCenterMiddleInfoVo> queryMyWorkSiteForPersonalCenter(Integer userId) {
        List<PersonalCenterMiddleInfoVo> result = Lists.newArrayList();
        setHouseInfo(userId, result);
        if (CollectionUtils.isNotEmpty(result)) {
            result.parallelStream().forEach(personalCenterMiddleInfoVo -> {
                addDeliverAndOrderSaleType(personalCenterMiddleInfoVo);
            });
        }
        return result;
    }

    @Override
    public OrderDeliverAndOrderSaleType queryOrderDeliverAndOrderSaleType(Integer orderId) {
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);

        // 房产信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return orderProxy.queryAppOrderBaseInfo(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name();
            }
        });
        // 查询方案价格所需信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return dmsProxy.getSimpleDeliveryInfo(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SIMPLE_DELIVERY_INFO.name();
            }
        });
        OrderDeliverAndOrderSaleType orderDeliverAndOrderSaleType = new OrderDeliverAndOrderSaleType();
        Map<String, Object> stringObjectMap = Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
        if (stringObjectMap != null) {
            AppOrderBaseInfoResponseVo orderBaseInfoResponseVo = (AppOrderBaseInfoResponseVo) stringObjectMap.get(ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name());
            DeliverySimpleInfoDto deliverySimpleInfoDto = (DeliverySimpleInfoDto) stringObjectMap.get(ConcurrentTaskEnum.QUERY_SIMPLE_DELIVERY_INFO.name());
            if (deliverySimpleInfoDto != null && !deliverySimpleInfoDto.getConfirmFlag() && StringUtils.isNotBlank(deliverySimpleInfoDto.getConfirmDateStr()) && deliverySimpleInfoDto.getDeliverStatus() >= 3) {
                //已确认开工
                orderDeliverAndOrderSaleType.setStartWork(true);
            }
            if (orderBaseInfoResponseVo != null) {
                orderDeliverAndOrderSaleType.setOrderSaleType(orderBaseInfoResponseVo.getOrderSaleType());
            }
        }
        return orderDeliverAndOrderSaleType;
    }


    private HouseResponse getHouseInfoByUserIdAndOrderId(Integer userId, Integer orderId) {
        HouseResponse result = null;
        List<HouseResponse> houseResponses = productProgramService.queryUserHouseList(userId, 2);
        if (CollectionUtils.isNotEmpty(houseResponses)) {
            for (HouseResponse houseInfoResponseVo : houseResponses) {
                if (houseInfoResponseVo.getOrderId().equals(orderId)) {
                    result = houseInfoResponseVo;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 获取方案详情所需数据
     *
     * @param orderId
     * @return
     */
    private Map<String, Object> concurrentQueryUserProgramForPersonalCenterNeedData(Integer orderId, Integer userId) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(4);

        // 查询预选选方案草稿
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return querySolutionDraft(orderId, null, null);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SELECTDESIGNDRAFT.name();
            }
        });

        // 查询订单信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return orderProxy.queryAppOrderBaseInfo(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_APPORDER_BASEINFO.name();
            }
        });
        // 房产信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return getHouseInfoByUserIdAndOrderId(userId, orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_HOUSEINFO.name();
            }
        });
        // 查询方案价格所需信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return productProgramOrderService.queryPayBaseInfo(orderId);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_PAY_BASEINFO.name();
            }
        });
        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    private CornerMarkVo buildCornerMarkVo(String cornerMarkStyle, String cornerMark, Integer resoureId) {
        return new CornerMarkVo().setCornerMark(cornerMark).setCornerMarkStyle(cornerMarkStyle).setId(resoureId);
    }


    void addDeliverAndOrderSaleType(PersonalCenterMiddleInfoVo personalCenterMiddleInfoVo) {
        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);

        // 房产信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return orderProxy.queryAppOrderBaseInfo(personalCenterMiddleInfoVo.getOrderId());
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name();
            }
        });
        // 查询方案价格所需信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return dmsProxy.getSimpleDeliveryInfo(personalCenterMiddleInfoVo.getOrderId());
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_SIMPLE_DELIVERY_INFO.name();
            }
        });
        Map<String, Object> stringObjectMap = Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
        if (stringObjectMap != null) {
            AppOrderBaseInfoResponseVo orderBaseInfoResponseVo = (AppOrderBaseInfoResponseVo) stringObjectMap.get(ConcurrentTaskEnum.QUERY_SOLUTION_INFO.name());
            DeliverySimpleInfoDto deliverySimpleInfoDto = (DeliverySimpleInfoDto) stringObjectMap.get(ConcurrentTaskEnum.QUERY_SIMPLE_DELIVERY_INFO.name());
            if (deliverySimpleInfoDto != null && !deliverySimpleInfoDto.getConfirmFlag() && StringUtils.isNotBlank(deliverySimpleInfoDto.getConfirmDateStr()) && deliverySimpleInfoDto.getDeliverStatus() >= 3) {
                personalCenterMiddleInfoVo.setStartWork(true);
            }
            if (orderBaseInfoResponseVo != null) {
                personalCenterMiddleInfoVo.setOrderSaleType(orderBaseInfoResponseVo.getOrderSaleType());
            }
        }

    }
}
