package com.ihomefnt.o2o.intf.proxy.ad;

import java.util.Map;

import com.ihomefnt.o2o.intf.domain.ad.dto.AdvertListDto;

/**
 * 启动页
 * @author ZHAO
 */
public interface AdvertProxy {
	/**
	 * 查询APP启动页列表
	 * @param paramMap
	 * @return
	 */
	AdvertListDto queryStartPageList(Map<String, Object> paramMap);
}
