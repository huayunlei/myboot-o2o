package com.ihomefnt.o2o.intf.domain.personalneed.dto;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.homecard.dto.DNAProspectPictureVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DNARoomPictureVo;
import lombok.Data;

/**
 * 个性化需求  DNA信息
 * Author: ZHAO
 * Date: 2018年5月26日
 */
@Data
public class DnaInfoResponseVo {
	private Integer dnaId;//DNA id
	
	private String dnaName;//DNA名称
	
	private String styleName;//风格名
	
	private List<DNAProspectPictureVo> prospectPictureList;//意境图列表
	
	private List<DNARoomPictureVo> roomPictureList;//空间效果图列表

}
