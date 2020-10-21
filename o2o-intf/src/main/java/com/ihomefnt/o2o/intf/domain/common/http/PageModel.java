package com.ihomefnt.o2o.intf.domain.common.http;


import java.util.List;

import com.ihomefnt.o2o.intf.domain.product.doo.Room;
import lombok.Data;

@Data
public class PageModel {
    //结果集 
    @SuppressWarnings("rawtypes")
	private List list; 
       
    //查询记录数 
    private long totalRecords;
       
    //每页多少条数据 
    private int pageSize;

    //第几页 
    private int pageNo;
    
    //总页数
    private long totalPages;
    
    private List<Room> roomList;

    @SuppressWarnings("rawtypes")
	public List getList() {
        return list;
    }

    @SuppressWarnings("rawtypes")
	public void setList(List list) {
        this.list = list;
    }

    public PageModel() {
    }

    public PageModel(List list, long totalRecords, int pageSize, int pageNo, long totalPages) {
        this.list = list;
        this.totalRecords = totalRecords;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.totalPages = totalPages;
    }
}

