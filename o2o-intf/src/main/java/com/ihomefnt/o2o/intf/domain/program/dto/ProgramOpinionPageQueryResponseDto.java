package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.program.vo.response.ProgramOpinionDetailResponse;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author hua
 * @Date 2019-11-05 15:48
 */
@Data
public class ProgramOpinionPageQueryResponseDto {

    private List<ProgramOpinionDetailResponse> records;
    /**
     * 总记录数
     */
    private int totalCount;
}
