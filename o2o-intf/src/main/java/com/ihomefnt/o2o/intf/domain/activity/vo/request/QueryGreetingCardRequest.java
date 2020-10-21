package com.ihomefnt.o2o.intf.domain.activity.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询定制贺卡
 * @author ZHAO
 */
@Data
@ApiModel("查询定制贺卡请求参数")
public class QueryGreetingCardRequest extends HttpBaseRequest{

	@ApiModelProperty("定制贺卡ID")
    private Integer cardId;
}
