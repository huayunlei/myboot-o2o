package com.ihomefnt.o2o.intf.domain.paintscreen.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户画作列表
 *
 * @author liyonggang
 * @create 2018-12-10 19:39
 */
@Data
public class UserArtDto implements Serializable {

    private static final long serialVersionUID = -890637395589271506L;

    //获取时间
    private String artAcquireTime;
    //艺术品时效 0:永久有效 1:一个月 2:三个月 3:半年 4:一年
    private Integer artDeadline;
    //过期时间
    private String artExpirationTime;
    //画作ID
    private Integer artId;
    //画作图片
    private String artImage;
    //0:用户上传 1:应用提供
    private Integer resourceType;
    //用户画作ID
    private Integer userArtId;
    //高
    private Integer height;
    //宽
    private Integer width;

    private String artName;
}
