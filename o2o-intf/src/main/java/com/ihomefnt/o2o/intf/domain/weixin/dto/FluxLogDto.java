/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.weixin.dto;

import com.ihomefnt.o2o.intf.domain.weixin.vo.request.HttpFluxLogRequest;
import com.ihomefnt.o2o.intf.manager.constant.weixin.FluxConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @author zhang
 * 用户领取记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode
public class FluxLogDto {

	private Long logId;// 日志Id

	private Long activityId;// 活动Id

	private String unionId;// 微信的unionId

	private String mobile;// 用户手机号码

	private Timestamp createTime;// 领取时间

	private Integer mobileType;// 号码类型：1 联通 2 非联通
	
	public FluxLogDto(HttpFluxLogRequest request){
		this.mobile =request.getMobile();
		this.unionId=request.getUnionId();
		this.activityId =FluxConstant.CODE_FLUX_ACTIVITY;
	}
}
