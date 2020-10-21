package com.ihomefnt.o2o.intf.domain.user.vo.request;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class SendSMSRequestVo extends HttpBaseRequest{
	private List<String> mobiles;
}
