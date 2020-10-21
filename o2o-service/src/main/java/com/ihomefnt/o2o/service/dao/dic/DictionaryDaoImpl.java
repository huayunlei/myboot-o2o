/**
 * 
 */
package com.ihomefnt.o2o.service.dao.dic;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.dic.DictionaryDao;

/**
 * @author zhang
 *
 */
@Repository
public class DictionaryDaoImpl implements DictionaryDao {
	
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    private static final String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.dic.DictionaryDao.";

	/* (non-Javadoc)
	 */
	@Override
	public String getValueByKey(String key) {
		return sqlSessionTemplate.selectOne(NAME_SPACE+ "getValueByKey", key);
	}

}
