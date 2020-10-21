package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("小星星艺术家列表")
public class StarArtistListDto implements Serializable {

	private static final long serialVersionUID = -146373867572990979L;

	/**
     *我的小艺术家
     */
    @ApiModelProperty("我的小艺术家")
	private List<StarArtistDto> myStarArtist;
	
    /**
     *全部小艺术家
     */
    @ApiModelProperty("全部小艺术家")
	private List<StarArtistDto> allStarArtist;

}
