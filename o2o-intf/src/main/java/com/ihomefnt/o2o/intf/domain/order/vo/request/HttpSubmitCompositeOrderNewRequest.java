package com.ihomefnt.o2o.intf.domain.order.vo.request;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.product.doo.Room;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class HttpSubmitCompositeOrderNewRequest extends HttpBaseRequest {
    private List<Room> roomList;
    private Long compositeProductId;//套装的产品ID
    
    private Long roomId;//空间的产品ID
    private int isUseCoupon;//是否使用现金券
    
    private Long voucherId;// 抵用券领取ID,存放的是t_voucher_detail表主键.
    
}
