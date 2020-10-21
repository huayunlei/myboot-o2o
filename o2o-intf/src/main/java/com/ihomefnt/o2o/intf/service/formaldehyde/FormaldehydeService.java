package com.ihomefnt.o2o.intf.service.formaldehyde;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.formaldehyde.dto.TFormaldehyde;

public interface FormaldehydeService {

	Long enrollFormaldehyde(TFormaldehyde formaldehyde);
	
	List<String> queryFormaldehyde();
}
