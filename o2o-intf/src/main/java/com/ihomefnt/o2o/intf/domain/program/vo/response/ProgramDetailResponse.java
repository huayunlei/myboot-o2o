package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.programorder.dto.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品方案详情返回值
 * 
 * @author ZHAO
 */
@Data
@ApiModel("产品方案详情返回")
public class ProgramDetailResponse implements Serializable {
	@ApiModelProperty("方案名称")
	private String name;

	@ApiModelProperty("风格")
	private String styleName;

	@ApiModelProperty("套系名称")
	private String seriesName;

	@ApiModelProperty("全屋价格")
	private String price;

	@ApiModelProperty("装修类别:硬装+软装")
	private String category;

	@ApiModelProperty("空间集合")
	private List<SpaceEntity> spaceList;

	@ApiModelProperty("硬装方案：各空间用途")
	private List<String> hardList;

	@ApiModelProperty("家具总件数")
	private Integer furnitureTotalNum;

	@ApiModelProperty("老户型格局")
	private String housePattern;

	@ApiModelProperty("户型面积")
	private String houseArea;

	@ApiModelProperty("户型id")
	private Long apartmentId;

	@ApiModelProperty("户型版本")
	private Long apartmentVersion;

	@ApiModelProperty("户型格局")
	private String apartmentPattern;

	@ApiModelProperty("是否是拆改方案 0 不是 1 是")
	private Integer reformFlag;

	@ApiModelProperty("平面设计图")
	private String solutionGraphicDesignUrl;

	@ApiModelProperty("户型名称")
	private String houseTypeName;

	@ApiModelProperty("宽高比")
	private BigDecimal graphicDesignAspectRatio;

	@ApiModelProperty("方案设计描述")
	private String solutionDesignIdea;

//	@ApiModelProperty("")
	private BigDecimal solutionDiscount;

	@ApiModelProperty("浏览量")
	private Integer visitNum;

	@ApiModelProperty("风格集合")
	private List<String> styleNameList;

	@ApiModelProperty("套系名称集合")
	private List<String> seriesNameList;

	@ApiModelProperty("3D巡游的链接地址")
	private String vrLinkUrl;

	@ApiModelProperty("小贴士信息")
	private KnowledgeListResponse knowledgeInfo;

	@ApiModelProperty("增配包信息")
	private List<AddBagDetail> addBagInfo;

	@ApiModelProperty("标准升级包信息")
	private List<StandardUpgradeInfo> upgradeInfos;

	@ApiModelProperty("非标准升级包信息")
	private List<NoStandardUpgradeInfo> noUpgradeInfos;

	@ApiModelProperty("标准升级项待选")
	private SolutionStandardUpgradeTotalResponse upgradeInfosForChoice;
	
	@ApiModelProperty("设计师列表")
	private List<DesignerInfoForProgramResponse> designerList;

	@ApiModelProperty("可替换空间数量")
	private Integer replaceRoomCount;

	@ApiModelProperty("优点")
	private String advantage;

	@ApiModelProperty("标签集合")
	private List<String> tagList;

	@ApiModelProperty("可替换空间标识集合文案")
	private String replaceRoomPraise;

	@ApiModelProperty("可替换家具空间标识集合文案")
	private String replaceFruniturePraise;

	@ApiModelProperty("空间硬装列表")
	private List<SpaceDesign> spaceDesignList;

	public ProgramDetailResponse() {
		this.name = "";
		this.styleName = "";
		this.seriesName = "";
		this.price = "";
		this.category = "";
		this.spaceList = new ArrayList<>();
		this.hardList = new ArrayList<>();
		this.furnitureTotalNum = 0;
		this.housePattern = "";
		this.houseArea = "";
		this.solutionGraphicDesignUrl = "";
		this.graphicDesignAspectRatio = new BigDecimal(1);
		this.solutionDesignIdea = "";
		this.solutionDiscount = BigDecimal.ZERO;
		this.visitNum = 0;
		this.styleNameList = new ArrayList<>();
		this.seriesNameList = new ArrayList<>();
		this.vrLinkUrl = "";
		this.houseTypeName = "";
		this.knowledgeInfo = new KnowledgeListResponse();
		this.addBagInfo = new ArrayList<>();
		this.replaceRoomCount = 0;
		this.advantage = "";
		this.tagList = new ArrayList<>();
		this.replaceRoomPraise = "";
		this.replaceFruniturePraise = "";
	}

}
