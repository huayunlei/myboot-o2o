package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author jerfan cang
 * @date 2018/10/20 14:41
 */
@Data
@ApiModel("ProductFilterListResponseVo")
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterListResponseVo {

    @ApiModelProperty("过滤的商品列表 skuId")
    private Set<Integer> productFilterList;
}
