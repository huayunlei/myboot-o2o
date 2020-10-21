package com.ihomefnt.o2o.service.dao.designer;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.designer.DesignerViewDao;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerView;

@Repository
public class DesignerViewDaoImpl implements DesignerViewDao{

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.designer.DesignerViewDao.";
    
	@Override
	public Long addDesignerView(DesignerView designerView) {
		sqlSessionTemplate.insert(NAME_SPACE + "addDesignerView", designerView);
		return designerView.getDesignerViewId();
	}
}
