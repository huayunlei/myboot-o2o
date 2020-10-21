package com.ihomefnt.o2o.intf.service.tactivity;

import com.ihomefnt.o2o.intf.domain.tactivity.dto.TTmpActivity;

import java.util.List;
import java.util.Map;

public interface TmpActivityService {

    List<TTmpActivity> queryTActivity(Map<String, Object> paramMap);
    
    int updateTActivityById(TTmpActivity tTmpActivity);
    
    int updateTActivityByMobile(TTmpActivity tTmpActivity);
}
