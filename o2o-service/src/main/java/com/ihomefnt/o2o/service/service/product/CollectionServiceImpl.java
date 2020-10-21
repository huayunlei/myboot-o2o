package com.ihomefnt.o2o.service.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.dao.product.CollectionDao;
import com.ihomefnt.o2o.intf.domain.product.doo.TCollection;
import com.ihomefnt.o2o.intf.service.product.CollectionService;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionDao collectionDao;
    
    @Override
    public TCollection queryCollection(Long productId, Long userId, Long type) {
        return collectionDao.queryCollection(productId, userId, type);
    }

}
