package com.ihomefnt.o2o.service.dao.address;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.address.AddressDao;
import com.ihomefnt.o2o.intf.domain.address.doo.TReceiveAddressDo;

@Repository
public class AddressDaoImpl implements AddressDao {

    private static final Logger LOG = LoggerFactory.getLogger(AddressDaoImpl.class);
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.address.AddressDao.";

    @Override
    public TReceiveAddressDo queryAddressByUserId(Long userId) {
        LOG.info("AddressDao.queryAddressByUserId() start");
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryAddressByUserId", userId);
    }

    @Override
    public void addAddress(TReceiveAddressDo address) {
        LOG.info("AddressDao.addAddress() start");
        sqlSessionTemplate.insert(NAME_SPACE + "addAddress", address);
    }
}
