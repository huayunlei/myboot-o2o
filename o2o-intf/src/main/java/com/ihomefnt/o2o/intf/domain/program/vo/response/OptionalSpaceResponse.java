package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.programorder.dto.OptionalSpaceInfo;
import lombok.Data;

import java.util.List;

/**
 * 可替换空间信息
 * Author: ZHAO
 * Date: 2018年5月2日
 */
@Data
public class OptionalSpaceResponse extends OptionalSpaceInfo {
	private List<OptionalSpaceInfo> optionalSpaceList;//可替换空间集合
}
