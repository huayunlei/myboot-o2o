package com.ihomefnt.o2o.intf.domain.programorder.dto;

import java.util.List;


/**
 * 硬装标准
 * @author ZHAO
 */
public class HardStandardDetail {
	private Integer sameFlag;//相同标志
	
	private String spaceName;//空间名称 
	
	private List<String> material;//明细

	public Integer getSameFlag() {
		return sameFlag;
	}

	public void setSameFlag(Integer sameFlag) {
		this.sameFlag = sameFlag;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public List<String> getMaterial() {
		return material;
	}

	public void setMaterial(List<String> material) {
		this.material = material;
	}

}
