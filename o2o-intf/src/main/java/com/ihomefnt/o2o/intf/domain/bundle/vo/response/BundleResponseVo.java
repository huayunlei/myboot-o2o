/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年12月15日
 * Description:BundleResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.bundle.vo.response;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class BundleResponseVo {

	// 升级标识:0 不升级,1 增量升级,2 全量升级 3 回滚
	private Integer upgradeFlag;

	// 下载路径
	private String url;

	// 最终bundle版本号
	private String lastVersion;
}
