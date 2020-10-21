package com.ihomefnt.o2o.intf.proxy.home;

import com.ihomefnt.o2o.intf.domain.homecard.dto.*;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BannerResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.DraftSimpleRequestPage;
import com.ihomefnt.o2o.intf.domain.program.vo.response.DraftInfoResponse;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.SolutionDraftResponse;
import com.ihomefnt.oms.trade.util.PageModel;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;

/**
 * APP3.0新版首页WCM服务代理接口DAO层
 * http://192.168.1.11:18080/wcm-web/swagger
 * wcm-web
 * @author ZHAO
 */
public interface HomeCardWcmProxy {
	
	/**
	 * 查询DNA配置列表（卡片配置）
	 * @return
	 */
	CardListResponseVo getCardConfigList();
	
	/**
	 * 查询Video配置列表（置顶视频列表）
	 * @return
	 */
	VideoListResponseVo getVideoConfigList();

	/**
	 * 查询所以Video列表
	 * @param typeId 视频类型
	 * @param pageNo 第几页
	 * @param pageSize 每页大小
	 * @return
	 */
	VideoListResponseVo getVideoList(Integer typeId, Integer pageNo, Integer pageSize);
	
	/**
	 * 查询城市、套系字典集合
	 * @return
	 */
	CityHardListReaponseVo getCityHardList();
	
	/**
	 * 查询视频分类
	 * @return
	 */
	VideoTypeListResponseVo getVideoTypeList();
	
	/**
	 * 新增DNA点赞记录
	 * @param userId
	 * @param dnaId
	 * @param favoriteFlag 0未点赞1已点赞
	 * @return
	 */
	boolean addFavoriteRecord(Integer userId, Integer dnaId, Integer favoriteFlag);
	
	/**
	 * 根据dna ID查询点赞数
	 * @param dnaId
	 * @return
	 */
	Integer queryFavoriteCountByDnaId(Integer dnaId);
	
	/**
	 * 根据dna ID集合分别查询点赞数
	 * @param dnaId
	 * @return
	 */
	DnaFavoriteCountListResponseVo queryFavoriteCountListByDnaIdList(List<Integer> dnaIdList);
	
	/**
	 * 根据用户ID和DNA ID查询点赞记录
	 * @param userId
	 * @param dnaId
	 * @return
	 */
	DnaFavoriteResponseVo queryFavoriteRecordByUserIdAndDnaId(Integer userId, Integer dnaId);

	/**
	 * 新增浏览记录
	 * @param dnaId
	 * @param visitType
	 * @return
	 */
	@Async
	void addVisitRecord(Integer dnaId, Integer visitType);
	
	/**
	 * 根据ID和浏览类型查询浏览量
	 * @param dnaId
	 * @param visitType
	 * @return
	 */
	Integer queryVisitCountByDnaId(Integer dnaId, Integer visitType);
	
	/**
	 * 新增用户评论
	 * @param dnaId
	 * @param mobile
	 * @param content
	 * @param starNum 评论星数
	 * @param commentType  1DNA、2艺术品、3晒家
	 * @param replyCommentId 回复评论ID
	 * @return
	 */
	Integer addComment(Integer dnaId, String mobile, String content, Integer starNum, Integer commentType, Integer replyCommentId);
	
	/**
	 * 根据DNAid查询评论（分页）
	 * @param dnaId
	 * @param commentType
	 * @param userLevel
     * @param id
	 * @return
	 */
	CommentListResponseVo queryCommentListByDnaId(Integer dnaId, Integer commentType, Integer pageNo, Integer pageSize, Integer userLevel, Integer userId);
	
	/**
	 * 查询评论权限
	 * @param code
	 * @return
	 */
	CommentLimitResponseVo queryCommentLimitByCode(String code);
	
	/**
	 * 根据ID查询评论记录
	 * @param id
	 * @return
	 */
	CommentResponseVo queryCommentById(Integer id);
	
	/**
	 * 根据DNNA  ID查询评论总数
	 * @param dnaId
	 * @return
	 */
	Integer queryCommentCountByDnaId(Integer dnaId, Integer commentType, Integer userLevel, Integer userId);
	
	/**
	 * 根据DNNA  ID分别查询评论总数
	 * @param dnaId
	 * @return
	 */
	DnaFavoriteCountListResponseVo queryCommentCountListByDnaIdList(List<Integer> dnaId, Integer commentType, Integer userLevel, Integer userId);
	
	/**
	 * 根据评论ID查询评论回复集合
	 * @param commentId
	 * @param commentType
	 * @return
	 */
	CommentReplyListResponseVo queryReplyCommentListByPid(String commentId, Integer commentType);
	
	/**
	 * 根据评论ID查询评论回复集合
	 * @param commentId
	 * @param commentType
	 * @return
	 */
	CommentReplyListResponseVo queryReplyCommentListByPids(List<String> commentIds, Integer commentType);

	/**
	 * 批量查询dna的操作记录
	 * 
	 * @param dnaIdList
	 * @param forwardDna:1 浏览dna 2浏览方案3转发dna
	 * @return
	 */
	DnaVisitListResponse queryVisitCountListByDnaIds(List<Integer> dnaIdList, Integer forwardDna);
	
	/**
	 * 根据类型查询banner
	 * @return
	 * Author: ZHAO
	 * Date: 2018年7月20日
	 */
	List<BannerResponseVo> queryBannerByType();
	
	/**
	 * 查询订单评论
	 * @param params
	 * @return
	 * Author: ZHAO
	 * Date: 2018年7月22日
	 */
	PageModel queryOrderCommentListByCondition(QueryOrderCommentRequestVo params);

	/**
	 * 新增选方案草稿
	 * @param params
	 * @return
	 * Author: ZHAO
	 * Date: 2018年7月22日
	 */
	String addOrderDraft(Map<String, Object> params);
	
	/**
	 * 查询选方案草稿
	 * @param params
	 * @return
	 * Author: ZHAO
	 * Date: 2018年7月22日
	 */
	SolutionDraftResponse queryOrderDraftByCondition(Map<String, Object> params);
	
	/**
	 * 删除方案草稿
	 * @param params
	 * @return
	 * Author: ZHAO
	 * Date: 2018年7月23日
	 */
	Boolean deleteOrderDraft(Map<String, Object> params);
	
	/**
	 * 根据条件查询方案草稿数量
	 * @param params
	 * @return
	 * Author: ZHAO
	 * Date: 2018年7月23日
	 */
	Integer queryOrderDraftCountByCondition(Map<String, Object> params);

	/**
	 * 新增草稿
	 * @param params
	 * @return
	 */
	String addDraft(Map<String, Object> params);

	/**
	 * 更新草稿
	 * @param params
	 * @return
	 */
	String updateDraft(Map<String, Object> params);

	/**
	 * 查询草稿全量数据
	 * @param params
	 * @return
	 */
	DraftInfoResponse queryDraftInfo(Map<String, Object> params);

	/**
	 * 草稿箱列表
	 * @param params
	 * @return
	 */
	DraftSimpleRequestPage queryDraftList(Map<String, Object> params);

	/**
	 * 草稿签约状态设置
	 * @param params
	 * @return
	 */
	Boolean signDraft(Map<String, Object> params);

	DraftInfoResponse queryOrderDraftTotalStatus(Object param);


	Integer readDraft(String draftId);

	/**
	 * 逻辑删除草稿
	 * @param params
	 * @return
	 */
	Boolean deleteDraft(Map<String, Object> params);

	Boolean deleteProgramMasterAndSubTask(Long orderNum, Long draftProfileNum);

}
