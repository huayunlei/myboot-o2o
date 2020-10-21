package com.ihomefnt.o2o.api.controller.mirror;

import com.ihomefnt.o2o.intf.domain.mirror.dto.UserInformationDto;
import com.ihomefnt.o2o.intf.manager.util.common.response.ResponseVo;
import com.ihomefnt.o2o.intf.service.mirror.MagicMirrorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 何佳文
 * @date: 2019-11-08 16:20
 */
@Api(tags = "【魔镜API】")
@RestController
@RequestMapping("/magic/mirror")
public class MagicMirrorController {

    @Resource
    private MagicMirrorService magicMirrorService;

    @PostMapping("/information")
    @ApiOperation("用户信息")
    public ResponseVo<Boolean> information(@RequestBody UserInformationDto informationDto) {
        boolean result = magicMirrorService.submitUserInformation(informationDto);
        return ResponseVo.success(result);
    }
}
