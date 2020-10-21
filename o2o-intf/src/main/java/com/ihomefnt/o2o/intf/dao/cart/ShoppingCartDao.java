package com.ihomefnt.o2o.intf.dao.cart;

import com.ihomefnt.o2o.intf.domain.cart.dto.ShoppingCartDto;
import com.ihomefnt.o2o.intf.domain.cart.dto.ShoppingCartProductDto;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductOrder;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductSummaryResponse;
import com.ihomefnt.o2o.intf.domain.product.doo.Room;

import java.util.List;

public interface ShoppingCartDao {

	List<Room> queryShoppingCartOnSlave(Long userId);
	
	List<ShoppingCartProductDto> queryShoppingCartOffSlave(Long userId);
	
	void addShoppingCartBatch(List<ShoppingCartDto> list);
	
	int queryShoppingCartCnt(Long userId);
	
	List<Long> queryShoppingCartProduct(Long userId);
	
	int deleteOffProduct(Long userId, Long productId);
	
	List<ProductSummaryResponse> queryProductInRoom();
	
	/**
	 * 根据商品id查询对应商品
	 * @param productId
	 * @return
	 */
	List<ProductSummaryResponse> queryProductInRoomByProductId(Long productId);
	
	/**
	 * 根据商品id集合查询对应商品
	 * @param productIdList
	 * @return
	 */
	List<ProductSummaryResponse> queryProductInRoomByProductIdList(List<Long> productIdList);
	
	List<ProductOrder> queryProductInfo(List<Long> productId);
}
