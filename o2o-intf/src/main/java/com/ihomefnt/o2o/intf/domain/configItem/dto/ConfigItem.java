package com.ihomefnt.o2o.intf.domain.configItem.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ConfigItem {
	/** 主鍵 */
	private Long configId; // 配置项id
	private String name;// 配置项名称
	private int type;// 配置项类型
	private Timestamp createtime;// 创建时间
	private Long pid;// 配置项关联的父ID
	private Timestamp lastmodifytime;// 最后一次修改的时间
	private int status;// 配置项的状态
}
