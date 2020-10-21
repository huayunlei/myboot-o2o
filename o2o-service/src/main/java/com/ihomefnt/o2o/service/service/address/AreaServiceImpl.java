package com.ihomefnt.o2o.service.service.address;

import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.common.util.StringUtil;
import com.ihomefnt.o2o.intf.domain.address.dto.*;
import com.ihomefnt.o2o.intf.domain.address.vo.response.CityResponseVo;
import com.ihomefnt.o2o.intf.domain.address.vo.response.DistrictResponseVo;
import com.ihomefnt.o2o.intf.domain.address.vo.response.ProvinceResponseVo;
import com.ihomefnt.o2o.intf.proxy.address.AreaProxy;
import com.ihomefnt.o2o.intf.service.address.AreaService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
    AreaProxy areaDao;
    @Resource
    private StringRedisTemplate strRedisTemplate;
	
	@Override
	public AreaDto queryCity(Long areaId) {
		AreaDto area= areaDao.queryArea(areaId);
		if (null != area) {
		    area= areaDao.queryArea(area.getParentId());
		}
		return area;
	}
	
    @Override
    public List<ProvinceResponseVo> queryAddress() {
	    List<ProvinceDto> result = areaDao.queryAddress();

        if(CollectionUtils.isNotEmpty(result)){
            List<ProvinceResponseVo> list = new ArrayList<ProvinceResponseVo>();
            for(ProvinceDto provinceDto:result){
                ProvinceResponseVo province =new ProvinceResponseVo();
                province.setPid(provinceDto.getProvinceId());
                province.setProvinceName(provinceDto.getProvinceName());
                List<CityDto> cityList =provinceDto.getCityList();
                List<CityResponseVo> citys =new ArrayList<CityResponseVo>();
                if(CollectionUtils.isNotEmpty(cityList)){
                    for(CityDto cityDto:cityList){
                        CityResponseVo city = new CityResponseVo();
                        city.setCid(cityDto.getCityId());
                        city.setCityName(cityDto.getCityName());
                        List<DistrictDto> districtList =cityDto.getDistrictList();

                        List<DistrictResponseVo> districts = new ArrayList<DistrictResponseVo>();
                        if(CollectionUtils.isNotEmpty(districtList)){
                            for(DistrictDto districtDto:districtList){
                                DistrictResponseVo district =new DistrictResponseVo();
                                district.setDid(districtDto.getDistrictId());
                                district.setDistrictName(districtDto.getDistrictName());
                                districts.add(district);
                            }
                        }
                        city.setDistrictList(districts);
                        citys.add(city);
                    }
                }
                province.setCityList(citys);
                list.add(province);
            }
            return list;
        }

        return null;
    }

    @Override
    public AreaDto getArea(long areaId) {
        return areaDao.queryArea(areaId);
    }

    /**
     * 查询地区信息exp：江苏南京市雨花区
     * @param areaId
     * @return
     */
    @Override
    public String queryFullAddress(long areaId) {
        return areaDao.queryFullAddress(areaId);
    }

    @Override
    public AreaInfoDto getAreaInfo(Long areaId) {
        String value = strRedisTemplate.opsForValue().get("AREA");
        List<AreaDto> areaDtoList = null;
        if (StringUtil.isNotBlank(value)) {
            areaDtoList = JsonUtils.json2list(value, AreaDto.class);
        } else {
            areaDtoList = areaDao.queryAreaList();
            strRedisTemplate.opsForValue().set("AREA", JsonUtils.obj2json(areaDtoList));
        }

        if (null == areaDtoList) {
            areaDtoList = new ArrayList<>();
        }
        Map<Long, AreaDto> childParentAreaMap = getChildParentAreaMap(areaDtoList);
        if (null == childParentAreaMap) {
            childParentAreaMap = new HashMap<>();
        }

        Map<Long, AreaDto> areaMap = queryAllAreaMap(areaDtoList);
        if (null == areaMap) {
            areaMap = new HashMap<>();
        }

        return getAreaInfo(areaId, childParentAreaMap, areaMap);
    }

    @Override
    public Map<Long, AreaDto> getChildParentAreaMap() {
        String value = strRedisTemplate.opsForValue().get("AREA");
        List<AreaDto> areaDtoList = null;
        if (StringUtil.isNotBlank(value)) {
            areaDtoList = JsonUtils.json2list(value, AreaDto.class);
        } else {
            areaDtoList = areaDao.queryAreaList();
            strRedisTemplate.opsForValue().set("AREA", JsonUtils.obj2json(areaDtoList));
        }

        if (null == areaDtoList) {
            areaDtoList = new ArrayList<>();
        }

        return getChildParentAreaMap(areaDtoList);
    }

    /**
     * 获取区域地址信息
     *
     * @param areaId
     * @param childParentAreaMap
     * @param areaMap
     * @return
     */
    @Override
    public AreaInfoDto getAreaInfo(Long areaId, Map<Long, AreaDto> childParentAreaMap, Map<Long, AreaDto> areaMap) {

        AreaInfoDto areaInfoDto = new AreaInfoDto();

        // 地区信息
        AreaDto areaDto = areaMap.get(areaId);
        if (null != areaDto) {
            areaInfoDto.setAreaId(areaId);
            areaInfoDto.setAreaName(areaDto.getAreaName());
        }

        List<AreaDto> areaParentList = getParentArea(areaId, childParentAreaMap);
        if (null == areaParentList) {
            areaParentList = new ArrayList<>();
        }
        if (2 == areaParentList.size()) {
            // 含省和市
            AreaDto cityArea = areaParentList.get(1);
            if (null != cityArea) {
                areaInfoDto.setCityId(cityArea.getAreaId());
                areaInfoDto.setCityName(cityArea.getAreaName());
            }

            AreaDto provinceArea = areaParentList.get(0);
            if (null != provinceArea) {
                areaInfoDto.setProvinceId(provinceArea.getAreaId());
                areaInfoDto.setProvinceName(provinceArea.getAreaName());
            }
        } else if (1 == areaParentList.size()) {
            // 只含市
            AreaDto cityArea = areaParentList.get(0);
            if (null != cityArea) {
                areaInfoDto.setCityId(cityArea.getAreaId());
                areaInfoDto.setCityName(cityArea.getAreaName());
            }
        }

        return areaInfoDto;
    }

    /**
     * 获取指定地区的父级区域集合
     *
     * @param childId
     * @param childParentAreaMap
     * @return
     */
    public List<AreaDto> getParentArea(Long childId, Map<Long, AreaDto> childParentAreaMap) {

        List<AreaDto> areaList = new ArrayList<>();
        getParentAreaRepeat(areaList, childId, childParentAreaMap);

        return areaList;
    }

    /**
     * 迭代获取父级区域
     *
     * @param areaList
     * @param childId
     * @param childParentAreaMap
     */
    private void getParentAreaRepeat(List<AreaDto> areaList, Long childId, Map<Long, AreaDto> childParentAreaMap) {

        AreaDto area = childParentAreaMap.get(childId);
        if (null != area) {
            areaList.add(0, area);
            getParentAreaRepeat(areaList, area.getAreaId(), childParentAreaMap);
        }
    }

    public Map<Long, AreaDto> queryAllAreaMap(List<AreaDto> areaList) {
        if (null == areaList) {
            areaList = new ArrayList<>();
        }

        /**
         * key : 区域id,value : 区域对象
         */
        Map<Long, AreaDto> areaMap = new HashMap<>();
        for (AreaDto item : areaList) {
            areaMap.put(item.getAreaId(), item);
        }
        return areaMap;
    }

    /**
     * 根据地区列表组装map结构
     *
     * @param areaList
     * @return
     */
    public Map<Long, AreaDto> getChildParentAreaMap(List<AreaDto> areaList) {
        if (null == areaList) {
            areaList = new ArrayList<>();
        }

        /**
         * key : 区域id,value : 区域对象
         */
        Map<Long, AreaDto> areaMap = new HashMap<>();
        for (AreaDto item : areaList) {
            areaMap.put(item.getAreaId(), item);
        }

        /**
         * key ：区域id, value ： 父对象
         */
        Map<Long, AreaDto> childParentMap = new HashMap<>();
        for (AreaDto item : areaList) {
            Long parentId = item.getParentId();
            AreaDto areaParent = areaMap.get(parentId);
            if (null != areaParent) {
                childParentMap.put(item.getAreaId(), areaParent);
            }
        }
        return childParentMap;
    }


}
