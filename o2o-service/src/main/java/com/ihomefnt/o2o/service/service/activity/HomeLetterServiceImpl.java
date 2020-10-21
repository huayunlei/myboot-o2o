package com.ihomefnt.o2o.service.service.activity;

import com.ihomefnt.o2o.intf.domain.activity.vo.request.*;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.house.HouseService;
import com.ihomefnt.o2o.service.proxy.program.ProductProgramProxyImpl;
import com.ihomefnt.o2o.intf.service.activity.HomeLetterService;
import com.ihomefnt.o2o.intf.manager.constant.activity.HomeLetterPublishEnum;
import com.ihomefnt.o2o.intf.manager.constant.activity.HomeLetterVoteEnum;
import com.ihomefnt.o2o.intf.proxy.activity.HomeLetterProxy;
import com.ihomefnt.o2o.intf.domain.activity.dto.HomeLetterListDto;
import com.ihomefnt.o2o.intf.domain.activity.dto.HomeLetterWinningDto;
import com.ihomefnt.o2o.intf.domain.activity.dto.PublishArticleResultDto;
import com.ihomefnt.o2o.intf.domain.activity.dto.VoteRecordListDto;
import com.ihomefnt.o2o.intf.domain.activity.dto.HomeLetterVo;
import com.ihomefnt.o2o.intf.domain.activity.dto.VoteRecordVo;
import com.ihomefnt.o2o.intf.domain.activity.vo.response.ActivityInfoResponse;
import com.ihomefnt.o2o.intf.domain.activity.vo.response.ArticleInfoResponse;
import com.ihomefnt.o2o.intf.domain.activity.vo.response.PublishArticleResponseVo;
import com.ihomefnt.o2o.intf.domain.activity.vo.response.VoteRecordResponse;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.constant.home.HomeCardPraise;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardWcmProxy;
import com.ihomefnt.o2o.intf.domain.homecard.dto.CommentLimitResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise;
import com.ihomefnt.o2o.intf.domain.program.dto.HouseInfoResponseVo;
import com.ihomefnt.oms.trade.util.PageModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1219活动 三行家书
 * @author ZHAO
 */
@Service
public class HomeLetterServiceImpl implements HomeLetterService{
	
	@Autowired
	private HomeLetterProxy homeLetterProxy;
	
	@Autowired
	private HomeCardWcmProxy homeCardWcmProxy;

	@Autowired
	private UserProxy userProxy;

	@Autowired
	private ProductProgramProxyImpl productProgramProxy;
	@Autowired
	private HouseService houseService;

	@Override
	public PublishArticleResponseVo publishArticle(PublishArticleRequest request) {
        Long code = 2L;
        
        if(StringUtils.isBlank(request.getOpenId()) || request.getOpenId().equalsIgnoreCase("undefined")){
        	code = 8L;
        }else if(StringUtils.isBlank(request.getContent())){
        	code = 6L;
        }else if(StringUtils.isBlank(request.getMobile())){
        	code = 7L;
        }else{
	        //判断活动是否已经结束
			CommentLimitResponseVo commentLimitResponseVo = homeCardWcmProxy.queryCommentLimitByCode(HomeCardPraise.HOME_LETTER_LIMIT);
			if(commentLimitResponseVo != null && commentLimitResponseVo.getActiveFlag() != null && commentLimitResponseVo.getActiveFlag() == HomeCardPraise.HOME_LETTER_ACTIVITY){
				//活动有效
		        Map<String, Object> params = new HashMap<String, Object>();
		        params.put("openId", request.getOpenId());
		        params.put("mobile", request.getMobile());
		        params.put("headImgUrl", request.getHeadImgUrl());
		        params.put("nickName", request.getNickName());
		        params.put("content", request.getContent());
		        
		        //查询用户楼盘信息
		        UserDto userDto = userProxy.getUserByMobile(request.getMobile());
		        if(userDto != null){
		        	List<HouseInfoResponseVo> houseInfoList = houseService.queryUserHouseList(userDto.getId());
		        	if(CollectionUtils.isNotEmpty(houseInfoList)){
				        params.put("buildingId", houseInfoList.get(0).getHouseProjectId());
				        params.put("buildingName", houseInfoList.get(0).getHouseProjectName());
		        	}
		        }
		        
		        PublishArticleResultDto articleResponseVo = homeLetterProxy.publishArticle(params);
		        if(articleResponseVo != null && HomeLetterPublishEnum.SUCCESS.getCode() == articleResponseVo.getResultCode()){
		        	return new PublishArticleResponseVo(articleResponseVo.getArticleId());
		        }
			}else{
				//活动已结束
				code = 5L;
			}
        }

		HomeLetterPublishEnum letterPublishEnum = HomeLetterPublishEnum.getValue(code);
		throw new BusinessException(letterPublishEnum.getResult(), letterPublishEnum.getValue());
	}

