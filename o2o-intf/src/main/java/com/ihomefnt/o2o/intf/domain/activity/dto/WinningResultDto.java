package com.ihomefnt.o2o.intf.domain.activity.dto;

import lombok.Data;

import java.util.List;

/**
 * 1219贺卡中奖结果
 * @author ZHAO
 */
@Data
public class WinningResultDto {
	private List<GreetingCardRecordDto> winningResultList;
}
