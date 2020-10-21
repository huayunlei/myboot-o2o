package com.ihomefnt.o2o.intf.domain.program.customgoods.dto;

import com.ihomefnt.o2o.intf.domain.program.customgoods.request.DetailQueryRequest;
import com.ihomefnt.o2o.intf.domain.program.customgoods.response.DetailVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liyonggang
 * @create 2019-06-26 11:15
 */
@Data
@ApiModel
@Accessors(chain = true)
public class GroupDetailListDto {

    private DetailQueryRequest detailQueryRequest;

    private DetailVO detailVO;
}
