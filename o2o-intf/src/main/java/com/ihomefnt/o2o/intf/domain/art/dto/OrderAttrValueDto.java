package com.ihomefnt.o2o.intf.domain.art.dto;

import java.io.Serializable;

public class OrderAttrValueDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7496198748232753582L;

    /**
     *主键
     */
    private Integer idOrderAttrvalue;

    /**
     *订单id
     */
    private Integer fidOrder;

    /**
     *订单属性
     */
    private Integer orderType;

    /**
     *属性id
     */
    private Integer fidtOrderAttr;

    /**
     *属性名
     */
    private String attrName;

    /**
     *属性值
     */
    private String attrValue;

    public Integer getIdOrderAttrvalue() {
        return idOrderAttrvalue;
    }

    public void setIdOrderAttrvalue(Integer idOrderAttrvalue) {
        this.idOrderAttrvalue = idOrderAttrvalue;
    }

    public Integer getFidOrder() {
        return fidOrder;
    }

    public void setFidOrder(Integer fidOrder) {
        this.fidOrder = fidOrder;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getFidtOrderAttr() {
        return fidtOrderAttr;
    }

    public void setFidtOrderAttr(Integer fidtOrderAttr) {
        this.fidtOrderAttr = fidtOrderAttr;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
}
