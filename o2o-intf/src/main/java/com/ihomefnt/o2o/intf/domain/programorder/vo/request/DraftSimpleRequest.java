package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SolutionEffectInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-03-21 09:56
 */
@ApiModel("草稿简易信息")
@Data
@Accessors(chain = true)
public class DraftSimpleRequest extends SolutionEffectInfo {


    /**
     * draftName : 草稿名称
     * styleName : 欧式
     * createTime : 2019-01-12 11:11:11
     * totalPrice : 22222
     * orderNum : 1122
     * draftProfileNum : 1553090346690
     * status : 1
     * url : xx
     */

    @ApiModelProperty("草稿id")
    private String draftId;

    @ApiModelProperty("草稿名称")
    private String draftName;

    @ApiModelProperty("风格名称")
    private String styleName;

    @ApiModelProperty("创建时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("草稿方案总价")
    private Integer totalPrice;

    @ApiModelProperty("订单编号")
    private Integer orderNum;

    @ApiModelProperty("下单类型:1-APP方案下单，2-爱家贷专用方案下单，3-方案代客下单，4-商品代客下单")
    private Integer purchaseType = 1;

    @ApiModelProperty("草稿编号")
    private long draftProfileNum;

    @ApiModelProperty("状态 // 1已签约；2未签约；3历史签约 4.最新草稿")
    private Integer draftSignStatus;

    @ApiModelProperty("图片地址")
    private String url;

    @ApiModelProperty("方案类型 0软装+硬装 1纯软装")
    private Integer decorationType;

    @ApiModelProperty("草稿离线渲染主任务状态 0:没有渲染任务 1:未开始，2:进行中，3：已完成，4：失败")
    private Integer masterTaskStatus = 0;

    @ApiModelProperty("渲染完成是否已读 1:未读，2:已读，为空忽略")
    private Integer markRead;

    @ApiModelProperty("-1:失效（老订单） 0：锁价中 1：最终锁价")
    private Integer lockPriceFlag;

    @ApiModelProperty("锁价倒计时")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lockPriceExpireTime;

    @ApiModelProperty("锁价倒计时 单位秒")
    private Integer priceLockCount;

    @ApiModelProperty("预计渲染完成时间")
    private String preMasterTaskFinishTime;

    @ApiModelProperty("户型版本")
    private Long apartmentVersion;

    @ApiModelProperty("户型ID")
    private Long apartmentId;

    @ApiModelProperty("是否是拆改方案 0 不是 1 是")
    private Integer reformFlag = 0;

    @ApiModelProperty("方案ID")
    private Integer solutionId;

    @ApiModelProperty("是否需要展示2018年8月前的设计方案，不支持加入对比 1不展示 0展示")
    private Integer standardHardSolutionFlag = 1;

    @ApiModelProperty("选配方案列表")
    private List<Integer> solutionIdList;

    @ApiModelProperty("方案状态 4是上架")
    private Integer solutionStatus = 4;

    @ApiModelProperty("数据类型：方案：1，草稿：2")
    private Integer type = 2;

    @ApiModelProperty("导读页是否已读  0:未读，1：已读")
    private Integer hasRead = 0;

    public DraftSimpleRequest(String draftId) {
        this.draftId = draftId;
    }

    public DraftSimpleRequest() {
    }

    public Long timeNum() {
        return this.getUpdateTime() == null ? 0 : this.getUpdateTime().getTime();
    }
}
