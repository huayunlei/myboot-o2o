package com.ihomefnt.o2o.intf.proxy.program;

import com.ihomefnt.o2o.intf.domain.program.dto.HardStandardGroupListResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.HardStandardListResponseVo;

import java.util.List;

/**
 * 硬装标准WCM代理
 * @author ZHAO
 */
public interface HardStandardWcmProxy {
	/**
	 * 根据条件查询硬装标准
	 * @param seriesName
	 * @return
	 */
	HardStandardListResponseVo queryHardStandByCondition(List<String> seriesNameList);
	
	/**
	 * 查询硬装标准对比信息
	 * @return
	 */
	HardStandardGroupListResponseVo queryHardStandGroup();
}
