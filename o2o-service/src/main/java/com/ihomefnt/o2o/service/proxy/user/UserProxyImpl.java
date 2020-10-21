package com.ihomefnt.o2o.service.proxy.user;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.art.dto.StarArtistListDto;
import com.ihomefnt.o2o.intf.domain.user.dto.IdCardCertifincationDto;
import com.ihomefnt.o2o.intf.domain.user.dto.MemberDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserRealNameCertifincationDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserIdResultDto;
import com.ihomefnt.o2o.intf.domain.user.vo.response.CertificationResultResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.UserWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarArtistListRequest;
import com.ihomefnt.o2o.intf.domain.user.dto.LoginParamVo;
import com.ihomefnt.o2o.intf.domain.user.dto.LoginResultVo;
import com.ihomefnt.o2o.intf.domain.user.dto.LogoutParamVo;
import com.ihomefnt.o2o.intf.domain.user.dto.MemberAddParamVo;
import com.ihomefnt.o2o.intf.domain.user.dto.MemberUpdateParamVo;
import com.ihomefnt.o2o.intf.domain.user.dto.MobileRegisterParamVo;
import com.ihomefnt.o2o.intf.domain.user.dto.SmsCodeRegisterVo;
import com.ihomefnt.o2o.intf.domain.user.dto.SmsLoginUserParamVo;
import com.ihomefnt.o2o.intf.domain.user.dto.UserTokenParamVo;
import com.ihomefnt.o2o.intf.domain.user.dto.WechatAppletRegisterParamVo;

@Service
public class UserProxyImpl implements UserProxy {
	
	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public String loginByPassword(LoginParamVo loginUser) {
		ResponseVo<LoginResultVo> response = strongSercviceCaller.post(UserWebServiceNameConstants.LOGIN_BY_USER_PSD, loginUser,
                new TypeReference<ResponseVo<LoginResultVo>>() {
                });
		
		if (null != response && response.isSuccess()) {
			LoginResultVo userVo = response.getData();
			if (userVo != null) {
				return userVo.getToken();
			}
		}
		return null;
	}

	@Override
	public UserDto getUserByToken(String token) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("token", token);
		ResponseVo<UserDto> response = strongSercviceCaller.post(UserWebServiceNameConstants.GET_USER_BY_TOKEN, params,
                new TypeReference<ResponseVo<UserDto>>() {
                });
		
