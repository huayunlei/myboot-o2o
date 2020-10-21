package com.ihomefnt.o2o.intf.service.hbms;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.hbms.vo.request.CommentRequestVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.request.ConfirmNodeRequestVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.request.GetCommentRequestVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.request.GetNodeItemsRequestVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.request.OwnerParamRequestVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.response.GetCommentResponseVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.response.GetNodeItemsReponseVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.response.GetSurveyorProjectNodeResponseVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.response.GetUnhandleProjectResultResponseVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.response.TimeChangeResponseVo;


/**
 * @author xiamingyu
 * @date 2018/9/27
 */

public interface HbmsService {


    /**
     * 查询用户待确认的自定义项
     * @param orderId
     * @return
     */
    TimeChangeResponseVo queryNeedConfirmItem(Integer orderId);

    /**
     * 用户确认工期变更
     * @param orderId
     * @return
     */
    Integer confirmTimeChange(Integer orderId);

	boolean confirmNode(ConfirmNodeRequestVo request);

	GetNodeItemsReponseVo getNodeItems(GetNodeItemsRequestVo request);

	boolean comment(CommentRequestVo request);

	GetCommentResponseVo getComment(GetCommentRequestVo request);

	List<GetSurveyorProjectNodeResponseVo> getProjectCraft(OwnerParamRequestVo request);

	GetUnhandleProjectResultResponseVo getUnhandleProject(OwnerParamRequestVo request);

}
