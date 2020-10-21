package com.ihomefnt.o2o.intf.domain.homepage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/7/17
 */
@Data
@ApiModel("点评信息返回")
public class OrderCommentResponse {

    @ApiModelProperty("点评图片列表")
    private List<String> commentImageList;
}
