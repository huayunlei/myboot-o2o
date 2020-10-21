package com.ihomefnt.o2o.intf.domain.product.vo.response;

import com.ihomefnt.o2o.intf.domain.product.doo.Room;
import lombok.Data;

import java.util.List;

@Data
public class HttpRoomHistoryResponse {

	private List<Room> roomList;

}