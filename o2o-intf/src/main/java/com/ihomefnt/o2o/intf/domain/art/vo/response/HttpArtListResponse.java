package com.ihomefnt.o2o.intf.domain.art.vo.response;

import com.google.common.collect.Lists;
import com.ihomefnt.o2o.intf.domain.art.dto.ArtSpace;
import com.ihomefnt.o2o.intf.domain.art.dto.Artwork;
import lombok.Data;

import java.util.List;

/**
 * 艺术家列表
* @Title: HttpArtListResponse.java 
* @Description: TODO
* @author Charl 
* @date 2016年7月15日 下午1:24:53 
* @version V1.0
 */
@Data
public class HttpArtListResponse {
	
	private List<ArtSpace> artNameList = Lists.newArrayList(); //空间名称列表

	private Integer spaceId = 0; //空间id
	
	private List<Artwork> artworkList = Lists.newArrayList(); //艺术品列表
	
	private int totalPage = 1; //总页数
}
