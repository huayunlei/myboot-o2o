package com.ihomefnt.o2o.service.proxy.address;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.address.dto.UserAddressAddParamDto;
import com.ihomefnt.o2o.intf.domain.address.dto.UserAddressResultDto;
import com.ihomefnt.o2o.intf.domain.address.dto.UserAddressUpdateParamDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.UserWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.address.AddressProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;

@Service
public class AddressProxyImpl implements AddressProxy {

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public UserAddressResultDto queryDefaultByUserId(Integer userId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);

        try {
            ResponseVo<UserAddressResultDto> response = strongSercviceCaller.post(UserWebServiceNameConstants.ADDRESS_QUERY_DEFAULT_BY_USER_ID, param,
                    new TypeReference<ResponseVo<UserAddressResultDto>>() {
                    });
            if (null != response) {
                return response.getData();
            } else {
                throw new BusinessException(MessageConstant.FAILED);
            }
        } catch (Exception e) {
            throw new BusinessException(MessageConstant.FAILED);
        }
    }

    @Override
    public Integer updateById(UserAddressUpdateParamDto param) {
        if (param == null || param.getId() == null) {
            return -1;
        }

        try {
            ResponseVo<?> responseVo = strongSercviceCaller.post(UserWebServiceNameConstants.ADDRESS_UPDATE_BY_ID, param,
                    ResponseVo.class);

            if (responseVo == null) {
                return -1;
            }

            if (responseVo.isSuccess()) {
                return responseVo.getCode() == null ? -1 : responseVo.getCode();
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

    @Override
    public Integer addUserAddress(UserAddressAddParamDto param) {
        if (param == null || param.getUserId() == null || param.getProvinceId() == null || param.getCityId() == null
                || param.getCountryId() == null || StringUtils.isBlank(param.getAddress())
                || StringUtils.isBlank(param.getMobile()) || StringUtils.isBlank(param.getConsignee())) {
            return -1;
        }

        try {
            ResponseVo<?> responseVo = strongSercviceCaller.post(UserWebServiceNameConstants.ADDRESS_ADD_USER_ADDRESS, param,
                    ResponseVo.class);

            if (responseVo == null) {
                return -1;
            }

            if (responseVo.isSuccess()) {
                return responseVo.getCode() == null ? -1 : responseVo.getCode();
            }
        } catch (Exception e) {
            return -1;
        }

        return -1;
    }

    @Override
    public List<UserAddressResultDto> queryUserAddressList(Integer userId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        try {
            ResponseVo<List<UserAddressResultDto>> response = strongSercviceCaller.post(UserWebServiceNameConstants.ADDRESS_QUERY_USER_ADDRESS_LIST, param,
                    new TypeReference<ResponseVo<List<UserAddressResultDto>>>() {
                    });

            if (null != response && response.isSuccess() && response.getData() != null) {
                return response.getData();
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

}
