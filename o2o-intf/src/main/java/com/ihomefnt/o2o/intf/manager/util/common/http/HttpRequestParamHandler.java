package com.ihomefnt.o2o.intf.manager.util.common.http;

import com.ihomefnt.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * HttpRequestHandler 验证请求参数使用
 *
 * @author liyonggang
 * @create 2019-03-18 19:34
 */
@Service
public class HttpRequestParamHandler {

    @Autowired
    private UserProxy userProxy;

    /**
     * 限制登陆
     */
    public UserDto verificationLogin(String accessToken, boolean required) {
        UserDto userDto = null;
        if (StringUtils.isNotBlank(accessToken)) {
            userDto = userProxy.getUserByToken(accessToken);
        }
        if (required && userDto == null) {
            throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        return userDto;
    }
}
