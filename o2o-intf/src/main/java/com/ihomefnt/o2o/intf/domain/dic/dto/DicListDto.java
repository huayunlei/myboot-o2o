package com.ihomefnt.o2o.intf.domain.dic.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 字典集合
 * @author ZHAO
 */
@Data
public class DicListDto implements Serializable{
	private static final long serialVersionUID = 7530317955861173779L;
	
	private List<DicDto> dicList;

}
