package com.ihomefnt.o2o.intf.domain.paintscreen.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 画作列表
 *
 * @author liyonggang
 * @create 2018-12-10 14:43
 */
@Data
public class ResourceListDto implements Serializable {

    private static final long serialVersionUID = 3511932427317908149L;

    private Integer resourceId;

    private Integer resourceType;

    private  ResourceDetailDto resourceDetail;

    private Integer browseCount;

}
