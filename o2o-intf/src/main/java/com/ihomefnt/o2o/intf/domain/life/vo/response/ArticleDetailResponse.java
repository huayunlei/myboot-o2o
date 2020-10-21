package com.ihomefnt.o2o.intf.domain.life.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文章详情返回数据
 */
@Data
public class ArticleDetailResponse {

	/**
	 * 文章ID
	 */
	private Integer articleId;
	/**
	 * 标题
	 */
	private String title;

	/**
	 * 作者ID
	 */
	private long authorId;


	/**
	 * 作者笔名
	 */
	private String authorName;

	/**
	 * 作者简介
	 */
	private String authorIntroduce;

	/**
	 * 作者头像
	 */
	private String authorHeadUrl;

	/**
	 * 封面图片
	 */
	private String coverUrl;

	/**
	 * 摘要
	 */
	private String summary;

	/**
	 * 文章类型：1.音频图文 2.图文内容
	 */
	private int contentType;

	/**
	 * 音频文件地址
	 */
	private String audioUrl;

	/**
	 * 文章正文
	 */
	private String content;

	/**
	 * 发布时间
	 */
	private String statusTime;

	/**
	 * 创建时间
	 */
	private String createTime;




	/**
	 * 点赞数
	 */
	private int praiseCount;

	/**
	 * 用户是否点赞 0：未点赞 1：已点赞 默认未点赞
	 */
	private String praised = "0";

	/**
	 * 评论数
	 */
	@ApiModelProperty("评论数")
	private int commentCount;
}
