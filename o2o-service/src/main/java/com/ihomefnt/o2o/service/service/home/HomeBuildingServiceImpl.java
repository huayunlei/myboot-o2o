package com.ihomefnt.o2o.service.service.home;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.google.common.collect.Lists;
import com.ihomefnt.common.util.StringUtil;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.*;
import com.ihomefnt.o2o.intf.domain.homebuild.vo.request.BuildingAddUpdateRequest;
import com.ihomefnt.o2o.intf.domain.homebuild.vo.response.*;
import com.ihomefnt.o2o.intf.domain.homecard.dto.HouseInfo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.ProductFilterInfo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BasePropertyResponseVo;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.AppSolutionDesignResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.HouseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.ProvinceLocationResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.THouseResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.BuildingHouseInfoResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.HouseResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AddCustomerProjectDto;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AladdinCustomerInfoVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AladdinHouseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.QueryMasterOrderIdByHouseIdResultDto;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.AddCustomerProjectResponseVo;
import com.ihomefnt.o2o.intf.domain.toc.dto.AgentAndCustomerDto;
import com.ihomefnt.o2o.intf.manager.constant.home.AddCustomerResponseEnum;
import com.ihomefnt.o2o.intf.manager.constant.home.HomeCardPraise;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstant;
import com.ihomefnt.o2o.intf.manager.constant.personalneed.DesignTaskSystemEnum;
import com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise;
import com.ihomefnt.o2o.intf.manager.constant.program.StyleEnum;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.designdemand.PersonalNeedProxy;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardBossProxy;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardWcmProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.proxy.toc.TocProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.home.HomeBuildingService;
import com.ihomefnt.o2o.intf.service.house.HouseService;
import com.ihomefnt.o2o.intf.service.program.ProductProgramService;
import com.ihomefnt.o2o.service.manager.config.ApiConfig;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * APP4.0楼盘相关
 * Author: ZHAO
 * Date: 2018年4月12日
 */
@Service
@SuppressWarnings("all")
public class HomeBuildingServiceImpl implements HomeBuildingService {

    @Autowired
    private HomeCardBossProxy homeCardBossProxy;

    @Autowired
    private ProductProgramProxy productProgramProxy;

    @Autowired
    private ProductProgramOrderProxyImpl productProgramOrderProxy;

    @Autowired
    private HomeCardWcmProxy homeCardWcmProxy;

    @Autowired
    private DicProxy dicProxy;

    @Autowired
    private ApiConfig apiConfig;

    @Autowired
    private TocProxy tocProxy;
    @Autowired
    private UserProxy userProxy;
    @Autowired
    private ProductProgramService productProgramService;
    @Autowired
    private HouseService houseService;

    @Autowired
    private PersonalNeedProxy personalNeedProxy;

    @NacosValue(value = "${SYSTEM_CITY_WHITE_LIST}", autoRefreshed = true)
    private String SYSTEM_CITY_WHITE_LIST;

    @NacosValue(value = "${house.pattern.config.range}", autoRefreshed = true)
    private List<Integer> housePatternConfigRange;

    @Override
    public BasePropertyListResponseVo getStyleInfo() {
        List<ProductFilterInfo> styleList = new ArrayList<ProductFilterInfo>();

        List<BasePropertyResponseVo> basePropertyResponse = homeCardBossProxy.getProductFilterInfo();
        for (BasePropertyResponseVo basePropertyResponseVo : basePropertyResponse) {
            //属性类型:1系列 2风格 3空间标识 4空间用途
            if (basePropertyResponseVo.getPropertyType() == 2 && basePropertyResponseVo.getPropertyId() != null && basePropertyResponseVo.getPropertyId() > 0) {
                ProductFilterInfo filterInfo = new ProductFilterInfo();
                filterInfo.setFilterId(basePropertyResponseVo.getPropertyId());
                filterInfo.setFilterName(basePropertyResponseVo.getPropertyName());
                filterInfo.setFilterImgUrl(StyleEnum.getImgUrlByName(basePropertyResponseVo.getPropertyName()));
                styleList.add(filterInfo);
            }
        }

        BasePropertyListResponseVo result = new BasePropertyListResponseVo();
        result.setStyleList(basePropertyResponse);
        return result;
    }

    @Override
    public List<BuildingProvinceResponse> getBuildingInfo() {
        List<BuildingProvinceResponse> responseList = new ArrayList<BuildingProvinceResponse>();

        List<ProvinceLocationResponseVo> locationResponseVos = productProgramProxy.queryLocationInfo();
        if (CollectionUtils.isNotEmpty(locationResponseVos)) {
            boolean searchFlag = isCanSearchTest();

            for (ProvinceLocationResponseVo provinceLocationResponseVo : locationResponseVos) {
                BuildingProvinceResponse buildingProvinceResponse = new BuildingProvinceResponse();
                buildingProvinceResponse.setProvinceId(provinceLocationResponseVo.getProvinceId());
                if (StringUtils.isNotBlank(provinceLocationResponseVo.getProvince())) {
                    buildingProvinceResponse.setProvinceName(provinceLocationResponseVo.getProvince());
                }
                buildingProvinceResponse.setCityList(setCityInfo(provinceLocationResponseVo.getBuildingList(), searchFlag));
                buildingProvinceResponse.getCityList().forEach(buildingCityInfo -> buildingCityInfo.setProvinceName(provinceLocationResponseVo.getProvince()).setProvinceId(provinceLocationResponseVo.getProvinceId()));
                responseList.add(buildingProvinceResponse);
            }
        }

        return responseList;
    }

