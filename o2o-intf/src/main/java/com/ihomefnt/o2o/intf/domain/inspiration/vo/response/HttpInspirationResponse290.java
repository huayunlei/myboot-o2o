
package com.ihomefnt.o2o.intf.domain.inspiration.vo.response;

import com.ihomefnt.o2o.intf.domain.inspiration.dto.Article270;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Picture270;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.ShareOrder;
import lombok.Data;

import java.util.List;

/**
 * 灵感文章首页
 * @author Charl
 */
@Data
public class HttpInspirationResponse290 {

	private List<Article270> firstList;// 5篇灵感文章：只有当pageNo=1,才有值。
	
	private List<ShareOrder> shareOrderList; // 5篇新家大晒文章：只有当pageNo=1,才有值

	private List<Picture270> middleList;// 最近上传传的3张图：只有当pageNo=1,才有值。

	private List<ShareOrder> lastList;// 新家大晒列表

}
