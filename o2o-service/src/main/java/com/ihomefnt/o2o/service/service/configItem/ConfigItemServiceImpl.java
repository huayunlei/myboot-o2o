package com.ihomefnt.o2o.service.service.configItem;

import com.ihomefnt.o2o.intf.service.configItem.ConfigItemService;
import com.ihomefnt.o2o.intf.dao.configItem.ConfigItemDao;
import com.ihomefnt.o2o.intf.domain.configItem.dto.ConfigItem;
import com.ihomefnt.o2o.intf.domain.configItem.dto.Item;
import com.ihomefnt.o2o.intf.domain.configItem.vo.response.HttpItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigItemServiceImpl implements ConfigItemService {

	@Autowired
    ConfigItemDao configItemDao;
	
	@Override
	public ConfigItem findConfigItemById(Long id) {
		return configItemDao.findConfigItemById(id);
	}

	@Override
	public List<HttpItemResponse> queryItemByConfigId(Long configId) {
		List<HttpItemResponse> itemsResponse = new ArrayList<HttpItemResponse>();
		List<Item> items=configItemDao.queryItemByConfigId(configId);
		if(items!=null&&!items.isEmpty()){
			for (int i = 0,l=items.size(); i < l; i++) {
				HttpItemResponse itemResponse = new HttpItemResponse(items.get(i));
				itemsResponse.add(itemResponse);
			}
		}
		return itemsResponse;
	}

	@Override
	public ConfigItem findConfigItemByConfigName(String configName) {
		return configItemDao.findConfigItemByConfigName(configName);
	}

}
