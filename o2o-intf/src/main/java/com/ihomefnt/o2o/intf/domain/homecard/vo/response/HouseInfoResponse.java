package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import com.ihomefnt.o2o.intf.domain.homecard.dto.ProductFilterInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("根据户型id查询户型空间信息")
@Data
public class HouseInfoResponse {

    @ApiModelProperty("空间用途枚举")
    private RoomInfoVo roomInfo;

    @ApiModelProperty("空间用途可选性")
    private List<Integer> roomUsageFilterIdList;

    @ApiModelProperty("是否包含dr户型数据 0:无 1:有")
    private Integer hasHouseExt = 0;

    @ApiModelProperty("layOut跳转数据")
    private RoomLayOutVo roomLayOut;

    @ApiModelProperty("最新的户型id")
    private Integer newLayoutId;

    @ApiModelProperty("房间数量")
    private Integer layoutRoom;

    @ApiModelProperty("空间用途可选性枚举")
    private List<RoomUsageName> roomUsageNameList;


    @ApiModel("可选空间用途枚举")
    @Data
    public static class RoomInfoVo {

        @ApiModelProperty("可选空间用途枚举")
        private List<ProductFilterInfo> roomList;

        @ApiModelProperty("空间")
        private String roomName;
    }

    @ApiModel("layOut跳转数据")
    @Data
    public static class RoomLayOutVo {
        @ApiModelProperty("layOut跳转链接")
        private String layOutUrl;
    }

    @ApiModel("空间用途可选性")
    @Data
    public static class RoomUsageName {

        @ApiModelProperty("用途id")
        private Integer roomUsageId;

        @ApiModelProperty("用途名称")
        private String roomUsageName;

        public RoomUsageName(Integer roomUsageId, String roomUsageName) {
            this.roomUsageId = roomUsageId;
            this.roomUsageName = roomUsageName;
        }

        public RoomUsageName() {
        }
    }

}