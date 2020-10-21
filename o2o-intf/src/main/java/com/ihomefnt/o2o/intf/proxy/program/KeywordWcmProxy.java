package com.ihomefnt.o2o.intf.proxy.program;

import com.ihomefnt.o2o.intf.domain.program.dto.KeywordListResponseVo;

import java.util.List;

/**
 * Keyword  WCM代理
 * @author ZHAO
 */
public interface KeywordWcmProxy {
	/**
	 * 查询信息集合
	 * @param keywordList
	 * @return
	 */
	KeywordListResponseVo getKeywordList(List<String> keywordList);
}
