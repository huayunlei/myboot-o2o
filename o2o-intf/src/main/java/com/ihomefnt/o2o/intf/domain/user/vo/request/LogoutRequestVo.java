package com.ihomefnt.o2o.intf.domain.user.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by shirely_geng on 15-1-18.
 */
@Data
public class LogoutRequestVo extends HttpBaseRequest {
    private String refreshToken;
    
    private String mobile;
}
