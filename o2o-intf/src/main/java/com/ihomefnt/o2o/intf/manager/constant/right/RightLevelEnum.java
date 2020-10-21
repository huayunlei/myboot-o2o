package com.ihomefnt.o2o.intf.manager.constant.right;

public enum RightLevelEnum {
    LEVEL_ONE(0, "普通", "https://static.ihomefnt.com/1/image/icon_normal-word.png","","https://static.ihomefnt.com/1/image/right_background.png","共7项",""),
    LEVEL_TWO(1, "黄金", "https://static.ihomefnt.com/1/image/icon_golden-pic.png","https://static.ihomefnt.com/1/image/icon_golden-word.png","https://static.ihomefnt.com/1/image/right_background.png","共19项","最高可省14166元"),
    LEVEL_THREE(2, "铂金", "https://static.ihomefnt.com/1/image/icon_platinum-pic.png","https://static.ihomefnt.com/1/image/icon_platinum-word.png","https://static.ihomefnt.com/1/image/right_background.png","共23项","最高可省25766元"),
    LEVEL_FOR(3, "钻石", "https://static.ihomefnt.com/1/image/icon_diamond-pic.png","https://static.ihomefnt.com/1/image/icon_diamond-word.png","https://static.ihomefnt.com/1/image/right_background.png","共32项","最高可省42,166元");




    private int code;
    private String name;//权益名称
    private String gradeLevelPicUrl;//权益等级图片地址
    private String gradelLevelUrl;//url图片
    private String gradeLevelBackPicUrl;//底图
    private String totalContent;//共享受特权
    private String gradeClassifyDeac;//权益描述

    RightLevelEnum(int code, String name, String gradeLevelPicUrl, String gradelLevelUrl, String gradeLevelBackPicUrl, String totalContent, String gradeClassifyDeac) {
        this.code = code;
        this.name = name;
        this.gradeLevelPicUrl = gradeLevelPicUrl;
        this.gradelLevelUrl = gradelLevelUrl;
        this.gradeLevelBackPicUrl = gradeLevelBackPicUrl;
        this.totalContent = totalContent;
        this.gradeClassifyDeac = gradeClassifyDeac;
    }


    public static String getGradelLevelUrl(int code) {
        RightLevelEnum[] values = values();
        for (RightLevelEnum v : values) {
            if (v.getCode() == code) {
                return v.getGradelLevelUrl();
            }
        }
        return null;
    }

    public static String getTotalContent(int code) {
        RightLevelEnum[] values = values();
        for (RightLevelEnum v : values) {
            if (v.getCode() == code) {
                return v.getTotalContent();
            }
        }
        return null;
    }

    public static String getGradeClassifyDeac(int code) {
        RightLevelEnum[] values = values();
        for (RightLevelEnum v : values) {
            if (v.getCode() == code) {
                return v.getGradeClassifyDeac();
            }
        }
        return null;
    }

    public static String getName(int code) {
        RightLevelEnum[] values = values();
        for (RightLevelEnum v : values) {
            if (v.getCode() == code) {
                return v.getName();
            }
        }
        return null;
    }

    public static String getGradeLevelPicUrl(int code) {
        RightLevelEnum[] values = values();
        for (RightLevelEnum v : values) {
            if (v.getCode() == code) {
                return v.getGradeLevelPicUrl();
            }
        }
        return null;
    }

    public static String getGradeLevelBackPicUrl(int code) {
        RightLevelEnum[] values = values();
        for (RightLevelEnum v : values) {
            if (v.getCode() == code) {
                return v.getGradeLevelBackPicUrl();
            }
        }
        return null;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGradeLevelPicUrl() {
        return gradeLevelPicUrl;
    }

    public void setGradeLevelPicUrl(String gradeLevelPicUrl) {
        this.gradeLevelPicUrl = gradeLevelPicUrl;
    }

    public String getGradelLevelUrl() {
        return gradelLevelUrl;
    }

    public void setGradelLevelUrl(String gradelLevelUrl) {
        this.gradelLevelUrl = gradelLevelUrl;
    }

    public String getGradeLevelBackPicUrl() {
        return gradeLevelBackPicUrl;
    }

    public void setGradeLevelBackPicUrl(String gradeLevelBackPicUrl) {
        this.gradeLevelBackPicUrl = gradeLevelBackPicUrl;
    }

    public String getTotalContent() {
        return totalContent;
    }

    public void setTotalContent(String totalContent) {
        this.totalContent = totalContent;
    }

    public String getGradeClassifyDeac() {
        return gradeClassifyDeac;
    }

    public void setGradeClassifyDeac(String gradeClassifyDeac) {
        this.gradeClassifyDeac = gradeClassifyDeac;
    }
}
