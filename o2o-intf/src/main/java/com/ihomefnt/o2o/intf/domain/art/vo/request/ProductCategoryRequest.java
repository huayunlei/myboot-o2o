package com.ihomefnt.o2o.intf.domain.art.vo.request;

import com.ihomefnt.o2o.intf.domain.art.dto.FrontCategoryInfoDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-07 10:24
 */
@ApiModel("艺术品列表请求参数")
@Data
public class ProductCategoryRequest  extends HttpBaseRequest {

    @ApiModelProperty(value = "商品中心oms商品分类信息")
    private List<FrontCategoryInfoDto.ArtCategoryInfoBean> artCategoryInfo;
}
