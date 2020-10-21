package com.ihomefnt.o2o.intf.domain.homecard.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DNA详情
 * @author ZHAO
 */
@NoArgsConstructor
@Data
public class DNAInfoResponseVo extends DNAInfoResponseCommonVo{
	private Integer dnaId;//DNA ID
	
	private String dnaName;//DNA名称
	
	private String headImgUrl;//DNA首图
	
	private String seriesName;//系列名
	
	private String styleName;//风格名
	
	private List<String> tagList;// 标签列表
	
	private String shortDesc;//短文案,较短
	
	private String designIdea;//设计理念,较长
	
	private List<DNAProspectPictureVo> prospectPictureList;//意境图列表
	
	private List<DNASoftDecorationFeature> softDecorationFeatureList;// 软装亮点列表
	
	private List<DNARoomVo> dnaRoomList;//空间列表
	
	private String vrLinkUrl;//VR跳转地址

	private BigDecimal dnaTotalPrice;//DNA总价（参考）

	@ApiModelProperty("材质示意图")
	private List<String> materialDiagramUrl;



}
