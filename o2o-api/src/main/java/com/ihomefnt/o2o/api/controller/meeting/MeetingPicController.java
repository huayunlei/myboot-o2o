package com.ihomefnt.o2o.api.controller.meeting;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.HouseManagerRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.PublishPicRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.SearchPicWallRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.UploadImageRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.FamilyInfoListResponseVo;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.HouseManagerResponse;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.LocalStrategyResponseVo;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.UploadImageResponseVo;
import com.ihomefnt.o2o.intf.service.meeting.MeetingPicService;
import com.ihomefnt.oms.trade.util.PageModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 【2017跨年】艾管家和照片墙
 *
 * @author ZHAO
 */
@ApiIgnore
@Deprecated
@Api(tags = "【2017跨年相关】", hidden = true)
@RequestMapping("/meetingPic")
@RestController
public class MeetingPicController {
    @Autowired
    private MeetingPicService meetingPicService;

    @ApiOperation(value = "根据家庭ID查询艾管家信息", notes = "根据家庭ID查询艾管家信息")
    @RequestMapping(value = "/searchHouseManager", method = RequestMethod.POST)
    public HttpBaseResponse<HouseManagerResponse> searchHouseManager(@RequestBody HouseManagerRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        HouseManagerResponse managerResponse = meetingPicService.searchHouseManager(request);
        return HttpBaseResponse.success(managerResponse);
    }

    @ApiOperation(value = "发布照片墙", notes = "发布照片墙")
    @RequestMapping(value = "/publishPic", method = RequestMethod.POST)
    public HttpBaseResponse<Boolean> publishPic(@RequestBody PublishPicRequest request) {
        if (request == null || request.getMemberId() == null || StringUtils.isBlank(request.getPicUrls())) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        Boolean flag = meetingPicService.publishPic(request);
        return HttpBaseResponse.success(flag);
    }

    @ApiOperation(value = "查询照片墙", notes = "查询照片墙")
    @RequestMapping(value = "/searchPicWall", method = RequestMethod.POST)
    public HttpBaseResponse<PageModel> searchPicWall(@RequestBody SearchPicWallRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        PageModel pageModel = meetingPicService.searchPicWall(request);
        return HttpBaseResponse.success(pageModel);
    }

    @ApiOperation(value = "查询所有家庭用户组", notes = "查询所有家庭用户组")
    @RequestMapping(value = "/searchFamilyGroup", method = RequestMethod.POST)
    public HttpBaseResponse<FamilyInfoListResponseVo> searchFamilyGroup() {
        FamilyInfoListResponseVo obj = meetingPicService.searchFamilyGroup();
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "查询本地攻略", notes = "查询本地攻略")
    @RequestMapping(value = "/getLocalStrategy", method = RequestMethod.POST)
    public HttpBaseResponse<LocalStrategyResponseVo> getLocalStrategy() {
        String result = meetingPicService.getLocalStrategy();
        return HttpBaseResponse.success(new LocalStrategyResponseVo(result));
    }

    @ApiOperation(value = "上传照片base64", notes = "上传照片")
    @RequestMapping(value = "/uploadImageBase64", method = RequestMethod.POST)
    public HttpBaseResponse<UploadImageResponseVo> uploadImageBase64(@RequestBody UploadImageRequest request) {
        if (request == null || StringUtils.isBlank(request.getImageBase64())) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        String imageUrl = meetingPicService.uploadImageBase64(request);
        return HttpBaseResponse.success(new UploadImageResponseVo(imageUrl));
    }

    @ApiOperation(value = "上传照片", notes = "上传照片")
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public HttpBaseResponse<UploadImageResponseVo> uploadImage(@RequestBody UploadImageRequest request) {
        if (request == null || StringUtils.isBlank(request.getImageBase64())) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        String imageUrl = meetingPicService.uploadImage(request);
        return HttpBaseResponse.success(new UploadImageResponseVo(imageUrl));
    }

}
