package com.ihomefnt.o2o.service.service.controlcap;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ihomefnt.o2o.intf.domain.controlcap.vo.request.AccessOptionsByBuildingRequestVo;
import com.ihomefnt.o2o.intf.domain.controlcap.vo.response.ProgramListForHouseTypeResponseVo;
import com.ihomefnt.o2o.intf.domain.homebuild.vo.response.BuildingProvinceResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.request.HttpProductProgramRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.AdviserBuildingHouseTypeResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.AdviserBuildingProgramResponse;
import com.ihomefnt.o2o.intf.proxy.controlcap.ControlCapService;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.service.home.HomeBuildingService;
import com.ihomefnt.o2o.intf.service.program.ProductProgramService;
import com.ihomefnt.o2o.service.proxy.controlcap.ControlCapProxyImpl;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingInfo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地推
 *
 * @author liyonggang
 * @create 2018-11-28 10:59
 */
@Service
public class ControlCapServiceImpl implements ControlCapService {
    @Autowired
    private HomeBuildingService homeBuildingService;
    @Autowired
    private ProductProgramProxy productProgramProxy;
    @Autowired
    private ProductProgramService productProgramService;
    @Autowired
    private ControlCapProxyImpl controlCapProxy;

    public static final Logger LOGGER = LoggerFactory.getLogger(ControlCapServiceImpl.class);

    /**
     * 根据楼盘id获取分区,楼栋,单元,房号
     *
     * @return
     */
    @Override
    public BuildingInfo getAccessOptionsByBuildingId(Integer buildingId) {
        if (buildingId == null) {
            return null;
        }
        List<BuildingProvinceResponse> buildingInfo = homeBuildingService.getBuildingInfo();
        BuildingInfo result = new BuildingInfo();
        //从省市区楼盘分区中取出指定楼盘和分区
        if (CollectionUtils.isNotEmpty(buildingInfo)) {
            buildingInfo.parallelStream().forEach(buildingProvinceResponse -> {
                if (CollectionUtils.isNotEmpty(buildingProvinceResponse.getCityList())) {
                    buildingProvinceResponse.getCityList().parallelStream().forEach(buildingCityInfo -> {
                        if (CollectionUtils.isNotEmpty(buildingCityInfo.getBuildingList())) {
                            buildingCityInfo.getBuildingList().parallelStream().forEach(buildingInfos -> {
                                if (buildingInfos.getBuildingId().equals(buildingId) && CollectionUtils.isNotEmpty(buildingInfos.getZoneList())) {
                                    BeanUtils.copyProperties(buildingInfos, result);
                                }
                            });
                        }
                    });
                }
            });
        }
        //查询每个分区的楼栋等信息
        if (CollectionUtils.isNotEmpty(result.getZoneList())) {
            result.getZoneList().parallelStream().forEach(buildingZoneInfo -> buildingZoneInfo.setBuildingNo(productProgramProxy.queryBuildingUnitNoByZoneId(buildingZoneInfo.getZoneId())));
        }
        return result;
    }

