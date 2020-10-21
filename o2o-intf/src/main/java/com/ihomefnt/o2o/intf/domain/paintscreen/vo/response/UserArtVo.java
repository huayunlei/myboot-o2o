package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import lombok.Data;

@Data
public class UserArtVo {



    private Integer userArtId;
    private Integer artId;
    private String artName;
    private String artImage;
    private Integer resourceType;
    private Integer artDeadline;
    private Integer width;
    private Integer height;
    private String artAcquireTime;
    private String artExpirationTime;
}