		if (null != response && response.isSuccess()) {
			return response.getData();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean checkNickNameExist(String nickName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nickName", nickName);
		ResponseVo response = strongSercviceCaller.post(UserWebServiceNameConstants.CHECK_NICKNAME_EXIST, params,
                ResponseVo.class);
		
		if (null != response) {
			return response.isSuccess();
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Integer addMemberInfo(MemberAddParamVo params) {
		ResponseVo response = strongSercviceCaller.post(UserWebServiceNameConstants.ADD_MEMBER_INFO, params,
                ResponseVo.class);
		
		if (null != response) {
			return response.getCode() == null ? -1 : response.getCode();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Integer updateMemberInfo(MemberUpdateParamVo params) {
		ResponseVo response = strongSercviceCaller.post(UserWebServiceNameConstants.UPDATE_MEMBER, params,
                ResponseVo.class);
		
		if (null != response) {
			return response.getCode() == null ? -1 : response.getCode();
		}
		return null;
	}

	@Override
	public LoginResultVo loginBySmsCode(SmsLoginUserParamVo params) {
		LoginResultVo vo = new LoginResultVo();
		ResponseVo<LoginResultVo> response = strongSercviceCaller.post(UserWebServiceNameConstants.LOGIN_BY_SMS_CODE, params,
				new TypeReference<ResponseVo<LoginResultVo>>() {
        		});
		if (null == response) {
			vo.setCode(-2);
			vo.setMsg("登录接口请求异常");
			vo.setSuccess(false);
		} else {
			vo.setCode(response.getCode());
			vo.setMsg(response.getMsg());
			vo.setSuccess(response.isSuccess());
			if (response.isSuccess()) {
				LoginResultVo userVo = response.getData();
				vo.setToken(userVo.getToken());
			}
		}
		
		return vo;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean logout(LogoutParamVo params) {
		ResponseVo response = strongSercviceCaller.post(UserWebServiceNameConstants.USER_LOGOUT, params,
                ResponseVo.class);
		
		if (null != response) {
			return response.isSuccess();
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean checkLegalUser(String token) {
		UserTokenParamVo params = new UserTokenParamVo();
		params.setToken(token);
		ResponseVo response = strongSercviceCaller.post(UserWebServiceNameConstants.CHECK_LEGAL_USER, params,
                ResponseVo.class);
		
		if (null != response) {
			return response.isSuccess();
		}
		return false;
	}

	@Override
	public UserIdResultDto registerBySmsCode(SmsCodeRegisterVo smsCodeRegisterDto) {
		UserIdResultDto userIdResultVo = new UserIdResultDto();

		ResponseVo<UserIdResultDto> response = strongSercviceCaller.post(UserWebServiceNameConstants.REGISTER_BY_SMS_CODE, smsCodeRegisterDto,
				new TypeReference<ResponseVo<UserIdResultDto>>() {
        		});
		
		if (null == response) {
			userIdResultVo.setCode(-2);
			userIdResultVo.setMsg("注册接口请求异常");
			userIdResultVo.setSuccess(false);
		} else {
			userIdResultVo.setCode(response.getCode());
			userIdResultVo.setMsg(response.getMsg());
			userIdResultVo.setSuccess(response.isSuccess());
			if (response.isSuccess()) {
				UserIdResultDto userVo = response.getData();
				userIdResultVo.setUserId(userVo.getUserId());
				userIdResultVo.setToken(userVo.getToken());
			}
		}
		
		return userIdResultVo;
	}

	@Override
	public UserDto getUserByMobile(String mobile) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", mobile);
		ResponseVo<UserDto> response = strongSercviceCaller.post(UserWebServiceNameConstants.GET_USER_BY_MOBILE, params,
                new TypeReference<ResponseVo<UserDto>>() {
                });
		
		if (null != response && response.isSuccess()) {
			return response.getData();
		}
		return null;
	}

	@Override
	public IdCardCertifincationDto queryCertifincationResult(Integer userId) {
		ResponseVo<IdCardCertifincationDto> response = strongSercviceCaller.post(UserWebServiceNameConstants.QUERY_CERTIFINCATION_RESULT, userId,
                new TypeReference<ResponseVo<IdCardCertifincationDto>>() {
                });
		
		if (null != response && response.isSuccess()) {
			return response.getData();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean setPassword(Integer userId, String password) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("password", password);
		ResponseVo response = strongSercviceCaller.post(UserWebServiceNameConstants.USER_SET_USER_PSD, params,
                ResponseVo.class);
		
		if (null != response) {
			return response.isSuccess();
		}
		return false;
	}

	@Override
	public CertificationResultResponseVo realNameCertifincation(
			UserRealNameCertifincationDto certifincationParams) {
		ResponseVo<CertificationResultResponseVo> response = strongSercviceCaller.post(UserWebServiceNameConstants.REALNAME_CERTIFINCATION, certifincationParams,
                new TypeReference<ResponseVo<CertificationResultResponseVo>>() {
                });
		
		if (null != response && response.isSuccess()) {
			return response.getData();
		}
		return null;
	}

	@Override
	public UserDto getUserById(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", id);
		ResponseVo<UserDto> response = strongSercviceCaller.post(UserWebServiceNameConstants.GET_USER_BY_ID, params,
                new TypeReference<ResponseVo<UserDto>>() {
                });
		
		if (null != response && response.isSuccess()) {
			return response.getData();
		}
		return null;
	}

	@Override
	public List<UserDto> batchQueryUserInfo(List<Integer> list) {
		ResponseVo<List<UserDto>> response = strongSercviceCaller.post(UserWebServiceNameConstants.BATCH_QUERY_USER_INFO, list,
                new TypeReference<ResponseVo<List<UserDto>>>() {
                });
		
		if (null != response && response.isSuccess()) {
			return response.getData();
		}
		return null;
	}

	@Override
	public Map<String, Object> getAgentTokenByMobile(String mobile) {
		ResponseVo<?> responseVo = strongSercviceCaller.post(UserWebServiceNameConstants.GET_AGENT_TOKEN_BY_MOBILE, mobile, ResponseVo.class);
		
		Map<String, Object> result = new HashMap<String, Object>();
		if (responseVo == null) {
			return null;
		} 
		result.put("agentFlag", responseVo.getCode());
		result.put("accessToken", responseVo.getData());
		return result;
	}

	@Override
	public StarArtistListDto getStarArtistList(StarArtistListRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", request.getUserId());
		params.put("roleCode", "15");
		int pageNo = request.getPageNo() == 0 ? 1 : request.getPageNo();
		int pageSize = request.getPageSize() == 0 ? 10 :request.getPageSize(); 
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		
		ResponseVo<StarArtistListDto> response = strongSercviceCaller.post(UserWebServiceNameConstants.GET_STARARTIST_LIST, params,
                new TypeReference<ResponseVo<StarArtistListDto>>() {
                });
		
		if (null != response && response.isSuccess()) {
			return response.getData();
		}
		return null;
	}

	@Override
	public List<MemberDto> getUserInfoByNameAndCode(String userName, String roleCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", userName);
		params.put("roleCode", roleCode);
		
		ResponseVo<List<MemberDto>> response = strongSercviceCaller.post(UserWebServiceNameConstants.GET_USER_INFO_BY_NAME_AND_CODE, params,
                new TypeReference<ResponseVo<List<MemberDto>>>() {
                });
		
		if (null != response && response.isSuccess()) {
			return response.getData();
		}
		return null;
	}

	@Override
	public UserIdResultDto registerByWeChatApplet(WechatAppletRegisterParamVo params) {
		ResponseVo<UserIdResultDto> response = strongSercviceCaller.post(UserWebServiceNameConstants.REGISTER_BY_WECHAT_APPLET, params,
                new TypeReference<ResponseVo<UserIdResultDto>>() {
                });
		
		UserIdResultDto userIdResultVo = new UserIdResultDto();
		if (null == response) {
			userIdResultVo.setCode(-2);
			userIdResultVo.setMsg("注册接口请求异常");
			userIdResultVo.setSuccess(false);
		} else {
			userIdResultVo.setCode(response.getCode());
			userIdResultVo.setMsg(response.getMsg());
			userIdResultVo.setSuccess(response.isSuccess());
			if (response.isSuccess()) {
				UserIdResultDto userVo = response.getData();
				userIdResultVo.setUserId(userVo.getUserId());
				userIdResultVo.setToken(userVo.getToken());
			}
		}
		
		return userIdResultVo;
	}

	@Override
	public String getNewStarMpOpenId(Integer userId) {
		ResponseVo<String> response = strongSercviceCaller.post(UserWebServiceNameConstants.GET_NEWSTAR_MP_OPENID, userId,
                new TypeReference<ResponseVo<String>>() {
                });
		
		if (null != response && response.isSuccess()) {
			return response.getData();
		}
		return null;
	}

	@Override
	public UserIdResultDto register(MobileRegisterParamVo params) {
		ResponseVo<UserIdResultDto> response = strongSercviceCaller.post(UserWebServiceNameConstants.USER_REGISTER_BY_MOBILE, params,
                new TypeReference<ResponseVo<UserIdResultDto>>() {
                });
		
		if (null != response && response.isSuccess()) {
			return response.getData();
		}
		return null;
	}

}
