package com.ihomefnt.o2o.intf.proxy.home;

import com.ihomefnt.o2o.intf.domain.homebuild.dto.AppHousePropertyResultDto;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.ProjectResponse;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.SpecificUserDecisionResultDto;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DNABaseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DNAInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DNARoomAndItemVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.SolutionInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.ApartmentInfoVo;
import com.ihomefnt.o2o.intf.domain.homecard.vo.response.ApartmentRoomVo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BasePropertyResponseVo;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * APP3.0新版首页Boss服务代理接口DAO层
 * http://192.168.1.11:10007/boss-api/swagger
 * 产品中心
 * @author ZHAO
 */
public interface HomeCardBossProxy {
	
	/**
	 * 查询用户的前4套方案信息
	 * @param userId
	 * @param houseProjectId 楼盘项目id
	 * @param houseTypeId 户型id
	 * @param queryResultCount 查询条数限制
	 * @return
	 */
	SolutionInfoResponseVo getUserSpecificProgram(Integer userId, Integer houseProjectId, Integer houseTypeId, Integer queryResultCount);
	
	/**
	 * 查询筛选条件（风格、空间、套系）
	 * @return
	 */
	List<BasePropertyResponseVo> getProductFilterInfo();
	
	/**
	 * 根据条件查询DNA列表信息
	 * @param style 风格
	 * @param styleIdList 风格
	 * @param roomUseId 空间用途
	 * @param seriesId 价格套系
	 * @param pageNum 第几页
	 * @param pageSize 每页显示条数
	 * @return
	 */
	DNABaseInfoResponseVo getProductByCondition(Integer userId, Integer style, List<Integer> styleIdList, Integer roomUseId, Integer seriesId, Integer pageNum, Integer pageSize);
	
	/**
	 * 根据产品ID查询DNA详情
	 * @param dnaId 
	 * @return
	 */
	DNAInfoResponseVo getDnaDetailById(Integer dnaId);
	
	/**
	 * 根据产品ID查询软装清单
	 * @param dnaId 
	 * @return
	 */
	List<DNARoomAndItemVo> getSoftListByCondition(Integer dnaId );

	List<ApartmentRoomVo> getHouseInfoByLayoutId(Integer layoutId);

	ApartmentInfoVo getHouseInfoByApartmentId(Map map);

	ApartmentInfoVo getHouseInfoByApartmentIdNew(Map map);

	ProjectResponse queryBuildingDetail(Integer buildingId);

	SpecificUserDecisionResultDto queryUserHouseSpecific(Integer userId, Set<Integer> houseIdSet);

	AppHousePropertyResultDto queryHouseDetail(Integer customerHouseId);

}
