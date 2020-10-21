package com.ihomefnt.o2o.intf.domain.art.vo.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 专题详情返回数据
 * @author ZHAO
 */
@Data
public class ArtSubjectDetailResponse {
	private Integer id;//ID
	
	private String title;//专题主标题
	
	private String subTitle;//专题副标题
	
	private String headImgUrl;//卡片头图
	
	private String bannerUrl;//专题banner
	
	private String subjectDesc;//专题描述
	
	private String subjectImgUrl;//专题描述图片
	
	private Date createTime;//添加时间
	
	private List<SubjectProductResponse> productList;//商品信息
}
