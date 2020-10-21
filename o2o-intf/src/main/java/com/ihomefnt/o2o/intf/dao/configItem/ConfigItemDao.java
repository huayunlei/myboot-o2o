package com.ihomefnt.o2o.intf.dao.configItem;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.configItem.dto.ConfigItem;
import com.ihomefnt.o2o.intf.domain.configItem.dto.Item;

public interface ConfigItemDao {
	 /**
     * 根据id查找配置项
     * @param id
     * @return
     */
    ConfigItem findConfigItemById(Long id);
    /**
     * 根据configName查找配置项
     * @param configName
     * @return
     */
    ConfigItem findConfigItemByConfigName(String configName);
    /**
	 * 根据配置项查找具体的项
	 * @param configId
	 * @return
	 */
	public List<Item> queryItemByConfigId(Long configId);
}
