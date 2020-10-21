package com.ihomefnt.o2o.intf.domain.designdemand.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liyonggang
 * @create 2019-08-13 18:34
 */
@Data
@Accessors(chain = true)
public class CommentRecordResponse {

    private Integer designDemandId;

    private Integer commitRecordId;

    private String imageUrl;

    private String dnaName;

    private String dnaStyle;//DNA风格名称

    private String time;

    private String statusName;

    private Integer status; //设计任务 1待提交 2待确认 3设计中 4已完成

    private String priceRange;

    private Integer type = 2; //1:草稿，2:提交过的数据
}
