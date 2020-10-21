package com.ihomefnt.o2o.service.service.staticdata;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.staticdata.vo.response.AboutAiJiaDataResponse;
import com.ihomefnt.o2o.intf.domain.staticdata.vo.response.FamilyOrderServiceProcessDataResponse;
import com.ihomefnt.o2o.intf.domain.staticdata.vo.response.ProductServiceDataResponse;
import com.ihomefnt.o2o.intf.domain.staticdata.vo.response.UserStoryDataResponse;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.service.staticdata.StaticPageDataService;
import org.springframework.stereotype.Service;

/**
 * 静态数据Service
 *
 * @author liyonggang
 * @create 2019-11-15 11:23
 */
@Service
public class StaticPageDataServiceImpl implements StaticPageDataService {

    @NacosValue(value = "${FAMILY_ORDER_SERVICE_PROCESS_DATA}", autoRefreshed = true)
    private String FAMILY_ORDER_SERVICE_PROCESS_DATA;

    @NacosValue(value = "${USER_STORY_DATA}", autoRefreshed = true)
    private String USER_STORY_DATA;

    @NacosValue(value = "${ABOUT_AIJIA_DATA}", autoRefreshed = true)
    private String ABOUT_AIJIA_DATA;

    @NacosValue(value = "${PRODUCT_SERVICE_DATA}", autoRefreshed = true)
    private String PRODUCT_SERVICE_DATA;

    /**
     * 全品家服务流程
     *
     * @param request
     * @return
     */
    @Override
    public FamilyOrderServiceProcessDataResponse getFamilyOrderServiceProcessData(HttpBaseRequest request) {
        return JSON.parseObject(FAMILY_ORDER_SERVICE_PROCESS_DATA, FamilyOrderServiceProcessDataResponse.class);
    }

    /**
     * 用户故事数据获取
     *
     * @param request
     * @return
     */
    @Override
    public UserStoryDataResponse getUserStoryData(HttpBaseRequest request) {
        UserStoryDataResponse userStoryDataResponse = JSON.parseObject(USER_STORY_DATA, UserStoryDataResponse.class);
        userStoryDataResponse.getResourceList().forEach(staticResourceDto -> staticResourceDto.setCoverImage(AliImageUtil.imageCompress(staticResourceDto.getCoverImage(),1,750, ImageConstant.SIZE_MIDDLE)));
        return userStoryDataResponse;
    }

    /**
     * 关于艾佳数据获取
     *
     * @param request
     * @return
     */
    @Override
    public AboutAiJiaDataResponse getAboutAiJiaData(HttpBaseRequest request) {
        return JSON.parseObject(ABOUT_AIJIA_DATA, AboutAiJiaDataResponse.class);
    }

    /**
     * 产品服务数据获取
     *
     * @param request
     * @return
     */
    @Override
    public ProductServiceDataResponse getProductServiceData(HttpBaseRequest request) {
        return JSON.parseObject(PRODUCT_SERVICE_DATA, ProductServiceDataResponse.class);
    }
}
