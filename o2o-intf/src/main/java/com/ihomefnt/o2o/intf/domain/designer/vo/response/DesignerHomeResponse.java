package com.ihomefnt.o2o.intf.domain.designer.vo.response;

import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerPicture;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerSuit;
import lombok.Data;

import java.util.List;

/**
 * Created by hvk687 on 10/22/15.
 */
@Data
public class DesignerHomeResponse {
    private String designerName = "";//设计师名字
    private String avatar = "";//头像
    private Integer caseCount;//案例数量
    private Integer picCount;//灵感数量
    private String bgPic = "";//背景图片
    private String concept = "";//设计理念
    private String description = "";//介绍
    private String mobile = "";
    private Long designerId;
    private List<DesignerSuit> suitList; // load one suit
    private List<DesignerPicture> picList;//load three pictures
    
    private boolean enable;
    private String title1;
    private String icon1;
    private String desc;

}
