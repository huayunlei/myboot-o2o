package com.ihomefnt.o2o.intf.domain.personalneed.vo.response;

import com.ihomefnt.o2o.intf.domain.homecard.dto.ArtisticEntity;
import com.ihomefnt.o2o.intf.domain.program.dto.RoomPictureInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 个性化需求  DNA意境
 * Author: ZHAO
 * Date: 2018年5月26日
 */
@Data
public class PersonalArtisticResponse {
	private Integer dnaId;//DNA  ID
	
	private String dnaName;//DNA名称
	
	private String headImgUrl;//首图
	
	private String style;//风格
	
	private List<ArtisticEntity> artisticList;//意境集合
	
	private List<RoomPictureInfo> roomImageList;//空间照片集合

	public PersonalArtisticResponse() {
		this.dnaId = -1;
		this.dnaName = "";
		this.headImgUrl = "";
		this.style = "";
		this.artisticList = new ArrayList<ArtisticEntity>();
		this.roomImageList = new ArrayList<RoomPictureInfo>();
	}

}
