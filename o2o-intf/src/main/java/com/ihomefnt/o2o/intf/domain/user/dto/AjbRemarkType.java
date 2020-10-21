/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: Charl
 * Date: 2017/1/18
 * Description:AjbRemarkType.java
 */
package com.ihomefnt.o2o.intf.domain.user.dto;

/**
 * 艾积分备注信息
 *
 * @author Charl
 */
public enum AjbRemarkType {
	freeze1(0, 1, "[冻结]充值"), freeze2(0, 2, "[冻结]退款充值"), freeze3(0, 3, "[冻结]退款"), freeze4(0, 4, "待支付订单冻结艾积分"), freeze5(0, 5, "[冻结]诚意金充值"), freeze6(0, 6, "[冻结]提现"), freeze7(0,7,"申请退款冻结艾积分"),
    recorded1(1, 1, "交款赠送艾积分"), recorded2(1, 2, "取消订单返还艾积分"), recorded3(1, 3, "[已入账]退款"), recorded4(1, 4, "支付订单支出艾积分"), recorded5(1, 5, "[已入账]诚意金充值"), recorded6(1, 6, "[已入账]提现"), recorded7(1,7,"退款回收艾积分"),
    cancel1(2, 1, "[取消]充值"), cancel2(2, 2, "[取消]退款充值"), cancel3(2, 3, "[取消]退款"), cancel4(2, 4, "取消订单解冻艾积分"), cancel5(2, 5, "[取消]诚意金充值"), cancel6(2, 6, "[取消]提现"), cancel7(2,7,"取消退款解冻艾积分");

    private Integer status; //状态：0.冻结  1.已入账  2.取消

    private Integer type; // 类型：1.充值 2.退款充值 3.退款 4.支付订单5.诚意金充值（转化）6.提现 7.回收

    private String remark; //备注

    public static String getRemark(Integer status, Integer type) {
        for (AjbRemarkType ajbRemarkType : AjbRemarkType.values()) {
            if (ajbRemarkType.getStatus().equals(status) && ajbRemarkType.getType().equals(type)) {
                return ajbRemarkType.getRemark();
            }
        }
        return "";
    }

    AjbRemarkType(Integer status, Integer type, String remark) {
        this.status = status;
        this.type = type;
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

