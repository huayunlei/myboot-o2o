package com.ihomefnt.o2o.intf.domain.inspiration.dto;

import lombok.Data;

@Data
public class PictureInfo {
	private Long pictureId;
    private String imageUrl;
    private int width;//原始宽度
    private int height;//原始高度
    private String roomId;//空间id
    private String type;//风格
    private int curPicWidt;//图片宽度
    private int curPicHeight;//图片高度
    private int collection;//是否已收藏0：未收藏1：收藏
}
