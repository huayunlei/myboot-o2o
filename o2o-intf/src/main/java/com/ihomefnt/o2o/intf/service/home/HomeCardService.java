package com.ihomefnt.o2o.intf.service.home;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.homecard.vo.request.*;
import com.ihomefnt.o2o.intf.domain.homecard.vo.response.*;

import java.util.List;
import java.util.Map;

/**
 * APP3.0新版首页Service层
 * @author ZHAO
 */
public interface HomeCardService {
	/**
	 * 首页推荐版块
	 * 卡片类型（1DNA、2样板套装、3banner、4艺术品、5视频）
	 * @return
	 */
	List<RecommendBoardResponse> getRecommendBoard(HttpBaseRequest request, HttpUserInfoRequest userDto);
	
	/**
	 * 首页产品版块
	 * @return
	 */
	ProductBoardListResponse getProductBoard(ProductBoardRequest request);
	
	/**
	 * 首页视频版块
	 * @return
	 */
	VideoBoardResponse getVideoBoard(VideoBoardRequest request);
	
	/**
	 * 首页产品版块筛选条件
	 * @param request 
	 * @return
	 */
	Map getProductFilterInfo(ProductFilterInfoRequest request);

	/**
	 * DNA详情
	 * @return
	 */
	DnaDetailResponse getDnaDetailById(DnaDetailRequest request);
	
	/**
	 * DNA硬装清单
	 * @param request
	 * @return
	 */
	HardSpaceListResponseVo getHardListByCondition(HardSoftRequest request);

	/**
	 * DNA软装清单
	 * @param request
	 * @return
	 */
	SoftDetailListResponseVo getSoftListByCondition(HardSoftRequest request);
	
	/**
	 * DNA点赞
	 * @param userId
	 * @param dnaId
	 * @return
	 */
	DnaFavoriteResultResponse setDnaFavorite(Integer userId, Integer dnaId);
	
	/**
	 * 查询用户是否已经点赞
	 * @param userId
	 * @param dnaId
	 * @return
	 */
	UserFavoriteFlagResponseVo queryUserFavoriteFlagByDnaId(Integer userId, Integer dnaId);
	
	/**
	 * 新增DNA评论
	 * @param request
	 * @return
	 */
	DnaCommentResultResponse addDnaComment(DnaCommentRequest request);
	
	/**
	 * 根据DNA ID查询评论（分页）
	 * @param request
	 * @return
	 */
	DnaCommentListResponse queryDnaCommentListByDnaId(DnaCommentListRequest request);
	
	/**
	 * DNA分享
	 * @param dnaId
	 * @return
	 */
	DnaShareResponse shareDna(Integer dnaId);

	/**
	 * DNA转发操作记录
	 * 
	 * @param dnaId
	 */
	void setDnaForward(Integer dnaId);
	
	/**
	 * 将评论dna集合封装成map
	 * @param dnaIdList
	 * @return
	 */
	Map<Integer, Integer> getCommentMapByDnaIdList(List<Integer> dnaIdList,int type, HttpUserInfoRequest userDto);
	
	/**
	 * 将喜欢dna集合封装成map
	 * 
	 * @param dnaIdList
	 * @return
	 */
	Map<Integer, Integer> getDnaMapByDnaIdList(List<Integer> dnaIdList);

	HouseInfoResponse getHouseInfoByLayoutId(HouseInfoQueryRequest request);

    DesignerMoreDetailResponse getDesignerDetailById(DesignerMoreDetailRequest request);

	/**
	 * 查询是否有提交设计需求权限 true 有false 没有
	 * @param request
	 * @return
	 */
	SubmitDesignResponse querySubmitDesignRequirement(HouseInfoQueryRequest request);

	/**
	 * 空间标识需求提示是否需要更新
	 * @param appVersion
	 * @param bundleVersions
	 * @param osType
	 * @return
	 */
	boolean getSpaceMarkMustUpdate(String appVersion,Object bundleVersions,Integer osType);
}
