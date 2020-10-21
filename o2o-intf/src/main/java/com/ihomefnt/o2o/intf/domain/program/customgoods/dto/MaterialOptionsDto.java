package com.ihomefnt.o2o.intf.domain.program.customgoods.dto;

import lombok.Data;

import java.util.List;

/**
 * 筛选项信息
 *
 * @author liyonggang
 * @create 2019-03-20 16:04
 */
@Data
public class MaterialOptionsDto {


    //物料分类id
    private Integer categoryId;
    //选项集合
    private List<OptionsBean> options;

    @Data
    public static class OptionsBean {

        //选项id
        private Integer optionId;
        //选项名称
        private String optionName;
        //选项值集合
        private List<AttrsBean> attrs;

        @Data
        public static class AttrsBean {
            //选项id
            private Integer attrId;
            //选项值
            private String attrValue;

        }
    }
}
