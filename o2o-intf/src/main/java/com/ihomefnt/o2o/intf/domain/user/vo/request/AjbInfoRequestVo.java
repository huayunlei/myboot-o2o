package com.ihomefnt.o2o.intf.domain.user.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class AjbInfoRequestVo extends HttpBaseRequest {

    private Integer accountBookType;    //账本类型  现金券 1, 诚意金 2, 定金 3, 佣金 4, 提现 5,艾积分 6
    private Integer pageNo;
    private Integer pageSize;
    
}
