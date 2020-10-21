package com.ihomefnt.o2o.intf.domain.designer.vo.response;

import com.ihomefnt.o2o.intf.domain.designer.vo.request.PageRequest;
import lombok.Data;

/**
 * Created by hvk687 on 10/22/15.
 */
@Data
public class DesignerSuitListResponse extends PageRequest {
    private Long designerId;
}
