package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ihomefnt.o2o.intf.domain.programorder.dto.OrderRefundInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 全品家订单详情
 *
 * @author liyonggang
 * @create 2019-05-06 10:50
 */
@Data
@ApiModel("全品家订单详情new")
@Accessors(chain = true)
public class FamilyOrderDetailResponse {

    @ApiModelProperty("用户信息")
    private UserInfo userInfo;

    @ApiModelProperty("房产信息")
    private HouseInfo houseInfoResponseVo;

    @ApiModelProperty("方案信息")
    private SolutionInfo solutionInfo;

    @ApiModelProperty("交付信息")
    private List<Node> deliveryNode;

    @ApiModelProperty("订单信息")
    private OrderInfo orderInfo;

    @ApiModelProperty("转化动作入口信息")
    private TransformInfo transformInfo;

    @ApiModelProperty("订单退款信息")
    private OrderRefundInfo orderRefundInfo;// 订单退款信息


    @Data
    @ApiModel("全品家订单—房产信息")
    public static class HouseInfo {

        @ApiModelProperty("楼盘地址")
        private String buildingAddress;//楼盘地址

        @ApiModelProperty("维修地址")
        private String maintainAddress;//维修地址

        @ApiModelProperty("楼盘名称")
        private String buildingName;//楼盘名称

        @ApiModelProperty("户型名称")
        private String houseName;//户型名称

        @ApiModelProperty("户型格局")
        private String housePattern;//户型格局

        @ApiModelProperty("户型面积")
        private String houseArea;//户型面积

        @ApiModelProperty("户型全称")
        private String houseFullName;//户型全称

        @ApiModelProperty("楼盘ID")
        private Integer buildingId;//楼盘ID

        @ApiModelProperty("房产ID")
        private Integer houseId;//房产ID

        @ApiModelProperty("户型ID")
        private Integer houseTypeId;//户型ID
    }

    @Data
    @ApiModel("全品家订单-交付节点")
    public static class Node {

        @ApiModelProperty("节点名称")
        private String nodeName;

        @ApiModelProperty("节点状态 1：未开始 2：进行中 3：已完成")
        private Integer nodeStatus;

        @ApiModelProperty("节点备注")
        private String remark;

        public Node(String nodeName, Integer nodeStatus, String remark) {
            this.nodeName = nodeName;
            this.nodeStatus = nodeStatus;
            this.remark = remark;
        }

        public Node() {
        }
    }

    @Data
    @ApiModel("全品家订单-用户信息")
    public static class UserInfo {

        @ApiModelProperty("用户名")
        private String userName;

        @ApiModelProperty("用户ID")
        private Integer userId;

        @ApiModelProperty("手机号")
        private String mobile;

        @ApiModelProperty("手机号影藏中间4位")
        private String hideMobile;

        @ApiModelProperty("权益等级id")
        private Integer gradeId;

        @ApiModelProperty("权益等级名称")
        private String gradeName;

        @ApiModelProperty("权益版本号")//2020版本权益为4
        private Integer rightsVersion = 2;
    }

    @Data
    @ApiModel("全品家订单-方案信息")
    public static class SolutionInfo {

        @ApiModelProperty("图片")
        private String pictureUrl = "https://static.ihomefnt.com/1/image/solution_default.png!M-MIDDLE";

        @ApiModelProperty("方案原价")
        private BigDecimal originalPrice;

        @ApiModelProperty("订单总价(款项优化后)")
        private BigDecimal finalOrderPrice;

        @ApiModelProperty("艾升级优惠")
        private BigDecimal upgradeDisAmount;

        @ApiModelProperty("其他优惠")
        private BigDecimal otherDisAmount;

        @ApiModelProperty("款项优化后的剩余应付")
        private BigDecimal newRestAmount;

        @ApiModelProperty("方案id")
        private Integer solutionId;
    }

    @Data
    @ApiModel("全品家订单-订单信息")
    public static class OrderInfo {
        private Integer orderId;

        @ApiModelProperty("是否是老订单")
        private Boolean oldFlag = false;

        @ApiModelProperty("订单状态")
        private Integer state;

        @ApiModelProperty("订单类型 11套装、硬装+软装 12套装、纯软装 13自由搭配、硬装+软装 14自由搭配、纯软装")
        private Integer orderType;

        @ApiModelProperty(" 订单来源6：代客下单 其它：方案下单")
        private Integer source;// 订单来源6：代客下单 其它：方案下单

        @ApiModelProperty("创建时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date createTime;

        @ApiModelProperty("更新时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date updateTime;

        @ApiModelProperty("已付金额")
        private BigDecimal paidAmount;
    }


    @Data
    @ApiModel("全品家订单-转化动作")
    public static class TransformInfo {
        @ApiModelProperty("联系客服入口 0 不显示 1显示")
        private Integer imEntry = 1;

        @ApiModelProperty("交定金入口 0 不显示 1显示")
        private Integer downEntry = 0;

        @ApiModelProperty("去支付入口 0 不显示 1显示")
        private Integer payEntry = 0;

    }

}
