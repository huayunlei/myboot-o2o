package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import com.ihomefnt.o2o.intf.domain.program.vo.request.AddBagCreateRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.program.dto.RoomEffectImageDto;
import com.ihomefnt.o2o.intf.domain.program.dto.RoomReplaceHardProductDto;
import com.ihomefnt.o2o.intf.domain.program.dto.RoomReplaceProductDto;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.WholeRoomSpaceInfo;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ServiceItemDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 创建订单请求输入
 *
 * @author xwf
 */
@ApiModel("查询订单价格入参")
@Data
public class QueryFamilyOrderPriceRequest  extends HttpBaseRequest {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "全品家订单id")
    private Integer orderId;

    @ApiModelProperty(value = "房产id")
    @Deprecated
    private Integer houseId;

    @ApiModelProperty(value = "房产id")
    private Integer customerHouseId;

    @ApiModelProperty(value = "空间id集合")
    private List<Integer> roomIds;

    @ApiModelProperty(value = "空间效果图集合")
    private List<RoomEffectImageDto> roomEffectImageDtos;

    @ApiModelProperty("软装增配包商品清单")
    private List<AddBagCreateRequest> softAddBagProducts;

    @ApiModelProperty("硬装增配包商品清单")
    private List<AddBagCreateRequest> hardAddBagProducts;

    @ApiModelProperty("硬装升级项集合")
    private List<StandardCreateRequest> hardStandardDtos;

    @ApiModelProperty(value = "订单来源")
    private int source = 0;

    @ApiModelProperty("空间商品调整对象")
    private List<RoomReplaceProductDto> replaceProductDtos;

    @ApiModelProperty("空间硬装商品调整对象")
    private List<RoomReplaceHardProductDto> replaceHardProductDtos;
    /*
     * 硬装二期2018-10-08
     */
    // by cangjifeng
    @ApiModelProperty("全屋空间替换清单")
    private WholeRoomSpaceInfo wholeRoomSpaceInfo;
    // by cangjifeng end

    @ApiModelProperty("服务费明细")
    private List<ServiceItemDto> serviceItemList;
}
