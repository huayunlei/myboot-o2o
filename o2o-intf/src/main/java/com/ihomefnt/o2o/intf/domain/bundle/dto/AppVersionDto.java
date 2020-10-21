package com.ihomefnt.o2o.intf.domain.bundle.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by shirely_geng on 15-2-11.
 */
@Data
public class AppVersionDto {
    private Long vId;
    private String version;
    private String download;
    private Boolean must;
    private String updateContent;
    private String updateTime;
    private String partnerValue;
    /**
     * 强更版本号
     */
    private String mustUpdateVersion;
}
