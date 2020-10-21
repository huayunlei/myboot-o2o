package com.ihomefnt.o2o.intf.domain.designer.vo.response;

import com.ihomefnt.o2o.intf.domain.inspiration.dto.WeiXinArticle;
import lombok.Data;

import java.util.List;



@Data
public class DesignHomeResponse {
    private DesignerPicsResponse designerPicsResponse;
    private List<WeiXinArticle> articles;
}
