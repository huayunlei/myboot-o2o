package com.ihomefnt.o2o.intf.manager.constant;

/**
 * 个人中心用户权益，对应图片枚举
 *
 * @author liyonggang
 * @create 2019-02-22 21:38
 */
public enum UserRightsForPersonalCenterEnum {

    COMMON(0, "http://pnip3dmei.bkt.clouddn.com/rights_common.png", "普通"),
    GOLD(1, "http://pnip3dmei.bkt.clouddn.com/rights_gold.png", "黄金"),
    PLATINUM(2, "http://pnip3dmei.bkt.clouddn.com/rights_platinum.png", "铂金"),
    DIAMOND(3, "http://pnip3dmei.bkt.clouddn.com/rights_diamond.png", "钻石"),
    SILVER(4, "", "白银"),
    BRONZE(5, "", "青铜");
    Integer gradeId;

    String rightsBackgroundPic;

    String gradeName;

    UserRightsForPersonalCenterEnum(Integer gradeId, String rightsBackgroundPic, String gradeName) {
        this.gradeId = gradeId;
        this.rightsBackgroundPic = rightsBackgroundPic;
        this.gradeName = gradeName;
    }

    public Integer getGradeId() {
        return gradeId;
    }


    public String getRightsBackgroundPic() {
        return rightsBackgroundPic;
    }


    public String getGradeName() {
        return gradeName;
    }

    public static UserRightsForPersonalCenterEnum getEnumByGradeId(Integer gradeId) {
        UserRightsForPersonalCenterEnum[] values = UserRightsForPersonalCenterEnum.values();
        for (UserRightsForPersonalCenterEnum value : values) {
            if (value.gradeId.equals(gradeId)) {
                return value;
            }
        }
        return null;
    }
}
