package com.ihomefnt.o2o.intf.dao.suit;

import com.ihomefnt.o2o.intf.domain.suit.dto.*;

import java.util.List;



public interface WpfSuitDao {

    /**
     * 获取全品家列表.
     * @return
     */
    List<TWpfSuit> getWpfSuitList();

    /**
     * 获取全品家套装详情.
     * @return
     */
    TWpfSuit getWpfSuitDetail(Integer wpfSuitId);

    /**
     * 获取全品家套装的包装清单图片.
     * @param httpWpfSuitRequest
     * @return
     */
    List<TWpfStyleImage> getWpfSuitBomImage(HttpWpfSuitRequest httpWpfSuitRequest);

    /**
     * 获取全品家套装详情底部商品信息.
     * @return
     */
    List<TWpfSuitAd> getWpfSuitAd();

    /**
     * 预约全品家服务信息.
     * @param httpWpfServiceResquest
     * @return
     */
    int applyWpfService(HttpWpfServiceRequest httpWpfServiceResquest);
    
    /**
     * h5全品家预约服务
     */
    int applyH5WpfService(HttpWpfAppointmentRequest request);
    
    /**
     * 根据accessToken获取用户姓名、手机号码、城市信息
     */
    HttpWpfAppointmentRequest queryRequestByAccessToken(String accessToken);
    
    /**
     * 获取全品家套装材料信息
     * @param wpfSuitId 套装id
     * @return 套装材料信息
     */
    List<TWpfMaterial> queryWpfMaterialItems(Integer wpfSuitId);
    
    /**
     * 获取全品家案例列表
     * @return
     */
    List<WpfCaseItem> queryWpfCaseList(List<Long> wpfCaseIdList);

}
