package com.ihomefnt.o2o.intf.domain.bundle.vo.response;

import lombok.Data;

/**
 * Created by shirely_geng on 15-1-20.
 */
@Data
public class QiniuTokenResponseVo {
    private String token;
    private String downloadUrl;
    private String key;
}
