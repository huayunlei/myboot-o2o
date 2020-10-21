package com.ihomefnt.o2o.service.service.life;

import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.life.dto.*;
import com.ihomefnt.o2o.intf.domain.life.vo.response.LifeCommentListResponse;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.proxy.life.LifeCommentProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.life.LifeCommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.testng.collections.Lists;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author liyonggang
 * @create 2018-11-01 17:46
 */
@Service
public class LifeCommentServiceImpl implements LifeCommentService {

    @Autowired
    private LifeCommentProxy lifeCommentDao;
    @Autowired
    private UserProxy userProxy;

    /**
     * 获取生活评论列表
     *
     * @param request
     * @return
     */
    @Override
    public PageResponse<LifeCommentListResponse> getLifeCommentList(LifeCommentListDto request) {
        try {
            PageResponse<LifeCommentListResponse> lifeCommentList = lifeCommentDao.getLifeCommentList(request);
            List<LifeCommentListResponse> list = lifeCommentList.getList();
            if (!list.isEmpty()) {
                Set<Integer> userIds = list.stream().map(LifeCommentListResponse::getUserId).collect(Collectors.toSet());
                list.forEach(action -> {
                    if (!CollectionUtils.isEmpty(action.getReplyList())) {
                        userIds.addAll(action.getReplyList().stream().map(ReplyDto::getUserId).collect(Collectors.toSet()));
                    }
                });
                List<UserRightsDto> rightList = lifeCommentDao.getUsersTopRight(Lists.newArrayList(userIds));
                List<UserDto> userDtos = userProxy.batchQueryUserInfo(Lists.newArrayList(userIds));
                if (!CollectionUtils.isEmpty(rightList)) {
                    Map<Long, UserRightsDto> userRightsDtoMap = rightList.stream().collect(Collectors.toMap(right->right.getUserId(),right->right));
                    list.forEach(action -> {
                        UserRightsDto userRightsDtos = userRightsDtoMap.get(action.getUserId());
                        if (userRightsDtos != null) {
                            action.setLevel(userRightsDtos.getMaxGradeId());//设置客户权益等级
                        }
                    });
                }
                if (!CollectionUtils.isEmpty(userDtos)){
                    Map<Integer, UserDto> userMap = userDtos.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
                    list.forEach(action -> {
                        UserDto userDto = userMap.get(action.getUserId());
                        if (userDto != null&&userDto.getMember()!=null&& StringUtils.isNotBlank(userDto.getMember().getuImg())) {
                            action.setAvatar(AliImageUtil.imageCompress(userDto.getMember().getuImg(),request.getOsType(),request.getWidth(), ImageConstant.SIZE_SMALL));
                        }else {
                            action.setAvatar(StaticResourceConstants.USER_DEFAULT_HEAD_IMAGE);
                        }
                    });
                }
            }
            return lifeCommentList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 发布评论
     *
     * @param request
     * @return
     */
    @Override
    public LifeCommentListResponse publishComment(PublishCommentDto request) {
    	LifeCommentListResponse response = lifeCommentDao.publishComment(request);
        if (response==null){
            return null;
        }
        List<UserRightsDto> usersTopRight = lifeCommentDao.getUsersTopRight(Lists.newArrayList(response.getUserId()));
        if (CollectionUtils.isEmpty(usersTopRight)){
            return response;
        }
        response.setLevel(usersTopRight.get(0).getMaxGradeId());
        return response;
    }

    /**
     * 评论点赞
     *
     * @param request
     * @return
     */
    @Override
    public Integer toPraised(PublishCommentDto request) {
        UserDto userVo =userProxy.getUserByToken(request.getAccessToken());
        if (userVo == null) {
        	throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN,"未登录");
        } else {
            request.setUserId(Long.valueOf(userVo.getId()));
            if (userVo.getMember()!=null) {
                request.setUserName(userVo.getMember().getNickName());
                request.setHeadUrl(userVo.getMember().getuImg());
            }
        }
        Integer result = lifeCommentDao.toPraised(request);
        if (1 != result) {
        	throw new BusinessException(MessageConstant.FAILED);
        }
        return result;
    }

    /**
     * 删除评论
     *
     * @param request
     * @return
     */
    @Override
    public Integer deleteComment(PublishCommentDto request) {
        HttpUserInfoRequest userVo = request.getUserInfo();
        if (userVo == null) {
        	throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN,"未登录");
        } else {
            request.setUserId(Long.valueOf(userVo.getId()));
        }
        Integer result = lifeCommentDao.deleteComment(request);
        if (1 != result) {
        	throw new BusinessException(MessageConstant.FAILED);
        }
        return result;
    }

    /**
     * 查询文章简单信息
     *
     * @param request
     * @return
     */
    @Override
    public LifeSimpleInfoDto articleSimpleInfo(PublishCommentDto request) {
        return lifeCommentDao.articleSimpleInfo(request);
    }

}
