package com.ihomefnt.o2o.intf.service.inspiration;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.inspiration.dto.KeyValue;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.HttpArticleRequest270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.HttpCommentRequest270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.HttpInspirationRequest;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.HttpInspirationRequest270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.HttpMoreInspirationRequest;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.HttpSearchArticleRequest270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpArticleResponse270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpCaseDetailResponse;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpCaseListResponse;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpCommentResponse270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpInspirationResponse270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpInspirationResponse290;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpPictureDetailResponse;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpPictureListResponse;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpSearchArticleResponse270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpStrategyDetailResponse;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpStrategyListResponse;
import com.ihomefnt.o2o.intf.domain.product.vo.response.AppButton;

public interface InspirationService {
	
	List<AppButton> queryPhotoButton();
	
	List<AppButton> queryStrategyButton();
	
	HttpCaseListResponse queryCaseList(HttpMoreInspirationRequest request);
	
	HttpCaseDetailResponse queryCaseDetail(HttpInspirationRequest request);
	
	HttpStrategyListResponse queryStrategyList(HttpMoreInspirationRequest request);
	
	HttpStrategyDetailResponse queryStrategyDetail(HttpInspirationRequest request);
	
	HttpPictureListResponse queryPictureAlbumList(HttpMoreInspirationRequest request, Long userId);
	
	HttpPictureDetailResponse queryPictureAlbumView(HttpInspirationRequest request);
	
	int updateViewCount(HttpInspirationRequest request);
	
	int updateTranspondCount(HttpInspirationRequest request);
	
	/**
	 * 获取所有文章栏目
	 * @return
	 */
	List<KeyValue>	getArticleTypeList290();
	
	/**
	 * 
	 * 获取改版后的灵感版本首页 （2.9.3版本）
	 */
	HttpInspirationResponse290 getHome290(HttpInspirationRequest270  request);
	
	/**
	 * 获取所有文章栏目
	 * @return
	 */
	List<KeyValue>	getArticleTypeList270();
	
	/**
	 * 
	 * 获取改版后的灵感版本首页
	 */
	HttpInspirationResponse270 getHome270(HttpInspirationRequest270  request);
	
	/**
	 * 根据主键获取灵感文章
	 * 
	 * @return
	 */
	HttpArticleResponse270 getArticleDetailByPK270(HttpArticleRequest270   request,Long userId);
	
	/**
	 * 根据灵感文章主键获取评论列表
	 * @param request
	 * @return
	 */
	HttpCommentResponse270 getCommentListByArticleId(HttpArticleRequest270   request);
	
	/**
	 * 点赞 ：0点赞成功1已点赞2点赞失败
	 * @param articleId
	 * @param userId
	 * @return
	 */
	int praiseArticle(Long articleId,Long userId);
	
	/**
	 * 新增评论
	 * @param request
	 * @return
	 */
	boolean addComment(HttpCommentRequest270 request,Long userId);

	/**
	 * 搜索灵感文章
     *
	 */
	HttpSearchArticleResponse270 searhArticleList(HttpSearchArticleRequest270 request);
	
	
	/**
	 * 转发文章
	 * @param articleId
	 * @return
	 */
	boolean forwardArticle(Long articleId);
	
	/**
	 * 收藏文章
	 * @param articleId
	 * @return
	 */
	boolean collectArticle(Long articleId);

    HttpCaseDetailResponse queryCaseDetailApi(HttpInspirationRequest request);

    HttpStrategyDetailResponse queryStrategyDetailApi(HttpInspirationRequest request);
}
