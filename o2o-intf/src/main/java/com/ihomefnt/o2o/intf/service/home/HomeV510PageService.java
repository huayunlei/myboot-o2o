package com.ihomefnt.o2o.intf.service.home;

import com.ihomefnt.o2o.intf.domain.homepage.dto.BannerResponseVo;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.HomeFrameRequest;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.QueryDraftRequest;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.HomeFrameResponse;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.SolutionDraftResponse;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.DesignDnaRoomVo;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleQuestionSelectedResponse;

import java.util.List;
import java.util.Map;

/**
 * APP5.0首页
 * Author: ZHAO
 * Date: 2018年7月19日
 */
public interface HomeV510PageService {
	/**
	 * 首页节点数据
	 * @param request
	 * @return
	 * Author: ZHAO
	 * Date: 2018年7月19日
	 */
	@Deprecated
	HomeFrameResponse getHomePageData(HomeFrameRequest request);
	
	/**
	 * 查询方案草稿
	 * @param request
	 * @return
	 * Author: ZHAO
	 * Date: 2018年7月22日
	 */
	SolutionDraftResponse querySolutionDraft(QueryDraftRequest request);
	
    void assembleDnaRoomList(Map<String, Object> obj, List<DesignDnaRoomVo> taskDnaRoomList, StyleQuestionSelectedResponse questionTwo, Integer osType, Integer width);

	/**
	 * 根据版本号过滤banner
	 * @param bannerResponseVos
	 * @param appVersion
	 */
	void reduceBannerByVersion(List<BannerResponseVo> bannerResponseVos, String appVersion);
}
