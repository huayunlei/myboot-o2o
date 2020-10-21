package com.ihomefnt.o2o.service.service.tactivity;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.service.tactivity.TmpActivityService;
import com.ihomefnt.o2o.intf.dao.tactivity.TmpActivityDao;
import com.ihomefnt.o2o.intf.domain.tactivity.dto.TTmpActivity;

@Service
public class TmpActivityServiceImpl implements TmpActivityService{

    @Autowired
    TmpActivityDao tmpActivityDao;

    @Override
    public List<TTmpActivity> queryTActivity(Map<String, Object> paramMap) {
        return tmpActivityDao.queryTActivity(paramMap);
    }

    @Override
    public int updateTActivityById(TTmpActivity tTmpActivity) {
        return tmpActivityDao.updateTActivityById(tTmpActivity);
    }

    @Override
    public int updateTActivityByMobile(TTmpActivity tTmpActivity) {
        return tmpActivityDao.updateTActivityByMobile(tTmpActivity);
    }
}
