package com.ihomefnt.o2o.intf.domain.activity.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 制作贺卡
 * @author ZHAO
 */
@Data
@ApiModel("制作贺卡请求参数")
public class MakeGreetingCardRequest extends HttpBaseRequest{

	@ApiModelProperty("图片ID")
    private Integer picId;

	@ApiModelProperty("祝福语")
    private String blessingWords;

	@ApiModelProperty("署名")
    private String signature;
}
