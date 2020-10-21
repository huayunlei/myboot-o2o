package com.ihomefnt.o2o.intf.service.home;

import com.ihomefnt.o2o.intf.domain.bundle.vo.request.VersionBundleRequestVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.HomeFrameRequest;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.QueryDraftRequest;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.UserCommentRequest;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.AboutUsResponse;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.HomeFrameResponse;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.SolutionDraftResponse;
import com.ihomefnt.o2o.intf.domain.program.vo.response.DraftInfoResponse;
import com.ihomefnt.o2o.intf.domain.programorder.vo.request.*;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.CreateOrderResponse;
import com.ihomefnt.oms.trade.util.PageModel;

import javax.servlet.http.HttpServletRequest;

/**
 * APP5.0首页
 * Author: ZHAO
 * Date: 2018年7月19日
 */
public interface HomeV5PageService {
    /**
     * 首页节点数据
     *
     * @param request
     * @return Author: ZHAO
     * Date: 2018年7月19日
     */
    HomeFrameResponse getHomePageData(HomeFrameRequest request);

    /**
     * 首页关于我们
     *
     * @param request
     * @return Author: ZHAO
     * Date: 2018年7月20日
     */
    AboutUsResponse getAboutUsInfo(VersionBundleRequestVo request);

    /**
     * 查询用户评论（订单）
     *
     * @return Author: ZHAO
     * Date: 2018年7月20日
     */
    PageModel getUserCommentList(UserCommentRequest request);

    /**
     * 查询方案草稿
     *
     * @param request
     * @return Author: ZHAO
     * Date: 2018年7月22日
     */
    SolutionDraftResponse querySolutionDraft(QueryDraftRequest request);

    /**
     * 新增方案草稿
     *
     * @param request
     * @return Author: ZHAO
     * Date: 2018年7月22日
     */
    String createSolutionCraft(CreateDraftRequest request);

    /**
     * 删除方案草稿
     *
     * @param request
     * @return Author: ZHAO
     * Date: 2018年8月3日
     */
    Boolean deleteSolutionDraft(QueryDraftRequest request);

    /**
     * 保存或更新草稿
     *
     * @param request
     * @return
     */
    String addOrUpdateDraft(DraftInfoRequest request);

    /**
     * 查询草稿
     *
     * @param request
     * @return
     */
    DraftInfoResponse queryDraftInfo(QueryDraftRequest request);

    /**
     * 草稿箱信息查询
     *
     * @param request
     * @return
     */
    DraftSimpleRequestPage queryDraftList(QueryDraftRequest request);

    /**
     * 订单价格和草稿价格对比
     *
     * @param request
     * @return
     */
    boolean compareOrderToDraft(QueryDraftRequest request);

    /**
     * 根据草稿id下单
     *
     * @param request
     * @param req
     * @return
     */
    CreateOrderResponse createFamilyOrderByDraft(FamilyOrderRequest request, HttpServletRequest req);

    /**
     * 创建全品家订单统一处理
     *
     * @param userDto
     * @param request
     * @param flag
     * @param req
     * @return
     */
    CreateOrderResponse createFamilyOrder(HttpUserInfoRequest userDto, FamilyOrderRequest request, HttpServletRequest req, Integer flag);

    /**
     * 查询下单参数
     * @param request
     * @return
     */
    CreateFamilyOrderRequest queryOrderParam(FamilyOrderRequest request);

    /**
     * 创建草稿并下单
     * @param request
     * @return
     */
    CreateOrderResponse createDraftAndOrder(CreateOrderAndDraftRequest request);

    /**
     * 组装下单参数
     * @param orderRequest
     * @param draftInfoResponse
     */
    void setOrderRequest(CreateFamilyOrderRequest orderRequest, DraftInfoResponse draftInfoResponse);

    /**
     * 逻辑删除草稿
     * @param deleteDraftRequest
     * @return
     */
    boolean deleteDraft(DeleteDraftRequest deleteDraftRequest);
}
