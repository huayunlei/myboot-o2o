package com.ihomefnt.o2o.intf.domain.art.vo.response;

import com.ihomefnt.o2o.intf.domain.art.dto.ArtDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @author wanyunxin
 * @create 2019-08-06 18:52
 */
@ApiModel("艺术品列表")
@Data
public class ArtListPageResponse {

    @ApiModelProperty("艺术品列表")
    private List<ArtDto> list;

    @ApiModelProperty("当前第几页")
    private Integer pageNo;

    @ApiModelProperty("每页显示多少条")
    private Integer pageSize;

    @ApiModelProperty("总条数")
    private Integer totalCount;

    @ApiModelProperty("总页数")
    private Integer totalPage;
}
