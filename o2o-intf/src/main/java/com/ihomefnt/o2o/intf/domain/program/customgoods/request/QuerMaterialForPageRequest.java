package com.ihomefnt.o2o.intf.domain.program.customgoods.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 分页查询物料信息
 *
 * @author liyonggang
 * @create 2019-03-19 14:48
 */
@Data
@ApiModel("分页查询物料信息")
public class QuerMaterialForPageRequest extends HttpBaseRequest {

    @ApiModelProperty("品牌id")
    private Integer brandId;
    @ApiModelProperty("组件id")
    private Integer componentId;
    @ApiModelProperty("默认组合id")
    private Integer defaultGroupId;
    @ApiModelProperty("默认物料id")
    private Integer defaultMaterialId;
    @ApiModelProperty("页码")
    private Integer pageNo;
    @ApiModelProperty("页长")
    private Integer pageSize;
    @ApiModelProperty("已选物料id")
    private String selectedMaterialId;
    @ApiModelProperty("排序类型: 0=默认排序，1=价格升序，2=价格降序")
    private Integer sortType;
    @ApiModelProperty("默认组合数量,用于商品中心计算总差价")
    private Integer defaultGroupNum;
    @ApiModelProperty("筛选项集合")
    private List<OptionValueIdListBean> optionValueIdList;

    @ApiModelProperty("是否可选免费赠品 1 可选 0不可选")
    private Integer freeAble = 0;

    @ApiModelProperty("默认窗帘沙价格")
    private BigDecimal defaultYarnPrice;

    @ApiModelProperty("组件分类id")
    private Integer componentCategoryId;

    @ApiModelProperty("已选组件数量")
    private Integer selectedComponentNum;




    @Data
    @ApiModel("筛选项")
    public static class OptionValueIdListBean {
        @ApiModelProperty("筛选值id")
        private List<Integer> attrIdList;
        @ApiModelProperty("筛选项id")
        private Integer optionId;
    }
}
