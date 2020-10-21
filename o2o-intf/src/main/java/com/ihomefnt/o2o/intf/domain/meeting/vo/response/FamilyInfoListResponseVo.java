package com.ihomefnt.o2o.intf.domain.meeting.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FamilyInfoListResponseVo {
    private List<FamilyInfoResponse> familyList;
}
