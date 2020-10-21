package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.homecard.dto.HardStandardDetailVo;
import lombok.Data;

import java.util.List;

/**
 * 硬装标准空间
 * @author ZHAO
 */
@Data
public class HardStandardSpaceResponseVo {
	private String spaceName;//空间名称
	
	private List<HardStandardDetailVo> itemList; //硬装清单
}
