package com.ihomefnt.o2o.service.service.hbms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.o2o.intf.domain.hbms.dto.CommentParamDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.ConfirmNodeParamDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetCommentParamDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetCommentResultDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetNodeItemsParamDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetNodeItemsResultDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetSurveyorProjectNodeDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetUnhandleProjectResultDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.NeedConfirmItemsDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.OwnerParamDto;
import com.ihomefnt.o2o.intf.domain.hbms.vo.request.CommentRequestVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.request.ConfirmNodeRequestVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.request.GetCommentRequestVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.request.GetNodeItemsRequestVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.request.OwnerParamRequestVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.response.GetCommentResponseVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.response.GetNodeItemsReponseVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.response.GetSurveyorProjectNodeResponseVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.response.GetUnhandleProjectResultResponseVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.response.TimeChangeItemResponseVo;
import com.ihomefnt.o2o.intf.domain.hbms.vo.response.TimeChangeResponseVo;
import com.ihomefnt.o2o.intf.proxy.hbms.OwnerProxy;
import com.ihomefnt.o2o.intf.service.hbms.HbmsService;
import com.ihomefnt.o2o.intf.manager.util.common.secure.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/9/27
 */

@Service
public class HbmsServiceImpl implements HbmsService {

    @Autowired
    private OwnerProxy ownerProxy;

    @Override
    public TimeChangeResponseVo queryNeedConfirmItem(Integer orderId) {
        List<NeedConfirmItemsDto> itemsVos = ownerProxy.queryNeedConfirmItem(orderId);
        TimeChangeResponseVo response = new TimeChangeResponseVo();
        List<TimeChangeItemResponseVo> responseItemList = new ArrayList<>();
        Integer dayAdded = 0;
        if(itemsVos==null) {
            return null;
        }
        for (NeedConfirmItemsDto vo : itemsVos){
            if(StringUtils.isEmpty(response.getAdviserMobile())){
                response.setAdviserMobile(vo.getPhone());
            }
            if(vo.getStandardDays()>dayAdded){
                dayAdded = vo.getStandardDays();
            }
            TimeChangeItemResponseVo item = new TimeChangeItemResponseVo().setItemId(vo.getId()).setItemName(vo.getName());
            responseItemList.add(item);
        }
        response.setAddedDate(dayAdded);
        response.setItemList(responseItemList);
        return response;
    }

    @Override
    public Integer confirmTimeChange(Integer orderId) {
        return ownerProxy.confirmTimeChange(orderId);
    }

	@Override
	public boolean confirmNode(ConfirmNodeRequestVo request) {
		ConfirmNodeParamDto param = ModelMapperUtil.strictMap(request, ConfirmNodeParamDto.class);
		return ownerProxy.confirmNode(param);
	}

	@Override
	public GetNodeItemsReponseVo getNodeItems(GetNodeItemsRequestVo request) {
		GetNodeItemsParamDto param = ModelMapperUtil.strictMap(request, GetNodeItemsParamDto.class);
		GetNodeItemsResultDto result = ownerProxy.getNodeItems(param);
		if (null == result) {
			return null;
		}
		return ModelMapperUtil.strictMap(result, GetNodeItemsReponseVo.class);
	}

	@Override
	public boolean comment(CommentRequestVo request) {
		CommentParamDto param = ModelMapperUtil.strictMap(request, CommentParamDto.class);
		return ownerProxy.comment(param);
	}

	@Override
	public GetCommentResponseVo getComment(GetCommentRequestVo request) {
		GetCommentParamDto param = ModelMapperUtil.strictMap(request, GetCommentParamDto.class);
		GetCommentResultDto result = ownerProxy.getComment(param);
		if (null == result) {
			return null;
		}
		return ModelMapperUtil.strictMap(result, GetCommentResponseVo.class);
	}

	@Override
	public List<GetSurveyorProjectNodeResponseVo> getProjectCraft(OwnerParamRequestVo request) {
		OwnerParamDto param = ModelMapperUtil.strictMap(request, OwnerParamDto.class);
		List<GetSurveyorProjectNodeDto> result = ownerProxy.getProjectCraft(param);
		if (null == result) {
			return null;
		}
		return ModelMapperUtil.strictMapList(result, GetSurveyorProjectNodeResponseVo.class);
	}

	@Override
	public GetUnhandleProjectResultResponseVo getUnhandleProject(OwnerParamRequestVo request) {
		OwnerParamDto param = ModelMapperUtil.strictMap(request, OwnerParamDto.class);
		GetUnhandleProjectResultDto result = ownerProxy.getUnhandleProject(param);
		if (null == result) {
			return null;
		}
		return ModelMapperUtil.strictMap(result, GetUnhandleProjectResultResponseVo.class);
	}
}
