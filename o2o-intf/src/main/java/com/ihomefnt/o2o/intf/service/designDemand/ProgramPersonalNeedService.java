package com.ihomefnt.o2o.intf.service.designDemand;

import com.ihomefnt.o2o.intf.domain.personalneed.dto.CommitDesignDemandVo;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.*;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.*;

import java.util.List;

/**
 * 用户个性化需求
 * Author: ZHAO
 * Date: 2018年5月25日
 */
public interface ProgramPersonalNeedService {
	/**
	 * 查询用户是否可以提需求
	 * @param orderId
	 * @return
	 * Author: ZHAO
	 * Date: 2018年5月25日
	 */
	boolean checkUserDemond(Integer orderId,Integer userId);
	
	/**
	 * 提交方案设计需求
	 * @param request
	 * @param userId
	 * @return
	 * Author: ZHAO
	 * Date: 2018年5月25日
	 */
	CommitDesignDemandVo commitDesignDemand(CommitDesignRequest request, Integer userId);
	
	/**
	 * 查询设计需求
	 * @param orderId
	 * @param width
	 * @return
	 * Author: ZHAO
	 * Date: 2018年5月26日
	 */
	PersonalDesignResponse queryDesignDemond(Integer orderId, Integer width, String mobile,Integer osType);
	
	/**
	 * 查询DNA集合信息
	 * @param width
	 * @return
	 * Author: ZHAO
	 * Date: 2018年5月26日
	 */
	List<PersonalArtisticResponse> randomQueryDna(Integer width);
	
	/**
	 * 查询家庭个性标签集合
	 * @return
	 * Author: ZHAO
	 * Date: 2018年5月26日
	 */
	List<String> queryPersonalTagList();

	/**
	 * 查询选风格记录
	 * @param request
	 * @param userId
	 * @return
	 */
	StyleRecordResponse queryStyleRecord(StyleRecordRequest request,Integer userId);

	/**筛选dna空间
	 * @param request
	 * @return
	 */
	List<FilterQueryDnaRoomResponse> filterDnaRoomByPurpose(FilterDnaRoomReq request);

	/**分页查询dna空间信息
	 * @param request
	 * @return
	 */
	DnaRoomListResponse queryDnaRoomByPurposeId(QueryDnaRoomRequest request);

	Integer transferDesignStatus(Integer taskStatus);

	QueryDesignStatusResponse queryDesignStatus(QueryDesignStatusRequest request);

}
