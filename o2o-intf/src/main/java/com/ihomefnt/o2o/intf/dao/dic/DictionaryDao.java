/**
 * 
 */
package com.ihomefnt.o2o.intf.dao.dic;


/**
 * @author zhang
 *
 */
public interface DictionaryDao {
	
	/**
	 * 
	 * 通过键来查询对应的值
	 * @return
	 */
	String getValueByKey(String key);

}
