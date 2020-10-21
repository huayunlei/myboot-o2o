package com.ihomefnt.o2o.intf.domain.designdemand.dto;

import com.google.common.collect.Lists;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.CommitDesignRequest;
import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-08-13 19:19
 */
@Data
public class OrderDesignDemandListForDraft {

    private List<CommitDesignRequest> designDemandList = Lists.newArrayList();
}
