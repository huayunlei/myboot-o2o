package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.programorder.dto.HardStandardGroupDetail;
import lombok.Data;

import java.util.List;

/**
 * 硬装标准数据
 * @author ZHAO
 */
@Data
public class HardStandardGroupResponseVo {
	private String spaceName;//空间名称
	
	private List<HardStandardGroupDetail> materialList;//项目材质集合
}
