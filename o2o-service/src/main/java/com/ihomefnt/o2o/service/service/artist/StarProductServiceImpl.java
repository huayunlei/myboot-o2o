/*
 * Author: ZHAO
 * Date: 2018/10/11
 * Description:StarProductServiceImpl.java
 */
package com.ihomefnt.o2o.service.service.artist;

import com.ihomefnt.common.util.DateUtils;
import com.ihomefnt.o2o.intf.dao.artcomment.ArtCommentDao;
import com.ihomefnt.o2o.intf.domain.artcomment.dto.ArtCommentDto;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarArtRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarUserLoginRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtCustomRecordResponse;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtStarGood;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtStarWork;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtStarWorkList;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.manager.constant.agent.AgentPraise;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.proxy.art.StarProductProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.artist.StarProductService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小星星商品作品
 *
 * @author ZHAO
 */
@Service
public class StarProductServiceImpl implements StarProductService{
    @Autowired
    private StarProductProxy starProductProxy;
    
    @Autowired
    private UserProxy userProxy;
    
    @Autowired
    private ArtCommentDao artCommentDao;

    @Override
    public ArtStarWorkList getArtStarWorksList(StarUserLoginRequest request) {
        Map<String, Object> paramMap = new HashMap<>();
        if(null != request){
			HttpUserInfoRequest userVo = request.getUserInfo();
            if(null != userVo){
                paramMap.put("userId", userVo.getId());
            }
        }
        return starProductProxy.getArtStarWorksList(paramMap);
    }

    @Override
    public ArtStarWork getArtStarWorkDetail(StarArtRequest request) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("artId", request.getArtId());
        return starProductProxy.getArtStarWorkDetail(paramMap);
    }

    @Override
    public List<ArtStarGood> getArtStarGoodList(StarArtRequest request) {
        Map<String, Object> paramMap = new HashMap<>();
        return starProductProxy.getArtStarGoodList(paramMap);
    }

    @Override
    public ArtStarGood getArtStarGoodDetail(StarArtRequest request) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("artId", request.getArtId());
        ArtStarGood artStarGood = starProductProxy.getArtStarGoodDetail(paramMap);
        if(artStarGood != null){
        	artStarGood.setProductId(artStarGood.getArtId());
        }
        return artStarGood;
    }

	@Override
	public List<ArtCustomRecordResponse> getCustomRecordList(StarArtRequest request) {
		if(null == request){
			return null;
		}
		//查询此作品涉及到的订单
		List<ArtCustomRecordResponse> recordList = starProductProxy.getOrderListByArtId(request.getArtId().intValue());
		
		if(CollectionUtils.isNotEmpty(recordList)){
			//查询订单评论
			List<Integer> orderIds = new ArrayList<Integer>();
			recordList.forEach(e -> {
				orderIds.add(e.getOrderId());
				//时间格式为  2018-10-18
				e.setCreateTimeStr(DateUtils.parseStrDateTimeToString(e.getCreateTimeStr(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
				if(StringUtils.isBlank(e.getNickName())){
					e.setNickName(AgentPraise.ART_STAR_DEFAULT_NICKNAME);
				}
				if(StringUtils.isBlank(e.getImgUrl())){
					e.setImgUrl(StaticResourceConstants.ART_STAR_DEFAULT_UIMG);
				}
			});
			List<ArtCommentDto> orderCommentList = artCommentDao.getCommentListByOrderIdList(orderIds);
			if(CollectionUtils.isNotEmpty(orderCommentList)){
				Map<Integer, ArtCommentDto> orderCommentMap = new HashMap<Integer, ArtCommentDto>();
				orderCommentList.forEach(e -> {
					if(!orderCommentMap.containsKey(e.getOrderId()) && e.getUserStar().compareTo(3) > 0){
						orderCommentMap.put(e.getOrderId(), e);
					}
				});
				
				recordList.forEach(e -> {
					ArtCommentDto artCommentDto = orderCommentMap.get(e.getOrderId());
					if(artCommentDto != null){
						e.setComment(artCommentDto.getUserComment());
						e.setStarNum(artCommentDto.getUserStar());
					}
				});
			}
		}
		
		return recordList;
	}
    
}
