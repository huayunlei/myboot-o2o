package com.ihomefnt.o2o.service.proxy.art;

import com.ihomefnt.o2o.intf.manager.constant.proxy.OmsWebServiceNameConstants;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.art.dto.ArtStockCountInfo;
import com.ihomefnt.o2o.intf.proxy.art.ArtStockProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;

@Service
public class ArtStockProxyImpl implements ArtStockProxy {


	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public ArtStockCountInfo queryInventory(int artworkId) {
		ResponseVo<ArtStockCountInfo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post("cms-web.api-inventory.queryInventory", artworkId, 
					new TypeReference<ResponseVo<ArtStockCountInfo>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (null == responseVo) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public ArtStockCountInfo queryOmsInventory(String artworkId) {
		ResponseVo<ArtStockCountInfo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.QUERY_INVENTORY, artworkId,
					new TypeReference<ResponseVo<ArtStockCountInfo>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (null == responseVo) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;
	}

}
