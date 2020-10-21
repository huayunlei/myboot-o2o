package com.ihomefnt.o2o.intf.domain.homecard.vo.request;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * APP3.0新版首页产品版块请求参数
 * @author ZHAO
 */
@ApiModel(value="ProductBoardRequest",description="APP3.0新版首页产品版块块请求参数")
public class ProductBoardRequest extends HttpBaseRequest{
	@ApiModelProperty("风格")
	private Integer style;
	
	@ApiModelProperty("风格id集")
	private List<Integer> styleIdList;
	
	@ApiModelProperty("空间")
	private Integer room;
	
	@ApiModelProperty("价格")
	private Integer price;
	
	@ApiModelProperty("是否只查询我设计的产品: 1 只查询, 其他值都是不查询")
	private Integer artistTag;
	
	@ApiModelProperty("当前第几页")
	private Long pageNo;

	@ApiModelProperty("当前第几页")
	private Integer curPageNo;
	
	@ApiModelProperty("每页显示多少条")
	private Integer pageSize;

	public ProductBoardRequest() {
		this.style = 0;
		this.room = 0;
		this.price = 0;
		this.pageNo = 1L;
		this.curPageNo = 1;
		this.pageSize = 10;
	}

	public ProductBoardRequest(List<Integer> styleIdList, Integer style, Integer room, Integer price, Long pageNo, Integer pageSize,Integer curPageNo) {
		this.styleIdList = styleIdList;
		this.style = style;
		this.room = room;
		this.price = price;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.curPageNo = curPageNo;
	}

	public Integer getCurPageNo() {
		return curPageNo;
	}

	public void setCurPageNo(Integer curPageNo) {
		this.curPageNo = curPageNo;
	}

	public List<Integer> getStyleIdList() {
		return styleIdList;
	}

	public void setStyleIdList(List<Integer> styleIdList) {
		this.styleIdList = styleIdList;
	}

	public Integer getRoom() {
		return room;
	}

	public void setRoom(Integer room) {
		this.room = room;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getArtistTag() {
		return artistTag;
	}

	public void setArtistTag(Integer artistTag) {
		this.artistTag = artistTag;
	}

	public Integer getStyle() {
		return style;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}
	
}
