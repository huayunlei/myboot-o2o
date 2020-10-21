package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

import java.util.List;

/**
 * 视频分类集合
 * @author ZHAO
 */
@Data
public class VideoTypeListResponseVo {
	private List<VideoTypeEntity> typeList;
}