	@Override
	public void homeLetterVote(HomeLetterVoteRequest request) {
        Long code = 2L;
        
        if(StringUtils.isBlank(request.getOpenId()) || request.getOpenId().equalsIgnoreCase("undefined")){
        	code = 6L;
        }else if(StringUtils.isBlank(request.getNickName()) || StringUtils.isBlank(request.getHeadImgUrl()) || !request.getHeadImgUrl().startsWith("http://wx.qlogo.cn/mmopen")){
        	code = 7L;
        }else{
	        //判断活动是否已经结束
	  		CommentLimitResponseVo commentLimitResponseVo = homeCardWcmProxy.queryCommentLimitByCode(HomeCardPraise.HOME_LETTER_LIMIT);
	  		if(commentLimitResponseVo != null && commentLimitResponseVo.getActiveFlag() != null && commentLimitResponseVo.getActiveFlag() == HomeCardPraise.HOME_LETTER_ACTIVITY){
	  			//活动有效
	  			Map<String, Object> params = new HashMap<String, Object>();
	  	        params.put("articleId", request.getArticleId());
	  	        params.put("openId", request.getOpenId());
	  	        params.put("headImgUrl", request.getHeadImgUrl());
	  	        params.put("nickName", request.getNickName());
	  	        code = homeLetterProxy.homeLetterVote(params);
	  		}else{
	  			code = 5L;
	  		}
        }
        
        HomeLetterVoteEnum homeLetterVoteEnum = HomeLetterVoteEnum.getValue(code);
        if (HomeLetterPublishEnum.SUCCESS.getCode() == code) {
			throw new BusinessException(homeLetterVoteEnum.getResult(), homeLetterVoteEnum.getValue());
		}
	}

	@Override
	public PageModel queryVoteRecordList(QueryVoteRecordRequest request) {
		PageModel pageModel = new PageModel();
		Integer pageNo = 1;
		Integer pageSize = 10;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("articleId", request.getArticleId());
		if(request.getPageNo() != null && request.getPageNo() > 0){
			pageNo = request.getPageNo();
		}
		if(request.getPageSize() != null && request.getPageSize() > 0){
			pageSize = request.getPageSize();
		}
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		VoteRecordListDto listResponseVo = homeLetterProxy.queryVoteRecordList(params);
		List<VoteRecordResponse> voteRecordList = new ArrayList<VoteRecordResponse>();
		if(listResponseVo != null){
			List<VoteRecordVo> list = listResponseVo.getList();
			for (VoteRecordVo voteRecordVo : list) {
				VoteRecordResponse recordResponse = new VoteRecordResponse();
	        	recordResponse.setNickName(voteRecordVo.getNickName());
	        	recordResponse.setVoteDayTimeStr(voteRecordVo.getVoteDayTimeStr());
	        	recordResponse.setVoteHourTimeStr(voteRecordVo.getVoteHourTimeStr());
	        	voteRecordList.add(recordResponse);
			}
			
			pageModel.setList(voteRecordList);
			pageModel.setPageNo(listResponseVo.getPageNo());
			pageModel.setPageSize(listResponseVo.getPageSize());
			pageModel.setTotalPages(listResponseVo.getTotalPage());
			pageModel.setTotalRecords(listResponseVo.getTotalCount());
		}else{
			pageModel.setList(voteRecordList);
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageSize);
			pageModel.setTotalPages(1);
			pageModel.setTotalRecords(0);
		}
		
