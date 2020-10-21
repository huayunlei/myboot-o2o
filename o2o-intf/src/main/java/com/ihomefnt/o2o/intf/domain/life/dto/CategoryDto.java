package com.ihomefnt.o2o.intf.domain.life.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/28 0028.
 */
@Data
public class CategoryDto implements Serializable {
    private static final long serialVersionUID = -7899381941037014696L;
    /**
     * 类目ID
     */
    private int categoryId;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 类目图片
     */
    private String iconUrl;

    /**
     * 简述
     */
    private String sketch;

    /**
     * 创建时间
     */
    private String createTime;

    private String updateTime;


    /**
     * 0 未上架 1已上架
     */
    private int showFlag;

    /**
     * 已发布文章数量
     */
    private Integer count;
    /**
     * 情景图
     */
    private String imageUrl;

    /**
     * 排序号
     */
    private int sortNo;

    /**
     * 排序方式 1横向 2纵向
     */
    private int sortMode;

    /**
     * 跳转链接
     */
    private String openUrl = "";
}
