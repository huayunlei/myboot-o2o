package com.ihomefnt.o2o.service.service.meeting;

import com.ihomefnt.o2o.intf.domain.meeting.dto.*;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.service.meeting.MeetingPicService;
import com.ihomefnt.o2o.intf.proxy.meeting.MeetingPicProxy;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.HouseManagerRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.PublishPicRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.SearchPicWallRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.UploadImageRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.FamilyInfoListResponseVo;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.FamilyInfoResponse;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.HouseManagerResponse;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.PicWallInfoResponse;
import com.ihomefnt.o2o.intf.proxy.program.KeywordWcmProxy;
import com.ihomefnt.o2o.intf.domain.program.dto.KeywordListResponseVo;
import com.ihomefnt.o2o.intf.domain.art.dto.KeywordVo;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.oms.trade.util.PageModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 照片墙
 * @author ZHAO
 */
@Service
public class MeetingPicServiceImpl implements MeetingPicService{

	@Autowired
	private MeetingPicProxy meetingPicProxy;
	
	@Autowired
	private KeywordWcmProxy keywordWcmProxy;
	
	@Override
	public HouseManagerResponse searchHouseManager(HouseManagerRequest request) {
		HouseManagerResponse response = new HouseManagerResponse();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("familyId", request.getFamilyId());
		HouseManagerDto houseManagerDto = meetingPicProxy.searchHouseManager(params);
		if(houseManagerDto != null && houseManagerDto.getManagerId() != null && houseManagerDto.getManagerId() > 0){
			response.setManagerId(houseManagerDto.getManagerId());
			if(StringUtils.isNotBlank(houseManagerDto.getDescription())){
				response.setDescription(houseManagerDto.getDescription());
			}
			if(StringUtils.isNotBlank(houseManagerDto.getManagerName())){
				response.setManagerName(houseManagerDto.getManagerName());
			}
			if(StringUtils.isNotBlank(houseManagerDto.getNickName())){
				response.setNickName(houseManagerDto.getNickName());
			}
			if(StringUtils.isNotBlank(houseManagerDto.getPhone())){
				response.setPhone(houseManagerDto.getPhone());
			}
			if(StringUtils.isNotBlank(houseManagerDto.getUrl())){
				response.setUrl(QiniuImageUtils.compressImageAndSamePicTwo(houseManagerDto.getUrl(), 61, 61));//切图处理
			}
		}
		
		return response;
	}

	@Override
	public Boolean publishPic(PublishPicRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("familyId", request.getFamilyId());
		params.put("memberId", request.getMemberId());
		params.put("urls", request.getPicUrls());
		boolean flag = meetingPicProxy.publishPic(params);
		if (!flag) {
			throw new BusinessException("上传失败了，再试试吧～");
		}
		return true;
	}

	@Override
	public PageModel searchPicWall(SearchPicWallRequest request) {
		SimpleDateFormat dayFormat_DAY = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PageModel pageModel = new PageModel();
		List<PicWallInfoResponse> picWallInfoResponses = new ArrayList<PicWallInfoResponse>();
		
		int pageNo = 1;
		int pageSize = 10;
		int width = 750;
		if(request.getWidth() != null && request.getWidth() > 0){
			width = request.getWidth();
		}
		
		if(request.getPageNo() != null && request.getPageNo() > 0){
			pageNo = request.getPageNo();
		}
		if(request.getPageSize() != null && request.getPageSize() > 0){
			pageSize = request.getPageSize();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(request.getFamilyId() != null && request.getFamilyId() > 0){
			params.put("familyId", request.getFamilyId());
		}
		
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		PicWallListDto picWallListDto = meetingPicProxy.searchPicWall(params);
		if(picWallListDto != null){
			List<PicWallInfoDto> picWallInfoDtos = picWallListDto.getList();
			for (PicWallInfoDto picWallInfoDto : picWallInfoDtos) {
				if(StringUtils.isNotBlank(picWallInfoDto.getUrls())){
					PicWallInfoResponse infoResponse = new PicWallInfoResponse();
					infoResponse.setFamilyId(picWallInfoDto.getFamilyId());
					infoResponse.setMemberId(picWallInfoDto.getMemberId());
					infoResponse.setPicId(picWallInfoDto.getPicId());
					if(StringUtils.isNotBlank(picWallInfoDto.getProjectName())){
						infoResponse.setProjectName(picWallInfoDto.getProjectName());
					}
					if(picWallInfoDto.getPublishTime() != null){
						infoResponse.setPublishTime(dayFormat_DAY.format(picWallInfoDto.getPublishTime()));
					}
					infoResponse.setBigImage(QiniuImageUtils.compressImageAndSamePicTwo(picWallInfoDto.getUrls(), width, -1));//大图
					infoResponse.setSmallImage(QiniuImageUtils.compressImageAndDiffPic(picWallInfoDto.getUrls(), 240, 240));//小图
					if(StringUtils.isNotBlank(picWallInfoDto.getMemberName())){
						infoResponse.setMemberName(picWallInfoDto.getMemberName());
					}
					picWallInfoResponses.add(infoResponse);
				}
			}
			
			pageModel.setTotalPages(picWallListDto.getTotalPage());
			pageModel.setTotalRecords(picWallListDto.getTotalCount());
		}else{
			pageModel.setTotalPages(1);
			pageModel.setTotalRecords(0);
		}

		pageModel.setList(picWallInfoResponses);
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);
		
		return pageModel;
	}

