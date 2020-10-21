package com.ihomefnt.o2o.intf.domain.common.http;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by shirely_geng on 15-1-10.
 */
@Data
@ApiModel("公共请求参数")
@Accessors(chain = true)
public class HttpBaseRequest {
    @ApiModelProperty(value = "设备token")
    private String deviceToken;

    @ApiModelProperty(value = "设备类型")
    private Integer osType; // 订单来源,1:iPhone客户端，2:Android客户端，3:H5网站，4:PC网站，5:客服电话下单，6:客服现场下单 8:小程序订单

    @ApiModelProperty(value = "app版本号")
    private String appVersion;

    @ApiModelProperty(value = "iOS 自然默认 100 ,每个应用都不一样")
    private String parterValue;// iOS 自然默认 100
    // ,每个应用都不一样。具体请参看http://wiki.ihomefnt.com:8002/pages/viewpage.action?pageId=3834260

    @ApiModelProperty(value = "设备宽度")
    private Integer width;

    @ApiModelProperty(value = "定位城市id")
    private String cityCode;

    @ApiModelProperty(value = "登录标识")
    private String accessToken;

    @ApiModelProperty(value = "定位地区")
    private String location;

    @ApiModelProperty(value = "手机号码")
    private String mobileNum; // 手机号码

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "系统版本")
    private String systemVersion;

    @ApiModelProperty(value = "bundle版本号列表")
    private Object bundleVersions;

    @ApiModelProperty(value = "用户基本信息")
    private HttpUserInfoRequest userInfo;
}
