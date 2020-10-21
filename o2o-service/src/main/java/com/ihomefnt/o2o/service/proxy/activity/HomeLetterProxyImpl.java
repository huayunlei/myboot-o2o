package com.ihomefnt.o2o.service.proxy.activity;

import com.ihomefnt.o2o.intf.domain.activity.dto.*;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.activity.HomeLetterProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 1219活动 三行家书
 * @author ZHAO
 */
@Service
public class HomeLetterProxyImpl implements HomeLetterProxy {
	@Resource
	private StrongSercviceCaller strongSercviceCaller;

	private static final Logger LOG = LoggerFactory.getLogger(HomeLetterProxyImpl.class);

	@Override
	public PublishArticleResultDto publishArticle(Map<String, Object> params) {
		HttpBaseResponse<PublishArticleResultDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.HOME_LETTER_PUBLISH_ARTICLE, params,
					new TypeReference<HttpBaseResponse<PublishArticleResultDto>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}

		PublishArticleResultDto resultDto = responseVo.getObj();
		if(responseVo.getCode() != null){
			resultDto.setResultCode(responseVo.getCode());
		}
		return resultDto;
	}

	@Override
	public Long homeLetterVote(Map<String, Object> params) {
		HttpBaseResponse<?> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.HOME_LETTER_VOTE, params, HttpBaseResponse.class);
		} catch (Exception e) {
			return 2L;
		}
		if(responseVo == null){
			return 2L;
		}
		return responseVo.getCode();
	}

	@Override
	public VoteRecordListDto queryVoteRecordList(Map<String, Object> params) {
		HttpBaseResponse<VoteRecordListDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.HOME_LETTER_QUERY_VOTE_RECORD_LIST, params,
					new TypeReference<HttpBaseResponse<VoteRecordListDto>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}

		return responseVo.getObj();
	}

	@Override
	public HomeLetterVo queryArticleInfo(Map<String, Object> params) {
		HttpBaseResponse<HomeLetterVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.HOME_LETTER_QUERY_ARTICLE_INFO, params,
					new TypeReference<HttpBaseResponse<HomeLetterVo>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}

		return responseVo.getObj();
	}

	@Override
	public HomeLetterListDto queryArticleList(Map<String, Object> params) {
		HttpBaseResponse<HomeLetterListDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.HOME_LETTER_QUERY_ARTICLE_LIST, params,
					new TypeReference<HttpBaseResponse<HomeLetterListDto>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}

		return responseVo.getObj();
	}

	@Override
	public HomeLetterWinningDto queryWinningResult() {
		Map<String, Object> params = new HashMap<String, Object>();
		HttpBaseResponse<HomeLetterWinningDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.HOME_LETTER_QUERY_WINNING_RESULT, params,
					new TypeReference<HttpBaseResponse<HomeLetterWinningDto>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}

		return responseVo.getObj();
	}
}
