package com.ihomefnt.o2o.intf.manager.constant.right;

import io.swagger.annotations.ApiModelProperty;

public enum RightLevelNewEnum {
    LEVEL_ONE(0, "普通","https://img13.ihomefnt.com/9940ec7f71a2fb1adf1e6636836bfb09991f68e8b696bb12cab448f5033108eb.png!H-SMALL","https://img13.ihomefnt.com/05a105eb650338a2b142a2f1c4a04274551794c14014f1036ecf4946868be582.png!M-MIDDLE","", "https://static.ihomefnt.com/1/image/icon_normal-word.png","","https://static.ihomefnt.com/1/image/right_background.png","共6项",""),
    LEVEL_TWO(1, "黄金","https://img13.ihomefnt.com/9940ec7f71a2fb1adf1e6636836bfb097fd011566433fab7ab3056836f88c4bc.png!H-SMALL","https://img13.ihomefnt.com/05a105eb650338a2b142a2f1c4a04274bd4454b82c222b6e44bdb2beeda86bc4.png!M-MIDDLE", "https://img13.ihomefnt.com/78b21b0ac49e74f7e95f807c3ed389cb0d3f1d4f31f3c926c7972f26188b6dd2.png!thum-100x100","https://static.ihomefnt.com/1/image/icon_golden-pic.png","https://static.ihomefnt.com/1/image/icon_golden-word.png","https://static.ihomefnt.com/1/image/right_background.png","共15项",""),
    LEVEL_THREE(2, "铂金","https://img13.ihomefnt.com/9940ec7f71a2fb1adf1e6636836bfb09d2133d634e74b75e0309e757d74bbabf.png!H-SMALL","https://img13.ihomefnt.com/05a105eb650338a2b142a2f1c4a042749063e843edf2bb43e9c8d98bfa1b3a61.png!M-MIDDLE","https://img13.ihomefnt.com/78b21b0ac49e74f7e95f807c3ed389cb08d093366ff8c1686364e1a355aabe1b.png!thum-100x100", "https://static.ihomefnt.com/1/image/icon_platinum-pic.png","https://static.ihomefnt.com/1/image/icon_platinum-word.png","https://static.ihomefnt.com/1/image/right_background.png","共15项",""),
    LEVEL_FOR(3, "钻石","https://img13.ihomefnt.com/9940ec7f71a2fb1adf1e6636836bfb095d7aa696e500cd8758dfb85ce2e144db.png!H-SMALL","https://img13.ihomefnt.com/9940ec7f71a2fb1adf1e6636836bfb09260c481e48790a1c5bc9630d8053dc7a.png!M-MIDDLE", "https://img13.ihomefnt.com/78b21b0ac49e74f7e95f807c3ed389cb0f37e5cdc6e90b3423676bf22ada06d2.png!thum-100x100","https://static.ihomefnt.com/1/image/icon_diamond-pic.png","https://static.ihomefnt.com/1/image/icon_diamond-word.png","https://static.ihomefnt.com/1/image/right_background.png","共15项",""),
    LEVEL_FIV(4, "白银","https://img13.ihomefnt.com/9940ec7f71a2fb1adf1e6636836bfb090764bf0b89befe7f376218f0004c2da3.jpg!H-SMALL","https://img13.ihomefnt.com/9940ec7f71a2fb1adf1e6636836bfb09bc226ef24365f5fd7541a0366352c0fe.png!M-MIDDLE", "https://img13.ihomefnt.com/78b21b0ac49e74f7e95f807c3ed389cb17d5d9b51d910b17266865ed32451944.png!thum-100x100","https://img13.ihomefnt.com/78b21b0ac49e74f7e95f807c3ed389cb41c00785104db641e9ec704329d20e21.png!H-SMALL","https://img13.ihomefnt.com/78b21b0ac49e74f7e95f807c3ed389cb9a2a8e6f1ac2188f1bab31431a1a9b5d.png!H-SMALL","https://static.ihomefnt.com/1/image/right_background.png","共9项","仅现金客户可享受专属权益"),
    LEVEL_SIX(5, "青铜","https://img13.ihomefnt.com/9940ec7f71a2fb1adf1e6636836bfb0993cceb5599a63518e38993cc974c0aed.png!H-SMALL","https://img13.ihomefnt.com/9940ec7f71a2fb1adf1e6636836bfb09778bedc8b76a67a05fe250f5f94d9eb2.png!M-MIDDLE", "https://img13.ihomefnt.com/78b21b0ac49e74f7e95f807c3ed389cba9616f51b45d369410a8cb4369b6c9e3.png!thum-100x100","https://img13.ihomefnt.com/78b21b0ac49e74f7e95f807c3ed389cb280fc0c539bc41a7914be118845cd5b7.png!H-SMALL","https://img13.ihomefnt.com/78b21b0ac49e74f7e95f807c3ed389cbb8fa2dd9204df3087c1b88e6942106be.png!H-SMALL","https://static.ihomefnt.com/1/image/right_background.png","共8项","仅限全品家软用户");




