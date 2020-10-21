/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.inspiration.dto;

import com.ihomefnt.o2o.intf.manager.constant.inspiration.InspirationConstant;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.Timestamp;

/**
 * @author zhang
 *
 */
@Data
public class ArticleComment270 {
	
	private Long commentId;//评论主键
	@JsonIgnore
	private String userName;//用户姓名
	@JsonIgnore
	private String mobile;//用户手机号码
	@JsonIgnore
	private Timestamp createTime ;//创建时间

	/**
	 * 展示规则 ：先昵称； 昵称为空显示加星星手机号码； 手机号码为空显示"一个热心网友"
	 */
	private String displayUserName;// 前台用于展示用户昵称：

	private String displayTime;// 前台用于展示评论时间

	private String comment;// 评论意见

	public String getDisplayUserName() {
		if(StringUtils.isNotBlank(userName)){
			return userName;
		}
		//加星星手机号码 规则待定
		if(StringUtils.isNotBlank(mobile)){
			if(mobile.length()>7){
				return mobile.substring(0,3)+"****"+mobile.substring(7);
			}
			
		}
		return InspirationConstant.ANONYMOUS_PERSON;
	}


	public String getDisplayTime() {
		Long second = (System.currentTimeMillis() - createTime.getTime()) / 1000;
		Long min = second / 60;
		Long hour = min / 60;
		Long day = hour / 24;
		Long month = day / 30;
		Long year = day / 365;
		if (second <= 0) {
			return "1秒前";
		}
		if (second > 0 && min == 0) {
			return second + "秒前";
		}
		if (min > 0 && hour == 0) {
			return min + "分钟前";
		}
		if (hour > 0 && day == 0) {
			return hour + "小时前";
		}
		if (day > 0 && month == 0) {
			return day + "天前";
		}
		if (month > 0 && year == 0) {
			return month + "月前";
		}
		if (year > 0) {
			return year + "年前";
		}
		return displayTime;
	}
}
