package com.ihomefnt.o2o.intf.domain.personalneed.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.CommitDesignDnaRoom;
import com.ihomefnt.o2o.intf.domain.style.vo.request.StyleQuestionAnwserCommitRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Author: ZHAO
 * Date: 2018年5月25日
 */
@ApiModel("个性化需求提交参数")
@Data
public class CommitDesignRequest extends HttpBaseRequest {

    @ApiModelProperty("订单Id")
    private Integer orderId;

    @ApiModelProperty("家装预算")
    private String budget;

    @ApiModelProperty("已选DNA ID")
    private Integer dnaId;

    @ApiModelProperty("硬装质量")
    private String hardQuality;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("风格问题答案集")
    private List<StyleQuestionAnwserCommitRequest> styleQuestionAnwserList;

    @ApiModelProperty("已选DNA空间")
    private List<CommitDesignDnaRoom> dnaRoomList;

    @ApiModelProperty("已选DNA空间")
    private List<CommitDesignDnaRoom> taskDnaRoomList;

    private Date addTime;

    private Integer commitRecordId;

    private String designDemandId;

    private Integer status;//设计任务 111待提交 112待确认 3设计中 4已完成

    @ApiModelProperty("设计需求版本号")
    private Integer version;

    @ApiModelProperty("创建人id")
    private Integer createUserId;

    @ApiModelProperty("1  - 舒克 2  - betaApp")
    private Integer source = 1;

    @ApiModelProperty("新户型id")
    private Integer newLayoutId;

    private Integer taskStatus;//-2 待确认

    private String createTimeStr;

    private Integer id;

    @ApiModelProperty("订单Id")
    private Integer orderNum;

    @ApiModelProperty("提交涞源")
    private Integer submitSource;

    public Integer getSource() {
        return source == null ? submitSource : source;
    }

    public Integer getSubmitSource() {
        return submitSource == null ? source : submitSource;
    }

    public List<CommitDesignDnaRoom> getDnaRoomList() {
        return dnaRoomList == null ? taskDnaRoomList : dnaRoomList;
    }

    public List<CommitDesignDnaRoom> getTaskDnaRoomList() {
        return taskDnaRoomList == null ? dnaRoomList : taskDnaRoomList;
    }

    public Integer getOrderId() {
        return orderId == null ? orderNum : orderId;
    }

    public Integer getOrderNum() {
        return orderNum == null ? orderId : orderNum;
    }

    public Integer getStatus() {
        if (taskStatus != null && taskStatus.equals(-2)) {
            return 112;
        } else {
            return status == null ? taskStatus : status;
        }
    }
}
