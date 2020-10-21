/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.ihomefnt.common.util.image.ImageQuality;
import com.ihomefnt.common.util.image.ImageTool;
import com.ihomefnt.common.util.image.ImageType;

/**
 * @author zhang<br/>
 *         套装对象<br/>
 */
@Data
@NoArgsConstructor
public class Suit {
	
	private Long suitId; //套装ID
	
	private Long houseId;//户型ID
	
	private Long buildingId;//小区ID
	
	private Long districtId;//城市ID

	private String suitImage;// 推荐套装图片

	private String graphicDesignUrl;// 套装平面图片URL

	private String graphicDesignDesc;// 套装平面图片描述
	
	@JsonIgnore
	private String images;//套装图片
	
	private String suitStyle;// 套装风格
	
	private String suitName;// 套装名称

	private String suitStr;// 风格面积数量的拼接字段

	private Double suitPrice;// 套装价格
	
	private Double areaSize ;//套装面积
	
	private Long sales;//销量
	
	private String productCount;//套装件数
	
	public String getSuitImage() {
		if(StringUtils.isNotBlank(images)){
			suitImage=images.split(",")[0].replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "");
			if(StringUtils.isNotBlank(suitImage)){
				return suitImage+ImageTool.appendTypeAndQuality(ImageType.SMALL, ImageQuality.HIGH);//"?imageView2/0/w/260/h/148";
			}
		}
		return null;
	}
	public String getGraphicDesignUrl() {
		if(StringUtils.isNotBlank(graphicDesignUrl)){
			return graphicDesignUrl;
		}else{
			return null;
		}
		
	}
	public String getSuitStr() {
		StringBuffer buff = new StringBuffer();
		if(suitStyle!=null){
			buff.append(suitStyle);
			buff.append(" | ");
		}
		if(areaSize!=null){
			String ss = areaSize.toString().replaceAll("0+?$", "");//去掉后面无用的零
			ss = ss.toString().replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点			
			if(StringUtils.isNotBlank(ss)&&!ss.equals("0")){
				buff.append(ss);
				buff.append("平方米");
				buff.append(" | ");
			}
		}
		if(productCount!=null){
			buff.append(productCount);
			buff.append("件套");
		}
		return buff.toString();
	}
}
