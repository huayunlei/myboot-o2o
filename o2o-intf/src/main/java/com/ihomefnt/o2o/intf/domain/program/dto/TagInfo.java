package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liyonggang
 * @create 2019-08-09 11:47
 */
@Data
@Accessors(chain = true)
public class TagInfo {
    private Integer tagId;
    private String tagName;
}
