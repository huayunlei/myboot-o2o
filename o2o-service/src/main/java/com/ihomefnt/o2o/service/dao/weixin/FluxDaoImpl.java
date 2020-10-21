/**
 * 
 */
package com.ihomefnt.o2o.service.dao.weixin;

import com.ihomefnt.o2o.intf.dao.weixin.FluxDao;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FLuxAccessToken;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FluxActivity;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FluxLogDto;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FluxUser;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zhang
 *
 */
@Repository
public class FluxDaoImpl implements FluxDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	private static final String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.weixin.FluxDao.";

	@Override
	public FluxActivity queryActivityByPK(Long activityId) {
		return this.sqlSessionTemplate.selectOne(NAME_SPACE	+ "queryActivityByPK", activityId);
	}

	@Override
	public int queryLogByConditon(Map<String, Object> paramMap) {
		return this.sqlSessionTemplate.selectOne(NAME_SPACE	+ "queryLogByConditon", paramMap);
	}

	@Override
	public int acceptFlux(FluxLogDto dto) {
		return this.sqlSessionTemplate.insert(NAME_SPACE + "acceptFlux", dto);
	}

	@Override
	public FLuxAccessToken getFLuxAccessToken() {
		return this.sqlSessionTemplate.selectOne(NAME_SPACE	+ "getFLuxAccessToken");
	}

	@Override
	public int insertFLuxAccessToken(FLuxAccessToken token) {
		return this.sqlSessionTemplate.insert(NAME_SPACE + "insertFLuxAccessToken", token);
	}

	@Override
	public List<FluxUser> getFluxUserList(Map<String, Object> paramMap) {		
		return this.sqlSessionTemplate.selectList(NAME_SPACE + "getFluxUserList", paramMap);
	}

	@Override
	public int insertFluxUserList(List<FluxUser> list) {		
		return this.sqlSessionTemplate.insert(NAME_SPACE + "insertFluxUserList", list);
	}

	@Override
	public int UpdateFluxUser(Map<String, Object> paramMap) {
		return this.sqlSessionTemplate.update(NAME_SPACE + "UpdateFluxUser", paramMap);
	}

}
