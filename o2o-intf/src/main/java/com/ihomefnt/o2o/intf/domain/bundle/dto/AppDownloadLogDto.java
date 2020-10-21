package com.ihomefnt.o2o.intf.domain.bundle.dto;

import lombok.Data;

/**
 * Created by hvk687 on 9/18/15.
 */
@Data
public class AppDownloadLogDto {
    private Long id;
    private Integer osType;
    private String actCode;
    private String pvalue;
    private String ip;
    private String city;

}
