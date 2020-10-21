package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liyonggang
 * @create 2018-12-07 0:14
 */
@Data
public class BasePageVo<T> implements Serializable {

    private static final long serialVersionUID = 5695639216518365984L;
    /**
     * 当前页数
     */
    private Integer current;
    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 数据
     */
    private List<T> rows;
    /**
     * 总记录数
     */
    private Integer totalCount;


}
