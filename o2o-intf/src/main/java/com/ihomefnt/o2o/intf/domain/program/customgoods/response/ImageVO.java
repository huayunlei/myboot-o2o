package com.ihomefnt.o2o.intf.domain.program.customgoods.response;

import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

/**
 * 图片数据
 *
 * @author liyonggang
 * @create 2019-03-18 19:53
 */
@ApiModel("图片")
@Data
@Accessors(chain = true)
public class ImageVO {

    @ApiModelProperty("大图")
    private String bigImage;

    @ApiModelProperty("中图")
    private String middleImage;

    @ApiModelProperty("小图")
    private String smallImage;

    private static ImageVO build(String bigImage, String middleImage, String smallImage) {
        return new ImageVO().setBigImage(bigImage).setSmallImage(smallImage).setMiddleImage(middleImage);
    }

    /**
     * 封装图片，包装为原图+按屏幕宽度缩放+按屏幕宽度五分之一缩放的数据，供前端使用
     *
     * @param image
     * @param width
     * @return
     */
    public static ImageVO buildImageVO(String image, Integer width) {
        if (StringUtils.isBlank(image) || width == null) {
            return null;
        }
        return ImageVO.build(AliImageUtil.imageCompress(image,2,width, ImageConstant.SIZE_LARGE), AliImageUtil.imageCompress(image,2,width, ImageConstant.SIZE_MIDDLE), AliImageUtil.imageCompress(image,2,width, ImageConstant.SIZE_SMALL));

    }
}
