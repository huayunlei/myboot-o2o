package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liyonggang
 * @create 2018-11-05 15:12
 */
@Data
public class ArtworkImageDto implements Serializable {


    private static final long serialVersionUID = -6153150993683618062L;

    private String imageId;

    private String imageUrl;

    private String description;

    private String order;

    private String width;

    private String height;

    private String imageType;

}
