package com.plateno.booking.internal.bean.request.common;

import java.io.Serializable;

public class PageReq<T> implements Serializable {
	
	private static final long serialVersionUID = -5881250416998465556L;

	private int total = 0;

	private int pageSize = 10;

	private int pageIndex = 1;

	private int totalPage = 0;
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}