    /**
     * 根据户型id获取当前户型方案列表
     *
     * @return
     */
    @Override
    public ProgramListForHouseTypeResponseVo getProgramListByHouseIdAndBuildingIdAndZoneId(AccessOptionsByBuildingRequestVo request) {
        HttpProductProgramRequest httpProductProgramRequest = new HttpProductProgramRequest();
        httpProductProgramRequest.setHouseProjectId(request.getBuildingId());
        httpProductProgramRequest.setHouseTypeId(request.getHouseTypeId());
        httpProductProgramRequest.setZoneId(request.getZoneId());
        httpProductProgramRequest.setSolutionName(request.getSolutionName());
        //根据楼盘和分区查询方案休信息
        AdviserBuildingProgramResponse adviserBuildingProgramResponse = productProgramService.queryProgramListByBuildingId(httpProductProgramRequest, request.getWidth());
        ProgramListForHouseTypeResponseVo programListForHouseTypeResponse = new ProgramListForHouseTypeResponseVo();
        programListForHouseTypeResponse.setProgramList(Lists.newArrayList());
        List<AdviserBuildingHouseTypeResponse> houseTypeList = adviserBuildingProgramResponse.getHouseTypeList();
        programListForHouseTypeResponse.setBuildingId(request.getBuildingId());
        programListForHouseTypeResponse.setBuildingName(adviserBuildingProgramResponse.getBuildingName());
        programListForHouseTypeResponse.setZoneId(request.getZoneId());
        programListForHouseTypeResponse.setHouseTypeId(request.getHouseTypeId());
        //检索指定户型的所有方案并合并
        for (AdviserBuildingHouseTypeResponse adviserBuildingHouseTypeResponse : houseTypeList) {
            if (adviserBuildingHouseTypeResponse.getHouseTypeId().equals(request.getHouseTypeId())) {
                programListForHouseTypeResponse.setHouseTypeName(adviserBuildingHouseTypeResponse.getHouseTypeName());
                if (CollectionUtils.isNotEmpty(adviserBuildingHouseTypeResponse.getSeriesProgramList())) {
                    adviserBuildingHouseTypeResponse.getSeriesProgramList().forEach(seriesProgramListResponse -> {
                        if (CollectionUtils.isNotEmpty(seriesProgramListResponse.getProgramList())) {
                            programListForHouseTypeResponse.getProgramList().addAll(seriesProgramListResponse.getProgramList());
                        }
                    });
                }
            }
        }
        int result = controlCapProxy.addCustomerInfo(request.getCustomerInfoDto());
        if (result != 1) {
            LOGGER.error("customer add error params {}", JSON.toJSONString(request.getCustomerInfoDto()));
        }
        return programListForHouseTypeResponse;
    }

    /**
     * 根据户型id获取其他户型方案列表
     *
     * @return
     */
    @Override
    public List<ProgramListForHouseTypeResponseVo> getProgramListByNotHouseTypeAndBuildingIdAndZoneId(AccessOptionsByBuildingRequestVo request) {

        HttpProductProgramRequest httpProductProgramRequest = new HttpProductProgramRequest();
        httpProductProgramRequest.setHouseProjectId(request.getBuildingId());
        httpProductProgramRequest.setHouseTypeId(request.getHouseTypeId());
        httpProductProgramRequest.setZoneId(request.getZoneId());
        httpProductProgramRequest.setSolutionName(request.getSolutionName());
        //根据楼盘和分区查询方案休信息
        AdviserBuildingProgramResponse adviserBuildingProgramResponse = productProgramService.queryProgramListByBuildingId(httpProductProgramRequest, request.getWidth());
        List<ProgramListForHouseTypeResponseVo> programListForHouseTypeResponseList = Lists.newArrayList();
        List<AdviserBuildingHouseTypeResponse> houseTypeList = adviserBuildingProgramResponse.getHouseTypeList();
        //检索非指定户型的所有方案并合并
        for (AdviserBuildingHouseTypeResponse adviserBuildingHouseTypeResponse : houseTypeList) {
            if (!adviserBuildingHouseTypeResponse.getHouseTypeId().equals(request.getHouseTypeId())) {
                ProgramListForHouseTypeResponseVo programListForHouseTypeResponse = new ProgramListForHouseTypeResponseVo();
                programListForHouseTypeResponse.setProgramList(Lists.newArrayList());
                programListForHouseTypeResponse.setBuildingId(request.getBuildingId());
                programListForHouseTypeResponse.setBuildingName(adviserBuildingProgramResponse.getBuildingName());
                programListForHouseTypeResponse.setZoneId(request.getZoneId());
                programListForHouseTypeResponse.setHouseTypeId(adviserBuildingHouseTypeResponse.getHouseTypeId());
                programListForHouseTypeResponse.setHouseTypeName(adviserBuildingHouseTypeResponse.getHouseTypeName());
                if (CollectionUtils.isNotEmpty(adviserBuildingHouseTypeResponse.getSeriesProgramList())) {
                    adviserBuildingHouseTypeResponse.getSeriesProgramList().forEach(seriesProgramListResponse -> {
                        if (CollectionUtils.isNotEmpty(seriesProgramListResponse.getProgramList())) {
                            programListForHouseTypeResponse.getProgramList().addAll(seriesProgramListResponse.getProgramList());
                        }
                    });
                }
                programListForHouseTypeResponseList.add(programListForHouseTypeResponse);
            }
        }
        return programListForHouseTypeResponseList;
    }
}
