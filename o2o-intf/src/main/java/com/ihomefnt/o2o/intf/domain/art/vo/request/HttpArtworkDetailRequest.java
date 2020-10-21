package com.ihomefnt.o2o.intf.domain.art.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 艺术品详情请求
* @Title: HttpArtworkDetailRequest.java 
* @Description: TODO
* @author Charl 
* @date 2016年7月18日 下午7:49:19 
* @version V1.0
 */
@ApiModel
@Data
public class HttpArtworkDetailRequest extends HttpBaseRequest{
	
	private Long artworkId; //艺术品id

	private String skuId;//新版艾商城skuId

	@ApiModelProperty("售卖类型 0默认 1定制商品")
	private Integer saleType;
}

