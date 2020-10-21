package com.ihomefnt.o2o.service.service.designer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.service.designer.DesignerViewService;
import com.ihomefnt.o2o.intf.dao.designer.DesignerViewDao;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerView;

@Service
public class DesignerViewServiceImpl implements DesignerViewService {

	@Autowired
	DesignerViewDao designerViewDao;

	@Override
	public Long addDesignerView(DesignerView designerView) {
		return designerViewDao.addDesignerView(designerView);
	}
}
