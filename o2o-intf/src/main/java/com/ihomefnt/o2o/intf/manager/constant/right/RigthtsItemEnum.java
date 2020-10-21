package com.ihomefnt.o2o.intf.manager.constant.right;

/**
 * @author wanyunxin
 * @create 2020-01-02 13:56
 */
public enum RigthtsItemEnum {

    LEVEL_ONE(2, "定制设计","https://img13.ihomefnt.com/arts-centre/UserArt/b4f4092213e97d715f8d6fccf8c7df296ad29dd73c040ad36aed540d8d06ba3b.png","https://img13.ihomefnt.com/arts-centre/UserArt/2237c352bc6e4745d55ef0379219922a8e1c6a8d147747eedcee513f9adb8f68.png"),
    LEVEL_TWO(4, "开工派对","https://img13.ihomefnt.com/arts-centre/UserArt/b4f4092213e97d715f8d6fccf8c7df293ab91086df3349ba7468bc113a2a0f03.png","https://img13.ihomefnt.com/arts-centre/UserArt/2237c352bc6e4745d55ef0379219922ad20f2f7b0ed540dd235a9ed8bd4439bc.png"),
    LEVEL_THREE(5, "甲醛监测与治理","https://img13.ihomefnt.com/arts-centre/UserArt/b4f4092213e97d715f8d6fccf8c7df298a9ae8602600c06ce4088b15407c1d09.png","https://img13.ihomefnt.com/arts-centre/UserArt/2237c352bc6e4745d55ef0379219922a9a7b444eb0334a7829e2760cea2c0f3b.png"),
    LEVEL_FOR(7, "艾监理","https://img13.ihomefnt.com/arts-centre/UserArt/b4f4092213e97d715f8d6fccf8c7df2990b6c305b4e860adebca7db984fe6a5a.png","https://img13.ihomefnt.com/arts-centre/UserArt/2237c352bc6e4745d55ef0379219922ab22c659dda1245a2d17c8a74f605a422.png"),
    LEVEL_FIV(33, "开荒保洁","https://img13.ihomefnt.com/arts-centre/UserArt/b4f4092213e97d715f8d6fccf8c7df29e709b47eaef55a7353ea6dd24658761d.png","https://img13.ihomefnt.com/arts-centre/UserArt/2237c352bc6e4745d55ef0379219922a7fc78ad7eea089477d9535ad9e9a4976.png"),
    LEVEL_SIX(35, "艾积分","https://img13.ihomefnt.com/arts-centre/UserArt/b4f4092213e97d715f8d6fccf8c7df29f2c63e763d53330270b903b562839b93.png","https://img13.ihomefnt.com/arts-centre/UserArt/2237c352bc6e4745d55ef0379219922a2f4a1d748d8edc264e302901b474f8cd.png");
    private int code;
    private String name;//权益名称
    private String url;//权益图片地址
    private String urlNew;//权益图片地址

    RigthtsItemEnum(int code, String name, String url, String urlNew) {
        this.code = code;
        this.name = name;
        this.url = url;
        this.urlNew = urlNew;
    }

    public String getUrlNew() {
        return urlNew;
    }

    public void setUrlNew(String urlNew) {
        this.urlNew = urlNew;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static String getItemUrl(int code) {
        RigthtsItemEnum[] values = values();
        for (RigthtsItemEnum v : values) {
            if (v.getCode() == code) {
                return v.getUrl();
            }
        }
        return null;
    }

    public static String getItemUrlNew(int code) {
        RigthtsItemEnum[] values = values();
        for (RigthtsItemEnum v : values) {
            if (v.getCode() == code) {
                return v.getUrlNew();
            }
        }
        return null;
    }
}
