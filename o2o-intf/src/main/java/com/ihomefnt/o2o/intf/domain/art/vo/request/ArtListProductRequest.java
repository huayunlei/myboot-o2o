package com.ihomefnt.o2o.intf.domain.art.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-07 11:06
 */
@ApiModel("查询产品或艺术品可定制图案检索条件")
@Data
public class ArtListProductRequest extends ProductRequest{

    @ApiModelProperty("风格id")
    private List<String> styleIdList;

    @ApiModelProperty(value = "艺术品id")
    private String worksId;

    @ApiModelProperty("当前第几页")
    private Integer pageNo = 1;

    @ApiModelProperty("每页显示条数")
    private Integer pageSize = 10;


}
