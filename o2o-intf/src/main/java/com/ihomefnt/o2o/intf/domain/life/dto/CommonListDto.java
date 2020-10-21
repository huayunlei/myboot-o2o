package com.ihomefnt.o2o.intf.domain.life.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/9/30 0030.
 */
@Data
public class CommonListDto implements Serializable {
    private static final long serialVersionUID = 8893904487402589060L;
    private Integer categoryId;
    private String  categoryName;
    private List<ArticleDto> articleList;
    /**
     * 排序方式 1横向 2纵向
     */
    private int sortMode;

    /**
     * 跳转链接
     */
    private String openUrl = "";
}
