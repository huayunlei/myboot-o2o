package com.ihomefnt.o2o.intf.domain.program.customgoods.dto;

import lombok.Data;

import java.util.List;

/**
 * 组合详情
 *
 * @author liyonggang
 * @create 2019-03-20 14:27
 */
@Data
public class GroupDetailDto {

    //组合id
    private Integer groupId;
    //组合图片
    private String groupImage;

    //组合图片列表
    private List<String> groupImageList;

    //组合名称
    private String groupName;

    // 末级类目ID
    private Integer lastCategoryId;

    // 末级类目名称
    private String lastCategoryName;

    //组件信息
    private List<ComponentListBean> componentList;

    @Data
    public static class ComponentListBean {

        //组件分类id
        private int categoryId;
        //组件分类名称
        private String componentCategoryName;
        //组件数量
        private Integer componentNum;
        //物料信息
        private MaterialDetailBean materialDetail;
        //组件用量
        private Integer quantities;

        @Data
        public static class MaterialDetailBean {
            //物料品牌id
            private Integer brandId;
            //物料品牌名称
            private String brandName;
            //高
            private Integer height;
            //长
            private Integer length;
            //物料id
            private Integer materialId;
            //物料图片
            private String materialImage;
            //物料名称
            private String materialName;
            //宽
            private Integer width;
            //属性集合
            private List<OptionAttrListBean> optionAttrList;


            @Data
            public static class OptionAttrListBean {
                //属性id
                private Integer attrId;
                //属性值
                private String attrValue;
                private Integer optionId;
                //属性名
                private String optionName;

            }
        }
    }
}
