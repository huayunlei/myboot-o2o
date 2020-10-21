/**
 *
 */
package com.ihomefnt.o2o.service.service.house;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.common.concurrent.TaskProcessManager;
import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.common.util.StringUtil;
import com.ihomefnt.o2o.intf.dao.house.HouseDao;
import com.ihomefnt.o2o.intf.domain.address.dto.AreaDto;
import com.ihomefnt.o2o.intf.domain.address.dto.AreaInfoDto;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.AppHousePropertyResultDto;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.HousePropertyInfoExtResultDto;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.ProjectResponse;
import com.ihomefnt.o2o.intf.domain.house.dto.House;
import com.ihomefnt.o2o.intf.domain.program.dto.HouseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.THouseResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AladdinAdviserInfoVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AladdinHouseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.QueryMasterOrderIdByHouseIdResultDto;
import com.ihomefnt.o2o.intf.domain.user.dto.HousePropertyInfoResultDto;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardBossProxy;
import com.ihomefnt.o2o.intf.proxy.home.HouseProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.service.address.AreaService;
import com.ihomefnt.o2o.intf.service.house.HouseService;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author Administrator
 *
 */
@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseDao houseDao;
    @Autowired
    private HouseProxy houseProxy;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ProductProgramProxy productProgramProxy;
    @Autowired
    private ProductProgramOrderProxyImpl productProgramOrderProxy;
    @Autowired
    private HomeCardBossProxy homeCardBossProxy;

    @Override
    public House queryHouseById(Long houseId) {
        return houseDao.queryHouseById(houseId);
    }

    @Override
    public List<HouseInfoResponseVo> queryUserHouseList(Integer userId) {
        /**
         * 1、根据用户id查询房产信息
         *
         * 2、查询关联的户型信息（批量）
         *
         * 3、查询风格信息（批量）
         *
         * 4、查询系列信息（批量）
         */
        if (null == userId) {
            return new ArrayList<>();
        }

        // 根据用户id查询用户房产列表
        List<AppHousePropertyResultDto> houseResultDtoList = houseProxy.queryHouseByUserId(userId);
        if (null == houseResultDtoList || CollectionUtils.isEmpty(houseResultDtoList)) {
            return new ArrayList<>();
        }

        List<HouseInfoResponseVo> userHouseResultList = new ArrayList<>();
        Set<Integer> houseIdSet = new HashSet<>();// 房产id集合
        Set<Integer> buildingIdSet = new HashSet<>();//楼盘ID集合
        Set<Integer> layoutIdSet = new HashSet<>();//户型ID集合
        for (AppHousePropertyResultDto item : houseResultDtoList) {
            HousePropertyInfoResultDto housePropertyInfoResultDto = item.getHousePropertyInfoResultDto();
            if (null != housePropertyInfoResultDto) {
                houseIdSet.add(housePropertyInfoResultDto.getCustomerHouseId());
                if (housePropertyInfoResultDto.getBuildingId() != null) {
                    buildingIdSet.add(housePropertyInfoResultDto.getBuildingId());
                }
                if (housePropertyInfoResultDto.getLayoutId() != null) {
                    layoutIdSet.add(housePropertyInfoResultDto.getLayoutId());
                }
            }
        }

        //多线程
        List<IdentityTaskAction<Object>> identityTaskActionList = new ArrayList<>();

        // 查询下级区域的父级结构
        identityTaskActionList.add(new IdentityTaskAction() {
            @Override
            public Object doInAction() throws Exception {
                Map<Long, AreaDto> childParentAreaMap = areaService.getChildParentAreaMap();
                if (null == childParentAreaMap) {
                    childParentAreaMap = new HashMap<>();
                }
                return childParentAreaMap;
            }

            @Override
            public String identity() {
                return "getChildParentAreaMap";
            }
        });

        //查询楼盘信息
        identityTaskActionList.add(new IdentityTaskAction() {
            @Override
            public Object doInAction() throws Exception {
                // 封装楼盘map结构
                Map<Long, ProjectResponse> buildingMap = new HashMap<>();
                if (!buildingIdSet.isEmpty()) {
                    //查询楼盘信息
                    List<ProjectResponse> buildingList = houseProxy.queryBuildingListWithIds(new ArrayList<>(buildingIdSet));
                    if (null == buildingList) {
                        buildingList = new ArrayList<>();
                    }
                    for (ProjectResponse item : buildingList) {
                        buildingMap.put(item.getBuildingId(), item);
                    }
                }
                return buildingMap;
            }

            @Override
            public String identity() {
                return "queryBuildingListWithIds";
            }
        });

        //查询房产订单信息
        identityTaskActionList.add(new IdentityTaskAction() {
            @Override
            public Object doInAction() throws Exception {
                Map<Long, QueryMasterOrderIdByHouseIdResultDto> aladdinOrderMap = new HashMap<>();
                if (!houseIdSet.isEmpty()) {
                    List<QueryMasterOrderIdByHouseIdResultDto> orderInfoList = productProgramOrderProxy
                            .queryMasterOrderIdsByHouseIds(new ArrayList<>(houseIdSet));
                    if (orderInfoList == null) {
                        orderInfoList = new ArrayList<>();
                    }
                    for (QueryMasterOrderIdByHouseIdResultDto queryMasterOrderIdByHouseIdResultDto : orderInfoList) {
                        aladdinOrderMap.put(queryMasterOrderIdByHouseIdResultDto.getHouseId(), queryMasterOrderIdByHouseIdResultDto);
                    }
                }
                return aladdinOrderMap;
            }

            @Override
            public String identity() {
                return "queryAladdinOrderInfo";
            }
        });

        //查询房产户型信息
        identityTaskActionList.add(new IdentityTaskAction() {
            @Override
            public Object doInAction() throws Exception {
                Map<Long, THouseResponse> houseTypeMap = new HashMap<>();
                if (!layoutIdSet.isEmpty()) {
                    List<THouseResponse> houseTypeDtos = productProgramProxy.batchQueryHouse(new ArrayList<>(layoutIdSet));
                    if (!CollectionUtils.isEmpty(houseTypeDtos)) {
                        for (THouseResponse houseTypeDto : houseTypeDtos) {
                            houseTypeMap.put(houseTypeDto.getHouseId(), houseTypeDto);
                        }
                    }
                }
                return houseTypeMap;
            }

            @Override
            public String identity() {
                return "queryHouseTypeInfo";
            }
        });

        // 执行
        Map<String, Object> taskResultMap = TaskProcessManager.getTaskProcess()
                .executeIdentityTask(identityTaskActionList);

        Map<Long, AreaDto> childParentAreaMap = (Map<Long, AreaDto>) taskResultMap.get("getChildParentAreaMap");
        Map<Long, ProjectResponse> buildingMap = (Map<Long, ProjectResponse>) taskResultMap.get("queryBuildingListWithIds");
        Map<Long, QueryMasterOrderIdByHouseIdResultDto> aladdinOrderMap = (Map<Long, QueryMasterOrderIdByHouseIdResultDto>) taskResultMap.get("queryAladdinOrderInfo");
        Map<Long, THouseResponse> houseTypeMap = (Map<Long, THouseResponse>) taskResultMap.get("queryHouseTypeInfo");

        for (AppHousePropertyResultDto item : houseResultDtoList) {
            HousePropertyInfoResultDto housePropertyInfoResultDto = item.getHousePropertyInfoResultDto();
            if (null == housePropertyInfoResultDto) {
                continue;
            }

            HouseInfoResponseVo userHouseResultVo = ModelMapperUtil.strictMap(item, HouseInfoResponseVo.class);

            Integer buildingId = housePropertyInfoResultDto.getBuildingId();
            Integer houseId = housePropertyInfoResultDto.getCustomerHouseId();

            userHouseResultVo.setBuildingInfo(housePropertyInfoResultDto.getBuildingInfo());
            userHouseResultVo.setHouseTypeId(housePropertyInfoResultDto.getLayoutId());
            userHouseResultVo.setLayoutId(housePropertyInfoResultDto.getLayoutId());
            userHouseResultVo.setHousingNum(housePropertyInfoResultDto.getHousingNum());
            userHouseResultVo.setId(housePropertyInfoResultDto.getCustomerHouseId());
            userHouseResultVo.setZoneId(housePropertyInfoResultDto.getZoneId());
            userHouseResultVo.setPartitionName(housePropertyInfoResultDto.getPartitionName());
            userHouseResultVo.setLayoutBalcony(housePropertyInfoResultDto.getLayoutBalcony());
            userHouseResultVo.setLayoutCloak(housePropertyInfoResultDto.getLayoutCloak());
            userHouseResultVo.setLayoutKitchen(housePropertyInfoResultDto.getLayoutKitchen());
            userHouseResultVo.setLayoutLiving(housePropertyInfoResultDto.getLayoutLiving());
            userHouseResultVo.setLayoutRoom(housePropertyInfoResultDto.getLayoutRoom());
            userHouseResultVo.setLayoutStorage(housePropertyInfoResultDto.getLayoutStorage());
            userHouseResultVo.setLayoutToliet(housePropertyInfoResultDto.getLayoutToilet());
            userHouseResultVo.setCustomerName(housePropertyInfoResultDto.getCustomerName());
            userHouseResultVo.setUnitNum(housePropertyInfoResultDto.getUnitNum());
            HousePropertyInfoExtResultDto housePropertyInfoExtResultDto = item.getHousePropertyInfoExtResultDto();
            if (housePropertyInfoExtResultDto != null && housePropertyInfoExtResultDto.getDeliverTime() != null) {
                userHouseResultVo.setDeliverTime(housePropertyInfoExtResultDto.getDeliverTime());
            }

            userHouseResultVo.setRoomNum(housePropertyInfoResultDto.getRoomNum());

            if (houseTypeMap != null && userHouseResultVo.getLayoutId() != null) {
                THouseResponse houseTypeDto = houseTypeMap.get(userHouseResultVo.getLayoutId().longValue());
                if (houseTypeDto != null) {
                    userHouseResultVo.setHouseTypeName(houseTypeDto.getHouseName());
                    userHouseResultVo.setSize(String.valueOf(houseTypeDto.getArea()));
                    userHouseResultVo.setLayoutImgUrl(houseTypeDto.getMarkPic());
                    userHouseResultVo.setNormalLayoutPic(houseTypeDto.getNormalPic());
                }
            }

            if (null != aladdinOrderMap && houseId != null) {
                QueryMasterOrderIdByHouseIdResultDto queryMasterOrderIdByHouseIdResultDto = aladdinOrderMap.get(houseId.longValue());
                if (queryMasterOrderIdByHouseIdResultDto != null) {
                    userHouseResultVo.setMasterOrderId(queryMasterOrderIdByHouseIdResultDto.getMasterOrderNum());
                    userHouseResultVo.setMasterOrderStatus(queryMasterOrderIdByHouseIdResultDto.getMasterOrderStatus());
                    userHouseResultVo.setSource(queryMasterOrderIdByHouseIdResultDto.getSource());
                }
            }

            if (null != buildingId) {
                userHouseResultVo.setHouseProjectId(buildingId);

                // 查询楼盘信息
                ProjectResponse projectResponse = buildingMap.get(buildingId.longValue());
                if (null != projectResponse) {
                    userHouseResultVo.setHouseArea(projectResponse.getStreet());
                    userHouseResultVo.setHouseProjectName(projectResponse.getBuildingName());
                    if (null != projectResponse.getFidDistrict()) {
                        userHouseResultVo.setAreaId(Long.valueOf(projectResponse.getFidDistrict()));

                        AreaInfoDto areaInfoDto = areaService.getAreaInfo(Long.valueOf(projectResponse.getFidDistrict()), childParentAreaMap,
                                childParentAreaMap);
                        if (null != areaInfoDto) {
                            userHouseResultVo.setAreaName(areaInfoDto.getAreaName());
                            userHouseResultVo.setCityId(areaInfoDto.getCityId());
                            userHouseResultVo.setCityName(areaInfoDto.getCityName());
                            userHouseResultVo.setProvinceId(areaInfoDto.getProvinceId());
                            userHouseResultVo.setProvinceName(areaInfoDto.getProvinceName());
                        }
                    }
                }
            }

            //toc老带新用户手填信息回显处理
            if (StringUtil.isNotBlank(userHouseResultVo.getBuildingInfo())) {//手填信息不为空
                String buildingInfo = userHouseResultVo.getBuildingInfo();
                JSONObject jasonObject = JSONObject.parseObject(buildingInfo);
                Map map = (Map) jasonObject;
                String type = String.valueOf(map.get("type"));
                if ("2".equals(type)) {//type=2表示手填数据
                    userHouseResultVo.setProvinceName((String) map.get("province"));
                    userHouseResultVo.setCityName((String) map.get("city"));
                    userHouseResultVo.setHouseProjectName((String) map.get("community"));
                    userHouseResultVo.setHousingNum(String.valueOf(map.get("buildNo")));
                    userHouseResultVo.setUnitNum(String.valueOf(map.get("unitNo")));
                    userHouseResultVo.setRoomNum(String.valueOf(map.get("roomNo")));
                    userHouseResultVo.setSize(String.valueOf(map.get("area")));
                } else if ("3".equals(type)) {
                    userHouseResultVo.setHouseProjectId((Integer) map.get("projectId"));
                    if (userHouseResultVo.getHouseProjectId() == null) {
                        userHouseResultVo.setHouseProjectId(housePropertyInfoResultDto.getBuildingId());
                    }
                    if (userHouseResultVo.getHouseProjectName() == null) {
                        userHouseResultVo.setHouseProjectName((String) map.get("projectName"));//项目楼盘名称
                    }
                    if (userHouseResultVo.getHouseTypeId() == null) {
                        userHouseResultVo.setHouseTypeId((Integer) map.get("apartmentId"));//户型id
                    }
                    if (userHouseResultVo.getHouseTypeId() == null) {
                        userHouseResultVo.setHouseTypeId(housePropertyInfoResultDto.getLayoutId());
                    }
                    if (userHouseResultVo.getHouseTypeName() == null) {
                        userHouseResultVo.setHouseTypeName((String) map.get("apartmentName"));//户型名称
                    }
                    if (userHouseResultVo.getLayoutCloak() == null) {
                        userHouseResultVo.setLayoutCloak((Integer) map.get("cloakroom"));//衣帽间
                    }
                    if (userHouseResultVo.getLayoutStorage() == null) {
                        userHouseResultVo.setLayoutStorage((Integer) map.get("storageRoom"));//储物间
                    }
                    if (userHouseResultVo.getLayoutBalcony() == null) {
                        userHouseResultVo.setLayoutBalcony((Integer) map.get("balcony"));//阳台
                    }
                    if (userHouseResultVo.getLayoutToliet() == null) {
                        userHouseResultVo.setLayoutToliet((Integer) map.get("bathroom"));//卫生间
                    }
                    if (userHouseResultVo.getLayoutKitchen() == null) {
                        userHouseResultVo.setLayoutKitchen((Integer) map.get("kitchen"));//厨房
                    }
                    if (userHouseResultVo.getLayoutLiving() == null) {
                        userHouseResultVo.setLayoutLiving((Integer) map.get("livingRoom"));//客厅餐厅
                    }
                    if (userHouseResultVo.getUnitNum() == null) {
                        userHouseResultVo.setUnitNum(String.valueOf(map.get("unitNo")));//单元号
                    }
                    if (userHouseResultVo.getLayoutRoom() == null) {
                        userHouseResultVo.setLayoutRoom((Integer) map.get("room"));//卧室
                    }
                    if (userHouseResultVo.getProvinceName() == null) {
                        userHouseResultVo.setProvinceName((String) map.get("province"));//省份名称
                    }
                    if (userHouseResultVo.getProvinceId() == null) {
                        userHouseResultVo.setProvinceId(Long.parseLong(map.get("provinceId") + ""));//省份id
                    }
                    if (userHouseResultVo.getRoomNum() == null) {
                        userHouseResultVo.setRoomNum(String.valueOf(map.get("roomNo")));//房间号
                    }
                    if (StringUtil.isBlank(userHouseResultVo.getSize())) {
                        userHouseResultVo.setSize(String.valueOf(map.get("area") == null ? "" : map.get("area")));//面积
                    }
                    if (userHouseResultVo.getCityName() == null) {
                        userHouseResultVo.setCityName((String) map.get("city"));//城市名
                    }
                    if (userHouseResultVo.getCityId() == null) {
                        userHouseResultVo.setCityId((Long.parseLong(map.get("cityId") + "")));//城市id
                    }
                    if (userHouseResultVo.getHousingNum() == null) {
                        userHouseResultVo.setHousingNum(String.valueOf(map.get("buildingNo")));//楼栋号
                    }
                }
            }

            userHouseResultList.add(userHouseResultVo);
        }

        return userHouseResultList;
    }

    @Override
    public AladdinHouseInfoResponseVo queryHouseByHouseId(Integer customerHouseId) {
        AppHousePropertyResultDto appHousePropertyResultDto = homeCardBossProxy.queryHouseDetail(customerHouseId);
        if (null == appHousePropertyResultDto) {
            return null;
        }

        HousePropertyInfoResultDto housePropertyInfoResultDto = appHousePropertyResultDto
                .getHousePropertyInfoResultDto();
        HousePropertyInfoExtResultDto houseExt = appHousePropertyResultDto.getHousePropertyInfoExtResultDto();
        if (null == housePropertyInfoResultDto) {
            return null;
        }

        AladdinHouseInfoResponseVo houseFullInfoResultVo = new AladdinHouseInfoResponseVo();
        //客户信息
        houseFullInfoResultVo.setUserInfo(appHousePropertyResultDto.getCustomerBaseResultDto());

        //置家顾问信息
        AladdinAdviserInfoVo adviserInfo = new AladdinAdviserInfoVo();
        if (houseExt != null) {
            adviserInfo.setUserName(houseExt.getAdviserName());
            adviserInfo.setMobile(houseExt.getAdviserMobile());
        }

        HouseInfoResponseVo houseInfo = ModelMapperUtil.strictMap(houseExt, HouseInfoResponseVo.class);

        Integer buildingId = housePropertyInfoResultDto.getBuildingId();
        if (null != buildingId) {
            houseInfo.setHouseProjectId(buildingId);
            setBuildingInfo(housePropertyInfoResultDto, houseExt, houseInfo);

            //款项信息 20180824 APP端此信息已不做展示，故删除

            List<IdentityTaskAction<Object>> identityTaskActionList = new ArrayList<>();

            // 查询户型信息
            Integer layoutId = housePropertyInfoResultDto.getLayoutId();
            if (layoutId != null) {
                identityTaskActionList.add(getUserHouseInfoTask(layoutId, houseInfo));
            }

            //查询订单信息
            identityTaskActionList.add(getOrderInfoTask(housePropertyInfoResultDto.getCustomerHouseId(), houseInfo));

            // 执行
            Map<String, Object> taskResultMap = TaskProcessManager.getTaskProcess()
                    .executeIdentityTask(identityTaskActionList);
        }

        houseFullInfoResultVo.setHouseInfo(houseInfo);
        houseFullInfoResultVo.setAdviserInfoVo(adviserInfo);

        return houseFullInfoResultVo;
    }

    private IdentityTaskAction getOrderInfoTask(Integer houseId, HouseInfoResponseVo houseInfo) {
        return new IdentityTaskAction() {
            @Override
            public Object doInAction() throws Exception {
                return getOrderInfo(houseId, houseInfo);
            }

            @Override
            public String identity() {
                return "getOrderInfo";
            }
        };
    }

    private boolean getOrderInfo(Integer houseId, HouseInfoResponseVo houseInfo) {
        List<QueryMasterOrderIdByHouseIdResultDto> list = productProgramOrderProxy
                .queryMasterOrderIdsByHouseIds(Arrays.asList(houseId));

        if (!CollectionUtils.isEmpty(list)) {
            QueryMasterOrderIdByHouseIdResultDto queryMasterOrderIdByHouseIdResultDto = list.get(0);
            houseInfo.setMasterOrderId(queryMasterOrderIdByHouseIdResultDto.getMasterOrderNum());
            houseInfo.setMasterOrderStatus(queryMasterOrderIdByHouseIdResultDto.getMasterOrderStatus());
        }

        return true;
    }

    private IdentityTaskAction getUserHouseInfoTask(Integer layoutId, HouseInfoResponseVo houseInfo) {
        return new IdentityTaskAction() {
            @Override
            public Object doInAction() throws Exception {
                return getHouseInfo(layoutId, houseInfo);
            }

            @Override
            public String identity() {
                return "getUserHouseInfo";
            }
        };
    }

    private boolean getHouseInfo(Integer layoutId, HouseInfoResponseVo houseInfo) {
        List<THouseResponse> houseTypeDtos = productProgramProxy.batchQueryHouse(Arrays.asList(layoutId));
        if (!CollectionUtils.isEmpty(houseTypeDtos)) {
            THouseResponse houseTypeDto = houseTypeDtos.get(0);
            if (null != houseTypeDto) {
                houseInfo.setHouseTypeName(houseTypeDto.getHouseName());
                houseInfo.setSize(String.valueOf(houseTypeDto.getArea()));
                houseInfo.setLayoutImgUrl(houseTypeDto.getMarkPic());
                houseInfo.setNormalLayoutPic(houseTypeDto.getNormalPic());
            }
        }
        return true;
    }

    private void setBuildingInfo(HousePropertyInfoResultDto housePropertyInfoResultDto, HousePropertyInfoExtResultDto houseExt, HouseInfoResponseVo houseInfo) {
        houseInfo.setHouseProjectName(housePropertyInfoResultDto.getBuildingName());
        houseInfo.setHouseTypeId(housePropertyInfoResultDto.getLayoutId());
        houseInfo.setLayoutId(housePropertyInfoResultDto.getLayoutId());
        houseInfo.setHousingNum(housePropertyInfoResultDto.getHousingNum());
        houseInfo.setId(housePropertyInfoResultDto.getCustomerHouseId());
        houseInfo.setLayoutBalcony(housePropertyInfoResultDto.getLayoutBalcony());
        houseInfo.setLayoutCloak(housePropertyInfoResultDto.getLayoutCloak());
        houseInfo.setLayoutKitchen(housePropertyInfoResultDto.getLayoutKitchen());
        houseInfo.setLayoutLiving(housePropertyInfoResultDto.getLayoutLiving());
        houseInfo.setLayoutRoom(housePropertyInfoResultDto.getLayoutRoom());
        houseInfo.setLayoutStorage(housePropertyInfoResultDto.getLayoutStorage());
        houseInfo.setLayoutToliet(housePropertyInfoResultDto.getLayoutToilet());
        houseInfo.setCustomerName(housePropertyInfoResultDto.getCustomerName());
        houseInfo.setZoneId(housePropertyInfoResultDto.getZoneId());
        houseInfo.setPartitionName(housePropertyInfoResultDto.getPartitionName());

        String buildingInfo = housePropertyInfoResultDto.getBuildingInfo();
        houseInfo.setBuildingInfo(buildingInfo);
        if (StringUtil.isNotBlank(buildingInfo)) {
            JSONObject jasonObject = JSONObject.parseObject(buildingInfo);
            Map map = (Map) jasonObject;
            String type = String.valueOf(map.get("type"));
            if ("2".equals(type)) {//type=2表示手填数据
                houseInfo.setProvinceName(String.valueOf(map.get("province")));
                houseInfo.setCityName(String.valueOf(map.get("city")));
                houseInfo.setHouseProjectName(String.valueOf(map.get("community")));
                houseInfo.setHousingNum(String.valueOf(map.get("buildNo")));
                houseInfo.setUnitNum(String.valueOf(map.get("unitNo")));
                houseInfo.setRoomNum(String.valueOf(map.get("roomNo")));
                houseInfo.setSize(String.valueOf(map.get("area")));
            }
        }

        if (null != houseExt && !StringUtil.isEmpty(houseExt.getDeliverTime())) {
            houseInfo.setDeliverTime(houseExt.getDeliverTime());
        }

        // 设置偏好列表
        houseInfo.setUnitNum(housePropertyInfoResultDto.getUnitNum());
        if (null != houseExt && !StringUtil.isEmpty(houseExt.getStyle())) {
            List<String> styleList = Arrays.asList(houseExt.getStyle().split(","));
            houseInfo.setStyleList(styleList);
        }

        houseInfo.setRoomNum(housePropertyInfoResultDto.getRoomNum());
        if (null != housePropertyInfoResultDto.getFidDistrict()) {
            houseInfo.setAreaId(Long.valueOf(housePropertyInfoResultDto.getFidDistrict()));

            //查询区域信息
            AreaInfoDto areaDto = areaService.getAreaInfo(housePropertyInfoResultDto.getFidDistrict().longValue());
            if (null != areaDto) {
                houseInfo.setAreaName(areaDto.getAreaName());
                houseInfo.setCityId(areaDto.getCityId());
                houseInfo.setCityName(areaDto.getCityName());
                houseInfo.setProvinceId(areaDto.getProvinceId());
                houseInfo.setProvinceName(areaDto.getProvinceName());
            }
        }
    }

}
