package com.ihomefnt.o2o.intf.domain.life.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/9/28 0028.
 */
@Data
public class LifeHomePage implements Serializable {
	private static final long serialVersionUID = -678456483725193993L;

	/**
     * 类目列表
     */
    private List<CategoryDto> categoryList;

    /**
     * banner区列表
     */
    private List<BannerDto> bannerList;

    /**
     * 推荐文章列表
     */
    private List<ArticleDto> recommendList;

    /**
     * 通用文章列表
     */
    private List<CommonListDto> commonArticleList;
}
