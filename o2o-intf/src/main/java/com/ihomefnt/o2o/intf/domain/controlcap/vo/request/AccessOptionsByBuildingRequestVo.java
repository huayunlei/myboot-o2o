package com.ihomefnt.o2o.intf.domain.controlcap.vo.request;

import com.ihomefnt.o2o.intf.domain.controlcap.dto.CustomerInfoDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liyonggang
 * @create 2018-11-28 10:50
 */
@Data
@ApiModel("查询楼盘户型信息")
public class AccessOptionsByBuildingRequestVo extends HttpBaseRequest {

    @ApiModelProperty("楼盘id")
    private Integer buildingId;
    @ApiModelProperty("空间id")
    private Integer zoneId;
    @ApiModelProperty("户型id")
    private Integer houseTypeId;

    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("验证码")
    private String smsCode;
    //客户信息
    private CustomerInfoDto customerInfoDto;
    @ApiModelProperty("方案名称")
    private String solutionName;

}
