package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class THouseResponse {
    
	private Long houseId;
	
	private String houseName;
	
	private BigDecimal area;
	
	private Date updateTime;
	
	private Integer chamber;//卧室
    
	private Integer hall;//厅
    
	private Integer toilet;//卫
    
	private Integer kitchen;//厨房
    
	private Integer balcony;//阳台
    
	private Integer storage;//储藏间
    
	private Integer cloak;//衣帽间
	
    private String normalPic;//户型图
    
	private Integer location;//户型位置：0东西通用、1东边户、2西边户
    
    private String markPic;//空间标示图
    
    private String pakFile; //户型PAK文件
    
	private String cadFile; //户型cad文件
    
    private Long buildingId;
    
    private Integer companyId;
    
    private Long zoneId;

    public String generatePatternName() {
        StringBuffer sb = new StringBuffer();
        if (this.getChamber() > 0) {
            sb.append(this.getChamber());
            sb.append("室");
        }
        if (this.getHall() > 0) {
            sb.append(this.getHall());
            sb.append("厅");
        }
        if (this.getKitchen() > 0) {
            sb.append(this.getKitchen());
            sb.append("厨");
        }
        if (this.getToilet() > 0) {
            sb.append(this.getToilet());
            sb.append("卫");
        }
        if (this.getBalcony() > 0) {
            sb.append(this.getBalcony());
            sb.append("阳台");
        }
        if (this.getStorage() > 0) {
            sb.append(this.getStorage());
            sb.append("储藏间");
        }
        if (this.getCloak() > 0) {
            sb.append(this.getCloak());
            sb.append("衣帽间");
        }
        return sb.toString();
    }
}
