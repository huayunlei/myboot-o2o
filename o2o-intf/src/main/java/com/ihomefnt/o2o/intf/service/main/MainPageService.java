package com.ihomefnt.o2o.intf.service.main;

import com.ihomefnt.o2o.intf.domain.main.vo.request.MainFrameRequest;
import com.ihomefnt.o2o.intf.domain.main.vo.request.NodeContentRequest;
import com.ihomefnt.o2o.intf.domain.main.vo.response.ContentResponse;
import com.ihomefnt.o2o.intf.domain.main.vo.response.MainPageNewResponse;
import com.ihomefnt.o2o.intf.domain.main.vo.response.MainPageResponse;

/**
 * @author xiamingyu
 * @date 2019/3/20
 */

public interface MainPageService {


    /**
     * 获取APP首页数据
     * @param MainFrameRequest
     * @return
     */
    MainPageResponse getMainFrameData(MainFrameRequest request);


    /**
     * 刷新节点内容信息
     * @param request
     * @return
     */
    ContentResponse getNodeContent(NodeContentRequest request);
}
