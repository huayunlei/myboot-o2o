package com.ihomefnt.o2o.intf.domain.personalneed.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2018/9/6
 */
@Data
@Accessors(chain = true)
public class StyleRecordRequest extends HttpBaseRequest {

    private Integer orderId;

}
