package com.ihomefnt.o2o.intf.domain.program.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 图片信息
 *
 * @author liyonggang
 * @create 2019-04-22 18:23
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OffLineMessageDto {
    private Integer taskId;//dr任务id

    private List<String> url;//图片集合
}
