package com.ihomefnt.o2o.intf.domain.dic.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典信息
 * @author ZHAO
 */
@Data
public class DicDto implements Serializable{

	private static final long serialVersionUID = 8774289682577441032L;

	// 主键id
	private Integer id;

	// 键
	private String keyDesc;

	// 值
	private String valueDesc;

	// 父id
	private Integer pid;

	// 排序
	private Integer sortBy;

	// 创建时间
	private Date createTime;

	// 修改时间
	private Date updateTime;

	// 删除标志
	private Integer deleteFlag;

}
