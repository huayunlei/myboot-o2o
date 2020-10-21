package com.ihomefnt.o2o.service.dao.formaldehyde;

import com.ihomefnt.o2o.intf.dao.formaldehyde.FormaldehydeDao;
import com.ihomefnt.o2o.intf.domain.formaldehyde.dto.TFormaldehyde;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FormaldehydeDaoImpl implements FormaldehydeDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    private static final String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.formaldehyde.FormaldehydeDao.";
    
	@Override
	public Long enrollFormaldehyde(TFormaldehyde formaldehyde) {
        sqlSessionTemplate.insert(NAME_SPACE + "enrollFormaldehyde", formaldehyde);
        return formaldehyde.getId();
	}

	@Override
	public List<String> queryFormaldehyde() {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryFormaldehyde");
	}

}
