package com.ihomefnt.o2o.intf.domain.artist.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 艺术家信息
 * Author: ZHAO
 * Date: 2018年10月14日
 */
@Data
public class ArtistInfoResponse{
	@ApiModelProperty("用户id")
    private Integer userId;
    
    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String uImg;

    @ApiModelProperty("年龄")
    private Integer age;
    
    @ApiModelProperty("作品集合")
    private List<ArtStarWork> starWorkList;
}
