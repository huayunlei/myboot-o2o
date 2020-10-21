package com.ihomefnt.o2o.intf.domain.inspiration.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Article {
    private Long articleId;

    private String articleTitle;

    private Long fidCategory;

    private String articleContent;

    private Integer articleStatus;

    private Timestamp createTime;

    private Timestamp updateTime;

    private String headFigure;

    private String classify;//分类

    private String description;

}
