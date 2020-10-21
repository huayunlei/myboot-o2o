package com.ihomefnt.o2o.intf.manager.constant.kuaidi100;

/**
 * 快递公司编码枚举
 *
 * @author liyonggang
 * @create 2019-01-25 11:16
 */
public enum LogisticsCompanyCodeEnum {
    YUNDA("韵达快递", "yunda"),
    SHUNFENG("顺丰快递", "shunfeng"),
    DEBANG("德邦物流", "debangwuliu"),
    EMS("EMS", "ems"),
    SHENTONG("申通E物流", "shentong"),
    YUANTONG("圆通速递", "yuantong"),
    ZHONGTONG("中通速递", "zhongtong"),
    ZHAIJISONG("宅急送", "zhaijisong"),
    TIANTIAN("天天快递", "tiantian"),
    HUITONG("汇通快运", "huitongkuaidi"),
    QITA("其他", "qita");

    String name;
    String code;

    LogisticsCompanyCodeEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
