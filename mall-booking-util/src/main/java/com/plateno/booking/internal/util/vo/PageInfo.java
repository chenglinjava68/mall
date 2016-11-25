package com.plateno.booking.internal.util.vo;

import java.util.List;

/**
 * 分页信息封装
 * @author mogt
 * @date 2016年11月25日
 * @param <T>
 */
public class PageInfo<T> {
	
    /** 总数 */
    private int count;

    /** 页数 */
    private int page;

    /** 总页数 */
    private int pageCount;

    /** 列表 */
    private List<T> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
