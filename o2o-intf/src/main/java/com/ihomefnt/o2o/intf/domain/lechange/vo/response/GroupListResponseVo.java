package com.ihomefnt.o2o.intf.domain.lechange.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.lechange.vo.request.GroupVo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupListResponseVo {
	
	private List<GroupVo> groupList;

}
