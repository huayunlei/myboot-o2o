package com.ihomefnt.o2o.service.proxy.controlcap;

import com.ihomefnt.o2o.intf.domain.controlcap.dto.CustomerInfoDto;
import com.ihomefnt.o2o.intf.proxy.controlcap.ControlCapProxy;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author liyonggang
 * @create 2018-11-28 11:01
 */
@Repository
public class ControlCapProxyImpl implements ControlCapProxy {
    @Resource
    private ServiceCaller serviceCaller;


    @Override
    public int addCustomerInfo(CustomerInfoDto customerInfoDto) {
        try {
        	HttpBaseResponse<?> result = serviceCaller.post("wcm-web.customer.addCustomerInfo", customerInfoDto, HttpBaseResponse.class);
        	if (null != result && null != result.getCode() && 1 == result.getCode()) {
        		return 1;
        	}
        } catch (Exception e) {
        	return 0;
        }
        return 0;
    }
}
