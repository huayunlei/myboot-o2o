package com.ihomefnt.o2o.intf.domain.paintscreen.dto;

import lombok.Data;

/**
 * 文件信息
 *
 * @author liyonggang
 * @create 2019-01-17 16:55
 */
@Data
public class ImageInfoDto {

    private String fileUrl;//文件url
    private String fileType;//文件类型
    private String fileName;//文件名
    private Integer size;//文件大小
    private Integer width;//图片宽度
    private Integer height;//图片高度
}
