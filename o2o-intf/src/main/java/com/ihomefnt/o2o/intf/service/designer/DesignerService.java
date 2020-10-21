package com.ihomefnt.o2o.intf.service.designer;

import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerModel;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerPic;
import com.ihomefnt.o2o.intf.domain.designer.vo.request.DesignerPicBrowseRequest;
import com.ihomefnt.o2o.intf.domain.designer.vo.request.DesignerPicRequest;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerPicture;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerSuit;

import java.util.List;
import java.util.Map;

public interface DesignerService {
    /**
     * 瀑布流形式
     *
     * @param map
     * @return
     */
    List<DesignerPic> queryDesignerPic(Map<String, Object> map,
                                       Integer screenWidth);

    List<DesignerPic> queryDesignerPicsById(
            DesignerPicRequest designerPicRequest, Integer screenWidth);

    List<DesignerPic> queryDesignerPic(DesignerPicBrowseRequest designerPicRes,
                                       Integer screenWidth);

    DesignerModel loadDesignerInfo(String mobile);

    Integer loadDesignerSuitCount(Long designerId);

    Integer loadDesignerPicCount(Long designerId);

    String loadDesignerSuitImg(Long designerId);

    List<DesignerSuit> loadDesignerSuit(Long designerId, Integer pageNo, Integer pageSize);

    List<DesignerPicture> loadDesignerPicList(Long designerId, Integer pageNo, Integer pageSize);

    void resizeDesignerPicture(List<DesignerPicture> pictureList, Integer screenWidth);

}
