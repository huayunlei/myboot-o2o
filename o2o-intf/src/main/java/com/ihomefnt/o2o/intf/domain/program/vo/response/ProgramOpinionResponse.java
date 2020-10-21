package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-08-08 16:06
 */
@Data
public class ProgramOpinionResponse {

    private Integer totalRecordCount = 0;

    private Integer current;

    private Integer total = 0;

    private Integer size;

    private Integer pages;

    private List<ProgramOpinionRecordInfo> records = Lists.newArrayList();
}
