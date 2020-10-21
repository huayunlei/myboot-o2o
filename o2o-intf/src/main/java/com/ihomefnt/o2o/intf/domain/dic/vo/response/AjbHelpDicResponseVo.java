package com.ihomefnt.o2o.intf.domain.dic.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.user.dto.AjbRemark;
import lombok.Data;

@Data
public class AjbHelpDicResponseVo {

    private List<AjbRemark> remarkList;
}
