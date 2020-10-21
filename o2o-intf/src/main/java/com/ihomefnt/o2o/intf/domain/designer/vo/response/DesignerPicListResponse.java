package com.ihomefnt.o2o.intf.domain.designer.vo.response;

import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerPicture;
import lombok.Data;

import java.util.List;

/**
 * Created by hvk687 on 10/22/15.
 */
@Data
public class DesignerPicListResponse {
    private Integer total;
    private Integer size;
    private List<DesignerPicture> picList;
}
