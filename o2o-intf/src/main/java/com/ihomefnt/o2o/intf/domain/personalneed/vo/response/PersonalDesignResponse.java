package com.ihomefnt.o2o.intf.domain.personalneed.vo.response;

import com.ihomefnt.o2o.intf.domain.homecard.dto.ArtisticEntity;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.DesignDnaRoomVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 个性化需求
 * Author: ZHAO
 * Date: 2018年5月26日
 */
@Data
@ApiModel("选风格详情")
@Accessors(chain = true)
public class PersonalDesignResponse {

	@ApiModelProperty("订单ID")
	private Integer orderId;

	@ApiModelProperty("用户标签集合")
	private List<String> userTags;

	@ApiModelProperty("家装预算")
	private String budget;

	@ApiModelProperty("意境集合")
	private List<ArtisticEntity> artisticList;

	@ApiModelProperty("硬装质量")
	private String hardQuality;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("DNA id")
	private Integer dnaId;

	@ApiModelProperty("DNA名称")
	private String dnaName;

	@ApiModelProperty("DNA风格")
	private String dnaStyle;

	@ApiModelProperty("DNA头图")
	private String dnaHeadImage;

	@ApiModelProperty("任务状态")
	private Integer taskStatus = 4;

	@ApiModelProperty("任务状态文案")
	private String taskStatusStr;

	@ApiModelProperty("创建时间")
	private String createTime;

	@ApiModelProperty("风格提交记录ID")
	private Integer styleCommitRecordId;

	@ApiModelProperty("DNA空间集合")
	private List<DesignDnaRoomVo> taskDnaRoomList;

	@ApiModelProperty("设计任务beta状态")
	private Integer newTaskStatus;

	@ApiModelProperty("方案ID")
	private Integer solutionId;

	private String designDemandId;

	@ApiModelProperty("导读页是否已读")
	private Boolean solutionsHasRead = true;

	@ApiModelProperty("是否是艾佳贷签约")
	private Boolean isLoan = false;

	@ApiModelProperty("订单状态")
	private Integer orderStatus;

	@ApiModelProperty("订单子状态")
	private Integer orderSubStatus;

	@ApiModelProperty("创建人id")
	private Integer createUserId;


}
