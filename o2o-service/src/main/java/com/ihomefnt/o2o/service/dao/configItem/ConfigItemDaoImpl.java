package com.ihomefnt.o2o.service.dao.configItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.configItem.ConfigItemDao;
import com.ihomefnt.o2o.intf.domain.configItem.dto.ConfigItem;
import com.ihomefnt.o2o.intf.domain.configItem.dto.Item;

@Repository
public class ConfigItemDaoImpl implements ConfigItemDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	private static String NAME_SPACE = "com.ihomefnt.o2oold.intf.configItem.";

	@Override
	public ConfigItem findConfigItemById(Long configId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("configId", configId);
		return sqlSessionTemplate.selectOne(
				NAME_SPACE + "queryConfigitemtById", paramMap);
	}

	@Override
	public List<Item> queryItemByConfigId(Long configId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("configId", configId);
		return sqlSessionTemplate.selectList(NAME_SPACE
				+ "queryItemsByConfigId", paramMap);
	}

	@Override
	public ConfigItem findConfigItemByConfigName(String configName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("configName", configName);
		return sqlSessionTemplate.selectOne(
				NAME_SPACE + "findConfigItemByConfigName", paramMap);
	}

}
