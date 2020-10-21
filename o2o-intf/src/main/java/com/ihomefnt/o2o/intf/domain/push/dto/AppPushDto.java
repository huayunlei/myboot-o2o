package com.ihomefnt.o2o.intf.domain.push.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * wcm APP push
 *
 * @author liyonggang
 * @create 2019-11-28 10:52 上午
 */
@Data
@Accessors(chain = true)
public class AppPushDto {
    private String mobile;
    private Map<String, String> params;
    private String pushKey;
}
