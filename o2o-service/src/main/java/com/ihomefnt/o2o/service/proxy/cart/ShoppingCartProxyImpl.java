package com.ihomefnt.o2o.service.proxy.cart;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.cart.dto.AjbAccountDto;
import com.ihomefnt.o2o.intf.domain.cart.vo.response.GoodsCountResponseVo;
import com.ihomefnt.o2o.intf.domain.cart.vo.response.GoodsListResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AccountWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.OmsWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.cart.ShoppingCartProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;

/**
 * @author Charl
 */
@Service
public class ShoppingCartProxyImpl implements ShoppingCartProxy {

	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public int addGoods(Object param) {
		try {
			ResponseVo<?> responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.SHOPPING_CART_ADD_GOODS, param, ResponseVo.class);
			if (null != responseVo && responseVo.isSuccess()) {
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
		
		return 0;
	}

	@Override
	public GoodsListResponseVo goodsList(Object param) {
		try {
			ResponseVo<GoodsListResponseVo> responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.SHOPPING_CART_GOODS_LIST, param,
					new TypeReference<ResponseVo<GoodsListResponseVo>>() {});
			if (null != responseVo && responseVo.isSuccess()) {
				return responseVo.getData();
			}
		} catch (Exception e) {
			return null;
		}
		
		return null;
	}

	@Override
	public int addGoodsAmount(Object param) {
		try {
			ResponseVo<?> responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.SHOPPING_CART_ADD_GOODS_AMOUNT, param, ResponseVo.class);
			if (null != responseVo && responseVo.isSuccess()) {
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
		
		return 0;
	}

	@Override
	public int reduceGoodsAmount(Object param) {
		try {
			ResponseVo<?> responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.SHOPPING_CART_REDUCE_GOODS_AMOUNT, param, ResponseVo.class);
			if (null != responseVo && responseVo.isSuccess()) {
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
		
		return 0;
	}

	@Override
	public int removeGoods(Object param) {
		try {
			ResponseVo<?> responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.SHOPPING_CART_REMOVE_GOODS, param, ResponseVo.class);
			if (null != responseVo && responseVo.isSuccess()) {
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
		
		return 0;
	}

	@Override
	public int batchRemoveGoods(Object param) {
		try {
			ResponseVo<?> responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.SHOPPING_CART_BATCH_REMOVE_GOODS, param, ResponseVo.class);
			if (null != responseVo && responseVo.isSuccess()) {
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
		
		return 0;
	}

	@Override
	public int goodsCount(Object param) {
		try {
			ResponseVo<GoodsCountResponseVo> responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.SHOPPING_CART_GOODS_COUNT, param,
					new TypeReference<ResponseVo<GoodsCountResponseVo>>() {});
			if (null != responseVo && responseVo.isSuccess() && null != responseVo.getData()) {
				GoodsCountResponseVo resultVo = responseVo.getData();
				return resultVo.getCount();
			}
		} catch (Exception e) {
			return 0;
		}
		
		return 0;
	}

	@Override
	public AjbAccountDto ajbInfo(Object param) {
		try {
			ResponseVo<AjbAccountDto> responseVo = strongSercviceCaller.post(AccountWebServiceNameConstants.QUERY_AJB_ACCOUNT_BY_USER_ID_FOR_APP, param,
					new TypeReference<ResponseVo<AjbAccountDto>>() {});
			if (null != responseVo && responseVo.isSuccess()) {
				return responseVo.getData();
			}
		} catch (Exception e) {
			return null;
		}
		
		return null;
	}

	@Override
	public boolean settleAccount(Object param) {
		try {
			ResponseVo<Boolean> responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.SHOPPING_CART_SETTLE_ACCOUNTS, param,
					new TypeReference<ResponseVo<Boolean>>() {});
			if (null != responseVo && responseVo.isSuccess()) {
				return responseVo.getData();
			}
		} catch (Exception e) {
			return false;
		}
		
		return false;
	}

}
