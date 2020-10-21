package com.ihomefnt.o2o.api.controller.sales;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.user.dto.RoleDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.constant.user.UserRoleConstant;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.proxy.sales.vo.request.BindRequest;
import com.ihomefnt.o2o.intf.proxy.sales.vo.request.CustomerListRequest;
import com.ihomefnt.o2o.intf.proxy.sales.vo.response.CustomerListResponse;
import com.ihomefnt.o2o.intf.proxy.sales.vo.response.InviteResponse;
import com.ihomefnt.o2o.intf.service.sales.SalesService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Created by hvk687 on 10/20/15.
 */
@ApiIgnore
@Deprecated
@Api(tags = "销售老API", hidden = true)
@RestController
@RequestMapping(value = "/sales")
public class SalesController {

    @Autowired
    UserService userService;
    @Autowired
    SalesService salesService;

    @ApiOperation(value = "homecard", notes = "homecard")
    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public HttpBaseResponse<CustomerListResponse> home(@Json CustomerListRequest request) {
        CustomerListResponse response = null;

        /**
         * whether user is login
         */
        String accessToken = request.getAccessToken();
        UserDto userDto = userService.getUserByToken(accessToken);
        if (userDto == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        List<RoleDto> roles = userDto.getRoles();
        if (null != roles) {
            boolean flag = false;
            for (RoleDto role : roles) {
                String code = role.getCode();
                if (UserRoleConstant.DEAL_USER_ZHONGNAN.equals(code)) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.FAILED);
            }
            response = salesService.loadSalesCustomer(userDto.getId().longValue(), request.getFrom(), 10);
        }
        return HttpBaseResponse.success(response);
    }

    /**
     * bind user and sales
     *
     * @return
     */
    @ApiOperation(value = "bind", notes = "bind")
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public HttpBaseResponse<Void> bind(@Json BindRequest request) {
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (userDto == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }

        Long salesId = userDto.getId().longValue();
        InviteResponse response = salesService.bindUser(salesId, request.getMobile(), 2);
        if (!response.getStatus().equals(InviteResponse.SUCCESS)) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, response.getMsg());
        }

        return HttpBaseResponse.success();
    }

}
