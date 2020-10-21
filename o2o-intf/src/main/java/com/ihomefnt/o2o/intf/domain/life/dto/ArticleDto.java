package com.ihomefnt.o2o.intf.domain.life.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/28 0028.
 */
@Data
public class ArticleDto implements Serializable {
    private static final long serialVersionUID = -1780115599036051659L;
    /**
     * 文章ID
     */
    private Integer articleId;

    /**
     * 类目ID
     */
    private Integer categoryId;
    /**
     * 标题
     */
    private String title;

    /**
     * 作者ID
     */
    private AuthorDto author;

    /**
     * 封面图片
     */
    private String coverUrl;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 备注
     */
    private String remark;

    /**
     * 文章类型：1.音频图文 2.图文内容
     */
    private int contentType;

    /**
     * 音频文件地址
     */
    private String audioUrl;

    /**
     * 文章正文
     */
    private String content;

    /**
     * 发布状态
     */
    private Integer publishFlag;

    /**
     * 发布时间
     */
    private String statusTime;

    /**
     * 浏览量
     */
    private int  browseCount = 0;

    /**
     * 转发数
     */
    private int forwardCount = 0;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建时间
     */
    private String updateTime;


    /**
     * 点赞数
     */
    private int praiseCount;

    /**
     * 笔名
     */
    private String penName = "";
    /**
     * 作者简介
     */
    private String introduce = "";

    /**
     * 头像图片
     */
    private String headUrl = "";
    /**
     * 评论数量
     */
    private int commentCount = 0;

    /**
     * 类型  0：图片 1：视频
     */
    private Integer type = 0;


    /**
     * 类型 视频地址，视频资源才有
     */
    private String videoUrl;
}
