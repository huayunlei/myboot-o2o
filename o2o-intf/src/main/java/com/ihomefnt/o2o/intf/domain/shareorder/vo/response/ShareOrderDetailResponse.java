package com.ihomefnt.o2o.intf.domain.shareorder.vo.response;

import java.util.Date;
import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.Id;

import com.ihomefnt.o2o.intf.domain.program.dto.ImageEntity;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.ShareOrderComment;

/**
 * 晒家详情返回数据
 * 
 * @author ZHAO
 */
@Data
public class ShareOrderDetailResponse {
	/**
	 * 晒单mongoId
	 */
	@Id
	private String shareOrderId;
	/**
	 * 用户id
	 */
	private long userId;

	/**
	 * 用户昵称
	 */
	private String userNickName;

	/**
	 * 用户头像
	 */
	private String userImgUrl;

	/**
	 * 用户手机号码
	 */
	private String phoneNum;

	/**
	 * 展示在前端的名称，若有用户昵称则为用户昵称，否则为用户手机号码 only for front end ,no meaning for
	 * backend
	 */
	private String showName;

	/**
	 * 晒单文字内容
	 */
	private String textContent;

	/**
	 * 晒单图片内容
	 */
	private List<String> imgContent;

	// 大小图
	private List<ImageEntity> imgObj;

	/**
	 * 晒单生成时间
	 */
	private long createTime;

	/**
	 * 晒单生成时间文字 eg. 5分钟前
	 */
	private String createTimeText;

	/**
	 * 晒单点赞数
	 */
	private int praiseNum;

	/**
	 * 用户是否点赞 0：未点赞 1：已点赞 默认未点赞
	 */
	private String praised = "0";

	/**
	 * 用户评论
	 */
	private List<ShareOrderComment> comments;

	private int stickyPostNum; // 精华贴标识:1精华贴 0普通贴, 默认0

	private Integer deleteFlag;// 删除标志（-1已删除 0未删除）

	private Date deleteTime;// 删除时间

	private String cityName;// 城市名称

	private String buildingAddress;// 楼盘地址

	private Integer replyNum;// 回复数量

	private Integer canDeleteFlag;// 是否可删除标志 0不可删除 1可删除

	private String userOfficialName;// 用户官方回复名称

	private int type = 0;// 0:表示老晒家 1:表示楼盘运营

	private BuildingtopicViewResponse buildingTopicData;// 楼盘运营
}
