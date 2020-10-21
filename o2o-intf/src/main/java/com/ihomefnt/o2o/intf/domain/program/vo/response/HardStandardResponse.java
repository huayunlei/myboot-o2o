package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.programorder.dto.HardStandardDetail;
import lombok.Data;

import java.util.List;

/**
 * 硬装标准返回数据
 * @author ZHAO
 */
@Data
public class HardStandardResponse {
	private String spaceName;//空间
	
	private List<HardStandardDetail> materialList;//项目材质集合
}
