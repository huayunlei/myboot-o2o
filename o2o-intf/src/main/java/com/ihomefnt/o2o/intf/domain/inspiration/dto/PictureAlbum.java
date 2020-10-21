package com.ihomefnt.o2o.intf.domain.inspiration.dto;

import lombok.Data;

import java.util.List;
@Data
public class PictureAlbum {
	private Long albumId;
	private String albumName;
	private String url;      //图片url
    private String styleName;    //风格名
    private int width;          //宽
    private int height;         //高
    private String createTime;  //创建时间
    private String status;      //状态
    
    private List<PictureInfo> pictureInfoList ;
    private String roomId;      //空间id
	private int readCount;      //阅读次数
	private int transpondCount;  //转发次数
	private int imageSetFlag;    //是否图集
	private int collection;
	
}
