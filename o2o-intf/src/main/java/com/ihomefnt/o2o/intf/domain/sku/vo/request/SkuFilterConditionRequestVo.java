package com.ihomefnt.o2o.intf.domain.sku.vo.request;

import com.alibaba.fastjson.JSONArray;
import  com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/8/30 16:08
 */
@Data
public class SkuFilterConditionRequestVo {

    /**
     * 风格ID
     */
    private Integer style;

    /**
     * 风格ID
     */
    private Integer defaultSkuId;

    /**
     * 二级目录id
     */
    private Integer categoryLevelTwoId;

    /**
     * 当前页数 不传或者不合法时候给你0
     */
    private Integer pageIndex =1;

    /**
     * 每页的记录数据 默认10
     */
    private Integer pageSize = 10;

    /**
     * 价格 指的是原始的价格
     */
    private Integer price;

    /**
     * 排序方式 0 默认排序 1 价格升序 2 价格降序
     */
    private Integer sort = 0;

    /**
     * 要排除sku其id
     */
    private JSONArray skuIds;

    /**
     * 三级类目id集合
     */
    private JSONArray categoryLevelThreeIds;

    /**
     * 四级类目id集合
     */
    private JSONArray categoryLevelFourIds;

    /**
     * 品牌id集合
     */
    private JSONArray brandIds;

    /**
     * 高度，仅搜索床垫时候指的是床垫的厚度
     * maxValue 和 minValue
     */
    private JSONArray skuHeight;

    /**
     * 长度
     * maxValue 和 minValue
     */
    private JSONObject skuLength;

    /**
     * 尺寸规格
     */
    private JSONArray ruleSize;

    /**
     * 屏幕宽度
     */
    private Integer width;

    @ApiModelProperty("家具类型")
    private Integer furnitureType;

    @ApiModelProperty("默认sku")
    private Integer baseSkuId;

    @ApiModelProperty("标配skuId数量")
    private Long baseSkuCount;

    @ApiModelProperty("查询家具类型,0:通用家具,1:床,2:床垫")
    private Integer itemType;
    @ApiModelProperty("建议适配床垫最小高")
    private Integer suggestMattressMinHeight;
    @ApiModelProperty("建议适配床垫长")
    private Integer suggestMattressLength;
    @ApiModelProperty("建议适配床垫宽度")
    private Integer suggestMattressWidth;
    @ApiModelProperty("建议适配床垫最大高")
    private Integer suggestMattressMaxHeight;

    public com.alibaba.fastjson.JSONObject parseJson() throws RuntimeException{
        JSONObject json =new JSONObject();
        if(null == categoryLevelTwoId){
            throw new RuntimeException("二级目录id不能为空");
        }
        if(null ==price){
            throw new RuntimeException("sku价格不能为空");
        }
        json.put("style",style);
        json.put("categoryLevelTwoId",categoryLevelTwoId);
        json.put("pageIndex",pageIndex);
        json.put("pageSize",pageSize);
        json.put("defaultSkuId",defaultSkuId);
        json.put("price",price);
        json.put("sort",sort);
        json.put("skuIds",skuIds);
        json.put("categoryLevelThreeIds",categoryLevelThreeIds);
        json.put("categoryLevelFourIds",categoryLevelFourIds);
        json.put("brandIds",brandIds);
        json.put("skuHeight",skuHeight);
        json.put("skuLength",skuLength);
        json.put("ruleSize",ruleSize);
        json.put("width",width);
        json.put("furnitureType",furnitureType);
        json.put("baseSkuId",baseSkuId);
        json.put("baseSkuCount",baseSkuCount);
        json.put("suggestMattressMinHeight",suggestMattressMinHeight);
        json.put("suggestMattressLength",suggestMattressLength);
        json.put("suggestMattressWidth",suggestMattressWidth);
        json.put("suggestMattressMaxHeight",suggestMattressMaxHeight);
        json.put("itemType",itemType);
        return json;
    }
}
