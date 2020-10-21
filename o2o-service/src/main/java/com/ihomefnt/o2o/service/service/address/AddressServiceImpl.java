package com.ihomefnt.o2o.service.service.address;

import com.ihomefnt.o2o.intf.domain.address.dto.AreaDto;
import com.ihomefnt.o2o.intf.domain.address.dto.UserAddressAddParamDto;
import com.ihomefnt.o2o.intf.domain.address.dto.UserAddressResultDto;
import com.ihomefnt.o2o.intf.domain.address.dto.UserAddressUpdateParamDto;
import com.ihomefnt.o2o.intf.domain.address.vo.request.QueryReceiveAddressRequestVo;
import com.ihomefnt.o2o.intf.domain.address.vo.request.ReceiveAddressRequestVo;
import com.ihomefnt.o2o.intf.domain.address.vo.request.SelReceiveAddressRequestVo;
import com.ihomefnt.o2o.intf.domain.address.vo.response.*;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.address.AddressProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.address.AddressService;
import com.ihomefnt.o2o.intf.service.address.AreaService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private UserProxy userProxy;
    @Autowired
    private AddressProxy addressProxy;
    @Autowired
    private AreaService areaService;

    @Override
    public ReceiveAddressResponseVo queryAddress(QueryReceiveAddressRequestVo request) {
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (null == userDto || null == userDto.getId()) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        UserAddressResultDto userAddress = addressProxy.queryDefaultByUserId(userDto.getId());
        if (null == userAddress) {
            ReceiveAddressResponseVo receiveAddressResponse = new ReceiveAddressResponseVo();
            List<ProvinceResponseVo> pList = areaService.queryAddress();
            receiveAddressResponse.setpList(pList);
            return receiveAddressResponse;
        }

        ReceiveAddressResponseVo receiveAddressResponse = new ReceiveAddressResponseVo();
        receiveAddressResponse.setPurchaserName(userAddress.getConsignee());
        receiveAddressResponse.setPurchaserTel(userAddress.getMobile());
        receiveAddressResponse.setStreet(userAddress.getAddress());
        receiveAddressResponse.setAreaId(userAddress.getCountryId().longValue());

        PCDIdsResponseVo pcdIds = new PCDIdsResponseVo();
        pcdIds.setProvinceId(userAddress.getProvinceId());
        pcdIds.setCityId(userAddress.getCityId());
        pcdIds.setDistrictId(userAddress.getCountryId());
        //查询所有省市区
        List<ProvinceResponseVo> pList = areaService.queryAddress();

        //添加省市区名称
        if (CollectionUtils.isNotEmpty(pList)) {
            pList.forEach(province -> {
                if (province.getPid().equals(userAddress.getProvinceId())) {
                    pcdIds.setProvinceName(province.getProvinceName());
                    List<CityResponseVo> cityList = province.getCityList();
                    if (CollectionUtils.isNotEmpty(cityList)) {
                        cityList.forEach(city -> {
                            if (city.getCid().equals(userAddress.getCityId())) {
                                pcdIds.setCityName(city.getCityName());
                                List<DistrictResponseVo> districtList = city.getDistrictList();
                                if (CollectionUtils.isNotEmpty(districtList)) {
                                    districtList.forEach(district -> {
                                        if (district.getDid().equals(userAddress.getCountryId())) {
                                            pcdIds.setDistrictName(district.getDistrictName());
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }

        receiveAddressResponse.setPcdIds(pcdIds);
        receiveAddressResponse.setpList(pList);
        return receiveAddressResponse;
    }

    @Override
    public int saveAddress(ReceiveAddressRequestVo request) {
        if (StringUtils.isBlank(request.getPurchaserName())
                || StringUtils.isBlank(request.getPurchaserTel())
                || null == request.getpCDSName()
                || StringUtils.isBlank(request.getpCDSName().getProvinceName())
                || StringUtils.isBlank(request.getpCDSName().getCityName())
                || StringUtils.isBlank(request.getpCDSName().getDistrictName())
                || StringUtils.isBlank(request.getpCDSName().getStreetName())
                || null == request.getpCDSName().getAreaId()) {
            throw new BusinessException(MessageConstant.PARAMS_NOT_EXISTS);
        }
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (null == userDto || null == userDto.getId()) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        int result = 0;
        Integer id = userDto.getId();
        UserAddressResultDto address = addressProxy.queryDefaultByUserId(id);
        //如果没数据，在数据库中增加数据
        if (null == address) {
            UserAddressAddParamDto userAddressAddDto = new UserAddressAddParamDto();
            userAddressAddDto.setUserId(id);
            userAddressAddDto.setAddress(request.getpCDSName().getStreetName());
            userAddressAddDto.setConsignee(request.getPurchaserName());
            userAddressAddDto.setMobile(request.getPurchaserTel());
            Long areaId = request.getpCDSName().getAreaId();
            userAddressAddDto.setCountryId(areaId.intValue());
            userAddressAddDto.setIsDefault(true);
            AreaDto cityArea = areaService.queryCity(areaId);
            if (null != cityArea) {
                userAddressAddDto.setCityId(cityArea.getAreaId().intValue());
                userAddressAddDto.setProvinceId(cityArea.getParentId().intValue());
            }
            result = addressProxy.addUserAddress(userAddressAddDto);
        } else {
            //如果有数据，取数据库中默认地址，并更新
            UserAddressResultDto defaultAddress = addressProxy.queryDefaultByUserId(id);
            UserAddressUpdateParamDto userAddress = new UserAddressUpdateParamDto();
            userAddress.setId(defaultAddress.getId());
            userAddress.setAddress(request.getpCDSName().getStreetName());
            userAddress.setConsignee(request.getPurchaserName());
            userAddress.setMobile(request.getPurchaserTel());
            Long areaId = request.getpCDSName().getAreaId();
            userAddress.setCountryId(areaId.intValue());
            AreaDto cityArea = areaService.queryCity(areaId);
            if (null != cityArea) {
                userAddress.setCityId(cityArea.getAreaId().intValue());
                userAddress.setProvinceId(cityArea.getParentId().intValue());
            }
            result = addressProxy.updateById(userAddress);
        }

        return result;
    }

    @Override
    public TReceiveAddressResponseVo selAddress(SelReceiveAddressRequestVo request) {
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (null == userDto || null == userDto.getId()) {
            throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }
        UserAddressResultDto userAddress = addressProxy.queryDefaultByUserId(userDto.getId());
        if (null == userAddress) {
            return null;
        }

        AreaDto provinceArea = areaService.getArea(userAddress.getProvinceId());
        AreaDto cityArea = areaService.getArea(userAddress.getCityId());
        AreaDto countryArea = areaService.getArea(userAddress.getCountryId());

        StringBuilder sb = new StringBuilder();
        if (null != provinceArea) {
            sb.append(provinceArea.getAreaName());
        }
        if (null != cityArea) {
            sb.append(" ").append(cityArea.getAreaName());
        }
        if (null != countryArea) {
            sb.append(" ").append(countryArea.getAreaName());
        }

        TReceiveAddressResponseVo res = new TReceiveAddressResponseVo();
        res.setPcdAddress(sb.toString());
        res.setPurchaserName(userAddress.getConsignee());
        res.setPurchaserTel(userAddress.getMobile());
        res.setUserId(userAddress.getUserId().longValue());
        res.setAreaId((long) userAddress.getCountryId());
        res.setStreet(userAddress.getAddress());
        res.setDefaultAddress(true);

        if (StringUtils.isBlank(res.getPcdAddress())
                || StringUtils.isBlank(res.getPurchaserName())
                || StringUtils.isBlank(res.getPurchaserTel())) {
            throw new BusinessException("用户地址信息填写不全！");
        }

        return res;
    }

}
