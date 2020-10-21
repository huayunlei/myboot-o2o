package com.ihomefnt.o2o.intf.domain.push.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.push.dto.ExtrasDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PullPersonalMessageListResponseVo {

	private List<ExtrasDto> pullPersonalMessageList;
}
