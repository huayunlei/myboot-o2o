/**
 * 
 */
package com.ihomefnt.o2o.intf.service.suit;

import com.ihomefnt.o2o.intf.domain.suit.dto.*;

import java.util.List;

/**
 * @author weitichao
 *
 */
public interface WpfSuitService {
	
	/**
     * 获取全品家列表.
     * @return
     */
    List<TWpfSuit> getWpfSuitList();
    
    /**
     * 获取全品家案例列表
     * @return
     */
    List<WpfCaseItem> getWpfCaseList();

    /**
     * 获取全品家套装详情.
     * @param httpWpfSuitRequest
     * @return
     */
    TWpfSuit getWpfSuitDetail(HttpWpfSuitRequest httpWpfSuitRequest);

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
     * 预约全品家相关服务.
     * @param httpWpfServiceResquest
     * @return
     */
    int applyWpfService(HttpWpfServiceRequest httpWpfServiceResquest);
    
    /**
     * 处理H5预约服务
     */
    String setWpfAppointment(HttpWpfAppointmentRequest request);
	
    /**
     * 发送全品家下单邮件
    * @param @param request
    * @param @return
    * @return boolean 
    * @author Charl
     */
    boolean submitWpfOrder(HttpWpfSubmitOrderRequest request);
}
