package com.ihomefnt.o2o.intf.service.art;

import com.ihomefnt.o2o.intf.domain.art.vo.response.ArtWordListResponseVo;

public interface ArtWordService {
	
	/**
	 * 查询关键字
	 * @param key
	 * @return
	 */
	ArtWordListResponseVo getArtWordList();
}
