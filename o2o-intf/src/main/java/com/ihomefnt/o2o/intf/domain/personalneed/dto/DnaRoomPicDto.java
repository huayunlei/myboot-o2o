package com.ihomefnt.o2o.intf.domain.personalneed.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanyunxin
 * @create 2019-09-03 18:28
 */
@Data
@NoArgsConstructor
public class DnaRoomPicDto {

    private Integer imgWidth;

    private Integer imgHeight;

    private String dnaRoomPicUrl;

    public DnaRoomPicDto(Integer imgWidth, Integer imgHeight, String dnaRoomPicUrl) {
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.dnaRoomPicUrl = dnaRoomPicUrl;
    }
}
