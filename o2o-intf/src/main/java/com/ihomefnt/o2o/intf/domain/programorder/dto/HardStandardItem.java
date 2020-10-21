package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

/**
 * 硬装标准详情
 * @author ZHAO
 */
@Data
public class HardStandardItem {
	private String subjectName;//项目
	
    private String material;//材质

    public HardStandardItem() {
        this.subjectName = "";
        this.material = "";
    }

}
