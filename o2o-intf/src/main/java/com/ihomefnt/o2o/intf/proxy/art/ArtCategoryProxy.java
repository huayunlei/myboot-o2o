package com.ihomefnt.o2o.intf.proxy.art;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.art.dto.ArtSubjectListResponseVo;
import com.ihomefnt.o2o.intf.domain.art.dto.FrameArtResponseVo;
import com.ihomefnt.o2o.intf.domain.art.dto.FrameCategoryResponseVo;
import com.ihomefnt.o2o.intf.domain.art.dto.SubjectProductResponseVo;

/**
 * 艺术品品类代理类
 * @author ZHAO
 */
public interface ArtCategoryProxy {
	/**
	 * 根据样式ID查询艺术品列表
	 * @param frameId
	 * @return
	 */
	FrameArtResponseVo queryFrameCategoryListByFrameId(Integer frameId);
	
	/**
	 * 根据样式ID集合查询艺术品列表
	 * @param frameId
	 * @return
	 */
	FrameArtResponseVo queryListByFrameIdList(List<Integer> frameIdList);
	
	/**
	 * 查询所有样式
	 * @return
	 */
	FrameCategoryResponseVo queryAllFrameList();
	
	/**
	 * 查询艺术品专题
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	ArtSubjectListResponseVo getArtSubjectList(Integer pageNo, Integer pageSize);
	
	/**
	 * 根据专题ID查询专题详情
	 * @param subjectId
	 * @return
	 */
	SubjectProductResponseVo querySubjectDetailById(Integer subjectId);

}
