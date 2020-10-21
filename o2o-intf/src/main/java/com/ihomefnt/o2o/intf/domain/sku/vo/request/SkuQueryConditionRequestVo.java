package com.ihomefnt.o2o.intf.domain.sku.vo.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/8/30 15:14
 */
@Data
@ApiModel("查询过滤条件请求bean")
public class SkuQueryConditionRequestVo {

    /**
     * 二级类目id
     */
    Integer categoryLevelTwo;

    /**
     * 四级类目id
     */
    Integer categoryLevelFour;
}
