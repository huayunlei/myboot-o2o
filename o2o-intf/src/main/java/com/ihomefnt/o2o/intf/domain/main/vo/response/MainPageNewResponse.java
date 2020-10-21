package com.ihomefnt.o2o.intf.domain.main.vo.response;

import com.ihomefnt.o2o.intf.domain.main.vo.vo.AlertInfoVo;
import com.ihomefnt.o2o.intf.domain.main.vo.vo.CoreOperationAreaInfoVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("首页核心操作区返回")
@Data
public class MainPageNewResponse {

    private CoreOperationAreaInfoVo coreOperationAreaInfo;

    private AlertInfoVo alertInfo;

}
