package com.ihomefnt.o2o.intf.service.staticdata;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.staticdata.vo.response.AboutAiJiaDataResponse;
import com.ihomefnt.o2o.intf.domain.staticdata.vo.response.FamilyOrderServiceProcessDataResponse;
import com.ihomefnt.o2o.intf.domain.staticdata.vo.response.ProductServiceDataResponse;
import com.ihomefnt.o2o.intf.domain.staticdata.vo.response.UserStoryDataResponse;

/**
 * 静态数据Service
 *
 * @author liyonggang
 * @create 2019-11-15 11:22
 */
public interface StaticPageDataService {

    /**
     * 全品家服务流程
     *
     * @param request
     * @return
     */
    FamilyOrderServiceProcessDataResponse getFamilyOrderServiceProcessData(HttpBaseRequest request);

    /**
     * 用户故事数据获取
     *
     * @param request
     * @return
     */
    UserStoryDataResponse getUserStoryData(HttpBaseRequest request);

    /**
     * 关于艾佳数据获取
     *
     * @param request
     * @return
     */
    AboutAiJiaDataResponse getAboutAiJiaData(HttpBaseRequest request);

    /**
     * 产品服务数据获取
     *
     * @param request
     * @return
     */
    ProductServiceDataResponse getProductServiceData(HttpBaseRequest request);
}
