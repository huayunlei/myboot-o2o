package com.ihomefnt.o2o.intf.proxy.meeting;

import com.ihomefnt.o2o.intf.domain.meeting.dto.FamilyGroupDto;
import com.ihomefnt.o2o.intf.domain.meeting.dto.HouseManagerDto;
import com.ihomefnt.o2o.intf.domain.meeting.dto.PicWallListDto;

import java.util.Map;

/**
 * 
 * @author ZHAO
 */
public interface MeetingPicProxy {
	/**
	 * 根据家庭ID查询艾管家信息
	 * @param params
	 * @return
	 */
	HouseManagerDto searchHouseManager(Map<String, Object> params);
	
	/**
	 * 发布照片墙
	 * @param params
	 * @return
	 */
	boolean publishPic(Map<String, Object> params);
	
	/**
	 * 查询照片墙
	 * @param params
	 * @return
	 */
	PicWallListDto searchPicWall(Map<String, Object> params);
	
	/**
	 * 查询所有家庭用户组
	 * @return
	 */
	FamilyGroupDto searchFamilyGroup();
	
	/**
	 * 上传图片
	 * @param params
	 * @return
	 */
	String uploadImage(Map<String, Object> params);

	/**
	 * 上传图片
	 * @param params
	 * @return
	 */
	String uploadImageBase64(Map<String, Object> params);
}
