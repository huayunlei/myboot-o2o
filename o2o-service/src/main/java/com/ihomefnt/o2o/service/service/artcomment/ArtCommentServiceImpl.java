/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月9日
 * Description:ArtCommentServiceImpl.java 
 */
package com.ihomefnt.o2o.service.service.artcomment;

import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.o2o.intf.dao.artcomment.ArtCommentDao;
import com.ihomefnt.o2o.intf.domain.art.dto.Artwork;
import com.ihomefnt.o2o.intf.domain.artcomment.dto.ArtCommentDto;
import com.ihomefnt.o2o.intf.domain.artcomment.dto.ArtCommentPage;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.request.ArtCommentListRequest;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.request.ArtCommentViewRequest;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.response.ArtCommentImage;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.response.ArtCommentPageListResponse;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.response.ArtCommentResponse;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicListDto;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstant;
import com.ihomefnt.o2o.intf.domain.order.dto.TOrder;
import com.ihomefnt.o2o.intf.manager.constant.artcomment.ArtCommentConstant;
import com.ihomefnt.o2o.intf.proxy.art.ArtProxy;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.service.artcomment.ArtCommentService;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import com.ihomefnt.o2o.service.service.common.CommentAdminService;
import com.ihomefnt.o2o.intf.domain.homecard.dto.CommentReply;
import com.ihomefnt.o2o.intf.manager.constant.home.HomeCardPraise;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardWcmProxy;
import com.ihomefnt.o2o.intf.domain.homecard.dto.CommentReplyListResponseVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.CommentResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.ShareOrder;
import com.ihomefnt.o2o.intf.proxy.shareorder.ShareOrderProxy;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhang
 */
@Service
public class ArtCommentServiceImpl implements ArtCommentService {

	@Autowired
	private ArtCommentDao dao;

	@Autowired
	private ShareOrderProxy shareDao;
	
	@Autowired
	private HomeCardWcmProxy homeCardWcmProxy;

	@Autowired 
	private ArtProxy artDao;

	@Autowired
	OrderService orderService;
	
	@Autowired
	private CommentAdminService commentAdminService;
	
	@Autowired
	private DicProxy dicProxy;
	
	private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy年MM月dd日");

	@Override
	public String createArtComment(ArtCommentDto dto) {
		if (dto == null) {
			return null;
		}
		Integer artType = null;
		if (OrderConstant.ORDER_TYPE_STAR_ART == dto.getOrderType()) {
			dto.setProductType(OrderConstant.PRODUCT_TYPE_STAR_ART);
			dto.setOrderType(OrderConstant.ORDER_TYPE_STAR_ART);
			artType = 3;
		} else {
			dto.setProductType(OrderConstant.PRODUCT_TYPE_ART);
			dto.setOrderType(OrderConstant.ORDER_TYPE_ART);
		}
		//商品名称  获取艺术品信息
		Artwork artwork = artDao.getArtworkByIdAndType(dto.getProductId().longValue(), artType);
		if(artwork != null){
			dto.setProductName(artwork.getName());
		}
		if(dto.getPid() == null){
			dto.setPid("");
		}
		String id = dao.createArtComment(dto);
		Boolean toShareOrderTag = dto.getToShareOrderTag();
		if (toShareOrderTag != null && toShareOrderTag.booleanValue() == true) {
			ShareOrder shareOrder = new ShareOrder();
			shareOrder.setTextContent(dto.getUserComment());
			shareOrder.setImgContent(dto.getImages());
			if (dto.getUserId() != null) {
				shareOrder.setUserId(dto.getUserId());
			}
			shareOrder.setUserImgUrl(dto.getNickImg());
			shareOrder.setUserNickName(dto.getNickName());
			shareOrder.setPhoneNum(dto.getMobileNum());
			shareOrder.setCreateTime(System.currentTimeMillis());
			shareDao.insertShareOrder(shareOrder);
		}
		return id;
	}

	@Override
	public ArtCommentResponse viewArtCommentByPK(ArtCommentViewRequest request) {
		
		if (request == null) {
			return null;
		}
		Integer width = request.getWidth();
		ArtCommentDto dto = dao.viewArtCommentByPK(request);
		if (dto == null) {
			return null;
		}
		ArtCommentResponse response = ModelMapperUtil.strictMap(dto, ArtCommentResponse.class);
		this.setVoByDto(response, dto, width);
		return response;
	}

