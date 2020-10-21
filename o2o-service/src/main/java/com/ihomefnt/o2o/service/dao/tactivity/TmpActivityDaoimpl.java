package com.ihomefnt.o2o.service.dao.tactivity;

import com.ihomefnt.o2o.intf.dao.tactivity.TmpActivityDao;
import com.ihomefnt.o2o.intf.domain.tactivity.dto.TTmpActivity;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TmpActivityDaoimpl implements TmpActivityDao{

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;
    
    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.tactivity.TmpActivityDao.";

    @Override
    public List<TTmpActivity> queryTActivity(Map<String, Object> paramMap) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryTActivity", paramMap);
    }

    @Override
    public int updateTActivityById(TTmpActivity tTmpActivity) {
        return sqlSessionTemplate.update(NAME_SPACE + "updateTActivityById", tTmpActivity);
    }

    @Override
    public int updateTActivityByMobile(TTmpActivity tTmpActivity) {
        return sqlSessionTemplate.update(NAME_SPACE + "updateTActivityByMobile", tTmpActivity);
    }
}
