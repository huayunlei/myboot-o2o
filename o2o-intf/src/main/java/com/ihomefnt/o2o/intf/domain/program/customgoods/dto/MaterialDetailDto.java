package com.ihomefnt.o2o.intf.domain.program.customgoods.dto;

import lombok.Data;

import java.util.List;

/**
 * 物料详情
 *
 * @author liyonggang
 * @create 2019-03-20 14:36
 */
@Data
public class MaterialDetailDto {

    //品牌id
    private Integer brandId;
    //品牌名称
    private String brandName;
    //高
    private Integer height;
    //长
    private Integer length;
    //物料id
    private Integer materialId;
    //物料图片
    private String materialImage;
    //物料图片列表
    private List<String> materialImageList;
    //物料名称
    private String materialName;
    //宽
    private Integer width;
    //属性集合
    private List<OptionAttrListBean> optionAttrList;

    // 末级类目ID
    private Integer lastCategoryId;

    // 末级类目名称
    private String lastCategoryName;

    // 顶级类目ID（用于前端判断是否窗帘，窗帘不显示品牌）
    private Integer topCategoryId;

    @Data
    public static class OptionAttrListBean {
        //属性id
        private Integer attrId;
        //属性值
        private String attrValue;
        //属性id
        private Integer optionId;
        //属性名称
        private String optionName;
    }
}
