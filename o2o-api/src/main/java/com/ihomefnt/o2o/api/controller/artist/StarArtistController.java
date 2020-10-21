package com.ihomefnt.o2o.api.controller.artist;

import com.ihomefnt.cms.utils.ModelMapperUtil;
import com.ihomefnt.o2o.intf.domain.art.dto.StarArtistDto;
import com.ihomefnt.o2o.intf.domain.art.dto.StarArtistListDto;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarArtistByNameRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarArtistListRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarArtistRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarUserRegisterRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtistInfoResponse;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.StarUserLoginResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.weixin.vo.request.GetPhoneNumberRequest;
import com.ihomefnt.o2o.intf.manager.util.common.wechat.Weixinutil;
import com.ihomefnt.o2o.intf.service.artist.ArtistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * @author huayunlei
 * @created 2018年10月10日 上午9:29:25
 * @desc 小星星艺术家
 */
@ApiIgnore
@RestController
@Api(tags = "【微信小程序】小星星艺术家API",hidden = true)
@RequestMapping("/starArtist")
public class StarArtistController {
	@Autowired
	private ArtistService artistService;
	
	/**
     * 小星星艺术家列表
     */
	@ApiOperation(value = "小星星艺术家列表查询", notes = "小星星艺术家列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public HttpBaseResponse<StarArtistListDto> list(@RequestBody StarArtistListRequest request) {
		if(null == request || StringUtils.isBlank(request.getAccessToken())){
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		StarArtistListDto baseResponse = artistService.getStarArtistList(request);
		return HttpBaseResponse.success(baseResponse);
    }
    
    /**
     * 根据姓名查询小星星艺术家
     */
	@ApiOperation(value = "根据姓名查询小星星艺术家", notes = "根据姓名查询小星星艺术家")
    @RequestMapping(value = "/getStarArtistByName", method = RequestMethod.POST)
    public HttpBaseResponse<List<StarArtistDto>> getStarArtistByName(@RequestBody StarArtistByNameRequest request) {
		if(null == request || StringUtils.isBlank(request.getAccessToken())){
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		List<StarArtistDto> baseResponse = artistService.getStarArtistByName(request);
		return HttpBaseResponse.success(baseResponse);
    }

    /**
     * 根据用户ID查询小星星艺术家
     */
	@ApiOperation(value = "根据用户ID查询小星星艺术家", notes = "根据用户ID查询小星星艺术家")
    @RequestMapping(value = "/getStarArtistById", method = RequestMethod.POST)
    public HttpBaseResponse<ArtistInfoResponse> getStarArtistById(@RequestBody StarArtistRequest request) {
		ArtistInfoResponse baseResponse = artistService.getStarArtistById(request);
		return HttpBaseResponse.success(baseResponse);
    }
	
	/**
     * 小星星用户微信小程序注册
     */
	@ApiOperation(value = "小星星用户微信小程序注册登录", notes = "小星星用户微信小程序注册登录")
    @RequestMapping(value = "/registerByWeChatApplet", method = RequestMethod.POST)
    public HttpBaseResponse<StarUserLoginResponse> registerByWeChatApplet(@RequestBody StarUserRegisterRequest request) {
        if (null == request || StringUtils.isBlank(request.getEncryptedData()) ||
				StringUtils.isBlank(request.getIv()) || StringUtils.isBlank(request.getLoginSessionKey())) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		Map<String, Object> map = Weixinutil.getPhoneNumber(ModelMapperUtil.strictMap(request, GetPhoneNumberRequest.class), "star");
		if(map == null || map.get("phoneNumber") == null){
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.FAILED);
		}
		request.setMobile(String.valueOf(map.get("phoneNumber")));
		StarUserLoginResponse userData = artistService.registerByWeChatApplet(request);
		return HttpBaseResponse.success(userData);
    }

	@ApiOperation(value = "getSessionKey", notes = "getSessionKey")
	@RequestMapping(value = "/getSessionKey", method = RequestMethod.POST)
	public HttpBaseResponse<Map> getSessionKey(@RequestBody GetPhoneNumberRequest request) {
		if (null == request || StringUtils.isBlank(request.getCode())) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		Map<String, Object> map = Weixinutil.getSessionKey(request, "star");
		return HttpBaseResponse.success(map);
	}
	
}
