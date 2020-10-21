package com.ihomefnt.o2o.intf.domain.program.customgoods.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * sku详情元素
 *
 * @author liyonggang
 * @create 2019-03-18 16:46
 */
@ApiModel("sku详情元素")
@Data
@Accessors(chain = true)
public class ElementVO {

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("图片集合")
    private List<ImageVO> imageList;

    @ApiModelProperty("属性集合")
    private List<Attribute<String, String>> attributeList;
}