    private int code;
    private String name;//权益名称
    @ApiModelProperty("权益等级图片")
    private String gradeNameUrl;
    @ApiModelProperty("权益等级背景图")
    private String gradeBackGround;
    private String gradeLevelIcoUrl;//权益等级小图标
    private String gradeLevelPicUrl;//权益等级图片地址
    private String gradelLevelUrl;//url图片
    private String gradeLevelBackPicUrl;//底图
    private String totalContent;//共享受特权
    private String gradeClassifyDeac;//权益描述

    RightLevelNewEnum(int code, String name,String gradeNameUrl,String gradeBackGround,String gradeLevelIcoUrl, String gradeLevelPicUrl, String gradelLevelUrl, String gradeLevelBackPicUrl, String totalContent, String gradeClassifyDeac) {
        this.code = code;
        this.name = name;
        this.gradeNameUrl = gradeNameUrl;
        this.gradeBackGround = gradeBackGround;
        this.gradeLevelIcoUrl = gradeLevelIcoUrl;
        this.gradeLevelPicUrl = gradeLevelPicUrl;
        this.gradelLevelUrl = gradelLevelUrl;
        this.gradeLevelBackPicUrl = gradeLevelBackPicUrl;
        this.totalContent = totalContent;
        this.gradeClassifyDeac = gradeClassifyDeac;
    }

    public static String getGradeNameUrl(int code) {
        RightLevelNewEnum[] values = values();
        for (RightLevelNewEnum v : values) {
            if (v.getCode() == code) {
                return v.getGradeNameUrl();
            }
        }
        return null;
    }

    public static String getGradeBackGround(int code) {
        RightLevelNewEnum[] values = values();
        for (RightLevelNewEnum v : values) {
            if (v.getCode() == code) {
                return v.getGradeBackGround();
            }
        }
        return null;
    }

    public static String getGradeLevelIcoUrl(int code) {
        RightLevelNewEnum[] values = values();
        for (RightLevelNewEnum v : values) {
            if (v.getCode() == code) {
                return v.getGradeLevelIcoUrl();
            }
        }
        return null;
    }

    public static String getGradelLevelUrl(int code) {
        RightLevelNewEnum[] values = values();
        for (RightLevelNewEnum v : values) {
            if (v.getCode() == code) {
                return v.getGradelLevelUrl();
            }
        }
        return null;
    }

    public static String getTotalContent(int code) {
        RightLevelNewEnum[] values = values();
        for (RightLevelNewEnum v : values) {
            if (v.getCode() == code) {
                return v.getTotalContent();
            }
        }
        return null;
    }

    public static String getGradeClassifyDeac(int code) {
        RightLevelNewEnum[] values = values();
        for (RightLevelNewEnum v : values) {
            if (v.getCode() == code) {
                return v.getGradeClassifyDeac();
            }
        }
        return null;
    }

    public static String getName(int code) {
        RightLevelNewEnum[] values = values();
        for (RightLevelNewEnum v : values) {
            if (v.getCode() == code) {
                return v.getName();
            }
        }
        return null;
    }

    public static String getGradeLevelPicUrl(int code) {
        RightLevelNewEnum[] values = values();
        for (RightLevelNewEnum v : values) {
            if (v.getCode() == code) {
                return v.getGradeLevelPicUrl();
            }
        }
        return null;
    }

    public static String getGradeLevelBackPicUrl(int code) {
        RightLevelNewEnum[] values = values();
        for (RightLevelNewEnum v : values) {
            if (v.getCode() == code) {
                return v.getGradeLevelBackPicUrl();
            }
        }
        return null;
    }

    public String getGradeNameUrl() {
        return gradeNameUrl;
    }

    public void setGradeNameUrl(String gradeNameUrl) {
        this.gradeNameUrl = gradeNameUrl;
    }

    public String getGradeBackGround() {
        return gradeBackGround;
    }

    public void setGradeBackGround(String gradeBackGround) {
        this.gradeBackGround = gradeBackGround;
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

    public String getGradeLevelIcoUrl() {
        return gradeLevelIcoUrl;
    }

    public void setGradeLevelIcoUrl(String gradeLevelIcoUrl) {
        this.gradeLevelIcoUrl = gradeLevelIcoUrl;
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
