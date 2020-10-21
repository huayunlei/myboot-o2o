package com.ihomefnt.o2o.intf.domain.inspiration.vo.response;

import com.ihomefnt.o2o.intf.domain.inspiration.dto.PictureAlbum;
import com.ihomefnt.o2o.intf.domain.product.doo.TreeNode;
import lombok.Data;

import java.util.List;
@Data
public class HttpPictureListResponse {
	
	private List<PictureAlbum> pictureList;
    private List<TreeNode> classifyTreeList;
    private int totalRecords;
    private int totalPages;
}
