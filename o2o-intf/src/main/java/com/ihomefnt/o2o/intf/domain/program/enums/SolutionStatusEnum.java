package com.ihomefnt.o2o.intf.domain.program.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

/**
 * 产品方案状态枚举
 *
 * @author Charl
 * @since 2017-09-18
 */
@AllArgsConstructor
@Getter
public enum SolutionStatusEnum {

    WAIT_SUBMIT(0, "未提交审核"),

    WAIT_CHECK(1, "审核中"),

    /**
     * 2019.08.15 方案状态扭转调整，废弃此状态
     */
    CHECK_PASS(2, "审核通过"),

    CHECK_FAILED(3, "审核不通过"),

    ONLINE(4, "已上线"),

    RENDERING_IMAGE(5, "渲图中"),

    OFFLINE(6, "已下线");

    private Integer status;

    private String name;

    /**
     * Map<方案当前状态, 可变更为的状态集合>
     */
    public static Map<SolutionStatusEnum, List<SolutionStatusEnum>> STATUS_ENUM_MAP;

    static {
        STATUS_ENUM_MAP = new HashMap<>();

        STATUS_ENUM_MAP.put(WAIT_SUBMIT, Collections.singletonList(WAIT_CHECK));
        STATUS_ENUM_MAP.put(WAIT_CHECK, Arrays.asList(CHECK_FAILED, RENDERING_IMAGE, ONLINE));
        STATUS_ENUM_MAP.put(CHECK_FAILED, Arrays.asList(WAIT_SUBMIT, WAIT_CHECK));
        STATUS_ENUM_MAP.put(ONLINE, Arrays.asList(OFFLINE, WAIT_SUBMIT));
        STATUS_ENUM_MAP.put(RENDERING_IMAGE, Arrays.asList(OFFLINE, WAIT_SUBMIT, ONLINE));
        STATUS_ENUM_MAP.put(OFFLINE, Arrays.asList(WAIT_SUBMIT, WAIT_CHECK));
    }

    public static String getNameByStatus(Integer status) {
        return Stream.of(values())
                .filter(el -> el.getStatus().equals(status))
                .map(SolutionStatusEnum::getName)
                .findFirst()
                .orElse("");
    }

    public static SolutionStatusEnum getEnumByStatus(Integer status) {
        return Stream.of(values())
                .filter(el -> el.getStatus().equals(status))
                .findFirst()
                .orElse(null);
    }

}