package com.ihomefnt.o2o.intf.manager.util.common.image;

/**
 * @author xiamingyu
 * @date 2018/10/15
 */

/**
 * 图片裁剪参数
 */
public class ImageCompressParam {
    /**
     * 压缩模式
     */
    private Integer mode;

    /**
     * 尺寸模式
     */
    private Integer size;

    /**
     * 分辨率模式
     */
    private Integer resolution;

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }
}
