package com.ihomefnt.o2o.intf.domain.program.customgoods.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-06-26 11:08
 */
@Data
public class GroupDetailListRequest extends HttpBaseRequest {

    private List<Integer> groupIdList;

}
