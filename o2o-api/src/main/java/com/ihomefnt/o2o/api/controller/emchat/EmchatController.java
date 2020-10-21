package com.ihomefnt.o2o.api.controller.emchat;


import com.ihomefnt.o2o.api.controller.BaseController;
import com.ihomefnt.o2o.common.config.ApiConfig;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.emchat.vo.request.ChatBaseRequestVo;
import com.ihomefnt.o2o.intf.domain.emchat.vo.request.EmchatIMUserRequestVo;
import com.ihomefnt.o2o.intf.domain.emchat.vo.response.ChatBaseResponseVo;
import com.ihomefnt.o2o.intf.domain.emchat.vo.response.EmchatIMUserResponseVo;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.emchat.EmchatIMUsersService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "【环信API】")
@RestController
@RequestMapping("/chat")
public class EmchatController extends BaseController {

    @Autowired
    ApiConfig apiConfig;

    @Autowired
    EmchatIMUsersService emchatIMUsersService;

    /**
     * 获取商品链接和常用语.
     *
     * @param chatBaseRequest
     * @return
     */
    @PostMapping(value = "/prodexp")
    public HttpBaseResponse<ChatBaseResponseVo> getProductAndExp(@Json ChatBaseRequestVo chatBaseRequest) {
        ChatBaseResponseVo chatBaseResponse = new ChatBaseResponseVo();
        if (null != chatBaseRequest) {
            String host = apiConfig.pcHost;
            if (!StringUtils.isNotBlank(host)) {
                host = "https://www.ihomefnt.com/";
            }
            StringBuilder sb = new StringBuilder(host);
            Long suitId = chatBaseRequest.getSuitId();
            Long roomId = chatBaseRequest.getRoomId();
            if (null != suitId) {
                sb.append("suit/").append(suitId);
                chatBaseResponse.setProductLink(sb.toString());
            } else if (null != roomId) {
                sb.append("spacedetail/").append(roomId);
                chatBaseResponse.setProductLink(sb.toString());
            }
        }

        return HttpBaseResponse.success(chatBaseResponse);
    }

    /**
     * 注册环信IM用户.
     *
     * @param emchatIMUserRequest
     * @return
     */
    @PostMapping(value = "/regemuser")
    public HttpBaseResponse<Void> registerEmIMUser(@Json EmchatIMUserRequestVo emchatIMUserRequest) {
        String userName = emchatIMUserRequest.getUserName();
        String nickName = emchatIMUserRequest.getNickName();
        int result = emchatIMUsersService.registerEmUser(userName, nickName);

        if (result != 1) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }

        return HttpBaseResponse.success();
    }


    /**
     * 初始化本地用户为环信IM用户.
     *
     * @return
     */
    @PostMapping(value = "/initemuser")
    public HttpBaseResponse<Void> initEmchatIMUser() {
        int result = emchatIMUsersService.initEmchatIMUser();

        if (result != 1) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }

        return HttpBaseResponse.success();
    }

    /**
     * 获取环信IM用户信息.
     *
     * @param emchatIMUserRequest
     * @return
     */
    @PostMapping(value = "/getemuser")
    public HttpBaseResponse<EmchatIMUserResponseVo> getEmchatIMUser(@Json EmchatIMUserRequestVo emchatIMUserRequest) {
        String userName = emchatIMUserRequest.getUserName();
        EmchatIMUserResponseVo emchatIMUser = emchatIMUsersService.getEmchatIMUser(userName);

        if (null == emchatIMUser) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }

        return HttpBaseResponse.success();
    }

    /**
     * 删除环信IM用户和本地信息.
     *
     * @return
     */
    @PostMapping(value = "/delemuser")
    public HttpBaseResponse<Void> deleteEmchatIMUser() {
        int result = emchatIMUsersService.deleteEmchatIMUser();

        if (result != 1) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }

        return HttpBaseResponse.success();
    }

    /**
     * 修改环信IM用户昵称.
     *
     * @param emchatIMUserRequest
     * @return
     */
    @PostMapping(value = "/modemuser")
    public HttpBaseResponse<Void> modifyEmchatIMUser(@Json EmchatIMUserRequestVo emchatIMUserRequest) {
        String userName = emchatIMUserRequest.getUserName();
        String nickName = emchatIMUserRequest.getNickName();
        int result = emchatIMUsersService.modifyEmchatIMUserNickname(userName, nickName);

        if (result != 1) {
            return HttpBaseResponse.fail(MessageConstant.FAILED);
        }

        return HttpBaseResponse.success();
    }


}
