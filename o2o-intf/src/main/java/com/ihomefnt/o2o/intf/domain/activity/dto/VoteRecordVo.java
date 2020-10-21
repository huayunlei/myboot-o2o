package com.ihomefnt.o2o.intf.domain.activity.dto;

import lombok.Data;

/**
 * 1219活动 投票记录
 * @author ZHAO
 */
@Data
public class VoteRecordVo {
	private String nickName;//昵称
	
	private String voteDayTimeStr;//投票时间  11月19日
	
	private String voteHourTimeStr;//投票时间  10:45
}
