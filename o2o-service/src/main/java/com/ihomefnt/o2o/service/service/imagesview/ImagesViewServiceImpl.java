package com.ihomefnt.o2o.service.service.imagesview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.service.imagesview.ImagesViewService;
import com.ihomefnt.o2o.intf.dao.imagesview.ImagesViewDao;
import com.ihomefnt.o2o.intf.domain.imagesview.dto.ImagesView;

@Service
public class ImagesViewServiceImpl implements ImagesViewService{
	
	@Autowired
	ImagesViewDao imagesViewDao;

	@Override
	public Long addImagesView(ImagesView imagesView) {
		return imagesViewDao.addImagesView(imagesView);
	}

}
