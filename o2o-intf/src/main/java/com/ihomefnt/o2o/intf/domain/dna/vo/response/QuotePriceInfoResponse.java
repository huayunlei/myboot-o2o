package com.ihomefnt.o2o.intf.domain.dna.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-11-18 10:14
 */
@Data
@ApiModel(value = "装修报价返回信息")
@Accessors(chain = true)
public class QuotePriceInfoResponse {

    @ApiModelProperty("已获取装修报价人数总计")
    private Integer quotePriceCount;

    @ApiModelProperty("装修报价页面图片")
    private String imageUrl;

    @ApiModelProperty("背景图片")
    private String bgImageUrl;

}
