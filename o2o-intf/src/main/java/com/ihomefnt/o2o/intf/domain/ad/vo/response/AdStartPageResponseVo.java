package com.ihomefnt.o2o.intf.domain.ad.vo.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdStartPageResponseVo {

	private List<AdStartPageItemResponseVo> startPageList;
}
