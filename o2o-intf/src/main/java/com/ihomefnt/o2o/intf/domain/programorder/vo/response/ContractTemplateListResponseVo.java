package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import com.ihomefnt.o2o.intf.domain.program.vo.response.ContractInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ContractTemplateListResponseVo {
    private List<ContractInfoResponse> contractTemplateList;
}
