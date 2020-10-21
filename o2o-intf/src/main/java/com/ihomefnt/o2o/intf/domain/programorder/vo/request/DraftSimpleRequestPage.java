package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import com.ihomefnt.o2o.intf.domain.programorder.dto.SolutionBottomVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SpaceMark;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-03-24 21:32
 */
@Data
public class DraftSimpleRequestPage {

    @ApiModelProperty("草稿总份数")
    private Integer totalRecords;

    @ApiModelProperty("签约总计")
    private Integer signRecords;

    @ApiModelProperty("页码")
    private Integer totalPages;

    @ApiModelProperty("用户是否已预确认方案，0:未预确认  1：已确认")
    private Integer preConfirmed;//用户是否已预确认方案，0:未预确认  1：已确认

    @ApiModelProperty("用户是否已交钱")
    private boolean hasPayed;

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("订单子状态")
    private Integer orderSubStatus;

    @ApiModelProperty("下单类型 1create 2 update")
    private Integer createOrderType = 1;

    @ApiModelProperty("签约入口展示与否 1展示 2 不展示")
    private Integer signEnter = 1;

    @ApiModelProperty("订单是否交完全款 0否  1是")
    private Integer allMoney = 0;

    @ApiModelProperty("未交清全款的老订单 0 否 1是")
    private Integer unAllMoneyAndUnLocked = 0;

    private List<DraftSimpleRequest> orderDraftSimpleList;

    @ApiModelProperty("空间标识图")//若不存在，取户型图
    private String houseTypeImage;

    @ApiModelProperty("户型空间标识")
    private List<SpaceMark> spaceMarkList;

    @ApiModelProperty("是否有待分配、待确认、设计中的任务")
    private Boolean hasTask = Boolean.FALSE;

    @ApiModelProperty("是否是艾佳贷签约")
    private Boolean isLoan = false;

    @ApiModelProperty("邻居家方案数量")
    private Long neighborProgramNum = 0L;

    //只在专属方案页面有效，邻居家方案页面不用关心该字段
    @ApiModelProperty("底部显示内容 1 显示邻居家方案 2显示提交设计入口")
    private Integer bottomFlag =2;

    @ApiModelProperty("底部显示内容")
    private SolutionBottomVo solutionBottomVo;


}
