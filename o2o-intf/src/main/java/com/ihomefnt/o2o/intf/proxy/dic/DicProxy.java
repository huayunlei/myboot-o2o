package com.ihomefnt.o2o.intf.proxy.dic;

import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicListDto;

public interface DicProxy {

	/**
     * 根据父key值查询字典集合
     *
     * @param keyDesc
     * @return
     */
    DicListDto getDicListByKey(String keyDesc);

    /**
     * 根据key值查询字典信息
     *
     * @param keyDesc
     * @return
     */
    DicDto queryDicByKey(String keyDesc);

}
