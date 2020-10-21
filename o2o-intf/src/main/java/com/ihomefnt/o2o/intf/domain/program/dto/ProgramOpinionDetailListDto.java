package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.util.List;

/**
 * 详情集合
 *
 * @author liyonggang
 * @create 2019-08-09 18:12
 */
@Data
public class ProgramOpinionDetailListDto {

    private Integer count;

    List<ProgramOpinionDetailDto> reviseOpinionList;
}
