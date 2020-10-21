package com.ihomefnt.o2o.intf.domain.paintscreen.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 画作资源详情
 *
 * @author liyonggang
 * @create 2018-12-10 14:38
 */
@Data
public class ResourceDetailDto implements Serializable {

    private static final long serialVersionUID = 4679061845589191494L;

    private Integer artId;
    private String artName;
    private String artImage;
    private String artIntro;
    private Integer categoryId;
    private Integer artType;
    private BigDecimal artPrice;
    private Integer sellState;
    private Integer artDeadline;
    private String artProperty;
    private String artTime;
    private Integer artistId;
    private Integer copyrightType;
    private String artTag;
    private Integer institutionsId;
    private Integer width;
    private Integer height;
    private String createTime;
    private Integer deleteFlag;
    private String deleteTime;

}
