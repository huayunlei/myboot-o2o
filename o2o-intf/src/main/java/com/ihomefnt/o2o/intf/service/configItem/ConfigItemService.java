package com.ihomefnt.o2o.intf.service.configItem;

import com.ihomefnt.o2o.intf.domain.configItem.dto.ConfigItem;
import com.ihomefnt.o2o.intf.domain.configItem.vo.response.HttpItemResponse;

import java.util.List;


public interface ConfigItemService {
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
	 * 根据配置项id获取具体项
	 * @param configId
	 * @return
	 */
	public List<HttpItemResponse> queryItemByConfigId(Long configId);
}
