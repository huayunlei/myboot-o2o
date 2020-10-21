package com.ihomefnt.o2o.intf.domain.user.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by shirely_geng on 15-1-15.
 */
@Data
public class ReSendSmsRequestVo extends HttpBaseRequest {
    private String mobile;
    private String registerKey;
}
