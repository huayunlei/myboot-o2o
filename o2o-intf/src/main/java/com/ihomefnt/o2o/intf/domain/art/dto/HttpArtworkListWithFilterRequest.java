package com.ihomefnt.o2o.intf.domain.art.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 艺术品筛选列表
 * @author Charl
 */
@Data
public class HttpArtworkListWithFilterRequest extends HttpBaseRequest {
	
	private int freeEx = 0; //艾积分免费兑换： 0.不用艾积分兑换 1.用艾积分兑换
	
	private Integer priceSort; //价格排序: 传空表示用默认的时间倒叙，1.正序 ASC 2.倒叙 DESC
	
	private List<Integer> typeIds; //用户选定的类型
	
	private List<Integer> priceIds; //用户选定的价格
	
	private List<Integer> roomIds; //用户选定的空间
	
	private int pageNo = 1; //默认用户选定第一页
	
	private int pageSize = 10; //默认每页显示10条
	
	private BigDecimal ajbMoney; //用户账户名下艾积分金额
	
	private int recommend = 0; //艺术品推荐：0.不推荐 1.推荐
	
	private Integer categoryType;//分类类别
}
