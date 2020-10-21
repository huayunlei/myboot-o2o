package com.ihomefnt.o2o.intf.domain.personalneed.vo.response;

import lombok.Data;

import java.util.List;

@Data
public class DnaRoomListResponse {

    private List<DnaRoomInfoResponse> dnaRoomList;

    private long totalPage;

}
