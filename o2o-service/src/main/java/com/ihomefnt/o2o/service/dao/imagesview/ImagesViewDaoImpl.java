package com.ihomefnt.o2o.service.dao.imagesview;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.imagesview.ImagesViewDao;
import com.ihomefnt.o2o.intf.domain.imagesview.dto.ImagesView;

@Repository
public class ImagesViewDaoImpl implements ImagesViewDao{

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.imagesview.ImagesViewDao.";
    
	@Override
	public Long addImagesView(ImagesView imagesView) {
		sqlSessionTemplate.insert(NAME_SPACE + "addImagesView", imagesView);
		return imagesView.getImagesViewId();
	}
}
