package com.ihomefnt.o2o.intf.domain.staticdata.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-11-21 15:33
 */
@NoArgsConstructor
@Data
@ApiModel("用户故事数据")
public class UserStoryDataResponse {

    @ApiModelProperty("顶部图")
    private SimpleImageResponse topImage;
    @ApiModelProperty("底部图")
    private SimpleImageResponse bottomImage;
    private List<StaticResourceDto> resourceList;
}
