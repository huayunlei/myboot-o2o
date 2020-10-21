package com.ihomefnt.o2o.intf.proxy.user;

import java.util.Map;

import com.ihomefnt.o2o.intf.domain.log.dto.VisitLogDto;

public interface LogProxy {
	
	/**
	 * 增加日志 <br/>
	 * @param params
	 */
	void addLog(Map<String, Object> params);

	/**
	 * 根据条件查询当天浏览记录
	 * @param params
	 * @return
	 */
	VisitLogDto queryVisitLogByCondition(Map<String, Object> params);

}