	@Override
	public FamilyInfoListResponseVo searchFamilyGroup() {
		List<FamilyInfoResponse> familyInfoResponses = new ArrayList<FamilyInfoResponse>();
		
		FamilyGroupDto familyGroupDto = meetingPicProxy.searchFamilyGroup();
		if(familyGroupDto != null && CollectionUtils.isNotEmpty(familyGroupDto.getFamilyGroup())){
			List<FamilyInfoDto> familyGroup = familyGroupDto.getFamilyGroup();
			for (FamilyInfoDto familyInfoDto : familyGroup) {
				FamilyInfoResponse infoResponse = new FamilyInfoResponse();
				infoResponse.setFamilyId(familyInfoDto.getFamilyId());
				infoResponse.setManagerId(familyInfoDto.getManagerId());
				if(StringUtils.isNotBlank(familyInfoDto.getCompany())){
					infoResponse.setCompany(familyInfoDto.getCompany());
				}
				if(StringUtils.isNotBlank(familyInfoDto.getFamilyName())){
					infoResponse.setFamilyName(familyInfoDto.getFamilyName());
				}
				if(StringUtils.isNotBlank(familyInfoDto.getOrderNum())){
					infoResponse.setOrderNum(familyInfoDto.getOrderNum());
				}
				if(StringUtils.isNotBlank(familyInfoDto.getProject())){
					infoResponse.setProject(familyInfoDto.getProject());
				}
				if(StringUtils.isNotBlank(familyInfoDto.getPhone())){
					infoResponse.setPhone(familyInfoDto.getPhone());
				}
				if(StringUtils.isNotBlank(familyInfoDto.getFamilyUrl())){
					infoResponse.setFamilyUrl(QiniuImageUtils.compressImageAndSamePicTwo(familyInfoDto.getFamilyUrl(), 204, 204));
				}
				familyInfoResponses.add(infoResponse);
			}
		}
		
		return new FamilyInfoListResponseVo(familyInfoResponses);
	}

	@Override
	public String getLocalStrategy() {
		String localStrategy = "";
		List<String> params = new ArrayList<String>();
		params.add("本地攻略");
		KeywordListResponseVo listResponseVo = keywordWcmProxy.getKeywordList(params);
		if(listResponseVo != null && CollectionUtils.isNotEmpty(listResponseVo.getKeywordList())){
			for (KeywordVo keyWord : listResponseVo.getKeywordList()) {
				for (String word : keyWord.getWords()) {
					localStrategy = localStrategy + "\n" + word;
				}
			}
		}
		
		return localStrategy;
	}

	@Override
	public String uploadImage(UploadImageRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bucketName", "aijia-adv");
		params.put("imageUrl", request.getImageBase64());
		String imageUrl = meetingPicProxy.uploadImage(params);
		if(StringUtils.isNotBlank(imageUrl)){
			return imageUrl;
		}
		return "";
	}

	@Override
	public String uploadImageBase64(UploadImageRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bucketName", "");
		params.put("imageUrl", request.getImageBase64());
		String imageUrl = meetingPicProxy.uploadImageBase64(params);
		if(StringUtils.isNotBlank(imageUrl)){
			return imageUrl;
		}
		return "";
	}

}
