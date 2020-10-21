package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.google.common.collect.Lists;
import com.ihomefnt.o2o.intf.domain.program.dto.ProgramPicDto;
import com.ihomefnt.o2o.intf.domain.program.vo.request.QueryCabinetPropertyListRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 方案软硬装清单
 *
 * @author liyonggang
 * @create 2019-07-02 17:45
 */
@Data
@Accessors(chain = true)
public class ProgramCommodityListResponse {

    List<RoomInfo> hardList = Lists.newArrayList();

    List<RoomInfo> softList = Lists.newArrayList();

    @Data
    @Accessors(chain = true)
    public static class RoomInfo {

        private Integer roomId;
        private String roomName;
        private Integer softNum = 0;
        private Integer hardNum = 0;
        private List<String> roomPicList;
        private List<ProgramPicDto> roomPicListNew;
        List<Item> itemList = Lists.newArrayList();

        public boolean getIsWholeHouse() {
            return roomName.equals("全屋");
        }


        @Data
        @Accessors(chain = true)
        public static class Item {
            private Integer skuId;
            private Integer categoryId;
            private String categoryName;
            private String image;
            private String skuName;
            private Integer itemType;//1:普通sku,2:bom,3:新定制品,4定制柜
            private Integer itemCount;
            private List<QueryCabinetPropertyListRequest.GroupQueryRequest> cabinetBomGroupInfoList;
            @ApiModelProperty("赠品展示标记 0 不展示 1可替换为赠品 2免费赠品 4效果图推荐")
            private Integer showFreeFlag = 0;
        }

    }

}
