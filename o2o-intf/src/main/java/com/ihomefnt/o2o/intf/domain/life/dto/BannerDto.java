package com.ihomefnt.o2o.intf.domain.life.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/29 0029.
 */
@Data
public class BannerDto implements Serializable {

    private static final long serialVersionUID = -4133995816088921233L;
    private int bannerId;

    private int articleId;

    private ArticleDto article;
    /**
     * 排序号1-5 默认为3
     */
    private int sortNo = 3;
    /**
     * banner类型 1.已发布内容 2.其他页面url
     */
    private int type;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面
     */
    private String coverUrl;
    /**
     * 页面url
     */
    private String pageUrl;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
}
