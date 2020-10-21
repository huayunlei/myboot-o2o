package com.ihomefnt.o2o.intf.domain.homebuild.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-11-27 13:36
 */
@Data
public class HousePatternConfigResponse {

    private List<PatternConfigDto> configList;

    @Data
    public static class PatternConfigDto {
        private Integer id;
        private String name;
        private List<Integer> optionList;
    }
}
