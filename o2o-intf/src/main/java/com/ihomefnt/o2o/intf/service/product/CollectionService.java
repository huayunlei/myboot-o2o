package com.ihomefnt.o2o.intf.service.product;

import com.ihomefnt.o2o.intf.domain.product.doo.TCollection;

public interface CollectionService {

    TCollection queryCollection(Long productId, Long userId, Long type);

}
