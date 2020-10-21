package com.ihomefnt.o2o.intf.domain.bundle.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shirely_geng on 15-2-11.
 */
@Data
public class AppVersionCheckResponseVo implements Serializable {

    private static final long serialVersionUID = -1094910309799582580L;

    private String version;
    private String downloadUrl;
    private Boolean force;
    private Boolean upgrade;
    private List<String> summary;
    private String updateTimeStr;
    // 纷发地址
    private String updateUrl;

}
