package com.ihomefnt.o2o.intf.domain.common.http;

import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-05-07 12:48
 */
@Data
public class PageInfo<T> {
    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int pages;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private int[] navigatepageNums;
    private int navigateFirstPage;
    private int navigateLastPage;
    protected long total;
    protected List<T> list;
}
