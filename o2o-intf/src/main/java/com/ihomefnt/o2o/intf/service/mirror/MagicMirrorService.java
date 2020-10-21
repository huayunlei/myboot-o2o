package com.ihomefnt.o2o.intf.service.mirror;

import com.ihomefnt.o2o.intf.domain.mirror.dto.UserInformationDto;

/**
 * @description:
 * @author: 何佳文
 * @date: 2019-11-08 16:23
 */
public interface MagicMirrorService {

    boolean submitUserInformation(UserInformationDto userInformationDto);
}