	/**
	 * 通过dto来设置vo对象
	 * 
	 * @param response
	 * @param dto
	 */
	private void setVoByDto(ArtCommentResponse response, ArtCommentDto dto, Integer width) {
		List<String> images = dto.getImages();
		List<ArtCommentImage> imageList = new ArrayList<ArtCommentImage>();
		if (CollectionUtils.isNotEmpty(images)) {
			for (String image : images) {
				if (StringUtils.isNotBlank(image)) {
					ArtCommentImage img = new ArtCommentImage();
					if (width == null) {
						img.setBigImage(image);
						img.setSmallImage(image);
					} else {
						img.setBigImage(image + "?imageView2/4/w/" + width);
						Integer smallWidth = width * 33 / 100;
						img.setSmallImage(image + "?imageView2/1/w/" + smallWidth);
					}
					imageList.add(img);
				}
			}
		}
		response.setImages(imageList);
		String createTime = dto.getCreateTime();
		try {
			if (createTime != null) {
				response.setCreateTimeStr(dayFormat.format(DateUtils.parseDate(createTime,"yyyy-MM-dd HH:mm:ss")));
			} else {
				response.setCreateTimeStr("");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		String nickName = response.getNickName();
		if(StringUtils.isEmpty(nickName)){
			String mobileNum =dto.getMobileNum();
			if(StringUtils.isNotBlank(mobileNum)){
				response.setNickName(StringUtil.getPhoneNumberForShow(mobileNum));
			}else{
				response.setNickName(ArtCommentConstant.DEFAULT_NICK_NAME);
			}			
		}
		
		//根据评论ID查询评论回复  
		CommentReplyListResponseVo replyListResponseVo = homeCardWcmProxy.queryReplyCommentListByPid(dto.getCommentId(), HomeCardPraise.COMMENT_TYPE_ART);
		if(replyListResponseVo != null){
			List<CommentResponseVo> replyList = replyListResponseVo.getReplyCommentList();
			if(CollectionUtils.isNotEmpty(replyList)){
				//官方回复名称
				String officialName = "";
                DicListDto dicListResponseVo = dicProxy.getDicListByKey(HomeCardPraise.COMMENT_ADMIN);
                if(dicListResponseVo != null){
                	List<DicDto> dicList = dicListResponseVo.getDicList();
                	if(!CollectionUtils.isEmpty(dicList)){
                		officialName = dicList.get(0).getValueDesc();
                	}
                }
                
				List<CommentReply> commentReplyList = new ArrayList<CommentReply>();
				for (CommentResponseVo commentResponseVo2 : replyList) {
					CommentReply commentReply = new CommentReply();
					commentReply.setCommentId(commentResponseVo2.getId());
					commentReply.setContent(commentResponseVo2.getContent());
					if(commentResponseVo2.getCreateTime() != null){
						commentReply.setCreateTime(commentResponseVo2.getCreateTime());
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");	
						commentReply.setCreateTimeDesc(dateFormat.format(commentResponseVo2.getCreateTime()));
					}
					//官方回复标志
					if(commentAdminService.judgeCommentAdminMobile(commentResponseVo2.getMobile())){
						commentReply.setOfficialFlag(HomeCardPraise.COMMENT_REPLY_OFFICIAL_FRONT);
						commentReply.setUserNickName(officialName);
					}else{
						commentReply.setOfficialFlag(HomeCardPraise.COMMENT_REPLY_ORTHER);
						if(StringUtils.isNotBlank(commentResponseVo2.getNickName())){
							commentReply.setUserNickName(commentResponseVo2.getNickName());
						}else{
							if(StringUtils.isNotBlank(commentResponseVo2.getMobile())){
								commentReply.setUserNickName(commentResponseVo2.getMobile().replaceAll(ProductProgramPraise.MOBILE_REGEX,ProductProgramPraise.MOBILE_REPLACE));
							}else{
								commentReply.setUserNickName(HomeCardPraise.ANONYMOUS_USER);
							}
						}
					}
					commentReply.setStarNum(commentResponseVo2.getStarNum());
					if(StringUtils.isNotBlank(commentResponseVo2.getHeadImg())){
						Integer imgWidth = 0;
						if(width != null){
							imgWidth = width * ImageSize.WIDTH_PER_SIZE_20
									/ ImageSize.WIDTH_PER_SIZE_100; 
						}
						commentReply.setUserHeadImgUrl(QiniuImageUtils.compressImageAndSamePic(commentResponseVo2.getHeadImg(), imgWidth, -1));
					}else{
						commentReply.setUserHeadImgUrl("");
					}
					if(StringUtils.isNotBlank(commentResponseVo2.getReplyNickName())){
						commentReply.setReplyNickName(commentResponseVo2.getReplyNickName());
					}else{
						commentReply.setReplyNickName(HomeCardPraise.ANONYMOUS_USER);
					}
					commentReply.setReplyUserId(commentResponseVo2.getReplyUserId());
					commentReplyList.add(commentReply);
				}
				response.setCommentReplyList(commentReplyList);
			}
		}
	}

	@Override
	public ArtCommentPageListResponse listArtCommentByCondition(ArtCommentListRequest request) {
		Integer productId = request.getProductId();
		if (productId == null) {
			return null;
		}
		ArtCommentPage page = dao.listArtCommentByCondition(request);
		ArtCommentPageListResponse response = ModelMapperUtil.strictMap(page, ArtCommentPageListResponse.class);
		List<ArtCommentDto> pageList = page.getCommentList();
		List<ArtCommentResponse> commentList = response.getCommentList();
		Integer width = request.getWidth();
		if (CollectionUtils.isNotEmpty(pageList)) {
			for (int i = 0; i < pageList.size(); i++) {
				ArtCommentResponse vo = commentList.get(i);
				ArtCommentDto dto = pageList.get(i);
				this.setVoByDto(vo, dto, width);
			}
		}

		return response;
	}

	@Override
	public Integer getCommentCountByOrderId(Integer orderId) {
		if (orderId == null) {
			return 0;
		}
		return dao.getCommentCountByOrderId(orderId);
	}

	@Override
	public List<ArtCommentDto> getCommentListByOrderIdAndProductIdList(Integer orderId, List<Integer> productIdList) {
		return dao.getCommentListByOrderIdAndProductIdList(orderId, productIdList);
	}

	@Override
	public List<ArtCommentDto> getCommentListByOrderIdList(List<Integer> orderIdList) {
		return dao.getCommentListByOrderIdList(orderIdList);
	}

	@Override
	public String brushArtData() {
		int error = 0;
		int success = 0;
		// 查询所有艺术品评论
		List<ArtCommentDto> artcommentList = dao.queryAllCommentList(0);
		for (ArtCommentDto artCommentDto : artcommentList) {
			String orderNum = "";
			String productName = "";
			//订单编号
			if(artCommentDto.getOrderId() != null){
				TOrder order = orderService.queryOrderByOrderId(artCommentDto.getOrderId().longValue());
				if(order != null){
					orderNum = order.getOrderNum();
				}
			}
			//商品名称
			if(artCommentDto.getProductId() != null){
				Artwork artWork = artDao.getArtworkById(artCommentDto.getProductId().longValue());
				if(artWork != null){
					productName = artWork.getName();
				}
			}
			artCommentDto.setOrderNum(orderNum);
			artCommentDto.setProductName(productName);
			artCommentDto.setDeleteFlag(0);
			artCommentDto.setReplyUserId(0);
			artCommentDto.setReplyNickName("");
			artCommentDto.setReplyNum(0);
			artCommentDto.setPid("");
			if(dao.updateCommentById(artCommentDto)){
				success++;
			}else{
				error++;
			}
		}
		return "更新成功"+success+"条，失败"+error+"条！";
	}


	@Override
	public void synCommentDataListFromMongoDBToRedis() {
		List<ArtCommentDto> list = dao.queryAllCommentList(1); // MongoDB
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		List<Integer> orderIdlist = new ArrayList<Integer>(); // 封装所有订单
		Map<Integer, Integer> productMap = new HashMap<Integer, Integer>();// 封装所有的商品评价数量
		Map<Integer, Integer> starMap = new HashMap<Integer, Integer>();// 封装所有的商品打分
		for (ArtCommentDto dto : list) {
			Integer productId = dto.getProductId();// 商品Id
			if (productId != null) {
				if (!productMap.containsKey(productId)) {
					productMap.put(productId, 1);
				} else {
					Integer value = productMap.get(productId) + 1;
					productMap.put(productId, value);
				}
				Integer userStar = dto.getUserStar();// 用户打分
				if (userStar != null) {
					if (!starMap.containsKey(productId)) {
						starMap.put(productId, userStar*20);
					} else {
						Integer value = starMap.get(productId) + userStar*20;
						starMap.put(productId, value);
					}
				}
			}

			Integer orderId = dto.getOrderId();// 订单Id
			if (orderId != null) {
				orderIdlist.add(orderId);
			}

		}
		// 将mongodb数据刷新到redis里面
		if (CollectionUtils.isNotEmpty(orderIdlist)) {
			for (Integer orderId : orderIdlist) {
				String value = AppRedisUtil.get(ArtCommentConstant.REDIS_ART_ORDER_PREFIX + orderId);
				if (StringUtils.isBlank(value)) {
					AppRedisUtil.incrBy(ArtCommentConstant.REDIS_ART_ORDER_PREFIX + orderId, 1);
				}
			}
		}

		if (!productMap.isEmpty()) {
			for (Map.Entry<Integer, Integer> entry : productMap.entrySet()) {
				Integer key = entry.getKey();
				Integer value = entry.getValue();
				AppRedisUtil.del(ArtCommentConstant.REDIS_ART_PRODUCT_PEOPLE_PREFIX + key);
				AppRedisUtil.incrBy(ArtCommentConstant.REDIS_ART_PRODUCT_PEOPLE_PREFIX + key, value);
			}
		}

		if (!starMap.isEmpty()) {
			for (Map.Entry<Integer, Integer> entry : starMap.entrySet()) {
				Integer key = entry.getKey();
				Integer value = entry.getValue();
				AppRedisUtil.del(ArtCommentConstant.REDIS_ART_PRODUCT_SCORE_PREFIX + key);
				AppRedisUtil.incrBy(ArtCommentConstant.REDIS_ART_PRODUCT_SCORE_PREFIX + key, value);
			}
		}
	}

	/* 根据订单id、商品id、订单类型查询商品评论列表*/
	@Override
	public List<ArtCommentDto> getCommentListByOrderIdOrderTypeProdId(Integer orderId, Integer productId,
			Integer orderType) {
		return dao.getCommentListByOrderIdOrderTypeProdId(orderId, productId, orderType);
	}

}
