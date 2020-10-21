package com.ihomefnt.o2o.intf.domain.life.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/28 0028.
 */
@Data
public class AuthorDto implements Serializable {
    private static final long serialVersionUID = -5910104207747737728L;
    /**
     * 作者ID
     */
    private int authorId;

    /**
     * 笔名
     */
    private String penName;

    /**
     * 作者姓名
     */
    private String authorName;

    /**
     * 作者简介
     */
    private String introduce;

    /**
     * 头像图片
     */
    private String headUrl;
}
