package com.ihomefnt.o2o.intf.service.program;

import com.ihomefnt.o2o.intf.domain.program.vo.request.CompareDraftRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.CompareDraftResponse;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-06-25 11:09
 */
public interface DraftProgramService {

    /**
     * 已选方案对比（草稿比对）
     * @param request
     * @return
     */
    CompareDraftResponse compareDraftList(CompareDraftRequest request);

}
