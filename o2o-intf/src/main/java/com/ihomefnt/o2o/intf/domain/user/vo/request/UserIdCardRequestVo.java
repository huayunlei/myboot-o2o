package com.ihomefnt.o2o.intf.domain.user.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户实名认证
 * @author ZHAO
 */
@ApiModel("用户实名认证")
@Data
public class UserIdCardRequestVo extends HttpBaseRequest{

	@ApiModelProperty("真实姓名")
    private String realName;

	@ApiModelProperty("身份证号码")
    private String idCardNum;

}
