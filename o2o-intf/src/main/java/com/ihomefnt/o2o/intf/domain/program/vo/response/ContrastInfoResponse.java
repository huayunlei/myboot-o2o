package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.program.dto.ContrastSpaceInfo;
import com.ihomefnt.o2o.intf.domain.program.dto.HardStandardSpaceResponseVo;
import lombok.Data;

import java.util.List;

/**
 * 对比方案信息
 * Author: ZHAO
 * Date: 2018年6月22日
 */
@Data
public class ContrastInfoResponse {

	private Integer solutionId;// 方案id

	private String solutionName;// 方案名称

	private String solutionHeadImgURL;// 方案首图地址

	private String solutionSeriesName;// 系列名称

	private String solutionStyleName;// 风格名称

	private String category;// 装修类别:硬装+软装

	private String solutionDesignIdea;// 方案设计描述
	
	private String advantage;//优点
	
	private List<String> tagList;//标签集合

	private List<ContrastSpaceInfo> spaceList;// 空间集合
	
	private List<HardStandardSpaceResponseVo> hardStandardList;//硬装标准信息

}
