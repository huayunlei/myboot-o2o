/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.suit.vo.response;

import com.ihomefnt.o2o.intf.domain.suit.dto.Room;
import com.ihomefnt.o2o.intf.domain.suit.dto.RoomImage;
import lombok.Data;

import java.util.List;

/**
 * @author zhang<br/> 
 * 空间响应对象<br/>
 *
 */
@Data
public class HttpRoomResponse {

	private List<RoomImage> list;// 一个空间下的所有图片
	
	private String graphicDesignUrl;//平面图片URL
    private String graphicDesignDesc;//平面图片描述
    private Room roomInfo;//空间详情
    private String serviceUrl ;//服务URL
    private double servicePicRatio;//服务URL高宽比
    private List<Room> roomList;//推荐空间
}
