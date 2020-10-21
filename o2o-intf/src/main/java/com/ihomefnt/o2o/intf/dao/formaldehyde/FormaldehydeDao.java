package com.ihomefnt.o2o.intf.dao.formaldehyde;

import com.ihomefnt.o2o.intf.domain.formaldehyde.dto.TFormaldehyde;

import java.util.List;

public interface FormaldehydeDao {

	Long enrollFormaldehyde(TFormaldehyde formaldehyde);
	
	List<String> queryFormaldehyde();
}
