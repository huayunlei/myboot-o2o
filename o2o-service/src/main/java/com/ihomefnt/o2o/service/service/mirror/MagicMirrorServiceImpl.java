package com.ihomefnt.o2o.service.service.mirror;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.mirror.dto.UserInformationDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.IhomeApiServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.MagicMirrorServiceNameConstants;
import com.ihomefnt.o2o.intf.service.mirror.MagicMirrorService;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @description:
 * @author: 何佳文
 * @date: 2019-11-08 16:29
 */
@Service
public class MagicMirrorServiceImpl implements MagicMirrorService {

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public boolean submitUserInformation(UserInformationDto userInformationDto) {
        ResponseVo<Boolean> result = strongSercviceCaller.post(MagicMirrorServiceNameConstants.SUBMIT_USER_INFORMATION, userInformationDto, new TypeReference<ResponseVo<Boolean>>() {
        });
        if (result != null) {
            return result.isSuccess();
        } else {
            return false;
        }
    }
}
