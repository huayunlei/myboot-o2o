package com.ihomefnt.o2o.intf.proxy.designdemand;

import com.ihomefnt.o2o.intf.domain.agent.dto.PageModel;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.CommitDesignDemandVo;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.DnaRoomInfoResponse;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.AppSolutionDesignResponseVo;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.DnaInfoResponseVo;

import java.util.List;
import java.util.Map;

/**
 * 个性化需求
 * Author: ZHAO
 * Date: 2018年5月26日
 */
public interface PersonalNeedProxy {
    /**
     * 查询用户是否可以提需求
     *
     * @return Author: ZHAO
     * Date: 2018年5月25日
     */
    boolean checkUserDemond(Map<String, Object> params);

    /**
     * 提交方案设计需求
     *
     * @param params
     * @return Author: ZHAO
     * Date: 2018年5月26日
     */
    CommitDesignDemandVo commitDesignDemand(Map<String, Object> params);

    /**
     * 查询最新一次选的风格
     *
     * @param params
     * @return Author: ZHAO
     * Date: 2018年5月26日
     */
    AppSolutionDesignResponseVo queryDesignDemond(Map<String, Object> params);

    /**
     * 查询DNA集合信息（每次随机生成dnaAmount个
     *
     * @return Author: ZHAO
     * Date: 2018年5月26日
     */
    List<DnaInfoResponseVo> randomQueryDna(Integer dnaAmount);

    /**
     * 查询用户选风格的历史记录
     *
     * @return
     */
    List<AppSolutionDesignResponseVo> queryDesignDemondHistory(Map<String, Object> params);

    PageModel<DnaRoomInfoResponse> queryDnaRoomByUsageId(Map<String, Object> params);

    /**
     * 根据订单号集合查询最新设计任务列表
     *
     * @param orderIdList
     * @return
     */
    List<AppSolutionDesignResponseVo> queryDesignDemondForOrderList(List<Integer> orderIdList);

    /**
     * 小艾发起设计任务
     *
     * @param params
     * @return
     */
    CommitDesignDemandVo commitDesignDemandByCustomerService(Map<String, Object> params);
}
