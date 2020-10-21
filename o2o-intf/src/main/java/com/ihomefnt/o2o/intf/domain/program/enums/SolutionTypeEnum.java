package com.ihomefnt.o2o.intf.domain.program.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

/**
 * 产品方案类型枚举
 * 1定制方案 2贷款方案 3本户型方案 4相似户型方案
 *
 * @author Charl
 * @since 2017-09-18
 */
@AllArgsConstructor
@Getter
public enum SolutionTypeEnum {

    CUSTOMIZED(1, "定制方案"),

    LOAN(2, "贷款方案"),

    NEIGHBOR(3, "本户型方案"),

    LIKE(4, "相似户型方案");

    private Integer type;

    private String name;


    public static String getNameByType(Integer status) {
        return Stream.of(values())
                .filter(el -> el.getType().equals(status))
                .map(SolutionTypeEnum::getName)
                .findFirst()
                .orElse("");
    }

    public static SolutionTypeEnum getEnumByStatus(Integer status) {
        return Stream.of(values())
                .filter(el -> el.getType().equals(status))
                .findFirst()
                .orElse(null);
    }

}