package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

import java.util.List;

/**
 * 艺术品品类
 * @author ZHAO
 */
@Data
public class FrameArtResponseVo {
	private List<FrameArtVo> categoryList;
}
