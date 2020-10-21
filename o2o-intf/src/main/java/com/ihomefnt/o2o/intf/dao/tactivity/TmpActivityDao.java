package com.ihomefnt.o2o.intf.dao.tactivity;

import com.ihomefnt.o2o.intf.domain.tactivity.dto.TTmpActivity;

import java.util.List;
import java.util.Map;

public interface TmpActivityDao {

    List<TTmpActivity> queryTActivity(Map<String, Object> paramMap);
    
    int updateTActivityById(TTmpActivity tTmpActivity);
    
    int updateTActivityByMobile(TTmpActivity tTmpActivity);
}
