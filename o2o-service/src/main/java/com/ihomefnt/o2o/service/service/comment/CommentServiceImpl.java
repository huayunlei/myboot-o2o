package com.ihomefnt.o2o.service.service.comment;

import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.o2o.intf.dao.comment.CommentDao;
import com.ihomefnt.o2o.intf.domain.comment.dto.CheckCommentDto;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentLabelsDto;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentsAddDto;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentsDto;
import com.ihomefnt.o2o.intf.domain.comment.dto.UserCommentDto;
import com.ihomefnt.o2o.intf.domain.comment.vo.request.CommentsAddRequestVo;
import com.ihomefnt.o2o.intf.domain.comment.vo.request.UserCommentRequestVo;
import com.ihomefnt.o2o.intf.domain.comment.vo.response.UserCommendResponseVo;
import com.ihomefnt.o2o.intf.proxy.comment.CommentProxy;
import com.ihomefnt.o2o.intf.service.comment.CommentService;
import com.ihomefnt.o2o.service.manager.config.ImageConfig;
import com.ihomefnt.o2o.service.manager.config.ImageProperty;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务点评
 *
 * @author jiangjun
 * @version 2.0, 2018-04-13 上午11:07
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentProxy commentProxy;
    @Autowired
	CommentDao commentDao;
	@Autowired
    ImageConfig imageConfig;

    @Override
    public List<CommentLabelsDto> getLabels(Integer type){
    	return commentProxy.getLabels(type);
    }

    @Override
    public CommentsDto getComment(Integer orderId){
    	return commentProxy.getComment(orderId);
    }

    @Override
    public String addComment(CommentsAddRequestVo comments){
    	CommentsAddDto params = ModelMapperUtil.strictMap(comments, CommentsAddDto.class);
    	return commentProxy.addComment(params);
    }

    @Override
    public boolean isCanComment(Integer orderId){
        CheckCommentDto checkComment = commentProxy.checkCommentByOrderId(orderId);
        return checkComment == null?false:checkComment.isCanComment();
    }

    @Override
    public boolean isAlreadyComment(Integer orderId){
        CheckCommentDto checkComment = commentProxy.checkCommentByOrderId(orderId);
        return checkComment == null?false:checkComment.isCommentType();
    }
    
    @Override
	public UserCommendResponseVo queryUserCommentList(UserCommentRequestVo request) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 10;
        int pageNo = request.getPageNo() > 0 ? request.getPageNo() : 1;
        params.put("size", pageSize);
        params.put("from", (pageNo - 1) * pageSize);
		params.put("productId", request.getProductId());
		params.put("type", request.getType());
		
		List<UserCommentDto> userCommentList = commentDao.queryUserCommentList(params);
		
		for (UserCommentDto userComment : userCommentList) {
            List<String> imgList = ImageUtil.removeEmptyStr(userComment.getImages());
            List<String> strResponseList = new ArrayList<String>();
            if (null != imgList && imgList.size() > 0) {
                for (String str : imgList) {
                    if (null != str && !"".equals(str)) {
                        str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
                        strResponseList.add(str);
                    }
                }
            }
            userComment.setImageList(strResponseList);
            userComment.setImages(null);
        }
		
		int totalRecords = commentDao.queryUserCommentCount(params);
		
		UserCommendResponseVo userCommendResponse = new UserCommendResponseVo();
		userCommendResponse.setTotalRecords(totalRecords);
        int totalPages = (int) ((totalRecords + pageSize - 1) / pageSize);
        userCommendResponse.setTotalPages(totalPages);
		userCommendResponse.setUserCommentList(userCommentList);
		
		return userCommendResponse;
	}
    
    public String appendImageMethod(int mode) {
        String methodUrl = "?imageView2/1/w/*/h/*";

        ImageProperty imageProperty = imageConfig.getImageConfigMap().get(mode);
        methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(imageProperty.getWidth()));
        methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(imageProperty.getHeight()));
        return methodUrl;
    }

}
