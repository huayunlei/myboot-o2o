package com.ihomefnt.o2o.intf.domain.homepage.dto;

import lombok.Data;

import java.util.Date;

/**
 * Banner
 * Author: ZHAO
 * Date: 2018年7月20日
 */
@Data
public class BannerResponseVo {
	// id
	private Integer id;

	// 标题
	private String title;

	// 大版本
	private Integer bigVersion;

	// 中版本
	private Integer midVersion;

	// 小版本
	private Integer smallVersion;

	// 介绍
	private String introduction;

	// 图片链接
	private String img;

	// 跳转类型:0 H5链接 ,1:晒家专题
	private Integer linkType;

	// 专题id
	private String fk;

	// 图片跳转
	private String url;

	// 排序
	private Integer sortBy;

	// 创建时间
	private Date createTime;

	// 修改时间
	private Date updateTime;

	// 删除标题
	private Integer deleteFlag;
}
