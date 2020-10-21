package com.ihomefnt.o2o.intf.domain.activity.vo.response;

import lombok.Data;

/**
 * 投票记录信息
 * @author ZHAO
 */
@Data
public class VoteRecordResponse {
	private String nickName;//昵称
	
	private String voteDayTimeStr;//投票时间  11月19日
	
	private String voteHourTimeStr;//投票时间  10:45
}
