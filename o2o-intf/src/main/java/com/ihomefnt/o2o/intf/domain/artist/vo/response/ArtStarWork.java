/*
 * Author: ZHAO
 * Date: 2018/10/11
 * Description:ArtStarWork.java
 */
package com.ihomefnt.o2o.intf.domain.artist.vo.response;

import com.ihomefnt.cms.intf.art.dto.ArtImage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 小星星作品
 *
 * @author ZHAO
 */
@Data
@ApiModel("小星星作品")
public class ArtStarWork {

    @ApiModelProperty("艺术家ID")
    private Long arterId;

    @ApiModelProperty("艺术家名称")
    private String arterName;

    @ApiModelProperty("艺术家头像")
    private String arterImgUrl;

    @ApiModelProperty("作品ID")
    private Long artId;

    @ApiModelProperty("作品名称")
    private String artName;

    @ApiModelProperty("作品图片")
    private String imgUrl;

    @ApiModelProperty("是否入围:1入围,0未入围")
    private Integer selectedStatus;

    @ApiModelProperty("作品简述")
    private String productDescription;

    @ApiModelProperty("制作过程")
    private List<ArtImage> imageList;

    @ApiModelProperty("音频")
    private String videoUrl;

    @ApiModelProperty("点评内容")
    private String reviewContent;
}
