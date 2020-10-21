package com.ihomefnt.o2o.service.service.meeting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.service.meeting.MeetingMsgService;
import com.ihomefnt.o2o.intf.domain.meeting.dto.AddMessageDto;
import com.ihomefnt.o2o.intf.domain.meeting.dto.QueryMessageDto;
import com.ihomefnt.o2o.intf.domain.meeting.dto.QueryMessageListDto;
import com.ihomefnt.o2o.intf.proxy.meeting.MeetingMsgProxy;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.AddMessageRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.QueryMessageRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.AddMessageResponse;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.QueryMessageResponse;
import com.ihomefnt.oms.trade.util.PageModel;

@Service
public class MeetingMsgServiceImpl implements MeetingMsgService {

	@Autowired
	private MeetingMsgProxy meetingMsgProxy;

	@Override
	public AddMessageResponse publishMsg(AddMessageRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberId", request.getMemberId());
		params.put("content", request.getContent());

		AddMessageResponse response = new AddMessageResponse();
		AddMessageDto addMsgDto = meetingMsgProxy.publishMsg(params);
		if (addMsgDto != null) {
			if (StringUtils.isNotBlank(addMsgDto.getMsgId())) {
				response.setMsgId(addMsgDto.getMsgId());
			}
		}
		return response;
	}

	@Override
	public PageModel queryMsg(QueryMessageRequest request) {

		PageModel pageModel = new PageModel();
		List<QueryMessageResponse> queryMessageResponses = new ArrayList<QueryMessageResponse>();

		int pageNo = 1;
		int pageSize = 10;

		if (request.getPageNo() != null && request.getPageNo() > 0) {
			pageNo = request.getPageNo();
		}
		if (request.getPageSize() != null && request.getPageSize() > 0) {
			pageSize = request.getPageSize();
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);

		QueryMessageListDto queryMessageListDto = meetingMsgProxy.queryMsg(params);

		if (queryMessageListDto != null) {
			List<QueryMessageDto> queryMessageDtos = queryMessageListDto.getList();
			for (QueryMessageDto queryMessageDto : queryMessageDtos) {
				QueryMessageResponse queryMessageResponse = new QueryMessageResponse();
				queryMessageResponse.setContent(queryMessageDto.getContent());
				queryMessageResponse.setMsgTime(queryMessageDto.getMsgTime());
				queryMessageResponse.setNickName(queryMessageDto.getNickName());
				queryMessageResponse.setUrl(queryMessageDto.getUrl());
				queryMessageResponses.add(queryMessageResponse);
			}

			pageModel.setTotalPages(queryMessageListDto.getTotalPage());
			pageModel.setTotalRecords(queryMessageListDto.getTotalCount());
		} else {
			pageModel.setTotalPages(1);
			pageModel.setTotalRecords(0);
		}

		pageModel.setList(queryMessageResponses);
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);

		return pageModel;
	}
}
