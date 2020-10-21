package com.ihomefnt.o2o.intf.domain.life.vo.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/28 0028.
 */
@Data
@ApiModel(value = "类目请求参数")
public class CategoryRequestVo extends ArticleListRequestVo {
    /**
     * 类目ID
     */
    private Integer categoryId;

    /**
     * 类目下查询上架的文章
     */
    private Integer publishFlag = 2;
}
