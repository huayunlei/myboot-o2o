package com.ihomefnt.o2o.intf.domain.program.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 产品方案类型枚举
 * 方案类型 0软装+硬装 1纯软装
 *
 * @author Charl
 * @since 2017-09-18
 */
@AllArgsConstructor
@Getter
public enum DecorationTypeEnum {

    ALL(0, "软装+硬装"),

    SOFT(1, "纯软装");

    private Integer type;

    private String name;


    public static String getNameByType(Integer type) {
        return Stream.of(values())
                .filter(el -> el.getType().equals(type))
                .map(DecorationTypeEnum::getName)
                .findFirst()
                .orElse("");
    }

    public static DecorationTypeEnum getEnumByStatus(Integer type) {
        return Stream.of(values())
                .filter(el -> el.getType().equals(type))
                .findFirst()
                .orElse(null);
    }

}