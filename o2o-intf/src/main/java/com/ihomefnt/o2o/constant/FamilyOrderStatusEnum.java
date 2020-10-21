package com.ihomefnt.o2o.constant;


/**
 * @author liyonggang
 * @create 2019-08-19 16:52
 */
public enum FamilyOrderStatusEnum {
    ORDER_STATUS_PRE_DELIVERY(12, "待交付"),
    ORDER_STATUS_CONTACT_STAGE(13, "接触阶段"),
    ORDER_STATUS_INTENTIONAL_PHASE(14, "意向阶段"),
    ORDER_STATUS_DEPOSIT_PHASE(15, "定金阶段"),
    ORDER_STATUS_SIGNING_STAGE(16, "签约阶段"),
    ORDER_STATUS_IN_DELIVERY(17, "交付中"),
    ORDER_STATUS_COMPLETED(2, "已完成"),
    ORDER_STATUS_CANCELED(3, "已取消"),

    /**
     * 签约状态子状态
     **/
    ORDER_STATUS_UN_SIGNING(161, "未签约"),
    ORDER_STATUS_SIGNING(162, "签约中"),
    ORDER_STATUS_SIGNED(163, "签约成功");

    /**
     * 后续扩展子状态请注意状态规范，大状态ID+子状态ID，比如：161 签约阶段-未签约 大状态16+小状态1
     **/
    private Integer status;

    private String description;

    FamilyOrderStatusEnum(Integer status, String description) {
        this.status = status;
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }}