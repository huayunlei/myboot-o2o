/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.ad.vo.response;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.Timestamp;
	
/**
 * @author weitichao
 *
 */
@Data
public class AdStartPageItemResponseVo {

	private Long picId = 0L; //主键id:图片id

	private String image = ""; //启动页图片url
	
	private String buttonUrl = "";//按钮跳转URL
	
	private Integer osType = -1; //设备类型 ：1.表示android
	
	private Integer groupId = -1; //组id
	
	@JsonIgnore
	private Timestamp startTimestamp; //后台用
	
	@JsonIgnore
	private Timestamp endTimestamp; //后台用
	
	@JsonIgnore
	private Timestamp updateTimestamp; //后台用

	private Long startTime = 0L; //启动页图片生效时间

	private Long endTime = 0L; //启动页图片结束时间

	private Long updateTime = 0L; //启动页图片修改时间
}
