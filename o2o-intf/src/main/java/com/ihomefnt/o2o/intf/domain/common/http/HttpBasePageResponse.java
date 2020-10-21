package com.ihomefnt.o2o.intf.domain.common.http;

import lombok.Data;

import java.util.List;

/**
 * Created by onefish on 2016/11/3 0003.
 */
@Data
public class HttpBasePageResponse<T> {
    /**
     * 统一返回码
     */
    private long code;
    /**
     * 返回消息
     */
    private String ext;
    /**
     * 返回的主体数据
     */
    private List<T> obj;
    /**
     * 当前页码
     */
    private int pageNo;
    /**
     * 每页的数据条数
     */
    private int pageSize;
    /**
     * 总记录数
     */
    private int totalCount;

    /**
     * 总页数
     */
    private int totalPage;
    
    private String userOfficialName;//用户官方回复名称

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public List<T> getObj() {
        return obj;
    }

    public void setObj(List<T> obj) {
        this.obj = obj;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        if (pageNo<=1) {
            this.pageNo=1;
        } else {
            this.pageNo = pageNo;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize<=0) {
            this.pageSize=20;
        } else {
            this.pageSize = pageSize;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.pageSize = this.pageSize > 0?this.pageSize:20;
        int totalPage = totalCount % this.pageSize == 0?totalCount / this.pageSize : totalCount / this.pageSize + 1;
        this.setTotalPage(totalPage);
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

	public String getUserOfficialName() {
		return userOfficialName;
	}

	public void setUserOfficialName(String userOfficialName) {
		this.userOfficialName = userOfficialName;
	}


}
