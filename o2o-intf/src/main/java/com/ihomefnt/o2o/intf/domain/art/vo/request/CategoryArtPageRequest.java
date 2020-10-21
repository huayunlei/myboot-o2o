package com.ihomefnt.o2o.intf.domain.art.vo.request;

import java.math.BigDecimal;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("分类艺术品分页请求参数")
@Data
public class CategoryArtPageRequest extends HttpBaseRequest{
	@ApiModelProperty("艾积分免费兑换： 0.不用艾积分兑换 1.用艾积分兑换")
	private Integer freeEx = 0; //艾积分免费兑换： 0.不用艾积分兑换 1.用艾积分兑换
	
	@ApiModelProperty("价格排序: 传空表示用默认的时间倒叙，1.正序 ASC 2.倒叙 DESC")
	private Integer priceSort; //价格排序: 传空表示用默认的时间倒叙，1.正序 ASC 2.倒叙 DESC
	
	@ApiModelProperty("当前第几页")
	private Integer pageNo = 1; //默认用户选定第一页
	
	@ApiModelProperty("每页显示条数")
	private Integer pageSize = 10; //默认每页显示10条
	
	@ApiModelProperty("用户账户名下艾积分金额")
	private BigDecimal ajbMoney; //用户账户名下艾积分金额

}
