package com.ihomefnt.o2o.service.proxy.life;

import com.google.common.collect.ImmutableBiMap;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.o2o.intf.domain.life.dto.*;
import com.ihomefnt.o2o.intf.domain.life.vo.response.LifeCommentListResponse;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinOrderServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.life.LifeCommentProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;
import com.ihomefnt.o2o.intf.manager.util.common.response.WcmExt;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author liyonggang
 * @create 2018-11-02 9:39
 */
@Repository
public class LifeCommentProxyImpl implements LifeCommentProxy {
	@Autowired
	private StrongSercviceCaller strongSercviceCaller;
	
    /**
     * 生活板块文章评论列表
     *
     * @param params
     * @return
     */
    @Override
    public PageResponse<LifeCommentListResponse> getLifeCommentList(LifeCommentListDto params) {
        PageResponse<LifeCommentListResponse> pageResponse = null;
        try {
            pageResponse = strongSercviceCaller.post(WcmWebServiceNameConstants.LIFE_GET_LIFE_COMMENT_LIST, params, new TypeReference<PageResponse<LifeCommentListResponse>>(){});
        } catch (Exception e) {
        	return null;
        }
        return pageResponse;
    }

    /**
     * 发布评论
     *
     * @param params
     * @return
     */
    @Override
    public LifeCommentListResponse publishComment(PublishCommentDto params) {
        HttpBaseResponse<LifeCommentListResponse> result = null;
        try {
            result = strongSercviceCaller.post(WcmWebServiceNameConstants.LIFE_PUBLISH_COMMENT, params, new TypeReference<HttpBaseResponse<LifeCommentListResponse>>(){});
        } catch (Exception e) {
            return  null;
        }
        if (null == result) {
        	return null;
        }
        return result.getObj();
    }

    /**
     * 评论点赞
     *
     * @param params
     * @return
     */
    @Override
    public Integer toPraised(PublishCommentDto params) {
        HttpBaseResponse<?> result = null;
        try {
            result = strongSercviceCaller.post(WcmWebServiceNameConstants.LIFE_TO_PRAISED, params, HttpBaseResponse.class);
        } catch (Exception e) {
        	return -1;
        }
        
        if (result == null || !HttpResponseCode.SUCCESS.equals(result.getCode())) {
            String msg = null;
            Long code = HttpResponseCode.FAILED;
            if (null != result) {
                code = result.getCode() != null ? result.getCode() : code;
                if (null != result.getExt()) {
                    WcmExt wcmExt = ModelMapperUtil.strictMap(result.getExt(), WcmExt.class);
                    msg = wcmExt == null ? "" : wcmExt.getMsg();
                }
            }
            throw new BusinessException(code, msg);
        }
        return 1;
    }

    /**
     * 删除评论
     *
     * @param params
     * @return
     */
    @Override
    public Integer deleteComment(PublishCommentDto params) {
        HttpBaseResponse<?> result = null;
        try {
            result = strongSercviceCaller.post(WcmWebServiceNameConstants.LIFE_DELETE_COMMENT, params, HttpBaseResponse.class);
        } catch (Exception e) {
        	return -1;
        }
        
        if (result == null || !HttpResponseCode.SUCCESS.equals(result.getCode())) {
            String msg = null;
            Long code = HttpResponseCode.FAILED;
            if (null != result) {
                code = result.getCode() != null ? result.getCode() : code;
                if (null != result.getExt()) {
                    WcmExt wcmExt = ModelMapperUtil.strictMap(result.getExt(), WcmExt.class);
                    msg = wcmExt == null ? "" : wcmExt.getMsg();
                }
            }
            throw new BusinessException(code, msg);
        }
        return 1;
    }

    /**
     * 查询文章简单信息
     *
     * @param params
     * @return
     */
    @Override
    public LifeSimpleInfoDto articleSimpleInfo(PublishCommentDto params) {
        HttpBaseResponse<LifeSimpleInfoDto> result = null;
        try {
            result = strongSercviceCaller.post(WcmWebServiceNameConstants.LIFE_ARTICLE_SIMPLE_INFO, params, new TypeReference<HttpBaseResponse<LifeSimpleInfoDto>>(){});
        } catch (Exception e) {
            return null;
        }
        if (null == result) {
        	return null;
        }
        return result.getObj();
    }

    @Override
    public List<ReplyDto> getCommentReListByCommentIds(List<Long> commentIds) {
        List<ReplyDto> result = null;
        try {
            result =  strongSercviceCaller.post(WcmWebServiceNameConstants.LIFE_GET_COMMENT_RELIST_BY_COMMENT_IDS, commentIds, new TypeReference<List<LifeCommentListResponse>>(){});
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 批量查询用户权益
     *
     * @param userIds
     * @return
     */
    @Override
    public List<UserRightsDto> getUsersTopRight(List<Integer> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return null;
        }
        ResponseVo<List<UserRightsDto>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_CUSTOMER_INFO_BY_PARAM, ImmutableBiMap.of("userIds", userIds), 
            		new TypeReference<List<UserRightsDto>>(){});
        } catch (Exception e) {
        	return null;
        }
        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
    	return responseVo.getData();
    }
}
