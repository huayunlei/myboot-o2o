/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.inspiration.vo.response;

import com.ihomefnt.o2o.intf.domain.inspiration.dto.Article270;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Picture270;
import lombok.Data;

import java.util.List;

/**
 * @author zhang
 *
 */
@Data
public class HttpInspirationResponse270 {

	private List<Article270> firstList;// 两篇文章：只有当pageNo=1,才有值。

	private List<Picture270> middleList;// 最近上传传的3张图：只有当pageNo=1,才有值。

	private List<Article270> lastList;// 除去前面两篇文章以外的文章，每次显示根据pageNo和pageSize决定
}
