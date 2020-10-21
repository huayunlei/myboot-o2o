/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.inspiration.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author zhang
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class Article270 {

	private Long articleId;// 主键Id

	private Long articleType;// 文章类型;

	private Long designerId;// 用户Id

	private String designerNickName;// 设计师名称

	private String displayArticleType;// 文章类型,返回给前台展示

	private int displayArticleTypeNum;// 文章类型 所在索引位置

	private String imageUrl;// 文章头图图片url

	private int readCount; // 文章阅读次数

	private int praiseCount;// 文章点赞次数

	private int forwardCount;// 文章转发次数

	private int collectCount;// 文章收藏数量

	private String title;// 文章标题

	private String content;// 文章内容

	@JsonIgnore
	private Timestamp createTime;// 创建时间

	private String displayCreateTime;// 创建时间,返回给前台用
	
	private String summary;//内容摘要
	
	private int praiseStatus;//当前用户是否已经点赞：0未点赞1点赞

	public String getDisplayCreateTime() {
		if (null == this.createTime) {
			this.displayCreateTime = null;
		} else {
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			this.displayCreateTime = sdf.format(createTime);
		}
		return displayCreateTime;
	}
}
