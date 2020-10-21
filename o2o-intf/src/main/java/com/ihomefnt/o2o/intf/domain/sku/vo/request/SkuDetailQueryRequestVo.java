package com.ihomefnt.o2o.intf.domain.sku.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jerfan cang
 * @date 2018/8/20 10:29
 */
@ApiModel("查询sku详情请求bean")
@Data
public class SkuDetailQueryRequestVo extends HttpBaseRequest implements Cloneable{

    /**
     * 用户id
     */
    private String userId;

    /**
     * skuId
     */
    private Integer skuId;

    /**商品类型
     * productType
     * @return
     */
    private int productType;

    @ApiModelProperty("批量查询接口使用skuList")
    private List<Integer> skuList;

    @Override
    public SkuDetailQueryRequestVo clone() throws CloneNotSupportedException {
        return (SkuDetailQueryRequestVo)super.clone();
    }
}
