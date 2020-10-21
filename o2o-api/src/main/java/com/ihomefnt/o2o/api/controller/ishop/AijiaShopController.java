package com.ihomefnt.o2o.api.controller.ishop;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.ishop.vo.request.AijiaShopHomeRequestVo;
import com.ihomefnt.o2o.intf.domain.ishop.vo.response.AijiaShopHomeResponseVo;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.ishop.AijiaShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Deprecated
@Api(tags = "艾商城首页api", hidden = true)
@RequestMapping("/aijiaShop")
@RestController
public class AijiaShopController {

    @Autowired
    private AijiaShopService aijiaShopService;

    /**
     * 艾商城首页 用户不登录也可以显示
     * param request
     *
     * @return
     */
    @ApiOperation(value = "homecard", notes = "艾商城首页接口")
    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public HttpBaseResponse<AijiaShopHomeResponseVo> home(@Json AijiaShopHomeRequestVo requestVo) {
        AijiaShopHomeResponseVo obj = aijiaShopService.getAijiaShopHome(requestVo);
        return HttpBaseResponse.success(obj);
    }

}