    /**
     * 按城市分组
     *
     * @param buildingList
     * @return Author: ZHAO
     * Date: 2018年4月13日
     */
    private List<BuildingCityInfo> setCityInfo(List<BuildingLocationInfoVo> buildingList, boolean searchFlag) {
        List<BuildingCityInfo> cityList = new ArrayList<BuildingCityInfo>();
        //1、 按城市分组
        Map<Integer, List<BuildingLocationInfoVo>> cityListMap = new HashMap<Integer, List<BuildingLocationInfoVo>>();
        for (BuildingLocationInfoVo buildingLocationInfoVo : buildingList) {
            if (buildingLocationInfoVo != null && buildingLocationInfoVo.getCityId() != null) {
                if (cityListMap.containsKey(buildingLocationInfoVo.getCityId())) {
                    cityListMap.get(buildingLocationInfoVo.getCityId()).add(buildingLocationInfoVo);
                } else {
                    List<BuildingLocationInfoVo> listMap = new ArrayList<BuildingLocationInfoVo>();
                    listMap.add(buildingLocationInfoVo);
                    cityListMap.put(buildingLocationInfoVo.getCityId(), listMap);
                }
            }
        }

        //2、 将map.entrySet()转换成list
        List<Map.Entry<Integer, List<BuildingLocationInfoVo>>> list = new ArrayList<Map.Entry<Integer, List<BuildingLocationInfoVo>>>(cityListMap.entrySet());

        //3、 遍历分组结果
        for (Map.Entry<Integer, List<BuildingLocationInfoVo>> entry : list) {
            BuildingCityInfo buildingCityInfo = new BuildingCityInfo();
            String cityName = "";
            buildingCityInfo.setCityId(entry.getKey());
            List<BuildingLocationInfoVo> vos = entry.getValue();
            if (CollectionUtils.isNotEmpty(vos)) {
                cityName = vos.get(0).getCity();
                List<BuildingInfo> buildingInfoList = new ArrayList<BuildingInfo>();//楼盘
                for (BuildingLocationInfoVo provinceLocationResponseVo : vos) {
                    if (StringUtils.isNotBlank(provinceLocationResponseVo.getBuildingName())) {
                        BuildingInfo buildingInfo = new BuildingInfo();
                        buildingInfo.setBuildingId(provinceLocationResponseVo.getBuildingId());
                        buildingInfo.setBuildingName(provinceLocationResponseVo.getBuildingName());
                        List<BuildingZoneInfoVo> zoneList = provinceLocationResponseVo.getZoneList();
                        if (CollectionUtils.isNotEmpty(zoneList)) {
                            List<BuildingZoneInfo> zoneListResponse = new ArrayList<BuildingZoneInfo>();//分区
                            for (BuildingZoneInfoVo buildingZoneInfoVo : zoneList) {
                                BuildingZoneInfo buildingZoneInfo = new BuildingZoneInfo();
                                buildingZoneInfo.setZoneId(buildingZoneInfoVo.getZoneId());
                                buildingZoneInfo.setZoneName(buildingZoneInfoVo.getZoneName());
                                zoneListResponse.add(buildingZoneInfo);
                            }
                            buildingInfo.setZoneList(zoneListResponse);
                        }
                        if (searchFlag) {
                            buildingInfoList.add(buildingInfo);
                        } else if (!provinceLocationResponseVo.getBuildingName().contains("测试")) {
                            buildingInfoList.add(buildingInfo);
                        }
                    }
                }
                buildingCityInfo.setBuildingList(buildingInfoList);
            }
            buildingCityInfo.setCityName(cityName);
            cityList.add(buildingCityInfo);
        }
        return cityList;
    }

