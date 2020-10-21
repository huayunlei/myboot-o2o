package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.programorder.dto.AddBagDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 判断方案是否存在软装替换、升级包、增配包
 * @author ZHAO
 */
@Data
public class SolutionExtraInfoResponse implements Serializable {
	private List<OptionalSoftSpaceResponse> optionalSoftSpaceList;//软装替换空间集合
	
	private SolutionStandardUpgradeTotalResponse standardUpgrade;//升级包
	
	private List<AddBagDetail> addBagList;//增配包

	public SolutionExtraInfoResponse() {
		this.optionalSoftSpaceList = new ArrayList<>();
		this.standardUpgrade = new SolutionStandardUpgradeTotalResponse();
		this.addBagList = new ArrayList<>();
	}

}
