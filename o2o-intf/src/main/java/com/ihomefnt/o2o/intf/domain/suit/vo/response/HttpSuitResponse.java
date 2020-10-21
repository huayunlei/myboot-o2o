/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.suit.vo.response;

import com.ihomefnt.o2o.intf.domain.suit.dto.Room;
import com.ihomefnt.o2o.intf.domain.suit.dto.Suit;
import lombok.Data;

import java.util.List;

/**
 * @author zhang<br/>
 *         套装响应对象<br/>
 *
 */
@Data
public class HttpSuitResponse {

	private String graphicDesignUrl;// 套装平面图片URL

	private String graphicDesignDesc;// 套装平面图片描述

	private List<Room> roomList;// 该套装下所有的空间集合

	private String serviceUrl;// 服务承诺统一URL
	
	private double servicePicRatio;//服务图片高宽比

	private List<Suit> suitList;// 推荐相关套装集合
}
