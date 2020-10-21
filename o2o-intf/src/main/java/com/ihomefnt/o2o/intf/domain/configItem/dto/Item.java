package com.ihomefnt.o2o.intf.domain.configItem.dto;

import lombok.Data;

@Data
public class Item {
	private Long id;//ID 主键
	private String name;//名称
	private Integer type;//类型  名称和类型一一对应
	private Integer serialnum;// 序号 用作进行排序使用
	private Integer status;//控制当前项的状态
	private Long configId;//控制当前项的状态
}
