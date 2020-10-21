package com.ihomefnt.o2o.intf.domain.art.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-06 18:55
 */
@ApiModel("新版艺术品列表请求参数")
@Data
public class ArtListRequest extends CategoryArtPageRequest {

    @ApiModelProperty("艺术家id")
    private String artistId;

    @ApiModelProperty("作品名称模糊检索")
    private String likeWorksName;

    @ApiModelProperty("用户选定的价格 枚举：1为1000以内 2为 1000～2000 以此类推")
    private List<Integer> priceRangeList;

    @ApiModelProperty("风格id")
    private List<String> styleList;
}
