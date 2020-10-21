package com.ihomefnt.o2o.intf.domain.art.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-08-12 14:56
 */
@Data
@ApiModel(value = "艺术家入参")
public class HttpArtistRequest extends HttpBaseRequest {

    @ApiModelProperty(value = "艺术家id")
    private String artistId;
}
