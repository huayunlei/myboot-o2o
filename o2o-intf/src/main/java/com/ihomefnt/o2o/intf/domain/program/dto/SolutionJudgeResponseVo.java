package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

/**
 * 判断方案是否存在软装替换、升级包、增配包
 * @author ZHAO
 */
@Data
public class SolutionJudgeResponseVo {
	private Boolean softReplace;//是否有软装替换
	
	private Boolean updateBag;//是否有升级包
	
	private Boolean extraInfo;//是否有增配包
}
