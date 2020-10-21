package com.ihomefnt.o2o.intf.manager.util.common.image;

import java.math.BigDecimal;

/**
 * @author xiamingyu
 * @date 2018/8/4
 */

public class Picture {

    private String url;

    private Integer width;

    private Integer height;

    private BigDecimal aspectRatio;

    public Picture(){

    }

    public Picture(String url, Integer width, Integer height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public BigDecimal getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(BigDecimal aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    /**
     * 保留两位小数,进行四舍五入
     * @param d
     * @return
     */
    public BigDecimal getRatio(){
        if(width>0 && height>0){
            BigDecimal w = new BigDecimal(width);
            BigDecimal h = new BigDecimal(height);
            BigDecimal ratio = w.divide(h,3,BigDecimal.ROUND_HALF_UP);
            return ratio;
        }
        return new BigDecimal(0);

    }
}
