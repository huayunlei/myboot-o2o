/*
 * Author: ZHAO
 * Date: 2018/10/11
 * Description:ArterWork.java
 */
package com.ihomefnt.o2o.intf.domain.artist.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 小星星艺术家
 *
 * @author ZHAO
 */
@Data
@ApiModel("小星星艺术家")
public class ArterWork {
    @ApiModelProperty("艺术家ID")
    private Integer arterId;
    
    @ApiModelProperty("艺术家名称")
    private String arterName;

    @ApiModelProperty("艺术家别名")
    private String arterNickName;

    @ApiModelProperty("艺术家头像")
    private String arterImgUrl;

    @ApiModelProperty("艺术家年纪")
    private Integer arterAge;

    @ApiModelProperty("艺术家作品")
    private List<ArtStarWork> starWorkList;
}
