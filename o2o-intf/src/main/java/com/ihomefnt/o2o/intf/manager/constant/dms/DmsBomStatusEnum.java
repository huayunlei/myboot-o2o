package com.ihomefnt.o2o.intf.manager.constant.dms;

public enum DmsBomStatusEnum {
    WAITING_PURCHASE(1, "待采购", 1000),

    WAITING_MANUFACTURER_RECEIVE(2, "待厂家接单",3010),

    WAITING_MANUFACTURER_SHIPMENT(3, "待厂家出货",3020),

    MANUFACTURER_SHIPMENT_COMPLETED(4, "厂家出货完成",3040),

    WAITING_LOGISTICS_ORDER(5, "待创建物流订单",5000),

    WAITING_SEND_LOGISTICS(6, "待派物流单",5010),

    WAITING_ORDERS(7, "待接单",5020),

    WAITING_COLLECT(8, "待揽收",5030),

    WAITING_SHIPMENT(9, "待发货",5040),

    WAITING_ARRIVAL(10, "待到货",5050),

    WAITING_INSTALL(11, "待安装",5060),

    WAITING_CHECK(12, "待验收",5070),

    COMPLETED(13, "完结",8888),

    CANCLE(20, "取消",9999);

    private int status;

    private String name;

    private int newCode;

    DmsBomStatusEnum(int status, String name, int newCode) {
        this.status = status;
        this.name = name;
        this.newCode = newCode;
    }

    public static DmsBomStatusEnum getEnum(Integer status) {
        if (status == null) {
            return null;
        }
        for (DmsBomStatusEnum statusEnum : values()) {
            if (statusEnum.getStatus() == status) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getNameByValue(Integer value) {
        DmsBomStatusEnum anEnum = getEnum(value);
        return anEnum == null ? "" : anEnum.getName();
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getNewCode() {
        return newCode;
    }


}
