package com.ihomefnt.o2o.api.controller.htp;

import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.htp.vo.request.GetExtByHouseIdRequestVo;
import com.ihomefnt.o2o.intf.domain.htp.vo.response.GetExtByHouseIdResponseVo;
import com.ihomefnt.o2o.intf.manager.util.common.response.ResponseVo;
import com.ihomefnt.o2o.intf.service.htp.HtpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "【户型接口】户型api")
@RequestMapping("/htp")
@RestController
public class HtpController {

    @Autowired
    private HtpService htpService;

    @ApiOperation(value = "根据户型id查询户型扩展信息", notes = "根据户型id查询户型扩展信息")
    @PostMapping(value = "/getExtByHouseId")
    public ResponseVo<GetExtByHouseIdResponseVo> getExtByHouseId(@RequestBody GetExtByHouseIdRequestVo request) {
        GetExtByHouseIdResponseVo mainPageResponse = htpService.getExtByHouseId(request);
        return ResponseVo.success(HttpResponseCode.SUCCESS, mainPageResponse, MessageConstant.SUCCESS);
    }
}
