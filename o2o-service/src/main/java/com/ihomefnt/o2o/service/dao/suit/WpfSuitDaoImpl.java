package com.ihomefnt.o2o.service.dao.suit;

import com.ihomefnt.o2o.intf.dao.suit.WpfSuitDao;
import com.ihomefnt.o2o.intf.domain.suit.dto.*;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Repository
public class WpfSuitDaoImpl implements WpfSuitDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private static final String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.suit.WpfSuitDao.";
    
    @Override
    public List<TWpfSuit> getWpfSuitList() {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryWpfSuitList");
    }

    @Override
    public TWpfSuit getWpfSuitDetail(Integer wpfSuitId) {
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryWpfSuitDetail", wpfSuitId);
    }

    @Override
    public List<TWpfStyleImage> getWpfSuitBomImage(HttpWpfSuitRequest httpWpfSuitRequest) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryWpfSuitBomImage", httpWpfSuitRequest);
    }

    @Override
    public List<TWpfSuitAd> getWpfSuitAd() {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryWpfSuitAd");
    }

    @Override
    public int applyWpfService(HttpWpfServiceRequest httpWpfServiceResquest) {
        return sqlSessionTemplate.insert(NAME_SPACE + "addWpfServiceAppointment", httpWpfServiceResquest);
    }

	@Override
	public int applyH5WpfService(HttpWpfAppointmentRequest request) {
		return sqlSessionTemplate.insert(NAME_SPACE + "addH5WpfServiceAppointment", request);
	}

	@Override
	public HttpWpfAppointmentRequest queryRequestByAccessToken(String accessToken) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryRequestByAccessToken", accessToken);
	}

	@Override
	public List<TWpfMaterial> queryWpfMaterialItems(Integer wpfSuitId) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryWpfMaterialItems", wpfSuitId);
	}

	@Override
	public List<WpfCaseItem> queryWpfCaseList(List<Long> wpfCaseIdList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("wpfCaseIdList", wpfCaseIdList);
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryWpfCaseList", paramMap);
	}

	

	
}
