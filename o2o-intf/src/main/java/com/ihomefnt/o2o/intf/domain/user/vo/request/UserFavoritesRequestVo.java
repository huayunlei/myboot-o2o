package com.ihomefnt.o2o.intf.domain.user.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by piweiwen on 15-1-20.
 */
@Data
public class UserFavoritesRequestVo extends HttpBaseRequest{
	
	private Long type;
	private int inspirationFlag;//1:灵感0：非灵感
	private int pageNo;
	private int pageSize;
	private int needPicWidt;
}
