package com.ihomefnt.o2o.intf.domain.delivery.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liyonggang
 * @create 2020-02-12 12:08 下午
 */
@Data
public class NoticeBoardDto {

    @ApiModelProperty("标题")
    private String title = "";

    @ApiModelProperty("跳转链接")
    private String openUrl = "";

    @ApiModelProperty("是否显示")
    private Boolean show = Boolean.FALSE;
}