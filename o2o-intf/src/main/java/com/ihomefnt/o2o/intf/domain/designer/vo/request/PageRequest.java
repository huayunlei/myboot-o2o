package com.ihomefnt.o2o.intf.domain.designer.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by hvk687 on 10/22/15.
 */
@Data
public class PageRequest extends HttpBaseRequest {
    private Integer pageNo;
    private Integer pageSize;
}
