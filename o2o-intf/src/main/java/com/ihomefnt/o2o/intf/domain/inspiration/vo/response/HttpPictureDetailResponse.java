package com.ihomefnt.o2o.intf.domain.inspiration.vo.response;

import com.ihomefnt.o2o.intf.domain.inspiration.dto.PictureAlbum;
import lombok.Data;

@Data
public class HttpPictureDetailResponse {
	
	private PictureAlbum currentAlbumInfo;
	private long previousAlbumId;
	private long nextAlbumId;
}
