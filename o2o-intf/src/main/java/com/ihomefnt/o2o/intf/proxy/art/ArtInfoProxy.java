package com.ihomefnt.o2o.intf.proxy.art;

import java.util.Map;

import com.ihomefnt.o2o.intf.domain.art.dto.ArtListResponseVo;

/**
 * 艺术品信息服务代理
 * @author ZHAO
 */
public interface ArtInfoProxy {
	/**
	 * 根据查询条件查询艺术品列表（分页）
	 * @param params
	 * @return
	 */
	ArtListResponseVo queryArtListByFilters(Map<String, Object> params);

}
