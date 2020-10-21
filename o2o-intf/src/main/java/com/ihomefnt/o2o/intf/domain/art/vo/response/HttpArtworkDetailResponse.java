package com.ihomefnt.o2o.intf.domain.art.vo.response;

import com.ihomefnt.o2o.intf.domain.art.dto.Artwork;
import com.ihomefnt.o2o.intf.domain.art.dto.ArtworkImage;
import lombok.Data;

import java.util.List;

@Data
public class HttpArtworkDetailResponse {
	
	private Artwork artwork; //艺术品
	
	private String payDes; //购买说明
	
	private String logoUrl;//艺术品logo图片
	
	private String artistItemName;//艺术家选项名称：艺术家 或者 机构
	
	private String descName;//艺术品详情项目名称
	
	private String imageText;//图文详情栏目名称
	
	private String noneImageText;//暂无图文详情
	
	private boolean displayScene;//是否为场景展示，true:场景展示 false:不进行场景展示

	private String sceneImage;//场景体验图图片

	private String sceneImageWidth;//场景体验图宽

	private String sceneImageHeight;//场景体验图高

	private List<ArtworkImage> artworkImages; //艺术品图片列表
	
	private List<ArtworkImage> backgroundImages; //艺术品背书图
}
