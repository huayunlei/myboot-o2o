package com.ihomefnt.o2o.intf.domain.configItem.vo.response;

import com.ihomefnt.o2o.intf.domain.configItem.dto.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class HttpItemResponse {
	private Long id;//ID 主键
	private String name;//名称
	public HttpItemResponse(Item item) {
		this.id=item.getId();
		this.name=item.getName();
	}
}
