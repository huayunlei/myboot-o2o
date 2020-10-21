package com.ihomefnt.o2o.intf.domain.art.vo.response;

import com.ihomefnt.o2o.intf.domain.art.dto.PriceSectionDto;
import com.ihomefnt.o2o.intf.domain.art.dto.StyleTypeDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-07 09:43
 */
@ApiModel("艺术品筛选条件")
@Data
public class ScreenTypeResponse {

    @ApiModelProperty("艺术风格列表")
    private List<StyleTypeDto> styleTypeList;

    @ApiModelProperty("艺术品价格区间选项")
    List<PriceSectionDto> priceSectionList;

    public ScreenTypeResponse(List<StyleTypeDto> styleTypeList, List<PriceSectionDto> priceSectionList) {
        this.styleTypeList = styleTypeList;
        this.priceSectionList = priceSectionList;
    }
}
