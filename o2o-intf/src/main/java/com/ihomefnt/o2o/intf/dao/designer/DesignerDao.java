package com.ihomefnt.o2o.intf.dao.designer;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerModel;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerPic;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerPicture;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerSuit;

public interface DesignerDao {
    /**
     * 瀑布流形式
     *
     * @param map
     * @return
     */
    List<DesignerPic> queryDesignerPic(Map<String, Object> map);

    DesignerPic queryDesignerPicById(Long id);

    /**
     * 旧 上一张
     *
     * @param map
     * @return
     */
    List<DesignerPic> queryDesignerPicShiftLeft(Map<String, Object> map);

    /**
     * 新 下一张
     *
     * @param map
     * @return
     */
    List<DesignerPic> queryDesignerPicShiftRight(Map<String, Object> map);

    DesignerModel loadDesignerInfo(String mobile);

    Integer loadDesignerSuitCount(Long designerId);

    Integer loadDesignerPicCount(Long designerId);

    String loadDesignerSuitImg(Long designerId);

    List<DesignerSuit> loadDesignerSuitList(Long designerId, Integer from, Integer size);

    List<DesignerPicture> loadDesignerPicList(Long designerId, Integer from, Integer size);

    DesignerModel loadDesignerInfoById(Long designerId);
}
