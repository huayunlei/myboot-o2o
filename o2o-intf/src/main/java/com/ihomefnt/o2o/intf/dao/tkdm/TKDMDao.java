package com.ihomefnt.o2o.intf.dao.tkdm;

import com.ihomefnt.o2o.intf.domain.tkdm.dto.TKDMSeo;

/**
 * Created by wangxiao on 10/29/15.
 */
public interface TKDMDao {
	
	TKDMSeo loadTKDM(String seoKey);
	
}
