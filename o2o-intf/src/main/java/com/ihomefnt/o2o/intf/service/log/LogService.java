package com.ihomefnt.o2o.intf.service.log;

import com.ihomefnt.o2o.intf.domain.log.vo.request.LogRequestVo;

public interface LogService {

	/** 
	* @Title: addOperationLog 
	* @Description: 记录APP操作日志
	* @param @param request  参数说明 
	* @return void    返回类型 
	* @throws 
	*/
	void addOperationLog(LogRequestVo request);

	/** 
	* @Title: addLog 
	* @Description: 记录日志 
	* @param @param request  参数说明 
	* @return void    返回类型 
	* @throws 
	*/
	void addLog(LogRequestVo request);

}
