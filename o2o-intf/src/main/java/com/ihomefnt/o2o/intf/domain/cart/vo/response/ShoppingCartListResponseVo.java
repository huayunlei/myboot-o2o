package com.ihomefnt.o2o.intf.domain.cart.vo.response;

import com.ihomefnt.o2o.intf.domain.cart.dto.ShoppingCartProductDto;
import com.ihomefnt.o2o.intf.domain.product.doo.Room;
import lombok.Data;

import java.util.List;

@Data
public class ShoppingCartListResponseVo {

	private List<Room> onSlaveProduct;//上架商品列表
	private List<ShoppingCartProductDto> offSlaveProduct;//下架商品列表
}