    /**
     * 设置户型信息
     *
     * @param houseLocationInfoVo
     * @return Author: ZHAO
     * Date: 2018年4月13日
     */
    private BuildingRoomInfo setHouseInfo(HouseLocationInfoVo houseLocationInfoVo) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);

        BuildingRoomInfo buildingLayoutInfo = new BuildingRoomInfo();
        if (houseLocationInfoVo != null) {
            buildingLayoutInfo.setRoomNo(houseLocationInfoVo.getRoomNo());
            buildingLayoutInfo.setHouseTypeId(houseLocationInfoVo.getHouseId());
            if (StringUtils.isNotBlank(houseLocationInfoVo.getLayoutImgUrl())) {
                buildingLayoutInfo.setImgUrl(QiniuImageUtils.compressImageAndSamePicTwo(houseLocationInfoVo.getLayoutImgUrl(), 750, -1));
            }
            if (houseLocationInfoVo.getArea() != null) {
                buildingLayoutInfo.setSize(nf.format(houseLocationInfoVo.getArea()) + ProductProgramPraise.AREA);
            } else {
                buildingLayoutInfo.setSize(0 + ProductProgramPraise.AREA);
            }
            if (StringUtils.isNotBlank(houseLocationInfoVo.getHouseName())) {
                if (houseLocationInfoVo.getHouseName().contains(HomeCardPraise.HOUSE_TYPE)) {
                    buildingLayoutInfo.setHouseTypeName(houseLocationInfoVo.getHouseName());
                } else {
                    buildingLayoutInfo.setHouseTypeName(houseLocationInfoVo.getHouseName() + HomeCardPraise.HOUSE_TYPE);
                }
            }
            //格局
            StringBuffer housePattern = new StringBuffer("");
            if (houseLocationInfoVo.getLayoutRoom() != null && houseLocationInfoVo.getLayoutRoom() > 0) {
                housePattern.append(houseLocationInfoVo.getLayoutRoom().toString()).append(ProductProgramPraise.CHAMBER);
            }
            if (houseLocationInfoVo.getLayoutLiving() != null && houseLocationInfoVo.getLayoutLiving() > 0) {
                housePattern.append(houseLocationInfoVo.getLayoutLiving().toString()).append(ProductProgramPraise.HALL);
            }
            if (houseLocationInfoVo.getLayoutKitchen() != null && houseLocationInfoVo.getLayoutKitchen() > 0) {
                housePattern.append(houseLocationInfoVo.getLayoutKitchen().toString()).append(ProductProgramPraise.KITCHEN);
            }
            if (houseLocationInfoVo.getLayoutToliet() != null && houseLocationInfoVo.getLayoutToliet() > 0) {
                housePattern.append(houseLocationInfoVo.getLayoutToliet().toString()).append(ProductProgramPraise.TOILET);
            }
            if (houseLocationInfoVo.getLayoutBalcony() != null && houseLocationInfoVo.getLayoutBalcony() > 0) {
                housePattern.append(houseLocationInfoVo.getLayoutBalcony().toString()).append(ProductProgramPraise.BALCONY);
            }
            buildingLayoutInfo.setLayout(housePattern.toString());
        }
        return buildingLayoutInfo;
    }

    @Override
    public BuildingHouseInfoResponse addOrUpdateHouseInfo(BuildingAddUpdateRequest request) {
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null || StringUtils.isBlank(userDto.getMobile())) {
            throw new BusinessException(MessageConstant.USER_NOT_LOGIN);
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userDto.getId());
        paramMap.put("customerName", request.getCustomerName());
        paramMap.put("mobile", userDto.getMobile());
        paramMap.put("customerGender", 1);//性别默认1：男
        if (request.getCustomerHouseId() != null) {
            paramMap.put("customerHouseId", request.getCustomerHouseId());
        } else if (request.getHouseId() != null) {
            paramMap.put("customerHouseId", request.getHouseId());
        }
        paramMap.put("buildingId", request.getBuildingId());
        paramMap.put("zoneId", request.getZoneId());
        paramMap.put("layoutId", request.getHouseTypeId());
        if (request.getHandoverDate() != null) {
            paramMap.put("deliverTime", request.getHandoverDate());
        }
        if (StringUtils.isNotBlank(request.getBuildingNo())) {
            paramMap.put("housingNum", request.getBuildingNo());
        }
        if (StringUtils.isNotBlank(request.getUnitNo())) {
            paramMap.put("unitNum", request.getUnitNo());
        }
        if (StringUtils.isNotBlank(request.getRoomNo())) {
            paramMap.put("roomNum", request.getRoomNo());
        }
        if (CollectionUtils.isNotEmpty(request.getStyleIds())) {
            paramMap.put("styleList", request.getStyleIds());
        }
        paramMap.put("buildingInfo", request.getBuildingInfo() == null ? "" : request.getBuildingInfo());
        if (StringUtils.isNotBlank(request.getBuildingInfo())) {
            JSONObject jsonObject = JSON.parseObject(request.getBuildingInfo());
            String type = jsonObject.getString("type");
            if ("3".equals(type)) {
                paramMap.put("originalSource", 21);
                AddCustomerProjectDto addCustomerProjectDto = new AddCustomerProjectDto();
                addCustomerProjectDto.setCloakroom(jsonObject.getInteger("cloakroom") == null ? 0 : jsonObject.getInteger("cloakroom"));
                addCustomerProjectDto.setStorageRoom(jsonObject.getInteger("storageRoom") == null ? 0 : jsonObject.getInteger("storageRoom"));
                addCustomerProjectDto.setBalcony(jsonObject.getInteger("balcony") == null ? 0 : jsonObject.getInteger("balcony"));
                addCustomerProjectDto.setBathroom(jsonObject.getInteger("bathroom") == null ? 0 : jsonObject.getInteger("bathroom"));
                addCustomerProjectDto.setKitchen(jsonObject.getInteger("kitchen") == null ? 0 : jsonObject.getInteger("kitchen"));
                addCustomerProjectDto.setLivingRoom(jsonObject.getInteger("livingRoom") == null ? 0 : jsonObject.getInteger("livingRoom"));
                addCustomerProjectDto.setUnitNo(jsonObject.getInteger("unitNo"));
                addCustomerProjectDto.setRoom(jsonObject.getInteger("room"));
                addCustomerProjectDto.setRoomNo(jsonObject.getInteger("roomNo"));
                addCustomerProjectDto.setProvinceId(jsonObject.getInteger("provinceId"));
                addCustomerProjectDto.setCityId(jsonObject.getInteger("cityId"));
                addCustomerProjectDto.setProjectName(jsonObject.getString("projectName"));
                addCustomerProjectDto.setBuildingNo(jsonObject.getInteger("buildingNo"));
                if (jsonObject.getInteger("area") != null) {
                    addCustomerProjectDto.setArea(jsonObject.getInteger("area"));
                    if (addCustomerProjectDto.getArea() > 500) {
                        throw new BusinessException(HttpResponseCode.PARAMS_VERIFICATION_ERROR, "面积必须在1～500之间的，请确认后重新输入");
                    }
                }
                addCustomerProjectDto.setUserId(request.getUserInfo().getId());
                //创建项目户型信息
                AddCustomerProjectResponseVo addCustomerProjectResponseVo = productProgramOrderProxy.addCustomerProject(addCustomerProjectDto);
                if (addCustomerProjectDto == null) {
                    throw new BusinessException(HttpResponseCode.SERVICE_RESPONSE_SUCCESS_FALSE, MessageConstant.FAILED);
                }
                paramMap.put("housingNum", addCustomerProjectDto.getBuildingNo());
                paramMap.put("unitNum", addCustomerProjectDto.getUnitNo());
                paramMap.put("roomNum", addCustomerProjectDto.getRoomNo());
                jsonObject.put("projectId", addCustomerProjectResponseVo.getProjectId());
                jsonObject.put("apartmentId", addCustomerProjectResponseVo.getApartmentId());
                jsonObject.put("apartmentName", addCustomerProjectResponseVo.getApartmentName());
                paramMap.put("buildingId", addCustomerProjectResponseVo.getProjectId());
                paramMap.put("apartmentId", addCustomerProjectResponseVo.getApartmentId());
                paramMap.put("apartmentName", addCustomerProjectResponseVo.getApartmentName());
                paramMap.put("layoutId", addCustomerProjectResponseVo.getApartmentId());
                paramMap.put("buildingInfo", jsonObject.toJSONString());
            }
        }
        Integer result = productProgramOrderProxy.addCustomerHouseInfo(paramMap);
        if (result == null) {
            throw new BusinessException(AddCustomerResponseEnum.getDescription(0));
        }
        Integer houseId = -1;
        if (request.getHouseId() != null && request.getHouseId() > 0) {
            //记录房产编辑次数
            homeCardWcmProxy.addVisitRecord(request.getHouseId(), HomeCardPraise.HOUSE_EDIT);
            houseId = request.getHouseId();
        } else {
            //新增
            if (result != null) {
                houseId = result;
            }
        }

        BuildingHouseInfoResponse buildingHouseInfoResponse = queryHouseInfoById(houseId);
        return buildingHouseInfoResponse;
    }

    @Override
    public BuildingHouseInfoResponse queryHouseInfoById(Integer customerHouseId) {
        BuildingHouseInfoResponse response = new BuildingHouseInfoResponse();

        AladdinHouseInfoResponseVo aladdinHouseInfoResponseVo = houseService.queryHouseByHouseId(customerHouseId);
        if (aladdinHouseInfoResponseVo != null) {
            //房产户型信息
            HouseInfoResponseVo houseInfo = aladdinHouseInfoResponseVo.getHouseInfo();
            if (houseInfo != null && houseInfo.getId() != null) {
                String buildingInfo = houseInfo.getBuildingInfo();
                if (StringUtils.isNotBlank(buildingInfo)) {
                    JSONObject jsonObject = JSON.parseObject(buildingInfo);
                    String type = jsonObject.getString("type");
                    if ("3".equals(type)) {
                        JSONObject jasonObject = JSONObject.parseObject(buildingInfo);
                        Map map = (Map) jasonObject;
                        houseInfo.setHouseProjectId((Integer) map.get("projectId"));
                        houseInfo.setHouseProjectName((String) map.get("projectName"));//项目楼盘名称
                        houseInfo.setHouseTypeId((Integer) map.get("apartmentId"));//户型id
                        if (StringUtil.isBlank(houseInfo.getHouseTypeName())) {
                            houseInfo.setHouseTypeName((String) map.get("apartmentName"));//户型名称
                        }
                        houseInfo.setLayoutCloak((Integer) map.get("cloakroom"));//衣帽间
                        houseInfo.setLayoutStorage((Integer) map.get("storageRoom"));//储物间
                        houseInfo.setLayoutBalcony((Integer) map.get("balcony"));//阳台
                        houseInfo.setLayoutToliet((Integer) map.get("bathroom"));//卫生间
                        houseInfo.setLayoutKitchen((Integer) map.get("kitchen"));//厨房
                        houseInfo.setLayoutLiving((Integer) map.get("livingRoom"));//客厅餐厅
                        houseInfo.setUnitNum(String.valueOf(map.get("unitNo")));//单元号
                        houseInfo.setLayoutRoom((Integer) map.get("room"));//卧室
                        houseInfo.setProvinceName((String) map.get("province"));//省份名称
                        houseInfo.setProvinceId(Long.parseLong(map.get("provinceId") + ""));//省份id
                        houseInfo.setRoomNum(String.valueOf(map.get("roomNo")));//房间号
                        if (StringUtil.isBlank(houseInfo.getSize())) {
                            houseInfo.setSize(String.valueOf(map.get("area") == null ? "" : map.get("area")));//面积
                        }
                        houseInfo.setCityName((String) map.get("city"));//城市名
                        houseInfo.setCityId((Long.parseLong(map.get("cityId") + "")));//城市id
                        houseInfo.setHousingNum(String.valueOf(map.get("buildingNo")));//楼栋号
                    }
                }
                response.setHouseId(houseInfo.getId());
                response.setCustomerHouseId(houseInfo.getId());
                response.setProvinceId(houseInfo.getProvinceId());
                response.setCityId(houseInfo.getCityId());
                response.setBuildingId(houseInfo.getHouseProjectId());
                response.setBuildingInfo(houseInfo.getBuildingInfo());//加入房产信息
                if (StringUtils.isNotBlank(houseInfo.getProvinceName())) {
                    response.setProvinceName(houseInfo.getProvinceName());
                }
                if (StringUtils.isNotBlank(houseInfo.getCityName())) {
                    response.setCityName(houseInfo.getCityName());
                }
                if (houseInfo.getHouseTypeId() != null) {
                    response.setHouseTypeId(houseInfo.getHouseTypeId());
                }
                if (StringUtils.isNotBlank(houseInfo.getHousingNum())) {
                    response.setBuildingNo(houseInfo.getHousingNum());
                }
                if (StringUtils.isNotBlank(houseInfo.getUnitNum())) {
                    response.setUnitNo(houseInfo.getUnitNum());
                }
                if (StringUtils.isNotBlank(houseInfo.getRoomNum())) {
                    response.setRoomNo(houseInfo.getRoomNum());
                }
                if (houseInfo.getZoneId() != null) {
                    response.setZoneId(houseInfo.getZoneId());
                }
                if (aladdinHouseInfoResponseVo.getUserInfo() != null && aladdinHouseInfoResponseVo.getUserInfo().getUserId() != null) {
                    response.setUserId(aladdinHouseInfoResponseVo.getUserInfo().getUserId());
                }
                if (StringUtils.isNotBlank(houseInfo.getPartitionName())) {
                    response.setZoneName(houseInfo.getPartitionName());
                }
                HouseInfo houseInfoResponse = setHouseInfoStandard(houseInfo);
                response.setBuildingName(houseInfoResponse.getBuildingName());
                response.setHouseTypeName(houseInfoResponse.getHouseTypeName());
                response.setLayout(houseInfoResponse.getHousePattern());
                response.setSize(houseInfoResponse.getSize());

                if (StringUtils.isNotBlank(houseInfo.getDeliverTime())) {
                    response.setHandoverDate(houseInfo.getDeliverTime());
                }
                if (StringUtils.isNotBlank(houseInfo.getLayoutImgUrl())) {
                    response.setImgUrl(AliImageUtil.imageCompress(houseInfo.getLayoutImgUrl(), 2, 750, ImageConstant.SIZE_MIDDLE));
                }
                if (CollectionUtils.isNotEmpty(houseInfo.getStyleList())) {
                    response.setStyleIds(houseInfo.getStyleList());
                }

                //订单状态扭转
                if (houseInfo.getMasterOrderId() != null) {
                    response.setOrderId(houseInfo.getMasterOrderId());
                }
                response.setOrderStatus(getOrderStatus(houseInfo.getMasterOrderStatus()));

                if (houseInfo.getLayoutRoom() != null) {
                    response.setLayoutRoom(houseInfo.getLayoutRoom());
                }

                //查询编辑增加次数
                //20180824去除此逻辑
				/*HouseAddEditCountResponse addEditCountResponse = queryHouseAddEditCount(houseId);
				if(addEditCountResponse != null){
					response.setEditCount(addEditCountResponse.getEditCount());
					response.setRemainEditCount(addEditCountResponse.getRemainEditCount());
				}*/
            }
            List<AppSolutionDesignResponseVo> designResponseVoList = personalNeedProxy.queryDesignDemondForOrderList(Lists.newArrayList(houseInfo.getMasterOrderId()));
            if (CollectionUtils.isNotEmpty(designResponseVoList)) {
                AppSolutionDesignResponseVo appSolutionDesignResponseVo = designResponseVoList.get(0);
                if (!appSolutionDesignResponseVo.getTaskStatus().equals(DesignTaskSystemEnum.INVALID.getTaskStatus()) && !appSolutionDesignResponseVo.getTaskStatus().equals(DesignTaskSystemEnum.CANCELLATION.getTaskStatus()) && !appSolutionDesignResponseVo.getTaskStatus().equals(DesignTaskSystemEnum.DESIGNED.getTaskStatus())) {
                    response.setHasUnderDesignDesignTask(1);
                }
            }
            AladdinCustomerInfoVo userInfo = aladdinHouseInfoResponseVo.getUserInfo();//用户信息
            if (userInfo != null && StringUtils.isNotBlank(userInfo.getName())) {
                response.setCustomerName(userInfo.getName());
            }
            List<QueryMasterOrderIdByHouseIdResultDto> queryMasterOrderIdByHouseIdResultDtos = productProgramOrderProxy.queryMasterOrderIdsByHouseIds(Lists.newArrayList(customerHouseId));
            if (CollectionUtils.isNotEmpty(queryMasterOrderIdByHouseIdResultDtos)) {
                QueryMasterOrderIdByHouseIdResultDto queryMasterOrderIdByHouseIdResultDto = queryMasterOrderIdByHouseIdResultDtos.get(0);
                response.setOrderSubStatus(queryMasterOrderIdByHouseIdResultDto.getOrderSubStatus());
            }
        }

        return response;
    }

    /**
     * 订单状态转义
     *
     * @param orginOrderStatus
     * @return Author: ZHAO
     * Date: 2018年4月16日
     */
    @Override
    public Integer getOrderStatus(Integer orginOrderStatus) {
        Integer orderStatus = -1;
        // 订单状态扭转
        if (orginOrderStatus != null) {
            if (orginOrderStatus == OrderConstant.ORDER_OMSSTATUS_TOUCH) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_TOUCH;// 接触状态
            } else if (orginOrderStatus == OrderConstant.ORDER_OMSSTATUS_PURPOSE) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_PURPOSE;// 意向状态
            } else if (orginOrderStatus == OrderConstant.ORDER_OMSSTATUS_HANDSEL) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_HANDSEL;// 定金状态
            } else if (orginOrderStatus == OrderConstant.ORDER_OMSSTATUS_SIGN) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_SIGN;// 签约状态
            } else if (orginOrderStatus == OrderConstant.ORDER_OMSSTATUS_DELIVERY) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_DELIVERY;// 交付中状态
            } else if (orginOrderStatus == OrderConstant.ORDER_OMSSTATUS_FINISH) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_FINISH;// 已完成状态
            } else if (orginOrderStatus == OrderConstant.ORDER_OMSSTATUS_CANCEL) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_CANCEL;// 已取消状态
            } else if (orginOrderStatus == OrderConstant.ORDER_OMSSTATUS_PRE_DELIVERY) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_SIGN;//  签约状态
            }
        }

        return orderStatus;
    }

    @Override
    public HouseAddEditCountResponse queryHouseAddEditCount(Integer houseId) {
        HouseAddEditCountResponse addEditCountResponse = new HouseAddEditCountResponse();

        //允许编辑总次数
        Integer allEditCount = HomeCardPraise.HOUSE_EDIT_COUNT;
        DicDto dicVo = dicProxy.queryDicByKey("HOUSE_EDIT_COUNT");
        if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
            allEditCount = Integer.parseInt(dicVo.getValueDesc());
        }

        //房产已编辑次数查询
        Integer editCount = 0;
        if (houseId != null) {
            editCount = homeCardWcmProxy.queryVisitCountByDnaId(houseId, HomeCardPraise.HOUSE_EDIT);
        }
        if (editCount == null) {
            editCount = 0;
        }
        Integer remainEditCount = 0;
        if (editCount < allEditCount) {
            remainEditCount = allEditCount - editCount;
        }

        addEditCountResponse.setEditCount(editCount);
        addEditCountResponse.setRemainEditCount(remainEditCount);
        return addEditCountResponse;
    }

    /**
     * 设置房产相关信息
     *
     * @param houseInfoResponseVo
     * @return Author: ZHAO
     * Date: 2018年4月18日
     */
    @Override
    public HouseInfo setHouseInfoStandard(HouseInfoResponseVo houseInfoResponseVo) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);

        HouseInfo houseInfoResponse = new HouseInfo();

        //楼盘名称
        if (StringUtils.isNotBlank(houseInfoResponseVo.getHouseProjectName())) {
            houseInfoResponse.setBuildingName(houseInfoResponseVo.getHouseProjectName().replace(ProductProgramPraise.HOUSE_NAME_BBC, ""));
        }
        //地址：省份城市楼盘
        StringBuffer buildingAddress = new StringBuffer("");
        if (StringUtils.isNotBlank(houseInfoResponseVo.getProvinceName())) {
            buildingAddress.append(houseInfoResponseVo.getProvinceName());
        }
        if (StringUtils.isNotBlank(houseInfoResponseVo.getCityName())) {
            buildingAddress.append(houseInfoResponseVo.getCityName());
        }
        if (StringUtils.isNotBlank(houseInfoResponseVo.getHouseProjectName())) {
            buildingAddress.append(houseInfoResponseVo.getHouseProjectName().replace(ProductProgramPraise.HOUSE_NAME_BBC, ""));
        }
        houseInfoResponse.setBuildingAddress(buildingAddress.toString());
        //户型名称
        if (StringUtils.isNotBlank(houseInfoResponseVo.getHouseTypeName())) {
            if (houseInfoResponseVo.getHouseTypeName().contains(HomeCardPraise.HOUSE_TYPE)) {
                houseInfoResponse.setHouseTypeName(houseInfoResponseVo.getHouseTypeName());
            } else {
                houseInfoResponse.setHouseTypeName(houseInfoResponseVo.getHouseTypeName() + HomeCardPraise.HOUSE_TYPE);
            }
        } else if (houseInfoResponseVo.getHouseTypeId() != null && houseInfoResponseVo.getHouseTypeId().equals(0)) {
            houseInfoResponse.setHouseTypeName(HomeCardPraise.HOUSE_TYPE_ORTHER);
        }
        //楼栋单元房号
        StringBuffer houseRoomNum = new StringBuffer("");
        if (StringUtils.isNotBlank(houseInfoResponseVo.getHousingNum())) {
            houseRoomNum.append(houseInfoResponseVo.getHousingNum());
        }
        if (StringUtils.isNotBlank(houseInfoResponseVo.getUnitNum())) {
            if (StringUtils.isNotBlank(houseRoomNum)) {
                houseRoomNum.append("-");
            }
            houseRoomNum.append(houseInfoResponseVo.getUnitNum());
        }
        if (StringUtils.isNotBlank(houseInfoResponseVo.getRoomNum())) {
            if (StringUtils.isNotBlank(houseRoomNum)) {
                houseRoomNum.append("-");
            }
            houseRoomNum.append(houseInfoResponseVo.getRoomNum());
        }
        houseInfoResponse.setUnitRoomNo(houseRoomNum.toString());
        //格局
        StringBuffer housePattern = new StringBuffer("");
        if (houseInfoResponseVo.getLayoutRoom() != null && houseInfoResponseVo.getLayoutRoom() > 0) {
            housePattern.append(houseInfoResponseVo.getLayoutRoom()).append(ProductProgramPraise.CHAMBER);
        }
        if (houseInfoResponseVo.getLayoutLiving() != null && houseInfoResponseVo.getLayoutLiving() > 0) {
            housePattern.append(houseInfoResponseVo.getLayoutLiving()).append(ProductProgramPraise.HALL);
        }
        if (houseInfoResponseVo.getLayoutKitchen() != null && houseInfoResponseVo.getLayoutKitchen() > 0) {
            housePattern.append(houseInfoResponseVo.getLayoutKitchen()).append(ProductProgramPraise.KITCHEN);
        }
        if (houseInfoResponseVo.getLayoutToliet() != null && houseInfoResponseVo.getLayoutToliet() > 0) {
            housePattern.append(houseInfoResponseVo.getLayoutToliet()).append(ProductProgramPraise.TOILET);
        }
        if (houseInfoResponseVo.getLayoutBalcony() != null && houseInfoResponseVo.getLayoutBalcony() > 0) {
            housePattern.append(houseInfoResponseVo.getLayoutBalcony()).append(ProductProgramPraise.BALCONY);
        }
        houseInfoResponse.setHousePattern(housePattern.toString());
        //面积
        StringBuffer size = new StringBuffer("");
        if (houseInfoResponseVo.getSize() != null) {
            size.append(houseInfoResponseVo.getSize()).append(ProductProgramPraise.AREA);
        } else {
            size.append(0).append(ProductProgramPraise.AREA);
        }
        houseInfoResponse.setSize(size.toString());

        return houseInfoResponse;
    }

    @Override
    public boolean isCanSearchTest() {
        boolean searchFlag = true;//默认可以查询测试楼盘【名称带测试两字的】
        // 只有满足生产环境才为true
        String openTagShow = apiConfig.getOpenTagShow();
        if (openTagShow.equals("true")) {
            DicDto dicVo = dicProxy.queryDicByKey("FILTER_TEST_FLAG");
            if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc()) && dicVo.getValueDesc().equals("true")) {
                //线上环境且是过滤测试楼盘
                searchFlag = false;
            }
        }

        return searchFlag;
    }

    @Override
    public BuildingNoListResponse queryBuildingUnitNoByZoneId(Integer zoneId, Integer width) {
        BuildingNoListResponse response = new BuildingNoListResponse();

        List<BuildingNoResponseVo> buildingNoResponseVos = productProgramProxy.queryBuildingUnitNoByZoneId(zoneId);
        if (CollectionUtils.isNotEmpty(buildingNoResponseVos)) {
            List<BuildingNoInfo> buildingNoInfoList = new ArrayList<BuildingNoInfo>();//楼栋号
            List<BuildingRoomInfo> houseTypeList = new ArrayList<BuildingRoomInfo>();//户型集合
            HashSet<Integer> houseTypeIdList = new HashSet<Integer>();//户型ID集合
            for (BuildingNoResponseVo buildingNoResponseVo : buildingNoResponseVos) {
                BuildingNoInfo buildingNoResponse = new BuildingNoInfo();
                buildingNoResponse.setHandoverDate(buildingNoResponseVo.getExpectedSubmitDate());
                buildingNoResponse.setBuildingNoId(buildingNoResponseVo.getBuildingId());
                buildingNoResponse.setBuildingNo(buildingNoResponseVo.getBuildingName());
                List<BuildingUnitInfoVo> unitVoList = buildingNoResponseVo.getUnitVoList();//单元
                if (CollectionUtils.isNotEmpty(unitVoList)) {
                    List<BuildingUnitInfo> unitList = new ArrayList<BuildingUnitInfo>();//单元集合
                    for (BuildingUnitInfoVo buildingUnitInfoVo : unitVoList) {
                        BuildingUnitInfo buildingUnitInfo = new BuildingUnitInfo();
                        buildingUnitInfo.setUnitNo(buildingUnitInfoVo.getUnitNo());
                        List<HouseLocationInfoVo> roomInfoVos = buildingUnitInfoVo.getRoomInfoVos();//房号信息
                        if (CollectionUtils.isNotEmpty(roomInfoVos)) {
                            List<BuildingRoomInfo> roomNoList = new ArrayList<BuildingRoomInfo>();//房号集合
                            for (HouseLocationInfoVo houseLocationInfoVo : roomInfoVos) {
                                BuildingRoomInfo buildingRoomInfo = setHouseInfo(houseLocationInfoVo);
                                roomNoList.add(buildingRoomInfo);
                                //户型去重
                                if (houseTypeIdList.add(houseLocationInfoVo.getHouseId())) {
                                    houseTypeList.add(buildingRoomInfo);
                                }
                            }
                            buildingUnitInfo.setRoomNoList(roomNoList);
                        }
                        unitList.add(buildingUnitInfo);
                    }
                    buildingNoResponse.setUnitList(unitList);
                }
                buildingNoInfoList.add(buildingNoResponse);
            }
            response.setBuildingNoList(buildingNoInfoList);
            response.setHouseTypeList(houseTypeList);
        }
        return response;
    }

    @Override
    public THouseResponse queryBuildingByLayoutId(Integer layoutId) {
        List<Integer> params = new ArrayList<Integer>();
        params.add(layoutId);
        List<THouseResponse> result = productProgramProxy.batchQueryHouse(params);
        if (CollectionUtils.isNotEmpty(result)) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public List<AgentAndCustomerDto> queryAgentCustomerList(Integer userId) {
        return tocProxy.queryAgentCustomerList(userId);
    }

    @Override
    public UserIsHasCodeNewAndAgentHouseInfoResponse queryUserIsHasCodeNewAndAgentHouseInfo(HttpBaseRequest request) {
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null || userDto.getId() == null || StringUtils.isBlank(userDto.getMobile())) {
            throw new BusinessException(MessageConstant.USER_NOT_LOGIN);
        }

        UserIsHasCodeNewAndAgentHouseInfoResponse response = null;
        if (userDto.getId() != null) {
            //查询用户经纪人列表
            List<AgentAndCustomerDto> agentAndCustomerDtos = this.queryAgentCustomerList(userDto.getId());
            response = new UserIsHasCodeNewAndAgentHouseInfoResponse();
            //判断用户否是有邀请码的用户
            if (CollectionUtils.isNotEmpty(agentAndCustomerDtos)) {
                AgentAndCustomerDto grAnent = null;
                for (AgentAndCustomerDto agentAndCustomerDto : agentAndCustomerDtos) {
                    if (agentAndCustomerDto.getType() == 1) {//这里只是个人经纪人
                        response.setNewUserHasCode(Boolean.TRUE);
                        grAnent = agentAndCustomerDto;
                        break;
                    }
                }
                if (grAnent != null) {
                    //查询经纪人房产列表
                    List<HouseResponse> houseResponses = productProgramService.queryUserHouseList(grAnent.getAgentUserId().intValue(), 2);
                    if (CollectionUtils.isNotEmpty(houseResponses)) {
                        HouseResponse houseResponse = houseResponses.get(0);
                        //查询经纪人房产列表的第一个房产的信息，封装
                        response.setAgentHouseInfo(this.queryHouseInfoById(houseResponse.getCustomerHouseId()));
                    }
                }
            }
        }
        return response;
    }

    @Override
    public BuildingSchemeRecord buildingSchemeRecord(Integer buildingId) {
        return productProgramOrderProxy.buildingSchemeRecord(buildingId);
    }

    @Override
    public List<BuildingCityInfo> getBuildingInfoStartForCity() {
        List<BuildingProvinceResponse> buildingInfo = this.getBuildingInfo();
        List<BuildingProvinceResponse> cityInfoList = JSON.parseArray(SYSTEM_CITY_WHITE_LIST, BuildingProvinceResponse.class);
        List<BuildingCityInfo> result = buildingInfo
                .stream()
                .map(BuildingProvinceResponse::getCityList)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(cityInfoList)) {
            List<BuildingCityInfo> cityWhiteList = cityInfoList
                    .stream()
                    .map(BuildingProvinceResponse::getCityList)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            List<Integer> collect = result
                    .stream()
                    .map(BuildingCityInfo::getCityId)
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(cityWhiteList)) {
                cityWhiteList.forEach(buildingCityInfo -> {
                    if (!collect.contains(buildingCityInfo.getCityId())) {
                        result.add(buildingCityInfo);
                    }
                });
                List<Integer> cityWhiteIdList = cityWhiteList.stream().map(BuildingCityInfo::getCityId).collect(Collectors.toList());
                result.forEach(buildingCityInfo -> {
                    if (cityWhiteIdList.contains(buildingCityInfo.getCityId())) {
                        buildingCityInfo.setCityType(1);
                    }
                });
            }
        }
        return result;
    }

    /**
     * 查询户型格局配置项
     *
     * @return
     */
    @Override
    public HousePatternConfigResponse queryHousePatternConfig() {
        HousePatternConfigResponse housePatternConfigResponse = productProgramOrderProxy.queryHousePatternConfig();
        if (housePatternConfigResponse != null && CollectionUtils.isNotEmpty(housePatternConfigResponse.getConfigList()) && CollectionUtils.isNotEmpty(housePatternConfigRange)) {
            housePatternConfigResponse.getConfigList().removeIf(config -> !housePatternConfigRange.contains(config.getId()));
        }
        return housePatternConfigResponse;
    }

    /**
     * 根据分区查楼栋单元信息
     *
     * @param zoneId
     * @return
     */
    @Override
    public List<BuildingNoInfo> queryBuildingUnitListByZoneId(Integer zoneId) {
        Pattern compile = Pattern.compile("^[0-9]*$");
        List<BuildingNoResponseVo> buildingNoResponseVos = productProgramProxy.queryBuildingUnitNoByZoneId(zoneId);
        if (CollectionUtils.isNotEmpty(buildingNoResponseVos)) {
            List<BuildingNoInfo> collect = buildingNoResponseVos
                    .stream()
                    .map(buildingNoResponseVo -> new BuildingNoInfo()
                            .setBuildingNo(buildingNoResponseVo.getBuildingName())
                            .setBuildingNoId(buildingNoResponseVo.getBuildingId())
                            .setHandoverDate(buildingNoResponseVo.getExpectedSubmitDate())
                            .setUnitNoList(CollectionUtils.isNotEmpty(buildingNoResponseVo.getUnitVoList())
                                    ? buildingNoResponseVo.getUnitVoList()
                                    .stream()
                                    .map(buildingUnitInfoVo -> buildingUnitInfoVo.getUnitNo())
                                    .collect(Collectors.toList())
                                    : Collections.emptyList()))
                    .collect(Collectors.toList());

            Map<Boolean, List<BuildingNoInfo>> collectMap = collect.stream().collect(Collectors.groupingBy(buildingNoInfo -> compile.matcher(buildingNoInfo.getBuildingNo()).matches()));
            List<BuildingNoInfo> sortNum = collectMap.get(Boolean.TRUE);
            List<BuildingNoInfo> sortNotNum = collectMap.get(Boolean.FALSE);
            if (CollectionUtils.isNotEmpty(sortNum)) {
                sortNum.sort((o1, o2) -> Integer.parseInt(o1.getBuildingNo()) - Integer.parseInt(o2.getBuildingNo()));
            }
            if (CollectionUtils.isNotEmpty(sortNotNum)) {
                sortNotNum.sort((o1, o2) -> o1.getBuildingNo().compareTo(o2.getBuildingNo()));
            }
            if (sortNum != null && CollectionUtils.isNotEmpty(sortNotNum)) {
                sortNum.addAll(sortNotNum);
            }
            if (CollectionUtils.isEmpty(sortNum)) {
                sortNum = sortNotNum;
            } else {
                sortNum.forEach(buildingNoInfo -> {
                    if (CollectionUtils.isNotEmpty(buildingNoInfo.getUnitNoList())) {
                        buildingNoInfo.getUnitNoList().sort((o1, o2) -> Integer.parseInt(o1) - Integer.parseInt(o2));
                    }
                });
            }
            return sortNum;
        }
        return Collections.emptyList();
    }

    /**
     * 根据分区，楼栋，单元查房间
     *
     * @param zoneId
     * @param buildingId
     * @return
     */
    @Override
    public List<BuildingRoomInfo> queryRoomListByZoneIdAndBuildingIdAndUnitId(Integer zoneId, Integer buildingId, String unitNo) {
        List<BuildingNoResponseVo> buildingNoResponseVos = productProgramProxy.queryBuildingUnitNoByZoneId(zoneId);
        if (CollectionUtils.isNotEmpty(buildingNoResponseVos)) {
            for (BuildingNoResponseVo buildingNoResponseVo : buildingNoResponseVos) {
                if (buildingId.equals(buildingNoResponseVo.getBuildingId()) && CollectionUtils.isNotEmpty(buildingNoResponseVo.getUnitVoList())) {
                    for (BuildingUnitInfoVo buildingUnitInfoVo : buildingNoResponseVo.getUnitVoList()) {
                        if (unitNo.equals(buildingUnitInfoVo.getUnitNo()) && CollectionUtils.isNotEmpty(buildingUnitInfoVo.getRoomInfoVos())) {
                            List<BuildingRoomInfo> collect = buildingUnitInfoVo.getRoomInfoVos().stream().map(houseLocationInfoVo -> this.setHouseInfo(houseLocationInfoVo)).collect(Collectors.toList());
                            collect.sort((o1, o2) -> Integer.parseInt(o1.getRoomNo()) - Integer.parseInt(o2.getRoomNo()));
                            return collect;
                        }
                    }
                }
            }
        }
        return Collections.emptyList();
    }
}
