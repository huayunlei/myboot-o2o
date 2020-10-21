package com.ihomefnt.o2o.intf.proxy.art;

import com.ihomefnt.o2o.intf.domain.art.dto.ArtStockCountInfo;

public interface ArtStockProxy {
	
	/**
	 * 查询艺术品库存
	 * @param artworkId
	 * @return
	 */
	ArtStockCountInfo queryInventory(int artworkId);

	ArtStockCountInfo queryOmsInventory(String artworkId);
	
}