		return pageModel;
	}

	@Override
	public PageModel queryArticleList(ArticleListRequest request) {
		PageModel pageModel = new PageModel();
		
		Integer pageNo = 1;
		Integer pageSize = 10;
		Integer searchType = 1;
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(request.getPageNo() != null && request.getPageNo() > 0){
			pageNo = request.getPageNo();
		}
		if(request.getPageSize() != null && request.getPageSize() > 0){
			pageSize = request.getPageSize();
		}
		if(request.getSearchType() != null){
			searchType = request.getSearchType();
		}
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		params.put("searchType", searchType);
		HomeLetterListDto listResponseVo = homeLetterProxy.queryArticleList(params);
		List<ArticleInfoResponse> articleList = new ArrayList<ArticleInfoResponse>();
		if(listResponseVo != null){
			List<HomeLetterVo> list = listResponseVo.getList();
			for (HomeLetterVo homeLetterVo : list) {
				ArticleInfoResponse articleInfoResponse = new ArticleInfoResponse();
				articleInfoResponse.setArticleId(homeLetterVo.getArticleId());
	        	articleInfoResponse.setContent(homeLetterVo.getContent());
	        	articleInfoResponse.setHeadImgUrl(homeLetterVo.getHeadImgUrl());
	        	articleInfoResponse.setNickName(homeLetterVo.getNickName());
	        	articleInfoResponse.setRankingNum(homeLetterVo.getRankingNum());
	        	articleInfoResponse.setVoteNum(homeLetterVo.getVoteNum());
	        	if(StringUtils.isNotBlank(homeLetterVo.getBuildingName())){
	        		if(homeLetterVo.getBuildingName().contains("非专属楼盘") || homeLetterVo.getBuildingName().contains("库存清理")){
	        			articleInfoResponse.setBuildingName("其他楼盘");
	        		}else{
	        			articleInfoResponse.setBuildingName(homeLetterVo.getBuildingName());
	        		}
	        	}
	        	articleList.add(articleInfoResponse);
			}
			
			pageModel.setList(articleList);
			pageModel.setPageNo(listResponseVo.getPageNo());
			pageModel.setPageSize(listResponseVo.getPageSize());
			pageModel.setTotalPages(listResponseVo.getTotalPage());
			pageModel.setTotalRecords(listResponseVo.getTotalCount());
		}else{
			pageModel.setList(articleList);
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageSize);
			pageModel.setTotalPages(1);
			pageModel.setTotalRecords(0);
		}
		
		return pageModel;
	}

	@Override
	public ArticleInfoResponse queryArticleInfo(ArticleInfoRequest request) {
		ArticleInfoResponse response = new ArticleInfoResponse();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(request.getArticleId() != null){
			params.put("articleId", request.getArticleId());
		}else{
			if(StringUtils.isNotBlank(request.getOpenId())){
				params.put("openId", request.getOpenId());
			}
		}
		HomeLetterVo homeLetterVo = homeLetterProxy.queryArticleInfo(params);
		if(homeLetterVo != null){
			response.setArticleId(homeLetterVo.getArticleId());
			response.setContent(homeLetterVo.getContent());
			response.setHeadImgUrl(homeLetterVo.getHeadImgUrl());
			response.setNickName(homeLetterVo.getNickName());
			response.setRankingNum(homeLetterVo.getRankingNum());
			response.setVoteNum(homeLetterVo.getVoteNum());
        	if(StringUtils.isNotBlank(homeLetterVo.getBuildingName())){
        		if(homeLetterVo.getBuildingName().contains("非专属楼盘") || homeLetterVo.getBuildingName().contains("库存清理")){
        			response.setBuildingName("其他楼盘");
        		}else{
        			response.setBuildingName(homeLetterVo.getBuildingName());
        		}
        	}
		}
		
		return response;
	}

	@Override
	public ActivityInfoResponse queryActivityInfo(ActivityInfoRequest request) {
		ActivityInfoResponse response = new ActivityInfoResponse();
		
		//判断活动是否已结束
		CommentLimitResponseVo commentLimitResponseVo = homeCardWcmProxy.queryCommentLimitByCode(HomeCardPraise.HOME_LETTER_LIMIT);
		if(commentLimitResponseVo != null && commentLimitResponseVo.getActiveFlag() != null && commentLimitResponseVo.getActiveFlag() == HomeCardPraise.HOME_LETTER_ACTIVITY){
			//活动有效
			response.setEndFlag(0);
		}else{
			//活动结束
			response.setEndFlag(1);
			
			//查询中奖结果
			List<ArticleInfoResponse> winningResultList = new ArrayList<ArticleInfoResponse>();
			HomeLetterWinningDto winningResponseVo = homeLetterProxy.queryWinningResult();
			if(winningResponseVo != null && CollectionUtils.isNotEmpty(winningResponseVo.getWinningResult())){
				List<HomeLetterVo> winningResult = winningResponseVo.getWinningResult();
				for (HomeLetterVo homeLetterVo : winningResult) {
					ArticleInfoResponse articleInfoResponse = new ArticleInfoResponse();
					articleInfoResponse.setArticleId(homeLetterVo.getArticleId());
		        	articleInfoResponse.setContent(homeLetterVo.getContent());
		        	articleInfoResponse.setHeadImgUrl(homeLetterVo.getHeadImgUrl());
		        	articleInfoResponse.setNickName(homeLetterVo.getNickName());
		        	articleInfoResponse.setVoteNum(homeLetterVo.getVoteNum());
		        	if(StringUtils.isNotBlank(homeLetterVo.getMobile())){
		        		articleInfoResponse.setMobile(homeLetterVo.getMobile().replaceAll(ProductProgramPraise.MOBILE_REGEX, ProductProgramPraise.MOBILE_REPLACE));
		        	}
		        	if(StringUtils.isNotBlank(homeLetterVo.getBuildingName())){
		        		if(homeLetterVo.getBuildingName().contains("非专属楼盘") || homeLetterVo.getBuildingName().contains("库存清理")){
		        			articleInfoResponse.setBuildingName("其他楼盘");
		        		}else{
		        			articleInfoResponse.setBuildingName(homeLetterVo.getBuildingName());
		        		}
		        	}
		        	winningResultList.add(articleInfoResponse);
				}
			}
			response.setWinningResultList(winningResultList);
		}
		
		//判断是否已发表过文章
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(request.getOpenId()) && !request.getOpenId().equalsIgnoreCase("undefined")){
			params.put("openId", request.getOpenId());
			HomeLetterVo homeLetterVo = homeLetterProxy.queryArticleInfo(params);
			if(homeLetterVo != null && homeLetterVo.getArticleId() > 0){
				response.setPublishFlag(1);//已发表
			}else{
				response.setPublishFlag(0);//未发表
			}
		}else{
			response.setPublishFlag(0);//默认未发表
		}
		
		return response;
	}

}
