package com.ihomefnt.o2o.service.proxy.home;

import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Maps;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.homecard.dto.*;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BannerResponseVo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.UserComment;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.SolutionDraftResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.DraftInfoResponse;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.DraftSimpleRequestPage;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.OrderDraftDto;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardWcmProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.oms.trade.util.PageModel;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP3.0新版首页WCM服务代理DAO层实现层
 *
 * @author ZHAO
 */
@Service
public class HomeCardWcmProxyImpl implements HomeCardWcmProxy {
    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    private static final Logger LOG = LoggerFactory.getLogger(HomeCardWcmProxyImpl.class);

    @Override
    public CardListResponseVo getCardConfigList() {
        JSONObject param = new JSONObject();

        HttpBaseResponse<CardListResponseVo> responseVo = strongSercviceCaller.post(
                "wcm-web.card.listCardForApp", param, new TypeReference<HttpBaseResponse<CardListResponseVo>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public VideoListResponseVo getVideoConfigList() {
        JSONObject param = new JSONObject();

        HttpBaseResponse<VideoListResponseVo> responseVo = strongSercviceCaller.post(
                "wcm-web.video.listTopForApp", param, new TypeReference<HttpBaseResponse<VideoListResponseVo>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public VideoListResponseVo getVideoList(Integer typeId, Integer pageNo, Integer pageSize) {
        JSONObject param = new JSONObject();
        if (pageNo != null && pageNo > 0) {
            param.put("pageNo", pageNo);
        }
        if (pageSize != null && pageSize > 0) {
            param.put("pageSize", pageSize);
        }
        if (typeId > 0) {
            param.put("type", typeId);
        }

        HttpBaseResponse<VideoListResponseVo> responseVo = strongSercviceCaller.post(
                "wcm-web.video.listVideoForApp", param, new TypeReference<HttpBaseResponse<VideoListResponseVo>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public CityHardListReaponseVo getCityHardList() {
        JSONObject param = new JSONObject();

        HttpBaseResponse<CityHardListReaponseVo> responseVo = strongSercviceCaller.post(
                "wcm-web.hard.getCondition", param, new TypeReference<HttpBaseResponse<CityHardListReaponseVo>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public VideoTypeListResponseVo getVideoTypeList() {
        JSONObject param = new JSONObject();

        HttpBaseResponse<VideoTypeListResponseVo> responseVo = strongSercviceCaller.post(
                "wcm-web.video.getCondition", param, new TypeReference<HttpBaseResponse<VideoTypeListResponseVo>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public boolean addFavoriteRecord(Integer userId, Integer dnaId, Integer favoriteFlag) {
        JSONObject param = new JSONObject();
        param.put("userId", userId);
        param.put("dnaId", dnaId);
        param.put("favoriteFlag", favoriteFlag);

        HttpBaseResponse<Boolean> responseVo = strongSercviceCaller.post(
                "wcm-web.favorite.addFavoriteRecord", param, new TypeReference<HttpBaseResponse<Boolean>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return false;
    }

    @Override
    public Integer queryFavoriteCountByDnaId(Integer dnaId) {
        JSONObject param = new JSONObject();
        param.put("dnaId", dnaId);

        HttpBaseResponse<Integer> responseVo = strongSercviceCaller.post(
                "wcm-web.favorite.queryFavoriteCountByDnaId", param, new TypeReference<HttpBaseResponse<Integer>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }


    @Override
    public DnaFavoriteCountListResponseVo queryFavoriteCountListByDnaIdList(List<Integer> dnaIdList) {
        JSONObject param = new JSONObject();
        param.put("dnaIdList", dnaIdList);

        HttpBaseResponse<DnaFavoriteCountListResponseVo> responseVo = strongSercviceCaller.post(
                "wcm-web.favorite.queryFavoriteCountListByDnaIdList", param, new TypeReference<HttpBaseResponse<DnaFavoriteCountListResponseVo>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    @Async
    public void addVisitRecord(Integer dnaId, Integer visitType) {
        JSONObject param = new JSONObject();
        param.put("dnaId", dnaId);
        param.put("visitType", visitType);
        strongSercviceCaller.post(
                "wcm-web.visit.addVisitRecord", param, new TypeReference<HttpBaseResponse<Boolean>>() {
                });
    }

    @Override
    public DnaVisitListResponse queryVisitCountListByDnaIds(List<Integer> dnaIds, Integer visitType) {
        JSONObject param = new JSONObject();
        param.put("dnaIds", dnaIds);
        param.put("visitType", visitType);

        HttpBaseResponse<DnaVisitListResponse> responseVo = strongSercviceCaller.post(
                "wcm-web.visit.queryVisitCountListByDnaIds", param, new TypeReference<HttpBaseResponse<DnaVisitListResponse>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public Integer queryVisitCountByDnaId(Integer dnaId, Integer visitType) {
        JSONObject param = new JSONObject();
        param.put("dnaId", dnaId);
        param.put("visitType", visitType);

        HttpBaseResponse<Integer> responseVo = strongSercviceCaller.post(
                "wcm-web.visit.queryVisitCountByDnaId", param, new TypeReference<HttpBaseResponse<Integer>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public Integer addComment(Integer dnaId, String mobile, String content, Integer starNum, Integer commentType, Integer replyCommentId) {
        JSONObject param = new JSONObject();
        param.put("dnaId", dnaId);
        param.put("mobile", mobile);
        param.put("content", content);
        param.put("starNum", starNum);
        param.put("commentType", commentType);
        param.put("replyCommentId", replyCommentId);

        HttpBaseResponse<Integer> responseVo = strongSercviceCaller.post(
                "wcm-web.comment.addComment", param, new TypeReference<HttpBaseResponse<Integer>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public CommentListResponseVo queryCommentListByDnaId(Integer dnaId, Integer commentType, Integer pageNo,
                                                         Integer pageSize, Integer userLevel, Integer userId) {
        JSONObject param = new JSONObject();
        param.put("dnaId", dnaId);
        param.put("commentType", commentType);
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);
        param.put("userLevel", userLevel);
        param.put("userId", userId);

        HttpBaseResponse<CommentListResponseVo> responseVo = strongSercviceCaller.post(
                "wcm-web.comment.queryCommentListByDnaId", param, new TypeReference<HttpBaseResponse<CommentListResponseVo>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public CommentLimitResponseVo queryCommentLimitByCode(String code) {
        JSONObject param = new JSONObject();
        param.put("code", code);

        HttpBaseResponse<CommentLimitResponseVo> responseVo = strongSercviceCaller.post(
                "wcm-web.comment.queryCommentLimitByCode", param, new TypeReference<HttpBaseResponse<CommentLimitResponseVo>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public DnaFavoriteResponseVo queryFavoriteRecordByUserIdAndDnaId(Integer userId, Integer dnaId) {
        JSONObject param = new JSONObject();
        param.put("userId", userId);
        param.put("dnaId", dnaId);

        HttpBaseResponse<DnaFavoriteResponseVo> responseVo = strongSercviceCaller.post(
                "wcm-web.favorite.queryFavoriteRecordByUserIdAndDnaId", param, new TypeReference<HttpBaseResponse<DnaFavoriteResponseVo>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public CommentResponseVo queryCommentById(Integer id) {
        JSONObject param = new JSONObject();
        param.put("id", id);

        HttpBaseResponse<CommentResponseVo> responseVo = strongSercviceCaller.post(
                "wcm-web.comment.queryCommentById", param, new TypeReference<HttpBaseResponse<CommentResponseVo>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public Integer queryCommentCountByDnaId(Integer dnaId, Integer commentType, Integer userLevel, Integer userId) {
        JSONObject param = new JSONObject();
        param.put("dnaId", dnaId);
        param.put("commentType", commentType);
        param.put("userLevel", userLevel);
        param.put("userId", userId);

        HttpBaseResponse<Integer> responseVo = strongSercviceCaller.post(
                "wcm-web.comment.queryCommentCountByDnaId", param, new TypeReference<HttpBaseResponse<Integer>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public DnaFavoriteCountListResponseVo queryCommentCountListByDnaIdList(List<Integer> dnaIdList, Integer commentType, Integer userLevel, Integer userId) {
        JSONObject param = new JSONObject();
        param.put("dnaIdList", dnaIdList);
        param.put("commentType", commentType);
        param.put("userLevel", userLevel);
        param.put("userId", userId);

        HttpBaseResponse<DnaFavoriteCountListResponseVo> responseVo = strongSercviceCaller.post(
                "wcm-web.comment.queryCommentCountListByDnaIdList", param, new TypeReference<HttpBaseResponse<DnaFavoriteCountListResponseVo>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public CommentReplyListResponseVo queryReplyCommentListByPid(String commentId, Integer commentType) {
        JSONObject param = new JSONObject();
        param.put("commentId", commentId);
        param.put("commentType", commentType);

        HttpBaseResponse<CommentReplyListResponseVo> responseVo = strongSercviceCaller.post(
                "wcm-web.comment.queryReplyCommentListByPid", param, new TypeReference<HttpBaseResponse<CommentReplyListResponseVo>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }


    @Override
    public CommentReplyListResponseVo queryReplyCommentListByPids(List<String> commentIds, Integer commentType) {
        JSONObject param = new JSONObject();
        param.put("commentIds", commentIds);
        param.put("commentType", commentType);

        HttpBaseResponse<CommentReplyListResponseVo> responseVo = strongSercviceCaller.post(
                "wcm-web.comment.queryReplyCommentListByPids", param, new TypeReference<HttpBaseResponse<CommentReplyListResponseVo>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public List<BannerResponseVo> queryBannerByType() {
        HttpBaseResponse<List<BannerResponseVo>> responseVo = strongSercviceCaller.post(
                "wcm-web.banner.queryBannerByType", new Object(), new TypeReference<HttpBaseResponse<List<BannerResponseVo>>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "o2o-api", keyGenerator = "springCacheKeyGenerator")
    public PageModel queryOrderCommentListByCondition(QueryOrderCommentRequestVo params) {
        HttpBaseResponse<PageResponse<UserComment>> responseVo = strongSercviceCaller.post(
                "wcm-web.ordercomment.queryOrderCommentListByCondition", params, new TypeReference<HttpBaseResponse<PageResponse<UserComment>>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            PageResponse<UserComment> pageResponse = responseVo.getObj();
            PageModel pageModel = JsonUtils.json2obj(JsonUtils.obj2json(pageResponse), PageModel.class);
            pageModel.setTotalPages(pageResponse.getTotalPage());
            pageModel.setTotalRecords(pageResponse.getTotalCount());
            return pageModel;
        }
        return null;
    }

    @Override
    public String addOrderDraft(Map<String, Object> params) {
        HttpBaseResponse<String> responseVo = strongSercviceCaller.post(
                "wcm-web.orderdraft.addOrderDraft", params, new TypeReference<HttpBaseResponse<String>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public String addDraft(Map<String, Object> params) {
        HttpBaseResponse<OrderDraftDto> responseVo = strongSercviceCaller.post(
                "wcm-web.draftNew.addDraft", params, new TypeReference<HttpBaseResponse<OrderDraftDto>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj().getDraftId();
        }
        return null;
    }

    @Override
    public String updateDraft(Map<String, Object> params) {
        HttpBaseResponse<OrderDraftDto> responseVo = strongSercviceCaller.post(
                "wcm-web.draftNew.updateDraft", params, new TypeReference<HttpBaseResponse<OrderDraftDto>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj().getDraftId();
        }
        return null;
    }

    @Override
    public DraftInfoResponse queryDraftInfo(Map<String, Object> params) {
        HttpBaseResponse<DraftInfoResponse> responseVo = strongSercviceCaller.post(
                "wcm-web.draftNew.queryDraftInfo", params, new TypeReference<HttpBaseResponse<DraftInfoResponse>>() {
                });
        if (responseVo != null) {
            if (responseVo.getObj() != null) {
                return responseVo.getObj();
            }
            throw new BusinessException(HttpResponseCode.RESPONSE_DATE_NOT_EXIST, MessageConstant.DRAFT_NOT_EXIST);
        }
        throw new BusinessException(MessageConstant.FAILED);
    }

    @Override
    public DraftSimpleRequestPage queryDraftList(Map<String, Object> params) {
        HttpBaseResponse<DraftSimpleRequestPage> responseVo = strongSercviceCaller.post(
                "wcm-web.draftNew.queryDraftListByPage", params, new TypeReference<HttpBaseResponse<DraftSimpleRequestPage>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        } else {
            throw new BusinessException(HttpResponseCode.RESPONSE_DATE_NOT_EXIST, MessageConstant.DRAFT_NOT_EXIST);
        }
    }


    @Override
    public SolutionDraftResponse queryOrderDraftByCondition(Map<String, Object> params) {
        HttpBaseResponse<SolutionDraftResponse> responseVo = strongSercviceCaller.post(
                "wcm-web.orderdraft.queryOrderDraftByCondition", params, new TypeReference<HttpBaseResponse<SolutionDraftResponse>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        } else {
            throw new BusinessException(HttpResponseCode.RESPONSE_DATE_NOT_EXIST, MessageConstant.DRAFT_NOT_EXIST);
        }
    }

    @Override
    public Boolean deleteOrderDraft(Map<String, Object> params) {
        HttpBaseResponse<Boolean> responseVo = strongSercviceCaller.post(
                "wcm-web.orderdraft.deleteOrderDraft", params, new TypeReference<HttpBaseResponse<Boolean>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public Boolean signDraft(Map<String, Object> params) {
        HttpBaseResponse responseVo = strongSercviceCaller.post(
                "wcm-web.draftNew.signDraft", params, new TypeReference<HttpBaseResponse>() {
                });

        if (responseVo != null && responseVo.getCode() == 1) {
            return true;
        }
        LOG.info("wcm-web.draftNew.signDraft fail");
        return false;
    }

    @Override
    public DraftInfoResponse queryOrderDraftTotalStatus(Object param) {
        HttpBaseResponse<DraftInfoResponse> responseVo = strongSercviceCaller.post(
                "wcm-web.programOffLineDraw.queryOrderDraftTotalStatus", param, new TypeReference<HttpBaseResponse<DraftInfoResponse>>() {
                });
        return responseVo.getObj();
    }

    @Override
    public Integer readDraft(String draftId) {
        HttpBaseResponse<Integer> responseVo = strongSercviceCaller.post(
                "wcm-web.programOffLineDraw.readDraft", Maps.newHashMap("draftId", draftId), new TypeReference<HttpBaseResponse<Integer>>() {
                });
        return responseVo.getObj();
    }

    @Override
    public Integer queryOrderDraftCountByCondition(Map<String, Object> params) {
        HttpBaseResponse<Integer> responseVo = strongSercviceCaller.post(
                "wcm-web.orderdraft.queryOrderDraftCountByCondition", params, new TypeReference<HttpBaseResponse<Integer>>() {
                });

        if (responseVo != null && responseVo.getObj() != null) {
            return responseVo.getObj();
        }
        return null;
    }

    @Override
    public Boolean deleteDraft(Map<String, Object> params) {
        HttpBaseResponse responseVo = strongSercviceCaller.post(
                "wcm-web.draftNew.deleteDraft", params, new TypeReference<HttpBaseResponse>() {
                });

        if (responseVo != null && responseVo.getCode() == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteProgramMasterAndSubTask(Long orderNum, Long draftProfileNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderNum", orderNum);
        params.put("draftProfileNum", draftProfileNum);

        HttpBaseResponse responseVo = strongSercviceCaller.post(
                "wcm-web.draftNew.deleteProgramMasterAndSubTask", params, new TypeReference<HttpBaseResponse>() {
                });

        if (responseVo != null && responseVo.getCode() == 1) {
            return true;
        }
        return false;
    }

}
