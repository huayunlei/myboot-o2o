package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import com.ihomefnt.o2o.intf.domain.homecard.dto.VideoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * APP3.0新版首页视频版块返回值
 * @author ZHAO
 */
@Data
@ApiModel(value="VideoBoardResponse",description="APP3.0新版首页视频版块返回值")
public class VideoBoardResponse {
	@ApiModelProperty("置顶视频数据")
	private List<VideoEntity> bannerList;
	
	@ApiModelProperty("视频列表数据")
	private List<VideoEntity> videoList;

	@ApiModelProperty("视频类型数据")
	private List<VideoTypeResponse> typeList;
	
	private Integer pageNo;//当前第几页
	
	private Integer pageSize;//每页显示多少条
	
	private Integer totalCount;//总共多少条
	
	private Integer totalPage;//总共多少页

	public VideoBoardResponse() {
		this.bannerList = new ArrayList<>();
		this.videoList = new ArrayList<>();
		this.typeList = new ArrayList<>();
		this.pageNo = 1;
		this.pageSize = 10;
		this.totalCount = 0;
		this.totalPage = 0;
	}


}
