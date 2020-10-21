package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.programorder.dto.HardStandardItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 套系硬装标准信息返回数据
 * @author ZHAO
 */
@Data
public class HardStandardSpaceResponse {
	private String spaceName;//空间
	
	private List<HardStandardItem> itemList;//项目材质

	public HardStandardSpaceResponse() {
		this.spaceName = "";
		this.itemList = new ArrayList<>();
	}

}
