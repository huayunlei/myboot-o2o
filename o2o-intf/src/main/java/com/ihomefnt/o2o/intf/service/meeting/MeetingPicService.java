package com.ihomefnt.o2o.intf.service.meeting;

import com.ihomefnt.o2o.intf.domain.meeting.vo.request.HouseManagerRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.PublishPicRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.SearchPicWallRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.UploadImageRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.FamilyInfoListResponseVo;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.HouseManagerResponse;
import com.ihomefnt.oms.trade.util.PageModel;

/**
 * 照片墙
 * @author ZHAO
 */
public interface MeetingPicService {
	/**
	 * 根据家庭ID查询艾管家信息
	 * @param request
	 * @return
	 */
	HouseManagerResponse searchHouseManager(HouseManagerRequest request);
	
	/**
	 * 发布照片墙
	 * @param request
	 * @return
	 */
	Boolean publishPic(PublishPicRequest request);

	/**
	 * 查询照片墙
	 * @param request
	 * @return
	 */
	PageModel searchPicWall(SearchPicWallRequest request);
	
	/**
	 * 查询所有家庭用户组
	 * @return
	 */
	FamilyInfoListResponseVo searchFamilyGroup();
	
	/**
	 * 查询本地攻略
	 * @return
	 */
	String getLocalStrategy();
	
	/**
	 * 上传图片
	 * @param request
	 * @return
	 */
	String uploadImageBase64(UploadImageRequest request);

	/**
	 * 上传图片
	 * @param request
	 * @return
	 */
	String uploadImage(UploadImageRequest request);
}
